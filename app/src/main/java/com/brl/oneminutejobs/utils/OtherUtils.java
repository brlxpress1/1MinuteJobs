package com.brl.oneminutejobs.utils;

import com.brl.oneminutejobs.R;

public class OtherUtils {


    //--
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

    //--
    public static int putImage(char value){

        int returnValue = 0;

        if(value == 'a'){


            returnValue = R.drawable._a;

        }else if(value == 'b'){


            returnValue = R.drawable._b;

        }
        else if(value == 'c'){


            returnValue = R.drawable._c;

        }
        else if(value == 'd'){


            returnValue = R.drawable._d;

        }
        else if(value == 'e'){


            returnValue = R.drawable._e;

        }
        else if(value == 'f'){


            returnValue = R.drawable._f;

        }
        else if(value == 'g'){


            returnValue = R.drawable._g;

        }
        else if(value == 'h'){


            returnValue = R.drawable._h;

        }
        else if(value == 'i'){


            returnValue = R.drawable._i;

        }
        else if(value == 'j'){


            returnValue = R.drawable._j;

        }
        else if(value == 'k'){


            returnValue = R.drawable._k;

        }
        else if(value == 'l'){


            returnValue = R.drawable._l;

        }
        else if(value == 'm'){


            returnValue = R.drawable._m;

        }
        else if(value == 'n'){


            returnValue = R.drawable._n;

        }
        else if(value == 'o'){


            returnValue = R.drawable._o;

        }
        else if(value == 'p'){


            returnValue = R.drawable._p;

        }
        else if(value == 'q'){


            returnValue = R.drawable._q;

        }
        else if(value == 'r'){


            returnValue = R.drawable._r;

        }
        else if(value == 's'){


            returnValue = R.drawable._s;

        }
        else if(value == 't'){


            returnValue = R.drawable._t;

        }
        else if(value == 'u'){


            returnValue = R.drawable._u;

        }
        else if(value == 'v'){


            returnValue = R.drawable._v;

        }
        else if(value == 'w'){


            returnValue = R.drawable._w;

        }
        else if(value == 'x'){


            returnValue = R.drawable._x;

        }
        else if(value == 'y'){


            returnValue = R.drawable._y;

        }
        else if(value == 'z'){


            returnValue = R.drawable._z;

        }
        else {


            returnValue = R.drawable._a;

        }

        return returnValue;
    }
}
