#!/bin/bash

# Ustal katalog, w którym znajduje się skrypt
SCRIPT_DIR=$(dirname "$(readlink -f "$0")")
SRC_DIR="$SCRIPT_DIR/src" # Katalog źródłowy
COMPILED_DIR="$SCRIPT_DIR/compiled" # Katalog na pliki skompilowane

# Upewnij się, że katalog na pliki skompilowane istnieje
mkdir -p "$COMPILED_DIR"

# Kompilacja plików źródłowych
echo "Kompilacja plików źródłowych..."
javac -d "$COMPILED_DIR" $(find "$SRC_DIR" -name "*.java")

# Ustawienie CLASSPATH
export CLASSPATH="$COMPILED_DIR"

# Zatrzymanie istniejącego procesu rmiregistry, jeśli działa
if pgrep -x "rmiregistry" > /dev/null
then
    echo "Zatrzymywanie starego procesu rmiregistry..."
    pkill rmiregistry
    sleep 5
fi

# Zwolnij port 1099, jeśli nadal jest zajęty
if lsof -i:1099 > /dev/null
then
    echo "Port 1099 jest zajęty. Zwalnianie portu..."
    fuser -k 1099/tcp
    sleep 2
fi

# Uruchomienie nowego rmiregistry
echo "Uruchamianie nowego rmiregistry..."
echo "Uruchamianie nowego rmiregistry..."
cd "$COMPILED_DIR" || exit 1
echo "rmiregistry zostało uruchomione."

# Uruchomienie serwera
echo "Uruchamianie serwera..."
gnome-terminal --active -- bash -c "java server.Server; exec bash"

# Czekaj na uruchomienie serwera
sleep 5

# Uruchomienie agentów
echo "Uruchamianie agenta 1..."
gnome-terminal --active -- bash -c "java agent.AgentServer 1; exec bash"

echo "Uruchamianie agenta 2..."
gnome-terminal --active -- bash -c "java agent.AgentServer 2; exec bash"

# Uruchomienie klienta
echo "Uruchamianie klienta..."
gnome-terminal --active -- bash -c "java client.Client; exec bash"



