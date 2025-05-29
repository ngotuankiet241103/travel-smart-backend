# train_model.py
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
import joblib

df = pd.read_csv("data/training_candidates.csv")

feature_cols = [
    'current_lat', 'current_lng', 'candidate_lat', 'candidate_lng', 'dist_to_candidate',
    'pref_tourist_attraction', 'pref_restaurant', 'pref_shopping', 'pref_park',
    'pref_cultural_site', 'pref_accommodation', 'pref_entertainment'
]

X = df[feature_cols]
y = df['label']

X_train, X_val, y_train, y_val = train_test_split(
    X, y, test_size=0.2, random_state=42, stratify=y)

model = RandomForestClassifier(n_estimators=100, random_state=42, class_weight="balanced")
model.fit(X_train, y_train)

# Lưu mô hình
joblib.dump(model, "model/ranking_model.pkl")
print("✅ Đã huấn luyện và lưu mô hình tại model/ranking_model.pkl")
