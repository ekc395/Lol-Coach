package com.lolcoach;

import com.lolcoach.model.Scenario;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());
    
    // Regex patterns for extracting game information
    private static final Pattern LANE_PATTERN = Pattern.compile("\\b(top|mid|bottom|bot|jungle|jg)\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern TIME_PATTERN = Pattern.compile("\\b(\\d+)\\s*(?:minutes?|mins?)\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern CHAMPION_PATTERN = Pattern.compile("\\b(I'm|I am|playing|champion)\\s+([A-Za-z]+)\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern ENEMY_CHAMPION_PATTERN = Pattern.compile("\\b(enemy|opponent|vs|against)\\s+([A-Za-z]+)\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern LEVEL_PATTERN = Pattern.compile("\\blevel\\s+(\\d+)\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern SITUATION_PATTERN = Pattern.compile("\\b(ahead|behind|winning|losing|struggling|dominating|even)\\b", Pattern.CASE_INSENSITIVE);
    
    public Parser() {
        LOGGER.info("Parser initialized - ready for scenario parsing");
    }
    
    /**
     * Converts spoken text into structured scenario data.
     * @param text The recognized speech text
     * @return Structured scenario data
     */
    public Scenario parseScenario(String text) {
        LOGGER.info("Parsing scenario from text: " + text);
        
        try {
            Scenario scenario = new Scenario();
            
            // Extract lane information
            String lane = extractLane(text);
            scenario.setLane(lane);
            
            // Extract time information
            int time = extractTime(text);
            scenario.setTime(time);
            
            // Extract player champion
            String playerChampion = extractPlayerChampion(text);
            scenario.setPlayerChampion(playerChampion);
            
            // Extract player level
            int playerLevel = extractPlayerLevel(text);
            scenario.setPlayerLevel(playerLevel);
            
            // Extract enemy champion
            String enemyChampion = extractEnemyChampion(text);
            scenario.setEnemyChampion(enemyChampion);
            
            // Extract enemy level
            int enemyLevel = extractEnemyLevel(text);
            scenario.setEnemyLevel(enemyLevel);
            
            // Extract situation description
            String situation = extractSituation(text);
            scenario.setSituation(situation);
            
            // Add additional parsed data
            Map<String, Object> additionalData = extractAdditionalData(text);
            scenario.setAdditionalData(additionalData);
            
            LOGGER.info("Successfully parsed scenario: " + scenario);
            return scenario;
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error parsing scenario", e);
            return createDefaultScenario();
        }
    }
    
    private String extractLane(String text) {
        Matcher matcher = LANE_PATTERN.matcher(text);
        if (matcher.find()) {
            String lane = matcher.group(1).toLowerCase();
            // Normalize lane names
            if (lane.equals("bot")) lane = "bottom";
            if (lane.equals("jg")) lane = "jungle";
            return lane;
        }
        return "unknown";
    }
    
    private int extractTime(String text) {
        Matcher matcher = TIME_PATTERN.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }
    
    private String extractPlayerChampion(String text) {
        Matcher matcher = CHAMPION_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "unknown";
    }
    
    private int extractPlayerLevel(String text) {
        Matcher matcher = LEVEL_PATTERN.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 1; // Default level
    }
    
    private String extractEnemyChampion(String text) {
        Matcher matcher = ENEMY_CHAMPION_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "unknown";
    }
    
    private int extractEnemyLevel(String text) {
        // Look for enemy level specifically
        String[] sentences = text.split("\\.");
        for (String sentence : sentences) {
            if (sentence.toLowerCase().contains("enemy") || 
                sentence.toLowerCase().contains("darius") ||
                sentence.toLowerCase().contains("opponent")) {
                Matcher matcher = LEVEL_PATTERN.matcher(sentence);
                if (matcher.find()) {
                    return Integer.parseInt(matcher.group(1));
                }
            }
        }
        return 1; // Default level
    }
    
    private String extractSituation(String text) {
        Matcher matcher = SITUATION_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group(1).toLowerCase();
        }
        return "neutral";
    }
    
    private Map<String, Object> extractAdditionalData(String text) {
        Map<String, Object> data = new HashMap<>();
        
        // Extract any additional context
        if (text.toLowerCase().contains("turret")) {
            data.put("nearTurret", true);
        }
        if (text.toLowerCase().contains("jungler")) {
            data.put("junglerNearby", true);
        }
        if (text.toLowerCase().contains("minion")) {
            data.put("minionWave", true);
        }
        
        return data;
    }
    
    private Scenario createDefaultScenario() {
        LOGGER.warning("Creating default scenario due to parsing error");
        return new Scenario("unknown", 0, "unknown", 1, "unknown", 1, "neutral");
    }
}
