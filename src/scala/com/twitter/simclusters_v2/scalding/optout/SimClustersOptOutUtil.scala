package com.tw ter.s mclusters_v2.scald ng.optout

 mport com.tw ter.algeb rd.Aggregator.s ze
 mport com.tw ter.algeb rd.QTreeAggregatorLo rBound
 mport com.tw ter.octa n. dent f ers.thr ftscala.Raw d
 mport com.tw ter.octa n.p13n.batch.P13NPreferencesScalaDataset
 mport com.tw ter.octa n.p13n.preferences.Compos e nterest
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.TypedP pe
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossClusterSa DC
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.Semant cCoreEnt y d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala.ClusterType
 mport com.tw ter.s mclusters_v2.thr ftscala.Semant cCoreEnt yW hScore
 mport com.tw ter.wtf. nterest.thr ftscala. nterest

/**
 * Opts out  nterested n clusters based on clusters' ent y embedd ngs.  f a user opted out an
 * ent y and t  user also  s  nterested  n a cluster w h that ent y embedd ng, unl nk t 
 * user from that ent y.
 */
object S mClustersOptOutUt l {

  /**
   * Reads User's Y  Tw ter Data opt-out select ons
   */
  def getP13nOptOutS ces(
    dateRange: DateRange,
    clusterType: ClusterType
  ): TypedP pe[(User d, Set[Semant cCoreEnt y d])] = {
    DAL
      .readMostRecentSnapshot(
        P13NPreferencesScalaDataset,
        dateRange
      )
      .w hRemoteReadPol cy(AllowCrossClusterSa DC)
      .toTypedP pe
      .map { record => (record. d, record.preferences) }
      .flatMap {
        case (Raw d.User d(user d), p13nPreferences) =>
          val optedOutEnt  es = p13nPreferences. nterestPreferences
            .map { preference =>
              preference.d sabled nterests
                .collect {
                  case Compos e nterest.Recom ndat on nterest(rec nterest)
                       f clusterType == ClusterType. nterested n =>
                    rec nterest. nterest match {
                      case  nterest.Semant cEnt y nterest(semant cCore nterest) =>
                        So (semant cCore nterest.ent y d)
                      case _ =>
                        None
                    }

                  case Compos e nterest.Recom ndat onKnownFor(rec nterest)
                       f clusterType == ClusterType.KnownFor =>
                    rec nterest. nterest match {
                      case  nterest.Semant cEnt y nterest(semant cCore nterest) =>
                        So (semant cCore nterest.ent y d)
                      case _ =>
                        None
                    }
                }.flatten.toSet
            }.getOrElse(Set.empty)
           f (optedOutEnt  es.nonEmpty) {
            So ((user d, optedOutEnt  es))
          } else {
            None
          }
        case _ =>
          None
      }
  }

  /**
   * Remove user's clusters whose  nferred ent y embedd ngs are opted out. W ll reta n t  user
   * entry  n t  p pe even  f all t  clusters are f ltered out.
   */
  def f lterOptedOutClusters(
    userToClusters: TypedP pe[(User d, Seq[Cluster d])],
    optedOutEnt  es: TypedP pe[(User d, Set[Semant cCoreEnt y d])],
    leg bleClusters: TypedP pe[(Cluster d, Seq[Semant cCoreEnt yW hScore])]
  ): TypedP pe[(User d, Seq[Cluster d])] = {

    val  n moryVal dClusterToEnt  es =
      leg bleClusters
        .mapValues(_.map(_.ent y d).toSet)
        .map(Map(_)).sum

    userToClusters
      .leftJo n(optedOutEnt  es)
      .mapW hValue( n moryVal dClusterToEnt  es) {
        case ((user d, (userClusters, optedOutEnt  esOpt)), val dClusterToEnt  esOpt) =>
          val optedOutEnt  esSet = optedOutEnt  esOpt.getOrElse(Set.empty)
          val val dClusterToEnt  es = val dClusterToEnt  esOpt.getOrElse(Map.empty)

          val clustersAfterOptOut = userClusters.f lter { cluster d =>
            val  sClusterOptedOut = val dClusterToEnt  es
              .getOrElse(cluster d, Set.empty)
              . ntersect(optedOutEnt  esSet)
              .nonEmpty
            ! sClusterOptedOut
          }.d st nct

          (user d, clustersAfterOptOut)
      }
      .f lter { _._2.nonEmpty }
  }

  val AlertEma l = "no-reply@tw ter.com"

  /**
   * Does san y c ck on t  results, to make sure t  opt out outputs are comparable to t 
   * raw vers on.  f t  delta  n t  number of users >= 0.1% or  d an of number of clusters per
   * user >= 1%, send alert ema ls
   */
  def san yC ckAndSendEma l(
    oldNumClustersPerUser: TypedP pe[ nt],
    newNumClustersPerUser: TypedP pe[ nt],
    modelVers on: Str ng,
    alertEma l: Str ng
  ): Execut on[Un ] = {
    val oldNumUsersExec = oldNumClustersPerUser.aggregate(s ze).toOpt onExecut on
    val newNumUsersExec = newNumClustersPerUser.aggregate(s ze).toOpt onExecut on

    val old d anExec = oldNumClustersPerUser
      .aggregate(QTreeAggregatorLo rBound(0.5))
      .toOpt onExecut on

    val new d anExec = newNumClustersPerUser
      .aggregate(QTreeAggregatorLo rBound(0.5))
      .toOpt onExecut on

    Execut on
      .z p(oldNumUsersExec, newNumUsersExec, old d anExec, new d anExec)
      .map {
        case (So (oldNumUsers), So (newNumUsers), So (old d an), So (new d an)) =>
          val deltaNum = (newNumUsers - oldNumUsers).toDouble / oldNumUsers.toDouble
          val delta d an = (old d an - new d an) / old d an
          val  ssage =
            s"num users before optout=$oldNumUsers,\n" +
              s"num users after optout=$newNumUsers,\n" +
              s" d an num clusters per user before optout=$old d an,\n" +
              s" d an num clusters per user after optout=$new d an\n"

          pr ntln( ssage)
           f (Math.abs(deltaNum) >= 0.001 || Math.abs(delta d an) >= 0.01) {
            Ut l.sendEma l(
               ssage,
              s"Anomaly  n $modelVers on opt out job. Please c ck cluster optout jobs  n Eagleeye",
              alertEma l
            )
          }
        case err =>
          Ut l.sendEma l(
            err.toStr ng(),
            s"Anomaly  n $modelVers on opt out job. Please c ck cluster optout jobs  n Eagleeye",
            alertEma l
          )
      }
  }

}
