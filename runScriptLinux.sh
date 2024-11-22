#!/bin/bash
SCRIPT_DIR=$(dirname "$(readlink -f "$0")")
SRC_DIR="$SCRIPT_DIR/src"
COMPILED_DIR="$SCRIPT_DIR/compiled"

mkdir -p "$COMPILED_DIR"

echo "Kompilacja plików źródłowych..."
javac -d "$COMPILED_DIR" $(find "$SRC_DIR" -name "*.java")

export CLASSPATH="$COMPILED_DIR"

if pgrep -x "rmiregistry" > /dev/null
then
    echo "Zatrzymywanie starego procesu rmiregistry..."
    pkill rmiregistry
    sleep 5
fi

if lsof -i:1099 > /dev/null
then
    echo "Port 1099 jest zajęty. Zwalnianie portu..."
    fuser -k 1099/tcp
    sleep 2
fi

echo "Uruchamianie nowego rmiregistry..."
echo "Uruchamianie nowego rmiregistry..."
cd "$COMPILED_DIR" || exit 1
echo "rmiregistry zostało uruchomione."

echo "Uruchamianie serwera..."
gnome-terminal --active -- bash -c "java server.Server; exec bash"

sleep 5

echo "Uruchamianie agenta 0..."
gnome-terminal --active -- bash -c "java agent.AgentServer 0; exec bash"

echo "Uruchamianie agenta 1..."
gnome-terminal --active -- bash -c "java agent.AgentServer 1; exec bash"

echo "Uruchamianie agenta 2..."
gnome-terminal --active -- bash -c "java agent.AgentServer 2; exec bash"

echo "Uruchamianie klienta..."
gnome-terminal --active -- bash -c "java client.Client; exec bash"



