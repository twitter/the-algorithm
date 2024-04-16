package com.tw ter.representat on_manager.store

 mport com.tw ter.servo.dec der.Dec derKeyEnum

object Dec derConstants {
  // Dec ders  n r ed from CR and RSX and only used  n LegacyRMS
  // T  r value are man pulated by CR and RSX's yml f le and t  r dec der dashboard
  //   w ll remove t m after m grat on completed
  val enableLogFavBasedApeEnt y20M145KUpdatedEmbedd ngCac dStore =
    "enableLogFavBasedApeEnt y20M145KUpdatedEmbedd ngCac dStore"

  val enableLogFavBasedApeEnt y20M145K2020Embedd ngCac dStore =
    "enableLogFavBasedApeEnt y20M145K2020Embedd ngCac dStore"

  val enablelogFavBased20M145K2020T etEmbedd ngStoreT  outs =
    "enable_log_fav_based_t et_embedd ng_20m145k2020_t  outs"
  val logFavBased20M145K2020T etEmbedd ngStoreT  outValueM ll s =
    "log_fav_based_t et_embedd ng_20m145k2020_t  out_value_m ll s"

  val enablelogFavBased20M145KUpdatedT etEmbedd ngStoreT  outs =
    "enable_log_fav_based_t et_embedd ng_20m145kUpdated_t  outs"
  val logFavBased20M145KUpdatedT etEmbedd ngStoreT  outValueM ll s =
    "log_fav_based_t et_embedd ng_20m145kUpdated_t  out_value_m ll s"

  val enableS mClustersEmbedd ngStoreT  outs = "enable_s m_clusters_embedd ng_store_t  outs"
  val s mClustersEmbedd ngStoreT  outValueM ll s =
    "s m_clusters_embedd ng_store_t  out_value_m ll s"
}

// Necessary for us ng servo Gates
object Dec derKey extends Dec derKeyEnum {
  val enableLogFavBasedApeEnt y20M145KUpdatedEmbedd ngCac dStore: Value = Value(
    Dec derConstants.enableLogFavBasedApeEnt y20M145KUpdatedEmbedd ngCac dStore
  )

  val enableLogFavBasedApeEnt y20M145K2020Embedd ngCac dStore: Value = Value(
    Dec derConstants.enableLogFavBasedApeEnt y20M145K2020Embedd ngCac dStore
  )
}
