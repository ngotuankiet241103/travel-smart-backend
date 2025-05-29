import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix, roc_auc_score
from imblearn.over_sampling import RandomOverSampler
import joblib

df = pd.read_csv("data/train_pointwise.csv")

feature_cols = [
    "current_lat", "current_lng", "candidate_lat", "candidate_lng", "dist_to_candidate",
    "pref_tourist_attraction", "pref_restaurant", "pref_shopping", "pref_park",
    "pref_cultural_site", "pref_accommodation", "pref_entertainment"
]

X = df[feature_cols]
y = df["label"]

X_train, X_val, y_train, y_val = train_test_split(X, y, stratify=y, random_state=42)

# Nếu dữ liệu vẫn mất cân bằng, giữ lại dòng này, nếu không thì có thể bỏ đi
ros = RandomOverSampler(random_state=42)
X_train, y_train = ros.fit_resample(X_train, y_train)

model = RandomForestClassifier(n_estimators=200, class_weight="balanced", random_state=42)
model.fit(X_train, y_train)

# Save model
joblib.dump(model, "model/rf_recommender.pkl")

# Đánh giá chất lượng trên tập validation
y_pred = model.predict(X_val)
y_proba = model.predict_proba(X_val)[:, 1]
print("Accuracy:", accuracy_score(y_val, y_pred))
print("ROC-AUC:", roc_auc_score(y_val, y_proba))
print("Classification report:\n", classification_report(y_val, y_pred))
print("Confusion matrix:\n", confusion_matrix(y_val, y_pred))
