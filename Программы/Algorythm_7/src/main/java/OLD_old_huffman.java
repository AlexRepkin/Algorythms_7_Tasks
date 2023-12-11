//import java.util.*;
//
//public class huffman {
//
//    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
//        System.out.println("Good day! Please, enter your text:");
//        String words = input.nextLine(); // Получен текст. Использован nextLine(), так как он игнорирует пробелы.
//        Map<Character, Integer> characters = new HashMap<>(); // Словарь для учёта всех символов в тексте.
//        int length = words.length();
//        // Подсчёт частот символов. При слиянии увеличивается на value
//        for (int i = 0; i < length; i++) characters.merge(words.charAt(i), 1, Integer::sum);
//        System.out.println("Counting process has been finished. Symbols, that were met in text:\n" + characters);
//        /* Напоминание - алгоритм заключаетс в выборке двух минимальных вершин и превращении их в детей новой вершины
//        с пометкой частота1 + частота2. После этого дети выкидываются, а родительская частота остаётся.*/
//
//        // Постройка двоичного Дерева Хаффмана
//        PriorityQueue<Map.Entry<Character, Integer>> priorityQueue = new PriorityQueue<>(
//                Comparator.comparingInt(Map.Entry::getValue)
//        );
//
//        priorityQueue.addAll(characters.entrySet());
//
//        while (priorityQueue.size() > 1) {
//            Map.Entry<Character, Integer> entry1 = priorityQueue.poll();
//            Map.Entry<Character, Integer> entry2 = priorityQueue.poll();
//
//            int mergedFrequency = entry1.getValue() + entry2.getValue();
//            // Create a new node with a placeholder character
//            Map.Entry<Character, Integer> mergedEntry = new AbstractMap.SimpleEntry<>('\0', mergedFrequency);
//
//            priorityQueue.offer(mergedEntry);
//        }
//
//        Map.Entry<Character, Integer> root = priorityQueue.poll();
//        Map<Character, String> huffmanCodes = generateHuffmanCodes(root);
//
//        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
//    }
//
//    private static Map<Character, String> generateHuffmanCodes(Map.Entry<Character, Integer> root) {
//        Map<Character, String> huffmanCodes = new HashMap<>();
//        generateHuffmanCode(root, "", huffmanCodes);
//        return huffmanCodes;
//    }
//
//    private static void generateHuffmanCode(Map.Entry<Character, Integer> entry, String code,
//                                            Map<Character, String> huffmanCodes) {
//        char character = entry.getKey();
//        if (character != '\0') {
//            huffmanCodes.put(character, code);
//            return;
//        }
//
//        generateHuffmanCode(new AbstractMap.SimpleEntry<>('\0', entry.getValue()), code + "0", huffmanCodes);
//        generateHuffmanCode(new AbstractMap.SimpleEntry<>('\0', entry.getValue()), code + "1", huffmanCodes);
//    }
//}