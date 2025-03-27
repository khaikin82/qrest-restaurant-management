package com.khaikin.qrest.table;

import java.util.List;

public interface TableService {
    List<Table> getAllTables();

    Table getTableById(Long id);

    Table createTable(Table table);

    Table updateTable(Long id, Table table);

    void deleteTableById(Long id);
}