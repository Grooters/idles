package io.github.grooters.idles.bean;

import com.google.gson.Gson;

/**
 * Create by 李林浪 in 2019/6/12
 * Elegant Code...
 */
public class User{

    private String name, signature, gender, number, college, avatarUrl, password, email, phone, token;

    private int code;

    private String desc;

    // number为key
    public User(String name, String signature, String gender, String number,String token,
                String college, String avatarUrl, String password, String email, String phone, int code) {
        this.name = name;
        this.signature = signature;
        this.gender = gender;
        this.number = number;
        this.college = college;
        this.avatarUrl = avatarUrl;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.code = code;
        this.token = token;
    }

    public static void main(String...args){

        Gson gson = new Gson();

        User user = new User("1","2","1","2","1","2","212","1","2","3",1);

        System.out.println(gson.toJson(user));

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}
