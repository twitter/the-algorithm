package com.tw ter.s mclusters_v2.scald ng. nferred_ent  es

 mport com.tw ter.algeb rd.Max
 mport com.tw ter.scald ng.Args
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.Durat on
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.R chDate
 mport com.tw ter.scald ng.TypedP pe
 mport com.tw ter.scald ng.TypedTsv
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.UTTEnt y d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.scald ng.common.TypedR chP pe._
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.s mclusters_v2.thr ftscala.Ent yS ce
 mport com.tw ter.s mclusters_v2.thr ftscala. nferredEnt y
 mport com.tw ter.s mclusters_v2.thr ftscala.Semant cCoreEnt yW hScore
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClusters nferredEnt  es
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersS ce
 mport com.tw ter.s mclusters_v2.thr ftscala.UserAndNe ghbors
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone
 mport com.tw ter.onboard ng.relevance.s ce.UttAccountRecom ndat onsScalaDataset
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.wtf.ent y_real_graph.scald ng.common.Semant cCoreF lters.getVal dSemant cCoreEnt  es
 mport com.tw ter.wtf.ent y_real_graph.scald ng.common.DataS ces

/**
 *  nfer  nterested- n ent  es for a g ven user. Depend ng on how and w re t  ent y s ce co s
 * from, t  can be ach eve a number of ways. For example,   can use user-> nterested- n clusters
 * and cluster-> semant ccore ent y embedd ngs to der ve user->ent y. Or,   can use a producers'
 * UTT embedd ngs and user-user engage nt graph to aggregate UTT engage nt  tory.
 */
object  nferredEnt  esFrom nterested n {

  def getUserToKnownForUttEnt  es(
    dateRange: DateRange,
    maxUttEnt  esPerUser:  nt
  )(
     mpl c  t  Zone: T  Zone
  ): TypedP pe[(User d, Seq[(Long, Double)])] = {

    val val dEnt  es = getVal dSemant cCoreEnt  es(
      DataS ces.semant cCore tadataS ce(dateRange, t  Zone)).d st nct.map { ent y d =>
      Set(ent y d)
    }.sum

    DAL
      .readMostRecentSnapshot(UttAccountRecom ndat onsScalaDataset, dateRange)
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
      .flatMapW hValue(val dEnt  es) {
        // Keep only val d Ent  es
        case (KeyVal( nterest, cand dates), So (val dUTTEnt  es))
             f val dUTTEnt  es.conta ns( nterest.utt D) =>
          cand dates.recom ndat ons.map { rec =>
            (rec.cand dateUser D, ( nterest.utt D, rec.score.getOrElse(0.0)))
          }
        case _ => None
      }
      .group
      .sortedReverseTake(maxUttEnt  esPerUser)(Order ng.by(_._2))
      .toTypedP pe
  }

  def f lterUTTEnt  es(
     nterested nEnt  es: TypedP pe[(User d, Seq[(UTTEnt y d,  nt)])],
    m nSoc alProofThreshold:  nt,
    max nterestsPerUser:  nt
  ): TypedP pe[(User d, Seq[UTTEnt y d])] = {

     nterested nEnt  es
      .map {
        case (user d, ent  es) =>
          val topEnt  es = ent  es
            .f lter(_._2 >= m nSoc alProofThreshold)
            .sortBy(-_._2)
            .take(max nterestsPerUser)
            .map(_._1)

          (user d, topEnt  es)
      }
      .f lter(_._2.nonEmpty)
  }

  def getUserToUTTEnt  es(
    userUserGraph: TypedP pe[UserAndNe ghbors],
    knownForEnt  es: TypedP pe[(User d, Seq[UTTEnt y d])]
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[(User d, Seq[(UTTEnt y d,  nt)])] = {
    val flatEngage ntGraph =
      userUserGraph
        .count("num_user_user_graph_records")
        .flatMap { userAndNe ghbors =>
          userAndNe ghbors.ne ghbors.flatMap { ne ghbor =>
            val producer d = ne ghbor.ne ghbor d
            val hasFav = ne ghbor.favScoreHalfL fe100Days.ex sts(_ > 0)
            val hasFollow = ne ghbor. sFollo d.conta ns(true)

             f (hasFav || hasFollow) {
              So ((producer d, userAndNe ghbors.user d))
            } else {
              None
            }
          }
        }
        .count("num_flat_user_user_graph_edges")

    flatEngage ntGraph
      .jo n(knownForEnt  es.count("num_producer_to_ent  es"))
      .w hReducers(3000)
      .flatMap {
        case (producer d, (user d, ent  es)) =>
          ent  es.map { ent y d => ((user d, ent y d), 1) }
      }
      .count("num_flat_user_to_ent y")
      .sumByKey
      .w hReducers(2999)
      .toTypedP pe
      .count("num_user_w h_ent  es")
      .collect {
        case ((user d, uttEnt y d), numEngage nts) =>
          (user d, Seq((uttEnt y d, numEngage nts)))
      }
      .sumByKey
  }

  /**
   *  nfer ent  es us ng user- nterested n clusters and ent y embedd ngs for those clusters,
   * based on a threshold
   */
  def get nterested nFromEnt yEmbedd ngs(
    userTo nterested n: TypedP pe[(User d, ClustersUser s nterested n)],
    clusterToEnt  es: TypedP pe[(Cluster d, Seq[Semant cCoreEnt yW hScore])],
     nferredFromCluster: Opt on[S mClustersS ce],
     nferredFromEnt y: Opt on[Ent yS ce]
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[(User d, Seq[ nferredEnt y])] = {
    val clusterToUsers = userTo nterested n
      .flatMap {
        case (user d, clusters) =>
          clusters.cluster dToScores.map {
            case (cluster d, score) =>
              (cluster d, (user d, score))
          }
      }
      .count("num_flat_user_to_ nterested_ n_cluster")

    clusterToUsers
      .jo n(clusterToEnt  es)
      .w hReducers(3000)
      .map {
        case (cluster d, ((user d,  nterested nScore), ent  esW hScores)) =>
          (user d, ent  esW hScores)
      }
      .flatMap {
        case (user d, ent  esW hScore) =>
          // Dedup by ent y ds  n case user  s assoc ated w h an ent y from d fferent clusters
          ent  esW hScore.map { ent y => (user d, Map(ent y.ent y d -> Max(ent y.score))) }
      }
      .sumByKey
      .map {
        case (user d, ent  esW hMaxScore) =>
          val  nferredEnt  es = ent  esW hMaxScore.map { ent yW hScore =>
             nferredEnt y(
              ent y d = ent yW hScore._1,
              score = ent yW hScore._2.get,
              s mclusterS ce =  nferredFromCluster,
              ent yS ce =  nferredFromEnt y
            )
          }.toSeq
          (user d,  nferredEnt  es)
      }
      .count("num_user_w h_ nferred_ent  es")
  }
}

/**
capesospy-v2 update --bu ld_locally --start_cron \
  --start_cron  nferred_ent  es_from_ nterested_ n \
  src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object  nferred nterested nSemant cCoreEnt  esBatchApp extends Sc duledExecut onApp {

  overr de def f rstT  : R chDate = R chDate("2023-01-01")

  overr de def batch ncre nt: Durat on = Days(1)

  pr vate val outputPath =  nferredEnt  es.MHRootPath + "/ nterested_ n"

  pr vate val outputPathKeyedByCluster =
     nferredEnt  es.MHRootPath + "/ nterested_ n_keyed_by_cluster"

   mport  nferredEnt  esFrom nterested n._

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    Execut on.un 

    val clusterToEnt  es =  nferredEnt  es
      .getLeg bleEnt yEmbedd ngs(dateRange, t  Zone)
      .count("num_leg ble_cluster_to_ent  es")
      .forceToD sk

    //  nferred  nterests. Only support 2020 model vers on
    val userToClusters2020 =
       nterested nS ces.s mClusters nterested n2020S ce(dateRange, t  Zone)

    val  nferredEnt  es2020 = get nterested nFromEnt yEmbedd ngs(
      userTo nterested n = userToClusters2020,
      clusterToEnt  es = clusterToEnt  es,
       nferredFromCluster = So ( nferredEnt  es. nterested n2020),
       nferredFromEnt y = So (Ent yS ce.S mClusters20M145K2020Ent yEmbedd ngsByFavScore)
    )(un que D)
      .count("num_user_w h_ nferred_ent  es_2020")

    val comb ned nferred nterests =
       nferredEnt  es.comb neResults( nferredEnt  es2020)

    // output cluster -> ent y mapp ng
    val clusterToEnt yExec = clusterToEnt  es
      .map {
        case (cluster d, ent  es) =>
          val  nferredEnt  es = S mClusters nferredEnt  es(
            ent  es.map(ent y =>  nferredEnt y(ent y.ent y d, ent y.score))
          )
          KeyVal(cluster d,  nferredEnt  es)
      }
      .wr eDALVers onedKeyValExecut on(
        S mclusters nferredEnt  esFrom nterested nKeyedByClusterScalaDataset,
        D.Suff x(outputPathKeyedByCluster)
      )

    // output user -> ent y mapp ng
    val userToEnt yExec = comb ned nferred nterests
      .map { case (user d, ent  es) => KeyVal(user d, ent  es) }
      .wr eDALVers onedKeyValExecut on(
        S mclusters nferredEnt  esFrom nterested nScalaDataset,
        D.Suff x(outputPath)
      )

    Execut on.z p(clusterToEnt yExec, userToEnt yExec).un 
  }
}

/**
Adhob debugg ng job. Uses Ent y Embedd ngs dataset to  nfer user  nterests

./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/ nferred_ent  es/ &&\
scald ng remote run \
  --ma n-class com.tw ter.s mclusters_v2.scald ng. nferred_ent  es. nferred nterested nSemant cCoreEnt  esAdhocApp \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng/ nferred_ent  es: nferred_ent  es_from_ nterested_ n-adhoc \
  --user recos-platform \
  -- --date 2019-11-11 --ema l y _ldap@tw ter.com
 */
object  nferred nterested nSemant cCoreEnt  esAdhocApp extends AdhocExecut onApp {
   mport  nferredEnt  esFrom nterested n._
  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val  nterested n =  nterested nS ces.s mClusters nterested n2020S ce(dateRange, t  Zone)

    val clusterToEnt  es =  nferredEnt  es
      .getLeg bleEnt yEmbedd ngs(dateRange, t  Zone)
      .count("num_leg ble_cluster_to_ent  es")

    // Debugg ng  nterested n -> Ent yEmbedd ngs approach
    val  nterested nFromEnt yEmbedd ngs = get nterested nFromEnt yEmbedd ngs(
       nterested n,
      clusterToEnt  es,
      None,
      None
    )(un que D)

    val d str but on = Ut l
      .pr ntSummaryOfNu r cColumn(
         nterested nFromEnt yEmbedd ngs.map { case (k, v) => v.s ze },
        So ("# of  nterested n ent  es per user")
      ).map { results =>
        Ut l.sendEma l(results, "# of  nterested n ent  es per user", args.getOrElse("ema l", ""))
      }

    Execut on
      .z p(
        d str but on,
         nterested nFromEnt yEmbedd ngs
          .wr eExecut on(
            TypedTsv("/user/recos-platform/adhoc/debug/ nterested_ n_from_ent y_embedd ngs"))
      ).un 
  }
}

/**
 Adhob debugg ngjob. Runs through t  UTT  nterest  nference, analyze t  s ze & d str but on of
  nterests per user.

./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/ nferred_ent  es/ &&\
scald ng remote run \
  --ma n-class com.tw ter.s mclusters_v2.scald ng. nferred_ent  es. nferredUTTEnt  esFrom nterested nAdhocApp \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng/ nferred_ent  es: nferred_ent  es_from_ nterested_ n-adhoc \
  --user recos-platform \
  -- --date 2019-11-03 --ema l y _ldap@tw ter.com
 */
object  nferredUTTEnt  esFrom nterested nAdhocApp extends AdhocExecut onApp {
   mport  nferredEnt  esFrom nterested n._

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val employeeGraphPath = "/user/recos-platform/adhoc/employee_graph_from_user_user/"
    val employeeGraph = TypedP pe.from(UserAndNe ghborsF xedPathS ce(employeeGraphPath))

    val maxKnownForUttsPerProducer = 100
    val m nSoc alProofThreshold = 10
    val max nferred nterestsPerUser = 500

    // KnownFor UTT ent  es
    val userToUttEnt  es = getUserToKnownForUttEnt  es(
      dateRange.emb ggen(Days(7)),
      maxKnownForUttsPerProducer
    ).map { case (user d, ent  es) => (user d, ent  es.map(_._1)) }

    val userTo nterestsEngage ntCounts = getUserToUTTEnt  es(employeeGraph, userToUttEnt  es)

    val top nterests = f lterUTTEnt  es(
      userTo nterestsEngage ntCounts,
      m nSoc alProofThreshold,
      max nferred nterestsPerUser
    ).count("num_users_w h_ nferred_ nterests")

    // Debugg ng UTT ent  es
    val analys s = Ut l
      .pr ntSummaryOfNu r cColumn(
        top nterests.map { case (k, v) => v.s ze },
        So (
          "# of UTT ent  es per user, maxKnownForUtt=100, m nSoc alProof=10, max nferredPerUser=500")
      ).map { results =>
        Ut l.sendEma l(results, "# of UTT ent  es per user", args.getOrElse("ema l", ""))
      }

    val outputPath = "/user/recos-platform/adhoc/ nferred_utt_ nterests"

    Execut on
      .z p(
        top nterests.wr eExecut on(TypedTsv(outputPath)),
        analys s
      ).un 
  }
}
