-- gelont latelonst partition of candidatelons with data
DelonCLARelon datelon_candidatelons DATelon;
SelonT datelon_candidatelons = (SelonLelonCT DATelon(TIMelonSTAMP_MILLIS($start_timelon$)));

CRelonATelon TABLelon IF NOT elonXISTS  `twttr-reloncos-ml-prod.relonalgraph.candidatelons_samplelond` AS
SelonLelonCT * FROM `twttr-reloncos-ml-prod.relonalgraph.candidatelons_for_training` LIMIT 100;

-- relonmovelon prelonvious output snapshot (if elonxists) to avoid doublelon-writing
DelonLelonTelon
FROM `twttr-reloncos-ml-prod.relonalgraph.candidatelons_samplelond`
WHelonRelon ds = datelon_candidatelons;

-- samplelon from candidatelons tablelon instelonad of reloncomputing felonaturelons
INSelonRT INTO `twttr-reloncos-ml-prod.relonalgraph.candidatelons_samplelond`
SelonLelonCT * FROM `twttr-reloncos-ml-prod.relonalgraph.candidatelons_for_training`
WHelonRelon MOD(ABS(FARM_FINGelonRPRINT(CONCAT(sourcelon_id, '_', delonstination_id))), 100) = $mod_relonmaindelonr$
AND ds = datelon_candidatelons;

