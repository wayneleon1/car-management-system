#!/bin/bash

cd "$(dirname "$0")"

# First, try to compile/build
echo "🔧 Building CLI client..."
mvn clean compile assembly:single

if [ $? -eq 0 ] && [ -f "target/cli-client-1.0-SNAPSHOT-jar-with-dependencies.jar" ]; then
    echo "✅ Build successful!"
    java -jar target/cli-client-1.0-SNAPSHOT-jar-with-dependencies.jar "$@"
else
    echo "⚠️  JAR build failed, trying direct execution..."
    
    # Try direct execution from class files
    if [ -d "target/classes" ]; then
        echo "📦 Running from class files..."
        
        # Build classpath
        CLASSPATH="target/classes"
        
        # Add dependency JARs
        for jar in $(find ~/.m2/repository -name "*.jar" 2>/dev/null | grep -E "(jackson|commons-cli|httpclient)"); do
            CLASSPATH="$CLASSPATH:$jar"
        done
        
        java -cp "$CLASSPATH" com.example.cli.Main "$@"
    else
        echo "❌ No compiled classes found. Please check build errors above."
    fi
fi