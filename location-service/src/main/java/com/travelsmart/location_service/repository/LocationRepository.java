package com.travelsmart.location_service.repository;

import com.travelsmart.location_service.constant.LocationStatus;
import com.travelsmart.location_service.constant.LocationType;
import com.travelsmart.location_service.entity.LocationEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.net.ContentHandler;
import java.util.List;
import java.util.Optional;
@Repository
public interface LocationRepository extends JpaRepository<LocationEntity,Long> {
    @Query(value = "SELECT l.* FROM location l WHERE l.state LIKE ?1 OR l.city LIKE ?1 OR l.name LIKE ?1 OR l.display_name LIKE ?1 AND l.status = ?2 AND type != 13 ",nativeQuery = true)
    List<LocationEntity> findBySearchParam(Pageable pageable, String search, LocationStatus status);
    @Query(value = """
            SELECT * FROM location
            WHERE
            (CAST(SUBSTRING_INDEX(boundingbox,';',1) AS DECIMAL(30, 18)) <= ?2 AND
            CAST(SUBSTRING_INDEX(boundingbox,';',-3) AS DECIMAL(30, 18))  >= ?2) AND
            (
            	CAST(SUBSTRING_INDEX(boundingbox,';',-2) AS DECIMAL(30, 18)) <= ?1 AND
            	CAST(SUBSTRING_INDEX(boundingbox,';',-1) AS DECIMAL(30, 18)) >= ?1
            )
            AND type != 13
            """,nativeQuery = true)
    LocationEntity findByCoordinates(String lon, String lat);
    @Query(value = "SELECT l.* FROM location l WHERE l.lat >= ?1 AND l.lat <= ?2 AND l.lon >= ?3 AND l.lon <= ?4 AND l.display_name LIKE ?5 AND l.type != ?6",nativeQuery = true)
    List<LocationEntity> findByRadius(String minLat,String maxLat,String minLon,String maxLon,String search,LocationType locationType);
    List<LocationEntity> findByCategoryIn(List<String> categories);
    @Transactional
    @Modifying
    @Query(value = "UPDATE location SET area = (ST_GeomFromText(?2, 4326)) WHERE place_id = ?1", nativeQuery = true)
    void savePolygon(Long placeId,String wktPolygon);

    @Query("SELECT l FROM LocationEntity l ORDER BY l.place_id DESC")
    Page<LocationEntity> findAllOrderByIdDesc(Pageable pageable);
    @Query("SELECT l FROM LocationEntity l WHERE  l.type != 13 AND  l.type IN ?2 AND l.address.city = (SELECT s.address.city FROM LocationEntity s WHERE s.place_id = ?1) ORDER BY RAND()")
    List<LocationEntity> findByTypeIn(Long id,List<LocationType> types);
    @Query(value = "SELECT * FROM location WHERE place_id = ?1",nativeQuery = true)
    Optional<LocationEntity> findByPlaceId(Long id);
    @Query("SELECT l FROM LocationEntity l WHERE place_id > 3")
    List<LocationEntity> findAllN();

    LocationEntity findByLonAndLat(String s, String s1);

    List<LocationEntity> findByTypeAndStatus(LocationType locationType, LocationStatus locationStatus);

    List<LocationEntity> findByType(LocationType locationType);
    @Query(value = "SELECT l.* FROM location l WHERE l.lat >= ?1 AND l.lat <= ?2 AND l.lon >= ?3 AND l.lon <= ?4 AND l.display_name LIKE ?5 AND l.type = ?6",nativeQuery = true)
    List<LocationEntity> findByRadiusAndSearchAndType(String s, String s1, String s2, String s3, String search, LocationType type);
}
