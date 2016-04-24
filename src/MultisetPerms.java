import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Generate all permutations of a multi-set represented as a string
 * <p>
 * Based on the "LOOPLESS GENERATION OF MULTISET PERMUTATIONS BY PREFIX SHIFTS"
 * Algorithm by AARON WILLIAMS
 * <p>
 * http://webhome.csc.uvic.ca/~haron/CoolMulti.pdf
 */
class MultisetPerms implements Iterable<String> {
    private final String input;
    private Node head, I, afterI;

    MultisetPerms(String input) {
        this.input = input;
        init();
    }

    private static Character[] rsort(String input) {
        Character[] chars = toChars(input);

        Arrays.sort(chars, new Comparator<Character>() {
            @Override
            public int compare(Character c1, Character c2) {
                if (c1 < c2)
                    return 1;
                if (c1 > c2)
                    return -1;

                return 0;
            }
        });

        return chars;
    }

    private static Character[] toChars(String input) {
        Character[] chars = new Character[input.length()];
        for (int i = 0; i < chars.length; i++)
            chars[i] = input.charAt(i);
        return chars;
    }

    private void init() {
        Node node = head = new Node();

        Character[] a = rsort(input);
        for (int i = 0; i < a.length; i++) {
            node.value = a[i];

            if (i == a.length - 2) {
                I = node;
            } else if (i == a.length - 1) {
                afterI = node;
                break;
            }

            node.next = new Node();
            node = node.next;
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new MultiPermIterator();
    }

    private static class Node {
        private Character value;
        private Node next;
    }

    private class MultiPermIterator implements Iterator<String> {

        Node h, i, a;

        private MultiPermIterator() {
            h = head;
            i = I;
            a = afterI;
        }

        @Override
        public boolean hasNext() {
            return h != null;
        }

        @Override
        public String next() {
            String v = value();
            moveNext();
            return v;
        }

        private void moveNext() {
            Node k, beforeK;

            if (a.next != null || a.value < h.value) {
                if (a.next != null && i.value >= a.next.value) {
                    beforeK = a;
                } else {
                    beforeK = i;
                }
                k = beforeK.next;
                beforeK.next = k.next;
                k.next = h;
                if (k.value < h.value) {
                    i = k;
                }
                a = i.next;
                h = k;
            } else {
                h = null;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private String value() {
            StringBuilder builder = new StringBuilder();

            Node n = h;
            while (n != null) {
                builder.append(n.value);
                n = n.next;
            }

            return builder.toString();
        }
    }
}
