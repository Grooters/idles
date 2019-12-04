package io.github.grooters.idles.bean;

/**
 * Create by 李林浪 in 2019/6/12
 * Elegant Code...
 */
public class User{

    private String token, name, signature, nickName, gender, number, college, avatarUrl, password;

    public User(String name, String signature, String nickName, String gender,
                String number, String college, String avatarUrl, String password, String token) {
        this.name = name;
        this.signature = signature;
        this.nickName = nickName;
        this.gender = gender;
        this.number = number;
        this.college = college;
        this.avatarUrl = avatarUrl;
        this.password = password;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
