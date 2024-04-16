package com.tw ter.follow_recom ndat ons.products.ho _t  l ne.conf gap 

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.follow_recom ndat ons.products.ho _t  l ne.conf gap .Ho T  l neParams._
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ho T  l neFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[Param[Boolean] w h FSNa ] =
    Seq(EnableWr  ngServ ng tory)

  overr de val durat onFSParams: Seq[FSBoundedParam[Durat on] w h HasDurat onConvers on] = Seq(
    Durat onGuardra lToForceSuggest,
    SuggestBasedFat gueDurat on
  )
}
