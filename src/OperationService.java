public class OperationService {
      Object lock = new Object();
      ;

      int performLongAndExpensiveOperation(int value) {
            System.out.println("Обращаемся к бд...");
            try {
                  Thread.sleep(3000);
            } catch (InterruptedException e) {
                  e.printStackTrace();
            }
            return value * value;
      }
}
