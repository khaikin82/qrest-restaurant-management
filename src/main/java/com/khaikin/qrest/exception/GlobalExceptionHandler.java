package com.khaikin.qrest.exception;

import com.khaikin.qrest.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse> handleConflictException(ConflictException e) {
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors()
//                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(new ApiResponse<>(false, "Validation failed", errors));
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ApiResponse<>(false, "Internal Server Error: " + ex.getMessage(), null));
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleUnwantedException(Exception e) {
        // Log lỗi ra và ẩn đi message thực sự
        e.printStackTrace();  // Thực tế người ta dùng logger
        String message = "Unknown error";
        ApiResponse apiResponse = new ApiResponse(message, false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

}
