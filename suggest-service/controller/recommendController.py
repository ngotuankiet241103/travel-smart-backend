from fastapi import APIRouter
from pydantic import BaseModel
from typing import Dict, Any
import joblib
import pandas as pd

router = APIRouter()

# Load model và các thông tin cần thiết
model = joblib.load("model/rf_recommender.pkl")
locs = pd.read_json("location.json", encoding="utf-8")

class RecommendRequest(BaseModel):
    current_lat: float
    current_lng: float
    tourist_attraction: int = 0
    restaurant: int = 0
    shopping: int = 0
    park: int = 0
    cultural_site: int = 0
    accommodation: int = 0
    entertainment: int = 0

@router.post("/suggests")
def recommend(req: RecommendRequest):
    locs_df = locs.copy()
    if "lng" not in locs_df.columns and "lon" in locs_df.columns:
        locs_df["lng"] = locs_df["lon"]
    locs_df = locs_df.rename(columns={"lat": "candidate_lat", "lng": "candidate_lng"})
    locs_df["current_lat"] = req.current_lat
    locs_df["current_lng"] = req.current_lng
    locs_df["dist_to_candidate"] = (
        (locs_df["current_lat"] - locs_df["candidate_lat"])**2 +
        (locs_df["current_lng"] - locs_df["candidate_lng"])**2
    )**0.5
    locs_df["pref_tourist_attraction"] = req.tourist_attraction
    locs_df["pref_restaurant"] = req.restaurant
    locs_df["pref_shopping"] = req.shopping
    locs_df["pref_park"] = req.park
    locs_df["pref_cultural_site"] = req.cultural_site
    locs_df["pref_accommodation"] = req.accommodation
    locs_df["pref_entertainment"] = req.entertainment

    feature_cols = [
        "current_lat", "current_lng", "candidate_lat", "candidate_lng", "dist_to_candidate",
        "pref_tourist_attraction", "pref_restaurant", "pref_shopping", "pref_park",
        "pref_cultural_site", "pref_accommodation", "pref_entertainment"
    ]
    X = locs_df[feature_cols]
    probs = model.predict_proba(X)[:, 1]
    locs_df["score"] = probs
    topk = locs_df.sort_values("score", ascending=False).head(10)
    return topk[["place_id", "name", "type", "score"]].to_dict(orient="records")