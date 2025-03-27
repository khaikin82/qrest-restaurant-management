package com.khaikin.qrest.table;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;

    @Override
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    @Override
    public Table getTableById(Long id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "tableId", id));
    }

    @Override
    public Table createTable(Table table) {
        return tableRepository.save(table);
    }

    @Override
    public Table updateTable(Long id, Table table) {
        Table existingTable = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "tableId", id));

        existingTable.setName(table.getName());
        existingTable.setFloor(table.getFloor());
        existingTable.setCapacity(table.getCapacity());
        existingTable.setAvailable(table.isAvailable());

        return tableRepository.save(existingTable);
    }

    @Override
    public void deleteTableById(Long id) {
        Table existingTable = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "tableId", id));

        tableRepository.delete(existingTable);
    }
}
