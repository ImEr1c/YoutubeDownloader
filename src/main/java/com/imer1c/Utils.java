package com.imer1c;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static boolean containsArray(String[] args, String arg)
    {
        for (String s : args)
        {
            if (s.equals(arg))
            {
                return true;
            }
        }

        return false;
    }

    public static String nextInArray(String[] args, String arg)
    {
        int index = indexOf(args, arg);

        if (index == -1)
        {
            throw new IllegalArgumentException("Can't determine next in array as the element before does not exist in the array");
        }

        int nextIndex = index + 1;

        if (nextIndex >= args.length || args[nextIndex].startsWith("--"))
        {
            Main.error("Value for argument " + arg + " has not been set");
            return null;
        }

        return args[nextIndex];
    }

    public static int indexOf(String[] args, String arg)
    {
        for (int i = 0; i < args.length; i++)
        {
            if (args[i].equals(arg))
            {
                return i;
            }
        }

        return -1;
    }

    public static String moveLetterToStart(String s)
    {
        int i = 0;
        char letter = ' ';

        for (char c : s.toCharArray())
        {
            if (Character.isAlphabetic(c))
            {
                letter = c;
                break;
            }

            i++;
        }

        return s.substring(0, i) + letter;
    }

    public static List<String> removeDuplicates(List<String> list)
    {
        List<String> result = new ArrayList<>();

        for (String s : list)
        {
            if (!result.contains(s))
            {
                result.add(s);
            }
        }

        return result;
    }
}
