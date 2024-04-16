package com.tw ter.follow_recom ndat ons.products.explore_tab.conf gap 

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.follow_recom ndat ons.products.explore_tab.conf gap .ExploreTabParams._
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .Param
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ExploreTabFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[Param[Boolean] w h FSNa ] =
    Seq(EnableProductForSoftUser)
}
