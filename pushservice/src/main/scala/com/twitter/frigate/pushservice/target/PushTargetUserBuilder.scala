package com.tw ter.fr gate.pushserv ce.target

 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.d scovery.common.conf gap .Conf gParamsBu lder
 mport com.tw ter.d scovery.common.conf gap .Exper  ntOverr de
 mport com.tw ter.featuresw c s.Rec p ent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common. tory._
 mport com.tw ter.fr gate.common.logger.MRLogger
 mport com.tw ter.fr gate.common.store.FeedbackRequest
 mport com.tw ter.fr gate.common.store.PushRec emsKey
 mport com.tw ter.fr gate.common.store.dev ce nfo.Dev ce nfo
 mport com.tw ter.fr gate.common.store. nterests.User d
 mport com.tw ter.fr gate.common.ut l._
 mport com.tw ter.fr gate.data_p pel ne.features_common.MrRequestContextForFeatureStore
 mport com.tw ter.fr gate.data_p pel ne.thr ftscala.User toryValue
 mport com.tw ter.fr gate.dau_model.thr ftscala.DauProbab l y
 mport com.tw ter.fr gate.pushcap.thr ftscala.Pushcap nfo
 mport com.tw ter.fr gate.pushcap.thr ftscala.PushcapUser tory
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.ml.Hydrat onContextBu lder
 mport com.tw ter.fr gate.pushserv ce.ml.PushMLModelScorer
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.store.LabeledPushRecsStoreKey
 mport com.tw ter.fr gate.pushserv ce.store.Onl neUser toryKey
 mport com.tw ter.fr gate.pushserv ce.ut l.Nsfw nfo
 mport com.tw ter.fr gate.pushserv ce.ut l.NsfwPersonal zat onUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.PushAppPerm ss onUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.PushCapUt l.getM n mumRestr ctedPushcap nfo
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushContext
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.RequestS ce
 mport com.tw ter.fr gate.thr ftscala.SecondaryAccountsByUserState
 mport com.tw ter.fr gate.thr ftscala.UserForPushTarget ng
 mport com.tw ter.fr gate.user_states.thr ftscala.MRUserHmmState
 mport com.tw ter.fr gate.user_states.thr ftscala.{UserState => MrUserState}
 mport com.tw ter.frontpage.stream.ut l.SnowflakeUt l
 mport com.tw ter.geoduck.common.thr ftscala.Place
 mport com.tw ter.geoduck.serv ce.thr ftscala.Locat onResponse
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter. rm .model.user_state.UserState
 mport com.tw ter. rm .model.user_state.UserState.UserState
 mport com.tw ter. rm .stp.thr ftscala.STPResult
 mport com.tw ter. b s.thr ftscala.ContentRecData
 mport com.tw ter. nterests.thr ftscala. nterest d
 mport com.tw ter.not f cat onserv ce.feedback.thr ftscala.Feedback nteract on
 mport com.tw ter.not f cat onserv ce.gener cfeedbackstore.FeedbackPromptValue
 mport com.tw ter.not f cat onserv ce.gener cfeedbackstore.Gener cFeedbackStore
 mport com.tw ter.not f cat onserv ce.gener cfeedbackstore.Gener cFeedbackStoreExcept on
 mport com.tw ter.not f cat onserv ce.model.serv ce.D sm ss nuFeedbackAct on
 mport com.tw ter.not f cat onserv ce.scr be.manhattan.Gener cNot f cat onsFeedbackRequest
 mport com.tw ter.not f cat onserv ce.thr ftscala.CaretFeedbackDeta ls
 mport com.tw ter.nrel. avyranker.FeatureHydrator
 mport com.tw ter.nrel.hydrat on.push.Hydrat onContext
 mport com.tw ter.perm ss ons_storage.thr ftscala.AppPerm ss on
 mport com.tw ter.rux.common.strato.thr ftscala.UserTarget ngProperty
 mport com.tw ter.sc o.nsfw_user_seg ntat on.thr ftscala.NSFWUserSeg ntat on
 mport com.tw ter.serv ce. tastore.gen.thr ftscala.Locat on
 mport com.tw ter.serv ce. tastore.gen.thr ftscala.UserLanguages
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.t  l nes.real_graph.thr ftscala.{RealGraphFeatures => RealGraphFeaturesUn on}
 mport com.tw ter.t  l nes.real_graph.v1.thr ftscala.RealGraphFeatures
 mport com.tw ter.ubs.thr ftscala.SellerAppl cat onState
 mport com.tw ter.ubs.thr ftscala.SellerTrack
 mport com.tw ter.user_sess on_store.thr ftscala.UserSess on
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport com.tw ter.wtf.scald ng.common.thr ftscala.UserFeatures

case class PushTargetUserBu lder(
   toryStore: PushServ ce toryStore,
  ema l toryStore: PushServ ce toryStore,
  labeledPushRecsStore: ReadableStore[LabeledPushRecsStoreKey, User toryValue],
  onl neUser toryStore: ReadableStore[Onl neUser toryKey, User toryValue],
  pushRec emsStore: ReadableStore[PushRec emsKey, Rec ems],
  userStore: ReadableStore[Long, User],
  push nfoStore: ReadableStore[Long, UserForPushTarget ng],
  userCountryStore: ReadableStore[Long, Locat on],
  userUtcOffsetStore: ReadableStore[Long, Durat on],
  dauProbab l yStore: ReadableStore[Long, DauProbab l y],
  nsfwConsu rStore: ReadableStore[Long, NSFWUserSeg ntat on],
  userFeatureStore: ReadableStore[Long, UserFeatures],
  userTarget ngPropertyStore: ReadableStore[Long, UserTarget ngProperty],
  mrUserStateStore: ReadableStore[Long, MRUserHmmState],
  t et mpress onStore: ReadableStore[Long, Seq[Long]],
  ntabCaretFeedbackStore: ReadableStore[Gener cNot f cat onsFeedbackRequest, Seq[
    CaretFeedbackDeta ls
  ]],
  gener cFeedbackStore: ReadableStore[FeedbackRequest, Seq[FeedbackPromptValue]],
  gener cNot f cat onFeedbackStore: Gener cFeedbackStore,
  t  l nesUserSess onStore: ReadableStore[Long, UserSess on],
  cac dT etyP eStore: ReadableStore[Long, T etyP eResult],
  strongT esStore: ReadableStore[Long, STPResult],
  userHTLLastV s Store: ReadableStore[Long, Seq[Long]],
  userLanguagesStore: ReadableStore[Long, UserLanguages],
   nputDec der: Dec der,
   nputAbDec der: Logg ngABDec der,
  realGraphScoresTop500 nStore: ReadableStore[Long, Map[Long, Double]],
  recentFollowsStore: ReadableStore[Long, Seq[Long]],
  resurrectedUserStore: ReadableStore[Long, Str ng],
  conf gParamsBu lder: Conf gParamsBu lder,
  optOutUser nterestsStore: ReadableStore[User d, Seq[ nterest d]],
  dev ce nfoStore: ReadableStore[Long, Dev ce nfo],
  pushcapDynam cPred ct onStore: ReadableStore[Long, PushcapUser tory],
  appPerm ss onStore: ReadableStore[(Long, (Str ng, Str ng)), AppPerm ss on],
  optoutModelScorer: PushMLModelScorer,
   nl neAct on toryStore: ReadableStore[Long, Seq[(Long, Str ng)]],
  featureHydrator: FeatureHydrator,
  openAppUserStore: ReadableStore[Long, Boolean],
  openedPushByH AggregatedStore: ReadableStore[Long, Map[ nt,  nt]],
  geoduckStoreV2: ReadableStore[Long, Locat onResponse],
  superFollowEl g b l yUserStore: ReadableStore[Long, Boolean],
  superFollowAppl cat onStatusStore: ReadableStore[(Long, SellerTrack), SellerAppl cat onState]
)(
  globalStatsRece ver: StatsRece ver) {

   mpl c  val statsRece ver: StatsRece ver = globalStatsRece ver

  pr vate val log = MRLogger("PushTargetUserBu lder")
  pr vate val recentFollowscounter = statsRece ver.counter("query_recent_follows")
  pr vate val  sModelTra n ngDataCounter =
    statsRece ver.scope("TargetUserBu lder").counter(" s_model_tra n ng")
  pr vate val feedbackStoreGenerat onErr = statsRece ver.counter("feedback_store_generat on_error")
  pr vate val newS gnUpUserStats = statsRece ver.counter("new_s gnup_user")
  pr vate val pushcapSelect onStat = statsRece ver.scope("pushcap_model ng")
  pr vate val dormantUserCount = statsRece ver.counter("dormant_user_counter")
  pr vate val optoutModelStat = statsRece ver.scope("optout_model ng")
  pr vate val placeFoundStat = statsRece ver.scope("geoduck_v2").stat("places_found")
  pr vate val placesNotFound = statsRece ver.scope("geoduck_v2").counter("places_not_found")
  // Ema l  tory store stats
  pr vate val ema l toryStats = statsRece ver.scope("ema l_t et_ tory")
  pr vate val emptyEma l toryCounter = ema l toryStats.counter("empty")
  pr vate val nonEmptyEma l toryCounter = ema l toryStats.counter("non_empty")

  pr vate val Mag cRecsCategory = "Mag cRecs"
  pr vate val Mo ntsV aMag cRecsCategory = "Mo ntsV aMag cRecs"
  pr vate val Mo ntsCategory = "Mo nts"

  def bu ldTarget(
    user d: Long,
     nputPushContext: Opt on[PushContext],
    forcedFeatureValues: Opt on[Map[Str ng, conf gap .FeatureValue]] = None
  ): Future[Target] = {
    val  toryStoreKeyContext =  toryStoreKeyContext(
      user d,
       nputPushContext.flatMap(_.use mcac For tory).getOrElse(false)
    )
    Future
      .jo n(
        userStore.get(user d),
        dev ce nfoStore.get(user d),
        push nfoStore.get(user d),
         toryStore.get( toryStoreKeyContext, So (30.days)),
        ema l toryStore.get(
           toryStoreKeyContext(user d, useStoreB = false),
          So (7.days) //   only keep 7 days of ema l t et  tory
        )
      ).flatMap {
        case (userOpt, dev ce nfoOpt, userForPushTarget ng nfoOpt, not f tory, ema l tory) =>
          getCustomFSF elds(
            user d,
            userOpt,
            dev ce nfoOpt,
            userForPushTarget ng nfoOpt,
            not f tory,
             nputPushContext.flatMap(_.requestS ce)).map { customFSF eld =>
            new Target {

              overr de lazy val stats: StatsRece ver = statsRece ver

              overr de val target d: Long = user d

              overr de val targetUser: Future[Opt on[User]] = Future.value(userOpt)

              overr de val  sEma lUser: Boolean =
                 nputPushContext.flatMap(_.requestS ce) match {
                  case So (s ce)  f s ce == RequestS ce.Ema l => true
                  case _ => false
                }

              overr de val pushContext =  nputPushContext

              overr de def globalStats: StatsRece ver = globalStatsRece ver

              overr de lazy val abDec der: ABDec derW hOverr de =
                ABDec derW hOverr de( nputAbDec der, ddgOverr deOpt on)

              overr de lazy val pushRec ems: Future[Rec ems] =
                pushRec emsStore
                  .get(PushRec emsKey( toryStoreKeyContext,  tory))
                  .map(_.getOrElse(Rec ems.empty))

              // L st of past t et cand dates sent  n t  past through ema l w h t  stamp
              overr de lazy val ema lRec ems: Future[Seq[(T  , Long)]] = {
                Future.value {
                  ema l tory.sortedEma l tory.flatMap {
                    case (t  Stamp, not f cat on) =>
                      not f cat on.contentRecsNot f cat on
                        .map { not f cat on =>
                          not f cat on.recom ndat ons.contentRecCollect ons.flatMap {
                            contentRecs =>
                              contentRecs.contentRecModules.flatMap { contentRecModule =>
                                contentRecModule.recData match {
                                  case ContentRecData.T etRec(t etRec) =>
                                    nonEmptyEma l toryCounter. ncr()
                                    Seq(t etRec.t et d)
                                  case _ =>
                                    emptyEma l toryCounter. ncr()
                                    N l
                                }
                              }
                          }
                        }.getOrElse {
                          emptyEma l toryCounter. ncr()
                          N l
                        }.map(t  Stamp -> _)
                  }
                }
              }

              overr de lazy val  tory: Future[ tory] = Future.value(not f tory)

              overr de lazy val pushTarget ng: Future[Opt on[UserForPushTarget ng]] =
                Future.value(userForPushTarget ng nfoOpt)

              overr de lazy val dec der: Dec der =  nputDec der

              overr de lazy val locat on: Future[Opt on[Locat on]] =
                userCountryStore.get(user d)

              overr de lazy val dev ce nfo: Future[Opt on[Dev ce nfo]] =
                Future.value(dev ce nfoOpt)

              overr de lazy val targetLanguage: Future[Opt on[Str ng]] = targetUser map { userOpt =>
                userOpt.flatMap(_.account.map(_.language))
              }

              overr de lazy val targetAge nYears: Future[Opt on[ nt]] =
                Future.value(customFSF eld.userAge)

              overr de lazy val  tastoreLanguages: Future[Opt on[UserLanguages]] =
                userLanguagesStore.get(target d)

              overr de lazy val utcOffset: Future[Opt on[Durat on]] =
                userUtcOffsetStore.get(target d)

              overr de lazy val userFeatures: Future[Opt on[UserFeatures]] =
                userFeatureStore.get(target d)

              overr de lazy val targetUserState: Future[Opt on[UserState]] =
                Future.value(
                  customFSF eld.userState
                    .flatMap(userState => UserState.valueOf(userState)))

              overr de lazy val targetMrUserState: Future[Opt on[MrUserState]] =
                Future.value(
                  customFSF eld.mrUserState
                    .flatMap(mrUserState => MrUserState.valueOf(mrUserState)))

              overr de lazy val accountStateW hDev ce nfo: Future[
                Opt on[SecondaryAccountsByUserState]
              ] = Future.None

              overr de lazy val dauProbab l y: Future[Opt on[DauProbab l y]] = {
                dauProbab l yStore.get(target d)
              }

              overr de lazy val labeledPushRecsHydrated: Future[Opt on[User toryValue]] =
                labeledPushRecsStore.get(LabeledPushRecsStoreKey(t ,  toryStoreKeyContext))

              overr de lazy val onl neLabeledPushRecs: Future[Opt on[User toryValue]] =
                labeledPushRecsHydrated.flatMap { labeledPushRecs =>
                   tory.flatMap {  tory =>
                    onl neUser toryStore.get(
                      Onl neUser toryKey(target d, labeledPushRecs, So ( tory))
                    )
                  }
                }

              overr de lazy val t et mpress onResults: Future[Seq[Long]] =
                t et mpress onStore.get(target d).map {
                  case So ( mpress onL st) =>
                     mpress onL st
                  case _ => N l
                }

              overr de lazy val realGraphFeatures: Future[Opt on[RealGraphFeatures]] =
                t  l nesUserSess onStore.get(target d).map { userSess onOpt =>
                  userSess onOpt.flatMap { userSess on =>
                    userSess on.realGraphFeatures.collect {
                      case RealGraphFeaturesUn on.V1(rGFeatures) =>
                        rGFeatures
                    }
                  }
                }

              overr de lazy val stpResult: Future[Opt on[STPResult]] =
                strongT esStore.get(target d)

              overr de lazy val lastHTLV s T  stamp: Future[Opt on[Long]] =
                userHTLLastV s Store.get(target d).map {
                  case So (lastV s T  stamps)  f lastV s T  stamps.nonEmpty =>
                    So (lastV s T  stamps.max)
                  case _ => None
                }

              overr de lazy val caretFeedbacks: Future[Opt on[Seq[CaretFeedbackDeta ls]]] = {
                val scr be toryLookbackPer od = 365.days
                val now = T  .now
                val request = Gener cNot f cat onsFeedbackRequest(
                  user d = target d,
                  eventStartT  stamp = now - scr be toryLookbackPer od,
                  eventEndT  stamp = now,
                  f lterCategory =
                    So (Set(Mag cRecsCategory, Mo ntsV aMag cRecsCategory, Mo ntsCategory)),
                  f lterFeedbackAct onText =
                    So (Set(D sm ss nuFeedbackAct on.FeedbackAct onTextSeeLessOften))
                )
                ntabCaretFeedbackStore.get(request)
              }

              overr de lazy val not f cat onFeedbacks: Future[
                Opt on[Seq[FeedbackPromptValue]]
              ] = {
                val scr be toryLookbackPer od = 30.days
                val now = T  .now
                val request = FeedbackRequest(
                  user d = target d,
                  oldestT  stamp = scr be toryLookbackPer od.ago,
                  ne stT  stamp = T  .now,
                  feedback nteract on = Feedback nteract on.Feedback
                )
                gener cFeedbackStore.get(request)
              }

              // DEPRECATED: Use not f cat onFeedbacks  nstead.
              // T   thod w ll  ncrease latency dramat cally.
              overr de lazy val promptFeedbacks: St ch[Seq[FeedbackPromptValue]] = {
                val scr be toryLookbackPer od = 7.days

                gener cNot f cat onFeedbackStore
                  .getAll(
                    user d = target d,
                    oldestT  stamp = scr be toryLookbackPer od.ago,
                    ne stT  stamp = T  .now,
                    feedback nteract on = Feedback nteract on.Feedback
                  ).handle {
                    case _: Gener cFeedbackStoreExcept on => {
                      feedbackStoreGenerat onErr. ncr()
                      Seq.empty[FeedbackPromptValue]
                    }
                  }
              }

              overr de lazy val optOutUser nterests: Future[Opt on[Seq[ nterest d]]] = {
                optOutUser nterestsStore.get(target d)
              }

              pr vate val exper  ntOverr de = ddgOverr deOpt on.map {
                case DDGOverr de(So (exp), So (bucket)) =>
                  Set(Exper  ntOverr de(exp, bucket))
                case _ => Set.empty[Exper  ntOverr de]
              }

              overr de val s gnupCountryCode =
                Future.value(userOpt.flatMap(_.safety.flatMap(_.s gnupCountryCode)))

              overr de lazy val params: conf gap .Params = {
                val fsRec p ent = Rec p ent(
                  user d = So (target d),
                  userRoles = userOpt.flatMap(_.roles.map(_.roles.toSet)),
                  cl entAppl cat on d = dev ce nfoOpt.flatMap(_.guessedPr maryCl entApp d),
                  userAgent = dev ce nfoOpt.flatMap(_.guessedPr maryDev ceUserAgent),
                  countryCode =
                    userOpt.flatMap(_.account.flatMap(_.countryCode.map(_.toUpperCase))),
                  customF elds = So (customFSF eld.fsMap),
                  s gnupCountryCode =
                    userOpt.flatMap(_.safety.flatMap(_.s gnupCountryCode.map(_.toUpperCase))),
                  languageCode = dev ce nfoOpt.flatMap {
                    _.dev ceLanguages.flatMap( b sAppPushDev ceSett ngsUt l. nferredDev ceLanguage)
                  }
                )

                conf gParamsBu lder.bu ld(
                  user d = So (target d),
                  exper  ntOverr des = exper  ntOverr de,
                  featureRec p ent = So (fsRec p ent),
                  forcedFeatureValues = forcedFeatureValues.getOrElse(Map.empty),
                )
              }

              overr de lazy val mrRequestContextForFeatureStore =
                MrRequestContextForFeatureStore(target d, params,  sModelTra n ngData)

              overr de lazy val dynam cPushcap: Future[Opt on[Pushcap nfo]] = {
                // Get t  pushcap from t  pushcap model pred ct on store
                 f (params(PushParams.EnableModelBasedPushcapAss gn nts)) {
                  val or g nalPushcap nfoFut =
                    PushCapUt l.getPushcapFromUser tory(
                      user d,
                      pushcapDynam cPred ct onStore,
                      params(FeatureSw chParams.PushcapModelType),
                      params(FeatureSw chParams.PushcapModelPred ct onVers on),
                      pushcapSelect onStat
                    )
                  // Mod fy t  push cap  nfo  f t re  s a restr cted m n value for pred cted push caps.
                  val restr ctedPushcap = params(PushFeatureSw chParams.Restr ctedM nModelPushcap)
                  or g nalPushcap nfoFut.map {
                    case So (or g nalPushcap nfo) =>
                      So (
                        getM n mumRestr ctedPushcap nfo(
                          restr ctedPushcap,
                          or g nalPushcap nfo,
                          pushcapSelect onStat))
                    case _ => None
                  }
                } else Future.value(None)
              }

              overr de lazy val targetHydrat onContext: Future[Hydrat onContext] =
                Hydrat onContextBu lder.bu ld(t )

              overr de lazy val featureMap: Future[FeatureMap] =
                targetHydrat onContext.flatMap { hydrat onContext =>
                  featureHydrator.hydrateTarget(
                    hydrat onContext,
                    t .params,
                    t .mrRequestContextForFeatureStore)
                }

              overr de lazy val globalOptoutProbab l  es: Seq[Future[Opt on[Double]]] = {
                params(PushFeatureSw chParams.GlobalOptoutModelParam).map { model_ d =>
                  optoutModelScorer
                    .s nglePred ct onForTargetLevel(model_ d, target d, featureMap)
                }
              }

              overr de lazy val bucketOptoutProbab l y: Future[Opt on[Double]] = {
                Future
                  .collect(globalOptoutProbab l  es).map {
                    _.z p(params(PushFeatureSw chParams.GlobalOptoutThresholdParam))
                      .ex sts {
                        case (So (score), threshold) => score >= threshold
                        case _ => false
                      }
                  }.flatMap {
                    case true =>
                      optoutModelScorer.s nglePred ct onForTargetLevel(
                        params(PushFeatureSw chParams.BucketOptoutModelParam),
                        target d,
                        featureMap)
                    case _ => Future.None
                  }
              }

              overr de lazy val optoutAdjustedPushcap: Future[Opt on[Short]] = {
                 f (params(PushFeatureSw chParams.EnableOptoutAdjustedPushcap)) {
                  bucketOptoutProbab l y.map {
                    case So (score) =>
                      val  dx = params(PushFeatureSw chParams.BucketOptoutSlotThresholdParam)
                        . ndexW re(score <= _)
                       f ( dx >= 0) {
                        val pushcap =
                          params(PushFeatureSw chParams.BucketOptoutSlotPushcapParam)( dx).toShort
                        optoutModelStat.scope("adjusted_pushcap").counter(f"$pushcap"). ncr()
                         f (pushcap >= 0) So (pushcap)
                        else None
                      } else None
                    case _ => None
                  }
                } else Future.None
              }

              overr de lazy val seedsW h  ght: Future[Opt on[Map[Long, Double]]] = {
                Future
                  .jo n(
                    realGraphScoresTop500 nStore.get(user d),
                    targetUserState,
                    targetUser
                  )
                  .flatMap {
                    case (seedSetOpt, userState, g zmoduckUser) =>
                      val seedSet = seedSetOpt.getOrElse(Map.empty[Long, Double])

                      // f new s gn_up or New user, comb ne recent_follows w h real graph seedset
                      val  sNewUserEnabled = {
                        val  sNe rThan7days = customFSF eld.daysS nceS gnup <= 7
                        val  sNewUserState = userState.conta ns(UserState.New)
                         sNewUserState ||  sNewS gnup ||  sNe rThan7days
                      }

                      val nonSeedSetFollowsFut = g zmoduckUser match {
                        case So (user)  f  sNewUserEnabled =>
                          recentFollowscounter. ncr()
                          recentFollowsStore.get(user. d)

                        case So (user)  f t . sModelTra n ngData =>
                          recentFollowscounter. ncr()
                           sModelTra n ngDataCounter. ncr()
                          recentFollowsStore.get(user. d)

                        case _ => Future.None
                      }
                      nonSeedSetFollowsFut.map { nonSeedSetFollows =>
                        So (
                          SeedsetUt l.comb neRecentFollowsW h  ghtedSeedset(
                            seedSet,
                            nonSeedSetFollows.getOrElse(N l)
                          )
                        )
                      }
                  }
              }

              overr de def mag cFanoutReason tory30Days: Future[Mag cFanoutReason tory] =
                 tory.map( tory => Mag cFanoutReason tory( tory))

              overr de val  sNewS gnup: Boolean =
                pushContext.flatMap(_. sFromNewUserLoopProcessor).getOrElse(false)

              overr de lazy val resurrect onDate: Future[Opt on[Str ng]] =
                Future.value(customFSF eld.react vat onDate)

              overr de lazy val  sResurrectedUser: Boolean =
                customFSF eld.daysS nceReact vat on. sDef ned

              overr de lazy val t  S nceResurrect on: Opt on[Durat on] =
                customFSF eld.daysS nceReact vat on.map(Durat on.fromDays)

              overr de lazy val appPerm ss ons: Future[Opt on[AppPerm ss on]] =
                PushAppPerm ss onUt l.getAppPerm ss on(
                  user d,
                  PushAppPerm ss onUt l.AddressBookPerm ss onKey,
                  dev ce nfo,
                  appPerm ss onStore)

              overr de lazy val  nl neAct on tory: Future[Seq[(Long, Str ng)]] = {
                 nl neAct on toryStore
                  .get(user d).map {
                    case So (sorted nl neAct on tory) => sorted nl neAct on tory
                    case _ => Seq.empty
                  }
              }

              lazy val  sOpenAppExper  ntUser: Future[Boolean] =
                openAppUserStore.get(user d).map(_.conta ns(true))

              overr de lazy val openedPushByH Aggregated: Future[Opt on[Map[ nt,  nt]]] =
                openedPushByH AggregatedStore.get(user d)

              overr de lazy val places: Future[Seq[Place]] = {
                geoduckStoreV2
                  .get(target d)
                  .map(_.flatMap(_.places))
                  .map {
                    case So (placeSeq)  f placeSeq.nonEmpty =>
                      placeFoundStat.add(placeSeq.s ze)
                      placeSeq
                    case _ =>
                      placesNotFound. ncr()
                      Seq.empty
                  }
              }

              overr de val  sBlueVer f ed: Future[Opt on[Boolean]] =
                Future.value(userOpt.flatMap(_.safety.flatMap(_. sBlueVer f ed)))

              overr de val  sVer f ed: Future[Opt on[Boolean]] =
                Future.value(userOpt.flatMap(_.safety.map(_.ver f ed)))

              overr de lazy val  sSuperFollowCreator: Future[Opt on[Boolean]] =
                superFollowEl g b l yUserStore.get(target d)
            }
          }
      }
  }

  /**
   * Prov de general way to add needed FS for target user, and package t m  n CustomFSF elds.
   * Custom F elds  s a po rful feature that allows Feature Sw ch l brary users to def ne and
   * match aga nst any arb rary f elds.
   **/
  pr vate def getCustomFSF elds(
    user d: Long,
    userOpt: Opt on[User],
    dev ce nfo: Opt on[Dev ce nfo],
    userForPushTarget ng nfo: Opt on[UserForPushTarget ng],
    not f tory:  tory,
    requestS ce: Opt on[RequestS ce]
  ): Future[CustomFSF elds] = {
    val react vat onDateFutOpt: Future[Opt on[Str ng]] = resurrectedUserStore.get(user d)
    val react vat onT  FutOpt: Future[Opt on[T  ]] =
      react vat onDateFutOpt.map(_.map(dateStr => DateUt l.dateStrToT  (dateStr)))

    val  sReact vatedUserFut: Future[Boolean] = react vat onT  FutOpt.map { t  Opt =>
      t  Opt
        .ex sts { t   => T  .now - t   < 30.days }
    }

    val daysS nceReact vat onFut: Future[Opt on[ nt]] =
      react vat onT  FutOpt.map(_.map(t   => T  .now.s nce(t  ). nDays))

    val daysS nceS gnup:  nt = (T  .now - SnowflakeUt l.t  From d(user d)). nDays
     f (daysS nceS gnup < 14) newS gnUpUserStats. ncr()

    val targetAge nYears = userOpt.flatMap(_.extendedProf le.flatMap(_.age nYears))

    val lastLog nFut: Future[Opt on[Long]] =
      userHTLLastV s Store.get(user d).map {
        case So (lastHTLV s T  s) =>
          val latestHTLV s T   = lastHTLV s T  s.max
          userForPushTarget ng nfo.flatMap(
            _.lastAct veOnAppT  stamp
              .map(_.max(latestHTLV s T  )).orElse(So (latestHTLV s T  )))
        case None =>
          userForPushTarget ng nfo.flatMap(_.lastAct veOnAppT  stamp)
      }

    val daysS nceLog nFut = lastLog nFut.map {
      _.map { lastLog nT  stamp =>
        val t  S nceLog n = T  .now - T  .fromM ll seconds(lastLog nT  stamp)
         f (t  S nceLog n. nDays > 21) {
          dormantUserCount. ncr()
        }
        t  S nceLog n. nDays
      }
    }

    /* Could add more custom FS  re */
    val userNSFW nfoFut: Future[Opt on[Nsfw nfo]] =
      nsfwConsu rStore
        .get(user d).map(_.map(nsfwUserSeg ntat on => Nsfw nfo(nsfwUserSeg ntat on)))

    val userStateFut: Future[Opt on[Str ng]] = userFeatureStore.get(user d).map { userFeaturesOpt =>
      userFeaturesOpt.flatMap { uFeats =>
        uFeats.userState.map(uState => uState.na )
      }
    }

    val mrUserStateFut: Future[Opt on[Str ng]] =
      mrUserStateStore.get(user d).map { mrUserStateOpt =>
        mrUserStateOpt.flatMap { mrUserState =>
          mrUserState.userState.map(_.na )
        }
      }

    Future
      .jo n(
        react vat onDateFutOpt,
         sReact vatedUserFut,
        userStateFut,
        mrUserStateFut,
        daysS nceLog nFut,
        daysS nceReact vat onFut,
        userNSFW nfoFut
      ).map {
        case (
              react vat onDate,
               sReact vatedUser,
              userState,
              mrUserState,
              daysS nceLog n,
              daysS nceReact vat on,
              userNSFW nfo) =>
          val numDaysRece vedPush nLast30Days:  nt =
            not f tory. tory.keys.map(_. nDays).toSet.s ze

          NsfwPersonal zat onUt l.computeNsfwUserStats(userNSFW nfo)

          CustomFSF elds(
             sReact vatedUser,
            daysS nceS gnup,
            numDaysRece vedPush nLast30Days,
            daysS nceLog n,
            daysS nceReact vat on,
            userOpt,
            userState,
            mrUserState,
            react vat onDate,
            requestS ce.map(_.na ),
            targetAge nYears,
            userNSFW nfo,
            dev ce nfo
          )
      }
  }
}
