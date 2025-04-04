package com.khaikin.qrest.restauranttable;

import java.util.List;

public interface RestaurantTableService {
    List<RestaurantTable> getAllTables();

    RestaurantTable getTableById(Long id);

    RestaurantTable createTable(RestaurantTable restaurantTable);

    List<RestaurantTable> createMultipleTables(MultipleTableRequestDto multipleTableRequest);

    RestaurantTable updateTable(Long id, RestaurantTable restaurantTable);

    void deleteTableById(Long id);
}