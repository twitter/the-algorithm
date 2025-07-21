#!/usr/bin/env node

import chalk from 'chalk';
import inquirer from 'inquirer';
import figlet from 'figlet';
import fs from 'fs-extra';
import path from 'path';
import os from 'os';
import { Spinner } from 'cli-spinner';
import { AIProviderManager } from './providers/aiProviderManager.js';
import { ConversationManager } from './utils/conversationManager.js';
import { ConfigManager } from './utils/configManager.js';
import { FileConfigLoader } from './utils/fileConfigLoader.js';
import { AlgorithmAnalyzer } from './utils/algorithmAnalyzer.js';

class XalgoCLI {
    constructor() {
        this.aiManager = new AIProviderManager();
        this.conversationManager = new ConversationManager();
        this.configManager = new ConfigManager();
        this.fileConfigLoader = new FileConfigLoader();
        this.algorithmAnalyzer = new AlgorithmAnalyzer();
        this.currentConversation = null;
        this.spinner = new Spinner('%s Thinking...');
        this.spinner.setSpinnerString('⠋⠙⠹⠸⠼⠴⠦⠧⠇⠏');
    }

    async start() {
        console.clear();
        await this.showWelcome();
        await this.checkConfiguration();
        await this.mainMenu();
    }

    async showWelcome() {
        const xalgoArt = `
 ██╗  ██╗ █████╗ ██╗      ██████╗  ██████╗ 
 ╚██╗██╔╝██╔══██╗██║     ██╔════╝ ██╔═══██╗
  ╚███╔╝ ███████║██║     ██║  ███╗██║   ██║
  ██╔██╗ ██╔══██║██║     ██║   ██║██║   ██║
 ██╔╝ ██╗██║  ██║███████╗╚██████╔╝╚██████╔╝
 ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝  ╚═════╝ 
        `;
        
        // Create blue gradient effect
        const lines = xalgoArt.split('\n');
        const blueShades = ['cyan', 'blue', 'blueBright', 'blue', 'cyan', 'blue'];
        
        console.log();
        lines.forEach((line, index) => {
            if (line.trim()) {
                const colorIndex = index % blueShades.length;
                console.log(chalk[blueShades[colorIndex]].bold(line));
            } else {
                console.log(line);
            }
        });
        
        console.log(chalk.white.bold('                    AI-Powered X Algorithm Explorer'));
        console.log(chalk.gray('━'.repeat(80)));
        console.log(chalk.cyan('  Dive deep into X\'s recommendation algorithms through AI conversations'));
        console.log(chalk.white('  • Real algorithm analysis  • Code insights  • Interactive exploration'));
        console.log(chalk.gray('━'.repeat(80)));
        console.log();
    }

    async checkConfiguration() {
        console.log(chalk.gray('Checking configuration...'));
        
        // Check if api-keys.txt file exists and has keys
        const hasFileConfig = await this.fileConfigLoader.hasConfigFile();
        console.log(chalk.gray(`File config exists: ${hasFileConfig}`));
        
        const fileProviders = await this.fileConfigLoader.getConfiguredProviders();
        console.log(chalk.gray(`File providers found: ${fileProviders.length}`));
        
        if (hasFileConfig && fileProviders.length > 0) {
            console.log(chalk.green(`✅ Found ${fileProviders.length} API key(s) configured in api-keys.txt`));
            console.log(chalk.white(`Available providers: ${fileProviders.map(p => p.displayName).join(', ')}`));
            return; // Skip interactive configuration
        }
        
        console.log(chalk.gray('No file config found, checking traditional config...'));
        
        // Fallback to traditional config
        const hasConfig = await this.configManager.hasValidConfig();
        if (!hasConfig) {
            console.log(chalk.yellow('First time setup - Let\'s configure your AI providers!'));
            console.log(chalk.cyan('TIP: You can also edit the api-keys.txt file directly instead!'));
            console.log(chalk.gray('Make sure to remove the # before your API key line in api-keys.txt'));
            await this.configureProviders();
        }
    }

    async configureProviders() {
        const providers = [
            { name: 'OpenAI (ChatGPT)', key: 'openai', envVar: 'OPENAI_API_KEY' },
            { name: 'Anthropic (Claude)', key: 'anthropic', envVar: 'ANTHROPIC_API_KEY' },
            { name: 'Deepseek', key: 'deepseek', envVar: 'DEEPSEEK_API_KEY' },
            { name: 'Mistral AI', key: 'mistral', envVar: 'MISTRAL_API_KEY' },
            { name: 'xAI (Grok)', key: 'grok', envVar: 'GROK_API_KEY' },
            { name: 'Google (Gemini)', key: 'google', envVar: 'GOOGLE_API_KEY' }
        ];

        console.log(chalk.cyan('\nConfigure AI Providers'));
        console.log(chalk.gray('Enter API keys for the providers you want to use (or press Enter to skip):\n'));

        for (const provider of providers) {
            const { apiKey } = await inquirer.prompt([{
                type: 'password',
                name: 'apiKey',
                message: `${provider.name} API Key:`,
                mask: '*'
            }]);

            if (apiKey.trim()) {
                await this.configManager.setApiKey(provider.key, apiKey.trim());
                console.log(chalk.green(`${provider.name} configured`));
            }
        }

        console.log(chalk.green('\nConfiguration complete!'));
        await this.pause();
    }

    async mainMenu() {
        while (true) {
            console.clear();
            await this.showWelcome();
            
            const conversations = await this.conversationManager.listConversations();
            const choices = [
                { name: 'Start New Conversation', value: 'new' },
                { name: 'Load Previous Conversation', value: 'load', disabled: conversations.length === 0 },
                { name: 'Configure AI Providers', value: 'config' },
                { name: 'Exit', value: 'exit' }
            ];

            const { action } = await inquirer.prompt([{
                type: 'list',
                name: 'action',
                message: 'What would you like to do?',
                choices
            }]);

            switch (action) {
                case 'new':
                    await this.startNewConversation();
                    break;
                case 'load':
                    await this.loadConversation();
                    break;
                case 'config':
                    await this.configureProviders();
                    break;
                case 'exit':
                    console.log(chalk.cyan('\nThanks for using Xalgo! Happy exploring!'));
                    process.exit(0);
                    break;
            }
        }
    }

    async startNewConversation() {
        // Check both file config and traditional config
        const fileProviders = await this.fileConfigLoader.getConfiguredProviders();
        const configProviders = await this.configManager.getConfiguredProviders();
        const providers = [...fileProviders, ...configProviders];
        
        // Remove duplicates
        const uniqueProviders = providers.filter((provider, index, self) =>
            index === self.findIndex(p => p.key === provider.key)
        );
        
        if (uniqueProviders.length === 0) {
            console.log(chalk.red('\nNo AI providers configured.'));
            console.log(chalk.yellow('Please edit the api-keys.txt file or configure providers manually.'));
            await this.pause();
            return;
        }

        const { provider } = await inquirer.prompt([{
            type: 'list',
            name: 'provider',
            message: 'Choose an AI provider:',
            choices: uniqueProviders.map(p => ({ name: p.displayName, value: p.key }))
        }]);

        this.currentConversation = await this.conversationManager.createNewConversation(provider);
        await this.chatLoop();
    }

    async loadConversation() {
        const conversations = await this.conversationManager.listConversations();
        
        const { conversationId } = await inquirer.prompt([{
            type: 'list',
            name: 'conversationId',
            message: 'Select a conversation to load:',
            choices: conversations.map(conv => ({
                name: `${conv.title} (${conv.provider} - ${conv.date})`,
                value: conv.id
            }))
        }]);

        this.currentConversation = await this.conversationManager.loadConversation(conversationId);
        await this.displayConversationHistory();
        await this.chatLoop();
    }

    async displayConversationHistory() {
        console.clear();
        await this.showWelcome();
        console.log(chalk.cyan.bold('Conversation History'));
        console.log(chalk.gray('━'.repeat(80)));

        for (const message of this.currentConversation.messages) {
            if (message.role === 'user') {
                console.log();
                console.log(chalk.blue('┌─ ') + chalk.white.bold('You') + chalk.blue(' ─' + '─'.repeat(70) + '┐'));
                console.log(chalk.blue('│ ') + chalk.white(message.content));
                console.log(chalk.blue('└' + '─'.repeat(76) + '┘'));
            } else {
                console.log();
                console.log(chalk.green('┌─ ') + chalk.white.bold(`${this.currentConversation.provider}`) + chalk.green(' ─' + '─'.repeat(65) + '┐'));
                
                const responseLines = message.content.split('\n');
                responseLines.forEach(line => {
                    if (line.length > 72) {
                        const wrapped = this.wrapText(line, 72);
                        wrapped.forEach(wrappedLine => {
                            console.log(chalk.green('│ ') + chalk.white(wrappedLine));
                        });
                    } else {
                        console.log(chalk.green('│ ') + chalk.white(line));
                    }
                });
                
                console.log(chalk.green('└' + '─'.repeat(76) + '┘'));
            }
        }
        console.log(chalk.gray('\n━'.repeat(80)));
    }

    async chatLoop() {
        // Show tips for getting started
        this.showTipsAndContext();
        
        while (true) {
            console.log(chalk.gray('─'.repeat(80)));
            
            // Create a proper input box
            console.log(chalk.blue('┌─ ') + chalk.cyan.bold('Ask about X algorithm') + chalk.blue(' ─' + '─'.repeat(45) + '┐'));
            
            const { input } = await inquirer.prompt([{
                type: 'input',
                name: 'input',
                message: chalk.blue('│ '),
                prefix: '',
                validate: (input) => {
                    if (input.trim() === '') return 'Please enter a question or command';
                    return true;
                }
            }]);

            console.log(chalk.blue('└' + '─'.repeat(78) + '┘'));

            if (input.toLowerCase().trim() === '/exit') {
                break;
            }

            if (input.toLowerCase().trim() === '/switch') {
                await this.switchProvider();
                continue;
            }

            if (input.toLowerCase().trim() === '/new') {
                const { confirm } = await inquirer.prompt([{
                    type: 'confirm',
                    name: 'confirm',
                    message: 'Start a new conversation? Current conversation will be saved.',
                    default: false
                }]);
                
                if (confirm) {
                    await this.conversationManager.saveConversation(this.currentConversation);
                    await this.startNewConversation();
                    return;
                }
                continue;
            }

            if (input.toLowerCase().trim() === '/help') {
                this.showTipsAndContext();
                continue;
            }

            await this.processUserInput(input);
        }

        await this.conversationManager.saveConversation(this.currentConversation);
        console.log(chalk.green('\nConversation saved!'));
        await this.pause();
    }

    async processUserInput(input) {
        // Add user message to conversation
        this.currentConversation.messages.push({
            role: 'user',
            content: input,
            timestamp: new Date().toISOString()
        });

        // Display user message in a nice box
        console.log();
        console.log(chalk.blue('┌─ ') + chalk.white.bold('You') + chalk.blue(' ─' + '─'.repeat(70) + '┐'));
        console.log(chalk.blue('│ ') + chalk.white(input));
        console.log(chalk.blue('└' + '─'.repeat(76) + '┘'));

        // Show thinking spinner
        this.spinner.start();

        try {
            // Analyze the query for algorithm insights
            const analysis = await this.algorithmAnalyzer.analyzeQuery(input);
            
            // Get AI response with enhanced context
            const contextualPrompt = await this.buildContextualPrompt(input, analysis);
            const response = await this.aiManager.getResponse(
                this.currentConversation.provider,
                contextualPrompt,
                this.currentConversation.messages.slice(0, -1)
            );

            this.spinner.stop(true);

            // Add AI response to conversation
            this.currentConversation.messages.push({
                role: 'assistant',
                content: response,
                timestamp: new Date().toISOString()
            });

            // Display AI response in a nice box
            console.log();
            console.log(chalk.green('┌─ ') + chalk.white.bold(`${this.currentConversation.provider}`) + chalk.green(' ─' + '─'.repeat(65) + '┐'));
            
            // Split response into lines and display
            const responseLines = response.split('\n');
            responseLines.forEach(line => {
                // Wrap long lines
                if (line.length > 72) {
                    const wrapped = this.wrapText(line, 72);
                    wrapped.forEach(wrappedLine => {
                        console.log(chalk.green('│ ') + chalk.white(wrappedLine));
                    });
                } else {
                    console.log(chalk.green('│ ') + chalk.white(line));
                }
            });
            
            console.log(chalk.green('└' + '─'.repeat(76) + '┘'));

            // Show relevant algorithm components if found
            if (analysis.relevantComponents.length > 0) {
                console.log(chalk.cyan('\nRelevant Algorithm Components:'));
                analysis.relevantComponents.slice(0, 3).forEach(comp => {
                    console.log(chalk.white(`   • ${comp.component}: ${comp.info.description}`));
                });
            }

            // Show suggested follow-up questions
            if (analysis.suggestedQuestions.length > 0) {
                console.log(chalk.yellow('\nFollow-up questions:'));
                analysis.suggestedQuestions.slice(0, 3).forEach(question => {
                    console.log(chalk.white(`   • ${question}`));
                });
            }

        } catch (error) {
            this.spinner.stop(true);
            console.log();
            console.log(chalk.red('┌─ ') + chalk.white.bold('Error') + chalk.red(' ─' + '─'.repeat(68) + '┐'));
            console.log(chalk.red('│ ') + chalk.white(error.message));
            console.log(chalk.red('└' + '─'.repeat(76) + '┘'));
        }

        console.log();
    }

    async buildContextualPrompt(userInput, analysis = null) {
        const context = `You are an expert AI assistant helping users understand the X (formerly Twitter) algorithm and its various components.

IMPORTANT FORMATTING RULES:
- Do NOT use any markdown formatting like asterisks for bold, hashtags for headers, or backticks for code blocks
- Use plain text only with clear structure and spacing
- Use simple bullet points with - or bullet symbols
- Use UPPERCASE for emphasis instead of bold formatting
- Use line breaks and spacing for readability

You have deep knowledge of:

CORE ALGORITHM COMPONENTS:
- Home Mixer: Main service for timeline construction and candidate mixing
- CR-Mixer: Content recommender for out-of-network candidate generation  
- Follow Recommendations Service (FRS): User follow recommendations
- SimClusters: Community detection and sparse embeddings
- Earlybird Search: Real-time search and in-network tweet retrieval
- User Tweet Entity Graph (UTEG): Graph-based recommendations using GraphJet
- TimelineRanker: Legacy ranking service with relevance scoring
- Trust & Safety Models: Content safety, NSFW detection, toxicity scoring

ALGORITHM PROCESSES:
- Timeline ranking algorithms and scoring functions
- Candidate generation pipelines and sourcing strategies
- Feature hydration and machine learning model inference
- Content filtering, safety mechanisms, and visibility controls
- User engagement metrics and behavioral signals
- Recommendation systems and personalization
- Real-time processing and caching strategies

The user is exploring the actual X algorithm codebase with components like:
- Scala services (Home Mixer, CR-Mixer, FRS)
- Python ML models (ranking, safety, embeddings)
- Java search infrastructure (Earlybird)
- Thrift service definitions and APIs

${analysis && analysis.relevantComponents.length > 0 ? 
`RELEVANT TO THIS QUERY:
${analysis.relevantComponents.slice(0, 3).map(comp => 
`- ${comp.component}: ${comp.info.description} (${comp.info.purpose})`
).join('\n')}` : ''}

Provide detailed, technical explanations that help users understand how these systems work in practice. Reference specific code patterns, algorithms, and architectural decisions when relevant. Remember to use PLAIN TEXT formatting only.

User question: ${userInput}`;

        return context;
    }

    wrapText(text, maxWidth) {
        const words = text.split(' ');
        const lines = [];
        let currentLine = '';

        for (const word of words) {
            if ((currentLine + word).length <= maxWidth) {
                currentLine += (currentLine ? ' ' : '') + word;
            } else {
                if (currentLine) lines.push(currentLine);
                currentLine = word;
            }
        }
        
        if (currentLine) lines.push(currentLine);
        return lines;
    }

    async switchProvider() {
        // Check both file config and traditional config
        const fileProviders = await this.fileConfigLoader.getConfiguredProviders();
        const configProviders = await this.configManager.getConfiguredProviders();
        const allProviders = [...fileProviders, ...configProviders];
        
        // Remove duplicates
        const providers = allProviders.filter((provider, index, self) =>
            index === self.findIndex(p => p.key === provider.key)
        );
        
        const currentProvider = this.currentConversation.provider;
        const availableProviders = providers.filter(p => p.key !== currentProvider);
        
        if (availableProviders.length === 0) {
            console.log(chalk.yellow('\nNo other providers configured.'));
            return;
        }

        const { newProvider } = await inquirer.prompt([{
            type: 'list',
            name: 'newProvider',
            message: 'Switch to which provider?',
            choices: availableProviders.map(p => ({ name: p.displayName, value: p.key }))
        }]);

        this.currentConversation.provider = newProvider;
        console.log(chalk.green(`\nSwitched to ${newProvider}`));
    }

    async pause() {
        await inquirer.prompt([{
            type: 'input',
            name: 'continue',
            message: 'Press Enter to continue...'
        }]);
    }

    showTipsAndContext() {
        console.log(chalk.white.bold('\nTips for exploring the X algorithm:'));
        console.log(chalk.gray('   1. Ask questions, analyze code, or explore components'));
        console.log(chalk.gray('   2. Be specific for the best results'));
        console.log(chalk.gray('   3. Use /help for more information'));
        console.log();
        
        console.log(chalk.yellow.bold('Try asking:'));
        console.log(chalk.white('   • "How does the For You timeline ranking work?"'));
        console.log(chalk.white('   • "Explain the SimClusters algorithm"'));
        console.log(chalk.white('   • "What is Home Mixer and how does it work?"'));
        console.log(chalk.white('   • "How does follow recommendation work?"'));
        console.log();
        
        console.log(chalk.green.bold('Commands:'));
        console.log(chalk.white('   /exit - Exit conversation  /switch - Change AI  /new - New chat  /help - Show this'));
        console.log();
    }
}

// Start the CLI
const cli = new XalgoCLI();
cli.start().catch(console.error);
