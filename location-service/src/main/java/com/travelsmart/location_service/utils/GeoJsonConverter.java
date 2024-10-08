package com.travelsmart.location_service.utils;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
public class GeoJsonConverter {

    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public static Polygon createPolygon(Object[][] coordinates) {
        Coordinate[] polygonCoordinates = new Coordinate[coordinates.length + 1];

        // Copy coordinates and ensure the polygon is closed by repeating the first point at the end
        for (int i = 0; i < coordinates.length; i++) {
            polygonCoordinates[i] = new Coordinate(Double.parseDouble(coordinates[i][0].toString()),
                    Double.parseDouble(coordinates[i][1].toString()));
        }

        // Closing the polygon (last point should be the same as the first)
        polygonCoordinates[coordinates.length] = new Coordinate(Double.parseDouble(coordinates[0][0].toString()),
                Double.parseDouble(coordinates[0][1].toString()));

        return geometryFactory.createPolygon(polygonCoordinates);
    }
    public static double[][] convertPolygonToDoubleArray(Polygon polygon) {
        // Extract the coordinates from the polygon
        Coordinate[] coordinates = polygon.getCoordinates();

        // Initialize a 2D array to store [longitude, latitude]
        double[][] result = new double[coordinates.length][2];

        // Fill the 2D array with coordinates
        for (int i = 0; i < coordinates.length; i++) {
            result[i][0] = coordinates[i].x; // longitude
            result[i][1] = coordinates[i].y; // latitude
        }

        return result;
    }
}
