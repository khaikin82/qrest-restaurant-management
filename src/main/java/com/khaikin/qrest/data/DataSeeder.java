package com.khaikin.qrest.data;

import com.khaikin.qrest.category.Category;
import com.khaikin.qrest.category.CategoryService;
import com.khaikin.qrest.combo.ComboService;
import com.khaikin.qrest.food.Food;
import com.khaikin.qrest.food.FoodService;
import com.khaikin.qrest.restauranttable.MultipleTableRequestDto;
import com.khaikin.qrest.restauranttable.RestaurantTable;
import com.khaikin.qrest.restauranttable.RestaurantTableRepository;
import com.khaikin.qrest.restauranttable.RestaurantTableService;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final RestaurantTableService restaurantTableService;
    private final FoodService foodService;
    private final ComboService comboService;
    private final CategoryService categoryService;

    @Override
    public void run(String... args)
            throws Exception {

        List<MultipleTableRequestDto> multipleTableRequestDtos = new ArrayList<>();
        multipleTableRequestDtos.add(new MultipleTableRequestDto("A",1, 4, 2));
        multipleTableRequestDtos.add(new MultipleTableRequestDto("A", 5, 12, 4));
        multipleTableRequestDtos.add(new MultipleTableRequestDto("B", 1, 6, 4));
        multipleTableRequestDtos.add(new MultipleTableRequestDto("B", 7, 10, 8));
        multipleTableRequestDtos.add(new MultipleTableRequestDto("C", 1, 5, 12));

        for (MultipleTableRequestDto multipleTableRequestDto : multipleTableRequestDtos) {
            restaurantTableService.createMultipleTables(multipleTableRequestDto);
        }

        List<Category> categories = new ArrayList<>();
        // Thêm các Category vào danh sách
        categories.add(new Category("Noodles", "Various types of noodle dishes"));
        categories.add(new Category("Rice", "Rice-based dishes from various cuisines"));
        categories.add(new Category("Vege", "Vegetarian dishes"));
        categories.add(new Category("Drink", "Refreshing beverages"));
        categories.add(new Category("Dessert", "Sweet courses to end a meal"));
        categories.add(new Category("Fried Food", "Crispy fried dishes"));

        for (Category category : categories) {
            categoryService.createCategory(category);
        }

        // Tạo các món ăn mẫu và liên kết với Category
        List<Food> foods = new ArrayList<>();

        foods.add(new Food("Beef Noodles", "Noodles stir-fried with tender beef and vegetables", 7.99, 50, "https://img.freepik.com/free-photo/high-angle-vietnamese-dish-with-mint_23-2148381198.jpg?t=st=1744032896~exp=1744036496~hmac=bb2d5b91377fd0f77afdb5b0d486d350f8c9a5d5e794092e06b61939100ea10b&w=826", categories.get(0))); // Noodles
        foods.add(new Food("Chicken Fried Rice", "Fried rice with juicy chicken and mixed vegetables", 6.99, 75, "https://img.freepik.com/free-photo/high-angle-traditional-asian-meal-with-chopsticks_23-2148694371.jpg?ga=GA1.1.1324529926.1734790422&semt=ais_hybrid&w=740", categories.get(1))); // Rice
        foods.add(new Food("Vegetarian Salad", "A healthy mix of fresh vegetables and herbs", 5.49, 30, "https://img.freepik.com/free-photo/top-view-tasty-salad-with-vegetables_23-2148515491.jpg?ga=GA1.1.1324529926.1734790422&semt=ais_hybrid&w=740", categories.get(2))); // Vege
        foods.add(new Food("Pineapple Fried Rice", "Fragrant rice stir-fried with pineapple, shrimp, and cashews", 8.49, 100, "https://img.freepik.com/free-photo/high-view-pineapple-plate-with-cutlery_23-2148494708.jpg?ga=GA1.1.1324529926.1734790422&semt=ais_hybrid&w=740", categories.get(1))); // Rice
        foods.add(new Food("Spaghetti Bolognese", "Classic Italian spaghetti with a rich meat sauce", 9.99, 50, "https://example.com/spaghetti_bolognese.jpg", categories.get(0))); // Noodles
        foods.add(new Food("Vegetable Soup", "A warming soup made from seasonal vegetables", 4.99, 40, "https://example.com/vegetable_soup.jpg", categories.get(2))); // Vege
        foods.add(new Food("Fried Chicken", "Crispy and juicy fried chicken pieces", 6.49, 150, "https://example.com/fried_chicken.jpg", categories.get(5))); // Fried Food
        foods.add(new Food("Chicken Wings", "Spicy grilled chicken wings served with dipping sauce", 5.99, 120, "https://example.com/chicken_wings.jpg", categories.get(5))); // Fried Food
        foods.add(new Food("Beef Burger", "Juicy beef patty with fresh lettuce, tomato, and cheese", 7.49, 80, "https://img.freepik.com/free-photo/side-view-double-cheeseburger-with-grilled-beef-patties-cheese-lettuce-leaf-burger-buns_141793-4883.jpg?ga=GA1.1.1324529926.1734790422&semt=ais_hybrid&w=740", categories.get(5))); // Fried Food
        foods.add(new Food("Veggie Burger", "Delicious veggie patty with fresh vegetables", 6.49, 60, "https://example.com/veggie_burger.jpg", categories.get(2))); // Vege
        foods.add(new Food("Grilled Fish", "Grilled fish served with a lemon butter sauce", 10.49, 40, "https://example.com/grilled_fish.jpg", categories.get(0))); // Noodles
        foods.add(new Food("Tacos", "Mexican tacos filled with beef, lettuce, and salsa", 5.49, 90, "https://example.com/tacos.jpg", categories.get(5))); // Fried Food
        foods.add(new Food("Fish and Chips", "Crispy fried fish served with golden fries", 8.99, 110, "https://example.com/fish_and_chips.jpg", categories.get(5))); // Fried Food
        foods.add(new Food("Lamb Kebabs", "Juicy grilled lamb served with rice and vegetables", 11.99, 60, "https://example.com/lamb_kebabs.jpg", categories.get(5))); // Fried Food
        foods.add(new Food("Pasta Primavera", "Pasta tossed with fresh vegetables in a light sauce", 7.99, 70, "https://example.com/pasta_primavera.jpg", categories.get(0))); // Noodles
        foods.add(new Food("Cheeseburger", "Classic cheeseburger with pickles, onions, and ketchup", 6.99, 130, "https://example.com/cheeseburger.jpg", categories.get(5))); // Fried Food
        foods.add(new Food("Mushroom Risotto", "Creamy risotto with fresh mushrooms and parmesan cheese", 8.49, 50, "https://example.com/mushroom_risotto.jpg", categories.get(2))); // Vege
        foods.add(new Food("Peking Duck", "Crispy duck served with pancakes and hoisin sauce", 14.99, 30, "https://example.com/peking_duck.jpg", categories.get(5))); // Fried Food
        foods.add(new Food("Pizza Margherita", "Simple pizza with fresh mozzarella, basil, and tomato sauce", 9.49, 80, "https://example.com/pizza_margherita.jpg", categories.get(0))); // Noodles
        foods.add(new Food("Chocolate Cake", "Rich chocolate cake with creamy frosting", 5.99, 60, "https://example.com/chocolate_cake.jpg", categories.get(4))); // Dessert

        for (Food food : foods) {
            foodService.createFood(food);
        }
    }
}
