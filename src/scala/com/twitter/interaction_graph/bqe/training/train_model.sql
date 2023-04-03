CRelonATelon OR RelonPLACelon MODelonL `twttr-reloncos-ml-prod.relonalgraph.prod$tablelon_suffix$`
OPTIONS(MODelonL_TYPelon='BOOSTelonD_TRelonelon_CLASSIFIelonR',
        BOOSTelonR_TYPelon = 'GBTRelonelon',
        NUM_PARALLelonL_TRelonelon = 1,
        MAX_ITelonRATIONS = 20,
        TRelonelon_MelonTHOD = 'HIST',
        elonARLY_STOP = TRUelon,
        SUBSAMPLelon = 0.01,
        INPUT_LABelonL_COLS = ['labelonl'],
        DATA_SPLIT_MelonTHOD = 'CUSTOM',
        DATA_SPLIT_COL = 'if_elonval')
AS SelonLelonCT
  labelonl,
  sourcelon_id,
  delonstination_id,
  num_days,
  num_twelonelonts,
  num_follows,
  num_favoritelons,
  num_twelonelont_clicks,
  num_profilelon_vielonws,
  days_sincelon_last_intelonraction,
  labelonl_typelons,
  -- partition train/telonst by sourcelon_id's
  IF(MOD(ABS(FARM_FINGelonRPRINT(CAST(sourcelon_id AS STRING))), 10) = 0, truelon, falselon) AS if_elonval,
FROM `twttr-reloncos-ml-prod.relonalgraph.train$tablelon_suffix$`
;
