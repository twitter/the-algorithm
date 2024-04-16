package com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.model_based_top c_recom ndat ons

 mport com.tw ter.scald ng.{DateRange, Days, Stat, TypedP pe, Un que D}
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.{Expl c Locat on, Proc3Atla}
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.{Language, Top c d, User d}
 mport com.tw ter.s mclusters_v2.hdfs_s ces.FavTfgTop cEmbedd ngsScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.User nterested nReadableStore
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  Embedd ngType,
   nternal d,
  LocaleEnt y d,
  ModelVers on,
  S mClustersEmbedd ng d
}
 mport java.ut l.T  Zone

/**
 * DataS ces object to read datasets for t  model based top c recom ndat ons
 */
object DataS ces {

  pr vate val top cEmbedd ngDataset = FavTfgTop cEmbedd ngsScalaDataset
  pr vate val top cEmbedd ngType = Embedd ngType.FavTfgTop c

  /**
   * Get user  nterested n data, f lter popular clusters and return fav-scores  nterested n embedd ng for user
   */
  def getUser nterested nData(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): TypedP pe[(User d, Map[ nt, Double])] = {
    val numUser nterested n nput = Stat("num_user_ nterested_ n")
    ExternalDataS ces.s mClusters nterest nS ce
      .map {
        case KeyVal(user d, clustersUser s nterested n) =>
          val clustersPostF lter ng = clustersUser s nterested n.cluster dToScores.f lter {
            case (cluster d, clusterScores) =>
              // f lter out popular clusters ( .e clusters w h > 5M users  nterested  n  ) from t  user embedd ng
              clusterScores.numUsers nterested nT ClusterUpperBound.ex sts(
                _ < User nterested nReadableStore.MaxClusterS zeForUser nterested nDataset)
          }
          numUser nterested n nput. nc()
          (user d, clustersPostF lter ng.mapValues(_.favScore.getOrElse(0.0)).toMap)
      }
  }

  def getPerLanguageTop cEmbedd ngs(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): TypedP pe[((Top c d, Language), Map[ nt, Double])] = {
    val numTFGPerLanguageEmbedd ngs = Stat("num_per_language_tfg_embedd ngs")
    DAL
      .readMostRecentSnapshotNoOlderThan(top cEmbedd ngDataset, Days(30))
      .w hRemoteReadPol cy(Expl c Locat on(Proc3Atla))
      .toTypedP pe
      .map {
        case KeyVal(k, v) => (k, v)
      }.collect {
        case (
              S mClustersEmbedd ng d(
                embedType,
                ModelVers on.Model20m145kUpdated,
                 nternal d.LocaleEnt y d(LocaleEnt y d(ent y d, lang))),
              embedd ng)  f (embedType == top cEmbedd ngType) =>
          numTFGPerLanguageEmbedd ngs. nc()
          ((ent y d, lang), embedd ng.embedd ng.map(_.toTuple).toMap)
      }.forceToD sk
  }
}
