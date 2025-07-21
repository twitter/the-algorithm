@echo off
echo ========================================
echo  XALGO - AI-Powered X Algorithm Explorer
echo ========================================
echo.
echo Installing dependencies...
call npm install

if %errorlevel% neq 0 (
    echo.
    echo Installation failed!
    echo Please check your Node.js installation and try again.
    echo.
    pause
    exit /b 1
)

echo.
echo Installation complete!
echo.
echo Starting Xalgo CLI...
echo.
call npm start

echo.
echo Xalgo CLI has exited.
pause
