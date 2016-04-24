import java.util.Iterator;

class Permuter implements Iterable<Integer> {
    private final MultisetPerms perms;

    Permuter(int n) {
        perms = new MultisetPerms(Integer.toString(n));
    }

    @Override
    public Iterator<Integer> iterator() {
        return new PermuteIterator();
    }

    private class PermuteIterator implements Iterator<Integer> {

        private final Iterator<String> rep;

        private PermuteIterator() {
            rep = perms.iterator();
        }

        @Override
        public boolean hasNext() {
            return rep.hasNext();
        }

        @Override
        public Integer next() {
            return Integer.valueOf(rep.next());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
