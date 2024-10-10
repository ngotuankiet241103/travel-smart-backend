package com.travelsmart.trip_service.utils;

import com.travelsmart.trip_service.dto.response.httpclient.DistanceResponse;

import java.util.*;

public class NearestNeighborTSP {

    // Hàm tính tổng khoảng cách cho hành trình
    public static double calculateTotalDistance(int[] path, double[][] distanceMatrix) {
        double totalDistance = 0;
        for (int i = 0; i < path.length - 1; i++) {
            totalDistance += distanceMatrix[path[i]][path[i + 1]];
        }
        totalDistance += distanceMatrix[path[path.length - 1]][path[0]]; // Quay lại điểm bắt đầu
        return totalDistance;
    }

    // Hàm tìm đường đi theo thuật toán Nearest Neighbor
    public static int[] nearestNeighbor(double[][] distanceMatrix, int start) {
        System.out.println(distanceMatrix.length);
        int n = distanceMatrix.length; // Số lượng điểm
        boolean[] visited = new boolean[n]; // Đánh dấu các điểm đã ghé thăm
        int[] path = new int[n]; // Lưu đường đi
        int current = start; // Bắt đầu từ điểm xuất phát
        path[0] = current;
        visited[current] = true;

        // Lặp để tìm điểm gần nhất
        for (int i = 1; i < n; i++) {
            double minDistance = Double.MAX_VALUE;
            int nearest = -1;

            // Tìm điểm gần nhất chưa ghé thăm
            for (int j = 0; j < n; j++) {
                if (!visited[j] && distanceMatrix[current][j] < minDistance) {
                    minDistance = distanceMatrix[current][j];
                    nearest = j;
                }
            }

            // Di chuyển đến điểm gần nhất
            current = nearest;
            path[i] = current;
            visited[current] = true;
        }

        return path; // Trả về đường đi
    }
    // Hàm tương tự như ở ví dụ trước để tính toán số địa điểm cho một ngày
    public static Map<String,Object> planDay(DistanceResponse[][] distanceMatrix, boolean[] visited, double[] visitTimes, double maxHoursPerDay,int currentLocation) {
        int n = distanceMatrix.length;
        List<Integer> dailyItinerary = new ArrayList<>();
        int startLocation = 0;
        double totalHours = 0;
        System.out.println(visited[currentLocation]);
        if(!visited[currentLocation]){

            dailyItinerary.add(currentLocation);
            visited[currentLocation] = true;
        }


        while (true) {
            double minDistance = Double.MAX_VALUE;
            int nextLocation = -1;

            // Tìm địa điểm gần nhất chưa được ghé thăm
            for (int i = startLocation; i < n; i++) {
                if (!visited[i] && distanceMatrix[currentLocation][i].getDistance() < minDistance) {
                    minDistance = distanceMatrix[currentLocation][i].getDistance();
                    nextLocation = i;
                }
            }

            // Nếu không còn địa điểm nào có thể ghé thăm, thoát khỏi vòng lặp
            if (nextLocation == -1) {
                break;
            }

            // Tính tổng thời gian (di chuyển và tham quan) cho địa điểm tiếp theo
            double travelTime = distanceMatrix[currentLocation][nextLocation].getTime(); // Thời gian di chuyển
            double visitTime = visitTimes[nextLocation]; // Thời gian tham quan tại địa điểm
            System.out.println("travel time" + travelTime);
            System.out.println("visit time"  + visitTime);
            System.out.println("time" + totalHours + travelTime + visitTime);
            currentLocation = nextLocation;
            if (totalHours + travelTime + visitTime <= maxHoursPerDay) {
                totalHours += travelTime + visitTime;
                dailyItinerary.add(nextLocation);
                visited[nextLocation] = true;

            } else {
                break;
            }

        }
        Map<String,Object> response = new HashMap<>();
        response.put("visited",visited);
        response.put("path",dailyItinerary);
        response.put("currentLocation",currentLocation);
        return response;
    }

}
