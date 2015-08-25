package cn.guolf.guoblog.util;

import java.util.ArrayList;

/**
 * Author：guolf on 8/20/15 10:19
 * Email ：guo@guolingfa.cn
 */
public class LintCode {

    public static void main(String[] args) {
        char[] c = new char[]{'M', 'r', ' ', 'j'};
        String s = c.toString();
        s.replace(" ", "%20");
    }

    /**
     * @param dictionary: an array of strings
     * @return: an arraylist of strings
     */
    static ArrayList<String> longestWords(String[] dictionary) {
        int max = 0;
        int[] ints = new int[dictionary.length];
        ArrayList<String> list = new ArrayList<String>();
        if (dictionary.length == 1) {
            list.add(dictionary[0]);
            return list;
        }
        for (int i = 0; i < dictionary.length; i++) {
            ints[i] = dictionary[i].length();
        }
        max = erfen(ints);
        for (String s : dictionary) {
            if (s.length() == max)
                list.add(s);
        }

        return list;
    }

    private static int erfen(int[] array) {
        int insertIndex;
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            if (array[i - 1] > temp) {
                insertIndex = binarySearch(array, 0, i - 1, temp);
                for (int j = i; j > insertIndex; j--) {
                    array[j] = array[j - 1];
                }
                array[insertIndex] = temp;
            }
        }
        return array[array.length];
    }

    static int binarySearch(int[] array, int l, int u, int t) {
        int curIndex;
        while (l < u) {
            curIndex = (l + u) / 2;
            if (array[curIndex] > t) {
                u = curIndex - 1;
            } else {
                l = curIndex + 1;
            }
        }
        return l;
    }

}
