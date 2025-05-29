import json
import random
import math

# Load locations
with open("location.json", encoding="utf-8") as f:
    locations = json.load(f)

# Only keep locations with valid lat/lon and type
valid_locations = [
    loc for loc in locations
    if "lat" in loc and "lon" in loc and "type" in loc and loc["type"] in [
        "TOURIST_ATTRACTION", "RESTAURANT", "SHOPPING", "PARK", "CULTURAL_SITE", "ACCOMMODATION", "ENTERTAINMENT"
    ]
]

types = ["TOURIST_ATTRACTION", "RESTAURANT", "SHOPPING", "PARK", "CULTURAL_SITE", "ACCOMMODATION", "ENTERTAINMENT"]

def random_visited_type_counts():
    return {t: random.randint(0, 5) for t in types}

def haversine(lat1, lon1, lat2, lon2):
    # Bán kính trái đất (km)
    R = 6371.0
    phi1 = math.radians(lat1)
    phi2 = math.radians(lat2)
    dphi = math.radians(lat2 - lat1)
    dlambda = math.radians(lon2 - lon1)
    a = math.sin(dphi/2)**2 + math.cos(phi1)*math.cos(phi2)*math.sin(dlambda/2)**2
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    return R * c

training_data = []
num_samples = 8000  # Change this as needed
max_distance_km = 200

for i in range(num_samples):
    while True:
        current = random.choice(valid_locations)
        target = random.choice(valid_locations)
        # Ensure current and target are not the same
        if current["place_id"] == target["place_id"]:
            continue
        distance = haversine(float(current["lat"]), float(current["lon"]), float(target["lat"]), float(target["lon"]))
        if distance <= max_distance_km:
            break
    entry = {
        "userId": f"u{i+1}",
        "current_lat": float(current["lat"]),
        "current_lng": float(current["lon"]),
        "visited_type_counts": random_visited_type_counts(),
        "target_location_id": f"loc{target['place_id']}",
        "target_location_type": target["type"],
        "target_lat": float(target["lat"]),
        "target_lng": float(target["lon"])
    }
    training_data.append(entry)

with open("data/training_data.json", "w", encoding="utf-8") as f:
    json.dump(training_data, f, ensure_ascii=False, indent=2)

print(f"Generated {num_samples} training samples.")