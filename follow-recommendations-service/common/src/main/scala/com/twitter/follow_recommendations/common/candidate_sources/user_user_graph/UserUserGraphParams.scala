package com.tw ter.follow_recom ndat ons.common.cand date_s ces.user_user_graph

 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param

object UserUserGraphParams {

  // max number of cand dates to return  n total, 50  s t  default param used  n Mag cRecs
  object MaxCand datesToReturn extends Param[ nt](default = 50)

  // w t r or not to  nclude UserUserGraph cand date s ce  n t    ghted blend ng step
  case object UserUserGraphCand dateS ceEnabled n  ghtMap
      extends FSParam[Boolean]("user_user_graph_cand date_s ce_enabled_ n_  ght_map", true)

  // w t r or not to  nclude UserUserGraph cand date s ce  n t  f nal transform step
  case object UserUserGraphCand dateS ceEnabled nTransform
      extends FSParam[Boolean]("user_user_graph_cand date_s ce_enabled_ n_transform", true)

}
