package com.tw ter.follow_recom ndat ons.common.cand date_s ces.user_user_graph

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .Param
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UserUserGraphFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[Param[Boolean] w h FSNa ] = Seq(
    UserUserGraphParams.UserUserGraphCand dateS ceEnabled n  ghtMap,
    UserUserGraphParams.UserUserGraphCand dateS ceEnabled nTransform
  )
}
