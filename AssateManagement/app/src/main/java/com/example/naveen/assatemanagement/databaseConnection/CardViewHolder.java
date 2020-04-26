package com.example.naveen.assatemanagement.databaseConnection;

public class CardViewHolder {

    private String AssateID;
    private String AssateName;
    private String BoughtDate;
    private String Status;

    public CardViewHolder(){}

    public CardViewHolder(String AssateID,String AssateName,String BoughtDate,String Status)
    {
        this.AssateID=AssateID;
        this.AssateName=AssateName;
        this.BoughtDate=BoughtDate;
        this.Status=Status;
    }

    public String getAssateID() {
        return AssateID;
    }

    public String getAssateName() {
        return AssateName;
    }

    public String getBoughtDate() {
        return BoughtDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setAssateID(String assateID) {
        AssateID = assateID;
    }

    public void setAssateName(String assateName) {
        AssateName = assateName;
    }

    public void setBoughtDate(String boughtDate) {
        BoughtDate = boughtDate;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
