package com.khaikin.qrest.restauranttable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultipleTableRequestDto {
    private String prefix;   // Tiền tố của bàn (ví dụ: "Bàn", "D", "A", ...)
    private int startTableNumber; // Tổng số bàn cần tạo
    private int endTableNumber;
    private int capacity;    // Số ghế mỗi bàn
}
