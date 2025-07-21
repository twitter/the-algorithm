import fs from 'fs-extra';
import path from 'path';
import os from 'os';
import crypto from 'crypto';

export class ConfigManager {
    constructor() {
        this.configDir = path.join(os.homedir(), '.xalgo');
        this.configFile = path.join(this.configDir, 'config.json');
        this.encryptionKey = this.getOrCreateEncryptionKey();
        this.ensureConfigExists();
    }

    async ensureConfigExists() {
        await fs.ensureDir(this.configDir);
        
        if (!(await fs.pathExists(this.configFile))) {
            const defaultConfig = {
                version: '1.0.0',
                providers: {},
                preferences: {
                    defaultProvider: null,
                    theme: 'default',
                    autoSave: true
                },
                createdAt: new Date().toISOString()
            };
            
            await fs.writeJson(this.configFile, defaultConfig, { spaces: 2 });
        }
    }

    getOrCreateEncryptionKey() {
        const keyFile = path.join(this.configDir, '.key');
        
        try {
            if (fs.existsSync(keyFile)) {
                return fs.readFileSync(keyFile, 'utf8');
            }
        } catch (error) {
            // Key file doesn't exist or can't be read, create new one
        }
        
        const key = crypto.randomBytes(32).toString('hex');
        try {
            fs.ensureDirSync(this.configDir);
            fs.writeFileSync(keyFile, key, { mode: 0o600 }); // Only user can read/write
        } catch (error) {
            console.warn('Warning: Could not save encryption key to disk');
        }
        
        return key;
    }

    encrypt(text) {
        const iv = crypto.randomBytes(16);
        const cipher = crypto.createCipher('aes-256-cbc', this.encryptionKey);
        let encrypted = cipher.update(text, 'utf8', 'hex');
        encrypted += cipher.final('hex');
        return iv.toString('hex') + ':' + encrypted;
    }

    decrypt(encryptedText) {
        try {
            const [ivHex, encrypted] = encryptedText.split(':');
            const decipher = crypto.createDecipher('aes-256-cbc', this.encryptionKey);
            let decrypted = decipher.update(encrypted, 'hex', 'utf8');
            decrypted += decipher.final('utf8');
            return decrypted;
        } catch (error) {
            throw new Error('Failed to decrypt API key');
        }
    }

    async loadConfig() {
        return await fs.readJson(this.configFile);
    }

    async saveConfig(config) {
        config.updatedAt = new Date().toISOString();
        await fs.writeJson(this.configFile, config, { spaces: 2 });
    }

    async setApiKey(provider, apiKey) {
        const config = await this.loadConfig();
        
        if (!config.providers) {
            config.providers = {};
        }
        
        config.providers[provider] = {
            apiKey: this.encrypt(apiKey),
            configuredAt: new Date().toISOString()
        };
        
        await this.saveConfig(config);
    }

    async getApiKey(provider) {
        const config = await this.loadConfig();
        
        if (!config.providers || !config.providers[provider]) {
            return null;
        }
        
        try {
            return this.decrypt(config.providers[provider].apiKey);
        } catch (error) {
            console.warn(`Warning: Could not decrypt API key for ${provider}`);
            return null;
        }
    }

    async removeApiKey(provider) {
        const config = await this.loadConfig();
        
        if (config.providers && config.providers[provider]) {
            delete config.providers[provider];
            await this.saveConfig(config);
            return true;
        }
        
        return false;
    }

    async hasValidConfig() {
        try {
            const config = await this.loadConfig();
            return config.providers && Object.keys(config.providers).length > 0;
        } catch (error) {
            return false;
        }
    }

    async getConfiguredProviders() {
        const config = await this.loadConfig();
        const providers = [];
        
        const providerInfo = {
            openai: { displayName: 'OpenAI (ChatGPT)', key: 'openai' },
            anthropic: { displayName: 'Anthropic (Claude)', key: 'anthropic' },
            deepseek: { displayName: 'Deepseek', key: 'deepseek' },
            mistral: { displayName: 'Mistral AI', key: 'mistral' },
            grok: { displayName: 'xAI (Grok)', key: 'grok' },
            google: { displayName: 'Google (Gemini)', key: 'google' }
        };
        
        if (config.providers) {
            for (const [key, value] of Object.entries(config.providers)) {
                if (value.apiKey && providerInfo[key]) {
                    providers.push(providerInfo[key]);
                }
            }
        }
        
        return providers;
    }

    async setPreference(key, value) {
        const config = await this.loadConfig();
        
        if (!config.preferences) {
            config.preferences = {};
        }
        
        config.preferences[key] = value;
        await this.saveConfig(config);
    }

    async getPreference(key, defaultValue = null) {
        const config = await this.loadConfig();
        
        if (config.preferences && config.preferences.hasOwnProperty(key)) {
            return config.preferences[key];
        }
        
        return defaultValue;
    }

    async getProviderStatus() {
        const config = await this.loadConfig();
        const status = {};
        
        const allProviders = ['openai', 'anthropic', 'deepseek', 'mistral', 'grok', 'google'];
        
        for (const provider of allProviders) {
            status[provider] = {
                configured: !!(config.providers && config.providers[provider]),
                configuredAt: config.providers && config.providers[provider] ? 
                            config.providers[provider].configuredAt : null
            };
        }
        
        return status;
    }

    async exportConfig() {
        const config = await this.loadConfig();
        
        // Create a safe export (without encrypted API keys)
        const safeConfig = {
            version: config.version,
            preferences: config.preferences,
            providers: {}
        };
        
        if (config.providers) {
            for (const [provider, data] of Object.entries(config.providers)) {
                safeConfig.providers[provider] = {
                    configured: true,
                    configuredAt: data.configuredAt
                };
            }
        }
        
        return safeConfig;
    }

    async resetConfig() {
        const backupPath = this.configFile + '.backup.' + Date.now();
        
        // Backup current config
        if (await fs.pathExists(this.configFile)) {
            await fs.copy(this.configFile, backupPath);
        }
        
        // Remove config file to trigger recreation
        await fs.remove(this.configFile);
        await this.ensureConfigExists();
        
        return backupPath;
    }
}
