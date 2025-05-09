package com.khaikin.qrest.table;

import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantTableService {
    List<RestaurantTable> getAllTables();

    RestaurantTable getTableById(Long id);

    List<RestaurantTable> getAvailableTablesAtTime(LocalDateTime time);

    RestaurantTable createTable(RestaurantTable restaurantTable);

    List<RestaurantTable> createMultipleTables(MultipleTableRequestDto multipleTableRequest);

    RestaurantTable updateTable(Long id, RestaurantTable restaurantTable);

    RestaurantTable updateTableStatus(Long id, RestaurantTableStatus status);
    RestaurantTable updateTableStatus(RestaurantTable restaurantTable, RestaurantTableStatus status);
    RestaurantTable updateTableStatus(Long id, RestaurantTableStatus status, RestaurantTableStatus errorStatus);

    void deleteTableById(Long id);
}