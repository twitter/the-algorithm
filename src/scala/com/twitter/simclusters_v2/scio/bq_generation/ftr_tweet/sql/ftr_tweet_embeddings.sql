W TH vars AS (
  SELECT
    T MESTAMP('{START_T ME}') AS start_t  ,
    T MESTAMP('{END_T ME}') AS end_t  ,
    UN X_M LL S('{END_T ME}') AS currentTs,
    {HALFL FE} AS halfL fe,
    {TWEET_SAMPLE_RATE} AS t et_sample_rate,
    {ENG_SAMPLE_RATE} AS eng_user_sample_rate,
    {M N_TWEET_FAVS} AS m n_t et_favs,
    {M N_TWEET_ MPS} AS m n_t et_ mps,
    {MAX_USER_LOG_N_ MPS} AS max_user_log_n_ mps,
    {MAX_USER_LOG_N_FAVS} AS max_user_log_n_favs,
    {MAX_USER_FTR} AS max_user_ftr,
    {MAX_TWEET_FTR} AS max_t et_ftr,
    700 AS MAX_EXPONENT, -- t   s t  max mum exponent one can have  n b gquery
  ),
  -- step 1: get  mpress ons and favs
   mpress ons AS (
    SELECT
      user dent f er.user d AS user_ d,
       em.t et nfo.act onT et d AS t et_ d,
       em.t et nfo.act onT etAuthor nfo.author d AS author_ d,
      TRUE AS  mpressed,
      M N(event tadata.s ceT  stampMs) AS m nTsM ll 
    FROM twttr-bql-un f ed-prod.un f ed_user_act ons.stream ng_un f ed_user_act ons, vars
    WHERE
      act onType = "Cl entT etL nger mpress on"
      AND DATE(dateH ) BETWEEN DATE(vars.start_t  ) AND DATE(vars.end_t  )
      AND T MESTAMP_M LL S(event tadata.s ceT  stampMs) BETWEEN vars.start_t   AND vars.end_t  
      AND MOD(ABS(farm_f ngerpr nt( em.t et nfo.act onT et d || '')), vars.t et_sample_rate) = 0
      AND MOD(ABS(farm_f ngerpr nt(user dent f er.user d || '')), vars.eng_user_sample_rate) = 0
     -- Apply t et age f lter  re
     AND t  stamp_m ll s((1288834974657 +
        (( em.t et nfo.act onT et d & 9223372036850581504) >> 22))) >= (vars.start_t  )
    GROUP BY 1, 2, 3
  ),
  favs AS (
    SELECT
      user dent f er.user d AS user_ d,
       em.t et nfo.act onT et d AS t et_ d,
       em.t et nfo.act onT etAuthor nfo.author d AS author_ d,
      M N(event tadata.s ceT  stampMs) AS m nTsM ll ,
      -- get last act on, and make sure that  's a fav
      ARRAY_AGG(act onType ORDER BY event tadata.s ceT  stampMs DESC L M T 1)[OFFSET(0)] = "ServerT etFav" AS favor ed,
    FROM `twttr-bql-un f ed-prod.un f ed_user_act ons_engage nts.stream ng_un f ed_user_act ons_engage nts`, vars
    WHERE
      act onType  N ("ServerT etFav", "ServerT etUnfav")
      AND DATE(dateH ) BETWEEN DATE(vars.start_t  ) AND DATE(vars.end_t  )
      AND T MESTAMP_M LL S(event tadata.s ceT  stampMs) BETWEEN vars.start_t   AND vars.end_t  
      AND MOD(ABS(farm_f ngerpr nt( em.t et nfo.act onT et d || '')), vars.t et_sample_rate) = 0
      AND MOD(ABS(farm_f ngerpr nt(user dent f er.user d || '')), vars.eng_user_sample_rate) = 0
       -- Apply t et age f lter  re
      AND t  stamp_m ll s((1288834974657 +
        (( em.t et nfo.act onT et d & 9223372036850581504) >> 22))) >= (vars.start_t  )
    GROUP BY 1, 2, 3
    HAV NG favor ed
  ),
  eng_data AS (
    SELECT
      user_ d, t et_ d, author_ d,  mpress ons.m nTsM ll , favor ed,  mpressed
    FROM  mpress ons
    LEFT JO N favs US NG(user_ d, t et_ d, author_ d)
  ),
  el g ble_t ets AS (
    SELECT
      t et_ d,
      author_ d,
      COUNT F(favor ed) num_favs,
      COUNT F( mpressed) num_ mps,
      COUNT F(favor ed) * 1.0 / COUNT F( mpressed) AS t et_ftr,
      ANY_VALUE(vars.m n_t et_favs) m n_t et_favs,
      ANY_VALUE(vars.m n_t et_ mps) m n_t et_ mps,
      ANY_VALUE(vars.max_t et_ftr) max_t et_ftr,
    FROM eng_data, vars
    GROUP BY 1, 2
    HAV NG num_favs >= m n_t et_favs -- t   s an aggress ve f lter to make t  workflow eff c ent
      AND num_ mps >= m n_t et_ mps
      AND t et_ftr <= max_t et_ftr -- f lter to combat spam
  ),
  el g ble_users AS (
    SELECT
      user_ d,
      CAST(LOG10(COUNT F( mpressed) + 1) AS  NT64) log_n_ mps,
      CAST(LOG10(COUNT F(favor ed) + 1) AS  NT64) log_n_favs,
      ANY_VALUE(vars.max_user_log_n_ mps) max_user_log_n_ mps,
      ANY_VALUE(vars.max_user_log_n_favs) max_user_log_n_favs,
      ANY_VALUE(vars.max_user_ftr) max_user_ftr,
      COUNT F(favor ed) * 1.0 / COUNT F( mpressed) user_ftr
    from eng_data, vars
    GROUP BY 1
    HAV NG
      log_n_ mps < max_user_log_n_ mps
      AND log_n_favs < max_user_log_n_favs
      AND user_ftr < max_user_ftr
  ),
  el g ble_eng_data AS (
    SELECT
      user_ d,
      eng_data.author_ d,
      t et_ d,
      m nTsM ll ,
      favor ed,
       mpressed
    FROM eng_data
     NNER JO N el g ble_t ets US NG(t et_ d)
     NNER JO N el g ble_users US NG(user_ d)
  ),
  follow_graph AS (
    SELECT user d, ne ghbor
    FROM `twttr-bq-cassowary-prod.user.user_user_normal zed_graph` user_user_graph, unnest(user_user_graph.ne ghbors) as ne ghbor
    WHERE DATE(_PART T ONT ME) =
          (  -- Get latest part  on t  
          SELECT MAX(DATE(_PART T ONT ME)) latest_part  on
          FROM `twttr-bq-cassowary-prod.user.user_user_normal zed_graph`, vars
          WHERE Date(_PART T ONT ME) BETWEEN
            DATE_SUB(Date(vars.end_t  ),
               NTERVAL 14 DAY) AND DATE(vars.end_t  )
            )
    AND ne ghbor. sFollo d  s True
  ),
  extended_el g ble_eng_data AS (
      SELECT
        user_ d,
        t et_ d,
        m nTsM ll ,
        favor ed,
         mpressed,
        ne ghbor.ne ghbor d  s NULL as  s_oon_eng
      FROM el g ble_eng_data  left JO N follow_graph ON (follow_graph.user d = el g ble_eng_data.user_ d AND follow_graph.ne ghbor.ne ghbor d = el g ble_eng_data.author_ d)
  ),
  -- step 2:  rge w h   kf
    kf AS (
  SELECT
    user d AS user_ d,

    cluster dToScore.key AS cluster d,
    cluster dToScore.value.favScore AS favScore,
    cluster dToScore.value.favScoreClusterNormal zedOnly AS favScoreClusterNormal zedOnly,
    cluster dToScore.value.favScoreProducerNormal zedOnly AS favScoreProducerNormal zedOnly,

    cluster dToScore.value.logFavScore AS logFavScore,
    cluster dToScore.value.logfavScoreClusterNormal zedOnly AS logfavScoreClusterNormal zedOnly, -- probably no need for cluster normal zat on anymore
    ROW_NUMBER() OVER (PART T ON BY user d ORDER BY cluster dToScore.value.logFavScore DESC) AS u  _cluster_rank_logfavscore,
    ROW_NUMBER() OVER (PART T ON BY user d ORDER BY cluster dToScore.value.logfavScoreClusterNormal zedOnly DESC) AS u  _cluster_rank_logfavscoreclusternormal zed,
  FROM `twttr-bq-cassowary-prod.user.s mclusters_v2_user_to_ nterested_ n_20M_145K_2020`, UNNEST(cluster dToScores) cluster dToScore, vars
  WHERE DATE(_PART T ONT ME) =
            (-- Get latest part  on t  
            SELECT MAX(DATE(_PART T ONT ME)) latest_part  on
            FROM `twttr-bq-cassowary-prod.user.s mclusters_v2_user_to_ nterested_ n_20M_145K_2020`
            WHERE Date(_PART T ONT ME) BETWEEN
            DATE_SUB(Date(vars.end_t  ),
               NTERVAL 14 DAY) AND DATE(vars.end_t  )
            )
          AND MOD(ABS(farm_f ngerpr nt(user d || '')), vars.eng_user_sample_rate) = 0
          AND cluster dToScore.value.logFavScore != 0
  ),
  eng_w_u   AS (
    SELECT
      T_ MP_FAV.user_ d,
      T_ MP_FAV.t et_ d,
      T_ MP_FAV. mpressed,
      T_ MP_FAV.favor ed,
      T_ MP_FAV.m nTsM ll ,
      T_ MP_FAV. s_oon_eng,

        KF.cluster d,
        KF.logFavScore,
        KF.logfavScoreClusterNormal zedOnly,
        KF.u  _cluster_rank_logfavscore,
        KF.u  _cluster_rank_logfavscoreclusternormal zed,
    FROM extended_el g ble_eng_data T_ MP_FAV, vars
     NNER JO N   kf
      ON T_ MP_FAV.user_ d =   KF.user_ d
    WHERE
        T_ MP_FAV. mpressed
  ),
  -- step 3: Calculate t et embedd ng
  t et_cluster_agg AS (
    SELECT
      t et_ d,
      cluster d,

      SUM( F( mpressed, logFavScore, 0)) denom_logFavScore,
      SUM( F(favor ed, logFavScore, 0)) nom_logFavScore,

      COUNT F( mpressed) n_ mps,
      COUNT F(favor ed) n_favs,

      COUNT F( mpressed AND u  _cluster_rank_logfavscore <= 5) n_ mps_at_5,
      COUNT F(favor ed AND u  _cluster_rank_logfavscore <= 5) n_favs_at_5,

      COUNT F(favor ed AND u  _cluster_rank_logfavscore <= 5 AND  s_oon_eng) n_oon_favs_at_5,
      COUNT F( mpressed AND u  _cluster_rank_logfavscore <= 5 AND  s_oon_eng) n_oon_ mps_at_5,

      SUM( F(favor ed AND u  _cluster_rank_logfavscore <= 5, 1, 0) * POW(0.5, (currentTs - m nTsM ll ) / vars.halfL fe)) AS decayed_n_favs_at_5,
      SUM( F( mpressed AND u  _cluster_rank_logfavscore <= 5, 1, 0) * POW(0.5, (currentTs - m nTsM ll ) / vars.halfL fe)) AS decayed_n_ mps_at_5,

      SUM( F(favor ed, logfavScoreClusterNormal zedOnly, 0) * POW(0.5, (currentTs - m nTsM ll ) / vars.halfL fe)) AS dec_sum_logfavScoreClusterNormal zedOnly,

      M N(m nTsM ll ) m nTsM ll ,

    FROM eng_w_u  , vars
    GROUP BY 1, 2
  ),
  t et_cluster_ nter d ate AS (
    SELECT
      t et_ d,
      cluster d,
      m nTsM ll ,

      n_ mps,
      n_favs,

      n_favs_at_5,
      n_ mps_at_5,
      n_oon_favs_at_5,
      n_oon_ mps_at_5,
      decayed_n_favs_at_5,
      decayed_n_ mps_at_5,

      denom_logFavScore,
      nom_logFavScore,

      dec_sum_logfavScoreClusterNormal zedOnly,

      SAFE_D V DE(n_favs_at_5, n_ mps_at_5) AS ftr_at_5,

      SAFE_D V DE(n_oon_favs_at_5,  n_oon_ mps_at_5) AS ftr_oon_at_5,

      row_number() OVER (PART T ON BY t et_ d ORDER BY nom_logFavScore DESC) cluster_nom_logFavScore_rank ng,
      row_number() OVER (PART T ON BY t et_ d ORDER BY dec_sum_logfavScoreClusterNormal zedOnly DESC) cluster_decSumLogFavClusterNormal zed_rank ng,
    FROM t et_cluster_agg
  ),
  t et_e AS (
    SELECT
      t et_ d,

      M N(m nTsM ll ) f rst_serve_m ll s,
      DATE(T MESTAMP_M LL S(M N(m nTsM ll ))) date_f rst_serve,

      ARRAY_AGG(STRUCT(
          cluster d,
          -- t  d v s on by MAX_EXPONENT  s to avo d overflow operat on
          ftr_at_5 * (2 / (1+EXP(-1* (decayed_n_favs_at_5/1000))) - 1) *  F(cluster_decSumLogFavClusterNormal zed_rank ng > MAX_EXPONENT, 0, 1.0/(POW(1.1, cluster_decSumLogFavClusterNormal zed_rank ng-1))) AS ftrat5_decayed_pop_b as_1000_rank_decay_1_1
      ) ORDER BY ftr_at_5 * (2 / (1+EXP(-1* (decayed_n_favs_at_5/1000))) - 1) *  F(cluster_decSumLogFavClusterNormal zed_rank ng > MAX_EXPONENT, 0, 1.0/(POW(1.1, cluster_decSumLogFavClusterNormal zed_rank ng-1))) DESC L M T {TWEET_EMBEDD NG_LENGTH}) ftrat5_decayed_pop_b as_1000_rank_decay_1_1_embedd ng,

      ARRAY_AGG(STRUCT(
          cluster d,
          -- t  d v s on by MAX_EXPONENT  s to avo d overflow operat on
          ftr_at_5 * (2 / (1+EXP(-1* (decayed_n_favs_at_5/10000))) - 1) *  F(cluster_decSumLogFavClusterNormal zed_rank ng > MAX_EXPONENT, 0, 1.0/(POW(1.1, cluster_decSumLogFavClusterNormal zed_rank ng-1))) AS ftrat5_decayed_pop_b as_10000_rank_decay_1_1
      ) ORDER BY ftr_at_5 * (2 / (1+EXP(-1* (decayed_n_favs_at_5/1000))) - 1) *  F(cluster_decSumLogFavClusterNormal zed_rank ng > MAX_EXPONENT, 0, 1.0/(POW(1.1, cluster_decSumLogFavClusterNormal zed_rank ng-1))) DESC L M T {TWEET_EMBEDD NG_LENGTH}) ftrat5_decayed_pop_b as_10000_rank_decay_1_1_embedd ng,

      ARRAY_AGG(STRUCT(
          cluster d,
          -- t  d v s on by MAX_EXPONENT  s to avo d overflow operat on
          ftr_oon_at_5 * (2 / (1+EXP(-1* (decayed_n_favs_at_5/1000))) - 1) *  F(cluster_nom_logFavScore_rank ng > MAX_EXPONENT, 0, 1.0/(POW(1.1, cluster_nom_logFavScore_rank ng-1))) AS oon_ftrat5_decayed_pop_b as_1000_rank_decay
      ) ORDER BY ftr_oon_at_5 * (2 / (1+EXP(-1* (decayed_n_favs_at_5/1000))) - 1) *  F(cluster_nom_logFavScore_rank ng > MAX_EXPONENT, 0, 1.0/(POW(1.1, cluster_nom_logFavScore_rank ng-1))) DESC L M T {TWEET_EMBEDD NG_LENGTH}) oon_ftrat5_decayed_pop_b as_1000_rank_decay_embedd ng,

      ARRAY_AGG(STRUCT(
          cluster d,
          dec_sum_logfavScoreClusterNormal zedOnly
          ) ORDER BY dec_sum_logfavScoreClusterNormal zedOnly DESC L M T {TWEET_EMBEDD NG_LENGTH}) dec_sum_logfavScoreClusterNormal zedOnly_embedd ng,

    FROM t et_cluster_ nter d ate, vars
    GROUP BY 1
  ),
  t et_e_unnest AS (
    SELECT
        t et_ d AS t et d,
        clusterToScores.cluster d AS cluster d,
        clusterToScores.{SCORE_KEY} t etScore
    FROM t et_e, UNNEST({SCORE_COLUMN}) clusterToScores
    WHERE clusterToScores.{SCORE_KEY}  S NOT NULL
      AND clusterToScores.{SCORE_KEY} > 0
  )
  SELECT
    t et d,
    cluster d,
    t etScore
  FROM t et_e_unnest
