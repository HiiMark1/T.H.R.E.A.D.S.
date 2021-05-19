import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class HashCosplay {
      ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();

      public void addPair(int key, int value){
            concurrentHashMap.put(key, value);
      }

      public int getValueFromKey(int key){
            int val = concurrentHashMap.get(key);
            return val;
      }

      public boolean haveThisKey(int key){
            return concurrentHashMap.containsKey(key);
      }

      public void clearHashMap(int key){
            concurrentHashMap.clear();
      }
      public static void main(String[] args) {
            Random random = new Random();
            HashCosplay hashCosplay = new HashCosplay();
            OperationService operationService = new OperationService();
            while(true){
                  Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                              int a = random.nextInt(10);
                              if(!hashCosplay.haveThisKey(a)){
                                    System.out.println("Обращаемся к бд");
                                    int val = operationService.performLongAndExpensiveOperation(a);
                                    hashCosplay.addPair(a, val);
                                    System.out.println(val);
                              } else{
                                    System.out.println("Значение уже есть в кэше " + hashCosplay.getValueFromKey(a));
                              }
                        }
                  });
                  thread.start();
                  try {
                        thread.join();
                  } catch (InterruptedException e) {
                        e.printStackTrace();
                  }
            }
      }
}
