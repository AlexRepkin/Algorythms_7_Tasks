import java.util.*;

public class huffman_old_too {

    static class Node implements Comparable<Node> {
        char symbol;
        int frequency;
        Node left, right;

        public Node(char symbol, int frequency) {
            this.symbol = symbol;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.frequency, other.frequency);
        }
    }

    private static final Map<Character, String> encodeMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Good day! Please, enter your text:");
        String words = input.nextLine(); // NextLine, так как он игнорирует пробелы.
        Map<Character, Integer> characters = new HashMap<>(); // Словарь для учёта всех символов в тексте.
        int length = words.length();
        // Подсчёт частот символов. При слиянии увеличивается на value
        for (int i = 0; i < length; i++) characters.merge(words.charAt(i), 1, Integer::sum);
        System.out.println("Counting process has been finished. Symbols, that were met in text:\n" + characters);
        /* Напоминание - алгоритм заключаетс в выборке двух минимальных вершин и превращении их в детей новой вершины
        с пометкой частота1 + частота2. После этого дети выкидываются, а родительская частота остаётся.*/

        //PriorityQueue - встроенная в Java функция, создающая приоритетную очередь.
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : characters.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            priorityQueue.offer(node);
        }
        Node root = buildHuffmanTree(priorityQueue);
        generateHuffmanCodes(root, "");
        // Example: Displaying the Huffman codes for each character
        for (Map.Entry<Character, String> entry : encodeMap.entrySet()) {
            System.out.println("Character: " + entry.getKey() + " Huffman Code: " + entry.getValue());
        }

        String encodedText = encode(words);
        System.out.println("Encoded Text: " + encodedText);

        String decodedText = decode(encodedText, root);
        System.out.println("Decoded Text: " + decodedText);
    }

    private static Node buildHuffmanTree(PriorityQueue<Node> priorityQueue) {
        while (priorityQueue.size() > 1) {
            Node i = priorityQueue.poll();
            Node j = priorityQueue.poll();

            Node k = new Node('\0', i.frequency + j.frequency);
            k.left = i;
            k.right = j;

            priorityQueue.offer(k);
        }
        return priorityQueue.poll(); // Return the root of the Huffman tree
    }

    private static void generateHuffmanCodes(Node node, String code) {
        if (node != null) {
            if (node.left == null && node.right == null) {
                encodeMap.put(node.symbol, code);
            }
            generateHuffmanCodes(node.left, code + "0");
            generateHuffmanCodes(node.right, code + "1");
        }
    }

    private static String encode(String text) {
        StringBuilder encodedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            encodedText.append(encodeMap.get(c));
        }
        return encodedText.toString();
    }

    private static String decode(String encodedText, Node root) {
        StringBuilder decodedText = new StringBuilder();
        Node current = root;
        for (char bit : encodedText.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else if (bit == '1') {
                current = current.right;
            }

            if (current.left == null && current.right == null) {
                decodedText.append(current.symbol);
                current = root; // Reset to the root for the next character
            }
        }
        return decodedText.toString();
    }
}
