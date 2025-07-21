# Xalgo CLI - Quick Start Guide

## Prerequisites

- **Node.js** (version 16 or higher)
- **npm** (comes with Node.js)
- API keys for at least one AI provider

## Installation & Setup

### Option 1: Easy Setup with Text File (Recommended)
1. Open `api-keys.txt` in any text editor (Notepad, VS Code, etc.)
2. Add your API key(s) by removing the `#` and adding your key:
   ```
   deepseek=your_actual_api_key_here
   ```
3. Save the file
4. Double-click `launch.bat` to start

### Option 2: Quick Start (Windows)
```bash
# Double-click start.bat or run in PowerShell:
.\start.bat
```

### Option 3: Troubleshooting (Windows)
If start.bat crashes or closes immediately:
```bash
# Try the debug version for detailed error info:
.\debug.bat

# Or launch directly (if dependencies are already installed):
.\launch.bat
```

### Option 4: Quick Start (macOS/Linux)
```bash
# Make script executable and run:
chmod +x start.sh
./start.sh
```

### Option 5: Manual Setup
```bash
# Install dependencies
npm install

# Start the CLI
npm start
```

## Getting API Keys

### OpenAI (ChatGPT)
1. Visit https://platform.openai.com/api-keys
2. Create an account or sign in
3. Click "Create new secret key"
4. Copy the key (starts with `sk-`)

### Anthropic (Claude)
1. Visit https://console.anthropic.com/
2. Create an account or sign in
3. Go to "API Keys" section
4. Generate a new key

### Deepseek
1. Visit https://platform.deepseek.com/
2. Create an account
3. Navigate to API settings
4. Generate API key

### Mistral AI
1. Visit https://console.mistral.ai/
2. Create account and verify email
3. Go to "API Keys"
4. Create new key

### xAI (Grok)
1. Visit https://console.x.ai/
2. Sign up for xAI account
3. Generate API key in console

### Google (Gemini)
1. Visit https://makersuite.google.com/app/apikey
2. Sign in with Google account
3. Click "Create API Key"

## First Run

### Easy Method (Recommended):
1. **Edit api-keys.txt**: Open the file in any text editor and add your API key
2. **Launch**: Double-click `launch.bat`
3. **Start Chatting**: Begin asking questions about the X algorithm!

### Traditional Method:
1. **Launch Xalgo**: Run the application using one of the methods above
2. **Configure Providers**: Enter API keys for the AI providers you want to use
3. **Start Chatting**: Begin asking questions about the X algorithm!

## Sample Questions to Try

### Algorithm Basics
- "How does the X recommendation algorithm work?"
- "Explain the timeline ranking system"
- "What is the Home Timeline algorithm?"

### Technical Deep Dives
- "How does the candidate generation work in Home Mixer?"
- "Explain the scoring functions used in ranking"
- "What machine learning models are used?"

### Specific Components
- "How does the Follow Recommendations Service work?"
- "Explain the Graph Feature Service"
- "What is the Trust and Safety scoring?"

### Code Analysis
- "Show me how tweet ranking works in the codebase"
- "Explain the SimClusters algorithm"
- "How does the Representation Manager work?"

## CLI Commands

While chatting, you can use these special commands:

- `/exit` - Exit the current conversation
- `/switch` - Switch to a different AI provider
- `/new` - Start a new conversation (saves current one)

## File Locations

Xalgo stores your data locally:

- **Conversations**: `~/.xalgo/conversations/`
- **Configuration**: `~/.xalgo/config.json`
- **Exports**: `~/.xalgo/exports/`

## Tips for Better Results

1. **Be Specific**: Ask about particular components or algorithms
2. **Reference Code**: Mention specific files or functions you're curious about
3. **Follow Up**: Build on previous answers for deeper understanding
4. **Switch Models**: Different AI providers may have different strengths

## Troubleshooting

### start.bat crashes or closes immediately
1. **Try debug.bat**: Double-click `debug.bat` for detailed error information
2. **Check Node.js**: Make sure Node.js is installed and in your PATH
3. **Run manually**: Open PowerShell in the Xalgo folder and run:
   ```powershell
   npm install
   node src/index.js
   ```
4. **Permission issues**: If you get execution policy errors, run:
   ```powershell
   Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
   ```

### "No AI providers configured"
- Make sure you've entered at least one valid API key
- Check that your API key is correct and has sufficient credits

### "Module not found" errors
- Run `npm install` to ensure all dependencies are installed
- Make sure you're using Node.js version 16 or higher

### "API key invalid" errors
- Double-check your API key is entered correctly
- Verify the API key has proper permissions
- Check if you have remaining credits/quota

### Connection issues
- Check your internet connection
- Some corporate networks may block AI API calls
- Try a different AI provider if one is not working

## Advanced Usage

### Export Conversations
Conversations are automatically saved. You can find them in JSON format at:
`~/.xalgo/conversations/`

### Multiple Conversations
You can have multiple ongoing conversations and switch between them easily.

### Custom Contexts
The CLI automatically provides context about the X algorithm codebase to help AI providers give more accurate responses.

## Support

If you encounter issues:
1. Check this guide first
2. Ensure your Node.js and npm are up to date
3. Verify your API keys are valid
4. Check the GitHub repository for updates

Happy exploring!
