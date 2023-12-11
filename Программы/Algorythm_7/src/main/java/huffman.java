import java.util.*;

// Класс, представляющий узел в двоичном дереве Хаффмана
class node {
    char data;          // Сам символ.
    int frequency;      // Частота символа (Сколькораз он встречается в тексте).
    node first, second;   // Дерево двоичное, поэтому только два потомка.
    // Создание нового узла по запрошенным значениям.
    node(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        this.first = this.second = null;
    }
}


public class huffman {
    static Map<Character, String> huffmanCodes = new HashMap<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Добрый день! Введите, пожалуйста, требуемый для зашифровки текст:");
        String words = input.nextLine(); // next работал неправильно, до пробела, пришлось использовать nextLine.
        Map<Character, Integer> characters = new HashMap<>(); // Словарь всех символов и их количества в тексте.
        int length = words.length();
        // Счёт, сколько раз символ встречается в тексте. merge увеличивает на value (=1), благодаря операции sum.
        // Integer::sum - лямбда-выражение.
        for (int i = 0; i < length; i++) characters.merge(words.charAt(i), 1, Integer::sum);
        List<node> nodes = transformation(characters); // Распределение двоичных значений между символами.
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.println("Символ: " + entry.getKey() + ", его двоичный код: " + entry.getValue());
        }
        System.out.println("Зашифрованный текст:");
        StringBuilder binary_text = new StringBuilder();
        for (int i = 0; i < words.length(); i++) {
            binary_text.append(huffmanCodes.get(words.charAt(i)));
        }
        System.out.println(binary_text);
        System.out.println("Расшифрованный текст:");
        StringBuilder back_to_normal_text = new StringBuilder();
        node current = nodes.get(0);
        for (int i = 0; i < binary_text.length(); i++) {
            if (binary_text.charAt(i) == '0') { // 0 - Справа, 1 - Слева.
                current = current.first;
            } else {
                current = current.second;
            }
            if (current.first == null && current.second == null) {
                // Если дошло до последнего узла ветки, то добавляем полученный символ в конец строки.
                back_to_normal_text.append(current.data);
                current = nodes.get(0); // Вернулись в начало дерева.
            }
        }
        System.out.println(back_to_normal_text);
    }

    private static List<node> transformation(Map<Character, Integer> characters) {
        /* В Java есть PriorityQueue - Приоритетная очередь. Ей удобно было воспользоваться,
          чтобы рассортировать узлы по частоте. Очередь заполняется узлами, состоящими из
          частоты, распределённого символа и детей (Сначала оба ребёнка null)*/
        PriorityQueue<node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));
        for (Map.Entry<Character, Integer> entry : characters.entrySet()) {
            priorityQueue.add(new node(entry.getKey(), entry.getValue()));
        }
        // Пока в очереди есть больше 1 узла, детям присваиваются (и убираются из очереди) узлы.
        while (priorityQueue.size() > 1) {
            node left = priorityQueue.poll();
            node right = priorityQueue.poll();
            // Создание нового пустого (\0) узла. Его частота равна сумме частот его детей (Было сказано в видео).
            node internalNode = new node('\0', left.frequency + right.frequency);
            internalNode.first = left;
            internalNode.second = right;
            /* Добавление созданного узла в очередь. Вместо двух детей
               в очереди появился родительский узел, бесконечного цикла нет.
            */
            priorityQueue.add(internalNode);
        }
        List<node> nodes = new ArrayList<>(); // Конечный список узлов - двоичное дерево.
        nodes.add(priorityQueue.poll()); // Оставшийся в очереди узел (корень) двоичного дерева, переходит в список.
        transform_into_bites(nodes.get(0), ""); // Корень получает, при переводе, в двоичный код, пустое значение.
        return nodes;
    }

    private static void transform_into_bites(node root, String bites) {
        // До тех пор, пока у узлов есть дети им присваивается соответствующее положению значение. Справа 0, слева 1.
        if (root != null) {
            if (root.first == null && root.second == null) {
                huffmanCodes.put(root.data, bites);
            }
            transform_into_bites(root.first, bites + "0");
            transform_into_bites(root.second, bites + "1");
        }
    }
}
