package com.tw ter.ho _m xer.product.l st_recom nded_users.model

 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.feature.Feature

object L stRecom ndedUsersFeatures {
  // Cand date features
  object  sG zmoduckVal dUserFeature extends Feature[UserCand date, Boolean]
  object  sL st mberFeature extends Feature[UserCand date, Boolean]
  object  sSGSVal dUserFeature extends Feature[UserCand date, Boolean]
  object ScoreFeature extends Feature[UserCand date, Double]
}
