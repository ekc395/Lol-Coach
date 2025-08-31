package com.lolcoach.service;

import com.lolcoach.model.Scenario;
import com.lolcoach.model.MatchData;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

public class TacticsService {
    private static final Logger LOGGER = Logger.getLogger(TacticsService.class.getName());
    
    // Champion-specific advice database
    private final Map<String, Map<String, String>> championAdvice;
    
    public TacticsService() {
        LOGGER.info("TacticsService initialized - ready for tactical analysis");
        this.championAdvice = initializeChampionAdvice();
    }
    
    /**
     * Generates tactical recommendations based on scenario and historical data.
     * @param scenario The parsed game scenario
     * @return Tactical advice as a string
     */
    public String generateAdvice(Scenario scenario) {
        LOGGER.info("Generating advice for scenario: " + scenario);
        
        try {
            StringBuilder advice = new StringBuilder();
            
            // Generate lane-specific advice
            advice.append(generateLaneAdvice(scenario));
            advice.append(" ");
            
            // Generate champion matchup advice
            advice.append(generateChampionAdvice(scenario));
            advice.append(" ");
            
            // Generate situation-specific advice
            advice.append(generateSituationAdvice(scenario));
            advice.append(" ");
            
            // Generate timing advice
            advice.append(generateTimingAdvice(scenario));
            
            String finalAdvice = advice.toString().trim();
            LOGGER.info("Generated advice: " + finalAdvice);
            
            return finalAdvice;
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating advice", e);
            return "Focus on farming safely and avoid unnecessary risks.";
        }
    }
    
    /**
     * Optional function to retrieve similar professional match situations.
     * @param criteria The scenario to match against
     * @return List of relevant match data
     */
    public List<MatchData> fetchProMatchData(Scenario criteria) {
        LOGGER.info("Fetching pro match data for criteria: " + criteria);
        
        // In a real implementation, this would query a database or API
        // For now, return sample data
        List<MatchData> matches = new ArrayList<>();
        
        // Add sample professional match data
        matches.add(new MatchData("PRO001", java.time.LocalDateTime.now(), 
                                 criteria.getLane(), criteria.getPlayerChampion(), 
                                 criteria.getEnemyChampion(), criteria.getSituation(), 
                                 "victory", "Play defensively and scale into late game"));
        
        return matches;
    }
    
    private String generateLaneAdvice(Scenario scenario) {
        switch (scenario.getLane().toLowerCase()) {
            case "top":
                return "In top lane, focus on wave management and teleport usage. ";
            case "mid":
                return "In mid lane, prioritize roaming opportunities and vision control. ";
            case "bottom":
                return "In bottom lane, coordinate with your support for safe farming. ";
            case "jungle":
                return "As jungler, track enemy jungler and secure objectives. ";
            default:
                return "Focus on your lane fundamentals. ";
        }
    }
    
    private String generateChampionAdvice(Scenario scenario) {
        String playerChamp = scenario.getPlayerChampion().toLowerCase();
        String enemyChamp = scenario.getEnemyChampion().toLowerCase();
        
        // Get champion-specific advice
        String playerAdvice = getChampionSpecificAdvice(playerChamp);
        String matchupAdvice = getMatchupAdvice(playerChamp, enemyChamp);
        
        return playerAdvice + " " + matchupAdvice;
    }
    
    private String generateSituationAdvice(Scenario scenario) {
        String situation = scenario.getSituation().toLowerCase();
        
        switch (situation) {
            case "ahead":
                return "You're ahead, maintain pressure and deny farm. ";
            case "behind":
                return "You're behind, play safely and farm under turret. ";
            case "winning":
                return "You're winning, extend your lead and help other lanes. ";
            case "losing":
                return "You're losing, focus on not dying and catching up in farm. ";
            case "struggling":
                return "You're struggling, ask for jungler assistance and play defensively. ";
            case "dominating":
                return "You're dominating, push your advantage and create pressure. ";
            case "even":
                return "The lane is even, focus on small advantages and vision control. ";
            default:
                return "Focus on fundamentals and avoid unnecessary risks. ";
        }
    }
    
    private String generateTimingAdvice(Scenario scenario) {
        int time = scenario.getTime();
        
        if (time < 5) {
            return "Early game: Focus on last hitting and avoiding early trades.";
        } else if (time < 15) {
            return "Mid game: Look for roaming opportunities and objective control.";
        } else if (time < 25) {
            return "Late game: Group with your team and focus on team fights.";
        } else {
            return "End game: Secure objectives and push for victory.";
        }
    }
    
    private String getChampionSpecificAdvice(String champion) {
        Map<String, String> advice = championAdvice.get(champion);
        if (advice != null) {
            return advice.get("general") != null ? advice.get("general") : "";
        }
        return "Focus on your champion's strengths and playstyle. ";
    }
    
    private String getMatchupAdvice(String playerChamp, String enemyChamp) {
        Map<String, String> playerAdvice = championAdvice.get(playerChamp);
        if (playerAdvice != null && playerAdvice.containsKey(enemyChamp)) {
            return playerAdvice.get(enemyChamp);
        }
        return "Study this matchup and adapt your playstyle accordingly. ";
    }
    
    private Map<String, Map<String, String>> initializeChampionAdvice() {
        Map<String, Map<String, String>> advice = new HashMap<>();
        
        // Garen advice
        Map<String, String> garenAdvice = new HashMap<>();
        garenAdvice.put("general", "Use your Q for mobility and engage, E for wave clear, and R for execution.");
        garenAdvice.put("darius", "Avoid extended trades with Darius. Use Q to disengage and farm safely under turret.");
        garenAdvice.put("teemo", "Build early magic resist and use Q to silence Teemo's blind.");
        advice.put("garen", garenAdvice);
        
        // Darius advice
        Map<String, String> dariusAdvice = new HashMap<>();
        dariusAdvice.put("general", "Stack your passive with auto attacks and use Q for sustain.");
        dariusAdvice.put("garen", "Extend trades to stack your passive and use E to prevent Garen's escape.");
        advice.put("darius", dariusAdvice);
        
        // Add more champions as needed
        
        return advice;
    }
}
