import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class HashCosplay {
      ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
      OperationService operationService = new OperationService();


      public void addPair(int key, int value){
            concurrentHashMap.put(key, value);
      }

      public int getValueFromKey(int key){
            if(haveThisKey(key)) {
                  System.out.println("Значение уже есть в хеше");
                  return concurrentHashMap.get(key);
            } else {
                  concurrentHashMap.put(key, key*key);
                  return operationService.performLongAndExpensiveOperation(key);
            }
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
            while(true){
                  Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                              int a = random.nextInt(10);
                              System.out.println("Обращаемся для получения данных для ключа " + a);
                              System.out.println(hashCosplay.getValueFromKey(a));
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
