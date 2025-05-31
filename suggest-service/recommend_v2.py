import pandas as pd
import joblib
import json

# Load model
model = joblib.load("model/rf_recommender.pkl")

# Load danh sách địa điểm (candidate set)
locs = pd.read_json("location.json", encoding="utf-8")

# User input
user_input = {
    "current_lat": 16.061475,
    "current_lng": 108.2328775,
    "visited_type_counts": {
        "TOURIST_ATTRACTION": 0,
        "RESTAURANT": 5,
        "SHOPPING": 1,
        "PARK": 0,
        "CULTURAL_SITE": 0,
        "ACCOMMODATION": 0,
        "ENTERTAINMENT": 0
    }
}


# Đảm bảo đúng tên cột (location.json phải có lat, lon, place_id, name, type)
# Nếu file của bạn dùng "lon" thay vì "lng", hãy tạo thêm cột "lng"
if "lng" not in locs.columns and "lon" in locs.columns:
    locs["lng"] = locs["lon"]

# Đảm bảo đúng tên cột cho model
locs = locs.rename(columns={"lat": "candidate_lat", "lng": "candidate_lng"})

# Thêm current_lat/current_lng cho tất cả candidate (giống user input)
locs["current_lat"] = user_input["current_lat"]
locs["current_lng"] = user_input["current_lng"]

# Tính lại khoảng cách
locs["dist_to_candidate"] = (
    (locs["current_lat"] - locs["candidate_lat"])**2 +
    (locs["current_lng"] - locs["candidate_lng"])**2
)**0.5

for t in ["TOURIST_ATTRACTION", "RESTAURANT", "SHOPPING", "PARK", "CULTURAL_SITE", "ACCOMMODATION", "ENTERTAINMENT"]:
    locs[f"pref_{t.lower()}"] = user_input["visited_type_counts"].get(t, 0)

feature_cols = [
    "current_lat", "current_lng", "candidate_lat", "candidate_lng", "dist_to_candidate",
    "pref_tourist_attraction", "pref_restaurant", "pref_shopping", "pref_park",
    "pref_cultural_site", "pref_accommodation", "pref_entertainment"
]

X = locs[feature_cols]

probs = model.predict_proba(X)[:, 1]
locs["score"] = probs

topk = locs.sort_values("score", ascending=False).head(10)
print(topk[["place_id", "name", "type", "score"]])
