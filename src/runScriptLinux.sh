#!/bin/bash
SCRIPT_DIR=$(dirname "$(readlink -f "$0")")
cd $SCRIPT_DIR

javac -d compiled *.java

export CLASSPATH="$SCRIPT_DIR/compiled"

# Zatrzymaj istniejący proces rmiregistry, jeśli działa
if pgrep -x "rmiregistry" > /dev/null
then
    echo "Zatrzymywanie starego procesu rmiregistry..."
    pkill rmiregistry
    sleep 2  # Poczekaj chwilę na pełne zatrzymanie
fi

echo "Uruchamianie nowego rmiregistry..."
rmiregistry &  # Uruchom rmiregistry w tle
echo "rmiregistry zostało uruchomione."

# set CLASSPATH to compiled

echo "Uruchamianie serwera..."
gnome-terminal --active -- bash -c "cd $SCRIPT_DIR/compiled; java Server; exec bash"

sleep 5

echo "Uruchamianie klienta..."
gnome-terminal --active -- bash -c "cd $SCRIPT_DIR/compiled; java Client; exec bash"