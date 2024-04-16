package com.tw ter.tsp.handlers

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.s mclusters_v2.common.Semant cCoreEnt y d
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.strato.response.Err
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.top c_recos.common.Conf gs.Consu rTop cEmbedd ngType
 mport com.tw ter.top c_recos.common.Conf gs.DefaultModelVers on
 mport com.tw ter.top c_recos.common.Conf gs.ProducerTop cEmbedd ngType
 mport com.tw ter.top c_recos.common.Conf gs.T etEmbedd ngType
 mport com.tw ter.top cl st ng.Top cL st ngV e rContext
 mport com.tw ter.top c_recos.common.LocaleUt l
 mport com.tw ter.top cl st ng.Annotat onRuleProv der
 mport com.tw ter.tsp.common.Dec derConstants
 mport com.tw ter.tsp.common.LoadS dder
 mport com.tw ter.tsp.common.RecTargetFactory
 mport com.tw ter.tsp.common.Top cSoc alProofDec der
 mport com.tw ter.tsp.common.Top cSoc alProofParams
 mport com.tw ter.tsp.stores.Top cSoc alProofStore
 mport com.tw ter.tsp.stores.Top cSoc alProofStore.Top cSoc alProof
 mport com.tw ter.tsp.stores.UttTop cF lterStore
 mport com.tw ter.tsp.stores.Top cT etsCos neS m lar yAggregateStore.ScoreKey
 mport com.tw ter.tsp.thr ftscala. tr cTag
 mport com.tw ter.tsp.thr ftscala.Top cFollowType
 mport com.tw ter.tsp.thr ftscala.Top cL st ngSett ng
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofRequest
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofResponse
 mport com.tw ter.tsp.thr ftscala.Top cW hScore
 mport com.tw ter.tsp.thr ftscala.TspT et nfo
 mport com.tw ter.tsp.ut ls. althS gnalsUt ls
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  r
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  outExcept on

 mport scala.ut l.Random

class Top cSoc alProofHandler(
  top cSoc alProofStore: ReadableStore[Top cSoc alProofStore.Query, Seq[Top cSoc alProof]],
  t et nfoStore: ReadableStore[T et d, TspT et nfo],
  uttTop cF lterStore: UttTop cF lterStore,
  recTargetFactory: RecTargetFactory,
  dec der: Top cSoc alProofDec der,
  statsRece ver: StatsRece ver,
  loadS dder: LoadS dder,
  t  r: T  r) {

   mport Top cSoc alProofHandler._

  def getTop cSoc alProofResponse(
    request: Top cSoc alProofRequest
  ): Future[Top cSoc alProofResponse] = {
    val scopedStats = statsRece ver.scope(request.d splayLocat on.toStr ng)
    scopedStats.counter("fanoutRequests"). ncr(request.t et ds.s ze)
    scopedStats.stat("numT etsPerRequest").add(request.t et ds.s ze)
    StatsUt l.trackBlockStats(scopedStats) {
      recTargetFactory
        .bu ldRecTop cSoc alProofTarget(request).flatMap { target =>
          val enableCos neS m lar yScoreCalculat on =
            dec der. sAva lable(Dec derConstants.enableTop cSoc alProofScore)

          val semant cCoreVers on d =
            target.params(Top cSoc alProofParams.Top cT etsSemant cCoreVers on d)

          val semant cCoreVers on dsSet =
            target.params(Top cSoc alProofParams.Top cT etsSemant cCoreVers on dsSet)

          val allowL stW hTop cFollowTypeFut = uttTop cF lterStore
            .getAllowL stTop csForUser(
              request.user d,
              request.top cL st ngSett ng,
              Top cL st ngV e rContext
                .fromThr ft(request.context).copy(languageCode =
                  LocaleUt l.getStandardLanguageCode(request.context.languageCode)),
              request.bypassModes.map(_.toSet)
            ).rescue {
              case _ =>
                scopedStats.counter("uttTop cF lterStoreFa lure"). ncr()
                Future.value(Map.empty[Semant cCoreEnt y d, Opt on[Top cFollowType]])
            }

          val t et nfoMapFut: Future[Map[T et d, Opt on[TspT et nfo]]] = Future
            .collect(
              t et nfoStore.mult Get(request.t et ds.toSet)
            ).ra seW h n(T et nfoStoreT  out)(t  r).rescue {
              case _: T  outExcept on =>
                scopedStats.counter("t et nfoStoreT  out"). ncr()
                Future.value(Map.empty[T et d, Opt on[TspT et nfo]])
              case _ =>
                scopedStats.counter("t et nfoStoreFa lure"). ncr()
                Future.value(Map.empty[T et d, Opt on[TspT et nfo]])
            }

          val def nedT et nfoMapFut =
            keepT etsW hT et nfoAndLanguage(t et nfoMapFut, request.d splayLocat on.toStr ng)

          Future
            .jo n(def nedT et nfoMapFut, allowL stW hTop cFollowTypeFut).map {
              case (t et nfoMap, allowL stW hTop cFollowType) =>
                val t et dsToQuery = t et nfoMap.keys.toSet
                val top cProofQuer es =
                  t et dsToQuery.map { t et d =>
                    Top cSoc alProofStore.Query(
                      Top cSoc alProofStore.Cac ableQuery(
                        t et d = t et d,
                        t etLanguage = LocaleUt l.getSupportedStandardLanguageCodeW hDefault(
                          t et nfoMap.getOrElse(t et d, None).flatMap {
                            _.language
                          }),
                        enableCos neS m lar yScoreCalculat on =
                          enableCos neS m lar yScoreCalculat on
                      ),
                      allo dSemant cCoreVers on ds = semant cCoreVers on dsSet
                    )
                  }

                val top cSoc alProofsFut: Future[Map[T et d, Seq[Top cSoc alProof]]] = {
                  Future
                    .collect(top cSoc alProofStore.mult Get(top cProofQuer es)).map(_.map {
                      case (query, results) =>
                        query.cac ableQuery.t et d -> results.toSeq.flatten.f lter(
                          _.semant cCoreVers on d == semant cCoreVers on d)
                    })
                }.ra seW h n(Top cSoc alProofStoreT  out)(t  r).rescue {
                  case _: T  outExcept on =>
                    scopedStats.counter("top cSoc alProofStoreT  out"). ncr()
                    Future(Map.empty[T et d, Seq[Top cSoc alProof]])
                  case _ =>
                    scopedStats.counter("top cSoc alProofStoreFa lure"). ncr()
                    Future(Map.empty[T et d, Seq[Top cSoc alProof]])
                }

                val random = new Random(seed = request.user d.to nt)

                top cSoc alProofsFut.map { top cSoc alProofs =>
                  val f lteredTop cSoc alProofs = f lterByAllo dL st(
                    top cSoc alProofs,
                    request.top cL st ngSett ng,
                    allowL stW hTop cFollowType.keySet
                  )

                  val f lteredTop cSoc alProofsEmptyCount:  nt =
                    f lteredTop cSoc alProofs.count {
                      case (_, top cSoc alProofs: Seq[Top cSoc alProof]) =>
                        top cSoc alProofs. sEmpty
                    }

                  scopedStats
                    .counter("f lteredTop cSoc alProofsCount"). ncr(f lteredTop cSoc alProofs.s ze)
                  scopedStats
                    .counter("f lteredTop cSoc alProofsEmptyCount"). ncr(
                      f lteredTop cSoc alProofsEmptyCount)

                   f ( sCrTop cT ets(request)) {
                    val soc alProofs = f lteredTop cSoc alProofs.mapValues(_.flatMap { top cProof =>
                      val top cW hScores = bu ldTop cW hRandomScore(
                        top cProof,
                        allowL stW hTop cFollowType,
                        random
                      )
                      top cW hScores
                    })
                    Top cSoc alProofResponse(soc alProofs)
                  } else {
                    val soc alProofs = f lteredTop cSoc alProofs.mapValues(_.flatMap { top cProof =>
                      getTop cProofScore(
                        top cProof = top cProof,
                        allowL stW hTop cFollowType = allowL stW hTop cFollowType,
                        params = target.params,
                        random = random,
                        statsRece ver = statsRece ver
                      )

                    }.sortBy(-_.score).take(MaxCand dates))

                    val personal zedContextSoc alProofs =
                       f (target.params(Top cSoc alProofParams.EnablePersonal zedContextTop cs)) {
                        val personal zedContextEl g b l y =
                          c ckPersonal zedContextsEl g b l y(
                            target.params,
                            allowL stW hTop cFollowType)
                        val f lteredT ets =
                          f lterPersonal zedContexts(soc alProofs, t et nfoMap, target.params)
                        backf llPersonal zedContexts(
                          allowL stW hTop cFollowType,
                          f lteredT ets,
                          request.tags.getOrElse(Map.empty),
                          personal zedContextEl g b l y)
                      } else {
                        Map.empty[T et d, Seq[Top cW hScore]]
                      }

                    val  rgedSoc alProofs = soc alProofs.map {
                      case (t et d, proofs) =>
                        (
                          t et d,
                          proofs
                            ++ personal zedContextSoc alProofs.getOrElse(t et d, Seq.empty))
                    }

                    // Note that   w ll NOT f lter out t ets w h no TSP  n e  r case
                    Top cSoc alProofResponse( rgedSoc alProofs)
                  }
                }
            }
        }.flatten.ra seW h n(T  out)(t  r).rescue {
          case _: Cl entD scardedRequestExcept on =>
            scopedStats.counter("Cl entD scardedRequestExcept on"). ncr()
            Future.value(DefaultResponse)
          case err: Err  f err.code == Err.Cancelled =>
            scopedStats.counter("CancelledErr"). ncr()
            Future.value(DefaultResponse)
          case _ =>
            scopedStats.counter("Fa ledRequests"). ncr()
            Future.value(DefaultResponse)
        }
    }
  }

  /**
   * Fetch t  Score for each Top c Soc al Proof
   */
  pr vate def getTop cProofScore(
    top cProof: Top cSoc alProof,
    allowL stW hTop cFollowType: Map[Semant cCoreEnt y d, Opt on[Top cFollowType]],
    params: Params,
    random: Random,
    statsRece ver: StatsRece ver
  ): Opt on[Top cW hScore] = {
    val scopedStats = statsRece ver.scope("getTop cProofScores")
    val enableT etToTop cScoreRank ng =
      params(Top cSoc alProofParams.EnableT etToTop cScoreRank ng)

    val m nT etToTop cCos neS m lar yThreshold =
      params(Top cSoc alProofParams.T etToTop cCos neS m lar yThreshold)

    val top cW hScore =
       f (enableT etToTop cScoreRank ng) {
        scopedStats.counter("enableT etToTop cScoreRank ng"). ncr()
        bu ldTop cW hVal dScore(
          top cProof,
          T etEmbedd ngType,
          So (Consu rTop cEmbedd ngType),
          So (ProducerTop cEmbedd ngType),
          allowL stW hTop cFollowType,
          DefaultModelVers on,
          m nT etToTop cCos neS m lar yThreshold
        )
      } else {
        scopedStats.counter("bu ldTop cW hRandomScore"). ncr()
        bu ldTop cW hRandomScore(
          top cProof,
          allowL stW hTop cFollowType,
          random
        )
      }
    top cW hScore

  }

  pr vate[handlers] def  sCrTop cT ets(
    request: Top cSoc alProofRequest
  ): Boolean = {
    // CrTop c (across a var ety of D splayLocat ons)  s t  only use case w h Top cL st ngSett ng.All
    request.top cL st ngSett ng == Top cL st ngSett ng.All
  }

  /**
   * Consol date log cs relevant to w t r only qual y top cs should be enabled for  mpl c  Follows
   */

  /***
   * Consol date log cs relevant to w t r Personal zed Contexts backf ll ng should be enabled
   */
  pr vate[handlers] def c ckPersonal zedContextsEl g b l y(
    params: Params,
    allowL stW hTop cFollowType: Map[Semant cCoreEnt y d, Opt on[Top cFollowType]]
  ): Personal zedContextEl g b l y = {
    val scopedStats = statsRece ver.scope("c ckPersonal zedContextsEl g b l y")
    val  sRecentFav nAllowl st = allowL stW hTop cFollowType
      .conta ns(Annotat onRuleProv der.recentFavTop c d)

    val  sRecentFavEl g ble =
       sRecentFav nAllowl st && params(Top cSoc alProofParams.EnableRecentEngage ntsTop c)
     f ( sRecentFavEl g ble)
      scopedStats.counter(" sRecentFavEl g ble"). ncr()

    val  sRecentRet et nAllowl st = allowL stW hTop cFollowType
      .conta ns(Annotat onRuleProv der.recentRet etTop c d)

    val  sRecentRet etEl g ble =
       sRecentRet et nAllowl st && params(Top cSoc alProofParams.EnableRecentEngage ntsTop c)
     f ( sRecentRet etEl g ble)
      scopedStats.counter(" sRecentRet etEl g ble"). ncr()

    val  sYML nAllowl st = allowL stW hTop cFollowType
      .conta ns(Annotat onRuleProv der. M ghtL keTop c d)

    val  sYMLEl g ble =
       sYML nAllowl st && params(Top cSoc alProofParams.Enable M ghtL keTop c)
     f ( sYMLEl g ble)
      scopedStats.counter(" sYMLEl g ble"). ncr()

    Personal zedContextEl g b l y( sRecentFavEl g ble,  sRecentRet etEl g ble,  sYMLEl g ble)
  }

  pr vate[handlers] def f lterPersonal zedContexts(
    soc alProofs: Map[T et d, Seq[Top cW hScore]],
    t et nfoMap: Map[T et d, Opt on[TspT et nfo]],
    params: Params
  ): Map[T et d, Seq[Top cW hScore]] = {
    val f lters: Seq[(Opt on[TspT et nfo], Params) => Boolean] = Seq(
       althS gnalsF lter,
      t etLanguageF lter
    )
    applyF lters(soc alProofs, t et nfoMap, params, f lters)
  }

  /** *
   * f lter t ets w h None t et nfo and undef ned language
   */
  pr vate def keepT etsW hT et nfoAndLanguage(
    t et nfoMapFut: Future[Map[T et d, Opt on[TspT et nfo]]],
    d splayLocat on: Str ng
  ): Future[Map[T et d, Opt on[TspT et nfo]]] = {
    val scopedStats = statsRece ver.scope(d splayLocat on)
    t et nfoMapFut.map { t et nfoMap =>
      val f lteredT et nfoMap = t et nfoMap.f lter {
        case (_, optT et nfo: Opt on[TspT et nfo]) =>
           f (optT et nfo. sEmpty) {
            scopedStats.counter("undef nedT et nfoCount"). ncr()
          }

          optT et nfo.ex sts { t et nfo: TspT et nfo =>
            {
               f (t et nfo.language. sEmpty) {
                scopedStats.counter("undef nedLanguageCount"). ncr()
              }
              t et nfo.language. sDef ned
            }
          }

      }
      val undef nedT et nfoOrLangCount = t et nfoMap.s ze - f lteredT et nfoMap.s ze
      scopedStats.counter("undef nedT et nfoOrLangCount"). ncr(undef nedT et nfoOrLangCount)

      scopedStats.counter("T et nfoCount"). ncr(t et nfoMap.s ze)

      f lteredT et nfoMap
    }
  }

  /***
   * f lter t ets w h NO evergreen top c soc al proofs by t  r  alth s gnal scores & t et languages
   *  .e., t ets that are poss ble to be converted  nto Personal zed Context top c t ets
   * TBD: w t r   are go ng to apply f lters to all top c t et cand dates
   */
  pr vate def applyF lters(
    soc alProofs: Map[T et d, Seq[Top cW hScore]],
    t et nfoMap: Map[T et d, Opt on[TspT et nfo]],
    params: Params,
    f lters: Seq[(Opt on[TspT et nfo], Params) => Boolean]
  ): Map[T et d, Seq[Top cW hScore]] = {
    soc alProofs.collect {
      case (t et d, soc alProofs)  f soc alProofs.nonEmpty || f lters.forall { f lter =>
            f lter(t et nfoMap.getOrElse(t et d, None), params)
          } =>
        t et d -> soc alProofs
    }
  }

  pr vate def  althS gnalsF lter(
    t et nfoOpt: Opt on[TspT et nfo],
    params: Params
  ): Boolean = {
    !params(
      Top cSoc alProofParams.EnableTop cT et althF lterPersonal zedContexts) ||  althS gnalsUt ls
      . s althyT et(t et nfoOpt)
  }

  pr vate def t etLanguageF lter(
    t et nfoOpt: Opt on[TspT et nfo],
    params: Params
  ): Boolean = {
    Personal zedContextTop csAllo dLanguageSet
      .conta ns(t et nfoOpt.flatMap(_.language).getOrElse(LocaleUt l.DefaultLanguage))
  }

  pr vate[handlers] def backf llPersonal zedContexts(
    allowL stW hTop cFollowType: Map[Semant cCoreEnt y d, Opt on[Top cFollowType]],
    soc alProofs: Map[T et d, Seq[Top cW hScore]],
     tr cTagsMap: scala.collect on.Map[T et d, scala.collect on.Set[ tr cTag]],
    personal zedContextEl g b l y: Personal zedContextEl g b l y
  ): Map[T et d, Seq[Top cW hScore]] = {
    val scopedStats = statsRece ver.scope("backf llPersonal zedContexts")
    soc alProofs.map {
      case (t et d, top cW hScores) =>
         f (top cW hScores.nonEmpty) {
          t et d -> Seq.empty
        } else {
          val  tr cTagConta nsT etFav =  tr cTagsMap
            .getOrElse(t et d, Set.empty[ tr cTag]).conta ns( tr cTag.T etFavor e)
          val backf llRecentFav =
            personal zedContextEl g b l y. sRecentFavEl g ble &&  tr cTagConta nsT etFav
           f ( tr cTagConta nsT etFav)
            scopedStats.counter(" tr cTag.T etFavor e"). ncr()
           f (backf llRecentFav)
            scopedStats.counter("backf llRecentFav"). ncr()

          val  tr cTagConta nsRet et =  tr cTagsMap
            .getOrElse(t et d, Set.empty[ tr cTag]).conta ns( tr cTag.Ret et)
          val backf llRecentRet et =
            personal zedContextEl g b l y. sRecentRet etEl g ble &&  tr cTagConta nsRet et
           f ( tr cTagConta nsRet et)
            scopedStats.counter(" tr cTag.Ret et"). ncr()
           f (backf llRecentRet et)
            scopedStats.counter("backf llRecentRet et"). ncr()

          val  tr cTagConta nsRecentSearc s =  tr cTagsMap
            .getOrElse(t et d, Set.empty[ tr cTag]).conta ns(
               tr cTag. nterestsRankerRecentSearc s)

          val backf llYML = personal zedContextEl g b l y. sYMLEl g ble
           f (backf llYML)
            scopedStats.counter("backf llYML"). ncr()

          t et d -> bu ldBackf llTop cs(
            allowL stW hTop cFollowType,
            backf llRecentFav,
            backf llRecentRet et,
            backf llYML)
        }
    }
  }

  pr vate def bu ldBackf llTop cs(
    allowL stW hTop cFollowType: Map[Semant cCoreEnt y d, Opt on[Top cFollowType]],
    backf llRecentFav: Boolean,
    backf llRecentRet et: Boolean,
    backf llYML: Boolean
  ): Seq[Top cW hScore] = {
    Seq(
       f (backf llRecentFav) {
        So (
          Top cW hScore(
            top c d = Annotat onRuleProv der.recentFavTop c d,
            score = 1.0,
            top cFollowType = allowL stW hTop cFollowType
              .getOrElse(Annotat onRuleProv der.recentFavTop c d, None)
          ))
      } else { None },
       f (backf llRecentRet et) {
        So (
          Top cW hScore(
            top c d = Annotat onRuleProv der.recentRet etTop c d,
            score = 1.0,
            top cFollowType = allowL stW hTop cFollowType
              .getOrElse(Annotat onRuleProv der.recentRet etTop c d, None)
          ))
      } else { None },
       f (backf llYML) {
        So (
          Top cW hScore(
            top c d = Annotat onRuleProv der. M ghtL keTop c d,
            score = 1.0,
            top cFollowType = allowL stW hTop cFollowType
              .getOrElse(Annotat onRuleProv der. M ghtL keTop c d, None)
          ))
      } else { None }
    ).flatten
  }

  def toReadableStore: ReadableStore[Top cSoc alProofRequest, Top cSoc alProofResponse] = {
    new ReadableStore[Top cSoc alProofRequest, Top cSoc alProofResponse] {
      overr de def get(k: Top cSoc alProofRequest): Future[Opt on[Top cSoc alProofResponse]] = {
        val d splayLocat on = k.d splayLocat on.toStr ng
        loadS dder(d splayLocat on) {
          getTop cSoc alProofResponse(k).map(So (_))
        }.rescue {
          case LoadS dder.LoadS dd ngExcept on =>
            statsRece ver.scope(d splayLocat on).counter("LoadS dd ngExcept on"). ncr()
            Future.None
          case _ =>
            statsRece ver.scope(d splayLocat on).counter("Except on"). ncr()
            Future.None
        }
      }
    }
  }
}

object Top cSoc alProofHandler {

  pr vate val MaxCand dates = 10
  // Currently   do hardcode for t  language c ck of Personal zedContexts Top cs
  pr vate val Personal zedContextTop csAllo dLanguageSet: Set[Str ng] =
    Set("pt", "ko", "es", "ja", "tr", " d", "en", "h ", "ar", "fr", "ru")

  pr vate val T  out: Durat on = 200.m ll seconds
  pr vate val Top cSoc alProofStoreT  out: Durat on = 40.m ll seconds
  pr vate val T et nfoStoreT  out: Durat on = 60.m ll seconds
  pr vate val DefaultResponse: Top cSoc alProofResponse = Top cSoc alProofResponse(Map.empty)

  case class Personal zedContextEl g b l y(
     sRecentFavEl g ble: Boolean,
     sRecentRet etEl g ble: Boolean,
     sYMLEl g ble: Boolean)

  /**
   * Calculate t  Top c Scores for each (t et, top c), f lter out top c proofs whose scores do not
   * pass t  m n mum threshold
   */
  pr vate[handlers] def bu ldTop cW hVal dScore(
    top cProof: Top cSoc alProof,
    t etEmbedd ngType: Embedd ngType,
    maybeConsu rEmbedd ngType: Opt on[Embedd ngType],
    maybeProducerEmbedd ngType: Opt on[Embedd ngType],
    allowL stW hTop cFollowType: Map[Semant cCoreEnt y d, Opt on[Top cFollowType]],
    s mClustersModelVers on: ModelVers on,
    m nT etToTop cCos neS m lar yThreshold: Double
  ): Opt on[Top cW hScore] = {

    val consu rScore = maybeConsu rEmbedd ngType
      .flatMap { consu rEmbedd ngType =>
        top cProof.scores.get(
          ScoreKey(consu rEmbedd ngType, t etEmbedd ngType, s mClustersModelVers on))
      }.getOrElse(0.0)

    val producerScore = maybeProducerEmbedd ngType
      .flatMap { producerEmbedd ngType =>
        top cProof.scores.get(
          ScoreKey(producerEmbedd ngType, t etEmbedd ngType, s mClustersModelVers on))
      }.getOrElse(0.0)

    val comb nedScore = consu rScore + producerScore
     f (comb nedScore > m nT etToTop cCos neS m lar yThreshold || top cProof. gnoreS mClusterF lter ng) {
      So (
        Top cW hScore(
          top c d = top cProof.top c d.ent y d,
          score = comb nedScore,
          top cFollowType =
            allowL stW hTop cFollowType.getOrElse(top cProof.top c d.ent y d, None)))
    } else {
      None
    }
  }

  pr vate[handlers] def bu ldTop cW hRandomScore(
    top cSoc alProof: Top cSoc alProof,
    allowL stW hTop cFollowType: Map[Semant cCoreEnt y d, Opt on[Top cFollowType]],
    random: Random
  ): Opt on[Top cW hScore] = {

    So (
      Top cW hScore(
        top c d = top cSoc alProof.top c d.ent y d,
        score = random.nextDouble(),
        top cFollowType =
          allowL stW hTop cFollowType.getOrElse(top cSoc alProof.top c d.ent y d, None)
      ))
  }

  /**
   * F lter all t  non-qual f ed Top c Soc al Proof
   */
  pr vate[handlers] def f lterByAllo dL st(
    top cProofs: Map[T et d, Seq[Top cSoc alProof]],
    sett ng: Top cL st ngSett ng,
    allowL st: Set[Semant cCoreEnt y d]
  ): Map[T et d, Seq[Top cSoc alProof]] = {
    sett ng match {
      case Top cL st ngSett ng.All =>
        // Return all t  top cs
        top cProofs
      case _ =>
        top cProofs.mapValues(
          _.f lter(top cProof => allowL st.conta ns(top cProof.top c d.ent y d)))
    }
  }
}
