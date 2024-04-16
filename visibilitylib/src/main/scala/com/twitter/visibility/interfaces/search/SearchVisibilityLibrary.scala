package com.tw ter.v s b l y. nterfaces.search

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. d aserv ces. d a_ut l.Gener c d aKey
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Stopwatch
 mport com.tw ter.ut l.Try
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.Verd ctLogger
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder. d a. d aFeatures
 mport com.tw ter.v s b l y.bu lder. d a.Strato d aLabelMaps
 mport com.tw ter.v s b l y.bu lder.t ets._
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.bu lder.users.Relat onsh pFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rFeatures
 mport com.tw ter.v s b l y.common. d aSafetyLabelMapS ce
 mport com.tw ter.v s b l y.common.M s nformat onPol cyS ce
 mport com.tw ter.v s b l y.common.SafetyLabelMapS ce
 mport com.tw ter.v s b l y.common.TrustedFr endsS ce
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.rules.ComposableAct ons._
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.features.T et s nnerQuotedT et
 mport com.tw ter.v s b l y.features.T et sRet et
 mport com.tw ter.v s b l y.features.T et sS ceT et
 mport com.tw ter.v s b l y. nterfaces.common.search.SearchVFRequestContext
 mport com.tw ter.v s b l y. nterfaces.search.SearchV s b l yL brary.EvaluateT et
 mport com.tw ter.v s b l y. nterfaces.search.SearchV s b l yL brary.RequestT et d
 mport com.tw ter.v s b l y. nterfaces.search.T etType.EvaluateT etType
 mport com.tw ter.v s b l y.logg ng.thr ftscala.VFL bType
 mport com.tw ter.v s b l y.models.Content d
 mport com.tw ter.v s b l y.models.Content d.BlenderT et d
 mport com.tw ter.v s b l y.models.Content d.T et d
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.SafetyLevel.toThr ft
 mport com.tw ter.v s b l y.models.V e rContext
 mport com.tw ter.v s b l y.rules.Act on
 mport com.tw ter.v s b l y.rules.Allow
 mport com.tw ter.v s b l y.rules.Drop
 mport com.tw ter.v s b l y.rules. nterst  al
 mport com.tw ter.v s b l y.rules.T et nterst  al

object T etType extends Enu rat on {
  type EvaluateT etType = Value
  val REQUEST: T etType.Value = Value(1)
  val QUOTED: T etType.Value = Value(2)
  val SOURCE: T etType.Value = Value(3)
}

 mport com.tw ter.v s b l y. nterfaces.search.T etType._

object SearchV s b l yL brary {
  type RequestT et d = Long
  type EvaluateT et d = Long
  type EvaluateT et = T et

  def bu ldW hStratoCl ent(
    v s b l yL brary: V s b l yL brary,
    dec der: Dec der,
    stratoCl ent: StratoCl ent,
    userS ce: UserS ce,
    userRelat onsh pS ce: UserRelat onsh pS ce
  ): SearchV s b l yL brary = new SearchV s b l yL brary(
    v s b l yL brary,
    dec der,
    stratoCl ent,
    userS ce,
    userRelat onsh pS ce,
    None
  )

  def bu ldW hSafetyLabelMapS ce(
    v s b l yL brary: V s b l yL brary,
    dec der: Dec der,
    stratoCl ent: StratoCl ent,
    userS ce: UserS ce,
    userRelat onsh pS ce: UserRelat onsh pS ce,
    safetyLabelMapS ce: SafetyLabelMapS ce
  ): SearchV s b l yL brary = new SearchV s b l yL brary(
    v s b l yL brary,
    dec der,
    stratoCl ent,
    userS ce,
    userRelat onsh pS ce,
    So (safetyLabelMapS ce)
  )

  def createVerd ctLogger(
    enableVerd ctLogger: Gate[Un ],
    dec der: Dec der,
    statsRece ver: StatsRece ver
  ): Verd ctLogger = {
     f (enableVerd ctLogger()) {
      Verd ctLogger(statsRece ver, dec der)
    } else {
      Verd ctLogger.Empty
    }
  }

  def scr beV s b l yVerd ct(
    result: Comb nedV s b l yResult,
    enableVerd ctScr b ng: Gate[Un ],
    verd ctLogger: Verd ctLogger,
    v e r d: Opt on[Long],
    safetyLevel: SafetyLevel
  ): Un  =  f (enableVerd ctScr b ng()) {
    verd ctLogger.scr beVerd ct(
      v s b l yResult = result.t etV s b l yResult,
      v e r d = v e r d,
      safetyLevel = toThr ft(safetyLevel),
      vfL bType = VFL bType.SearchV s b l yL brary)

    result.quotedT etV s b l yResult.map(quotedT etV s b l yResult =>
      verd ctLogger.scr beVerd ct(
        v s b l yResult = quotedT etV s b l yResult,
        v e r d = v e r d,
        safetyLevel = toThr ft(safetyLevel),
        vfL bType = VFL bType.SearchV s b l yL brary))
  }
}

class SearchV s b l yL brary(
  v s b l yL brary: V s b l yL brary,
  dec der: Dec der,
  stratoCl ent: StratoCl ent,
  userS ce: UserS ce,
  userRelat onsh pS ce: UserRelat onsh pS ce,
  safetyLabelMapS ceOpt on: Opt on[SafetyLabelMapS ce]) {

  val l braryStatsRece ver = v s b l yL brary.statsRece ver
  val stratoCl entStatsRece ver = v s b l yL brary.statsRece ver.scope("strato")
  val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")
  val svlRequestCounter = l braryStatsRece ver.counter("svl_requests")
  val vfLatencyOverallStat = l braryStatsRece ver.stat("vf_latency_overall")
  val vfLatencySt chBu ldStat = l braryStatsRece ver.stat("vf_latency_st ch_bu ld")
  val vfLatencySt chRunStat = l braryStatsRece ver.stat("vf_latency_st ch_run")
  val v s b l yDec derGates = V s b l yDec derGates(dec der)
  val verd ctLogger = SearchV s b l yL brary.createVerd ctLogger(
    v s b l yDec derGates.enableVerd ctLoggerSVL,
    dec der,
    l braryStatsRece ver)

  val t etLabels = safetyLabelMapS ceOpt on match {
    case So (safetyLabelMapS ce) =>
      new StratoT etLabelMaps(safetyLabelMapS ce)
    case None =>
      new StratoT etLabelMaps(
        SafetyLabelMapS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver))
  }

  val  d aLabelMaps = new Strato d aLabelMaps(
     d aSafetyLabelMapS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver))

  val t etFeatures = new T etFeatures(t etLabels, l braryStatsRece ver)
  val searchContextFeatures = new SearchContextFeatures(l braryStatsRece ver)
  val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)
  val v e rFeatures = new V e rFeatures(userS ce, l braryStatsRece ver)
  val relat onsh pFeatures =
    new Relat onsh pFeatures(userRelat onsh pS ce, l braryStatsRece ver)
  val m s nfoPol cyS ce =
    M s nformat onPol cyS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver)
  val m s nfoPol cyFeatures =
    new M s nformat onPol cyFeatures(m s nfoPol cyS ce, stratoCl entStatsRece ver)
  val exclus veT etFeatures =
    new Exclus veT etFeatures(userRelat onsh pS ce, l braryStatsRece ver)
  val  d aFeatures = new  d aFeatures( d aLabelMaps, l braryStatsRece ver)
  val trustedFr endsT etFeatures = new TrustedFr endsFeatures(
    trustedFr endsS ce = TrustedFr endsS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver))
  val ed T etFeatures = new Ed T etFeatures(l braryStatsRece ver)

  def batchProcessSearchV s b l yRequest(
    batchSvRequest: BatchSearchV s b l yRequest
  ): St ch[BatchSearchV s b l yResponse] = {
    val elapsed = Stopwatch.start()
    svlRequestCounter. ncr()

    val response: St ch[BatchSearchV s b l yResponse] =
      batchSvRequest.t etContexts.groupBy(t etContext => t etContext.safetyLevel) map {
        case (safetyLevel: SafetyLevel, t etContexts: Seq[T etContext]) =>
          val (contentsToBeEvaluated, contentV sResultTypes) =
            extractContentsToBeEvaluated(t etContexts, batchSvRequest.v e rContext)

          getV s b l yResult(
            contentsToBeEvaluated,
            safetyLevel,
            batchSvRequest.v e rContext,
            batchSvRequest.searchVFRequestContext)
            .map { contentV sResults: Seq[Try[V s b l yResult]] =>
              (contentV sResultTypes z p contentV sResults)
                .map(handleV s b l yResultByT etType)
                .groupBy {
                  case (requestT et d: RequestT et d, (_, _)) => requestT et d
                }.mapValues(comb neV s b l yResult)
            }.onSuccess(res =>
              res.values.flatten.foreach(_ =>
                SearchV s b l yL brary.scr beV s b l yVerd ct(
                  _,
                  v s b l yDec derGates.enableVerd ctScr b ngSVL,
                  verd ctLogger,
                  batchSvRequest.v e rContext.user d,
                  safetyLevel)))
      } reduceLeft { (left, r ght) =>
        St ch.jo nMap(left, r ght)((v sResultsA, v sResultsB) => v sResultsA ++ v sResultsB)
      } map { v sResults =>
        val (succeed, fa led) = v sResults.part  on { case (_, v sResult) => v sResult.nonEmpty }
        val fa ledT et ds: Seq[Long] = fa led.keys.toSeq
        BatchSearchV s b l yResponse(
          v s b l yResults = succeed.mapValues(v sResult => v sResult.get),
          fa ledT et ds = fa ledT et ds
        )
      }

    val runSt chStartMs = elapsed(). nM ll seconds
    val bu ldSt chStatMs = elapsed(). nM ll seconds
    vfLatencySt chBu ldStat.add(bu ldSt chStatMs)

    response
      .onSuccess(_ => {
        val overallMs = elapsed(). nM ll seconds
        vfLatencyOverallStat.add(overallMs)
        val st chRunMs = elapsed(). nM ll seconds - runSt chStartMs
        vfLatencySt chRunStat.add(st chRunMs)
      })
  }

  pr vate def extractContentsToBeEvaluated(
    t etContexts: Seq[T etContext],
    v e rContext: V e rContext
  ): (
    Seq[(T etContext, EvaluateT etType, EvaluateT et, Content d)],
    Seq[
      (RequestT et d, EvaluateT etType)
    ]
  ) = {
    val contentsToBeEvaluated: Seq[
      (T etContext, EvaluateT etType, EvaluateT et, Content d)
    ] = t etContexts.map(tc =>
      (
        tc,
        REQUEST,
        tc.t et,
        getContent d(
          v e r d = v e rContext.user d,
          author d = tc.t et.coreData.get.user d,
          t et = tc.t et))) ++
      t etContexts
        .f lter(tc => tc.quotedT et.nonEmpty).map(tc =>
          (
            tc,
            QUOTED,
            tc.quotedT et.get,
            getContent d(
              v e r d = v e rContext.user d,
              author d = tc.quotedT et.get.coreData.get.user d,
              t et = tc.quotedT et.get))) ++
      t etContexts
        .f lter(tc => tc.ret etS ceT et.nonEmpty).map(tc =>
          (
            tc,
            SOURCE,
            tc.ret etS ceT et.get,
            getContent d(
              v e r d = v e rContext.user d,
              author d = tc.ret etS ceT et.get.coreData.get.user d,
              t et = tc.ret etS ceT et.get)))

    val contentV sResultTypes: Seq[(RequestT et d, EvaluateT etType)] = {
      contentsToBeEvaluated.map {
        case (tc: T etContext, t etType: EvaluateT etType, _, _) =>
          (tc.t et. d, t etType)
      }
    }

    (contentsToBeEvaluated, contentV sResultTypes)
  }

  pr vate def comb neV s b l yResult(
    v sResults: Seq[(RequestT et d, (EvaluateT etType, Try[V s b l yResult]))]
  ): Opt on[Comb nedV s b l yResult] = {
    v sResults.sortBy(_._2._1)(ValueOrder ng) match {
      case Seq(
            (_, (REQUEST, Return(requestT etV sResult))),
            (_, (QUOTED, Return(quotedT etV sResult))),
            (_, (SOURCE, Return(s ceT etV sResult)))) =>
        requestT etV sResult.verd ct match {
          case Allow =>
            So (Comb nedV s b l yResult(s ceT etV sResult, So (quotedT etV sResult)))
          case _ =>
            So (Comb nedV s b l yResult(requestT etV sResult, So (quotedT etV sResult)))
        }
      case Seq(
            (_, (REQUEST, Return(requestT etV sResult))),
            (_, (QUOTED, Return(quotedT etV sResult)))) =>
        So (Comb nedV s b l yResult(requestT etV sResult, So (quotedT etV sResult)))
      case Seq(
            (_, (REQUEST, Return(requestT etV sResult))),
            (_, (SOURCE, Return(s ceT etV sResult)))) =>
        requestT etV sResult.verd ct match {
          case Allow =>
            So (Comb nedV s b l yResult(s ceT etV sResult, None))
          case _ =>
            So (Comb nedV s b l yResult(requestT etV sResult, None))
        }

      case Seq((_, (REQUEST, Return(requestT etV sResult)))) =>
        So (Comb nedV s b l yResult(requestT etV sResult, None))
      case _ => None
    }
  }

  pr vate def getV s b l yResult(
    contents: Seq[(T etContext, EvaluateT etType, EvaluateT et, Content d)],
    safetyLevel: SafetyLevel,
    v e rContext: V e rContext,
    svRequestContext: SearchVFRequestContext
  ): St ch[Seq[Try[V s b l yResult]]] = {

    val contentContext: Map[Content d, (T etContext, EvaluateT etType, EvaluateT et)] =
      contents.map {
        case (
              t etContext: T etContext,
              t etType: EvaluateT etType,
              t et: EvaluateT et,
              content d: Content d) =>
          content d -> ((t etContext, t etType, t et))
      }.toMap

    val featureMapProv der: (Content d, SafetyLevel) => FeatureMap = {
      case (content d: Content d, _) =>
        val (t etContext, t etType, t et) = contentContext(content d)
        bu ldFeatureMap(
          evaluatedT et = t et,
          t etType = t etType,
          t etContext = t etContext,
          v e rContext = v e rContext,
          svRequestContext = svRequestContext
        )
    }

    v s b l yL brary.runRuleEng neBatch(
      content ds = contents.map { case (_, _, _,  d: Content d) =>  d },
      featureMapProv der = featureMapProv der,
      v e rContext = v e rContext,
      safetyLevel = safetyLevel
    )
  }

  pr vate def getContent d(v e r d: Opt on[Long], author d: Long, t et: T et): Content d = {
     f (v e r d.conta ns(author d))
      T et d(t et. d)
    else BlenderT et d(t et. d)
  }

  pr vate def bu ldFeatureMap(
    evaluatedT et: T et,
    t etType: EvaluateT etType,
    t etContext: T etContext,
    v e rContext: V e rContext,
    svRequestContext: SearchVFRequestContext
  ): FeatureMap = {
    val author d = evaluatedT et.coreData.get.user d
    val v e r d = v e rContext.user d
    val  sRet et =
       f (t etType.equals(REQUEST)) t etContext.ret etS ceT et.nonEmpty else false
    val  sS ceT et = t etType.equals(SOURCE)
    val  sQuotedT et = t etType.equals(QUOTED)
    val t et d aKeys: Seq[Gener c d aKey] = evaluatedT et. d a
      .getOrElse(Seq.empty)
      .flatMap(_. d aKey.map(Gener c d aKey.apply))

    v s b l yL brary.featureMapBu lder(
      Seq(
        v e rFeatures
          .forV e rSearchContext(svRequestContext, v e rContext),
        relat onsh pFeatures.forAuthor d(author d, v e r d),
        t etFeatures.forT et(evaluatedT et),
         d aFeatures.for d aKeys(t et d aKeys),
        authorFeatures.forAuthor d(author d),
        searchContextFeatures.forSearchContext(svRequestContext),
        _.w hConstantFeature(T et sRet et,  sRet et),
        m s nfoPol cyFeatures.forT et(evaluatedT et, v e rContext),
        exclus veT etFeatures.forT et(evaluatedT et, v e rContext),
        trustedFr endsT etFeatures.forT et(evaluatedT et, v e r d),
        ed T etFeatures.forT et(evaluatedT et),
        _.w hConstantFeature(T et s nnerQuotedT et,  sQuotedT et),
        _.w hConstantFeature(T et sS ceT et,  sS ceT et),
      )
    )
  }

  pr vate def handleV s b l yResultByT etType(
    z pV sResult: ((RequestT et d, EvaluateT etType), Try[V s b l yResult])
  ): (RequestT et d, (EvaluateT etType, Try[V s b l yResult])) = {
    z pV sResult match {
      case (( d: RequestT et d, REQUEST), Return(v sResult)) =>
        ( d, (REQUEST, Return(handleComposableV s b l yResult(v sResult))))
      case (( d: RequestT et d, QUOTED), Return(v sResult)) =>
        (
           d,
          (
            QUOTED,
            Return(
              handle nnerQuotedT etV s b l yResult(handleComposableV s b l yResult(v sResult)))))
      case (( d: RequestT et d, SOURCE), Return(v sResult)) =>
        ( d, (SOURCE, Return(handleComposableV s b l yResult(v sResult))))
      case (( d: RequestT et d, t etType: EvaluateT etType), result: Try[V s b l yResult]) =>
        ( d, (t etType, result))
    }
  }

  pr vate def handleComposableV s b l yResult(result: V s b l yResult): V s b l yResult = {
     f (result.secondaryVerd cts.nonEmpty) {
      result.copy(verd ct = composeAct ons(result.verd ct, result.secondaryVerd cts))
    } else {
      result
    }
  }

  pr vate def composeAct ons(pr mary: Act on, secondary: Seq[Act on]): Act on = {
     f (pr mary. sComposable && secondary.nonEmpty) {
      val act ons = Seq[Act on] { pr mary } ++ secondary
      val  nterst  alOpt = Act on.getF rst nterst  al(act ons: _*)
      val soft ntervent onOpt = Act on.getF rstSoft ntervent on(act ons: _*)
      val l m edEngage ntsOpt = Act on.getF rstL m edEngage nts(act ons: _*)
      val avo dOpt = Act on.getF rstAvo d(act ons: _*)

      val numAct ons =
        Seq[Opt on[_]]( nterst  alOpt, soft ntervent onOpt, l m edEngage ntsOpt, avo dOpt)
          .count(_. sDef ned)
       f (numAct ons > 1) {
        T et nterst  al(
           nterst  alOpt,
          soft ntervent onOpt,
          l m edEngage ntsOpt,
          None,
          avo dOpt
        )
      } else {
        pr mary
      }
    } else {
      pr mary
    }
  }

  pr vate def handle nnerQuotedT etV s b l yResult(
    result: V s b l yResult
  ): V s b l yResult = {
    val newVerd ct: Act on =
      result.verd ct match {
        case  nterst  al:  nterst  al => Drop( nterst  al.reason)
        case ComposableAct onsW h nterst  al(t et nterst  al) => Drop(t et nterst  al.reason)
        case verd ct => verd ct
      }

    result.copy(verd ct = newVerd ct)
  }
}
