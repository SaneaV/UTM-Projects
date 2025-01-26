package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Scanner;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class UtmShopClient {

  private static final HttpClient client = createHttpClient();
  private static final String BASE_URL = "https://localhost:5001/api/Category";
  private static final Gson gson = new Gson();

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("\nChoose an action:");
      System.out.println("1. List categories");
      System.out.println("2. Show category details");
      System.out.println("3. Create a new category");
      System.out.println("4. Delete a category");
      System.out.println("5. Update a category title");
      System.out.println("6. List products in a category");
      System.out.println("7. Add a product to a category");
      System.out.println("0. Exit");
      System.out.print("Your choice: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      try {
        switch (choice) {
          case 1 -> listCategories();
          case 2 -> showCategoryDetails(scanner);
          case 3 -> createCategory(scanner);
          case 4 -> deleteCategory(scanner);
          case 5 -> updateCategoryTitle(scanner);
          case 6 -> listProducts(scanner);
          case 7 -> addProductToCategory(scanner);
          case 0 -> {
            return;
          }
          default -> System.out.println("Invalid choice, try again.");
        }
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }

  private static void listCategories() throws Exception {
    executeGetRequest("/categories", "Categories:");
  }

  private static void showCategoryDetails(Scanner scanner) throws Exception {
    int categoryId = readIntInput(scanner, "Enter category ID: ");
    executeGetRequest("/categories/" + categoryId, "Category Details:");
  }

  private static void createCategory(Scanner scanner) throws Exception {
    String title = readStringInput(scanner, "Enter category name: ");
    sendPostRequest("/categories", new CreateCategoryRequest(title), "Category created successfully.");
  }

  private static void deleteCategory(Scanner scanner) throws Exception {
    int categoryId = readIntInput(scanner, "Enter category ID to delete: ");
    sendDeleteRequest("/categories/" + categoryId, "Category deleted successfully.");
  }

  private static void updateCategoryTitle(Scanner scanner) throws Exception {
    int categoryId = readIntInput(scanner, "Enter category ID to update: ");
    String title = readStringInput(scanner, "Enter new category title: ");
    sendPutRequest("/" + categoryId, new CreateCategoryRequest(title), "Category updated successfully.");
  }

  private static void listProducts(Scanner scanner) throws Exception {
    int categoryId = readIntInput(scanner, "Enter category ID to list products: ");
    executeGetRequest("/categories/" + categoryId + "/products", "Products:");
  }

  private static void addProductToCategory(Scanner scanner) throws Exception {
    int categoryId = readIntInput(scanner, "Enter category ID to add product: ");
    String title = readStringInput(scanner, "Enter product title: ");
    double price = readDoubleInput(scanner, "Enter product price: ");
    sendPostRequest("/categories/" + categoryId + "/products", new ProductRequest(title, price, categoryId),
        "Product added successfully.");
  }

  private static void executeGetRequest(String path, String message) throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + path))
        .header("Accept", "application/json")
        .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() == 200) {
      Type listType = new TypeToken<List<?>>() {
      }.getType();
      List<?> items = gson.fromJson(response.body(), listType);
      if (!items.isEmpty()) {
        System.out.println(message);
        items.forEach(item -> System.out.println(item.toString()));
      } else {
        System.out.println("No items found.");
      }
    } else {
      System.out.println("Error fetching data: " + response.statusCode());
    }
  }

  private static void sendPostRequest(String path, Object body, String successMessage) throws Exception {
    String json = gson.toJson(body);
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + path))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() == 200) {
      System.out.println(successMessage);
    } else {
      System.out.println("Error creating item: " + response.statusCode());
    }
  }

  private static void sendPutRequest(String path, Object body, String successMessage) throws Exception {
    String json = gson.toJson(body);
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + path))
        .header("Content-Type", "application/json")
        .PUT(HttpRequest.BodyPublishers.ofString(json))
        .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() == 200) {
      System.out.println(successMessage);
    } else {
      System.out.println("Error updating item: " + response.statusCode());
    }
  }

  private static void sendDeleteRequest(String path, String successMessage) throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + path))
        .DELETE()
        .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() == 200) {
      System.out.println(successMessage);
    } else {
      System.out.println("Error deleting item: " + response.statusCode());
    }
  }

  private static int readIntInput(Scanner scanner, String prompt) {
    System.out.print(prompt);
    final int intValue = scanner.nextInt();
    scanner.nextLine();
    return intValue;
  }

  private static String readStringInput(Scanner scanner, String prompt) {
    System.out.print(prompt);
    return scanner.nextLine();
  }

  private static double readDoubleInput(Scanner scanner, String prompt) {
    System.out.print(prompt);
    final double doubleValue = scanner.nextDouble();
    scanner.nextLine();
    return doubleValue;
  }

  private static HttpClient createHttpClient() {
    try {
      TrustManager[] trustAllCerts = new TrustManager[]{
          new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }

            public X509Certificate[] getAcceptedIssuers() {
              return null;
            }
          }
      };
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      return HttpClient.newBuilder()
          .sslContext(sslContext)
          .build();
    } catch (Exception e) {
      throw new RuntimeException("Failed to create HttpClient", e);
    }
  }
}