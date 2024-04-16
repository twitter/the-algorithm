package com.tw ter.follow_recom ndat ons.common.pred cates.d sm ss

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

object D sm ssedCand datePred cateParams {
  case object LookBackDurat on extends Param[Durat on](180.days)
}
