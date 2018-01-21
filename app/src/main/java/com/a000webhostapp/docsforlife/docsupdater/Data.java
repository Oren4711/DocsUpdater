package com.a000webhostapp.docsforlife.docsupdater;



public class Data {

    private String docname;
    private int image;

    public Data(String docname, int image) {
        this.docname = docname;
        this.image = image;
    }


    public String getDocname(){
        return docname;
    }

    public int getImage(){
        return image;
    }
}
