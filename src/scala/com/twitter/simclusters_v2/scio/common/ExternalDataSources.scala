package com.tw ter.s mclusters_v2.sc o.common

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.common.ut l.Clock
 mport com.tw ter.common_ ader.thr ftscala.Common ader
 mport com.tw ter.common_ ader.thr ftscala. dType
 mport com.tw ter.common_ ader.thr ftscala.Vers onedCommon ader
 mport com.tw ter.fr gate.data_p pel ne.mag crecs.mag crecs_not f cat ons_l e.thr ftscala.Mag cRecsNot f cat onL e
 mport com.tw ter.fr gate.data_p pel ne.scald ng.mag crecs.mag crecs_not f cat on_l e.Mag crecsNot f cat onL e1DayLagScalaDataset
 mport com.tw ter. es ce.thr ftscala. nteract onEvent
 mport com.tw ter. es ce.thr ftscala. nteract onTargetType
 mport com.tw ter. nterests_ds.jobs. nterests_serv ce.UserTop cRelat onSnapshotScalaDataset
 mport com.tw ter. nterests.thr ftscala. nterestRelat onType
 mport com.tw ter. nterests.thr ftscala.User nterestsRelat onSnapshot
 mport com.tw ter.pengu n.scald ng.datasets.Pengu nUserLanguagesScalaDataset
 mport com.tw ter.search.adapt ve.scr b ng.thr ftscala.Adapt veSearchScr beLog
 mport com.tw ter.s mclusters_v2.hdfs_s ces.UserUserFavGraphScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces.Val dFlockEdgeState d
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces.getStandardLanguageCode
 mport com.tw ter.twadoop.user.gen.thr ftscala.Comb nedUser
 mport flockdb_tools.datasets.flock.FlockBlocksEdgesScalaDataset
 mport flockdb_tools.datasets.flock.FlockFollowsEdgesScalaDataset
 mport flockdb_tools.datasets.flock.FlockReportAsAbuseEdgesScalaDataset
 mport flockdb_tools.datasets.flock.FlockReportAsSpamEdgesScalaDataset
 mport org.joda.t  . nterval
 mport com.tw ter.s mclusters_v2.thr ftscala.EdgeW hDecayed  ghts
 mport com.tw ter.users ce.snapshot.comb ned.Users ceScalaDataset
 mport com.tw ter.users ce.snapshot.flat.Users ceFlatScalaDataset
 mport com.tw ter.ut l.Durat on
 mport twadoop_conf g.conf gurat on.log_categor es.group.search.Adapt veSearchScalaDataset

object ExternalDataS ces {
  def userS ce(
    noOlderThan: Durat on = Durat on.fromDays(7)
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[Comb nedUser] = {
    sc.custom nput(
      "ReadUserS ce",
      DAL
        .readMostRecentSnapshotNoOlderThan(
          Users ceScalaDataset,
          noOlderThan,
          Clock.SYSTEM_CLOCK,
          DAL.Env ron nt.Prod
        )
    )
  }

  def userCountryS ce(
    noOlderThan: Durat on = Durat on.fromDays(7)
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[(Long, Str ng)] = {
    sc.custom nput(
        "ReadUserCountryS ce",
        DAL
          .readMostRecentSnapshotNoOlderThan(
            Users ceFlatScalaDataset,
            noOlderThan,
            Clock.SYSTEM_CLOCK,
            DAL.Env ron nt.Prod,
          )
      ).flatMap { flatUser =>
        for {
          user d <- flatUser. d
          country <- flatUser.accountCountryCode
        } y eld {
          (user d, country.toUpperCase)
        }
      }.d st nct
  }

  def userUserFavS ce(
    noOlderThan: Durat on = Durat on.fromDays(14)
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[EdgeW hDecayed  ghts] = {
    sc.custom nput(
      "ReadUserUserFavS ce",
      DAL
        .readMostRecentSnapshotNoOlderThan(
          UserUserFavGraphScalaDataset,
          noOlderThan,
          Clock.SYSTEM_CLOCK,
          DAL.Env ron nt.Prod
        )
    )
  }

  def  nferredUserConsu dLanguageS ce(
    noOlderThan: Durat on = Durat on.fromDays(7)
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[(Long, Seq[(Str ng, Double)])] = {
    sc.custom nput(
        "Read nferredUserConsu dLanguageS ce",
        DAL
          .readMostRecentSnapshotNoOlderThan(
            Pengu nUserLanguagesScalaDataset,
            noOlderThan,
            Clock.SYSTEM_CLOCK,
            DAL.Env ron nt.Prod
          )
      ).map { kv =>
        val consu d = kv.value.consu d
          .collect {
            case scoredStr ng  f scoredStr ng.  ght > 0.001 => //throw away 5% outl ers
              (getStandardLanguageCode(scoredStr ng. em), scoredStr ng.  ght)
          }.collect {
            case (So (language), score) => (language, score)
          }
        (kv.key, consu d)
      }
  }

  def flockBlockS ce(
    noOlderThan: Durat on = Durat on.fromDays(7)
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[(Long, Long)] = {
    sc.custom nput(
        "ReadFlockBlock",
        DAL.readMostRecentSnapshotNoOlderThan(
          FlockBlocksEdgesScalaDataset,
          noOlderThan,
          Clock.SYSTEM_CLOCK,
          DAL.Env ron nt.Prod))
      .collect {
        case edge  f edge.state == Val dFlockEdgeState d =>
          (edge.s ce d, edge.dest nat on d)
      }
  }

  def flockFollowS ce(
    noOlderThan: Durat on = Durat on.fromDays(7)
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[(Long, Long)] = {
    sc.custom nput(
        "ReadFlockFollow",
        DAL
          .readMostRecentSnapshotNoOlderThan(
            FlockFollowsEdgesScalaDataset,
            noOlderThan,
            Clock.SYSTEM_CLOCK,
            DAL.Env ron nt.Prod))
      .collect {
        case edge  f edge.state == Val dFlockEdgeState d =>
          (edge.s ce d, edge.dest nat on d)
      }
  }

  def flockReportAsAbuseS ce(
    noOlderThan: Durat on = Durat on.fromDays(7)
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[(Long, Long)] = {
    sc.custom nput(
        "ReadFlockReportAsAbuseJava",
        DAL
          .readMostRecentSnapshotNoOlderThan(
            FlockReportAsAbuseEdgesScalaDataset,
            noOlderThan,
            Clock.SYSTEM_CLOCK,
            DAL.Env ron nt.Prod)
      )
      .collect {
        case edge  f edge.state == Val dFlockEdgeState d =>
          (edge.s ce d, edge.dest nat on d)
      }
  }

  def flockReportAsSpamS ce(
    noOlderThan: Durat on = Durat on.fromDays(7)
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[(Long, Long)] = {
    sc.custom nput(
        "ReadFlockReportAsSpam",
        DAL
          .readMostRecentSnapshotNoOlderThan(
            FlockReportAsSpamEdgesScalaDataset,
            noOlderThan,
            Clock.SYSTEM_CLOCK,
            DAL.Env ron nt.Prod))
      .collect {
        case edge  f edge.state == Val dFlockEdgeState d =>
          (edge.s ce d, edge.dest nat on d)
      }
  }

  def  eS ceT etEngage ntsS ce(
     nterval:  nterval
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[ nteract onEvent] = {
    sc.custom nput(
        "Read eS ceT etEngage ntsS ce",
        DAL
          .read(
            com.tw ter. es ce.process ng.events.batch.ServerEngage ntsScalaDataset,
             nterval,
            DAL.Env ron nt.Prod,
          )
      ).f lter { event =>
        // f lter out logged out users because t  r favor es are less rel able
        event.engag ngUser d > 0L && event.targetType ==  nteract onTargetType.T et
      }
  }

  def top cFollowGraphS ce(
    noOlderThan: Durat on = Durat on.fromDays(7)
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[(Long, Long)] = {
    // T   mple ntat on  re  s sl ghtly d fferent than t  top cFollowGraphS ce funct on  n
    // src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/common/ExternalDataS ces.scala
    //   don't do an add  onal hashJo n on uttFollowableEnt  esS ce.
    sc.custom nput(
        "ReadTop cFollowGraphS ce",
        DAL
          .readMostRecentSnapshotNoOlderThan(
            UserTop cRelat onSnapshotScalaDataset,
            noOlderThan,
            Clock.SYSTEM_CLOCK,
            DAL.Env ron nt.Prod
          )
      ).collect {
        case user nterestsRelat onSnapshot: User nterestsRelat onSnapshot
             f user nterestsRelat onSnapshot. nterestType == "UTT" &&
              user nterestsRelat onSnapshot.relat on ==  nterestRelat onType.Follo d =>
          (user nterestsRelat onSnapshot. nterest d, user nterestsRelat onSnapshot.user d)
      }
  }

  def mag cRecsNotf cat onOpenOrCl ckEventsS ce(
     nterval:  nterval
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[Mag cRecsNot f cat onL e] = {
    sc.custom nput(
        "ReadMag cRecsNotf cat onOpenOrCl ckEventsS ce",
        DAL
          .read(Mag crecsNot f cat onL e1DayLagScalaDataset,  nterval, DAL.Env ron nt.Prod))
      .f lter { entry =>
        // keep entr es w h a val d user d and t et d, opened or cl cked t  stamp def ned
        val user dEx sts = entry.targetUser d. sDef ned
        val t et dEx sts = entry.t et d. sDef ned
        val openOrCl ckEx sts =
          entry.openT  stampMs. sDef ned || entry.ntabCl ckT  stampMs. sDef ned
        user dEx sts && t et dEx sts && openOrCl ckEx sts
      }
  }

  def adapt veSearchScr beLogsS ce(
     nterval:  nterval
  )(
     mpl c  sc: Sc oContext
  ): SCollect on[(Long, Str ng)] = {
    sc.custom nput(
        "ReadAdapt veSearchScr beLogsS ce",
        DAL
          .read(Adapt veSearchScalaDataset,  nterval, DAL.Env ron nt.Prod))
      .flatMap({ scr beLog: Adapt veSearchScr beLog =>
        for {
          user d <- user dFromBlenderAdapt veScr beLog(scr beLog)
          // f lter out logged out search quer es
           f user d != 0
          queryStr ng <- scr beLog.requestLog.flatMap(_.request).flatMap(_.rawQuery)
        } y eld {
          (user d, Set(queryStr ng))
        }
      })
      //  f a user searc s for t  sa  query mult ple t  s, t re could be dupl cates.
      // De-dup t m to get t  d st nct quer es searc d by a user
      .sumByKey
      .flatMap {
        case (user d, d st nctQuerySet) =>
          d st nctQuerySet.map { query =>
            (user d, query)
          }
      }
  }

  pr vate def user dFromBlenderAdapt veScr beLog(
    blenderAdapt veLog: Adapt veSearchScr beLog
  ): Opt on[Long] = {
    blenderAdapt veLog.vers onedCommon ader match {
      case Vers onedCommon ader.Common ader(Common ader.Server ader(server ader)) =>
        server ader.request nfo match {
          case So (request nfo) => request nfo. ds.get( dType.User d).map(_.toLong)
          case _ => None
        }
      case _ => None
    }
  }

}
