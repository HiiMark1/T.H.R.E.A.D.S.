import javafx.util.Pair;

import java.util.*;

public class HashWithLimitSizeAndTime {
      ArrayList<Pair<Long, Pair<Integer, Integer>>> hash = new ArrayList<Pair<Long, Pair<Integer, Integer>>>();
      int additionNumber;
      int limit;
      Date date = new Date();

      public HashWithLimitSizeAndTime(int limit) {
            this.limit = limit;
            for (int i = 0; i < limit; i++) {
                  Pair<Integer, Integer> pair = new Pair<>(0, 0);
                  Pair<Long, Pair<Integer, Integer>> pairWithTime = new Pair<>(date.getTime(), pair);
                  hash.add(additionNumber, pairWithTime);
                  additionNumber++;
            }
            additionNumber=0;
      }

      public void addPair(int key, int value) {
            Pair<Integer, Integer> pair = new Pair<>(key, value);
            Pair<Long, Pair<Integer, Integer>> pairWithTime = new Pair<>(date.getTime(), pair);
            hash.set(additionNumber, pairWithTime);
            if (additionNumber < limit - 1) {
                  additionNumber++;
            } else {
                  additionNumber = 0;
            }
      }

      public boolean haveThisKey(int key) {
            for (int i = 0; i < limit; i++) {
                  if ((date.getTime() - hash.get(i).getKey()) < 1000000000 && hash.get(i).getValue().getKey().equals(key)) {
                        return true;
                  }
            }
            return false;
      }

      public int getValueFromKey(int key) {
            int j =0;
            for (int i = 0; i < limit; i++) {
                  if (hash.get(i).getKey().equals(key)) {
                        j=i;
                        break;
                  }
            }
            return hash.get(j).getValue().getValue();
      }

      public static void main(String[] args) {
            HashWithLimitSizeAndTime hashWithLimitSizeAndTime = new HashWithLimitSizeAndTime(3);
            OperationService operationService = new OperationService();

            Random random = new Random();
            while (true) {
                  Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                              int a = random.nextInt(10);
                              if (!hashWithLimitSizeAndTime.haveThisKey(a)) {
                                    System.out.println("Обращаемся к бд");
                                    int val = operationService.performLongAndExpensiveOperation(a);
                                    hashWithLimitSizeAndTime.addPair(a, val);
                                    System.out.println(val);
                              } else {
                                    System.out.println("Значение уже есть в кэше " + hashWithLimitSizeAndTime.getValueFromKey(a));
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
