public class OperationService {
      Object lock = new Object();;

      int performLongAndExpensiveOperation(int value){
            synchronized (lock){
                  try {
                        Thread.sleep(2500);
                  } catch (InterruptedException e) {
                        e.printStackTrace();
                  }
                  return value*value;
            }
      }
}
