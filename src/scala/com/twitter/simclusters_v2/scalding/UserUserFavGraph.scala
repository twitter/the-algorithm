package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.algeb rd.DecayedValue
 mport com.tw ter.algeb rd.DecayedValueMono d
 mport com.tw ter.algeb rd.Mono d
 mport com.tw ter.algeb rd.Sem group
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng.typed.UnsortedGrouped
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch._
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala.DecayedSums
 mport com.tw ter.s mclusters_v2.thr ftscala.EdgeW hDecayed  ghts
 mport com.tw ter.t  l neserv ce.thr ftscala.Contextual zedFavor eEvent
 mport com.tw ter.t  l neserv ce.thr ftscala.Favor eEventUn on
 mport com.tw ter.users ce.snapshot.flat.Users ceFlatScalaDataset
 mport com.tw ter.users ce.snapshot.flat.thr ftscala.FlatUser
 mport com.tw ter.ut l.T  
 mport twadoop_conf g.conf gurat on.log_categor es.group.t  l ne.T  l neServ ceFavor esScalaDataset

sealed tra  FavState

object Fav extends FavState

object UnFavW houtPr orFav extends FavState

object UnFavW hPr orFav extends FavState

case class T  stampedFavState(favOrUnfav: FavState, t  stampM ll s: Long)

object T  stampedFavStateSem group extends Sem group[T  stampedFavState] {
  overr de def plus(left: T  stampedFavState, r ght: T  stampedFavState): T  stampedFavState = {

    /**
     * Ass gn ng to f rst, second ensures commutat ve property
     */
    val (f rst, second) =  f (left.t  stampM ll s < r ght.t  stampM ll s) {
      (left, r ght)
    } else {
      (r ght, left)
    }
    (f rst.favOrUnfav, second.favOrUnfav) match {
      case (_, UnFavW hPr orFav) => second
      case (UnFavW hPr orFav, UnFavW houtPr orFav) =>
        T  stampedFavState(UnFavW hPr orFav, second.t  stampM ll s)
      case (Fav, UnFavW houtPr orFav) =>
        T  stampedFavState(UnFavW hPr orFav, second.t  stampM ll s)
      case (UnFavW houtPr orFav, UnFavW houtPr orFav) => second
      case (_, Fav) => second
    }
  }
}

object UserUserFavGraph {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
  // sett ng t  prune threshold  n t  mono d below to 0.0, s nce   want to do   own prun ng
  // outs de t  mono d, pr mar ly to be able to count how many scores are pruned.
   mpl c  val dvMono d: Mono d[DecayedValue] = DecayedValueMono d(0.0)
   mpl c  val lfvSem group: Sem group[T  stampedFavState] = T  stampedFavStateSem group

  def getSum dFavGraph(
    prev ousGraphOpt: Opt on[TypedP pe[EdgeW hDecayed  ghts]],
    newFavsDateRange: DateRange,
    halfL ves nDays: L st[ nt],
    m nScoreToKeep: Double
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[EdgeW hDecayed  ghts] = {
    val newFavs = DAL.read(T  l neServ ceFavor esScalaDataset, newFavsDateRange).toTypedP pe
    val endT   = T  .fromM ll seconds(newFavsDateRange.end.t  stamp)
    val userS ce =
      DAL.readMostRecentSnapshotNoOlderThan(Users ceFlatScalaDataset, Days(7)).toTypedP pe
    getSum dFavGraphW hVal dUsers(
      prev ousGraphOpt,
      newFavs,
      halfL ves nDays,
      endT  ,
      m nScoreToKeep,
      userS ce
    )
  }

  def getSum dFavGraphW hVal dUsers(
    prev ousGraphOpt: Opt on[TypedP pe[EdgeW hDecayed  ghts]],
    newFavs: TypedP pe[Contextual zedFavor eEvent],
    halfL ves nDays: L st[ nt],
    endT  : T  ,
    m nScoreToKeep: Double,
    userS ce: TypedP pe[FlatUser]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[EdgeW hDecayed  ghts] = {
    val fullGraph = getSum dFavGraph(
      prev ousGraphOpt,
      newFavs,
      halfL ves nDays,
      endT  ,
      m nScoreToKeep
    )
    removeDeact vedOrSuspendedUsers(fullGraph, userS ce)
  }

  def processRawFavEvents(
    favsOrUnfavs: TypedP pe[Contextual zedFavor eEvent]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[((User d, T et d, User d), T  stampedFavState)] = {
    val numFavsBeforeUn q = Stat("num_favs_before_un q")
    val numUnFavsBeforeUn q = Stat("num_unfavs_before_un q")
    val numF nalFavs = Stat("num_f nal_favs")
    val numUnFavsW hPr orFavs = Stat("num_unfavs_w h_pr or_favs")
    val numUnFavsW houtPr orFavs = Stat("num_unfavs_w hout_pr or_favs")

    favsOrUnfavs
      .flatMap { cfe: Contextual zedFavor eEvent =>
        cfe.event match {
          case Favor eEventUn on.Favor e(fav) =>
            numFavsBeforeUn q. nc()
            So (
              (
                (fav.user d, fav.t et d, fav.t etUser d),
                T  stampedFavState(Fav, fav.eventT  Ms)))
          case Favor eEventUn on.Unfavor e(unfav) =>
            numUnFavsBeforeUn q. nc()
            So (
              (
                (unfav.user d, unfav.t et d, unfav.t etUser d),
                T  stampedFavState(UnFavW houtPr orFav, unfav.eventT  Ms)))
          case _ => None
        }
      }
      .sumByKey
      .toTypedP pe
      .flatMap {
        case fav @ (_, T  stampedFavState(Fav, _)) =>
          numF nalFavs. nc()
          So (fav)
        case unfav @ (_, T  stampedFavState(UnFavW houtPr orFav, _)) =>
          numUnFavsW houtPr orFavs. nc()
          So (unfav)
        case (_, T  stampedFavState(UnFavW hPr orFav, _)) =>
          numUnFavsW hPr orFavs. nc()
          None
      }
  }

  pr vate def getGraphFromNewFavsOnly(
    newFavs: TypedP pe[Contextual zedFavor eEvent],
    halfL ves nDays: L st[ nt],
    endT  : T  
  )(
     mpl c  un que D: Un que D
  ): UnsortedGrouped[(User d, User d), Map[ nt, DecayedValue]] = {

    val numEventsNe rThanEndT   = Stat("num_events_ne r_than_endt  ")

    processRawFavEvents(newFavs).map {
      case ((user d, _, author d), T  stampedFavState(favOrUnfav, t  stampM ll s)) =>
        val halfL fe nDaysToScores = halfL ves nDays.map { halfL fe nDays =>
          val g venT   = T  .fromM ll seconds(t  stampM ll s)
           f (g venT   > endT  ) {
            // techn cally t  should never happen, and even  f   d d happen,
            //   shouldn't have to care, but  'm not c ng that t    ghts aren't be ng computed
            // correctly for events that sp lled over t  edge
            numEventsNe rThanEndT  . nc()
          }
          val t   nSeconds = math.m n(g venT  . nSeconds, endT  . nSeconds)
          val value = favOrUnfav match {
            case Fav => 1.0
            case UnFavW houtPr orFav => -1.0
            case UnFavW hPr orFav => 0.0
          }
          val decayedValue = DecayedValue.bu ld(value, t   nSeconds, halfL fe nDays.days. nSeconds)
          halfL fe nDays -> decayedValue
        }
        ((user d, author d), halfL fe nDaysToScores.toMap)
    }.sumByKey
  }

  def getSum dFavGraph(
    prev ousGraphOpt: Opt on[TypedP pe[EdgeW hDecayed  ghts]],
    newFavs: TypedP pe[Contextual zedFavor eEvent],
    halfL ves nDays: L st[ nt],
    endT  : T  ,
    m nScoreToKeep: Double
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[EdgeW hDecayed  ghts] = {
    val prunedScoresCounter = Stat("num_pruned_scores")
    val negat veScoresCounter = Stat("num_negat ve_scores")
    val prunedEdgesCounter = Stat("num_pruned_edges")
    val keptEdgesCounter = Stat("num_kept_edges")
    val keptScoresCounter = Stat("num_kept_scores")
    val numCommonEdges = Stat("num_common_edges")
    val numNewEdges = Stat("num_new_edges")
    val numOldEdges = Stat("num_old_edges")

    val unprunedOuterJo nedGraph = prev ousGraphOpt match {
      case So (prev ousGraph) =>
        prev ousGraph
          .map {
            case EdgeW hDecayed  ghts(src d, dest d, decayedSums) =>
              val ts = decayedSums.lastUpdatedT  stamp.toDouble / 1000
              val map = decayedSums.halfL fe nDaysToDecayedSums.map {
                case (halfL fe nDays, value) =>
                  halfL fe nDays -> DecayedValue.bu ld(value, ts, halfL fe nDays.days. nSeconds)
              }.toMap
              ((src d, dest d), map)
          }
          .outerJo n(getGraphFromNewFavsOnly(newFavs, halfL ves nDays, endT  ))
          .toTypedP pe
      case None =>
        getGraphFromNewFavsOnly(newFavs, halfL ves nDays, endT  ).toTypedP pe
          .map {
            case ((src d, dest d), scoreMap) =>
              ((src d, dest d), (None, So (scoreMap)))
          }
    }

    unprunedOuterJo nedGraph
      .flatMap {
        case ((src d, dest d), (prev ousScoreMapOpt, newScoreMapOpt)) =>
          val latestT  DecayedValues = halfL ves nDays.map { hl nDays =>
            hl nDays -> DecayedValue.bu ld(0, endT  . nSeconds, hl nDays.days. nSeconds)
          }.toMap

          val updatedDecayedValues =
            Mono d.sum(
              L st(prev ousScoreMapOpt, newScoreMapOpt, So (latestT  DecayedValues)).flatten)

          (prev ousScoreMapOpt, newScoreMapOpt) match {
            case (So (pm), None) => numOldEdges. nc()
            case (None, So (nm)) => numNewEdges. nc()
            case (So (pm), So (nm)) => numCommonEdges. nc()
          }

          val prunedMap = updatedDecayedValues.flatMap {
            case (hl nDays, decayedValue) =>
               f (decayedValue.value < m nScoreToKeep) {
                 f (decayedValue.value < 0) {
                  negat veScoresCounter. nc()
                }
                prunedScoresCounter. nc()
                None
              } else {
                keptScoresCounter. nc()
                So ((hl nDays, decayedValue.value))
              }
          }

           f (prunedMap.nonEmpty) {
            keptEdgesCounter. nc()
            So (EdgeW hDecayed  ghts(src d, dest d, DecayedSums(endT  . nM ll s, prunedMap)))
          } else {
            prunedEdgesCounter. nc()
            None
          }
      }
  }

  def removeDeact vedOrSuspendedUsers(
    full: TypedP pe[EdgeW hDecayed  ghts],
    userS ce: TypedP pe[FlatUser]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[EdgeW hDecayed  ghts] = {
    val numVal dUsers = Stat("num_val d_users")
    val num nval dUsers = Stat("num_ nval d_users")
    val numEdgesBeforeUsers ceJo n = Stat("num_edges_before_jo n_w h_users ce")
    val numEdgesW hVal dS ce = Stat("num_edges_w h_val d_s ce")
    val numEdgesW hVal dS ceAndDest = Stat("num_edges_w h_val d_s ce_and_dest")

    val val dUsers = userS ce.flatMap {
      case flatUser
           f !flatUser.deact vated.conta ns(true) && !flatUser.suspended.conta ns(true)
            && flatUser. d.nonEmpty =>
        numVal dUsers. nc()
        flatUser. d
      case _ =>
        num nval dUsers. nc()
        None
    }.forceToD sk // avo d read ng  n t  whole of userS ce for both of t  jo ns below

    val toJo n = full.map { edge =>
      numEdgesBeforeUsers ceJo n. nc()
      (edge.s ce d, edge)
    }

    toJo n
      .jo n(val dUsers.asKeys)
      .map {
        case (_, (edge, _)) =>
          numEdgesW hVal dS ce. nc()
          (edge.dest nat on d, edge)
      }
      .jo n(val dUsers.asKeys)
      .map {
        case (_, (edge, _)) =>
          numEdgesW hVal dS ceAndDest. nc()
          edge
      }
  }
}

/**
 * ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:fav_graph_adhoc && \
 * oscar hdfs --user fr gate --host hadoopnest1.atla.tw ter.com --bundle fav_graph_adhoc \
 * --tool com.tw ter.s mclusters_v2.scald ng.UserUserFavGraphAdhoc --screen --screen-detac d \
 * --tee logs/userUserFavGraphAdhoc_20170101 -- --date 2017-01-01 --halfL ves nDays 14 50 100 \
 * --outputD r /user/fr gate/y _ldap/userUserFavGraphAdhoc_20170101_hl14_50_100
 *
 * ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:fav_graph_adhoc && \
 * oscar hdfs --user fr gate --host hadoopnest1.atla.tw ter.com --bundle fav_graph_adhoc \
 * --tool com.tw ter.s mclusters_v2.scald ng.UserUserFavGraphAdhoc --screen --screen-detac d \
 * --tee logs/userUserFavGraphAdhoc_20170102_addPrev ous20170101 -- --date 2017-01-02 \
 * --prev ousGraphD r /user/fr gate/y _ldap/userUserFavGraphAdhoc_20170101_hl14_50_100 \
 * --halfL ves nDays 14 50 100 \
 * --outputD r /user/fr gate/y _ldap/userUserFavGraphAdhoc_20170102_addPrev ous20170101_hl14_50_100
 */
object UserUserFavGraphAdhoc extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default
  val log = Logger()

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val prev ousGraphOpt = args.opt onal("prev ousGraphD r").map { d r =>
            TypedP pe.from(EdgeW hDecayedWtsF xedPathS ce(d r))
          }
          val favsDateRange = DateRange.parse(args.l st("date"))
          val halfL ves = args.l st("halfL ves nDays").map(_.to nt)
          val m nScoreToKeep = args.double("m nScoreToKeep", 1e-5)
          val outputD r = args("outputD r")
          Ut l.pr ntCounters(
            UserUserFavGraph
              .getSum dFavGraph(prev ousGraphOpt, favsDateRange, halfL ves, m nScoreToKeep)
              .wr eExecut on(EdgeW hDecayedWtsF xedPathS ce(outputD r))
          )
        }
    }
}

/**
 * $ capesospy-v2 update --start_cron fav_graph src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object UserUserFavGraphBatch extends Tw terSc duledExecut onApp {
  pr vate val f rstT  : Str ng = "2017-01-01"
   mpl c  val tz = DateOps.UTC
   mpl c  val parser = DateParser.default
  pr vate val batch ncre nt: Durat on = Days(2)
  pr vate val f rstStartDate = DateRange.parse(f rstT  ).start

  val outputPath: Str ng = "/user/cassowary/processed/user_user_fav_graph"
  val log = Logger()

  pr vate val execArgs = Analyt csBatchExecut onArgs(
    batchDesc = BatchDescr pt on(t .getClass.getNa ),
    f rstT   = BatchF rstT  (R chDate(f rstT  )),
    lastT   = None,
    batch ncre nt = Batch ncre nt(batch ncre nt)
  )

  overr de def sc duledJob: Execut on[Un ] = Analyt csBatchExecut on(execArgs) { dateRange =>
    Execut on.w h d {  mpl c  un que d =>
      Execut on.w hArgs { args =>
        val prev ousGraph =  f (dateRange.start.t  stamp == f rstStartDate.t  stamp) {
          log. nfo("Looks l ke t   s t  f rst t  , sett ng prev ousGraph to None")
          None
        } else {
          So (
            DAL
              .readMostRecentSnapshot(UserUserFavGraphScalaDataset, dateRange - batch ncre nt)
              .toTypedP pe
          )
        }
        val halfL ves = args.l st("halfL ves nDays").map(_.to nt)
        val m nScoreToKeep = args.double("m nScoreToKeep", 1e-5)
        Ut l.pr ntCounters(
          UserUserFavGraph
            .getSum dFavGraph(prev ousGraph, dateRange, halfL ves, m nScoreToKeep)
            .wr eDALSnapshotExecut on(
              UserUserFavGraphScalaDataset,
              D.Da ly,
              D.Suff x(outputPath),
              D.EBLzo(),
              dateRange.end)
        )
      }
    }
  }
}

object DumpFavGraphAdhoc extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val favGraph = DAL
            .readMostRecentSnapshotNoOlderThan(UserUserFavGraphScalaDataset, Days(10))
            .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
            .toTypedP pe
            .collect {
              case edge  f edge.  ghts.halfL fe nDaysToDecayedSums.conta ns(100) =>
                (edge.s ce d, edge.dest nat on d, edge.  ghts.halfL fe nDaysToDecayedSums(100))
            }

          Execut on
            .sequence(
              Seq(
                Ut l.pr ntSummaryOfNu r cColumn(
                  favGraph.map(_._3),
                  So ("  ght")
                ),
                Ut l.pr ntSummaryOfNu r cColumn(
                  favGraph.map(c => math.log10(10.0 + c._3)),
                  So ("  ght_Log_P10")
                ),
                Ut l.pr ntSummaryOfNu r cColumn(
                  favGraph.map(c => math.log10(1.0 + c._3)),
                  So ("  ght_Log_P1")
                ),
                Ut l.pr ntSummaryOfCategor calColumn(favGraph.map(_._1), So ("S ce d")),
                Ut l.pr ntSummaryOfCategor calColumn(favGraph.map(_._2), So ("Dest d"))
              )
            ).flatMap { summarySeq =>
              pr ntln(summarySeq.mkStr ng("\n"))
              Execut on.un 
            }
        }
    }
}
