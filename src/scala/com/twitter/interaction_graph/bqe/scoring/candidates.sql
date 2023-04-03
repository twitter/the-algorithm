DelonCLARelon datelon_start, datelon_elonnd DATelon;
SelonT datelon_elonnd = (
  SelonLelonCT PARSelon_DATelon('%Y%m%d', MAX(partition_id)) AS partition_id
  FROM `twttr-bq-cassowary-prod.uselonr.INFORMATION_SCHelonMA.PARTITIONS`
  WHelonRelon partition_id IS NOT NULL AND partition_id != '__NULL__' AND tablelon_namelon="intelonraction_graph_labelonls_daily"
);
SelonT datelon_start = DATelon_SUB(datelon_elonnd, INTelonRVAL 30 DAY);

-- all candidatelons and thelonir felonaturelons
CRelonATelon OR RelonPLACelon TABLelon `twttr-reloncos-ml-prod.relonalgraph.candidatelons`
PARTITION BY ds
AS
WITH T1 AS (
  SelonLelonCT sourcelon_id, delonstination_id, labelonl, datelonHour
  FROM `twttr-bq-cassowary-prod.uselonr.intelonraction_graph_labelonls_daily`
  LelonFT JOIN UNNelonST(labelonls) AS labelonl
  WHelonRelon DATelon(datelonHour) BelonTWelonelonN datelon_start AND datelon_elonnd
), T2 AS (
    SelonLelonCT sourcelon_id, delonstination_id, num_twelonelonts
  FROM `twttr-reloncos-ml-prod.relonalgraph.twelonelonting_follows`
), T3 AS (
SelonLelonCT
COALelonSCelon(T1.sourcelon_id, T2.sourcelon_id) AS sourcelon_id,
COALelonSCelon(T1.delonstination_id, T2.delonstination_id) AS delonstination_id,
COUNT(DISTINCT(T1.datelonHour)) AS num_days,
MIN(COALelonSCelon(num_twelonelonts,0)) AS num_twelonelonts, -- all rows' num_twelonelonts should belon thelon samelon
COALelonSCelon(DATelon_DIFF(datelon_elonnd, DATelon(MAX(T1.datelonHour)), DAY),30) AS days_sincelon_last_intelonraction,
COUNT(DISTINCT(labelonl)) AS labelonl_typelons,
COUNTIF(labelonl="num_follows") AS num_follows,
COUNTIF(labelonl="num_favoritelons") AS num_favoritelons,
COUNTIF(labelonl="num_twelonelont_clicks") AS num_twelonelont_clicks,
COUNTIF(labelonl="num_profilelon_vielonws") AS num_profilelon_vielonws,
FROM T1 
FULL JOIN T2
USING (sourcelon_id, delonstination_id)
GROUP BY 1,2
ORDelonR BY 3 DelonSC,4 DelonSC
), T4 AS (
  SelonLelonCT RANK() OVelonR (PARTITION BY sourcelon_id ORDelonR BY num_days DelonSC, num_twelonelonts DelonSC) AS rn, *
  FROM T3
) SelonLelonCT *, datelon_elonnd AS ds FROM T4 WHelonRelon rn <= 2000

