import javafx.util.Pair;
import java.util.*;

public class HashWithLimitSize {
      ArrayList<Pair<Integer, Integer>> hash = new ArrayList<Pair<Integer, Integer>>();
      int additionNumber;
      int limit;

      public HashWithLimitSize(int limit) {
            this.limit = limit;
            for (int i = 0; i < limit; i++) {
                  Pair<Integer, Integer> pair = new Pair<>(0, 0);
                  hash.add(additionNumber, pair);
                  additionNumber++;
            }
            additionNumber=0;
      }

      public void addPair(int key, int value) {
            Pair<Integer, Integer> pair = new Pair<>(key, value);
            hash.set(additionNumber, pair);
            if (additionNumber < limit - 1) {
                  additionNumber++;
            } else {
                  additionNumber = 0;
            }
      }

      public boolean haveThisKey(int key) {
            for (int i = 0; i < limit; i++) {
                  if (hash.get(i).getKey().equals(key)) {
                        return true;
                  }
            }
            return false;
      }

      public int getValueFromKey(int key) {
            int j = 0;
            for (int i = 0; i < limit; i++) {
                  if (hash.get(i).getKey().equals(key)) {
                        j = i;
                        break;
                  }
            }
            return hash.get(j).getValue();
      }

      public static void main(String[] args) {
            HashWithLimitSize hashWithLimitSize = new HashWithLimitSize(3);
            OperationService operationService = new OperationService();

            Random random = new Random();
            while (true) {
                  Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                              int a = random.nextInt(10);
                              if (!hashWithLimitSize.haveThisKey(a)) {
                                    System.out.println("Обращаемся к бд");
                                    int val = operationService.performLongAndExpensiveOperation(a);
                                    hashWithLimitSize.addPair(a, val);
                                    System.out.println(val);
                              } else {
                                    System.out.println("Значение уже есть в кэше " + hashWithLimitSize.getValueFromKey(a));
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
