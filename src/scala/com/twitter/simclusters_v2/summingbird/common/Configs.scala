package com.tw ter.s mclusters_v2.summ ngb rd.common

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.ut l.Durat on

object Conf gs {

  f nal val role = "cassowary"

  f nal val ZoneAtla: Str ng = "atla"

  @deprecated("Use 'common/ModelVers ons'", "2019-09-04")
  f nal val ModelVers on20M145KDec11: Str ng = "20M_145K_dec11"
  @deprecated("Use 'common/ModelVers ons'", "2019-09-04")
  f nal val ModelVers on20M145KUpdated: Str ng = "20M_145K_updated"
  f nal val ModelVers on20M145K2020: Str ng = "20M_145K_2020"

  @deprecated("Use 'common/ModelVers ons'", "2019-09-04")
  f nal val ModelVers onMap: Map[Str ng, ModelVers on] = Map(
    ModelVers on20M145KDec11 -> ModelVers on.Model20m145kDec11,
    ModelVers on20M145KUpdated -> ModelVers on.Model20m145kUpdated,
    ModelVers on20M145K2020 -> ModelVers on.Model20m145k2020
  )

  f nal val favScoreThresholdForUser nterest: Str ng => Double = {
    case ModelVers on20M145KDec11 => 0.15
    case ModelVers on20M145KUpdated => 1.0
    case ModelVers on20M145K2020 => 0.3
    case modelVers onStr => throw new Except on(s"$modelVers onStr  s not a val d model")
  }

  @deprecated("Use 'common/ModelVers ons'", "2019-09-04")
  f nal val ReversedModelVers onMap = ModelVers onMap.map(_.swap)

  f nal val batc sToKeep:  nt = 1

  f nal val HalfL fe: Durat on = 8.h s
  f nal val HalfL fe nMs: Long = HalfL fe. nM ll seconds

  f nal val topKT etsPerCluster:  nt = 1600

  f nal val topKClustersPerEnt y:  nt = 50

  // t  conf g used  n offl ne job only
  f nal val topKClustersPerT et:  nt = 400

  // m n mum score to save cluster ds  n ent yTopKClusters cac 
  // ent y  ncludes ent  es ot r than t et d.
  f nal val scoreThresholdForEnt yTopKClustersCac : Double = 0.02

  // m n mum score to save cluster ds  n t etTopKClusters cac 
  f nal val scoreThresholdForT etTopKClustersCac : Double = 0.02

  // m n mum score to save t et ds  n clusterTopKT ets cac 
  f nal val scoreThresholdForClusterTopKT etsCac : Double = 0.001

  // m n mum score to save ent  es  n clusterTopKEnt  es cac 
  f nal val scoreThresholdForClusterTopKEnt  esCac : Double = 0.001

  f nal val M nFavor eCount = 8

  f nal val OldestT et nL ght ndex nM ll s = 1.h s. nM ll s

  f nal val OldestT etFavEventT   nM ll s = 3.days. nM ll s

  f nal val F rstUpdateValue = 1

  f nal val TempUpdateValue = -1
}
