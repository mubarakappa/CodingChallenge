package algo;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import algo.bigint.BigInt;
import com.carrotsearch.hppc.IntObjectHashMap;
import com.carrotsearch.hppc.ShortObjectHashMap;

import static java.lang.Math.sqrt;

public class GenerateNumbers {

    static byte ONE = 1;
    static BigInt ZERO_BIG = new BigInt(0);
    static BigInt ONE_BIG = new BigInt(1);

    static BigInt resCmb(int n, short k) {
        if (n == 1 && k == 1) {
            return ONE_BIG;
        }
        if (k > n || n <= 0 || k <= 0) {
            return ZERO_BIG;
        }

        return add(resCmb(n - k, k), resCmb(n - k, (short) (k - ONE)));
    }

    static BigInt add(final BigInt a, final BigInt b) {
        if (a.isZero() && b.isZero()) {
            return ZERO_BIG;
        } else if (a.isZero() && b == ONE_BIG || a == ONE_BIG && b.isZero()) {
            return ONE_BIG;
        }

        return a.copyAdd(b);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("Need one argument: number of blocks to find the combinations");
        }

        final int n = Integer.parseInt(args[0]);

        System.out.println("Processing started...");
        final long startTime = System.nanoTime();
        BigInt numOfCombinations = new BigInt(0);
        for (short i = 2; i <= sqrt(2 * n); i++) {
            numOfCombinations.add(resCmb(n, i));
        }
        System.out.println("Finished. Execution Time: " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime));

        System.out.println("Number of unique combinations summing up to (n=" + n + "): " + numOfCombinations);
    }

}
