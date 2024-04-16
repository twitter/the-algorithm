package com.tw ter.ho _m xer.param.dec der

 mport com.tw ter.servo.dec der.Dec derKeyEnum

/**
 * T se values must correspond to t  dec ders conf gured  n t 
 * ho -m xer/server/src/ma n/res ces/conf g/dec der.yml f le
 *
 * @see [[com.tw ter.product_m xer.core.product.ProductParamConf g.enabledDec derKey]]
 */
object Dec derKey extends Dec derKeyEnum {
  // Products
  val EnableFor Product = Value("enable_for_ _product")

  val EnableFollow ngProduct = Value("enable_follow ng_product")

  val EnableScoredT etsProduct = Value("enable_scored_t ets_product")

  val EnableL stT etsProduct = Value("enable_l st_t ets_product")

  val EnableL stRecom ndedUsersProduct = Value("enable_l st_recom nded_users_product")

  val EnableSubscr bedProduct = Value("enable_subscr bed_product")

  // Cand date P pel nes
  val EnableFor ScoredT etsCand dateP pel ne =
    Value("enable_for_ _scored_t ets_cand date_p pel ne")

  val EnableScoredT etsT etM xerCand dateP pel ne =
    Value("enable_scored_t ets_t et_m xer_cand date_p pel ne")

  val EnableScoredT ets nNetworkCand dateP pel ne =
    Value("enable_scored_t ets_ n_network_cand date_p pel ne")

  val EnableScoredT etsUtegCand dateP pel ne =
    Value("enable_scored_t ets_uteg_cand date_p pel ne")

  val EnableScoredT etsFrsCand dateP pel ne =
    Value("enable_scored_t ets_frs_cand date_p pel ne")

  val EnableScoredT etsL stsCand dateP pel ne =
    Value("enable_scored_t ets_l sts_cand date_p pel ne")

  val EnableScoredT etsPopularV deosCand dateP pel ne =
    Value("enable_scored_t ets_popular_v deos_cand date_p pel ne")

  val EnableScoredT etsBackf llCand dateP pel ne =
    Value("enable_scored_t ets_backf ll_cand date_p pel ne")

  val EnableS mClustersS m lar yFeatureHydrat on =
    Value("enable_s mclusters_s m lar y_feature_hydrat on")
}
