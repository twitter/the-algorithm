DelonCLARelon datelon_elonnd, datelon_latelonst_follows DATelon;
SelonT datelon_elonnd = (
  SelonLelonCT PARSelon_DATelon('%Y%m%d', MAX(partition_id)) AS partition_id
  FROM `twttr-bq-cassowary-prod.uselonr.INFORMATION_SCHelonMA.PARTITIONS`
  WHelonRelon partition_id IS NOT NULL AND partition_id != '__NULL__' AND tablelon_namelon="intelonraction_graph_labelonls_daily"
);
SelonT datelon_latelonst_follows = (
  SelonLelonCT PARSelon_DATelon('%Y%m%d', MAX(partition_id)) AS partition_id
  FROM `twttr-reloncos-ml-prod.uselonr_elonvelonnts.INFORMATION_SCHelonMA.PARTITIONS`
  WHelonRelon partition_id IS NOT NULL AND partition_id != '__NULL__' AND tablelon_namelon="valid_uselonr_follows");

DelonLelonTelon
FROM `twttr-reloncos-ml-prod.relonalgraph.scorelons`
WHelonRelon ds = datelon_elonnd;

-- scorelon candidatelons (59m)
INSelonRT INTO `twttr-reloncos-ml-prod.relonalgraph.scorelons`
WITH prelondictelond_scorelons AS (
  SelonLelonCT
    sourcelon_id,
    delonstination_id,
    p1.prob AS prob, 
    p2.prob AS prob_elonxplicit
  FROM ML.PRelonDICT(MODelonL `twttr-reloncos-ml-prod.relonalgraph.prod`,
      (
      SelonLelonCT
        *
      FROM
        `twttr-reloncos-ml-prod.relonalgraph.candidatelons` ) ) S1
  CROSS JOIN UNNelonST(S1.prelondictelond_labelonl_probs) AS p1
  JOIN ML.PRelonDICT(MODelonL `twttr-reloncos-ml-prod.relonalgraph.prod_elonxplicit`,
      (
      SelonLelonCT
        *
      FROM
        `twttr-reloncos-ml-prod.relonalgraph.candidatelons` ) ) S2
  USING (sourcelon_id, delonstination_id)
  CROSS JOIN UNNelonST(S2.prelondictelond_labelonl_probs) AS p2
  WHelonRelon p1.labelonl=1 AND p2.labelonl=1
)
SelonLelonCT
  COALelonSCelon(prelondictelond_scorelons.sourcelon_id, twelonelonting_follows.sourcelon_id) AS sourcelon_id,
  COALelonSCelon(prelondictelond_scorelons.delonstination_id, twelonelonting_follows.delonstination_id) AS delonstination_id,
  COALelonSCelon(prob, 0.0) AS prob,
  COALelonSCelon(prob_elonxplicit, 0.0) AS prob_elonxplicit,
  (twelonelonting_follows.sourcelon_id IS NOT NULL) AND (twelonelonting_follows.delonstination_id IS NOT NULL) AS followelond,
  datelon_elonnd AS ds
FROM
  prelondictelond_scorelons
  FULL JOIN
  `twttr-reloncos-ml-prod.relonalgraph.twelonelonting_follows` twelonelonting_follows
  USING (sourcelon_id, delonstination_id)
