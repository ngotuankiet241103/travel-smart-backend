import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import LabelEncoder
import joblib
import logging

# Cấu hình logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s"
)

# Load dữ liệu
data = pd.read_json("data/training_data.json")
logging.info("Loaded data:\n%s", data.head())

# Tính khoảng cách đến điểm đến
data['dist_to_target'] = (
    (data['current_lat'] - data['target_lat'])**2 +
    (data['current_lng'] - data['target_lng'])**2
)**0.5
print("Giá trị dist_to_target:")
print(data['dist_to_target'])

# Trích xuất sở thích người dùng — xử lý nếu dữ liệu không phải dict
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

# Tập đặc trưng và nhãn
X = data[
    [
        'current_lat', 'current_lng', 'target_lat', 'target_lng', 'dist_to_target',
        'pref_tourist_attraction', 'pref_restaurant', 'pref_shopping', 'pref_park',
        'pref_cultural_site', 'pref_accommodation', 'pref_entertainment'
    ]
]
y = data['target_location_id']

# Mã hóa nhãn
label_encoder = LabelEncoder()
y_encoded = label_encoder.fit_transform(y)

if len(data) < 2:
    logging.warning("⚠️ Dữ liệu quá ít để chia train/test. Sẽ huấn luyện trên toàn bộ dữ liệu.")
    X_train, y_train = X, y_encoded
else:
    from sklearn.model_selection import train_test_split
    X_train, _, y_train, _ = train_test_split(X, y_encoded, test_size=0.2, random_state=42)

# Huấn luyện mô hình
model = RandomForestClassifier(n_estimators=100, random_state=42,class_weight="balanced")
model.fit(X_train, y_train)

# Lưu model và encoder
joblib.dump(model, "recommendation_model.pkl")
joblib.dump(label_encoder, "location_encoder.pkl")

logging.info("✅ Mô hình đã được huấn luyện và lưu thành công.")
