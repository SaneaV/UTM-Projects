import chain.Handler;
import chain.IssueHandler;
import chain.ReservationHandler;
import chain.StockCheckHandler;
import command.AddProductCommand;
import command.Command;
import command.CommandManager;
import command.RemoveProductCommand;
import model.Product;
import model.Request;
import observer.Observer;
import observer.StockObserver;
import observer.StockSubject;

public class WarehouseManagementSystem {

  public static void main(String[] args) {
    // Создание товара
    Product laptopHp = new Product("Laptop HP", 10);

    // Цепочка обязанностей
    Handler stockCheckHandler = new StockCheckHandler();
    Handler reservationHandler = new ReservationHandler();
    Handler issueHandler = new IssueHandler();

    stockCheckHandler.setNextHandler(reservationHandler);
    reservationHandler.setNextHandler(issueHandler);

    // Создание запроса на выдачу товара
    Request requestHp = new Request(laptopHp, 3);
    stockCheckHandler.handleRequest(requestHp);

    // Паттерн Команда
    CommandManager commandManager = new CommandManager();
    commandManager.executeCommand(new AddProductCommand(laptopHp, 5));
    commandManager.executeCommand(new RemoveProductCommand(laptopHp, 8));

    // Паттерн Наблюдатель
    StockSubject stockSubject = new StockSubject(laptopHp);
    Observer observer = new StockObserver(laptopHp.getName());
    stockSubject.addObserver(observer);

    // Проверка уровня запасов
    stockSubject.checkStock();

    // Дополнительное списание для теста уведомлений
    commandManager.executeCommand(new RemoveProductCommand(laptopHp, 8));
    stockSubject.checkStock();

    final Request request = new Request(laptopHp, 10);
    stockCheckHandler.handleRequest(request);

//    ВТОРОЙ ТЕСТ
    System.out.println();
    Product laptopLenovo = new Product("Laptop Lenovo", 100);

    Request requestLenovo = new Request(laptopLenovo, 1);
    stockCheckHandler.handleRequest(requestLenovo);

    commandManager.executeCommand(new AddProductCommand(laptopLenovo, 5));
    commandManager.undoLastCommand();
    commandManager.executeCommand(new RemoveProductCommand(laptopLenovo, 8));
    commandManager.undoLastCommand();

    stockSubject = new StockSubject(laptopLenovo);
    observer = new StockObserver(laptopLenovo.getName());
    stockSubject.addObserver(observer);

    stockSubject.checkStock();
  }
}