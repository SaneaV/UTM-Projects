import domain.Vertex;
import service.GraphService;
import service.impl.GraphServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public static final GraphService graphService = new GraphServiceImpl();

    public static void main(String[] args) {

        boolean isActive = true;
        final List<Vertex> graphs = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\n\nМеню программы:\n");
            System.out.println("1. Добавить новую вершину");
            System.out.println("2. Добавить новую дугу");
            System.out.println("3. Удалить вершину");
            System.out.println("4. Удалить дугу");
            System.out.println("5. Список смежности");
            System.out.println("6. Матрица смежности");
            System.out.println("7. Матрица инцидентности");
            System.out.println("8. Выйти из программы\n");

            System.out.print("Сделайте ваш выбор: ");
            int choice = (scanner.nextInt());

            switch (choice) {
                case 1 -> {
                    System.out.print("Введите номер вершины: ");
                    int numberName = scanner.nextInt();
                    clearScreen();
                    if (graphService.addVertex(numberName, graphs)) {
                        System.out.println("Операция выполнена\n\n");
                        graphService.sortGraph(graphs);
                    } else {
                        System.out.println("Операция не выполнена\n\n");
                    }
                }
                case 2 -> {
                    System.out.print("Введите номер первой вершины: ");
                    int firstVertexNumber = scanner.nextInt();
                    System.out.print("Введите номер второй вершины: ");
                    int secondVertexNumber = scanner.nextInt();
                    clearScreen();
                    if (graphService.addArc(firstVertexNumber, secondVertexNumber, graphs)) {
                        System.out.println("Операция выполнена\n\n");
                        graphService.sortArcs(graphs);
                    } else {
                        System.out.println("Операция не выполнена\n\n");
                    }
                }
                case 3 -> {
                    System.out.print("Введите номер вершины: ");
                    int numberName = scanner.nextInt();
                    clearScreen();
                    if (graphService.deleteVertex(numberName, graphs)
                    ) {
                        System.out.println("Операция выполнена\n\n");
                    } else {
                        System.out.println("Операция не выполнена\n\n");
                    }
                }
                case 4 -> {
                    System.out.print("Введите номер первой вершины: ");
                    int firstVertexNumber = scanner.nextInt();
                    System.out.print("Введите номер второй вершины: ");
                    int secondVertexNumber = scanner.nextInt();
                    clearScreen();
                    if (graphService.deleteArc(firstVertexNumber, secondVertexNumber, graphs)
                    ) {
                        System.out.println("Операция выполнена\n\n");
                    } else {
                        System.out.println("Операция не выполнена\n\n");
                    }
                }
                case 5 -> {
                    clearScreen();
                    graphService.adjacencyList(graphs);
                }
                case 6 -> {
                    clearScreen();
                    graphService.adjacencyMatrix(graphs);
                }
                case 7 -> {
                    clearScreen();
                    graphService.incidenceMatrix(graphs);
                }
                case 8 -> {
                    clearScreen();
                    System.out.println("Пока!");
                    isActive = false;
                }
            }
        } while (isActive);
    }

    public static void clearScreen() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }
}
