# recommend.py
import pandas as pd
import joblib

# Load dữ liệu và model
df = pd.read_csv("data/training_candidates.csv")
model = joblib.load("model/ranking_model.pkl")

feature_cols = [
    'current_lat', 'current_lng', 'candidate_lat', 'candidate_lng', 'dist_to_candidate',
    'pref_tourist_attraction', 'pref_restaurant', 'pref_shopping', 'pref_park',
    'pref_cultural_site', 'pref_accommodation', 'pref_entertainment'
]

# Gợi ý cho user cụ thể
user_id = "u1"
user_candidates = df[df["userId"] == user_id]

X_user = user_candidates[feature_cols]
probs = model.predict_proba(X_user)[:, 1]

user_candidates = user_candidates.copy()
user_candidates["score"] = probs
ranked = user_candidates.sort_values("score", ascending=False)

print(f"🏖️ Gợi ý cho user {user_id}:")
print(ranked[["candidate_id", "score", "label"]])
