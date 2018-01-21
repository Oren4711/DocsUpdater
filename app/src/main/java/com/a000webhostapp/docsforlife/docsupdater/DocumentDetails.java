package com.a000webhostapp.docsforlife.docsupdater;



public class DocumentDetails {

    private String first_name;
    private String last_name;
    private String id_number;
    private String date_of_issue;
    private String doc_name;

    public DocumentDetails(String first_name,String last_name,String id_number,String date_of_issue,String doc_name){
        this.first_name = first_name;
        this.last_name = last_name;
        this.id_number = id_number;
        this.date_of_issue = date_of_issue;
        this.doc_name = doc_name;

    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getId_number() {
        return id_number;
    }

    public String getDate_of_issue() {
        return date_of_issue;
    }

    public String getDoc_name() {
        return doc_name;
    }
}
