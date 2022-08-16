package com.app.ProLesson.dataType;

public class User {

    private int id;
    private String account;
    private String psw;
    private int isAdmin;
    private String JSESSIONID;

    public User(int id, String account, String psw, int isAdmin) {
        this.id = id;
        this.account = account;
        this.psw = psw;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getPsw() {
        return psw;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public String getJSESSIONID() { return JSESSIONID; }

    public void setJSESSIONID(String jsid) { this.JSESSIONID = jsid; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", psw='" + psw + '\'' +
                ", isAdmin=" + isAdmin +
                ", JSESSIONID='" + JSESSIONID + '\'' +
                '}';
    }
}
