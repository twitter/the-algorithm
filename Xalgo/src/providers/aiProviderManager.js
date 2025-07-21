import axios from 'axios';
import fetch from 'node-fetch';

export class AIProviderManager {
    constructor() {
        this.providers = {
            openai: {
                name: 'OpenAI (ChatGPT)',
                endpoint: 'https://api.openai.com/v1/chat/completions',
                model: 'gpt-4'
            },
            anthropic: {
                name: 'Anthropic (Claude)',
                endpoint: 'https://api.anthropic.com/v1/messages',
                model: 'claude-3-sonnet-20240229'
            },
            deepseek: {
                name: 'Deepseek',
                endpoint: 'https://api.deepseek.com/v1/chat/completions',
                model: 'deepseek-chat'
            },
            mistral: {
                name: 'Mistral AI',
                endpoint: 'https://api.mistral.ai/v1/chat/completions',
                model: 'mistral-large-latest'
            },
            grok: {
                name: 'xAI (Grok)',
                endpoint: 'https://api.x.ai/v1/chat/completions',
                model: 'grok-beta'
            },
            google: {
                name: 'Google (Gemini)',
                endpoint: 'https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent',
                model: 'gemini-pro'
            }
        };
    }

    async getResponse(providerKey, prompt, conversationHistory = []) {
        const provider = this.providers[providerKey];
        if (!provider) {
            throw new Error(`Provider ${providerKey} not found`);
        }

        // Try to get API key from file first, then fallback to config manager
        const { FileConfigLoader } = await import('../utils/fileConfigLoader.js');
        const fileLoader = new FileConfigLoader();
        const fileKeys = await fileLoader.loadApiKeysFromFile();
        
        let apiKey = fileKeys[providerKey];
        
        // Fallback to config manager if not found in file
        if (!apiKey) {
            const { ConfigManager } = await import('../utils/configManager.js');
            const configManager = new ConfigManager();
            apiKey = await configManager.getApiKey(providerKey);
        }

        if (!apiKey) {
            throw new Error(`No API key configured for ${provider.name}. Please add it to api-keys.txt file.`);
        }

        switch (providerKey) {
            case 'openai':
                return await this.callOpenAI(apiKey, prompt, conversationHistory);
            case 'anthropic':
                return await this.callAnthropic(apiKey, prompt, conversationHistory);
            case 'deepseek':
                return await this.callDeepseek(apiKey, prompt, conversationHistory);
            case 'mistral':
                return await this.callMistral(apiKey, prompt, conversationHistory);
            case 'grok':
                return await this.callGrok(apiKey, prompt, conversationHistory);
            case 'google':
                return await this.callGoogle(apiKey, prompt, conversationHistory);
            default:
                throw new Error(`Provider ${providerKey} not implemented`);
        }
    }

    async callOpenAI(apiKey, prompt, conversationHistory) {
        const messages = [
            { role: 'system', content: 'You are an expert assistant helping users understand the X (formerly Twitter) algorithm. Use plain text formatting only - no markdown.' },
            ...conversationHistory,
            { role: 'user', content: prompt }
        ];

        const response = await axios.post(
            'https://api.openai.com/v1/chat/completions',
            {
                model: 'gpt-4',
                messages: messages,
                max_tokens: 2000,
                temperature: 0.7,
                stream: false
            },
            {
                headers: {
                    'Authorization': `Bearer ${apiKey}`,
                    'Content-Type': 'application/json'
                }
            }
        );

        return response.data.choices[0].message.content;
    }

    async callAnthropic(apiKey, prompt, conversationHistory) {
        let messages = [...conversationHistory, { role: 'user', content: prompt }];
        
        // Anthropic requires alternating user/assistant messages
        messages = this.normalizeMessages(messages);

        const response = await axios.post(
            'https://api.anthropic.com/v1/messages',
            {
                model: 'claude-3-sonnet-20240229',
                max_tokens: 2000,
                messages: messages,
                system: 'You are an expert assistant helping users understand the X (formerly Twitter) algorithm. Use plain text formatting only - no markdown.',
                stream: false
            },
            {
                headers: {
                    'x-api-key': apiKey,
                    'Content-Type': 'application/json',
                    'anthropic-version': '2023-06-01'
                }
            }
        );

        return response.data.content[0].text;
    }

    async callDeepseek(apiKey, prompt, conversationHistory) {
        const messages = [
            { role: 'system', content: 'You are an expert assistant helping users understand the X (formerly Twitter) algorithm. Use plain text formatting only - no markdown.' },
            ...conversationHistory,
            { role: 'user', content: prompt }
        ];

        const response = await axios.post(
            'https://api.deepseek.com/v1/chat/completions',
            {
                model: 'deepseek-chat',
                messages: messages,
                max_tokens: 2000,
                temperature: 0.7,
                stream: false
            },
            {
                headers: {
                    'Authorization': `Bearer ${apiKey}`,
                    'Content-Type': 'application/json'
                }
            }
        );

        return response.data.choices[0].message.content;
    }

    async callMistral(apiKey, prompt, conversationHistory) {
        const messages = [
            { role: 'system', content: 'You are an expert assistant helping users understand the X (formerly Twitter) algorithm. Use plain text formatting only - no markdown.' },
            ...conversationHistory,
            { role: 'user', content: prompt }
        ];

        const response = await axios.post(
            'https://api.mistral.ai/v1/chat/completions',
            {
                model: 'mistral-large-latest',
                messages: messages,
                max_tokens: 2000,
                temperature: 0.7,
                stream: false
            },
            {
                headers: {
                    'Authorization': `Bearer ${apiKey}`,
                    'Content-Type': 'application/json'
                }
            }
        );

        return response.data.choices[0].message.content;
    }

    async callGrok(apiKey, prompt, conversationHistory) {
        const messages = [
            { role: 'system', content: 'You are an expert assistant helping users understand the X (formerly Twitter) algorithm. Use plain text formatting only - no markdown.' },
            ...conversationHistory,
            { role: 'user', content: prompt }
        ];

        const response = await axios.post(
            'https://api.x.ai/v1/chat/completions',
            {
                model: 'grok-beta',
                messages: messages,
                max_tokens: 2000,
                temperature: 0.7,
                stream: false
            },
            {
                headers: {
                    'Authorization': `Bearer ${apiKey}`,
                    'Content-Type': 'application/json'
                }
            }
        );

        return response.data.choices[0].message.content;
    }

    async callGoogle(apiKey, prompt, conversationHistory) {
        // Convert conversation to Google's format
        const contents = [];
        
        // Add system message as first user message for Google
        contents.push({
            role: 'user',
            parts: [{ text: 'You are an expert assistant helping users understand the X (formerly Twitter) algorithm. Use plain text formatting only - no markdown.' }]
        });
        contents.push({
            role: 'model',
            parts: [{ text: 'I understand. I will help you explore the X algorithm using only plain text formatting without any markdown.' }]
        });
        
        // Add conversation history
        for (const msg of conversationHistory) {
            contents.push({
                role: msg.role === 'assistant' ? 'model' : 'user',
                parts: [{ text: msg.content }]
            });
        }
        
        // Add current prompt
        contents.push({
            role: 'user',
            parts: [{ text: prompt }]
        });

        const response = await axios.post(
            `https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=${apiKey}`,
            {
                contents: contents,
                generationConfig: {
                    maxOutputTokens: 2000,
                    temperature: 0.7
                }
            },
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        );

        return response.data.candidates[0].content.parts[0].text;
    }

    normalizeMessages(messages) {
        const normalized = [];
        let lastRole = null;

        for (const msg of messages) {
            if (msg.role === lastRole) {
                // Combine consecutive messages from the same role
                normalized[normalized.length - 1].content += '\n\n' + msg.content;
            } else {
                normalized.push(msg);
                lastRole = msg.role;
            }
        }

        return normalized;
    }
}
