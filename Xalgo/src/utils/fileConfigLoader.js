import fs from 'fs-extra';
import path from 'path';

export class FileConfigLoader {
    constructor() {
        this.configFile = 'api-keys.txt';
    }

    async loadApiKeysFromFile() {
        const filePath = path.resolve(this.configFile);
        
        if (!(await fs.pathExists(filePath))) {
            return {};
        }

        try {
            const content = await fs.readFile(filePath, 'utf8');
            const keys = {};
            
            const lines = content.split('\n');
            for (let i = 0; i < lines.length; i++) {
                const line = lines[i].trim();
                
                // Skip comments and empty lines
                if (line.startsWith('#') || line === '') {
                    continue;
                }
                
                // Parse key=value format
                const equalIndex = line.indexOf('=');
                if (equalIndex > 0) {
                    const provider = line.substring(0, equalIndex).trim();
                    const apiKey = line.substring(equalIndex + 1).trim();
                    
                    if (apiKey && apiKey !== '') {
                        keys[provider] = apiKey;
                        console.log(`Loaded API key for ${provider}`);
                    }
                }
            }
            
            if (Object.keys(keys).length === 0) {
                console.log('No valid API keys found. Make sure to remove the # before your API key lines.');
            }
            
            return keys;
        } catch (error) {
            console.warn('Warning: Could not read api-keys.txt file:', error.message);
            return {};
        }
    }

    async hasConfigFile() {
        return await fs.pathExists(path.resolve(this.configFile));
    }

    async getConfiguredProviders() {
        const keys = await this.loadApiKeysFromFile();
        const providers = [];
        
        const providerInfo = {
            openai: { displayName: 'OpenAI (ChatGPT)', key: 'openai' },
            anthropic: { displayName: 'Anthropic (Claude)', key: 'anthropic' },
            deepseek: { displayName: 'Deepseek', key: 'deepseek' },
            mistral: { displayName: 'Mistral AI', key: 'mistral' },
            grok: { displayName: 'xAI (Grok)', key: 'grok' },
            google: { displayName: 'Google (Gemini)', key: 'google' }
        };
        
        for (const [key, value] of Object.entries(keys)) {
            if (value && providerInfo[key]) {
                providers.push(providerInfo[key]);
            }
        }
        
        return providers;
    }
}
