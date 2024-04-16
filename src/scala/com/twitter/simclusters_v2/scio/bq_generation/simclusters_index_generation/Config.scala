package com.tw ter.s mclusters_v2.sc o.bq_generat on.s mclusters_ ndex_generat on

object Conf g {
  // Common Root Path
  val RootMHPath: Str ng = "manhattan_sequence_f les/s mclusters_to_t et_ ndex/"
  val RootThr ftPath: Str ng = "processed/s mclusters_to_t et_ ndex/"
  val AdhocRootPath = "adhoc/s mclusters_to_t et_ ndex/"
  // cluster-to-t et KeyVal Dataset Output Path
  val FavBasedClusterToT et ndexOutputPath = "fav_based_ ndex"
  val FavBasedEvergreenContentClusterToT et ndexOutputPath = "fav_based_evergreen_ ndex"
  val FavBasedV deoClusterToT et ndexOutputPath = "fav_based_v deo_ ndex"
  val V deoV ewBasedClusterToT et ndexOutputPath = "v deo_v ew_based_ ndex"
  val Ret etBasedClusterToT et ndexOutputPath = "ret et_based_ ndex"
  val ReplyBasedClusterToT et ndexOutputPath = "reply_based_ ndex"
  val PushOpenBasedClusterToT et ndexOutputPath = "push_open_based_ ndex"
  val AdsFavBasedClusterToT et ndexOutputPath = "ads_fav_based_ ndex"
  val AdsFavCl ckBasedClusterToT et ndexOutputPath = "ads_fav_cl ck_based_ ndex"

  // SQL f le path
  val s mclustersEngage ntBased ndexGenerat onSQLPath =
    s"/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/engage nt_based_ ndex_generat on.sql"
  val un f edUserT etAct onPa rGenerat onSQLPath =
    s"/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/un f ed_user_t et_act on_pa r_generat on.sql"
  val comb nedUserT etAct onPa rGenerat onSQLPath =
    s"/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/comb ned_user_t et_act on_pa r_generat on.sql"
  val adsUserT etAct onPa rGenerat onSQLPath =
    s"/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/ads_user_t et_act on_pa r_generat on.sql"
  val evergreenContentUserT etAct onPa rGenerat onSQLPath =
    s"/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/evergreen_content_user_t et_act on_pa r_generat on.sql"
  val favBasedV deoT etAct onPa rGenerat onSQLPath =
    s"/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/user_v deo_t et_fav_engage nt_generat on.sql"

  // Table na  for server/cl ent engage nts
  val cl entEngage ntTableNa : Str ng = "twttr-bq- es ce-prod.user.cl ent_engage nts"
  val serverEngage ntTableNa : Str ng = "twttr-bq- es ce-prod.user.server_engage nts"

  // T et  d column na s from UUA
  val act onT et dColumn: Str ng = " em.t et nfo.act onT et d"
  val ret etT et dColumn: Str ng = " em.t et nfo.ret etedT et d"
  val replyT et dColumn: Str ng = " em.t et nfo. nReplyToT et d"
  val pushT et dColumn: Str ng = " em.not f cat on nfo.content.t etNot f cat on.t et d"

  // Do not enable  alth or v deo f lters by default
  val enable althAndV deoF lters: Boolean = false

  // Do not enable top k t ets per cluster  ntersect on w h fav-based clusters
  val enable ntersect onW hFavBasedClusterTopKT ets ndex: Boolean = false

  // M n fav/ nteract on threshold
  val m n nteract onCount:  nt = 50
  val m nFavCount:  nt = 50

  // T et Embedd ngs conf gs
  val t etEmbedd ngsLength:  nt = 50
  val t etEmbedd ngsHalfL fe:  nt = 28800000

  // Cluster-to-t et  ndex conf gs
  val clusterTopKT ets:  nt = 2000
  val maxT etAgeH s:  nt = 24
  val m nEngage ntPerCluster:  nt = 0

  // Placeholder act on type for  nteract ons that don't have undo events (e.g. v deo v ews)
  val PlaceholderAct onType: Str ng = "PLACEHOLDER_ACT ON_TYPE"

  // Ads event engage nt type  ds
  val AdsFavEngage ntType ds = Seq(8) // Fav promoted t et
  val AdsCl ckEngage ntType ds = Seq(
    1, //URL
    42, // CARD_URL_CL CK
    53, // WEBS TE_CARD_CONTA NER_CL CK
    54, // WEBS TE_CARD_BUTTON_CL CK
    55, // WEBS TE_CARD_ MAGE_CL CK
    56, // WEBS TE_CARD_T TLE_CL CK
    69, // BUYNOW_CARD_CL CK
    70, // BUYNOW_PURCHASE_SUCCESS
    72, // V DEO_CTA_URL_CL CK
    76, // V DEO_AD_CTA_URL_CL CK
    80, // V DEO_CONTENT_CTA_URL_CL CK
    84, // CL_OFFER_CARD_CL CK
  )

}
