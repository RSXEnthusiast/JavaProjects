package discretemathematicshw5.pkg3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Joel Kalich
 */
public class DiscreteMathematicsHW53 {

    static List<String> perms = new ArrayList<String>();

    public static void main(String[] args) {
    	//Taking user string
        System.out.print("Enter a string: ");
        Scanner scanner = new Scanner(System.in);
        //converting input to upper case
        String input = scanner.next().toUpperCase();
        
        System.out.println("\nCalculating permutations of " + input + "...");
        input = alphabetize(input);
        permutation(input);
        
        //Printing permutations
        System.out.println("\nPermutations are:");
        System.out.println(perms);
        
        System.out.println("\nNumber of non-duplicate permutations: " + perms.size());
        
        //Printing middle permutation
        System.out.println("\nMiddle String 1: " + perms.get(perms.size() / 2) + " at index " + perms.size() / 2);
        //Printing other middle permutation, if there are two
        if (perms.size() % 2 == 0) {
            System.out.println("Middle String 2: " + perms.get(perms.size() / 2 - 1) + " at index " + (perms.size() / 2 - 1));
        }
        
        //taking user input for search
        System.out.println("\nTo see what numberical position a String has when the permutations are ordered lexiographically,");
        System.out.print("enter a String: ");
        //converting to upper case
        input = scanner.next().toUpperCase();
        
        //Printing the index of user input
        System.out.println("\n\"" + input + "\" is number " + (perms.indexOf(input) + 1) + " when the permutations are in lexiographical order.");
        scanner.close();
    }

    //To see if the array contains a string
    public static boolean contains(String[] s, String string) {
        for (String item : s) {
            if (item.equals(string)) {
                return true;
            }
        }
        return false;
    }

    //Alphabetizes a string;
    public static String alphabetize(String s) {
        return Stream.of(s.split("")).sorted().collect(Collectors.joining());
    }

    //Method to set up the recursive call
    private static void permutation(String s) {
        permutation("", s);
    }

    //Creates an array of all the permutations
    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) {
            if (!perms.contains(prefix)) {
                perms.add(prefix);
            }
        } else {
            for (int i = 0; i < n; i++) {
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
            }
        }
    }
}