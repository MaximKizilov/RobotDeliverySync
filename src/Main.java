import java.util.*;

public class Main {
    public static final int routes = 1000;
    public static final int length = 100;
    public static final String letters = "RLRFR";
        public static final Map<Integer, Integer> sizeToFreq = new TreeMap<>();

    public static void main(String[] args) {

        for (int i = 0; i < routes; i++) {
            new Thread(()-> {
                char[] direction = generateRoute(letters, length).toCharArray();
                Integer key = 0;
                for (int j = 0; j < length; j++) {
                    if (direction[j] == 'R') {
                        key++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(key)) {
                        sizeToFreq.compute(key, (k, value) -> value + 1);
                    } else
                        sizeToFreq.put(key, 1);
                }
            }).start();
        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(sizeToFreq.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        Iterator<Map.Entry<Integer, Integer>> iter = list.iterator();

        if (iter.hasNext()) {
            Map.Entry<Integer, Integer> mostFrequent = iter.next();
            System.out.println("Самое частое количество повторений " + mostFrequent.getKey() + " (встретилось " + mostFrequent.getValue() + " раз)");

            if (iter.hasNext()) {
                System.out.println("Другие размеры:");
                iter.forEachRemaining(entry -> System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)"));
            }
        }
    }

        public static String generateRoute (String letters,int length){
            Random random = new Random();
            StringBuilder route = new StringBuilder();
            for (int i = 0; i < length; i++) {
                route.append(letters.charAt(random.nextInt(letters.length())));
            }
            return route.toString();
        }
    }
