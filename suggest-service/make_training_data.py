import pandas as pd
import json
import random
import numpy as np

# Load dữ liệu từ file json
with open("data/training_data.json", "r", encoding="utf-8") as f:
    raw_data = json.load(f)

data = pd.DataFrame(raw_data)

# Load danh sách location
with open("location.json", "r", encoding="utf-8") as f:
    all_locs = json.load(f)

df_locs = pd.DataFrame(all_locs).set_index("place_id")
if "lat" in df_locs.columns:
    df_locs["lat"] = pd.to_numeric(df_locs["lat"], errors="coerce")
if "lon" in df_locs.columns:
    df_locs["lon"] = pd.to_numeric(df_locs["lon"], errors="coerce")

def euclidean_distance(lat1, lon1, lat2, lon2):
    return np.sqrt((lat1 - lat2)**2 + (lon1 - lon2)**2)

def get_candidates_within_radius(df, center_lat, center_lon, radius_km=20):
    # 1 độ ~ 111km, nên 20km ~ 0.18 độ
    radius_deg = radius_km / 111
    dists = euclidean_distance(df["lat"], df["lon"], center_lat, center_lon)
    return df[dists <= radius_deg]

samples = []

for idx, row in data.iterrows():
    # 3 positive: chọn ngẫu nhiên 3 địa điểm trong bán kính 20km từ vị trí hiện tại
    candidates = get_candidates_within_radius(df_locs, row["current_lat"], row["current_lng"], radius_km=20)
    candidates = candidates[~candidates.index.isin([row["target_location_id"]])]
    if len(candidates) >= 3:
        pos_samples = candidates.sample(3, random_state=idx)
    elif len(candidates) > 0:
        pos_samples = candidates
    else:
        continue  # Không có candidate nào gần, bỏ qua user này

    for cid, loc in pos_samples.iterrows():
        samples.append({
            "userId": row["userId"],
            "current_lat": float(row["current_lat"]),
            "current_lng": float(row["current_lng"]),
            "visited_type_counts": row["visited_type_counts"],
            "candidate_id": cid,
            "candidate_lat": float(loc["lat"]),
            "candidate_lng": float(loc["lon"]),
            "candidate_type": loc["type"],
            "label": 1
        })

    # Negative: chọn ngẫu nhiên 3 địa điểm ngoài bán kính 20km
    far_candidates = df_locs[~df_locs.index.isin(pos_samples.index)]
    far_candidates = far_candidates[~far_candidates.index.isin([row["target_location_id"]])]
    if len(far_candidates) >= 3:
        neg_samples = far_candidates.sample(3, random_state=idx)
    elif len(far_candidates) > 0:
        neg_samples = far_candidates
    else:
        continue

    for cid, loc in neg_samples.iterrows():
        samples.append({
            "userId": row["userId"],
            "current_lat": float(row["current_lat"]),
            "current_lng": float(row["current_lng"]),
            "visited_type_counts": row["visited_type_counts"],
            "candidate_id": cid,
            "candidate_lat": float(loc["lat"]),
            "candidate_lng": float(loc["lon"]),
            "candidate_type": loc["type"],
            "label": 0
        })

df_train = pd.DataFrame(samples)

# Ép kiểu lại cho chắc chắn
for col in ["current_lat", "current_lng", "candidate_lat", "candidate_lng"]:
    df_train[col] = pd.to_numeric(df_train[col], errors="coerce")

# Tính khoảng cách giống file main.py
df_train["dist_to_candidate"] = (
    (df_train["current_lat"] - df_train["candidate_lat"])**2 +
    (df_train["current_lng"] - df_train["candidate_lng"])**2
)**0.5

# Chuyển visited_type_counts thành features
for t in ["TOURIST_ATTRACTION", "RESTAURANT", "SHOPPING", "PARK", "CULTURAL_SITE", "ACCOMMODATION", "ENTERTAINMENT"]:
    df_train[f"pref_{t.lower()}"] = df_train["visited_type_counts"].apply(lambda x: x.get(t, 0))

# Lưu ra file csv để train
df_train.to_csv("data/train_pointwise.csv", index=False, encoding="utf-8")
