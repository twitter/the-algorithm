package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.algeb rd.Sem group
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.{D, Wr eExtens on}
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.{
  Analyt csBatchExecut on,
  Analyt csBatchExecut onArgs,
  BatchDescr pt on,
  BatchF rstT  ,
  Batch ncre nt,
  Tw terSc duledExecut onApp
}
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.{Cluster d, ModelVers ons, User d}
 mport com.tw ter.s mclusters_v2.hdfs_s ces.{
  AdhocKeyValS ces,
   nternalDataPaths,
  S mclustersV2KnownFor20M145K2020ScalaDataset,
  S mclustersV2Raw nterested nL e20M145K2020ScalaDataset,
  S mclustersV2Raw nterested n20M145KUpdatedScalaDataset,
  UserAndNe ghborsF xedPathS ce,
  UserUserGraphScalaDataset
}
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  ClustersUser s nterested n,
  ClustersUser sKnownFor,
  UserAndNe ghbors,
  UserTo nterested nClusterScores
}
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport java.ut l.T  Zone

/**
 * T  f le  mple nts t  job for comput ng users'  nterested n vector from KnownFor data set.
 *
 *   reads t  UserUserGraphScalaDataset to get user-user follow + fav graph, and t n
 * based on t  known-for clusters of each follo d/faved user,   calculate how much a user  s
 *  nterested n a cluster.
 *
 * T  ma n d fferences of t   nterested nFromKnownForL e compared to  nterested nFromKnownFor are
 * t  follow ng:
 * -   read t  UserUserGraph dataset that doesnot conta n t  producer normal zed scores
 * -   donot compute t  cluster normal zed scores for t  clusters per user
 * - For soc al proof threshold ng,   donot keep track of t  ent re l st of follow and
 * fav soc al proofs but rat r make use of numFollowSoc al and numFavSoc al (t   ntroduces
 * so  no se  f follow and fav soc al proof conta n t  sa  users)
 * - Store 200 clusters per user compared to 50  n   KF
 * - Runs more frequently compared to  ekly  n   KF
 */
/**
 * Product on job for comput ng  nterested n data set for t  model vers on 20M145K2020.
 *
 * To deploy t  job:
 *
 * capesospy-v2 update --bu ld_locally --start_cron  nterested_ n_l e_for_20M_145k_2020 \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object  nterested nFromKnownForL e20M145K2020 extends  nterested nFromKnownForL e {
  overr de val f rstT  : Str ng = "2021-04-24"
  overr de val outputKVDataset: KeyValDALDataset[KeyVal[Long, ClustersUser s nterested n]] =
    S mclustersV2Raw nterested nL e20M145K2020ScalaDataset
  overr de val outputPath: Str ng =  nternalDataPaths.Raw nterested nL e2020Path
  overr de val knownForModelVers on: Str ng = ModelVers ons.Model20M145K2020
  overr de val knownForDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUser sKnownFor]] =
    S mclustersV2KnownFor20M145K2020ScalaDataset
}
tra   nterested nFromKnownForL e extends Tw terSc duledExecut onApp {
   mpl c  val tz = DateOps.UTC
   mpl c  val parser = DateParser.default

  def f rstT  : Str ng
  val batch ncre nt: Durat on = Days(2)
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
          val userUserGraph =
            DAL.readMostRecentSnapshot(UserUserGraphScalaDataset).toTypedP pe
          val knownFor = KnownForS ces.fromKeyVal(
            DAL.readMostRecentSnapshot(knownForDALDataset, dateRange.extend(Days(30))).toTypedP pe,
            knownForModelVers on
          )

          val soc alProofThreshold = args. nt("soc alProofThreshold", 2)
          val maxClustersPerUser = args. nt("maxClustersPerUser", 200)

          val result =  nterested nFromKnownForL e
            .run(
              userUserGraph,
              knownFor,
              soc alProofThreshold,
              maxClustersPerUser,
              knownForModelVers on
            )

          val wr eKeyValResultExec = result
            .map {
              case (user d, clusters) => KeyVal(user d, clusters)
            }.wr eDALVers onedKeyValExecut on(
              outputKVDataset,
              D.Suff x(outputPath)
            )
          Ut l.pr ntCounters(wr eKeyValResultExec)
        }
      }
  }
}

/**
 * Adhoc job to compute user  nterested n.
 *
 * scald ng remote run \
 * --target src/scala/com/tw ter/s mclusters_v2/scald ng: nterested_ n_l e_20m_145k_2020-adhoc \
 * --ma n-class com.tw ter.s mclusters_v2.scald ng. nterested nFromKnownForL e20M145K2020Adhoc \
 * --user cassowary --cluster blueb rd-qus1 \
 * --keytab /var/l b/tss/keys/fluffy/keytabs/cl ent/cassowary.keytab \
 * --pr nc pal serv ce_acoount@TW TTER.B Z \
 * -- \
 * --outputD r /gcs/user/cassowary/adhoc/ nterested_ n_from_knownfor_l e/ \
 * --date 2020-08-25
 */
object  nterested nFromKnownForL e20M145K2020Adhoc extends AdhocExecut onApp {
  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val userUserGraph = DAL.readMostRecentSnapshot(UserUserGraphScalaDataset).toTypedP pe
    val soc alProofThreshold = args. nt("soc alProofThreshold", 2)
    val maxClustersPerUser = args. nt("maxClustersPerUser", 200)
    val knownForModelVers on = ModelVers ons.Model20M145K2020
    val knownFor = KnownForS ces.fromKeyVal(
      DAL
        .readMostRecentSnapshotNoOlderThan(
          S mclustersV2KnownFor20M145K2020ScalaDataset,
          Days(30)).toTypedP pe,
      knownForModelVers on
    )

    val outputS nk = AdhocKeyValS ces. nterested nS ce(args("outputD r"))
    Ut l.pr ntCounters(
       nterested nFromKnownForL e
        .run(
          userUserGraph,
          knownFor,
          soc alProofThreshold,
          maxClustersPerUser,
          knownForModelVers on
        ).wr eExecut on(outputS nk)
    )
  }

}

object  nterested nFromKnownForL e {
  pr vate def  fNanMake0(x: Double): Double =  f (x. sNaN) 0.0 else x

  case class SrcCluster nter d ate nfo(
    followScore: Double,
    favScore: Double,
    logFavScore: Double,
    numFollo d:  nt,
    numFaved:  nt) {

    //  lper funct on used for test cases
    overr de def equals(obj: scala.Any): Boolean = {
      obj match {
        case that: SrcCluster nter d ate nfo =>
          math.abs(followScore - that.followScore) < 1e-5 &&
            math.abs(favScore - that.favScore) < 1e-5 &&
            math.abs(logFavScore - that.logFavScore) < 1e-5 &&
            numFollo d == that.numFollo d &&
            numFaved == that.numFaved
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
        favScore = left.favScore + r ght.favScore,
        logFavScore = left.logFavScore + r ght.logFavScore,
        numFollo d = left.numFollo d + r ght.numFollo d,
        numFaved = left.numFaved + r ght.numFaved
      )
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
     nterested nFromKnownFor.keepOnlyTopClusters(
      groupClusterScores(
        userClusterPa rs(
          adjacencyL sts,
          knownFor,
          soc alProofThreshold
        )
      ),
      maxClustersPerUser,
      knownForModelVers on
    )
  }

  def userClusterPa rs(
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
              val favScore =
                srcW h  ghts.favScoreHalfL fe100Days.getOrElse(0.0) * knownForScore
              val logFavScore = srcW h  ghts.logFavScore.getOrElse(0.0) * knownForScore
              val numFollo d =  f (srcW h  ghts. sFollo d.conta ns(true)) {
                1
              } else 0

              val numFaved =  f (srcW h  ghts.favScoreHalfL fe100Days.ex sts(_ > 0)) {
                1
              } else 0

              (
                (srcW h  ghts.ne ghbor d, cluster d),
                SrcCluster nter d ate nfo(
                  followScore,
                  favScore,
                  logFavScore,
                  numFollo d,
                  numFaved
                )
              )
          }
      }
      .sumByKey
      .w hReducers(10000)
      .f lter {
        case ((_, _), SrcCluster nter d ate nfo(_, _, _, numFollo d, numFaved)) =>
          srcClusterPa rsBeforeSoc alProofThreshold ng. nc()
          //   donot remove dupl cates
          val soc alProofS ze = numFollo d + numFaved
          val result = soc alProofS ze >= soc alProofThreshold
           f (result) {
            srcClusterPa rsAfterSoc alProofThreshold ng. nc()
          }
          result
      }
  }

  def groupClusterScores(
     nter d ate: TypedP pe[((Long,  nt), SrcCluster nter d ate nfo)]
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[(Long, L st[( nt, UserTo nterested nClusterScores)])] = {

     mpl c  val  2b:  nt => Array[Byte] =  nject on. nt2B gEnd an

     nter d ate
      .map {
        case (
              (src d, cluster d),
              SrcCluster nter d ate nfo(
                followScore,
                favScore,
                logFavScore,
                numFollo d,
                numFaved
              )) =>
          (
            src d,
            L st(
              (
                cluster d,
                UserTo nterested nClusterScores(
                  followScore = So ( fNanMake0(followScore)),
                  favScore = So ( fNanMake0(favScore)),
                  logFavScore = So ( fNanMake0(logFavScore)),
                  numUsersBe ngFollo d = So (numFollo d),
                  numUsersThat reFaved = So (numFaved)
                ))
            )
          )
      }
      .sumByKey
      //      .w hReducers(1000)
      .toTypedP pe
  }
}
