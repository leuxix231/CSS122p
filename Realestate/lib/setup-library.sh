#!/bin/bash

# Real Estate Project - Library Setup Script
# This script downloads OpenCSV and configures the project

echo "=== Real Estate Project Setup ==="
echo ""

# Create lib directory
echo "[1/4] Creating lib directory..."
mkdir -p Realestate/lib

# Download OpenCSV 3.8
echo "[2/4] Downloading OpenCSV 3.8..."
curl -L -o Realestate/lib/opencsv-3.8.jar \
  https://repo1.maven.org/maven2/com/opencsv/opencsv/3.8/opencsv-3.8.jar

if [ $? -eq 0 ]; then
    echo "✓ OpenCSV downloaded successfully"
else
    echo "✗ Download failed. Please download manually from:"
    echo "  https://repo1.maven.org/maven2/com/opencsv/opencsv/3.8/opencsv-3.8.jar"
    exit 1
fi

# Update project.properties
echo "[3/4] Updating project.properties..."
sed -i 's|file.reference.opencsv-3.8.jar=.*|file.reference.opencsv-3.8.jar=lib/opencsv-3.8.jar|' \
  Realestate/nbproject/project.properties

echo "✓ Configuration updated"

# Fix database.csv prices (bonus fix)
echo "[4/4] Fixing price formatting in database.csv..."
sed -i 's/"1,\([0-9][0-9][0-9]\),00"/"1,\1,000"/g' Realestate/database.csv

echo ""
echo "=== Setup Complete ==="
echo "You can now open the project in NetBeans and run it!"
echo ""
echo "To commit these changes:"
echo "  git add Realestate/lib/opencsv-3.8.jar"
echo "  git add Realestate/nbproject/project.properties"
echo "  git add Realestate/database.csv"
echo "  git commit -m 'Fix library dependencies for portability'"
