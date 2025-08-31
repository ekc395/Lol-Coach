package com.lolcoach;

import com.lolcoach.model.Scenario;
import com.lolcoach.service.TacticsService;
import com.lolcoach.service.TtsService;
import com.lolcoach.api.RiotApiService;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class CoachApp {
    private static final Logger LOGGER = Logger.getLogger(CoachApp.class.getName());
    
    // Core services
    private VoiceInput voiceInput;
    private Parser parser;
    private TacticsService tacticsService;
    private TtsService ttsService;
    private RiotApiService riotApiService;
    
    // Application state
    private boolean isRunning = false;
    private Scanner scanner;
    
    public CoachApp() {
        initializeServices();
        setupLogging();
        LOGGER.info("LoL Voice Coach initialized successfully");
    }
    
    /**
     * Initialize all the core services
     */
    private void initializeServices() {
        voiceInput = new VoiceInput();
        parser = new Parser();
        tacticsService = new TacticsService();
        ttsService = new TtsService();
        riotApiService = new RiotApiService();
        
        LOGGER.info("All services initialized");
    }
    
    /**
     * Setup logging configuration
     */
    private void setupLogging() {
        try {
            LogManager.getLogManager().reset();
            java.util.logging.ConsoleHandler handler = new java.util.logging.ConsoleHandler();
            handler.setLevel(Level.ALL);
            LOGGER.addHandler(handler);
            LOGGER.setLevel(Level.INFO);
        } catch (Exception e) {
            System.err.println("Failed to setup logging: " + e.getMessage());
        }
    }
    
    /**
     * Start the main application loop
     */
    public void start() {
        LOGGER.info("Starting LoL Voice Coach...");
        isRunning = true;
        
        // Display welcome message
        displayWelcomeMessage();
        
        // Main application loop
        while (isRunning) {
            try {
                displayMainMenu();
                String choice = getUserInput();
                
                switch (choice.toLowerCase()) {
                    case "1":
                        processVoiceInput();
                        break;
                    case "2":
                        processTextInput();
                        break;
                    case "3":
                        configureApi();
                        break;
                    case "4":
                        showHelp();
                        break;
                    case "5":
                        exit();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
                // Add a small delay between iterations
                Thread.sleep(1000);
                
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error in main loop", e);
                System.out.println("An error occurred. Please try again.");
            }
        }
    }
    
    /**
     * Process voice input from the user
     */
    private void processVoiceInput() {
        System.out.println("\n🎤 Listening for voice input...");
        System.out.println("(In production, this would use Vosk for speech recognition)");
        
        String recognizedText = voiceInput.listenToVoice();
        System.out.println("Recognized: " + recognizedText);
        
        processScenario(recognizedText);
    }
    
    /**
     * Process text input from the user
     */
    private void processTextInput() {
        System.out.println("\n📝 Enter your game scenario:");
        System.out.println("Example: I am top lane, 10 minutes in, I'm Garen, enemy Darius is level 6 and ahead");
        
        String textInput = getUserInput();
        processScenario(textInput);
    }
    
    /**
     * Process a scenario and generate advice
     */
    private void processScenario(String input) {
        try {
            System.out.println("\n🔄 Processing scenario...");
            
            // Parse the input into structured data
            Scenario scenario = parser.parseScenario(input);
            System.out.println("Parsed scenario: " + scenario);
            
            // Optionally enrich with live data if API is available
            if (riotApiService.isConnected()) {
                System.out.println("📡 Enriching with live game data...");
                scenario = riotApiService.enrichScenarioWithLiveData(scenario);
            }
            
            // Generate tactical advice
            System.out.println("🧠 Generating tactical advice...");
            String advice = tacticsService.generateAdvice(scenario);
            
            // Display the advice
            System.out.println("\n💡 TACTICAL ADVICE:");
            System.out.println("=".repeat(50));
            System.out.println(advice);
            System.out.println("=".repeat(50));
            
            // Speak the advice
            System.out.println("\n🔊 Speaking advice...");
            ttsService.speakAdvice(advice);
            
            // Show additional options
            showAdviceOptions(scenario);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing scenario", e);
            System.out.println("Error processing scenario: " + e.getMessage());
        }
    }
    
    /**
     * Show options after advice is given
     */
    private void showAdviceOptions(Scenario scenario) {
        System.out.println("\n📋 What would you like to do next?");
        System.out.println("1. Get more detailed advice");
        System.out.println("2. View similar pro matches");
        System.out.println("3. Return to main menu");
        
        String choice = getUserInput();
        
        switch (choice) {
            case "1":
                String detailedAdvice = tacticsService.generateAdvice(scenario);
                System.out.println("\n📚 DETAILED ADVICE:");
                System.out.println(detailedAdvice);
                break;
            case "2":
                System.out.println("\n🏆 Fetching similar pro matches...");
                var proMatches = tacticsService.fetchProMatchData(scenario);
                System.out.println("Found " + proMatches.size() + " similar pro matches");
                for (var match : proMatches) {
                    System.out.println("- " + match.getAdvice());
                }
                break;
            case "3":
                System.out.println("Returning to main menu...");
                break;
            default:
                System.out.println("Invalid choice, returning to main menu...");
        }
    }
    
    /**
     * Configure the Riot API
     */
    private void configureApi() {
        System.out.println("\n⚙️  API Configuration");
        System.out.println("Enter your Riot API key (or press Enter to skip):");
        String apiKey = getUserInput();
        
        if (!apiKey.trim().isEmpty()) {
            System.out.println("Enter your region (e.g., na1, euw1):");
            String region = getUserInput();
            
            riotApiService.configure(apiKey, region);
            
            if (riotApiService.connect()) {
                System.out.println("✅ Successfully connected to Riot API!");
            } else {
                System.out.println("❌ Failed to connect to Riot API");
            }
        } else {
            System.out.println("Skipping API configuration. Running in offline mode.");
        }
    }
    
    /**
     * Display the main menu
     */
    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           🎮 LoL Voice Coach 🎮");
        System.out.println("=".repeat(50));
        System.out.println("1. 🎤 Voice Input (Speech Recognition)");
        System.out.println("2. 📝 Text Input");
        System.out.println("3. ⚙️  Configure API");
        System.out.println("4. ❓ Help");
        System.out.println("5. 🚪 Exit");
        System.out.println("=".repeat(50));
        System.out.print("Choose an option: ");
    }
    
    /**
     * Display welcome message
     */
    private void displayWelcomeMessage() {
        System.out.println("\n🎯 Welcome to LoL Voice Coach!");
        System.out.println("Your AI-powered tactical advisor for League of Legends");
        System.out.println("Speak or type your game situation to get real-time coaching advice");
        System.out.println("\nPress Enter to continue...");
        getUserInput();
    }
    
    /**
     * Show help information
     */
    private void showHelp() {
        System.out.println("\n📖 LoL Voice Coach Help");
        System.out.println("=".repeat(40));
        System.out.println("This application provides real-time coaching advice for League of Legends.");
        System.out.println("\nHow to use:");
        System.out.println("1. Choose voice or text input");
        System.out.println("2. Describe your game situation");
        System.out.println("3. Receive tactical advice");
        System.out.println("4. Get additional details or pro match data");
        System.out.println("\nExample scenarios:");
        System.out.println("- \"I am top lane, 10 minutes in, I'm Garen, enemy Darius is level 6 and ahead\"");
        System.out.println("- \"Mid lane, 15 minutes, I'm Ahri vs Zed, we're even\"");
        System.out.println("\nPress Enter to return to main menu...");
        getUserInput();
    }
    
    /**
     * Get user input from console
     */
    private String getUserInput() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner.nextLine().trim();
    }
    
    /**
     * Exit the application
     */
    private void exit() {
        System.out.println("\n👋 Thank you for using LoL Voice Coach!");
        System.out.println("Good luck on the Rift! 🏆");
        isRunning = false;
        
        if (scanner != null) {
            scanner.close();
        }
        
        System.exit(0);
    }
    
    /**
     * Main method to start the application
     */
    public static void main(String[] args) {
        try {
            CoachApp app = new CoachApp();
            app.start();
        } catch (Exception e) {
            System.err.println("Failed to start LoL Voice Coach: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
