package com.brl.oneminutejobs.others;

public class DateConversion {

    public static String organizeDate(String original){

        String tempS = "";

        tempS = original.replace("-","");

        String year = "";
        String month = "";
        String date = "";

        for(int i=0; i<= 3; i++){

            year = year +  tempS.charAt(i);
        }

        for(int i=4; i<= 5; i++){

            month = month +  tempS.charAt(i);
        }

        for(int i=6; i<= 7; i++){

            date = date +  tempS.charAt(i);
        }

        tempS = date+"-"+month+"-"+year;

        return  tempS;

    }
}
