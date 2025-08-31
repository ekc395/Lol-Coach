package com.lolcoach.model;

import java.util.List;
import java.util.Map;

public class Scenario {
    private String lane;
    private int time;
    private String playerChampion;
    private int playerLevel;
    private String enemyChampion;
    private int enemyLevel;
    private String situation;
    private Map<String, Object> additionalData;
    
    public Scenario() {}
    
    public Scenario(String lane, int time, String playerChampion, int playerLevel, 
                   String enemyChampion, int enemyLevel, String situation) {
        this.lane = lane;
        this.time = time;
        this.playerChampion = playerChampion;
        this.playerLevel = playerLevel;
        this.enemyChampion = enemyChampion;
        this.enemyLevel = enemyLevel;
        this.situation = situation;
    }
    
    // Getters and Setters
    public String getLane() { return lane; }
    public void setLane(String lane) { this.lane = lane; }
    
    public int getTime() { return time; }
    public void setTime(int time) { this.time = time; }
    
    public String getPlayerChampion() { return playerChampion; }
    public void setPlayerChampion(String playerChampion) { this.playerChampion = playerChampion; }
    
    public int getPlayerLevel() { return playerLevel; }
    public void setPlayerLevel(int playerLevel) { this.playerLevel = playerLevel; }
    
    public String getEnemyChampion() { return enemyChampion; }
    public void setEnemyChampion(String enemyChampion) { this.enemyChampion = enemyChampion; }
    
    public int getEnemyLevel() { return enemyLevel; }
    public void setEnemyLevel(int enemyLevel) { this.enemyLevel = enemyLevel; }
    
    public String getSituation() { return situation; }
    public void setSituation(String situation) { this.situation = situation; }
    
    public Map<String, Object> getAdditionalData() { return additionalData; }
    public void setAdditionalData(Map<String, Object> additionalData) { this.additionalData = additionalData; }
    
    @Override
    public String toString() {
        return String.format("Scenario{lane='%s', time=%d, playerChampion='%s', playerLevel=%d, " +
                           "enemyChampion='%s', enemyLevel=%d, situation='%s'}", 
                           lane, time, playerChampion, playerLevel, enemyChampion, enemyLevel, situation);
    }
}
