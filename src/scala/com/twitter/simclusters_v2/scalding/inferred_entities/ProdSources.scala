package com.tw ter.s mclusters_v2.scald ng. nferred_ent  es

 mport com.tw ter.scald ng.{DateRange, Days, TypedP pe}
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.{Expl c Locat on, ProcAtla}
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.{ModelVers ons, Semant cCoreEnt y d, User d}
 mport com.tw ter.s mclusters_v2.hdfs_s ces.{
  S mclusters nferredEnt  esFromKnownForScalaDataset,
  S mclustersV2 nterested n20M145KUpdatedScalaDataset,
  S mclustersV2 nterested nScalaDataset,
  S mclustersV2KnownFor20M145KDec11ScalaDataset,
  S mclustersV2KnownFor20M145KUpdatedScalaDataset,
  UserUserNormal zedGraphScalaDataset
}
 mport com.tw ter.s mclusters_v2.scald ng.KnownForS ces
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  Ent yS ce,
  S mClusterW hScore,
  S mClustersS ce,
  TopS mClustersW hScore,
  UserAndNe ghbors
}
 mport java.ut l.T  Zone

/**
 * Conven ence funct ons to read data from prod.
 */
object ProdS ces {

  // Returns t  Dec11 KnownFor from product on
  def getDec11KnownFor( mpl c  tz: T  Zone): TypedP pe[(User d, Seq[S mClusterW hScore])] =
    KnownForS ces
      .readDALDataset(
        S mclustersV2KnownFor20M145KDec11ScalaDataset,
        Days(30),
        ModelVers ons.Model20M145KDec11)
      .map {
        case (user d, clustersArray) =>
          val clusters = clustersArray.map {
            case (cluster d, score) => S mClusterW hScore(cluster d, score)
          }.toSeq
          (user d, clusters)
      }

  // Returns t  Updated KnownFor from product on
  def getUpdatedKnownFor( mpl c  tz: T  Zone): TypedP pe[(User d, Seq[S mClusterW hScore])] =
    KnownForS ces
      .readDALDataset(
        S mclustersV2KnownFor20M145KUpdatedScalaDataset,
        Days(30),
        ModelVers ons.Model20M145KUpdated
      )
      .map {
        case (user d, clustersArray) =>
          val clusters = clustersArray.map {
            case (cluster d, score) => S mClusterW hScore(cluster d, score)
          }.toSeq
          (user d, clusters)
      }

  def get nferredEnt  esFromKnownFor(
     nferredFromCluster: S mClustersS ce,
     nferredFromEnt y: Ent yS ce,
    dateRange: DateRange
  ): TypedP pe[(User d, Seq[(Semant cCoreEnt y d, Double)])] = {
    DAL
      .readMostRecentSnapshot(S mclusters nferredEnt  esFromKnownForScalaDataset, dateRange)
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
      .map {
        case KeyVal(user d, ent  es) =>
          val val dEnt  es =
            ent  es.ent  es
              .collect {
                case ent y
                     f ent y.ent yS ce.conta ns( nferredFromEnt y) &&
                      ent y.s mclusterS ce.conta ns( nferredFromCluster) =>
                  (ent y.ent y d, ent y.score)
              }
              .groupBy(_._1)
              .map { case (ent y d, scores) => (ent y d, scores.map(_._2).max) }
              .toSeq
          (user d, val dEnt  es)
      }
  }

  def getUserUserEngage ntGraph(dateRange: DateRange): TypedP pe[UserAndNe ghbors] = {
    DAL
      .readMostRecentSnapshot(UserUserNormal zedGraphScalaDataset, dateRange)
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
  }
}
