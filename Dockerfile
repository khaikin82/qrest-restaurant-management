# Bước 1: Sử dụng hình ảnh base Java (openjdk)
FROM openjdk:17-jdk-slim as builder

# Bước 2: Tạo thư mục làm việc trong container
WORKDIR /app

# Bước 3: Sao chép file JAR từ project vào container
COPY target/qrest-0.0.1-SNAPSHOT.jar /app/qrest-management-app.jar

# Bước 4: Cấu hình để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app/qrest-management-app.jar"]

# Bước 5: Mở cổng mà ứng dụng của bạn chạy trên container
EXPOSE 18080
