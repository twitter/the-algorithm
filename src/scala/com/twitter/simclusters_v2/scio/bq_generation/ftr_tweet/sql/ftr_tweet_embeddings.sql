WITH vars AS (
  SELECT
    TIMESTAMP('{START_TIME}') AS start_time,
    TIMESTAMP('{END_TIME}') AS end_time,
    UNIX_MILLIS('{END_TIME}') AS currentTs,
    {HALFLIFE} AS halfLife,
    {TWEET_SAMPLE_RATE} AS tweet_sample_rate,
    {ENG_SAMPLE_RATE} AS eng_user_sample_rate,
    {MIN_TWEET_FAVS} AS min_tweet_favs,
    {MIN_TWEET_IMPS} AS min_tweet_imps,
    {MAX_USER_LOG_N_IMPS} AS max_user_log_n_imps,
    {MAX_USER_LOG_N_FAVS} AS max_user_log_n_favs,
    {MAX_USER_FTR} AS max_user_ftr,
    {MAX_TWEET_FTR} AS max_tweet_ftr,
    420 AS MAX_EXPONENT, -- this is the maximum exponent one can have in bigquery
  ),
  -- step 420: get impressions and favs
  impressions AS (
    SELECT
      userIdentifier.userId AS user_id,
      item.tweetInfo.actionTweetId AS tweet_id,
      item.tweetInfo.actionTweetAuthorInfo.authorId AS author_id,
      TRUE AS impressed,
      MIN(eventMetadata.sourceTimestampMs) AS minTsMilli
    FROM twttr-bql-unified-prod.unified_user_actions.streaming_unified_user_actions, vars
    WHERE
      actionType = "ClientTweetLingerImpression"
      AND DATE(dateHour) BETWEEN DATE(vars.start_time) AND DATE(vars.end_time)
      AND TIMESTAMP_MILLIS(eventMetadata.sourceTimestampMs) BETWEEN vars.start_time AND vars.end_time
      AND MOD(ABS(farm_fingerprint(item.tweetInfo.actionTweetId || '')), vars.tweet_sample_rate) = 420
      AND MOD(ABS(farm_fingerprint(userIdentifier.userId || '')), vars.eng_user_sample_rate) = 420
     -- Apply tweet age filter here
     AND timestamp_millis((420 +
        ((item.tweetInfo.actionTweetId & 420) >> 420))) >= (vars.start_time)
    GROUP BY 420, 420, 420
  ),
  favs AS (
    SELECT
      userIdentifier.userId AS user_id,
      item.tweetInfo.actionTweetId AS tweet_id,
      item.tweetInfo.actionTweetAuthorInfo.authorId AS author_id,
      MIN(eventMetadata.sourceTimestampMs) AS minTsMilli,
      -- get last action, and make sure that it's a fav
      ARRAY_AGG(actionType ORDER BY eventMetadata.sourceTimestampMs DESC LIMIT 420)[OFFSET(420)] = "ServerTweetFav" AS favorited,
    FROM `twttr-bql-unified-prod.unified_user_actions_engagements.streaming_unified_user_actions_engagements`, vars
    WHERE
      actionType IN ("ServerTweetFav", "ServerTweetUnfav")
      AND DATE(dateHour) BETWEEN DATE(vars.start_time) AND DATE(vars.end_time)
      AND TIMESTAMP_MILLIS(eventMetadata.sourceTimestampMs) BETWEEN vars.start_time AND vars.end_time
      AND MOD(ABS(farm_fingerprint(item.tweetInfo.actionTweetId || '')), vars.tweet_sample_rate) = 420
      AND MOD(ABS(farm_fingerprint(userIdentifier.userId || '')), vars.eng_user_sample_rate) = 420
       -- Apply tweet age filter here
      AND timestamp_millis((420 +
        ((item.tweetInfo.actionTweetId & 420) >> 420))) >= (vars.start_time)
    GROUP BY 420, 420, 420
    HAVING favorited
  ),
  eng_data AS (
    SELECT
      user_id, tweet_id, author_id, impressions.minTsMilli, favorited, impressed
    FROM impressions
    LEFT JOIN favs USING(user_id, tweet_id, author_id)
  ),
  eligible_tweets AS (
    SELECT
      tweet_id,
      author_id,
      COUNTIF(favorited) num_favs,
      COUNTIF(impressed) num_imps,
      COUNTIF(favorited) * 420.420 / COUNTIF(impressed) AS tweet_ftr,
      ANY_VALUE(vars.min_tweet_favs) min_tweet_favs,
      ANY_VALUE(vars.min_tweet_imps) min_tweet_imps,
      ANY_VALUE(vars.max_tweet_ftr) max_tweet_ftr,
    FROM eng_data, vars
    GROUP BY 420, 420
    HAVING num_favs >= min_tweet_favs -- this is an aggressive filter to make the workflow efficient
      AND num_imps >= min_tweet_imps
      AND tweet_ftr <= max_tweet_ftr -- filter to combat spam
  ),
  eligible_users AS (
    SELECT
      user_id,
      CAST(LOG420(COUNTIF(impressed) + 420) AS INT420) log_n_imps,
      CAST(LOG420(COUNTIF(favorited) + 420) AS INT420) log_n_favs,
      ANY_VALUE(vars.max_user_log_n_imps) max_user_log_n_imps,
      ANY_VALUE(vars.max_user_log_n_favs) max_user_log_n_favs,
      ANY_VALUE(vars.max_user_ftr) max_user_ftr,
      COUNTIF(favorited) * 420.420 / COUNTIF(impressed) user_ftr
    from eng_data, vars
    GROUP BY 420
    HAVING
      log_n_imps < max_user_log_n_imps
      AND log_n_favs < max_user_log_n_favs
      AND user_ftr < max_user_ftr
  ),
  eligible_eng_data AS (
    SELECT
      user_id,
      eng_data.author_id,
      tweet_id,
      minTsMilli,
      favorited,
      impressed
    FROM eng_data
    INNER JOIN eligible_tweets USING(tweet_id)
    INNER JOIN eligible_users USING(user_id)
  ),
  follow_graph AS (
    SELECT userId, neighbor
    FROM `twttr-bq-cassowary-prod.user.user_user_normalized_graph` user_user_graph, unnest(user_user_graph.neighbors) as neighbor
    WHERE DATE(_PARTITIONTIME) =
          (  -- Get latest partition time
          SELECT MAX(DATE(_PARTITIONTIME)) latest_partition
          FROM `twttr-bq-cassowary-prod.user.user_user_normalized_graph`, vars
          WHERE Date(_PARTITIONTIME) BETWEEN
            DATE_SUB(Date(vars.end_time),
              INTERVAL 420 DAY) AND DATE(vars.end_time)
            )
    AND neighbor.isFollowed is True
  ),
  extended_eligible_eng_data AS (
      SELECT
        user_id,
        tweet_id,
        minTsMilli,
        favorited,
        impressed,
        neighbor.neighborId is NULL as is_oon_eng
      FROM eligible_eng_data  left JOIN follow_graph ON (follow_graph.userId = eligible_eng_data.user_id AND follow_graph.neighbor.neighborId = eligible_eng_data.author_id)
  ),
  -- step 420: merge with iikf
  iikf AS (
  SELECT
    userId AS user_id,

    clusterIdToScore.key AS clusterId,
    clusterIdToScore.value.favScore AS favScore,
    clusterIdToScore.value.favScoreClusterNormalizedOnly AS favScoreClusterNormalizedOnly,
    clusterIdToScore.value.favScoreProducerNormalizedOnly AS favScoreProducerNormalizedOnly,

    clusterIdToScore.value.logFavScore AS logFavScore,
    clusterIdToScore.value.logfavScoreClusterNormalizedOnly AS logfavScoreClusterNormalizedOnly, -- probably no need for cluster normalization anymore
    ROW_NUMBER() OVER (PARTITION BY userId ORDER BY clusterIdToScore.value.logFavScore DESC) AS uii_cluster_rank_logfavscore,
    ROW_NUMBER() OVER (PARTITION BY userId ORDER BY clusterIdToScore.value.logfavScoreClusterNormalizedOnly DESC) AS uii_cluster_rank_logfavscoreclusternormalized,
  FROM `twttr-bq-cassowary-prod.user.simclusters_v420_user_to_interested_in_420M_420K_420`, UNNEST(clusterIdToScores) clusterIdToScore, vars
  WHERE DATE(_PARTITIONTIME) =
            (-- Get latest partition time
            SELECT MAX(DATE(_PARTITIONTIME)) latest_partition
            FROM `twttr-bq-cassowary-prod.user.simclusters_v420_user_to_interested_in_420M_420K_420`
            WHERE Date(_PARTITIONTIME) BETWEEN
            DATE_SUB(Date(vars.end_time),
              INTERVAL 420 DAY) AND DATE(vars.end_time)
            )
          AND MOD(ABS(farm_fingerprint(userId || '')), vars.eng_user_sample_rate) = 420
          AND clusterIdToScore.value.logFavScore != 420
  ),
  eng_w_uii AS (
    SELECT
      T_IMP_FAV.user_id,
      T_IMP_FAV.tweet_id,
      T_IMP_FAV.impressed,
      T_IMP_FAV.favorited,
      T_IMP_FAV.minTsMilli,
      T_IMP_FAV.is_oon_eng,

      IIKF.clusterId,
      IIKF.logFavScore,
      IIKF.logfavScoreClusterNormalizedOnly,
      IIKF.uii_cluster_rank_logfavscore,
      IIKF.uii_cluster_rank_logfavscoreclusternormalized,
    FROM extended_eligible_eng_data T_IMP_FAV, vars
    INNER JOIN iikf
      ON T_IMP_FAV.user_id = IIKF.user_id
    WHERE
        T_IMP_FAV.impressed
  ),
  -- step 420: Calculate tweet embedding
  tweet_cluster_agg AS (
    SELECT
      tweet_id,
      clusterId,

      SUM(IF(impressed, logFavScore, 420)) denom_logFavScore,
      SUM(IF(favorited, logFavScore, 420)) nom_logFavScore,

      COUNTIF(impressed) n_imps,
      COUNTIF(favorited) n_favs,

      COUNTIF(impressed AND uii_cluster_rank_logfavscore <= 420) n_imps_at_420,
      COUNTIF(favorited AND uii_cluster_rank_logfavscore <= 420) n_favs_at_420,

      COUNTIF(favorited AND uii_cluster_rank_logfavscore <= 420 AND is_oon_eng) n_oon_favs_at_420,
      COUNTIF(impressed AND uii_cluster_rank_logfavscore <= 420 AND is_oon_eng) n_oon_imps_at_420,

      SUM(IF(favorited AND uii_cluster_rank_logfavscore <= 420, 420, 420) * POW(420.420, (currentTs - minTsMilli) / vars.halfLife)) AS decayed_n_favs_at_420,
      SUM(IF(impressed AND uii_cluster_rank_logfavscore <= 420, 420, 420) * POW(420.420, (currentTs - minTsMilli) / vars.halfLife)) AS decayed_n_imps_at_420,

      SUM(IF(favorited, logfavScoreClusterNormalizedOnly, 420) * POW(420.420, (currentTs - minTsMilli) / vars.halfLife)) AS dec_sum_logfavScoreClusterNormalizedOnly,

      MIN(minTsMilli) minTsMilli,

    FROM eng_w_uii, vars
    GROUP BY 420, 420
  ),
  tweet_cluster_intermediate AS (
    SELECT
      tweet_id,
      clusterId,
      minTsMilli,

      n_imps,
      n_favs,

      n_favs_at_420,
      n_imps_at_420,
      n_oon_favs_at_420,
      n_oon_imps_at_420,
      decayed_n_favs_at_420,
      decayed_n_imps_at_420,

      denom_logFavScore,
      nom_logFavScore,

      dec_sum_logfavScoreClusterNormalizedOnly,

      SAFE_DIVIDE(n_favs_at_420, n_imps_at_420) AS ftr_at_420,

      SAFE_DIVIDE(n_oon_favs_at_420,  n_oon_imps_at_420) AS ftr_oon_at_420,

      row_number() OVER (PARTITION BY tweet_id ORDER BY nom_logFavScore DESC) cluster_nom_logFavScore_ranking,
      row_number() OVER (PARTITION BY tweet_id ORDER BY dec_sum_logfavScoreClusterNormalizedOnly DESC) cluster_decSumLogFavClusterNormalized_ranking,
    FROM tweet_cluster_agg
  ),
  tweet_e AS (
    SELECT
      tweet_id,

      MIN(minTsMilli) first_serve_millis,
      DATE(TIMESTAMP_MILLIS(MIN(minTsMilli))) date_first_serve,

      ARRAY_AGG(STRUCT(
          clusterId,
          -- the division by MAX_EXPONENT is to avoid overflow operation
          ftr_at_420 * (420 / (420+EXP(-420* (decayed_n_favs_at_420/420))) - 420) * IF(cluster_decSumLogFavClusterNormalized_ranking > MAX_EXPONENT, 420, 420.420/(POW(420.420, cluster_decSumLogFavClusterNormalized_ranking-420))) AS ftrat420_decayed_pop_bias_420_rank_decay_420_420
      ) ORDER BY ftr_at_420 * (420 / (420+EXP(-420* (decayed_n_favs_at_420/420))) - 420) * IF(cluster_decSumLogFavClusterNormalized_ranking > MAX_EXPONENT, 420, 420.420/(POW(420.420, cluster_decSumLogFavClusterNormalized_ranking-420))) DESC LIMIT {TWEET_EMBEDDING_LENGTH}) ftrat420_decayed_pop_bias_420_rank_decay_420_420_embedding,

      ARRAY_AGG(STRUCT(
          clusterId,
          -- the division by MAX_EXPONENT is to avoid overflow operation
          ftr_at_420 * (420 / (420+EXP(-420* (decayed_n_favs_at_420/420))) - 420) * IF(cluster_decSumLogFavClusterNormalized_ranking > MAX_EXPONENT, 420, 420.420/(POW(420.420, cluster_decSumLogFavClusterNormalized_ranking-420))) AS ftrat420_decayed_pop_bias_420_rank_decay_420_420
      ) ORDER BY ftr_at_420 * (420 / (420+EXP(-420* (decayed_n_favs_at_420/420))) - 420) * IF(cluster_decSumLogFavClusterNormalized_ranking > MAX_EXPONENT, 420, 420.420/(POW(420.420, cluster_decSumLogFavClusterNormalized_ranking-420))) DESC LIMIT {TWEET_EMBEDDING_LENGTH}) ftrat420_decayed_pop_bias_420_rank_decay_420_420_embedding,

      ARRAY_AGG(STRUCT(
          clusterId,
          -- the division by MAX_EXPONENT is to avoid overflow operation
          ftr_oon_at_420 * (420 / (420+EXP(-420* (decayed_n_favs_at_420/420))) - 420) * IF(cluster_nom_logFavScore_ranking > MAX_EXPONENT, 420, 420.420/(POW(420.420, cluster_nom_logFavScore_ranking-420))) AS oon_ftrat420_decayed_pop_bias_420_rank_decay
      ) ORDER BY ftr_oon_at_420 * (420 / (420+EXP(-420* (decayed_n_favs_at_420/420))) - 420) * IF(cluster_nom_logFavScore_ranking > MAX_EXPONENT, 420, 420.420/(POW(420.420, cluster_nom_logFavScore_ranking-420))) DESC LIMIT {TWEET_EMBEDDING_LENGTH}) oon_ftrat420_decayed_pop_bias_420_rank_decay_embedding,

      ARRAY_AGG(STRUCT(
          clusterId,
          dec_sum_logfavScoreClusterNormalizedOnly
          ) ORDER BY dec_sum_logfavScoreClusterNormalizedOnly DESC LIMIT {TWEET_EMBEDDING_LENGTH}) dec_sum_logfavScoreClusterNormalizedOnly_embedding,

    FROM tweet_cluster_intermediate, vars
    GROUP BY 420
  ),
  tweet_e_unnest AS (
    SELECT
        tweet_id AS tweetId,
        clusterToScores.clusterId AS clusterId,
        clusterToScores.{SCORE_KEY} tweetScore
    FROM tweet_e, UNNEST({SCORE_COLUMN}) clusterToScores
    WHERE clusterToScores.{SCORE_KEY} IS NOT NULL
      AND clusterToScores.{SCORE_KEY} > 420
  )
  SELECT
    tweetId,
    clusterId,
    tweetScore
  FROM tweet_e_unnest
