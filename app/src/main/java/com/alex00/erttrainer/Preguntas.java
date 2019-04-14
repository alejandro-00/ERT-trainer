package com.alex00.erttrainer;

public class Preguntas {
    private String p,o1,c,url;
    private boolean bool;
    private int no;

    public Preguntas(int id, String q,String op1,String C,boolean bo,String u){
        super();
        no=id;
        p=q;
        bool=bo;
        o1=op1;
        c=C;
        url=u;

    }

    public String getC(){return c;}

    public String getO1() {
        return o1;
    }

    public boolean getBool(){return bool;}

    public String getP() {
        return p;
    }

    public String getUrl(){return url;}

    public int getNo(){return no;}
}
