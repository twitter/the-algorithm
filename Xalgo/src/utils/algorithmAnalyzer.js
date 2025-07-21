import fs from 'fs-extra';
import path from 'path';
import { spawn } from 'child_process';

export class AlgorithmAnalyzer {
    constructor() {
        this.repoPath = process.cwd();
        this.knowledgeBase = new Map();
        this.indexBuilt = false;
    }

    async buildKnowledgeBase() {
        if (this.indexBuilt) return;
        
        console.log('Building algorithm knowledge base...');
        
        // Key algorithm components from the real X codebase
        const algorithmComponents = {
            'home-mixer': {
                path: 'home-mixer',
                description: 'Main service for constructing Home Timeline',
                keyFiles: ['ForYouTimelineScorerMixerPipelineConfig.scala', 'ScoredTweetsRecommendationPipelineConfig.scala'],
                purpose: 'Timeline construction, candidate mixing, ranking orchestration'
            },
            'cr-mixer': {
                path: 'cr-mixer',
                description: 'Content Recommender - candidate generation coordination',
                keyFiles: ['CrMixerThriftController.scala'],
                purpose: 'Out-of-network tweet candidate generation and coordination'
            },
            'follow-recommendations-service': {
                path: 'follow-recommendations-service',
                description: 'Follow Recommendations Service (FRS)',
                keyFiles: ['HomeTimelineProduct.scala'],
                purpose: 'User follow recommendations and future graph predictions'
            },
            'simclusters': {
                path: 'src/scala/com/twitter/simclusters_v2',
                description: 'SimClusters - community detection and embeddings',
                keyFiles: ['OfflineTweetRecommendation.scala'],
                purpose: 'Community-based recommendations and embeddings'
            },
            'earlybird-search': {
                path: 'src/java/com/twitter/search',
                description: 'Earlybird - real-time search and in-network retrieval',
                keyFiles: [],
                purpose: 'In-network tweet retrieval and text search'
            },
            'timelineranker': {
                path: 'timelineranker',
                description: 'Legacy timeline ranking service',
                keyFiles: [],
                purpose: 'Relevance scoring from search index and UTEG'
            },
            'user-tweet-entity-graph': {
                path: 'src/scala/com/twitter/recos/user_tweet_entity_graph',
                description: 'UTEG - user-tweet interaction graph',
                keyFiles: ['TweetRecommendationsRunner.scala'],
                purpose: 'Graph-based recommendations using GraphJet framework'
            },
            'pushservice': {
                path: 'pushservice',
                description: 'Notification recommendations',
                keyFiles: ['ContentRecommenderMixerAdaptor.scala'],
                purpose: 'Push notification candidate generation and ranking'
            },
            'trust-and-safety': {
                path: 'trust_and_safety_models',
                description: 'Content safety and filtering',
                keyFiles: ['nsfw_media.py', 'toxicity/load_model.py'],
                purpose: 'NSFW detection, toxicity scoring, abuse prevention'
            }
        };

        for (const [component, info] of Object.entries(algorithmComponents)) {
            this.knowledgeBase.set(component, {
                ...info,
                files: await this.scanComponentFiles(info.path)
            });
        }

        this.indexBuilt = true;
        console.log(`Knowledge base built with ${this.knowledgeBase.size} components`);
    }

    async scanComponentFiles(componentPath) {
        const fullPath = path.join(this.repoPath, componentPath);
        const files = [];
        
        try {
            if (await fs.pathExists(fullPath)) {
                await this.walkDirectory(fullPath, componentPath, files);
            }
        } catch (error) {
            console.warn(`Could not scan ${componentPath}: ${error.message}`);
        }
        
        return files.slice(0, 50); // Limit to prevent overwhelming responses
    }

    async walkDirectory(dirPath, relativePath, files, depth = 0) {
        if (depth > 5) return; // Prevent too deep recursion
        
        try {
            const items = await fs.readdir(dirPath);
            
            for (const item of items) {
                const itemPath = path.join(dirPath, item);
                const relativeItemPath = path.join(relativePath, item);
                const stat = await fs.stat(itemPath);
                
                if (stat.isDirectory() && !this.shouldSkipDirectory(item)) {
                    await this.walkDirectory(itemPath, relativeItemPath, files, depth + 1);
                } else if (stat.isFile() && this.isRelevantFile(item)) {
                    files.push({
                        path: relativeItemPath,
                        name: item,
                        type: this.getFileType(item)
                    });
                }
            }
        } catch (error) {
            // Skip directories we can't read
        }
    }

    shouldSkipDirectory(dirname) {
        const skipDirs = ['target', 'build', '.git', 'node_modules', 'dist', 'out'];
        return skipDirs.includes(dirname);
    }

    isRelevantFile(filename) {
        const extensions = ['.scala', '.py', '.java', '.md', '.thrift', '.sql'];
        return extensions.some(ext => filename.endsWith(ext));
    }

    getFileType(filename) {
        if (filename.endsWith('.scala')) return 'scala';
        if (filename.endsWith('.py')) return 'python';
        if (filename.endsWith('.java')) return 'java';
        if (filename.endsWith('.md')) return 'markdown';
        if (filename.endsWith('.thrift')) return 'thrift';
        if (filename.endsWith('.sql')) return 'sql';
        return 'other';
    }

    async analyzeQuery(query) {
        await this.buildKnowledgeBase();
        
        const analysis = {
            relevantComponents: [],
            suggestedQuestions: [],
            codeReferences: [],
            algorithmConcepts: []
        };

        // Identify relevant components based on query
        const queryLower = query.toLowerCase();
        
        for (const [component, info] of this.knowledgeBase.entries()) {
            const relevanceScore = this.calculateRelevance(queryLower, component, info);
            if (relevanceScore > 0) {
                analysis.relevantComponents.push({
                    component,
                    info,
                    relevance: relevanceScore
                });
            }
        }

        // Sort by relevance
        analysis.relevantComponents.sort((a, b) => b.relevance - a.relevance);

        // Generate algorithm concepts
        analysis.algorithmConcepts = this.extractAlgorithmConcepts(queryLower);

        // Suggest follow-up questions
        analysis.suggestedQuestions = this.generateSuggestedQuestions(queryLower, analysis.relevantComponents);

        return analysis;
    }

    calculateRelevance(query, component, info) {
        let score = 0;
        
        // Direct component name match
        if (query.includes(component) || query.includes(component.replace('-', ''))) {
            score += 10;
        }
        
        // Description keywords
        const descWords = info.description.toLowerCase().split(' ');
        const queryWords = query.split(' ');
        
        for (const qWord of queryWords) {
            if (qWord.length > 3) { // Skip short words
                for (const dWord of descWords) {
                    if (dWord.includes(qWord) || qWord.includes(dWord)) {
                        score += 2;
                    }
                }
            }
        }
        
        // Purpose keywords
        const purposeWords = info.purpose.toLowerCase().split(' ');
        for (const qWord of queryWords) {
            if (qWord.length > 3) {
                for (const pWord of purposeWords) {
                    if (pWord.includes(qWord) || qWord.includes(pWord)) {
                        score += 3;
                    }
                }
            }
        }

        return score;
    }

    extractAlgorithmConcepts(query) {
        const concepts = [];
        const conceptMap = {
            'timeline': ['home timeline', 'for you timeline', 'following timeline', 'timeline ranking'],
            'recommendation': ['content recommendation', 'user recommendations', 'follow recommendations'],
            'ranking': ['machine learning ranking', 'scoring algorithms', 'candidate ranking'],
            'candidate': ['candidate generation', 'candidate sourcing', 'candidate pipelines'],
            'graph': ['social graph', 'user-tweet graph', 'interaction graph', 'follow graph'],
            'embedding': ['simclusters embeddings', 'user embeddings', 'tweet embeddings'],
            'search': ['tweet search', 'earlybird search', 'real-time search'],
            'filtering': ['content filtering', 'safety filtering', 'visibility filtering'],
            'scoring': ['relevance scoring', 'engagement scoring', 'quality scoring']
        };

        for (const [concept, variations] of Object.entries(conceptMap)) {
            if (query.includes(concept)) {
                concepts.push(...variations);
            }
        }

        return [...new Set(concepts)]; // Remove duplicates
    }

    generateSuggestedQuestions(query, relevantComponents) {
        const questions = [];
        
        if (relevantComponents.length > 0) {
            const topComponent = relevantComponents[0];
            const compName = topComponent.component;
            
            questions.push(
                `How does ${compName} integrate with other services?`,
                `What are the key algorithms used in ${compName}?`,
                `How is performance optimized in ${compName}?`,
                `What machine learning models does ${compName} use?`
            );
        }

        // Generic algorithm questions based on query content
        if (query.includes('timeline')) {
            questions.push(
                'How does tweet ranking work in the timeline?',
                'What features are used for timeline scoring?',
                'How does the algorithm balance in-network vs out-of-network content?'
            );
        }

        if (query.includes('recommendation')) {
            questions.push(
                'How does the follow recommendation algorithm work?',
                'What signals are used for content recommendations?',
                'How does the system handle cold start problems?'
            );
        }

        return questions.slice(0, 6); // Limit to 6 suggestions
    }

    getComponentSummary(componentName) {
        const info = this.knowledgeBase.get(componentName);
        if (!info) return null;

        return {
            name: componentName,
            description: info.description,
            purpose: info.purpose,
            fileCount: info.files.length,
            keyFiles: info.keyFiles,
            sampleFiles: info.files.slice(0, 10)
        };
    }

    getAllComponents() {
        const components = [];
        for (const [name, info] of this.knowledgeBase.entries()) {
            components.push({
                name,
                description: info.description,
                purpose: info.purpose,
                fileCount: info.files.length
            });
        }
        return components;
    }
}
