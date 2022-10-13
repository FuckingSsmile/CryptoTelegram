//package com.cryptotelegram;
//
//import java.util.ArrayList;
//import java.util.List;
//
public class Test {
//
    public static void main(String[] args) {
//
//        float i = 0x0123;
//        s1.add("a");
//        s1.add("b");
//        s1.add(1, "c");
//        System.out.println(s1);
//        List s2 = new ArrayList(  s1.subList(1, 2) );
//        System.out.println(s2);
//        s1.addAll(s2);
//        System.out.println(s1);


        String text = "41";
        double v = 0;

        try{

             v = Double.parseDouble(text);
        }catch (NumberFormatException e){
            v = 10;
        }


        System.out.println(v);

    }
}