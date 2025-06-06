import pandas as pd
import joblib
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix

# Load dữ liệu
data = pd.read_json("data/training_data.json")

# Tính khoảng cách đến điểm đến
data['dist_to_target'] = (
    (data['current_lat'] - data['target_lat'])**2 +
    (data['current_lng'] - data['target_lng'])**2
)**0.5

# Trích xuất đặc trưng
data['pref_tourist_attraction'] = data['visited_type_counts'].apply(
    lambda x: x.get("TOURIST_ATTRACTION", 0) if isinstance(x, dict) else 0
)
data['pref_restaurant'] = data['visited_type_counts'].apply(
    lambda x: x.get("RESTAURANT", 0) if isinstance(x, dict) else 0
)
data['pref_shopping'] = data['visited_type_counts'].apply(
    lambda x: x.get("SHOPPING", 0) if isinstance(x, dict) else 0
)
data['pref_park'] = data['visited_type_counts'].apply(
    lambda x: x.get("PARK", 0) if isinstance(x, dict) else 0
)
data['pref_cultural_site'] = data['visited_type_counts'].apply(
    lambda x: x.get("CULTURAL_SITE", 0) if isinstance(x, dict) else 0
)
data['pref_accommodation'] = data['visited_type_counts'].apply(
    lambda x: x.get("ACCOMMODATION", 0) if isinstance(x, dict) else 0
)
data['pref_entertainment'] = data['visited_type_counts'].apply(
    lambda x: x.get("ENTERTAINMENT", 0) if isinstance(x, dict) else 0
)

X = data[
    [
        'current_lat', 'current_lng', 'target_lat', 'target_lng', 'dist_to_target',
        'pref_tourist_attraction', 'pref_restaurant', 'pref_shopping', 'pref_park',
        'pref_cultural_site', 'pref_accommodation', 'pref_entertainment'
    ]
]
y = data['target_location_id']

# Load model và encoder
model = joblib.load("recommendation_model.pkl")
label_encoder = joblib.load("location_encoder.pkl")
y_encoded = label_encoder.transform(y)

# Dự đoán trên tập dữ liệu
y_pred = model.predict(X)

# Đánh giá
print("Accuracy:", accuracy_score(y_encoded, y_pred))
print("Classification report:\n", classification_report(y_encoded, y_pred))
print("Confusion matrix:\n", confusion_matrix(y_encoded, y_pred))