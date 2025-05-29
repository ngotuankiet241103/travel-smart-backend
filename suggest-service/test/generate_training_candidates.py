# generate_training_candidates.py
import pandas as pd
import random
import os

os.makedirs("data", exist_ok=True)

# Load dữ liệu gốc
data = pd.read_json("data/training_data.json")

candidates = []
for idx, row in data.iterrows():
    # Positive sample
    candidates.append({
        "userId": row["userId"],
        "current_lat": row["current_lat"],
        "current_lng": row["current_lng"],
        "visited_type_counts": row["visited_type_counts"],
        "candidate_lat": row["target_lat"],
        "candidate_lng": row["target_lng"],
        "candidate_id": row["target_location_id"],
        "candidate_type": row["target_location_type"],
        "label": 1
    })

    # Negative samples
    negative_samples = data[data["target_location_id"] != row["target_location_id"]].sample(
        3, random_state=idx)
    for _, neg in negative_samples.iterrows():
        candidates.append({
            "userId": row["userId"],
            "current_lat": row["current_lat"],
            "current_lng": row["current_lng"],
            "visited_type_counts": row["visited_type_counts"],
            "candidate_lat": neg["target_lat"],
            "candidate_lng": neg["target_lng"],
            "candidate_id": neg["target_location_id"],
            "candidate_type": neg["target_location_type"],
            "label": 0
        })

# Tạo DataFrame
df = pd.DataFrame(candidates)

# Tính khoảng cách
df['dist_to_candidate'] = (
    (df['current_lat'] - df['candidate_lat'])**2 +
    (df['current_lng'] - df['candidate_lng'])**2
) ** 0.5

# Trích xuất sở thích
types = ["TOURIST_ATTRACTION", "RESTAURANT", "SHOPPING", "PARK",
         "CULTURAL_SITE", "ACCOMMODATION", "ENTERTAINMENT"]
for t in types:
    df[f'pref_{t.lower()}'] = df['visited_type_counts'].apply(
        lambda x: x.get(t, 0) if isinstance(x, dict) else 0
    )

df.to_csv("data/training_candidates.csv", index=False)
print("✅ Đã tạo file training_candidates.csv")
