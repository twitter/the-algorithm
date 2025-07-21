#!/bin/bash

echo "========================================"
echo " XALGO - AI-Powered X Algorithm Explorer"
echo "========================================"
echo ""
echo "Installing dependencies..."
npm install

if [ $? -ne 0 ]; then
    echo ""
    echo "❌ Installation failed!"
    echo "Please check your Node.js installation and try again."
    exit 1
fi

echo ""
echo "✅ Installation complete!"
echo ""
echo "Starting Xalgo CLI..."
echo ""
npm start
