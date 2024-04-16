package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.algeb rd.Sem group
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.scald ng.TypedP pe
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Analyt csBatchExecut on
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Analyt csBatchExecut onArgs
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.BatchDescr pt on
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.BatchF rstT  
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Batch ncre nt
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Tw terSc duledExecut onApp
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala._

/**
 * T  f le  mple nts t  job for comput ng users'  nterested n vector from KnownFor data set.
 *
 *   reads t  UserUserNormal zedGraphScalaDataset to get user-user follow + fav graph, and t n
 * based on t  known-for clusters of each follo d/faved user,   calculate how much a user  s
 *  nterested n a cluster.
 */

/**
 * Product on job for comput ng  nterested n data set for t  model vers on 20M145K2020.
 *
 * To deploy t  job:
 *
 * capesospy-v2 update --bu ld_locally --start_cron  nterested_ n_for_20M_145k_2020 \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object  nterested nFromKnownFor20M145K2020 extends  nterested nFromKnownForBatchBase {
  overr de val f rstT  : Str ng = "2020-10-06"
  overr de val outputKVDataset: KeyValDALDataset[KeyVal[Long, ClustersUser s nterested n]] =
    S mclustersV2Raw nterested n20M145K2020ScalaDataset
  overr de val outputPath: Str ng =  nternalDataPaths.Raw nterested n2020Path
  overr de val knownForModelVers on: Str ng = ModelVers ons.Model20M145K2020
  overr de val knownForDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUser sKnownFor]] =
    S mclustersV2KnownFor20M145K2020ScalaDataset
}

/**
 * base class for t  ma n log c of comput ng  nterested n from KnownFor data set.
 */
tra   nterested nFromKnownForBatchBase extends Tw terSc duledExecut onApp {
   mpl c  val tz = DateOps.UTC
   mpl c  val parser = DateParser.default

  def f rstT  : Str ng
  val batch ncre nt: Durat on = Days(7)
  val lookBackDays: Durat on = Days(30)

  def outputKVDataset: KeyValDALDataset[KeyVal[Long, ClustersUser s nterested n]]
  def outputPath: Str ng
  def knownForModelVers on: Str ng
  def knownForDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUser sKnownFor]]

  pr vate lazy val execArgs = Analyt csBatchExecut onArgs(
    batchDesc = BatchDescr pt on(t .getClass.getNa .replace("$", "")),
    f rstT   = BatchF rstT  (R chDate(f rstT  )),
    lastT   = None,
    batch ncre nt = Batch ncre nt(batch ncre nt)
  )

  overr de def sc duledJob: Execut on[Un ] = Analyt csBatchExecut on(execArgs) {
     mpl c  dateRange =>
      Execut on.w h d {  mpl c  un que d =>
        Execut on.w hArgs { args =>
          val normal zedGraph =
            DAL.readMostRecentSnapshot(UserUserNormal zedGraphScalaDataset).toTypedP pe
          val knownFor = KnownForS ces.fromKeyVal(
            DAL.readMostRecentSnapshot(knownForDALDataset, dateRange.extend(Days(30))).toTypedP pe,
            knownForModelVers on
          )

          val soc alProofThreshold = args. nt("soc alProofThreshold", 2)
          val maxClustersPerUser = args. nt("maxClustersPerUser", 50)

          val result =  nterested nFromKnownFor
            .run(
              normal zedGraph,
              knownFor,
              soc alProofThreshold,
              maxClustersPerUser,
              knownForModelVers on
            )

          val wr eKeyValResultExec = result
            .map { case (user d, clusters) => KeyVal(user d, clusters) }
            .wr eDALVers onedKeyValExecut on(
              outputKVDataset,
              D.Suff x(outputPath)
            )

          // read prev ous data set for val dat on purpose
          val prev ousDataset =  f (R chDate(f rstT  ).t  stamp != dateRange.start.t  stamp) {
            DAL
              .readMostRecentSnapshot(outputKVDataset, dateRange.prepend(lookBackDays)).toTypedP pe
              .map {
                case KeyVal(user,  nterested n) =>
                  (user,  nterested n)
              }
          } else {
            TypedP pe.empty
          }

          Ut l.pr ntCounters(
            Execut on
              .z p(
                wr eKeyValResultExec,
                 nterested nFromKnownFor.dataSetStats(result, "NewResult"),
                 nterested nFromKnownFor.dataSetStats(prev ousDataset, "OldResult")
              ).un 
          )
        }
      }
  }
}

/**
 * Adhoc job to compute user  nterested n.
 *
 * scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng: nterested_ n_adhoc \
 * --user recos-platform \
 * --subm ter hadoopnest2.atla.tw ter.com \
 * --ma n-class com.tw ter.s mclusters_v2.scald ng. nterested nFromKnownForAdhoc -- \
 * --date 2019-08-26  --outputD r /user/recos-platform/adhoc/s mclusters_ nterested_ n_log_fav
 */
object  nterested nFromKnownForAdhoc extends Tw terExecut onApp {
  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val normal zedGraph = TypedP pe.from(
            UserAndNe ghborsF xedPathS ce(args("graph nputD r"))
          )
          val soc alProofThreshold = args. nt("soc alProofThreshold", 2)
          val maxClustersPerUser = args. nt("maxClustersPerUser", 20)
          val knownForModelVers on = args("knownForModelVers on")
          val knownFor = KnownForS ces.readKnownFor(args("knownFor nputD r"))

          val outputS nk = AdhocKeyValS ces. nterested nS ce(args("outputD r"))
          Ut l.pr ntCounters(
             nterested nFromKnownFor
              .run(
                normal zedGraph,
                knownFor,
                soc alProofThreshold,
                maxClustersPerUser,
                knownForModelVers on
              ).wr eExecut on(outputS nk)
          )
        }
    }
}

/**
 * Adhoc job to c ck t  output of an adhoc  nterested nS ce.
 */
object Dump nterested nAdhoc extends Tw terExecut onApp {
  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val users = args.l st("users").map(_.toLong).toSet
          val  nput = TypedP pe.from(AdhocKeyValS ces. nterested nS ce(args(" nputD r")))
           nput.f lter { case (user d, rec) => users.conta ns(user d) }.to erableExecut on.map {
            s => pr ntln(s.map(Ut l.prettyJsonMapper.wr eValueAsStr ng).mkStr ng("\n"))
          }
        }
    }
}

/**
 *  lper funct ons
 */
object  nterested nFromKnownFor {
  pr vate def  fNanMake0(x: Double): Double =  f (x. sNaN) 0.0 else x

  case class SrcCluster nter d ate nfo(
    followScore: Double,
    followScoreProducerNormal zed: Double,
    favScore: Double,
    favScoreProducerNormal zed: Double,
    logFavScore: Double,
    logFavScoreProducerNormal zed: Double,
    followSoc alProof: L st[Long],
    favSoc alProof: L st[Long]) {
    // overr d ng for t  sake of un  tests
    overr de def equals(obj: scala.Any): Boolean = {
      obj match {
        case that: SrcCluster nter d ate nfo =>
          math.abs(followScore - that.followScore) < 1e-5 &&
            math.abs(followScoreProducerNormal zed - that.followScoreProducerNormal zed) < 1e-5 &&
            math.abs(favScore - that.favScore) < 1e-5 &&
            math.abs(favScoreProducerNormal zed - that.favScoreProducerNormal zed) < 1e-5 &&
            math.abs(logFavScore - that.logFavScore) < 1e-5 &&
            math.abs(logFavScoreProducerNormal zed - that.logFavScoreProducerNormal zed) < 1e-5 &&
            followSoc alProof.toSet == that.followSoc alProof.toSet &&
            favSoc alProof.toSet == that.favSoc alProof.toSet
        case _ => false
      }
    }
  }

   mpl c  object SrcCluster nter d ate nfoSem group
      extends Sem group[SrcCluster nter d ate nfo] {
    overr de def plus(
      left: SrcCluster nter d ate nfo,
      r ght: SrcCluster nter d ate nfo
    ): SrcCluster nter d ate nfo = {
      SrcCluster nter d ate nfo(
        followScore = left.followScore + r ght.followScore,
        followScoreProducerNormal zed =
          left.followScoreProducerNormal zed + r ght.followScoreProducerNormal zed,
        favScore = left.favScore + r ght.favScore,
        favScoreProducerNormal zed =
          left.favScoreProducerNormal zed + r ght.favScoreProducerNormal zed,
        logFavScore = left.logFavScore + r ght.logFavScore,
        logFavScoreProducerNormal zed =
          left.logFavScoreProducerNormal zed + r ght.logFavScoreProducerNormal zed,
        followSoc alProof =
          Sem group.plus(left.followSoc alProof, r ght.followSoc alProof).d st nct,
        favSoc alProof = Sem group.plus(left.favSoc alProof, r ght.favSoc alProof).d st nct
      )
    }
  }

  /**
   * @param adjacencyL sts User-User follow/fav graph
   * @param knownFor KnownFor data set. Each user can be known for several clusters w h certa n
   *                 knownFor   ghts.
   * @param soc alProofThreshold A user w ll only be  nterested  n a cluster  f t y follow/fav at
   *                             least certa n number of users known for t  cluster.
   * @param un que d requ red for t se Stat
   * @return
   */
  def userClusterPa rsW houtNormal zat on(
    adjacencyL sts: TypedP pe[UserAndNe ghbors],
    knownFor: TypedP pe[(Long, Array[( nt, Float)])],
    soc alProofThreshold:  nt
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[((Long,  nt), SrcCluster nter d ate nfo)] = {
    val edgesToUsersW hKnownFor = Stat("num_edges_to_users_w h_known_for")
    val srcDestClusterTr ples = Stat("num_src_dest_cluster_tr ples")
    val srcClusterPa rsBeforeSoc alProofThreshold ng =
      Stat("num_src_cluster_pa rs_before_soc al_proof_threshold ng")
    val srcClusterPa rsAfterSoc alProofThreshold ng =
      Stat("num_src_cluster_pa rs_after_soc al_proof_threshold ng")

    val edges = adjacencyL sts.flatMap {
      case UserAndNe ghbors(src d, ne ghborsW h  ghts) =>
        ne ghborsW h  ghts.map { ne ghborW h  ghts =>
          (
            ne ghborW h  ghts.ne ghbor d,
            ne ghborW h  ghts.copy(ne ghbor d = src d)
          )
        }
    }

     mpl c  val l2b: Long => Array[Byte] =  nject on.long2B gEnd an

    edges
      .sketch(4000)
      .jo n(knownFor)
      .flatMap {
        case (dest d, (srcW h  ghts, clusterArray)) =>
          edgesToUsersW hKnownFor. nc()
          clusterArray.toL st.map {
            case (cluster d, knownForScoreF) =>
              val knownForScore = math.max(0.0, knownForScoreF.toDouble)

              srcDestClusterTr ples. nc()
              val followScore =
                 f (srcW h  ghts. sFollo d.conta ns(true)) knownForScore else 0.0
              val followScoreProducerNormal zedOnly =
                srcW h  ghts.followScoreNormal zedByNe ghborFollo rsL2.getOrElse(
                  0.0) * knownForScore
              val favScore =
                srcW h  ghts.favScoreHalfL fe100Days.getOrElse(0.0) * knownForScore

              val favScoreProducerNormal zedOnly =
                srcW h  ghts.favScoreHalfL fe100DaysNormal zedByNe ghborFaversL2.getOrElse(
                  0.0) * knownForScore

              val logFavScore = srcW h  ghts.logFavScore.getOrElse(0.0) * knownForScore

              val logFavScoreProducerNormal zedOnly = srcW h  ghts.logFavScoreL2Normal zed
                .getOrElse(0.0) * knownForScore

              val followSoc alProof =  f (srcW h  ghts. sFollo d.conta ns(true)) {
                L st(dest d)
              } else N l
              val favSoc alProof =  f (srcW h  ghts.favScoreHalfL fe100Days.ex sts(_ > 0)) {
                L st(dest d)
              } else N l

              (
                (srcW h  ghts.ne ghbor d, cluster d),
                SrcCluster nter d ate nfo(
                  followScore,
                  followScoreProducerNormal zedOnly,
                  favScore,
                  favScoreProducerNormal zedOnly,
                  logFavScore,
                  logFavScoreProducerNormal zedOnly,
                  followSoc alProof,
                  favSoc alProof
                )
              )
          }
      }
      .sumByKey
      .w hReducers(10000)
      .f lter {
        case ((_, _), SrcCluster nter d ate nfo(_, _, _, _, _, _, followProof, favProof)) =>
          srcClusterPa rsBeforeSoc alProofThreshold ng. nc()
          val d st nctSoc alProof = (followProof ++ favProof).toSet
          val result = d st nctSoc alProof.s ze >= soc alProofThreshold
           f (result) {
            srcClusterPa rsAfterSoc alProofThreshold ng. nc()
          }
          result
      }
  }

  /**
   * Add t  cluster-level l2 norm scores, and use t m to normal ze follow/fav scores.
   */
  def attachNormal zedScores(
     nter d ate: TypedP pe[((Long,  nt), SrcCluster nter d ate nfo)]
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[(Long, L st[( nt, UserTo nterested nClusterScores)])] = {

    def square(x: Double): Double = x * x

    val clusterCountsAndNorms =
       nter d ate
        .map {
          case (
                (_, cluster d),
                SrcCluster nter d ate nfo(
                  followScore,
                  followScoreProducerNormal zedOnly,
                  favScore,
                  favScoreProducerNormal zedOnly,
                  logFavScore,
                  logFavScoreProducerNormal zedOnly,
                  _,
                  _
                )
              ) =>
            (
              cluster d,
              (
                1,
                square(followScore),
                square(followScoreProducerNormal zedOnly),
                square(favScore),
                square(favScoreProducerNormal zedOnly),
                square(logFavScore),
                square(logFavScoreProducerNormal zedOnly)
              )
            )
        }
        .sumByKey
        //        .w hReducers(100)
        .map {
          case (
                cluster d,
                (
                  cnt,
                  squareFollowScore,
                  squareFollowScoreProducerNormal zedOnly,
                  squareFavScore,
                  squareFavScoreProducerNormal zedOnly,
                  squareLogFavScore,
                  squareLogFavScoreProducerNormal zedOnly
                )) =>
            (
              cluster d,
              (
                cnt,
                math.sqrt(squareFollowScore),
                math.sqrt(squareFollowScoreProducerNormal zedOnly),
                math.sqrt(squareFavScore),
                math.sqrt(squareFavScoreProducerNormal zedOnly),
                math.sqrt(squareLogFavScore),
                math.sqrt(squareLogFavScoreProducerNormal zedOnly)
              ))
        }

     mpl c  val  2b:  nt => Array[Byte] =  nject on. nt2B gEnd an

     nter d ate
      .map {
        case ((src d, cluster d), clusterScoresTuple) =>
          (cluster d, (src d, clusterScoresTuple))
      }
      .sketch(reducers = 900)
      .jo n(clusterCountsAndNorms)
      .map {
        case (
              cluster d,
              (
                (
                  src d,
                  SrcCluster nter d ate nfo(
                    followScore,
                    followScoreProducerNormal zedOnly,
                    favScore,
                    favScoreProducerNormal zedOnly,
                    logFavScore,
                    logFavScoreProducerNormal zedOnly, // not used for now
                    followProof,
                    favProof
                  )
                ),
                (
                  cnt,
                  followNorm,
                  followProducerNormal zedNorm,
                  favNorm,
                  favProducerNormal zedNorm,
                  logFavNorm,
                  logFavProducerNormal zedNorm // not used for now
                )
              )
            ) =>
          (
            src d,
            L st(
              (
                cluster d,
                UserTo nterested nClusterScores(
                  followScore = So ( fNanMake0(followScore)),
                  followScoreClusterNormal zedOnly = So ( fNanMake0(followScore / followNorm)),
                  followScoreProducerNormal zedOnly =
                    So ( fNanMake0(followScoreProducerNormal zedOnly)),
                  followScoreClusterAndProducerNormal zed = So (
                     fNanMake0(followScoreProducerNormal zedOnly / followProducerNormal zedNorm)),
                  favScore = So ( fNanMake0(favScore)),
                  favScoreClusterNormal zedOnly = So ( fNanMake0(favScore / favNorm)),
                  favScoreProducerNormal zedOnly = So ( fNanMake0(favScoreProducerNormal zedOnly)),
                  favScoreClusterAndProducerNormal zed =
                    So ( fNanMake0(favScoreProducerNormal zedOnly / favProducerNormal zedNorm)),
                  usersBe ngFollo d = So (followProof),
                  usersThat reFaved = So (favProof),
                  numUsers nterested nT ClusterUpperBound = So (cnt),
                  logFavScore = So ( fNanMake0(logFavScore)),
                  logFavScoreClusterNormal zedOnly = So ( fNanMake0(logFavScore / logFavNorm))
                ))
            )
          )
      }
      .sumByKey
      //      .w hReducers(1000)
      .toTypedP pe
  }

  /**
   * aggregate cluster scores for each user, to be used  nstead of attachNormal zedScores
   * w n   donot want to compute cluster-level l2 norm scores
   */
  def groupClusterScores(
     nter d ate: TypedP pe[((Long,  nt), SrcCluster nter d ate nfo)]
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[(Long, L st[( nt, UserTo nterested nClusterScores)])] = {

     nter d ate
      .map {
        case (
              (src d, cluster d),
              SrcCluster nter d ate nfo(
                followScore,
                followScoreProducerNormal zedOnly,
                favScore,
                favScoreProducerNormal zedOnly,
                logFavScore,
                logFavScoreProducerNormal zedOnly,
                followProof,
                favProof
              )
            ) =>
          (
            src d,
            L st(
              (
                cluster d,
                UserTo nterested nClusterScores(
                  followScore = So ( fNanMake0(followScore)),
                  followScoreProducerNormal zedOnly =
                    So ( fNanMake0(followScoreProducerNormal zedOnly)),
                  favScore = So ( fNanMake0(favScore)),
                  favScoreProducerNormal zedOnly = So ( fNanMake0(favScoreProducerNormal zedOnly)),
                  usersBe ngFollo d = So (followProof),
                  usersThat reFaved = So (favProof),
                  logFavScore = So ( fNanMake0(logFavScore)),
                ))
            )
          )
      }
      .sumByKey
      .w hReducers(1000)
      .toTypedP pe
  }

  /**
   * For each user, only keep up to a certa n number of clusters.
   * @param all nterests user w h a l st of  nterested n clusters.
   * @param maxClustersPerUser number of clusters to keep for each user
   * @param knownForModelVers on known for model vers on
   * @param un que d requ red for t se Stat
   * @return
   */
  def keepOnlyTopClusters(
    all nterests: TypedP pe[(Long, L st[( nt, UserTo nterested nClusterScores)])],
    maxClustersPerUser:  nt,
    knownForModelVers on: Str ng
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[(Long, ClustersUser s nterested n)] = {
    val userClusterPa rsBeforeUserTruncat on =
      Stat("num_user_cluster_pa rs_before_user_truncat on")
    val userClusterPa rsAfterUserTruncat on =
      Stat("num_user_cluster_pa rs_after_user_truncat on")
    val usersW hALotOfClusters =
      Stat(s"num_users_w h_more_than_${maxClustersPerUser}_clusters")

    all nterests
      .map {
        case (src d, fullClusterL st) =>
          userClusterPa rsBeforeUserTruncat on. ncBy(fullClusterL st.s ze)
          val truncatedClusters =  f (fullClusterL st.s ze > maxClustersPerUser) {
            usersW hALotOfClusters. nc()
            fullClusterL st
              .sortBy {
                case (_, clusterScores) =>
                  (
                    -clusterScores.favScore.getOrElse(0.0),
                    -clusterScores.logFavScore.getOrElse(0.0),
                    -clusterScores.followScore.getOrElse(0.0),
                    -clusterScores.logFavScoreClusterNormal zedOnly.getOrElse(0.0),
                    -clusterScores.followScoreProducerNormal zedOnly.getOrElse(0.0)
                  )
              }
              .take(maxClustersPerUser)
          } else {
            fullClusterL st
          }
          userClusterPa rsAfterUserTruncat on. ncBy(truncatedClusters.s ze)
          (src d, ClustersUser s nterested n(knownForModelVers on, truncatedClusters.toMap))
      }
  }

  def run(
    adjacencyL sts: TypedP pe[UserAndNe ghbors],
    knownFor: TypedP pe[(User d, Array[(Cluster d, Float)])],
    soc alProofThreshold:  nt,
    maxClustersPerUser:  nt,
    knownForModelVers on: Str ng
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {
    keepOnlyTopClusters(
      attachNormal zedScores(
        userClusterPa rsW houtNormal zat on(
          adjacencyL sts,
          knownFor,
          soc alProofThreshold
        )
      ),
      maxClustersPerUser,
      knownForModelVers on
    )
  }

  /**
   * run t   nterested n job, cluster normal zed scores are not attac d to user's clusters.
   */
  def runW houtClusterNormal zedScores(
    adjacencyL sts: TypedP pe[UserAndNe ghbors],
    knownFor: TypedP pe[(User d, Array[(Cluster d, Float)])],
    soc alProofThreshold:  nt,
    maxClustersPerUser:  nt,
    knownForModelVers on: Str ng
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {
    keepOnlyTopClusters(
      groupClusterScores(
        userClusterPa rsW houtNormal zat on(
          adjacencyL sts,
          knownFor,
          soc alProofThreshold
        )
      ),
      maxClustersPerUser,
      knownForModelVers on
    )
  }

  /**
   * pr nt out so  bas c stats of t  data set to make sure th ngs are not broken
   */
  def dataSetStats(
     nterested nData: TypedP pe[(User d, ClustersUser s nterested n)],
    dataSetNa : Str ng = ""
  ): Execut on[Un ] = {

    Execut on
      .z p(
        Ut l.pr ntSummaryOfNu r cColumn(
           nterested nData.map {
            case (user,  nterested n) =>
               nterested n.cluster dToScores.s ze
          },
          So (s"$dataSetNa  User nterested n S ze")
        ),
        Ut l.pr ntSummaryOfNu r cColumn(
           nterested nData.flatMap {
            case (user,  nterested n) =>
               nterested n.cluster dToScores.map {
                case (_, scores) =>
                  scores.favScore.getOrElse(0.0)
              }
          },
          So (s"$dataSetNa  User nterested n favScore")
        ),
        Ut l.pr ntSummaryOfNu r cColumn(
           nterested nData.flatMap {
            case (user,  nterested n) =>
               nterested n.cluster dToScores.map {
                case (_, scores) =>
                  scores.favScoreClusterNormal zedOnly.getOrElse(0.0)
              }
          },
          So (s"$dataSetNa  User nterested n favScoreClusterNormal zedOnly")
        ),
        Ut l.pr ntSummaryOfNu r cColumn(
           nterested nData.flatMap {
            case (user,  nterested n) =>
               nterested n.cluster dToScores.map {
                case (_, scores) =>
                  scores.logFavScoreClusterNormal zedOnly.getOrElse(0.0)
              }
          },
          So (s"$dataSetNa  User nterested n logFavScoreClusterNormal zedOnly")
        )
      ).un 
  }
}
