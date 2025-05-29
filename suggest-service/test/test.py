import joblib
import numpy as np

# Load model and encoder
model = joblib.load("recommendation_model.pkl")
label_encoder = joblib.load("location_encoder.pkl")

# Chọn tọa độ không trùng training set
current_lat = 11.9357221
current_lng = 108.4415459
target_lat = 11.4547262
target_lng = 107.31978062890363

# Tính khoảng cách
dist_to_target = ((current_lat - target_lat)**2 + (current_lng - target_lng)**2) ** 0.5

# Sở thích giả định theo đúng các type
pref_tourist_attraction = 0
pref_restaurant = 10
pref_shopping = 0
pref_park = 3
pref_cultural_site = 0
pref_accommodation = 1
pref_entertainment = 0

test_sample = np.array([
    [current_lat, current_lng, target_lat, target_lng, dist_to_target,
    pref_tourist_attraction, pref_restaurant, pref_shopping, pref_park,
    pref_cultural_site, pref_accommodation, pref_entertainment]
])

# Predict
pred = model.predict(test_sample)
pred_label = label_encoder.inverse_transform(pred)

print("Predicted target_location_id:", pred_label[0])