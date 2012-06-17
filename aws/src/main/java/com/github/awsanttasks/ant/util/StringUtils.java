package com.github.awsanttasks.ant.util;

/**
 * Copy/paste from apache commons since i dont want to depend on yet another package
 * http://kickjava.com/src/org/apache/commons/lang/StringUtils.java.htm
 */
public final class StringUtils
{
    private StringUtils(){}

    public static boolean isBlank(String   str)
    {
        int strLen;
        if (str == null || (strLen = str.length()) == 0)
        {
            return true;
        }

        for (int i = 0; i < strLen; i++)
        {
            if ((!Character.isWhitespace(str.charAt(i))))
            {
                return false;
            }
        }
        return true;
    }


    public static boolean isNotBlank(String   str)
    {
        int strLen;
        if (str == null || (strLen = str.length()) == 0)
        {
            return false;
        }

        for (int i = 0; i < strLen; i++)
        {
            if((!Character.isWhitespace(str.charAt(i))))
            {
                return true;
            }
        }
        return false;
    }

}
