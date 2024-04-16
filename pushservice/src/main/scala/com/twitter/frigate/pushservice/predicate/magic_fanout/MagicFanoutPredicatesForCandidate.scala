package com.tw ter.fr gate.pushserv ce.pred cate.mag c_fanout

 mport com.tw ter.aud ence_rewards.thr ftscala.HasSuperFollow ngRelat onsh pRequest
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Mag cFanoutCand date
 mport com.tw ter.fr gate.common.base.Mag cFanoutCreatorEventCand date
 mport com.tw ter.fr gate.common.base.Mag cFanoutProductLaunchCand date
 mport com.tw ter.fr gate.common. tory.Rec ems
 mport com.tw ter.fr gate.common.pred cate.Fat guePred cate.bu ld
 mport com.tw ter.fr gate.common.pred cate.Fat guePred cate.productLaunchTypeRecTypesOnlyF lter
 mport com.tw ter.fr gate.common.pred cate.Fat guePred cate.recOnlyF lter
 mport com.tw ter.fr gate.common.store. nterests. nterestsLookupRequestW hContext
 mport com.tw ter.fr gate.common.store. nterests.Semant cCoreEnt y d
 mport com.tw ter.fr gate.common.ut l. b sAppPushDev ceSett ngsUt l
 mport com.tw ter.fr gate.mag c_events.thr ftscala.CreatorFanoutType
 mport com.tw ter.fr gate.mag c_events.thr ftscala.ProductType
 mport com.tw ter.fr gate.mag c_events.thr ftscala.Target D
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutEventHydratedCand date
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutEventPushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutNewsEventPushCand date
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.pred cate.Fat guePred cate
 mport com.tw ter.fr gate.pushserv ce.pred cate.Pred catesForCand date
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.fr gate.thr ftscala.Not f cat onD splayLocat on
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter. nterests.thr ftscala.User nterests
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future

object Mag cFanoutPred catesForCand date {

  /**
   * C ck  f Semant c Core reasons sat sfy rank threshold ( for  avy users a non broad ent y should sat sfy t  threshold)
   */
  def mag cFanoutErg nterestRankThresholdPred cate(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[Mag cFanoutEventHydratedCand date] = {
    val na  = "mag cfanout_ nterest_erg_rank_threshold"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync { cand date: Mag cFanoutEventHydratedCand date =>
        cand date.target. s avyUserState.map {  s avyUser =>
          lazy val rankThreshold =
             f ( s avyUser) {
              cand date.target.params(PushFeatureSw chParams.Mag cFanoutRankErgThreshold avy)
            } else {
              cand date.target.params(PushFeatureSw chParams.Mag cFanoutRankErgThresholdNon avy)
            }
          Mag cFanoutPred catesUt l
            .c ck fVal dErgScEnt yReasonEx sts(
              cand date.effect veMag cEventsReasons,
              rankThreshold
            )
        }
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  def newsNot f cat onFat gue(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = "news_not f cat on_fat gue"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync { cand date: PushCand date =>
        Fat guePred cate
          .recTypeSetOnly(
            not f cat onD splayLocat on = Not f cat onD splayLocat on.PushToMob leDev ce,
            recTypes = Set(CommonRecom ndat onType.Mag cFanoutNewsEvent),
            max n nterval =
              cand date.target.params(PushFeatureSw chParams.MFMaxNumberOfPus s n nterval),
             nterval = cand date.target.params(PushFeatureSw chParams.MFPush nterval nH s),
            m n nterval = cand date.target.params(PushFeatureSw chParams.MFM n ntervalFat gue)
          )
          .apply(Seq(cand date))
          .map(_. adOpt on.getOrElse(false))

      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  /**
   * C ck  f reason conta ns any optouted semant c core ent y  nterests.
   *
   * @param stats
   *
   * @return
   */
  def mag cFanoutNoOptout nterestPred cate(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[Mag cFanoutEventPushCand date] = {
    val na  = "mag cfanout_optout_ nterest_pred cate"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    val w hOptOut nterestsCounter = stats.counter("w h_optout_ nterests")
    val w houtOptOut nterestsCounter = stats.counter("w hout_optout_ nterests")
    Pred cate
      .fromAsync { cand date: Mag cFanoutEventPushCand date =>
        cand date.target.optOutSemant cCore nterests.map {
          case (
                optOutUser nterests: Seq[Semant cCoreEnt y d]
              ) =>
            w hOptOut nterestsCounter. ncr()
            optOutUser nterests
              . ntersect(cand date.annotatedAnd nferredSemant cCoreEnt  es). sEmpty
          case _ =>
            w houtOptOut nterestsCounter. ncr()
            true
        }
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  /**
   * C cks  f t  target has only one dev ce language language,
   * and that language  s targeted for that event
   *
   * @param statsRece ver
   *
   * @return
   */
  def  nferredUserDev ceLanguagePred cate(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[Mag cFanoutEventPushCand date] = {
    val na  = " nferred_dev ce_language"
    val scopedStats = statsRece ver.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync { cand date: Mag cFanoutEventPushCand date =>
        val target = cand date.target
        target.dev ce nfo.map {
          _.flatMap { dev ce nfo =>
            val languages = dev ce nfo.dev ceLanguages.getOrElse(Seq.empty[Str ng])
            val d st nctDev ceLanguages =
               b sAppPushDev ceSett ngsUt l.d st nctDev ceLanguages(languages)

            cand date.newsFor  tadata.map { newsFor  tadata =>
              val eventLocales = newsFor  tadata.locales.getOrElse(Seq.empty)
              val eventLanguages = eventLocales.flatMap(_.language).map(_.toLo rCase).d st nct

              eventLanguages. ntersect(d st nctDev ceLanguages).nonEmpty
            }
          }.getOrElse(false)
        }
      }
      .w hStats(scopedStats)
      .w hNa (na )
  }

  /**
   * Bypass pred cate  f h gh pr or y push
   */
  def h ghPr or yNewsEventExceptedPred cate(
    pred cate: Na dPred cate[Mag cFanoutNewsEventPushCand date]
  )(
     mpl c  conf g: Conf g
  ): Na dPred cate[Mag cFanoutNewsEventPushCand date] = {
    Pred catesForCand date.exceptedPred cate(
      na  = "h gh_pr or y_excepted_" + pred cate.na ,
      fn = Mag cFanoutPred catesUt l.c ck fH ghPr or yNewsEventForCand date,
      pred cate
    )(conf g.statsRece ver)
  }

  /**
   * Bypass pred cate  f h gh pr or y push
   */
  def h ghPr or yEventExceptedPred cate(
    pred cate: Na dPred cate[Mag cFanoutEventPushCand date]
  )(
     mpl c  conf g: Conf g
  ): Na dPred cate[Mag cFanoutEventPushCand date] = {
    Pred catesForCand date.exceptedPred cate(
      na  = "h gh_pr or y_excepted_" + pred cate.na ,
      fn = Mag cFanoutPred catesUt l.c ck fH ghPr or yEventForCand date,
      pred cate
    )(conf g.statsRece ver)
  }

  def mag cFanoutS mClusterTarget ngPred cate(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[Mag cFanoutEventPushCand date] = {
    val na  = "s mcluster_target ng"
    val scopedStats = stats.scope(s"pred cate_$na ")
    val userStateCounters = scopedStats.scope("user_state")
    Pred cate
      .fromAsync { cand date: Mag cFanoutEventPushCand date =>
        cand date.target. s avyUserState.map {  s avyUser =>
          val s mClusterEmbedd ngs = cand date.newsFor  tadata.flatMap(
            _.eventContextScr be.flatMap(_.s mClustersEmbedd ngs))
          val TopKS mClustersCount = 50
          val eventS mClusterVectorOpt: Opt on[Mag cFanoutPred catesUt l.S mClusterScores] =
            Mag cFanoutPred catesUt l.getEventS mClusterVector(
              s mClusterEmbedd ngs.map(_.toMap),
              (ModelVers on.Model20m145kUpdated, Embedd ngType.FollowBasedT et),
              TopKS mClustersCount
            )
          val userS mClusterVectorOpt: Opt on[Mag cFanoutPred catesUt l.S mClusterScores] =
            Mag cFanoutPred catesUt l.getUserS mClusterVector(cand date.effect veMag cEventsReasons)
          (eventS mClusterVectorOpt, userS mClusterVectorOpt) match {
            case (
                  So (eventS mClusterVector: Mag cFanoutPred catesUt l.S mClusterScores),
                  So (userS mClusterVector)) =>
              val score = eventS mClusterVector
                .nor dDotProduct(userS mClusterVector, eventS mClusterVector)
              val threshold =  f ( s avyUser) {
                cand date.target.params(
                  PushFeatureSw chParams.Mag cFanoutS mClusterDotProduct avyUserThreshold)
              } else {
                cand date.target.params(
                  PushFeatureSw chParams.Mag cFanoutS mClusterDotProductNon avyUserThreshold)
              }
              val  sPassed = score >= threshold
              userStateCounters.scope( s avyUser.toStr ng).counter(s"$ sPassed"). ncr()
               sPassed

            case (None, So (userS mClusterVector)) =>
              cand date.commonRecType == CommonRecom ndat onType.Mag cFanoutSportsEvent

            case _ => false
          }
        }
      }
      .w hStats(scopedStats)
      .w hNa (na )
  }

  def geoTarget ngHoldback(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h Mag cFanoutCand date] = {
    Pred cate
      .from[PushCand date w h Mag cFanoutCand date] { cand date =>
         f (Mag cFanoutPred catesUt l.reasonsConta nGeoTarget(
            cand date.cand dateMag cEventsReasons)) {
          cand date.target.params(PushFeatureSw chParams.EnableMfGeoTarget ng)
        } else true
      }
      .w hStats(stats.scope("geo_target ng_holdback"))
      .w hNa ("geo_target ng_holdback")
  }

  def geoOptOutPred cate(
    userStore: ReadableStore[Long, User]
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h Mag cFanoutCand date] = {
    Pred cate
      .fromAsync[PushCand date w h Mag cFanoutCand date] { cand date =>
         f (Mag cFanoutPred catesUt l.reasonsConta nGeoTarget(
            cand date.cand dateMag cEventsReasons)) {
          userStore.get(cand date.target.target d).map { userOpt =>
            val  sGeoAllo d = userOpt
              .flatMap(_.account)
              .ex sts(_.allowLocat on toryPersonal zat on)
             sGeoAllo d
          }
        } else {
          Future.True
        }
      }
      .w hStats(stats.scope("geo_opt_out_pred cate"))
      .w hNa ("geo_opt_out_pred cate")
  }

  /**
   * C ck  f Semant c Core reasons conta ns val d utt reason & reason  s w h n top k top cs follo d by user
   */
  def mag cFanoutTop cFollowsTarget ngPred cate(
     mpl c  stats: StatsRece ver,
     nterestsLookupStore: ReadableStore[ nterestsLookupRequestW hContext, User nterests]
  ): Na dPred cate[Mag cFanoutEventHydratedCand date] = {
    val na  = "mag cfanout_top c_follows_target ng"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync[PushCand date w h Mag cFanoutEventHydratedCand date] { cand date =>
        cand date.follo dTop cLocal zedEnt  es.map(_.nonEmpty)
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  /** Requ res t  mag cfanout cand date to have a User D reason wh ch ranks below t  follow
   * rank threshold.  f no User D target ex sts t  cand date  s dropped. */
  def followRankThreshold(
    threshold: Param[ nt]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h Mag cFanoutCand date] = {
    val na  = "follow_rank_threshold"
    Pred cate
      .from[PushCand date w h Mag cFanoutCand date] { c =>
        c.cand dateMag cEventsReasons.ex sts { fanoutReason =>
          fanoutReason.reason match {
            case Target D.User D(_) =>
              fanoutReason.rank.ex sts { rank =>
                rank <= c.target.params(threshold)
              }
            case _ => false
          }
        }
      }
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  def userGeneratedEventsPred cate(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h Mag cFanoutEventHydratedCand date] = {
    val na  = "user_generated_mo nts"
    val stats = statsRece ver.scope(na )

    Pred cate
      .from { cand date: PushCand date w h Mag cFanoutEventHydratedCand date =>
        val  sUgmMo nt = cand date.semant cCoreEnt yTags.values.flatten.toSet
          .conta ns(Mag cFanoutPred catesUt l.UgmMo ntTag)
         f ( sUgmMo nt) {
          cand date.target.params(PushFeatureSw chParams.Mag cFanoutNewsUserGeneratedEventsEnable)
        } else true
      }.w hStats(stats)
      .w hNa (na )
  }
  def esc rb rdMag cfanoutEventParam(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h Mag cFanoutEventPushCand date] = {
    val na  = "mag cfanout_esc rb rd_fs"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")

    Pred cate
      .fromAsync[PushCand date w h Mag cFanoutEventPushCand date] { cand date =>
        val cand dateFr gateNot f = cand date.fr gateNot f cat on.mag cFanoutEventNot f cat on
        val  sEsc rb rdEvent = cand dateFr gateNot f.ex sts(_. sEsc rb rdEvent.conta ns(true))
        scopedStatsRece ver.counter(s"w h_esc rb rd_flag_$ sEsc rb rdEvent"). ncr()

         f ( sEsc rb rdEvent) {

          val l stOfEventsSemant cCoreDoma n ds =
            cand date.target.params(PushFeatureSw chParams.L stOfEventSemant cCoreDoma n ds)

          val candScDoma nEvent =
             f (l stOfEventsSemant cCoreDoma n ds.nonEmpty) {
              cand date.eventSemant cCoreDoma n ds
                . ntersect(l stOfEventsSemant cCoreDoma n ds).nonEmpty
            } else {
              false
            }
          scopedStatsRece ver
            .counter(
              s"w h_esc rb rd_fs_ n_l st_of_event_semant c_core_doma ns_$candScDoma nEvent"). ncr()
          Future.value(candScDoma nEvent)
        } else {
          Future.True
        }
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  /**
   *  C cks  f t  user has custom target ng enabled. f so, bucket t  user  n exper  nt. T  custom target ng refers to add ng
   *  t et authors as targets  n t  eventfanout serv ce.
   * @param stats [StatsRece ver]
   * @return Na dPred cate[PushCand date w h Mag cFanoutEventPushCand date]
   */
  def hasCustomTarget ngForNewsEventsParam(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h Mag cFanoutEventPushCand date] = {
    val na  = "mag cfanout_hascustomtarget ng"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")

    Pred cate
      .from[PushCand date w h Mag cFanoutEventPushCand date] { cand date =>
        cand date.cand dateMag cEventsReasons.ex sts { fanoutReason =>
          fanoutReason.reason match {
            case user dReason: Target D.User D =>
               f (user dReason.user D.hasCustomTarget ng.conta ns(true)) {
                cand date.target.params(
                  PushFeatureSw chParams.Mag cFanoutEnableCustomTarget ngNewsEvent)
              } else true
            case _ => true
          }
        }
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )

  }

  def mag cFanoutProductLaunchFat gue(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h Mag cFanoutProductLaunchCand date] = {
    val na  = "mag c_fanout_product_launch_fat gue"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync { cand date: PushCand date w h Mag cFanoutProductLaunchCand date =>
        val target = cand date.target
        val ( nterval, max n nterval, m n nterval) = {
          cand date.productLaunchType match {
            case ProductType.BlueVer f ed =>
              (
                target.params(PushFeatureSw chParams.ProductLaunchPush nterval nH s),
                target.params(PushFeatureSw chParams.ProductLaunchMaxNumberOfPus s n nterval),
                target.params(PushFeatureSw chParams.ProductLaunchM n ntervalFat gue))
            case _ =>
              (Durat on.fromDays(1), 0, Durat on.Zero)
          }
        }
        bu ld(
           nterval =  nterval,
          max n nterval = max n nterval,
          m n nterval = m n nterval,
          f lter tory = productLaunchTypeRecTypesOnlyF lter(
            Set(CommonRecom ndat onType.Mag cFanoutProductLaunch),
            cand date.productLaunchType.toStr ng),
          not f cat onD splayLocat on = Not f cat onD splayLocat on.PushToMob leDev ce
        ).flatContraMap { cand date: PushCand date => cand date.target. tory }
          .apply(Seq(cand date))
          .map(_. adOpt on.getOrElse(false))
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  def creatorPushTarget sNotCreator(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h Mag cFanoutCreatorEventCand date] = {
    val na  = "mag c_fanout_creator_ s_self"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    Pred cate
      .from { cand date: PushCand date w h Mag cFanoutCreatorEventCand date =>
        cand date.target.target d != cand date.creator d
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  def dupl cateCreatorPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h Mag cFanoutCreatorEventCand date] = {
    val na  = "mag c_fanout_creator_dupl cate_creator_ d"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync { cand: PushCand date w h Mag cFanoutCreatorEventCand date =>
        cand.target.pushRec ems.map { rec ems: Rec ems =>
          !rec ems.creator ds.conta ns(cand.creator d)
        }
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  def  sSuperFollow ngCreator(
  )(
     mpl c  conf g: Conf g,
    stats: StatsRece ver
  ): Na dPred cate[PushCand date w h Mag cFanoutCreatorEventCand date] = {
    val na  = "mag c_fanout_ s_already_superfollow ng_creator"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync { cand: PushCand date w h Mag cFanoutCreatorEventCand date =>
        conf g.hasSuperFollow ngRelat onsh pStore
          .get(
            HasSuperFollow ngRelat onsh pRequest(
              s ceUser d = cand.target.target d,
              targetUser d = cand.creator d)).map(_.getOrElse(false))
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  def mag cFanoutCreatorPushFat guePred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h Mag cFanoutCreatorEventCand date] = {
    val na  = "mag c_fanout_creator_fat gue"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync { cand date: PushCand date w h Mag cFanoutCreatorEventCand date =>
        val target = cand date.target
        val ( nterval, max n nterval, m n nterval) = {
          cand date.creatorFanoutType match {
            case CreatorFanoutType.UserSubscr pt on =>
              (
                target.params(PushFeatureSw chParams.CreatorSubscr pt onPush nterval nH s),
                target.params(
                  PushFeatureSw chParams.CreatorSubscr pt onPushMaxNumberOfPus s n nterval),
                target.params(PushFeatureSw chParams.CreatorSubscr pt onPushhM n ntervalFat gue))
            case CreatorFanoutType.NewCreator =>
              (
                target.params(PushFeatureSw chParams.NewCreatorPush nterval nH s),
                target.params(PushFeatureSw chParams.NewCreatorPushMaxNumberOfPus s n nterval),
                target.params(PushFeatureSw chParams.NewCreatorPushM n ntervalFat gue))
            case _ =>
              (Durat on.fromDays(1), 0, Durat on.Zero)
          }
        }
        bu ld(
           nterval =  nterval,
          max n nterval = max n nterval,
          m n nterval = m n nterval,
          f lter tory = recOnlyF lter(cand date.commonRecType),
          not f cat onD splayLocat on = Not f cat onD splayLocat on.PushToMob leDev ce
        ).flatContraMap { cand date: PushCand date => cand date.target. tory }
          .apply(Seq(cand date))
          .map(_. adOpt on.getOrElse(false))
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }
}
