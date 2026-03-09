@echo off
REM Real Estate Project - Library Setup Script (Windows)
REM This script downloads OpenCSV and configures the project

echo === Real Estate Project Setup ===
echo.

REM Create lib directory
echo [1/4] Creating lib directory...
if not exist "Realestate\lib" mkdir "Realestate\lib"

REM Download OpenCSV 3.8
echo [2/4] Downloading OpenCSV 3.8...
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/opencsv/opencsv/3.8/opencsv-3.8.jar' -OutFile 'Realestate\lib\opencsv-3.8.jar'"

if %ERRORLEVEL% EQU 0 (
    echo [OK] OpenCSV downloaded successfully
) else (
    echo [ERROR] Download failed. Please download manually from:
    echo   https://repo1.maven.org/maven2/com/opencsv/opencsv/3.8/opencsv-3.8.jar
    pause
    exit /b 1
)

REM Update project.properties
echo [3/4] Updating project.properties...
powershell -Command "(Get-Content 'Realestate\nbproject\project.properties') -replace 'file.reference.opencsv-3.8.jar=.*', 'file.reference.opencsv-3.8.jar=lib/opencsv-3.8.jar' | Set-Content 'Realestate\nbproject\project.properties'"

echo [OK] Configuration updated

REM Fix database.csv prices
echo [4/4] Fixing price formatting in database.csv...
powershell -Command "(Get-Content 'Realestate\database.csv') -replace '\"1,([0-9][0-9][0-9]),00\"', '\"1,$1,000\"' | Set-Content 'Realestate\database.csv'"

echo.
echo === Setup Complete ===
echo You can now open the project in NetBeans and run it!
echo.
echo To commit these changes:
echo   git add Realestate\lib\opencsv-3.8.jar
echo   git add Realestate\nbproject\project.properties
echo   git add Realestate\database.csv
echo   git commit -m "Fix library dependencies for portability"
echo.
pause
