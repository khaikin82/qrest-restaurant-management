package com.khaikin.qrest.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    @Query(value = """
    SELECT rt.* FROM restaurant_table rt
    WHERE rt.id NOT IN (
        SELECT r.restaurant_table_id FROM reservation r
        WHERE :time BETWEEN DATE_SUB(r.arrival_time, INTERVAL 3 HOUR) AND DATE_ADD(r.arrival_time, INTERVAL 3 HOUR)
    )
""", nativeQuery = true)
    List<RestaurantTable> findAvailableTablesAtTime(@Param("time") LocalDateTime time);
}
