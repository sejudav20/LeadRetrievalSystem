package com.example.conferencepro;

import java.util.ArrayList;

public class Encrypter {



    public String decrypt(String string,int id){
        ArrayList<Integer> encryptedParts=storeASCII(string);
        ArrayList<Integer> stringParts=new ArrayList<>();

        for(int value:encryptedParts){
            for(int i=id;i>0;i--){

                value--;
                if(value==Character.MIN_VALUE-1){
                    value=(int)Character.MAX_VALUE;


                }


            }
            stringParts.add(value);


        }


        encryptedParts.clear();

        String newString="";
        for(int v:stringParts){
            char c=(char)v;

            newString+=c;

        }

        stringParts.clear();
        return newString;




    }

    public String encrypt(String string, int id) {

        ArrayList<Integer> stringParts=storeASCII(string);
        ArrayList<Integer> encryptedParts=new ArrayList<>();

        for(int value:stringParts){
            for(int i=0;i<id;i++){

                value++;
                if(value==Character.MAX_VALUE+1){
                    value=(int)Character.MIN_VALUE;


                }


            }
            encryptedParts.add(value);


        }


        stringParts.clear();

        String newString="";
        for(int v:encryptedParts){
            char c=(char)v;

            newString+=c;

        }
        return newString;

    }

    //gets AscII code for a char
    private int getCode(char c) {

        int value = c;
        return value;

    }


    //stores all ascii values in a String

    private ArrayList<Integer> storeASCII(String s){

        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<s.length(); i++){
            list.add(getCode(s.charAt(i)));


        }
        return list;
    }


}