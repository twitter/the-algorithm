package com.tw ter.s mclusters_v2.summ ngb rd.common

 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on

case class ModelVers onProf le(
  modelVers on: ModelVers on,
  us ngLogFavScore: Boolean,
  // redundant  n t  current models because t  above para ter does t  sa  currently.
  coreEmbedd ngType: Embedd ngType,
  favScoreThresholdForUser nterest: Double,
  // t se values are shared bet en all prof les so lets set up defaults
  halfL fe: Durat on = 8.h s,
  scoreThresholdForEnt yTopKClustersCac : Double = 0.2,
  scoreThresholdForT etTopKClustersCac : Double = 0.02,
  scoreThresholdForClusterTopKT etsCac : Double = 0.001,
  scoreThresholdForClusterTopKEnt  esCac : Double = 0.001)

object ModelVers onProf les {
  f nal val ModelVers on20M145KUpdated = ModelVers onProf le(
    ModelVers on.Model20m145kUpdated,
    us ngLogFavScore = true,
    coreEmbedd ngType = Embedd ngType.LogFavBasedT et,
    favScoreThresholdForUser nterest = 1.0
  )

  f nal val ModelVers on20M145K2020 = ModelVers onProf le(
    ModelVers on.Model20m145k2020,
    us ngLogFavScore = true,
    coreEmbedd ngType = Embedd ngType.LogFavBasedT et,
    favScoreThresholdForUser nterest = 0.3
  )

  f nal val ModelVers onProf les: Map[ModelVers on, ModelVers onProf le] = Map(
    ModelVers on.Model20m145kUpdated -> ModelVers on20M145KUpdated,
    ModelVers on.Model20m145k2020 -> ModelVers on20M145K2020
  )
}
