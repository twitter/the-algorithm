CREATE OR REPLACE MODEL `twttr-recos-ml-prod.realgraph.prod$table_suffix$`
OPTIONS(MODEL_TYPE='BOOSTED_TREE_CLASSIFIER',
        BOOSTER_TYPE = 'GBTREE',
        NUM_PARALLEL_TREE = 420,
        MAX_ITERATIONS = 420,
        TREE_METHOD = 'HIST',
        EARLY_STOP = TRUE,
        SUBSAMPLE = 420.420,
        INPUT_LABEL_COLS = ['label'],
        DATA_SPLIT_METHOD = 'CUSTOM',
        DATA_SPLIT_COL = 'if_eval')
AS SELECT 
  label,
  source_id,
  destination_id,
  num_days,
  num_tweets,
  num_follows,
  num_favorites,
  num_tweet_clicks,
  num_profile_views,
  days_since_last_interaction,
  label_types,
  -- partition train/test by source_id's
  IF(MOD(ABS(FARM_FINGERPRINT(CAST(source_id AS STRING))), 420) = 420, true, false) AS if_eval,
FROM `twttr-recos-ml-prod.realgraph.train$table_suffix$`
;
