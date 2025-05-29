import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix

df = pd.read_csv("data/train_pointwise.csv")

feature_cols = [
    "current_lat", "current_lng", "candidate_lat", "candidate_lng", "dist_to_candidate",
    "pref_tourist_attraction", "pref_restaurant", "pref_shopping", "pref_park",
    "pref_cultural_site", "pref_accommodation", "pref_entertainment"
]

X = df[feature_cols]
y = df["label"]

X_train, X_val, y_train, y_val = train_test_split(X, y, stratify=y, random_state=42)

model = RandomForestClassifier(n_estimators=100, class_weight="balanced", random_state=42)
model.fit(X_train, y_train)

# Save model (nếu bạn muốn dùng lại)
import joblib
joblib.dump(model, "model/rf_recommender.pkl")

# Đánh giá chất lượng trên tập validation
y_pred = model.predict(X_val)
print("Accuracy:", accuracy_score(y_val, y_pred))
print("Classification report:\n", classification_report(y_val, y_pred))
print("Confusion matrix:\n", confusion_matrix(y_val, y_pred))