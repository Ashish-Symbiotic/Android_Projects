package com.example.naveen.assatemanagement.databaseConnection;


public class AssateHolder {
    private String data;
    private String title;
    private String status;

    public AssateHolder(){}

    public AssateHolder(String title,String data)
    {
        this.data=data;
        this.title=title;
    }

    public AssateHolder(String title,String data,String status)
    {
        this.data=data;
        this.title=title;
        this.status=status;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
