DelonCLARelon datelon_latelonst_twelonelont, datelon_latelonst_follows DATelon;
SelonT datelon_latelonst_twelonelont = (
  SelonLelonCT PARSelon_DATelon('%Y%m%d', SUBSTRING(MAX(partition_id), 1, 8)) AS partition_id
  FROM `twttr-bq-twelonelontsourcelon-pub-prod.uselonr.INFORMATION_SCHelonMA.PARTITIONS`
  WHelonRelon partition_id IS NOT NULL AND partition_id != '__NULL__' AND tablelon_namelon="public_twelonelonts");
SelonT datelon_latelonst_follows = (
  SelonLelonCT PARSelon_DATelon('%Y%m%d', MAX(partition_id)) AS partition_id
  FROM `twttr-reloncos-ml-prod.uselonr_elonvelonnts.INFORMATION_SCHelonMA.PARTITIONS`
  WHelonRelon partition_id IS NOT NULL AND partition_id != '__NULL__' AND tablelon_namelon="valid_uselonr_follows");

-- twelonelont count candidatelon felonaturelons
CRelonATelon OR RelonPLACelon TABLelon `twttr-reloncos-ml-prod.relonalgraph.twelonelonting_follows`
PARTITION BY ds
AS
WITH twelonelont_count AS (
  SelonLelonCT uselonrId, COUNT(uselonrId) AS num_twelonelonts
  FROM `twttr-bq-twelonelontsourcelon-pub-prod.uselonr.public_twelonelonts`
  WHelonRelon DATelon(ts) BelonTWelonelonN DATelon_SUB(datelon_latelonst_twelonelont, INTelonRVAL 3 DAY) AND datelon_latelonst_twelonelont
  GROUP BY 1
), all_follows AS (
  SelonLelonCT F.sourcelonId AS sourcelon_id, F.delonstinationId AS delonstination_id, COALelonSCelon(T.num_twelonelonts,0) AS num_twelonelonts,
  ROW_NUMBelonR() OVelonR (PARTITION BY F.sourcelonId ORDelonR BY T.num_twelonelonts DelonSC) AS rn
  FROM `twttr-reloncos-ml-prod.uselonr_elonvelonnts.valid_uselonr_follows` F
  LelonFT JOIN twelonelont_count T
  ON F.delonstinationId=T.uselonrId
  WHelonRelon DATelon(F._PARTITIONTIMelon) = datelon_latelonst_follows
) SelonLelonCT *, datelon_latelonst_twelonelont AS ds FROM all_follows  WHelonRelon rn <= 2000
;
