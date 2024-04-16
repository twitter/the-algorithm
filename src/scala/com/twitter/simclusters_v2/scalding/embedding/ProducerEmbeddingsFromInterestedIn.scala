package com.tw ter.s mclusters_v2.scald ng.embedd ng

 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l._
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.S mClustersEmbedd ngJob
 mport com.tw ter.s mclusters_v2.thr ftscala._
 mport com.tw ter.wtf.scald ng.jobs.common.{AdhocExecut onApp, Sc duledExecut onApp}
 mport java.ut l.T  Zone

object ProducerEmbedd ngsFrom nterested nBatchAppUt l {
   mport ProducerEmbedd ngsFrom nterested n._

  val user = System.getenv("USER")

  val rootPath: Str ng = s"/user/$user/manhattan_sequence_f les"

  //  lps speed up t  mult pl cat on step wh ch can get very b g
  val numReducersForMatr xMult pl cat on:  nt = 12000

  /**
   * G ven t  producer x cluster matr x, key by producer / cluster  nd v dually, and wr e output
   * to  nd v dual DAL datasets
   */
  def wr eOutput(
    producerClusterEmbedd ng: TypedP pe[((Cluster d, User d), Double)],
    producerTopKEmbedd ngsDataset: KeyValDALDataset[KeyVal[Long, TopS mClustersW hScore]],
    clusterTopKProducersDataset: KeyValDALDataset[
      KeyVal[Pers stedFullCluster d, TopProducersW hScore]
    ],
    producerTopKEmbedd ngsPath: Str ng,
    clusterTopKProducersPath: Str ng,
    modelVers on: ModelVers on
  ): Execut on[Un ] = {
    val keyedByProducer =
      toS mClusterEmbedd ng(producerClusterEmbedd ng, topKClustersToKeep, modelVers on)
        .map { case (user d, clusters) => KeyVal(user d, clusters) }
        .wr eDALVers onedKeyValExecut on(
          producerTopKEmbedd ngsDataset,
          D.Suff x(producerTopKEmbedd ngsPath)
        )

    val keyedByS mCluster = fromS mClusterEmbedd ng(
      producerClusterEmbedd ng,
      topKUsersToKeep,
      modelVers on
    ).map {
        case (cluster d, topProducers) => KeyVal(cluster d, topProducersToThr ft(topProducers))
      }
      .wr eDALVers onedKeyValExecut on(
        clusterTopKProducersDataset,
        D.Suff x(clusterTopKProducersPath)
      )

    Execut on.z p(keyedByProducer, keyedByS mCluster).un 
  }
}

/**
 * Base class for Fav based producer embedd ngs.  lps reuse t  code for d fferent model vers ons
 */
tra  ProducerEmbedd ngsFrom nterested nByFavScoreBase extends Sc duledExecut onApp {
   mport ProducerEmbedd ngsFrom nterested n._
   mport ProducerEmbedd ngsFrom nterested nBatchAppUt l._

  def modelVers on: ModelVers on

  val producerTopKEmbedd ngsByFavScorePathPref x: Str ng =
    "/producer_top_k_s mcluster_embedd ngs_by_fav_score_"

  val clusterTopKProducersByFavScorePathPref x: Str ng =
    "/s mcluster_embedd ng_top_k_producers_by_fav_score_"

  val m nNumFavers:  nt = m nNumFaversForProducer

  def producerTopKS mclusterEmbedd ngsByFavScoreDataset: KeyValDALDataset[
    KeyVal[Long, TopS mClustersW hScore]
  ]

  def s mclusterEmbedd ngTopKProducersByFavScoreDataset: KeyValDALDataset[
    KeyVal[Pers stedFullCluster d, TopProducersW hScore]
  ]

  def get nterested nFn: (DateRange, T  Zone) => TypedP pe[(Long, ClustersUser s nterested n)]

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val producerTopKEmbedd ngsByFavScorePathUpdated: Str ng =
      rootPath + producerTopKEmbedd ngsByFavScorePathPref x + ModelVers ons
        .toKnownForModelVers on(modelVers on)

    val clusterTopKProducersByFavScorePathUpdated: Str ng =
      rootPath + clusterTopKProducersByFavScorePathPref x + ModelVers ons
        .toKnownForModelVers on(modelVers on)

    val producerClusterEmbedd ngByFavScore = getProducerClusterEmbedd ng(
      get nterested nFn(dateRange.emb ggen(Days(5)), t  Zone),
      DataS ces.userUserNormal zedGraphS ce,
      DataS ces.userNormsAndCounts,
      userToProducerFavScore,
      userToClusterFavScore, // Fav score
      _.faverCount.ex sts(_ > m nNumFavers),
      numReducersForMatr xMult pl cat on,
      modelVers on,
      cos neS m lar yThreshold
    ).forceToD sk

    wr eOutput(
      producerClusterEmbedd ngByFavScore,
      producerTopKS mclusterEmbedd ngsByFavScoreDataset,
      s mclusterEmbedd ngTopKProducersByFavScoreDataset,
      producerTopKEmbedd ngsByFavScorePathUpdated,
      clusterTopKProducersByFavScorePathUpdated,
      modelVers on
    )
  }
}

/**
 * Base class for Follow based producer embedd ngs.  lps reuse t  code for d fferent model vers ons
 */
tra  ProducerEmbedd ngsFrom nterested nByFollowScoreBase extends Sc duledExecut onApp {
   mport ProducerEmbedd ngsFrom nterested n._
   mport ProducerEmbedd ngsFrom nterested nBatchAppUt l._

  def modelVers on: ModelVers on

  val producerTopKEmbedd ngsByFollowScorePathPref x: Str ng =
    "/producer_top_k_s mcluster_embedd ngs_by_follow_score_"

  val clusterTopKProducersByFollowScorePathPref x: Str ng =
    "/s mcluster_embedd ng_top_k_producers_by_follow_score_"

  def producerTopKS mclusterEmbedd ngsByFollowScoreDataset: KeyValDALDataset[
    KeyVal[Long, TopS mClustersW hScore]
  ]

  def s mclusterEmbedd ngTopKProducersByFollowScoreDataset: KeyValDALDataset[
    KeyVal[Pers stedFullCluster d, TopProducersW hScore]
  ]

  def get nterested nFn: (DateRange, T  Zone) => TypedP pe[(Long, ClustersUser s nterested n)]

  val m nNumFollo rs:  nt = m nNumFollo rsForProducer

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val producerTopKEmbedd ngsByFollowScorePath: Str ng =
      rootPath + producerTopKEmbedd ngsByFollowScorePathPref x + ModelVers ons
        .toKnownForModelVers on(modelVers on)

    val clusterTopKProducersByFollowScorePath: Str ng =
      rootPath + clusterTopKProducersByFollowScorePathPref x + ModelVers ons
        .toKnownForModelVers on(modelVers on)

    val producerClusterEmbedd ngByFollowScore = getProducerClusterEmbedd ng(
      get nterested nFn(dateRange.emb ggen(Days(5)), t  Zone),
      DataS ces.userUserNormal zedGraphS ce,
      DataS ces.userNormsAndCounts,
      userToProducerFollowScore,
      userToClusterFollowScore, // Follow score
      _.follo rCount.ex sts(_ > m nNumFollo rs),
      numReducersForMatr xMult pl cat on,
      modelVers on,
      cos neS m lar yThreshold
    ).forceToD sk

    wr eOutput(
      producerClusterEmbedd ngByFollowScore,
      producerTopKS mclusterEmbedd ngsByFollowScoreDataset,
      s mclusterEmbedd ngTopKProducersByFollowScoreDataset,
      producerTopKEmbedd ngsByFollowScorePath,
      clusterTopKProducersByFollowScorePath,
      modelVers on
    )
  }
}

/**
 capesospy-v2 update --bu ld_locally --start_cron \
 --start_cron producer_embedd ngs_from_ nterested_ n_by_fav_score \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object ProducerEmbedd ngsFrom nterested nByFavScoreBatchApp
    extends ProducerEmbedd ngsFrom nterested nByFavScoreBase {
  overr de def modelVers on: ModelVers on = ModelVers on.Model20m145kUpdated

  overr de def get nterested nFn: (
    DateRange,
    T  Zone
  ) => TypedP pe[(User d, ClustersUser s nterested n)] =
     nterested nS ces.s mClusters nterested nUpdatedS ce

  overr de val f rstT  : R chDate = R chDate("2019-09-10")

  overr de val batch ncre nt: Durat on = Days(7)

  overr de def producerTopKS mclusterEmbedd ngsByFavScoreDataset: KeyValDALDataset[
    KeyVal[Long, TopS mClustersW hScore]
  ] =
    ProducerTopKS mclusterEmbedd ngsByFavScoreUpdatedScalaDataset

  overr de def s mclusterEmbedd ngTopKProducersByFavScoreDataset: KeyValDALDataset[
    KeyVal[Pers stedFullCluster d, TopProducersW hScore]
  ] =
    S mclusterEmbedd ngTopKProducersByFavScoreUpdatedScalaDataset
}

/**
capesospy-v2 update --bu ld_locally --start_cron \
 --start_cron producer_embedd ngs_from_ nterested_ n_by_fav_score_2020 \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object ProducerEmbedd ngsFrom nterested nByFavScore2020BatchApp
    extends ProducerEmbedd ngsFrom nterested nByFavScoreBase {
  overr de def modelVers on: ModelVers on = ModelVers on.Model20m145k2020

  overr de def get nterested nFn: (
    DateRange,
    T  Zone
  ) => TypedP pe[(User d, ClustersUser s nterested n)] =
     nterested nS ces.s mClusters nterested n2020S ce

  overr de val f rstT  : R chDate = R chDate("2021-03-01")

  overr de val batch ncre nt: Durat on = Days(7)

  overr de def producerTopKS mclusterEmbedd ngsByFavScoreDataset: KeyValDALDataset[
    KeyVal[Long, TopS mClustersW hScore]
  ] =
    ProducerTopKS mclusterEmbedd ngsByFavScore2020ScalaDataset

  overr de def s mclusterEmbedd ngTopKProducersByFavScoreDataset: KeyValDALDataset[
    KeyVal[Pers stedFullCluster d, TopProducersW hScore]
  ] =
    S mclusterEmbedd ngTopKProducersByFavScore2020ScalaDataset
}

/**
capesospy-v2 update --bu ld_locally --start_cron \
 --start_cron producer_embedd ngs_from_ nterested_ n_by_fav_score_dec11 \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object ProducerEmbedd ngsFrom nterested nByFavScoreDec11BatchApp
    extends ProducerEmbedd ngsFrom nterested nByFavScoreBase {
  overr de def modelVers on: ModelVers on = ModelVers on.Model20m145kDec11

  overr de def get nterested nFn: (
    DateRange,
    T  Zone
  ) => TypedP pe[(User d, ClustersUser s nterested n)] =
     nterested nS ces.s mClusters nterested nDec11S ce

  overr de val f rstT  : R chDate = R chDate("2019-11-18")

  overr de val batch ncre nt: Durat on = Days(7)

  overr de def producerTopKS mclusterEmbedd ngsByFavScoreDataset: KeyValDALDataset[
    KeyVal[Long, TopS mClustersW hScore]
  ] =
    ProducerTopKS mclusterEmbedd ngsByFavScoreScalaDataset

  overr de def s mclusterEmbedd ngTopKProducersByFavScoreDataset: KeyValDALDataset[
    KeyVal[Pers stedFullCluster d, TopProducersW hScore]
  ] =
    S mclusterEmbedd ngTopKProducersByFavScoreScalaDataset
}

/**
capesospy-v2 update --bu ld_locally --start_cron \
 --start_cron producer_embedd ngs_from_ nterested_ n_by_follow_score \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object ProducerEmbedd ngsFrom nterested nByFollowScoreBatchApp
    extends ProducerEmbedd ngsFrom nterested nByFollowScoreBase {
  overr de def modelVers on: ModelVers on = ModelVers on.Model20m145kUpdated

  overr de def get nterested nFn: (
    DateRange,
    T  Zone
  ) => TypedP pe[(User d, ClustersUser s nterested n)] =
     nterested nS ces.s mClusters nterested nUpdatedS ce

  overr de val f rstT  : R chDate = R chDate("2019-09-10")

  overr de val batch ncre nt: Durat on = Days(7)

  overr de def producerTopKS mclusterEmbedd ngsByFollowScoreDataset: KeyValDALDataset[
    KeyVal[Long, TopS mClustersW hScore]
  ] =
    ProducerTopKS mclusterEmbedd ngsByFollowScoreUpdatedScalaDataset

  overr de def s mclusterEmbedd ngTopKProducersByFollowScoreDataset: KeyValDALDataset[
    KeyVal[Pers stedFullCluster d, TopProducersW hScore]
  ] =
    S mclusterEmbedd ngTopKProducersByFollowScoreUpdatedScalaDataset
}

/**
capesospy-v2 update --bu ld_locally --start_cron \
 --start_cron producer_embedd ngs_from_ nterested_ n_by_follow_score_2020 \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object ProducerEmbedd ngsFrom nterested nByFollowScore2020BatchApp
    extends ProducerEmbedd ngsFrom nterested nByFollowScoreBase {
  overr de def modelVers on: ModelVers on = ModelVers on.Model20m145k2020

  overr de def get nterested nFn: (
    DateRange,
    T  Zone
  ) => TypedP pe[(User d, ClustersUser s nterested n)] =
     nterested nS ces.s mClusters nterested n2020S ce

  overr de val f rstT  : R chDate = R chDate("2021-03-01")

  overr de val batch ncre nt: Durat on = Days(7)

  overr de def producerTopKS mclusterEmbedd ngsByFollowScoreDataset: KeyValDALDataset[
    KeyVal[Long, TopS mClustersW hScore]
  ] =
    ProducerTopKS mclusterEmbedd ngsByFollowScore2020ScalaDataset

  overr de def s mclusterEmbedd ngTopKProducersByFollowScoreDataset: KeyValDALDataset[
    KeyVal[Pers stedFullCluster d, TopProducersW hScore]
  ] =
    S mclusterEmbedd ngTopKProducersByFollowScore2020ScalaDataset
}

/**
capesospy-v2 update --bu ld_locally --start_cron \
 --start_cron producer_embedd ngs_from_ nterested_ n_by_follow_score_dec11 \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object ProducerEmbedd ngsFrom nterested nByFollowScoreDec11BatchApp
    extends ProducerEmbedd ngsFrom nterested nByFollowScoreBase {
  overr de def modelVers on: ModelVers on = ModelVers on.Model20m145kDec11

  overr de def get nterested nFn: (
    DateRange,
    T  Zone
  ) => TypedP pe[(User d, ClustersUser s nterested n)] =
     nterested nS ces.s mClusters nterested nDec11S ce

  overr de val f rstT  : R chDate = R chDate("2019-11-18")

  overr de val batch ncre nt: Durat on = Days(7)

  overr de def producerTopKS mclusterEmbedd ngsByFollowScoreDataset: KeyValDALDataset[
    KeyVal[Long, TopS mClustersW hScore]
  ] =
    ProducerTopKS mclusterEmbedd ngsByFollowScoreScalaDataset

  overr de def s mclusterEmbedd ngTopKProducersByFollowScoreDataset: KeyValDALDataset[
    KeyVal[Pers stedFullCluster d, TopProducersW hScore]
  ] =
    S mclusterEmbedd ngTopKProducersByFollowScoreScalaDataset
}

/**
 * Adhoc job to calculate producer's s mcluster embedd ngs, wh ch essent ally ass gns  nterested n
 * S mClusters to each producer, regardless of w t r t  producer has a knownFor ass gn nt.
 *
$ ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng:producer_embedd ngs_from_ nterested_ n-adhoc

 $ scald ng remote run \
 --ma n-class com.tw ter.s mclusters_v2.scald ng.embedd ng.ProducerEmbedd ngsFrom nterested nAdhocApp \
 --target src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng:producer_embedd ngs_from_ nterested_ n-adhoc \
 --user cassowary --cluster blueb rd-qus1 \
 --keytab /var/l b/tss/keys/fluffy/keytabs/cl ent/cassowary.keytab \
 --pr nc pal serv ce_acoount@TW TTER.B Z \
 -- --date 2020-08-25 --model_vers on 20M_145K_updated \
 --outputD r /gcs/user/cassowary/adhoc/producerEmbedd ngs/

 */
object ProducerEmbedd ngsFrom nterested nAdhocApp extends AdhocExecut onApp {

   mport ProducerEmbedd ngsFrom nterested n._

  pr vate val numReducersForMatr xMult pl cat on = 12000

  /**
   * Calculate t  embedd ng and wr es t  results keyed by producers and clusters separately  nto
   *  nd v dual locat ons
   */
  pr vate def runAdhocByScore(
     nterested nClusters: TypedP pe[(Long, ClustersUser s nterested n)],
    userUserNormalGraph: TypedP pe[UserAndNe ghbors],
    userNormsAndCounts: TypedP pe[NormsAndCounts],
    keyedByProducerS nkPath: Str ng,
    keyedByClusterS nkPath: Str ng,
    userToProducerScor ngFn: Ne ghborW h  ghts => Double,
    userToClusterScor ngFn: UserTo nterested nClusterScores => Double,
    userF lter: NormsAndCounts => Boolean,
    modelVers on: ModelVers on
  )(
     mpl c  un que D: Un que D
  ): Execut on[Un ] = {

    val producerClusterEmbedd ng = getProducerClusterEmbedd ng(
       nterested nClusters,
      userUserNormalGraph,
      userNormsAndCounts,
      userToProducerScor ngFn,
      userToClusterScor ngFn,
      userF lter,
      numReducersForMatr xMult pl cat on,
      modelVers on,
      cos neS m lar yThreshold
    ).forceToD sk

    val keyByProducerExec =
      toS mClusterEmbedd ng(producerClusterEmbedd ng, topKClustersToKeep, modelVers on)
        .wr eExecut on(
          AdhocKeyValS ces.topProducerToClusterEmbedd ngsS ce(keyedByProducerS nkPath))

    val keyByClusterExec =
      fromS mClusterEmbedd ng(producerClusterEmbedd ng, topKUsersToKeep, modelVers on)
        .map { case (cluster d, topProducers) => (cluster d, topProducersToThr ft(topProducers)) }
        .wr eExecut on(
          AdhocKeyValS ces.topClusterEmbedd ngsToProducerS ce(keyedByClusterS nkPath))

    Execut on.z p(keyByProducerExec, keyByClusterExec).un 
  }

  // Calculate t  embedd ngs us ng follow scores
  pr vate def runFollowScore(
     nterested nClusters: TypedP pe[(Long, ClustersUser s nterested n)],
    userUserNormalGraph: TypedP pe[UserAndNe ghbors],
    userNormsAndCounts: TypedP pe[NormsAndCounts],
    modelVers on: ModelVers on,
    outputD r: Str ng
  )(
     mpl c  un que D: Un que D
  ): Execut on[Un ] = {
    val keyByClusterS nkPath = outputD r + "keyedByCluster/byFollowScore_" + modelVers on
    val keyByProducerS nkPath = outputD r + "keyedByProducer/byFollowScore_" + modelVers on

    runAdhocByScore(
       nterested nClusters,
      userUserNormalGraph,
      userNormsAndCounts,
      keyedByProducerS nkPath = keyByProducerS nkPath,
      keyedByClusterS nkPath = keyByClusterS nkPath,
      userToProducerScor ngFn = userToProducerFollowScore,
      userToClusterScor ngFn = userToClusterFollowScore,
      _.follo rCount.ex sts(_ > m nNumFollo rsForProducer),
      modelVers on
    )
  }

  // Calculate t  embedd ngs us ng fav scores
  pr vate def runFavScore(
     nterested nClusters: TypedP pe[(Long, ClustersUser s nterested n)],
    userUserNormalGraph: TypedP pe[UserAndNe ghbors],
    userNormsAndCounts: TypedP pe[NormsAndCounts],
    modelVers on: ModelVers on,
    outputD r: Str ng
  )(
     mpl c  un que D: Un que D
  ): Execut on[Un ] = {
    val keyByClusterS nkPath = outputD r + "keyedByCluster/byFavScore_" + modelVers on
    val keyByProducerS nkPath = outputD r + "keyedByProducer/byFavScore_" + modelVers on

    runAdhocByScore(
       nterested nClusters,
      userUserNormalGraph,
      userNormsAndCounts,
      keyedByProducerS nkPath = keyByProducerS nkPath,
      keyedByClusterS nkPath = keyByClusterS nkPath,
      userToProducerScor ngFn = userToProducerFavScore,
      userToClusterScor ngFn = userToClusterFavScore,
      _.faverCount.ex sts(_ > m nNumFaversForProducer),
      modelVers on
    )
  }

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val outputD r = args("outputD r")

    val modelVers on =
      ModelVers ons.toModelVers on(args.requ red("model_vers on"))

    val  nterested nClusters = modelVers on match {
      case ModelVers on.Model20m145k2020 =>
         nterested nS ces.s mClusters nterested n2020S ce(dateRange, t  Zone).forceToD sk
      case ModelVers on.Model20m145kUpdated =>
         nterested nS ces.s mClusters nterested nUpdatedS ce(dateRange, t  Zone).forceToD sk
      case _ =>
         nterested nS ces.s mClusters nterested nDec11S ce(dateRange, t  Zone).forceToD sk
    }

    Execut on
      .z p(
        runFavScore(
           nterested nClusters,
          DataS ces.userUserNormal zedGraphS ce,
          DataS ces.userNormsAndCounts,
          modelVers on,
          outputD r
        ),
        runFollowScore(
           nterested nClusters,
          DataS ces.userUserNormal zedGraphS ce,
          DataS ces.userNormsAndCounts,
          modelVers on,
          outputD r
        )
      ).un 
  }
}

/**
 * Computes t  producer's  nterested n cluster embedd ng.  .e.  f a t et author (producer)  s not
 * assoc ated w h a KnownFor cluster, do a cross-product bet en
 * [user,  nterested n] and [user, producer] to f nd t  s m lar y matr x [ nterested n, producer].
 */
object ProducerEmbedd ngsFrom nterested n {
  val m nNumFollo rsForProducer:  nt = 100
  val m nNumFaversForProducer:  nt = 100
  val topKUsersToKeep:  nt = 300
  val topKClustersToKeep:  nt = 60
  val cos neS m lar yThreshold: Double = 0.01

  type Cluster d =  nt

  def topProducersToThr ft(producersW hScore: Seq[(User d, Double)]): TopProducersW hScore = {
    val thr ft = producersW hScore.map { producer =>
      TopProducerW hScore(producer._1, producer._2)
    }
    TopProducersW hScore(thr ft)
  }

  def userToProducerFavScore(ne ghbor: Ne ghborW h  ghts): Double = {
    ne ghbor.favScoreHalfL fe100DaysNormal zedByNe ghborFaversL2.getOrElse(0.0)
  }

  def userToProducerFollowScore(ne ghbor: Ne ghborW h  ghts): Double = {
    ne ghbor.followScoreNormal zedByNe ghborFollo rsL2.getOrElse(0.0)
  }

  def userToClusterFavScore(clusterScore: UserTo nterested nClusterScores): Double = {
    clusterScore.favScoreClusterNormal zedOnly.getOrElse(0.0)
  }

  def userToClusterFollowScore(clusterScore: UserTo nterested nClusterScores): Double = {
    clusterScore.followScoreClusterNormal zedOnly.getOrElse(0.0)
  }

  def getUserS mClustersMatr x(
    s mClustersS ce: TypedP pe[(User d, ClustersUser s nterested n)],
    extractScore: UserTo nterested nClusterScores => Double,
    modelVers on: ModelVers on
  ): TypedP pe[(User d, Seq[( nt, Double)])] = {
    s mClustersS ce.collect {
      case (user d, clusters)
           f ModelVers ons.toModelVers on(clusters.knownForModelVers on).equals(modelVers on) =>
        user d -> clusters.cluster dToScores
          .map {
            case (cluster d, clusterScores) =>
              (cluster d, extractScore(clusterScores))
          }.toSeq.f lter(_._2 > 0)
    }
  }

  /**
   * G ven a   ghted user-producer engage nt  tory matr x, as  ll as a
   *   ghted user- nterested nCluster matr x, do t  matr x mult pl cat on to y eld a   ghted
   * producer-cluster embedd ng matr x
   */
  def getProducerClusterEmbedd ng(
     nterested nClusters: TypedP pe[(User d, ClustersUser s nterested n)],
    userProducerEngage ntGraph: TypedP pe[UserAndNe ghbors],
    userNormsAndCounts: TypedP pe[NormsAndCounts],
    userToProducerScor ngFn: Ne ghborW h  ghts => Double,
    userToClusterScor ngFn: UserTo nterested nClusterScores => Double,
    userF lter: NormsAndCounts => Boolean, // funct on to dec de w t r to compute embedd ngs for t  user or not
    numReducersForMatr xMult pl cat on:  nt,
    modelVers on: ModelVers on,
    threshold: Double
  )(
     mpl c  u d: Un que D
  ): TypedP pe[((Cluster d, User d), Double)] = {
    val userS mClustersMatr x = getUserS mClustersMatr x(
       nterested nClusters,
      userToClusterScor ngFn,
      modelVers on
    )

    val userUserNormal zedGraph = getF lteredUserUserNormal zedGraph(
      userProducerEngage ntGraph,
      userNormsAndCounts,
      userToProducerScor ngFn,
      userF lter
    )

    S mClustersEmbedd ngJob
      .legacyMult plyMatr ces(
        userUserNormal zedGraph,
        userS mClustersMatr x,
        numReducersForMatr xMult pl cat on
      )
      .f lter(_._2 >= threshold)
  }

  def getF lteredUserUserNormal zedGraph(
    userProducerEngage ntGraph: TypedP pe[UserAndNe ghbors],
    userNormsAndCounts: TypedP pe[NormsAndCounts],
    userToProducerScor ngFn: Ne ghborW h  ghts => Double,
    userF lter: NormsAndCounts => Boolean
  )(
     mpl c  u d: Un que D
  ): TypedP pe[(User d, (User d, Double))] = {
    val numUsersCount = Stat("num_users_w h_engage nts")
    val userUserF lteredEdgeCount = Stat("num_f ltered_user_user_engage nts")
    val val dUsersCount = Stat("num_val d_users")

    val val dUsers = userNormsAndCounts.collect {
      case user  f userF lter(user) =>
        val dUsersCount. nc()
        user.user d
    }

    userProducerEngage ntGraph
      .flatMap { userAndNe ghbors =>
        numUsersCount. nc()
        userAndNe ghbors.ne ghbors
          .map { ne ghbor =>
            userUserF lteredEdgeCount. nc()
            (ne ghbor.ne ghbor d, (userAndNe ghbors.user d, userToProducerScor ngFn(ne ghbor)))
          }
          .f lter(_._2._2 > 0.0)
      }
      .jo n(val dUsers.asKeys)
      .map {
        case (ne ghbor d, ((user d, score), _)) =>
          (user d, (ne ghbor d, score))
      }
  }

  def fromS mClusterEmbedd ng[T, E](
    resultMatr x: TypedP pe[((Cluster d, T), Double)],
    topK:  nt,
    modelVers on: ModelVers on
  ): TypedP pe[(Pers stedFullCluster d, Seq[(T, Double)])] = {
    resultMatr x
      .map {
        case ((cluster d,  nput d), score) => (cluster d, ( nput d, score))
      }
      .group
      .sortedReverseTake(topK)(Order ng.by(_._2))
      .map {
        case (cluster d, topEnt  esW hScore) =>
          Pers stedFullCluster d(modelVers on, cluster d) -> topEnt  esW hScore
      }
  }

  def toS mClusterEmbedd ng[T](
    resultMatr x: TypedP pe[((Cluster d, T), Double)],
    topK:  nt,
    modelVers on: ModelVers on
  )(
     mpl c  order ng: Order ng[T]
  ): TypedP pe[(T, TopS mClustersW hScore)] = {
    resultMatr x
      .map {
        case ((cluster d,  nput d), score) => ( nput d, (cluster d, score))
      }
      .group
      //.w hReducers(3000) // uncom nt for producer-s mclusters job
      .sortedReverseTake(topK)(Order ng.by(_._2))
      .map {
        case ( nput d, topS mClustersW hScore) =>
          val topS mClusters = topS mClustersW hScore.map {
            case (cluster d, score) => S mClusterW hScore(cluster d, score)
          }
           nput d -> TopS mClustersW hScore(topS mClusters, modelVers on)
      }
  }
}
