import java.io.*;
 // Question 1 a i
class iteration {
 
        public static int power(int a, int n)
    {       
        int res = 1L;        
        for (int i = 0; i < n; i++) {
            res = res * a;
        }
        return res;
    }
};

//Question 1 a ii
public class divideAndconquer {

    public static double power(double a, int n) {
        if (n == 0) {
            return 1;
        } else if (n % 2 == 0) {
            double half = power(a, n / 2);
            return half * half;
        } else {
            double half = power(a, (n - 1) / 2);
            return a * half * half;
        }
    }

}

// 1 b
//Naïve Iterative Method: O(n)
//Divide-and-Conquer Method: O(log(n)), due to the recursive division of the problem by 2 until reaching the base case.

//Use Master Theorem
//T(n) = T(n/2) + O(1)

//Case 1: If the recurrence is of the form T(n) = a * T(n/b) + f(n), where f(n) is O(n^c) for some constant c and a < b^c, then the time complexity is O(n^c).

//a = 1 (one subproblem)
//b = 2 (problem size divided by 2)
//f(n) = O(1) (constant time)
//Since a = 1 and b^c = 2^0 = 1, a < b^c does not hold. Therefore, Case 1 does not apply.

//Case 2: If the recurrence is of the form T(n) = a * T(n/b) + f(n), where f(n) is O(n^c * log^k(n)) for some constants c > 0 and k >= 0, and a = b^c, then the time complexity is O(n^c * log^(k+1)(n)).

//a = 1 (one subproblem)
//b = 2 (problem size divided by 2)
//f(n) = O(1) (constant time)
//Since a = 1 and b^c = 2^0 = 1, we don't have a = b^c. Therefore, Case 2 does not apply.

//Case 3: If the recurrence is of the form T(n) = a * T(n/b) + f(n), where f(n) is O(n^c) for some constant c, and a > b^c, then the time complexity is O(f(n)).

//a = 1 (one subproblem)
//b = 2 (problem size divided by 2)
//f(n) = O(1) (constant time)
//Since a > b^c (1 > 2^0), Case 3 applies.

//Therefore, the solution to the divide-and-conquer recurrence T(n) = T(n/2) + O(1) is: T(n) = O(1)

//So, the time complexity of the divide-and-conquer algorithm for computing a^n is O(1), which means it has constant time complexity.

// 1 c
public class Test {
    public static void main(String[] args) {
        double a = 2;  
        int maxN = 1000000000;

        for (int n = 1; n <= maxN; n *= 10) {
            int startTime;
            int endTime;
            
            startTime = System.nanoTime();
            Power.powerIterative(x, n);
            endTime = System.nanoTime();
            long iterativeTime = endTime - startTime;
            
            startTime = System.nanoTime();
            Power.powerDivideAndConquer(x, n);
            endTime = System.nanoTime();
            int divideAndConquerTime = endTime - startTime;

            System.out.println("n = " + n);
            System.out.println("Iterative Time: " + iterativeTime + " ns");
            System.out.println("Divide-and-Conquer Time: " + divideAndConquerTime + " ns");
        }
    }
}




// 2 a
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pair {
    public static List<int[]> find(int[] S, int target) {
        mergeSort(S);
        List<int[]> pairs = new ArrayList<>();

        for (int num : S) {
            int complement = target - num;
            if (binarySearch(S, complement)) {
                pairs.add(new int[]{num, complement});
            }
        }

        return pairs;
    }

    public static void mergeSort(int[] arr) {
        if (arr.length <= 1) {
            return;
        }

        int mid = arr.length / 2;
        int[] left = Arrays.Range(arr, 0, mid);
        int[] right = Arrays.Range(arr, mid, arr.length);

        mergeSort(left);
        mergeSort(right);

        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        while (i < left.length) {
            arr[k++] = left[i++];
        }

        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }

    public static boolean binarySearch(int[] arr, int x) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == x) {
                return true;
            } else if (arr[mid] < x) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }
}


// 2 b

//Use Master Theorem
//T(n) = aT(n/b) + f(n)
// where a ≥ 1 and b > 1

//Case 1: If f(n) = O(n^c) for some c < log_b(a), then T(n) = Θ(n^log_b(a)).
//Case 2: If f(n) = Θ(n^log_b(a)), then T(n) = Θ(n^log_b(a) * log n).
//Case 3: If f(n) = Ω(n^c) for some c > log_b(a), and if a * f(n/b) ≤ k * f(n) for some constant k < 1 and sufficiently large n, then T(n) = Θ(f(n)).

//a = 1 (since we're dividing the problem into two halves with Merge Sort).
//b = 2 (because we're splitting the array into two equal halves).
//f(n) = O(n log^2 n) (the time complexity of sorting the array with Merge Sort and performing binary searches).
//log_b(a) = log_2(1) = 0.
//c = 2 (as f(n) = O(n^2)).

//Case 2: Since c = 2 is equal to log_b(a) = 0, we are in Case 2, the solution to the recurrence is T(n) = Θ(n^log_b(a) * log n) = Θ(n^0 * log n) = Θ(log n).

//time complexity of the algorithm is Θ(log n).
