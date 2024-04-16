package com.tw ter.s mclustersann.except ons

 mport com.tw ter.f nagle.RequestExcept on
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on

case class  nval dRequestForS mClustersAnnVar antExcept on(
  modelVers on: ModelVers on,
  embedd ngType: Embedd ngType,
  actualServ ceNa : Str ng,
  expectedServ ceNa : Opt on[Str ng])
    extends RequestExcept on(
      s"Request w h model vers on ($modelVers on) and embedd ng type ($embedd ngType) cannot be " +
        s"processed by serv ce var ant ($actualServ ceNa )." +
        s" Expected serv ce var ant: $expectedServ ceNa .",
      null)
