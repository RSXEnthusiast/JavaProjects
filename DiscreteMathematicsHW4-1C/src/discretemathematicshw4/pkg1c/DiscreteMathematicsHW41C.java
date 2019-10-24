package discretemathematicshw4.pkg1c;

import java.util.Arrays;

/**
 *
 * @author Joel Kalich
 */
public class DiscreteMathematicsHW41C {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[][] a = {{"a0"}, {"a1"}, {"a2"}, {"a3"}, {"a4"}, {"a5"}, {"a6"}, {"a7"}, {"a8"}, {"a9"}, {"a10"}};
        for (int i = 0; i < a.length; i++) {
            System.out.println("a" + i + " = " + Arrays.toString(a[i]));
        }
        int count = 11;
        while (!Arrays.toString(a[10]).equals("[a0]")) {
            a = calcNext(a);
            System.out.println("a" + count + " = " + Arrays.toString(a[10]));
            count++;
        }
    }

    public static String[][] calcNext(String[][] a) {
        String[] temp = new String[a[0].length + a[1].length + a[5].length + a[6].length + a[10].length];
        for (int i = 0; i < a[0].length; i++) {
            temp[i] = a[0][i];
        }
        for (int i = a[0].length; i < a[0].length + a[1].length; i++) {
            temp[i] = a[1][i - a[0].length];
        }
        for (int i = a[0].length + a[1].length; i < a[0].length + a[1].length + a[5].length; i++) {
            temp[i] = a[5][i - (a[0].length + a[1].length)];
        }
        for (int i = a[0].length + a[1].length + a[5].length; i < a[0].length + a[1].length + a[5].length + a[6].length; i++) {
            temp[i] = a[6][i - (a[0].length + a[1].length + a[5].length)];
        }
        for (int i = a[0].length + a[1].length + a[5].length + a[6].length; i < a[0].length + a[1].length + a[5].length + a[6].length + a[10].length; i++) {
            temp[i] = a[10][i - (a[0].length + a[1].length + a[5].length + a[6].length)];
        }
        temp = reduce(temp);
        for (int i = 0; i < a.length - 1; i++) {
            a[i] = a[i + 1];
        }
        a[10] = temp;
        return a;
    }

    private static String[] reduce(String[] temp) {
        String[] reduced = {};
        int a0 = 0;
        int a1 = 0;
        int a2 = 0;
        int a3 = 0;
        int a4 = 0;
        int a5 = 0;
        int a6 = 0;
        int a7 = 0;
        int a8 = 0;
        int a9 = 0;
        int a10 = 0;
        for (String temp1 : temp) {
            switch (temp1) {
                case "a0":
                    a0++;
                    break;
                case "a1":
                    a1++;
                    break;
                case "a2":
                    a2++;
                    break;
                case "a3":
                    a3++;
                    break;
                case "a4":
                    a4++;
                    break;
                case "a5":
                    a5++;
                    break;
                case "a6":
                    a6++;
                    break;
                case "a7":
                    a7++;
                    break;
                case "a8":
                    a8++;
                    break;
                case "a9":
                    a9++;
                    break;
                case "a10":
                    a10++;
                    break;
            }
        }
        if (a0 % 2 != 0) {
            reduced = add(reduced, "a0");
        }
        if (a1 % 2 != 0) {
            reduced = add(reduced, "a1");
        }
        if (a2 % 2 != 0) {
            reduced = add(reduced, "a2");
        }
        if (a3 % 2 != 0) {
            reduced = add(reduced, "a3");
        }
        if (a4 % 2 != 0) {
            reduced = add(reduced, "a4");
        }
        if (a5 % 2 != 0) {
            reduced = add(reduced, "a5");
        }
        if (a6 % 2 != 0) {
            reduced = add(reduced, "a6");
        }
        if (a7 % 2 != 0) {
            reduced = add(reduced, "a7");
        }
        if (a8 % 2 != 0) {
            reduced = add(reduced, "a8");
        }
        if (a9 % 2 != 0) {
            reduced = add(reduced, "a9");
        }
        if (a10 % 2 != 0) {
            reduced = add(reduced, "a10");
        }
        return reduced;
    }

    private static String[] add(String[] a, String value) {
        String[] result = new String[a.length + 1];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i];
        }
        result[a.length] = value;
        return result;
    }

    public static String[] removeByIndex(String[] a, int index) {
        System.out.println("Removing " + a[index] + " from index " + index);
        String[] newArray = new String[a.length - 1];
        for (int i = 0; i < index; i++) {
            newArray[i] = a[i];
        }
        for (int i = index; i < newArray.length; i++) {
            newArray[i] = a[i + 1];
        }
        return newArray;
    }
}
