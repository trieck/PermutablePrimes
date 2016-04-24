import java.util.ArrayList;
import java.util.List;

class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.printf("usage: %s limit%n", Main.class.getSimpleName());
            System.exit(1);
        }

        int limit = Integer.valueOf(args[0]);

        List<Integer> primes = primes(limit);

        for (Integer p : primes) {
            Permuter permuter = new Permuter(p);
            boolean valid = true;
            for (int u : permuter) {
                if (!isprime(primes, u)) {
                    valid = false;
                    break;
                }
            }

            if (valid) {    // all permutations are prime
                System.out.println(p);
            }
        }
    }

    private static boolean isprime(List<Integer> primes, int u) {
        for (Integer p : primes) {
            if (u == p)
                return true;
        }

        return false;
    }

    private static List<Integer> primes(int limit) {
        final int numPrimes = ubound(limit);
        final int sqrtLimit = (int) Math.sqrt(limit);

        List<Integer> primes = new ArrayList<>(numPrimes);

        boolean[] isComposite = new boolean[limit];

        for (int i = 2; i <= sqrtLimit; i++) {
            if (!isComposite[i]) {
                primes.add(i);
                for (int j = i * i; j < limit; j += i)
                    isComposite[j] = true;
            }
        }

        for (int i = sqrtLimit + 1; i < limit; i++)
            if (!isComposite[i])
                primes.add(i);

        return primes;
    }

    private static int ubound(int max) {
        return max > 1 ? (int) (1.25506 * max / Math.log((double) max)) : 0;
    }
}
