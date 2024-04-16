package com.tw ter.s mclusters_v2.scald ng.embedd ng.common

 mport com.tw ter.algeb rd.Aggregator
 mport com.tw ter.common.text.language.LocaleUt l
 mport com.tw ter.esc rb rd.common.thr ftscala.Locale
 mport com.tw ter.esc rb rd.common.thr ftscala.Local zedUser
 mport com.tw ter.esc rb rd. tadata.thr ftscala.Full tadata
 mport com.tw ter.esc rb rd.scald ng.s ce.Full tadataS ce
 mport com.tw ter.esc rb rd.scald ng.s ce.utt.UttS ceScalaDataset
 mport com.tw ter.esc rb rd.utt.strato.thr ftscala.SnapshotType
 mport com.tw ter.esc rb rd.utt.thr ftscala.UttEnt yRecord
 mport com.tw ter. nterests_ds.jobs. nterests_serv ce.UserTop cRelat onSnapshotScalaDataset
 mport com.tw ter. nterests.thr ftscala. nterestRelat onType
 mport com.tw ter. nterests.thr ftscala.User nterestsRelat onSnapshot
 mport com.tw ter.pengu n.scald ng.datasets.Pengu nUserLanguagesScalaDataset
 mport com.tw ter.scald ng.DateOps
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.Stat
 mport com.tw ter.scald ng.TypedP pe
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.scald ng.ValueP pe
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossClusterSa DC
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.common._
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S mclustersV2 nterested n20M145KUpdatedScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.UserUserFavGraphScalaDataset
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossDC
 mport com.tw ter.common_ ader.thr ftscala.Common ader
 mport com.tw ter.common_ ader.thr ftscala. dType
 mport com.tw ter.common_ ader.thr ftscala.Vers onedCommon ader
 mport flockdb_tools.datasets.flock.FlockBlocksEdgesScalaDataset
 mport flockdb_tools.datasets.flock.FlockFollowsEdgesScalaDataset
 mport flockdb_tools.datasets.flock.FlockReportAsAbuseEdgesScalaDataset
 mport flockdb_tools.datasets.flock.FlockReportAsSpamEdgesScalaDataset
 mport twadoop_conf g.conf gurat on.log_categor es.group.search.Adapt veSearchScalaDataset
 mport com.tw ter.search.adapt ve.scr b ng.thr ftscala.Adapt veSearchScr beLog
 mport twadoop_conf g.conf gurat on.log_categor es.group.t  l ne.T  l neServ ceFavor esScalaDataset
 mport t ets ce.common.UnhydratedFlatScalaDataset
 mport com.tw ter.fr gate.data_p pel ne.mag crecs.mag crecs_not f cat ons_l e.thr ftscala.Mag cRecsNot f cat onL e
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.s mclusters_v2.thr ftscala.EdgeW hDecayed  ghts
 mport com.tw ter.t  l neserv ce.thr ftscala.Contextual zedFavor eEvent
 mport com.tw ter.t  l neserv ce.thr ftscala.Favor eEventUn on
 mport com.tw ter.t ets ce.common.thr ftscala.UnhydratedFlatT et
 mport com.tw ter.users ce.snapshot.flat.Users ceFlatScalaDataset
 mport com.tw ter.wtf.ent y_real_graph.scald ng.common.DatasetConstants
 mport com.tw ter.wtf.ent y_real_graph.scald ng.common.Semant cCoreF lters
 mport com.tw ter.wtf.scald ng.cl ent_event_process ng.thr ftscala. nteract onDeta ls
 mport com.tw ter.wtf.scald ng.cl ent_event_process ng.thr ftscala. nteract onType
 mport com.tw ter.wtf.scald ng.cl ent_event_process ng.thr ftscala.T et mpress onDeta ls
 mport com.tw ter.fr gate.data_p pel ne.scald ng.mag crecs.mag crecs_not f cat on_l e.Mag crecsNot f cat onL e1DayLagScalaDataset
 mport com.tw ter. es ce.thr ftscala. nteract onEvent
 mport com.tw ter. es ce.thr ftscala. nteract onTargetType
 mport com.tw ter.wtf.scald ng.jobs.cl ent_event_process ng.User nteract onScalaDataset
 mport java.ut l.T  Zone
 mport com.tw ter. nterests_ds.jobs. nterests_serv ce.User nterestRelat onSnapshotScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l.User d
 mport com.tw ter.scald ng.typed.{ValueP pe => TypedValueP pe}
 mport com.tw ter.t ets ce.common.thr ftscala.UnhydratedT et
 mport t ets ce.common.UnhydratedScalaDataset

object ExternalDataS ces {
  val UTTDoma n = 131L
  val users ceColumns = Set(" d", "account_country_code", "language")
  val Val dFlockEdgeState d = 0

  def getStandardLanguageCode(language: Str ng): Opt on[Str ng] = {
    val locale = LocaleUt l.getLocaleOf(language)
     f (locale == LocaleUt l.UNKNOWN) None else So (locale.getLanguage)
  }

  // Reads UTT Ent y Records (`utt_s ce` dataset)
  def getUttEnt yRecords( mpl c  t  Zone: T  Zone): TypedP pe[UttEnt yRecord] = {
    DAL
      .readMostRecentSnapshotNoOlderThan(UttS ceScalaDataset, Days(14))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
  }

  /**
   * Extracts t  KGO seeds from t  UTT Ent y Records.
   * Uses t  most recent "Stable" vers on by default unless spec f ed ot rw se.
   *
   * @param uttVers on UTT Vers on to use  nstead of t  default value.
   */
  def getLocaleProducerSeed dsFromUttEnt yRecords(
    uttVers on: Opt on[Long] = None
  )(
     mpl c  t  Zone: T  Zone,
    un que d: Un que D
  ): TypedP pe[((Top c d, Language), Seq[User d])] = {

    val top cLangPa rCount = Stat("top c_lang_pa r_count_all")
    val top cLangPa rCountEmptySeed = Stat("top c_lang_pa r_count_empty_seed")
    val top cLangPa rCountLteOneSeed = Stat("top c_lang_pa r_count_lte_one_seed")
    val top cLangPa rCountLteF veSeeds = Stat("top c_lang_pa r_count_lte_f ve_seeds")
    val top cLangPa rCountLteTenSeeds = Stat("top c_lang_pa r_count_lte_ten_seeds")

    val uttEnt yRecords: TypedP pe[UttEnt yRecord] = getUttEnt yRecords

    val uttVers onToUse: ValueP pe[Long] = uttVers on match {
      case So (uttVers onValue) =>
        TypedValueP pe(uttVers onValue)
      case _ => // f nd t  most recent "stable" vers on as recom nded by t  Semant cCore team
        uttEnt yRecords
          .f lter(_.snapshotType.ex sts(_ == SnapshotType.Stable))
          .map(_.vers on)
          .d st nct
          .aggregate(Aggregator.m n) // t  most recent vers on  s t  smallest negat ve value
    }

    val uttEnt yRecordsS ngleVers on: TypedP pe[UttEnt yRecord] =
      uttEnt yRecords
        .f lterW hValue(uttVers onToUse) {
          case (uttEnt yRecord: UttEnt yRecord, uttVers onOpt: Opt on[Long]) =>
            uttVers onOpt.conta ns(uttEnt yRecord.vers on)
        }

    uttEnt yRecordsS ngleVers on.flatMap { uttEnt yRecord: UttEnt yRecord =>
      val local zedUsers: Seq[Local zedUser] =
        uttEnt yRecord.knownForUsers.flatMap(_.local zedUsers).getOrElse(N l)

      val val dLocal zedUsers: Seq[(Top c d, Language, User d)] =
        local zedUsers
          .flatMap {
            case Local zedUser(user d: User d, So (Locale(So (language: Str ng), _)), _) =>
              So ((uttEnt yRecord.ent y d, language, user d))
            case _ =>
              None
          }

      val localeProducerSeed ds: Seq[((Top c d, Language), Seq[User d])] = val dLocal zedUsers
        .groupBy {
          case (top c d: Top c d, language: Language, _) =>
            (top c d, language)
        }
        .mapValues(_.map(_._3).d st nct) // values are d st nct producer ds
        .toSeq

      localeProducerSeed ds.foreach { // stats
        case (_, seed ds: Seq[User d]) =>
          top cLangPa rCount. nc()
           f (seed ds. sEmpty) top cLangPa rCountEmptySeed. nc()
           f (seed ds.length <= 1) top cLangPa rCountLteOneSeed. nc()
           f (seed ds.length <= 5) top cLangPa rCountLteF veSeeds. nc()
           f (seed ds.length <= 10) top cLangPa rCountLteTenSeeds. nc()
      }

      localeProducerSeed ds
    }.forceToD sk
  }

  def uttEnt  esS ce(
    customFull tadataS ce: Opt on[TypedP pe[Full tadata]] = None
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[Long] = {
    customFull tadataS ce
      .getOrElse(full tadataS ce)
      .flatMap {
        case full tadata  f full tadata.doma n d == UTTDoma n =>
          for {
            bas c tadata <- full tadata.bas c tadata
             ndexableF elds <- bas c tadata. ndexableF elds
            tags <-  ndexableF elds.tags
             f !Semant cCoreF lters.shouldF lterByTags(tags.toSet, DatasetConstants.stopTags)
          } y eld {
            full tadata.ent y d
          }
        case _ => None
      }
  }

  // Get followable top cs from Esc rb rd
  def uttFollowableEnt  esS ce(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): TypedP pe[Long] = {
    val followableEnt yCount = Stat("followable_ent  es_count")
    val FollowableTag = "utt:followable_top c"
    full tadataS ce
      .flatMap {
        case full tadata  f full tadata.doma n d == UTTDoma n =>
          for {
            bas c tadata <- full tadata.bas c tadata
             ndexableF elds <- bas c tadata. ndexableF elds
            tags <-  ndexableF elds.tags
             f tags.conta ns(FollowableTag)
          } y eld {
            followableEnt yCount. nc()
            full tadata.ent y d
          }
        case _ => None
      }
  }

  def full tadataS ce(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[Full tadata] = {
    TypedP pe
      .from(
        new Full tadataS ce(s"/atla/proc/${Full tadataS ce.DefaultHdfsPath}")()(
          dateRange.emb ggen(Days(7))))
  }

  def userS ce( mpl c  t  Zone: T  Zone): TypedP pe[(User d, (Country, Language))] =
    DAL
      .readMostRecentSnapshotNoOlderThan(Users ceFlatScalaDataset, Days(7))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .w hColumns(users ceColumns)
      .toTypedP pe.flatMap { flatUser =>
        for {
          user d <- flatUser. d
          country <- flatUser.accountCountryCode
          language <- flatUser.language
          standardLang <- getStandardLanguageCode(language)
        } y eld {
          (user d, country.toUpperCase, standardLang)
        }
      }.d st nct
      .map { case (user, country, lang) => user -> (country, lang) }

  // Bu ld user language s ce from  nferred languages (pengu n_user_languages dataset)
  def  nferredUserConsu dLanguageS ce(
     mpl c  t  Zone: T  Zone
  ): TypedP pe[(User d, Seq[(Language, Double)])] = {
    DAL
      .readMostRecentSnapshotNoOlderThan(Pengu nUserLanguagesScalaDataset, Days(7))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
      .map { kv =>
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

  def  nferredUserProducedLanguageS ce(
     mpl c  t  Zone: T  Zone
  ): TypedP pe[(User d, Seq[(Language, Double)])] = {
    DAL
      .readMostRecentSnapshotNoOlderThan(Pengu nUserLanguagesScalaDataset, Days(7))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
      .map { kv =>
        val produced = kv.value.produced
          .collect {
            case scoredStr ng  f scoredStr ng.  ght > 0.15 => //throw away 5% outl ers
              (getStandardLanguageCode(scoredStr ng. em), scoredStr ng.  ght)
          }.collect {
            case (So (language), score) => (language, score)
          }
        (kv.key, produced)
      }
  }

  def s mClusters nterest nS ce(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[KeyVal[User d, ClustersUser s nterested n]] = {
    DAL
      .readMostRecentSnapshotNoOlderThan(
        S mclustersV2 nterested n20M145KUpdatedScalaDataset,
        Days(30))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
  }

  def s mClusters nterest nLogFavS ce(
    m nLogFavScore: Double
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[(User d, Map[Cluster d, Double])] = {
    s mClusters nterest nS ce.map {
      case KeyVal(user d, clustersUser s nterested n) =>
        user d -> clustersUser s nterested n.cluster dToScores
          .map {
            case (cluster d, scores) =>
              cluster d -> scores.logFavScore.getOrElse(0.0)
          }
          .f lter(_._2 > m nLogFavScore)
          .toMap
    }
  }

  def top cFollowGraphS ce(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): TypedP pe[(Top c d, User d)] = {
    val userTop cFollowCount = Stat("user_top c_follow_count")
    DAL
      .readMostRecentSnapshotNoOlderThan(UserTop cRelat onSnapshotScalaDataset, Days(7))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
      .collect {
        case user nterestsRelat onSnapshot: User nterestsRelat onSnapshot
             f user nterestsRelat onSnapshot. nterestType == "UTT" &&
              user nterestsRelat onSnapshot.relat on ==  nterestRelat onType.Follo d =>
          (user nterestsRelat onSnapshot. nterest d, user nterestsRelat onSnapshot.user d)
      }
      .hashJo n(uttFollowableEnt  esS ce.asKeys)
      .map {
        case (top c, (user, _)) =>
          userTop cFollowCount. nc()
          (top c, user)
      }
  }

  def not nterestedTop csS ce(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): TypedP pe[(Top c d, User d)] = {
    val userNot nterested nTop csCount = Stat("user_not_ nterested_ n_top cs_count")
    DAL
      .readMostRecentSnapshotNoOlderThan(
        User nterestRelat onSnapshotScalaDataset,
        Days(7)).w hRemoteReadPol cy(Expl c Locat on(ProcAtla)).toTypedP pe.collect {
        case user nterestsRelat onSnapshot: User nterestsRelat onSnapshot
             f user nterestsRelat onSnapshot. nterestType == "UTT" &&
              user nterestsRelat onSnapshot.relat on ==  nterestRelat onType.Not nterested =>
          (user nterestsRelat onSnapshot. nterest d, user nterestsRelat onSnapshot.user d)
      }
      .hashJo n(uttFollowableEnt  esS ce.asKeys)
      .map {
        case (top c, (user, _)) =>
          userNot nterested nTop csCount. nc()
          (top c, user)
      }
  }

  def t etS ce(
     mpl c  dateRange: DateRange
  ): TypedP pe[UnhydratedT et] = {
    DAL
      .read(UnhydratedScalaDataset, dateRange).w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
  }

  def flatT etsS ce(
     mpl c  dateRange: DateRange
  ): TypedP pe[UnhydratedFlatT et] = {
    DAL
      .read(UnhydratedFlatScalaDataset, dateRange)
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
  }

  def userT etFavor esS ce(
     mpl c  dateRange: DateRange
  ): TypedP pe[(User d, T et d, T  stamp)] = {
    DAL
      .read(T  l neServ ceFavor esScalaDataset, dateRange)
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
      .flatMap { cfe: Contextual zedFavor eEvent =>
        cfe.event match {
          case Favor eEventUn on.Favor e(fav) =>
            So (fav.user d, fav.t et d, fav.eventT  Ms)
          case _ =>
            None
        }
      }
  }

  def userT et mpress onsS ce(
    d llSec:  nt = 1
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[(User d, T et d, T  stamp)] = {
    DAL
      .read(User nteract onScalaDataset, dateRange)
      .w hRemoteReadPol cy(AllowCrossClusterSa DC)
      .toTypedP pe
      .flatMap {
        case user nteract on
             f user nteract on. nteract onType ==  nteract onType.T et mpress ons =>
          user nteract on. nteract onDeta ls match {
            case  nteract onDeta ls.T et mpress onDeta ls(
                  T et mpress onDeta ls(t et d, _, d llT   nSecOpt))
                 f d llT   nSecOpt.ex sts(_ >= d llSec) =>
              So (user nteract on.user d, t et d, user nteract on.t  Stamp)
            case _ =>
              None
          }
        case _ => None
      }
  }

  def transformFavEdges(
     nput: TypedP pe[EdgeW hDecayed  ghts],
    halfL fe nDaysForFavScore:  nt
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(Long, Long, Double)] = {
    val numEdgesW hSpec f edHalfL fe = Stat(
      s"num_edges_w h_spec f ed_half_l fe_${halfL fe nDaysForFavScore}_days")
    val numEdgesW houtSpec f edHalfL fe = Stat(
      s"num_edges_w hout_spec f ed_half_l fe_${halfL fe nDaysForFavScore}_days")
     nput
      .flatMap { edge =>
         f (edge.  ghts.halfL fe nDaysToDecayedSums.conta ns(halfL fe nDaysForFavScore)) {
          numEdgesW hSpec f edHalfL fe. nc()
          So ((edge.s ce d, edge.dest nat on d, edge.  ghts.halfL fe nDaysToDecayedSums(100)))
        } else {
          numEdgesW houtSpec f edHalfL fe. nc()
          None
        }
      }
  }

  def getFavEdges(
    halfL fe nDaysForFavScore:  nt
  )(
     mpl c  dateRange: DateRange,
    un que D: Un que D
  ): TypedP pe[(Long, Long, Double)] = {
     mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
    transformFavEdges(
      DAL
        .readMostRecentSnapshotNoOlderThan(UserUserFavGraphScalaDataset, Days(14))
        .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
        .toTypedP pe,
      halfL fe nDaysForFavScore
    )
  }

  def flockReportAsSpamS ce(
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[(User d, User d)] = {
    DAL
      .readMostRecentSnapshot(FlockReportAsSpamEdgesScalaDataset)
      .toTypedP pe
      .collect {
        case edge  f edge.state == Val dFlockEdgeState d =>
          (edge.s ce d, edge.dest nat on d)
      }
  }

  def flockBlocksS ce(
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[(User d, User d)] = {
    DAL
      .readMostRecentSnapshot(FlockBlocksEdgesScalaDataset)
      .toTypedP pe
      .collect {
        case edge  f edge.state == Val dFlockEdgeState d =>
          (edge.s ce d, edge.dest nat on d)
      }
  }

  def flockFollowsS ce(
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[(User d, User d)] = {
    DAL
      .readMostRecentSnapshot(FlockFollowsEdgesScalaDataset)
      .toTypedP pe
      .collect {
        case edge  f edge.state == Val dFlockEdgeState d =>
          (edge.s ce d, edge.dest nat on d)
      }
  }

  def flockReportAsAbuseS ce(
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[(User d, User d)] = {
    DAL
      .readMostRecentSnapshot(FlockReportAsAbuseEdgesScalaDataset)
      .toTypedP pe
      .collect {
        case edge  f edge.state == Val dFlockEdgeState d =>
          (edge.s ce d, edge.dest nat on d)
      }
  }

  def mag cRecsNotf cat onOpenOrCl ckEventsS ce(
     mpl c  dateRange: DateRange
  ): TypedP pe[Mag cRecsNot f cat onL e] = {
    DAL
      .read(Mag crecsNot f cat onL e1DayLagScalaDataset, dateRange)
      .toTypedP pe
      .f lter { entry =>
        // keep entr es w h a val d user d and t et d, opened or cl cked t  stamp def ned
        val user dEx sts = entry.targetUser d. sDef ned
        val t et dEx sts = entry.t et d. sDef ned
        val openOrCl ckEx sts =
          entry.openT  stampMs. sDef ned || entry.ntabCl ckT  stampMs. sDef ned
        user dEx sts && t et dEx sts && openOrCl ckEx sts
      }
  }

  def  eS ceT etEngage ntsS ce( mpl c  dateRange: DateRange): TypedP pe[ nteract onEvent] = {
    DAL
      .read(
        com.tw ter. es ce.process ng.events.batch.ServerEngage ntsScalaDataset,
        dateRange).w hColumns(
        Set("target d", "targetType", "engag ngUser d", "deta ls", "referenceT et"))
      .toTypedP pe
      .f lter { event =>
        // f lter out logged out users because t  r favor es are less rel able
        event.engag ngUser d > 0L && event.targetType ==  nteract onTargetType.T et
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

  def adapt veSearchScr beLogsS ce(
     mpl c  dateRange: DateRange
  ): TypedP pe[(User d, Str ng)] = {
    val searchData: TypedP pe[Adapt veSearchScr beLog] =
      DAL
        .read(Adapt veSearchScalaDataset, dateRange).toTypedP pe

    searchData
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
}
