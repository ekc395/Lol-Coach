#!/bin/bash

echo "🎮 LoL Voice Coach - Build and Run Script"
echo "=========================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 11 or higher."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven 3.6 or higher."
    exit 1
fi

echo "✅ Java and Maven found"
echo "Java version: $(java -version 2>&1 | head -n 1)"
echo "Maven version: $(mvn -version 2>&1 | head -n 1)"

echo ""
echo "🔨 Building project..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo ""
    echo "🚀 Running LoL Voice Coach..."
    echo "Press Ctrl+C to exit"
    echo ""
    
    # Run the main application
    mvn exec:java -Dexec.mainClass="main.java.CoachApp"
else
    echo "❌ Build failed. Please check the error messages above."
    exit 1
fi
