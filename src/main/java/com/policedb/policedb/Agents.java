package com.policedb.policedb;

public class Agents {

    int agent_id;

    String username, password, full_name, boss_name;

    public Agents(int agent_id, String username, String password, String full_name, String boss_name) {
        this.agent_id = agent_id;
        this.username = username;
        this.password = password;
        this.full_name = full_name;
        this.boss_name = boss_name;
    }

    public void setId(int agent_id) {
        this.agent_id = agent_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setBoss_name(String boss_name) {
        this.boss_name = boss_name;
    }


    public int getAgent_id() {
        return agent_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getBoss_name() {
        return boss_name;
    }

}
