package io.github.grooters.idles.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regexer {

    public static boolean isEmail(String content){

        if (content == null)
            return false;

        String rule = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";

        Pattern pattern;

        Matcher matcher;

        pattern = Pattern.compile(rule);

        matcher = pattern.matcher(content);

        return matcher.matches() ;
    }

    public static boolean isPhone(String content){

        String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";

        Pattern p = Pattern.compile(regExp);

        Matcher m = p.matcher(content);

        return m.find();
    }

}
