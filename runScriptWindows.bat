@echo off
setlocal

REM Ustal katalog, w którym znajduje się skrypt
set SCRIPT_DIR=%~dp0
set SRC_DIR=%SCRIPT_DIR%src
set COMPILED_DIR=%SCRIPT_DIR%compiled

REM Upewnij się, że katalog na pliki skompilowane istnieje
if not exist "%COMPILED_DIR%" mkdir "%COMPILED_DIR%"

echo Kompilacja plików źródłowych...
for /r "%SRC_DIR%" %%f in (*.java) do (
    echo Kompilowanie: %%f
    javac -d "%COMPILED_DIR%" -cp "%COMPILED_DIR%" "%%f"
)
if %ERRORLEVEL% NEQ 0 (
    echo Kompilacja zakończona błędem.
    pause
    exit /b 1
)

REM Ustawienie CLASSPATH
set CLASSPATH=%COMPILED_DIR%

REM Zatrzymywanie starego procesu rmiregistry, jeśli działa
for /f "tokens=5" %%P in ('netstat -ano ^| find "1099"') do (
    echo Port 1099 jest zajęty przez PID %%P. Zwalnianie portu...
    taskkill /PID %%P /F
    timeout /t 2 > nul
)

REM Uruchomienie rmiregistry
echo Uruchamianie nowego rmiregistry...
start cmd /k "cd /d "%COMPILED_DIR%" && rmiregistry -J--add-opens=java.rmi/sun.rmi.registry=ALL-UNNAMED && pause"
echo rmiregistry zostało uruchomione.

REM Uruchamianie serwera
echo Uruchamianie serwera...
start cmd /k "cd /d "%COMPILED_DIR%" && java server.Server && pause"

timeout /t 2 > nul

REM Uruchamianie agenta 0
echo Uruchamianie agenta 0...
start cmd /k "cd /d "%COMPILED_DIR%" && java agent.AgentServer 0 && pause"

REM Uruchamianie agenta 1
echo Uruchamianie agenta 1...
start cmd /k "cd /d "%COMPILED_DIR%" && java agent.AgentServer 1 && pause"

REM Uruchamianie klienta
echo Uruchamianie klienta...
start cmd /k "cd /d "%COMPILED_DIR%" && java client.Client && pause"

endlocal
