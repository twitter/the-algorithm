package com.tw ter.follow_recom ndat ons.common.pred cates.g zmoduck

 mport com.tw ter.follow_recom ndat ons.common.pred cates.g zmoduck.G zmoduckPred cateParams._
 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.ut l.Durat on

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class G zmoduckPred cateFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val durat onFSParams: Seq[FSBoundedParam[Durat on] w h HasDurat onConvers on] = Seq(
    G zmoduckGetT  out
  )
}
