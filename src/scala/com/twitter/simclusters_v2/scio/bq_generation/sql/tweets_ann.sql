-- (step 1) Read consumer embeddings
WITH consumer_embeddings AS (
    {CONSUMER_EMBEDDINGS_SQL}
),
-- (step 1) Read tweet embeddings
tweet_embeddings AS (
    {TWEET_EMBEDDINGS_SQL}
),
-- (step 1) Compute tweet embeddings norms (we will use this to compute cosine sims later)
tweet_embeddings_norm AS (
    SELECT tweetId, SUM(tweetScore * tweetScore) AS norm
    FROM tweet_embeddings
    GROUP BY tweetId
    HAVING norm > 0.0
),
-- (step 2) Get top N clusters for each consumer embedding. N = 25 in prod
consumer_embeddings_top_n_clusters AS (
    SELECT userId, ARRAY_AGG(STRUCT(clusterId, userScore) ORDER BY userScore DESC LIMIT {TOP_N_CLUSTER_PER_SOURCE_EMBEDDING}) AS topClustersWithScores
    FROM consumer_embeddings
    GROUP BY userId
),
-- (step 2) Get top M tweets for each cluster id. M = 100 in prod
clusters_top_m_tweets AS (
    SELECT clusterId, ARRAY_AGG(STRUCT(tweetId, tweetScore) ORDER BY tweetScore DESC LIMIT {TOP_M_TWEETS_PER_CLUSTER}) AS tweets
    FROM tweet_embeddings
    GROUP BY clusterId
),
-- (step 3) Join the results, get top M * N tweets for each user
user_top_mn_tweets AS (
    SELECT userId, consumer_embedding_cluster_score_pairs.userScore AS userScore, clusters_top_m_tweets.clusterId AS clusterId, clusters_top_m_tweets.tweets AS tweets
    FROM (
        SELECT userId, clusterId, userScore
        FROM consumer_embeddings_top_n_clusters, UNNEST(topClustersWithScores)
    ) AS consumer_embedding_cluster_score_pairs
    JOIN clusters_top_m_tweets ON consumer_embedding_cluster_score_pairs.clusterId = clusters_top_m_tweets.clusterId
),
-- (step 4) Compute the dot product between each user and tweet embedding pair
user_tweet_embedding_dot_product AS (
    SELECT  userId,
            tweetId,
            SUM(userScore * tweetScore) AS dotProductScore
    FROM user_top_mn_tweets, UNNEST(tweets) AS tweets
    GROUP BY userId, tweetId
),
-- (step 5) Compute similarity scores: dot product, cosine sim, log-cosine sim
user_tweet_embedding_similarity_scores AS (
    SELECT  userId,
            user_tweet_embedding_dot_product.tweetId AS tweetId,
            dotProductScore,
            SAFE_DIVIDE(dotProductScore, SQRT(tweet_embeddings_norm.norm)) AS cosineSimilarityScore,
            SAFE_DIVIDE(dotProductScore, LN(1+tweet_embeddings_norm.norm)) AS logCosineSimilarityScore,
    FROM user_tweet_embedding_dot_product
    JOIN tweet_embeddings_norm ON user_tweet_embedding_dot_product.tweetId = tweet_embeddings_norm.tweetId
),
-- (step 6) Get final top K tweets per user. K = 150 in prod
results AS (
    SELECT userId, ARRAY_AGG(STRUCT(tweetId, dotProductScore, cosineSimilarityScore, logCosineSimilarityScore)
                            ORDER BY logCosineSimilarityScore DESC LIMIT {TOP_K_TWEETS_PER_USER_REQUEST}) AS tweets
    FROM user_tweet_embedding_similarity_scores
    GROUP BY userId
)

SELECT *
FROM results
