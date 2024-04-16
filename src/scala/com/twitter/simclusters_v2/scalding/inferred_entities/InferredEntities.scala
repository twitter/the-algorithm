package com.tw ter.s mclusters_v2.scald ng. nferred_ent  es

 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Ent yEmbedd ngsS ces
 mport com.tw ter.s mclusters_v2.thr ftscala.ClusterType
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala. nferredEnt y
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.Semant cCoreEnt yW hScore
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClusters nferredEnt  es
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersS ce
 mport java.ut l.T  Zone

/**
 * Opt-out compl ance for S mClusters  ans offer ng users an opt on to opt out of clusters that
 * have  nferred leg ble  an ngs. T  f le sets so  of t  data s ces & thresholds from wh ch
 * t   nferred ent  es are cons dered leg ble. One should always refer to t  s ces & constants
 *  re for S mClusters'  nferred ent y compl ance work
 */
object  nferredEnt  es {
  val MHRootPath: Str ng =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_ nferred_ent  es"

  // Conven ence objects for def n ng cluster s ces
  val  nterested n2020 =
    S mClustersS ce(ClusterType. nterested n, ModelVers on.Model20m145k2020)

  val Dec11KnownFor = S mClustersS ce(ClusterType.KnownFor, ModelVers on.Model20m145kDec11)

  val UpdatedKnownFor = S mClustersS ce(ClusterType.KnownFor, ModelVers on.Model20m145kUpdated)

  val KnownFor2020 = S mClustersS ce(ClusterType.KnownFor, ModelVers on.Model20m145k2020)

  /**
   * T   s t  threshold at wh ch   cons der a s mcluster "leg ble" through an ent y
   */
  val M nLeg bleEnt yScore = 0.6

  /**
   * Query for t  ent y embedd ngs that are used for S mClusters compl ance.   w ll use t se
   * ent y embedd ngs for a cluster to allow a user to opt out of a cluster
   */
  def getLeg bleEnt yEmbedd ngs(
    dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[(Cluster d, Seq[Semant cCoreEnt yW hScore])] = {
    val ent yEmbedd ngs = Ent yEmbedd ngsS ces
      .getReverse ndexedSemant cCoreEnt yEmbedd ngsS ce(
        Embedd ngType.FavBasedSemat cCoreEnt y,
        ModelVers ons.Model20M145K2020, // only support t  latest 2020 model
        dateRange.emb ggen(Days(7)(t  Zone)) // read 7 days before & after to g ve buffer
      )
    f lterEnt yEmbedd ngsByScore(ent yEmbedd ngs, M nLeg bleEnt yScore)
  }

  // Return ent  es whose score are above threshold
  def f lterEnt yEmbedd ngsByScore(
    ent yEmbedd ngs: TypedP pe[(Cluster d, Seq[Semant cCoreEnt yW hScore])],
    m nEnt yScore: Double
  ): TypedP pe[(Cluster d, Seq[Semant cCoreEnt yW hScore])] = {
    ent yEmbedd ngs.flatMap {
      case (cluster d, ent  es) =>
        val val dEnt  es = ent  es.f lter { ent y => ent y.score >= m nEnt yScore }
         f (val dEnt  es.nonEmpty) {
          So ((cluster d, val dEnt  es))
        } else {
          None
        }

    }
  }

  /**
   * G ven  nferred ent  es from d fferent s ces, comb ne t  results  nto job's output format
   */
  def comb neResults(
    results: TypedP pe[(User d, Seq[ nferredEnt y])]*
  ): TypedP pe[(User d, S mClusters nferredEnt  es)] = {
    results
      .reduceLeft(_ ++ _)
      .sumByKey
      .map {
        case (user d,  nferredEnt  es) =>
          (user d, S mClusters nferredEnt  es( nferredEnt  es))
      }
  }
}
