# 🎮 LoL Voice Coach

An AI-powered tactical advisor for League of Legends that provides real-time coaching advice based on user-described scenarios and professional match data.

## 🎯 Features

- **Voice Input**: Speak your game situation using speech recognition (Vosk)
- **Text Input**: Type your scenario for quick advice
- **Smart Parsing**: NLP-based parsing of game scenarios into structured data
- **Tactical Analysis**: Generate personalized advice based on champion matchups, lane position, and game timing
- **Voice Output**: Hear your advice through text-to-speech (FreeTTS)
- **Live Data Integration**: Optional integration with LoL Client API and Riot API
- **Professional Match Data**: Access to historical pro match scenarios and outcomes

## 🏗️ Architecture

The application follows a modular service-oriented architecture:

```
src/main/java/
├── CoachApp.java          # Main application orchestrator
├── VoiceInput.java        # Speech recognition service
├── Parser.java           # NLP scenario parsing
├── model/
│   ├── Scenario.java     # Game scenario data model
│   └── MatchData.java    # Professional match data model
├── service/
│   ├── TacticsService.java # Tactical advice generation
│   └── TtsService.java   # Text-to-speech service
└── api/
    └── RiotApiService.java # Riot API integration
```

## 🚀 Quick Start

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Microphone (for voice input)
- Speakers/headphones (for voice output)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd lol-coach
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="main.java.CoachApp"
   ```

   Or build and run the JAR:
   ```bash
   mvn clean package
   java -jar target/lol-voice-coach-1.0.0-jar-with-dependencies.jar
   ```

## 📖 Usage

### 1. Voice Input Mode
- Select option 1 from the main menu
- Speak your game situation clearly
- Example: *"I am top lane, 10 minutes in, I'm Garen, enemy Darius is level 6 and ahead"*

### 2. Text Input Mode
- Select option 2 from the main menu
- Type your scenario description
- Example: *"Mid lane, 15 minutes, I'm Ahri vs Zed, we're even"*

### 3. API Configuration (Optional)
- Select option 3 to configure Riot API
- Enter your API key and region for live data integration
- Skip to run in offline mode

## 🎯 Example Scenarios

The system can parse various game situations:

- **Lane Information**: top, mid, bottom, jungle
- **Timing**: game time in minutes
- **Champions**: player and enemy champion names
- **Levels**: player and enemy levels
- **Situations**: ahead, behind, even, struggling, dominating

### Sample Input
```
"I am top lane, 10 minutes in, I'm Garen, enemy Darius is level 6 and ahead"
```

### Sample Output
```
💡 TACTICAL ADVICE:
==================================================
In top lane, focus on wave management and teleport usage. Use your Q for mobility and engage, E for wave clear, and R for execution. Avoid extended trades with Darius. Use Q to disengage and farm safely under turret. You're behind, play safely and farm under turret. Mid game: Look for roaming opportunities and objective control.
==================================================
```

## 🔧 Configuration

### Speech Recognition (Vosk)
To enable actual speech recognition, uncomment the Vosk dependency in `pom.xml` and implement the speech recognition logic in `VoiceInput.java`.

### Text-to-Speech (FreeTTS)
To enable actual TTS, uncomment the FreeTTS dependency in `pom.xml` and implement the TTS logic in `TtsService.java`.

### Riot API
Configure your Riot API key and region through the application menu for live data integration.

## 🧠 How It Works

1. **Input Processing**: Voice or text input is captured and processed
2. **Scenario Parsing**: NLP techniques extract structured game data
3. **Tactical Analysis**: Rule-based engine generates personalized advice
4. **Data Enrichment**: Optional live game data integration
5. **Advice Delivery**: Text and voice output of tactical recommendations

## 🎨 Customization

### Adding New Champions
Extend the `championAdvice` map in `TacticsService.java`:

```java
Map<String, String> newChampAdvice = new HashMap<>();
newChampAdvice.put("general", "General advice for the champion");
newChampAdvice.put("enemyChamp", "Specific matchup advice");
advice.put("championName", newChampAdvice);
```

### Adding New Situations
Extend the `SITUATION_PATTERN` regex in `Parser.java` and add corresponding advice logic.

### Custom Advice Rules
Modify the advice generation methods in `TacticsService.java` to implement your own tactical logic.

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

## 📦 Dependencies

- **Core**: Java 11, Maven
- **Speech Recognition**: Vosk (offline) / Google STT (online)
- **Text-to-Speech**: FreeTTS (offline)
- **HTTP Client**: Apache HttpClient
- **JSON Processing**: Jackson
- **Logging**: SLF4J + Logback

## 🚧 Development Status

- ✅ Core architecture and data models
- ✅ NLP scenario parsing
- ✅ Tactical advice generation
- ✅ Text-to-speech service (simulated)
- ✅ Voice input service (simulated)
- ✅ Riot API integration framework
- 🔄 Speech recognition implementation
- 🔄 TTS implementation
- 🔄 Live data integration

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Implement your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Riot Games for the League of Legends API
- Vosk for offline speech recognition
- FreeTTS for text-to-speech capabilities
- The League of Legends community for inspiration

## 🆘 Support

For issues and questions:
- Create an issue in the repository
- Check the help section in the application
- Review the logging output for debugging information

---

**Good luck on the Rift! 🏆**
