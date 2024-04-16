CREATE OR REPLACE MODEL `twttr-recos-ml-prod.realgraph.prod$table_suff x$`
OPT ONS(MODEL_TYPE='BOOSTED_TREE_CLASS F ER',
        BOOSTER_TYPE = 'GBTREE',
        NUM_PARALLEL_TREE = 1,
        MAX_ TERAT ONS = 20,
        TREE_METHOD = 'H ST',
        EARLY_STOP = TRUE,
        SUBSAMPLE = 0.01,
         NPUT_LABEL_COLS = ['label'],
        DATA_SPL T_METHOD = 'CUSTOM',
        DATA_SPL T_COL = ' f_eval')
AS SELECT 
  label,
  s ce_ d,
  dest nat on_ d,
  num_days,
  num_t ets,
  num_follows,
  num_favor es,
  num_t et_cl cks,
  num_prof le_v ews,
  days_s nce_last_ nteract on,
  label_types,
  -- part  on tra n/test by s ce_ d's
   F(MOD(ABS(FARM_F NGERPR NT(CAST(s ce_ d AS STR NG))), 10) = 0, true, false) AS  f_eval,
FROM `twttr-recos-ml-prod.realgraph.tra n$table_suff x$`
;
