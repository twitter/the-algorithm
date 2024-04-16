package com.tw ter.follow_recom ndat ons.common.pred cates.user_act v y

 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.ut l.Durat on

object UserAct v yPred cateParams {
  case object  avyT eterEnabled
      extends FSParam[Boolean]("user_act v y_pred cate_ avy_t eter_enabled", false)
  val Cac TTL: Durat on = Durat on.fromH s(6)
}
