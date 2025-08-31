package com.lolcoach.model;

import java.time.LocalDateTime;
import java.util.Map;

public class MatchData {
    private String matchId;
    private LocalDateTime timestamp;
    private String lane;
    private String playerChampion;
    private String enemyChampion;
    private String situation;
    private String outcome;
    private String advice;
    private Map<String, Object> additionalData;
    
    public MatchData() {}
    
    public MatchData(String matchId, LocalDateTime timestamp, String lane, String playerChampion,
                    String enemyChampion, String situation, String outcome, String advice) {
        this.matchId = matchId;
        this.timestamp = timestamp;
        this.lane = lane;
        this.playerChampion = playerChampion;
        this.enemyChampion = enemyChampion;
        this.situation = situation;
        this.outcome = outcome;
        this.advice = advice;
    }
    
    // Getters and Setters
    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getLane() { return lane; }
    public void setLane(String lane) { this.lane = lane; }
    
    public String getPlayerChampion() { return playerChampion; }
    public void setPlayerChampion(String playerChampion) { this.playerChampion = playerChampion; }
    
    public String getEnemyChampion() { return enemyChampion; }
    public void setEnemyChampion(String enemyChampion) { this.enemyChampion = enemyChampion; }
    
    public String getSituation() { return situation; }
    public void setSituation(String situation) { this.situation = situation; }
    
    public String getOutcome() { return outcome; }
    public void setOutcome(String outcome) { this.outcome = outcome; }
    
    public String getAdvice() { return advice; }
    public void setAdvice(String advice) { this.advice = advice; }
    
    public Map<String, Object> getAdditionalData() { return additionalData; }
    public void setAdditionalData(Map<String, Object> additionalData) { this.additionalData = additionalData; }
    
    @Override
    public String toString() {
        return String.format("MatchData{matchId='%s', timestamp=%s, lane='%s', playerChampion='%s', " +
                           "enemyChampion='%s', situation='%s', outcome='%s', advice='%s'}", 
                           matchId, timestamp, lane, playerChampion, enemyChampion, situation, outcome, advice);
    }
}
