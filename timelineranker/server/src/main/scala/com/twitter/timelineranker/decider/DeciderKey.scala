package com.tw ter.t  l neranker.dec der

 mport com.tw ter.servo.dec der.Dec derKeyEnum

object Dec derKey extends Dec derKeyEnum {
  // Dec ders that can be used to control load on TLR or  s backends.
  val EnableMaxConcurrencyL m  ng: Value = Value("enable_max_concurrency_l m  ng")

  // Dec ders related to test ng / debugg ng.
  val EnableRout ngToRankerDevProxy: Value = Value("enable_rout ng_to_ranker_dev_proxy")

  // Dec ders related to author zat on.
  val Cl entRequestAuthor zat on: Value = Value("cl ent_request_author zat on")
  val Cl entWr eWh el st: Value = Value("cl ent_wr e_wh el st")
  val AllowT  l neM xerRecapProd: Value = Value("allow_t  l ne_m xer_recap_prod")
  val AllowT  l neM xerRecycledProd: Value = Value("allow_t  l ne_m xer_recycled_prod")
  val AllowT  l neM xerHydrateProd: Value = Value("allow_t  l ne_m xer_hydrate_prod")
  val AllowT  l neM xerHydrateRecosProd: Value = Value("allow_t  l ne_m xer_hydrate_recos_prod")
  val AllowT  l neM xerSeedAuthorsProd: Value = Value("allow_t  l ne_m xer_seed_authors_prod")
  val AllowT  l neM xerS mclusterProd: Value = Value("allow_t  l ne_m xer_s mcluster_prod")
  val AllowT  l neM xerEnt yT etsProd: Value = Value("allow_t  l ne_m xer_ent y_t ets_prod")
  val AllowT  l neM xerL stProd: Value = Value("allow_t  l ne_m xer_l st_prod")
  val AllowT  l neM xerL stT etProd: Value = Value("allow_t  l ne_m xer_l st_t et_prod")
  val AllowT  l neM xerCommun yProd: Value = Value("allow_t  l ne_m xer_commun y_prod")
  val AllowT  l neM xerCommun yT etProd: Value = Value(
    "allow_t  l ne_m xer_commun y_t et_prod")
  val AllowT  l neScorerRecom ndedTrendT etProd: Value = Value(
    "allow_t  l ne_scorer_recom nded_trend_t et_prod")
  val AllowT  l neM xerUtegL kedByT etsProd: Value = Value(
    "allow_t  l ne_m xer_uteg_l ked_by_t ets_prod")
  val AllowT  l neM xerStag ng: Value = Value("allow_t  l ne_m xer_stag ng")
  val AllowT  l neRankerProxy: Value = Value("allow_t  l ne_ranker_proxy")
  val AllowT  l neRankerWarmup: Value = Value("allow_t  l ne_ranker_warmup")
  val AllowT  l neScorerRecTop cT etsProd: Value =
    Value("allow_t  l ne_scorer_rec_top c_t ets_prod")
  val AllowT  l neScorerPopularTop cT etsProd: Value =
    Value("allow_t  l ne_scorer_popular_top c_t ets_prod")
  val AllowT  l neScorerHydrateT etScor ngProd: Value = Value(
    "allow_t  l nescorer_hydrate_t et_scor ng_prod")
  val AllowT  l neServ ceProd: Value = Value("allow_t  l ne_serv ce_prod")
  val AllowT  l neServ ceStag ng: Value = Value("allow_t  l ne_serv ce_stag ng")
  val RateL m Overr deUnknown: Value = Value("rate_l m _overr de_unknown")

  // Dec ders related to reverse-chron ho  t  l ne mater al zat on.
  val Mult pl erOfMater al zat onT etsFetc d: Value = Value(
    "mult pl er_of_mater al zat on_t ets_fetc d"
  )
  val Backf llF lteredEntr es: Value = Value("enable_backf ll_f ltered_entr es")
  val T etsF lter ngLossageThreshold: Value = Value("t ets_f lter ng_lossage_threshold")
  val T etsF lter ngLossageL m : Value = Value("t ets_f lter ng_lossage_l m ")
  val Supple ntFollowsW hRealGraph: Value = Value("supple nt_follows_w h_real_graph")

  // Dec ders related to recap.
  val RecapEnableContentFeaturesHydrat on: Value = Value("recap_enable_content_features_hydrat on")
  val RecapMaxCountMult pl er: Value = Value("recap_max_count_mult pl er")
  val RecapEnableExtraSort ng nResults: Value = Value("recap_enable_extra_sort ng_ n_results")

  // Dec ders related to recycled t ets.
  val RecycledMaxCountMult pl er: Value = Value("recycled_max_count_mult pl er")
  val RecycledEnableContentFeaturesHydrat on: Value = Value(
    "recycled_enable_content_features_hydrat on")

  // Dec ders related to ent y t ets.
  val Ent yT etsEnableContentFeaturesHydrat on: Value = Value(
    "ent y_t ets_enable_content_features_hydrat on")

  // Dec ders related to both recap and recycled t ets
  val EnableRealGraphUsers: Value = Value("enable_real_graph_users")
  val MaxRealGraphAndFollo dUsers: Value = Value("max_real_graph_and_follo d_users")

  // Dec ders related to recap author
  val RecapAuthorEnableNewP pel ne: Value = Value("recap_author_enable_new_p pel ne")
  val RecapAuthorEnableContentFeaturesHydrat on: Value = Value(
    "recap_author_enable_content_features_hydrat on")

  // Dec ders related to recap hydrat on (rect et and ranked organ c).
  val RecapHydrat onEnableContentFeaturesHydrat on: Value = Value(
    "recap_hydrat on_enable_content_features_hydrat on")

  // Dec ders related to uteg l ked by t ets
  val UtegL kedByT etsEnableContentFeaturesHydrat on: Value = Value(
    "uteg_l ked_by_t ets_enable_content_features_hydrat on")
}
