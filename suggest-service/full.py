import pandas as pd
import random

# Load dữ liệu gốc
data = pd.read_json("data/training_data.json")

# Tạo candidate set
candidates = []
for idx, row in data.iterrows():
    # Thêm đúng target (label=1)
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
    # Thêm các negative sample (label=0)
    negative_samples = data[data["target_location_id"] != row["target_location_id"]].sample(3, random_state=idx)  # 3 negative mỗi user
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

df_candidates = pd.DataFrame(candidates)
# Tính khoảng cách
df_candidates['dist_to_candidate'] = (
    (df_candidates['current_lat'] - df_candidates['candidate_lat'])**2 +
    (df_candidates['current_lng'] - df_candidates['candidate_lng'])**2
)**0.5

# Trích xuất sở thích
for t in ["TOURIST_ATTRACTION", "RESTAURANT", "SHOPPING", "PARK", "CULTURAL_SITE", "ACCOMMODATION", "ENTERTAINMENT"]:
    df_candidates[f'pref_{t.lower()}'] = df_candidates['visited_type_counts'].apply(
        lambda x: x.get(t, 0) if isinstance(x, dict) else 0
    )
    from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split

feature_cols = [
    'current_lat', 'current_lng', 'candidate_lat', 'candidate_lng', 'dist_to_candidate',
    'pref_tourist_attraction', 'pref_restaurant', 'pref_shopping', 'pref_park',
    'pref_cultural_site', 'pref_accommodation', 'pref_entertainment'
]
X = df_candidates[feature_cols]
y = df_candidates['label']

X_train, X_val, y_train, y_val = train_test_split(X, y, test_size=0.2, random_state=42, stratify=y)

model = RandomForestClassifier(n_estimators=100, random_state=42, class_weight="balanced")
model.fit(X_train, y_train)
# Với mỗi user, lấy danh sách candidate, dự đoán xác suất, sắp xếp giảm dần
user_id = "u1"
user_candidates = df_candidates[df_candidates["userId"] == user_id]
X_user = user_candidates[feature_cols]
probs = model.predict_proba(X_user)[:, 1]
user_candidates = user_candidates.copy()
user_candidates["score"] = probs
ranked = user_candidates.sort_values("score", ascending=False)
print(ranked[["candidate_id", "score", "label"]])