@echo off
echo ========================================
echo  XALGO DEBUG - Troubleshooting Mode
echo ========================================
echo.

echo Checking Node.js installation...
node --version
if %errorlevel% neq 0 (
    echo ERROR: Node.js is not installed or not in PATH
    echo Please install Node.js from https://nodejs.org/
    pause
    exit /b 1
)

echo.
echo Checking npm installation...
npm --version
if %errorlevel% neq 0 (
    echo ERROR: npm is not available
    pause
    exit /b 1
)

echo.
echo Current directory:
cd
echo.

echo Checking if package.json exists...
if not exist "package.json" (
    echo ERROR: package.json not found
    echo Make sure you're in the correct directory
    pause
    exit /b 1
)

echo.
echo Installing dependencies with verbose output...
call npm install --verbose

if %errorlevel% neq 0 (
    echo.
    echo ERROR: npm install failed
    echo Check the error messages above
    pause
    exit /b 1
)

echo.
echo Installation successful! Starting Xalgo CLI...
echo.

call node src/index.js

echo.
echo Program ended. Press any key to close...
pause
