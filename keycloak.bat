@echo off
echo Navigating to E:\new-era-backend\customer-service\data-related\keycloak-24.0.2\bin
cd /d E:\new-era-backend\customer-service\data-related\keycloak-24.0.2\bin

echo Starting Keycloak in development mode with verbose output
.\kc.bat start-dev --verbose

pause