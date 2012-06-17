package com.github.awsanttasks.ant.util;

public final class Base36
{
    private Base36(){}

    public static String encode(long l)
    {
        StringBuilder ret = new StringBuilder();

        while (l > 0)
        {
            if ((l % 36) < 10) ret.append((char)(((int)'0') + (int)(l % 36)));
            else ret.append((char)(((int)'A') + (int)((l % 36) - 10)));
            l /= 36;
        }

        return ret.toString();
    }

    public static int decode(String s)
    {
        try
        {
            return Integer.parseInt(s, 36);
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }

    public static void main(String[] args)
    {
        System.out.println(Base36.encode(System.currentTimeMillis()));
    }

}
