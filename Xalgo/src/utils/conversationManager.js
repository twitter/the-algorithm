import fs from 'fs-extra';
import path from 'path';
import os from 'os';
import { v4 as uuidv4 } from 'uuid';

export class ConversationManager {
    constructor() {
        this.conversationsDir = path.join(os.homedir(), '.xalgo', 'conversations');
        this.ensureDirectoryExists();
    }

    async ensureDirectoryExists() {
        await fs.ensureDir(this.conversationsDir);
    }

    async createNewConversation(provider) {
        const conversation = {
            id: uuidv4(),
            provider: provider,
            title: `New Chat - ${new Date().toLocaleDateString()}`,
            createdAt: new Date().toISOString(),
            updatedAt: new Date().toISOString(),
            messages: []
        };

        return conversation;
    }

    async saveConversation(conversation) {
        conversation.updatedAt = new Date().toISOString();
        
        // Generate title from first user message if available
        if (conversation.messages.length > 0 && conversation.title.startsWith('New Chat')) {
            const firstUserMessage = conversation.messages.find(msg => msg.role === 'user');
            if (firstUserMessage) {
                conversation.title = this.generateTitle(firstUserMessage.content);
            }
        }

        const filePath = path.join(this.conversationsDir, `${conversation.id}.json`);
        await fs.writeJson(filePath, conversation, { spaces: 2 });
        
        return conversation;
    }

    async loadConversation(conversationId) {
        const filePath = path.join(this.conversationsDir, `${conversationId}.json`);
        
        if (!(await fs.pathExists(filePath))) {
            throw new Error('Conversation not found');
        }

        return await fs.readJson(filePath);
    }

    async listConversations() {
        const files = await fs.readdir(this.conversationsDir);
        const conversations = [];

        for (const file of files) {
            if (file.endsWith('.json')) {
                try {
                    const conversation = await fs.readJson(path.join(this.conversationsDir, file));
                    conversations.push({
                        id: conversation.id,
                        title: conversation.title,
                        provider: conversation.provider,
                        date: new Date(conversation.updatedAt).toLocaleDateString(),
                        messageCount: conversation.messages.length
                    });
                } catch (error) {
                    console.warn(`Warning: Could not load conversation ${file}`);
                }
            }
        }

        // Sort by update date, most recent first
        return conversations.sort((a, b) => 
            new Date(b.updatedAt || 0) - new Date(a.updatedAt || 0)
        );
    }

    async deleteConversation(conversationId) {
        const filePath = path.join(this.conversationsDir, `${conversationId}.json`);
        
        if (await fs.pathExists(filePath)) {
            await fs.remove(filePath);
            return true;
        }
        
        return false;
    }

    async exportConversation(conversationId, format = 'txt') {
        const conversation = await this.loadConversation(conversationId);
        const exportDir = path.join(os.homedir(), '.xalgo', 'exports');
        await fs.ensureDir(exportDir);

        let content = '';
        const timestamp = new Date().toISOString().replace(/[:.]/g, '-');
        
        if (format === 'txt') {
            content = this.formatConversationAsText(conversation);
            const filePath = path.join(exportDir, `${conversation.title.replace(/[^a-zA-Z0-9]/g, '_')}_${timestamp}.txt`);
            await fs.writeFile(filePath, content, 'utf8');
            return filePath;
        } else if (format === 'json') {
            const filePath = path.join(exportDir, `${conversation.title.replace(/[^a-zA-Z0-9]/g, '_')}_${timestamp}.json`);
            await fs.writeJson(filePath, conversation, { spaces: 2 });
            return filePath;
        }

        throw new Error('Unsupported export format');
    }

    formatConversationAsText(conversation) {
        let content = `XALGO CONVERSATION EXPORT
Title: ${conversation.title}
Provider: ${conversation.provider}
Created: ${new Date(conversation.createdAt).toLocaleString()}
Last Updated: ${new Date(conversation.updatedAt).toLocaleString()}
Messages: ${conversation.messages.length}

${'='.repeat(80)}

`;

        for (const message of conversation.messages) {
            const timestamp = new Date(message.timestamp).toLocaleString();
            const role = message.role === 'user' ? 'USER' : conversation.provider.toUpperCase();
            
            content += `[${timestamp}] ${role}:\n`;
            content += `${message.content}\n\n`;
            content += '-'.repeat(60) + '\n\n';
        }

        return content;
    }

    generateTitle(firstMessage) {
        // Take first 50 characters and clean up
        let title = firstMessage.substring(0, 50).trim();
        
        // Remove line breaks and extra spaces
        title = title.replace(/\s+/g, ' ');
        
        // Add ellipsis if truncated
        if (firstMessage.length > 50) {
            title += '...';
        }

        return title;
    }

    async getConversationStats() {
        const conversations = await this.listConversations();
        const stats = {
            total: conversations.length,
            providers: {},
            totalMessages: 0,
            lastActivity: null
        };

        for (const conv of conversations) {
            // Count by provider
            stats.providers[conv.provider] = (stats.providers[conv.provider] || 0) + 1;
            
            // Count total messages
            stats.totalMessages += conv.messageCount;
            
            // Track last activity
            if (!stats.lastActivity || new Date(conv.date) > new Date(stats.lastActivity)) {
                stats.lastActivity = conv.date;
            }
        }

        return stats;
    }
}
