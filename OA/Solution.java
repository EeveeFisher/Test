import java.util.*;
import java.io.*;
import java.lang.*;

public class Solution {


    public int[] CartManager(int[] items, int[] query){
        LinkedList<Integer> cart = new LinkedList<>();

        for(int item : items){
            cart.add(item);
        }

        for(int q : query){
            if(q>0){
                cart.addLast(q);
            }else{
                int target = -q;
                cart.removeFirstOccurrence(target);
            }
        }

        int[] result = new int[cart.size()];
        int i = 0;
        for(int val : cart) {
            result[i++] = val;
        }

        return result;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] items = {5, 1, 2, 2, 4, 6};
        int[] query = {1, -2, -1, -1};

        int[] result = sol.CartManager(items, query);
        System.out.println(Arrays.toString(result));

        int[] items2 = {1, 2, 1, 2, 1};
        int[] query2 = {-1, -1, 3, 4, -3};

        int[] result2 = sol.CartManager(items2, query2);
        System.out.println(Arrays.toString(result2));
    }
}


