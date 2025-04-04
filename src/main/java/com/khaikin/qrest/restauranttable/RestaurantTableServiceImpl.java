package com.khaikin.qrest.restauranttable;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantTableServiceImpl implements RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;

    @Override
    public List<RestaurantTable> getAllTables() {
        return restaurantTableRepository.findAll();
    }

    @Override
    public RestaurantTable getTableById(Long id) {
        return restaurantTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "tableId", id));
    }

    @Override
    public RestaurantTable createTable(RestaurantTable restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }

    @Override
    public RestaurantTable updateTable(Long id, RestaurantTable restaurantTable) {
        RestaurantTable existingRestaurantTable = restaurantTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "tableId", id));

        existingRestaurantTable.setName(restaurantTable.getName());
        existingRestaurantTable.setCapacity(restaurantTable.getCapacity());
        existingRestaurantTable.setAvailable(restaurantTable.isAvailable());

        return restaurantTableRepository.save(existingRestaurantTable);
    }

    @Override
    public void deleteTableById(Long id) {
        RestaurantTable existingRestaurantTable = restaurantTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "tableId", id));

        restaurantTableRepository.delete(existingRestaurantTable);
    }
}
