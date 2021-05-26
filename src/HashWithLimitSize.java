import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HashWithLimitSize {
      ConcurrentHashMap<Integer, Pair<Integer, Integer>> hash;
      OperationService operationService = new OperationService();
      int additionNumber;
      int limit;

      public HashWithLimitSize(int limit) {
            hash = new ConcurrentHashMap<>();
            this.limit = limit;
            for (int i = 0; i < limit; i++) {
                  Pair<Integer, Integer> pair = new Pair<>(0, 0);
                  hash.put(i, pair);
            }
            additionNumber = 0;
      }

      public void addPair(int key, int value) {
            Pair<Integer, Integer> pair = new Pair<>(key, value);
            if (haveThisKey(key)) {
                  int x = 0;
                  for (int i = 0; i < limit; i++) {
                        if (hash.get(i).getKey().equals(key)) {
                              x = i;
                              System.out.println("i = " + i);
                              break;
                        }
                  }
                  if (additionNumber == x) {
                        hash.put(additionNumber, pair);
                  }
                  if (additionNumber > x) {
                        Pair<Integer, Integer> pair1 = hash.get(additionNumber);
                        addWhenXSmallerThanAN(x, pair1);
                  }
                  if (additionNumber < x) {
                        Pair<Integer, Integer> pair1 = hash.get(x);
                        while (x < limit-1) {
                              Pair<Integer, Integer> pair2 = hash.get(x + 1);
                              hash.put(x+1, pair1);
                              pair1 = pair2;
                              x++;
                        }
                        if(additionNumber!=0){
                              Pair<Integer, Integer> pair2 = hash.get(0);
                              hash.put(0, pair1);
                              addWhenXSmallerThanAN(0, pair2);
                        }
                  }
            } else {
                  hash.put(additionNumber, pair);
            }
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

      public void addWhenXSmallerThanAN(int x, Pair<Integer, Integer> pair1) {
            for (int i = 0; i < additionNumber - x + 1; i++) {
                  Pair<Integer, Integer> pair2 = hash.get(additionNumber + i + 1);
                  hash.put(additionNumber + i, pair1);
                  pair1 = pair2;
            }
      }

      public int getValueFromKey(int key) {
            if(haveThisKey(key)){
                  System.out.println("Значение уже есть к хеше");
                  addPair(key, key*key);
                  for (int i = 0; i < limit; i++) {
                        if(hash.get(i).getKey().equals(key)){
                              return hash.get(i).getValue();
                        }
                  }
            } else {
                  addPair(key, operationService.performLongAndExpensiveOperation(key));
                  for (int i = 0; i < limit; i++) {
                        if(hash.get(i).getKey().equals(key)){
                              return hash.get(i).getValue();
                        }
                  }
            }
            return -1;
      }

      public static void main(String[] args) {
            HashWithLimitSize hashWithLimitSize = new HashWithLimitSize(3);
            Random random = new Random();
            while (true) {
                  Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                              int a = random.nextInt(10);
                              System.out.println("Обращаемся для получения данных для ключа " + a);
                              System.out.println(hashWithLimitSize.getValueFromKey(a));
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
