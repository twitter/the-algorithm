-- get latest partition of candidates with data
DECLARE date_candidates DATE;
SET date_candidates = (SELECT DATE(TIMESTAMP_MILLIS($start_time$)));

CREATE TABLE IF NOT EXISTS  `twttr-recos-ml-prod.realgraph.candidates_sampled` AS
SELECT * FROM `twttr-recos-ml-prod.realgraph.candidates_for_training` LIMIT 100;

-- remove previous output snapshot (if exists) to avoid double-writing
DELETE
FROM `twttr-recos-ml-prod.realgraph.candidates_sampled`
WHERE ds = date_candidates;

-- sample from candidates table instead of recomputing features
INSERT INTO `twttr-recos-ml-prod.realgraph.candidates_sampled`
SELECT * FROM `twttr-recos-ml-prod.realgraph.candidates_for_training`
WHERE MOD(ABS(FARM_FINGERPRINT(CONCAT(source_id, '_', destination_id))), 100) = $mod_remainder$
AND ds = date_candidates;

