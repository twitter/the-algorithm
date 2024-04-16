package com.tw ter.v s b l y. nterfaces.blender

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. d aserv ces. d a_ut l.Gener c d aKey
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.ut l.Stopwatch
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
 mport com.tw ter.v s b l y.rules.ComposableAct ons.ComposableAct onsW h nterst  al
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.features.T et s nnerQuotedT et
 mport com.tw ter.v s b l y.features.T et sRet et
 mport com.tw ter.v s b l y.features.T et sS ceT et
 mport com.tw ter.v s b l y.logg ng.thr ftscala.VFL bType
 mport com.tw ter.v s b l y.models.Content d
 mport com.tw ter.v s b l y.models.Content d.BlenderT et d
 mport com.tw ter.v s b l y.models.Content d.T et d
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.SafetyLevel.toThr ft
 mport com.tw ter.v s b l y.rules.Act on
 mport com.tw ter.v s b l y.rules.Allow
 mport com.tw ter.v s b l y.rules.Drop
 mport com.tw ter.v s b l y.rules. nterst  al
 mport com.tw ter.v s b l y.rules.T et nterst  al

object T etType extends Enu rat on {
  type T etType = Value
  val OR G NAL, SOURCE, QUOTED = Value
}
 mport com.tw ter.v s b l y. nterfaces.blender.T etType._

object BlenderV s b l yL brary {
  def bu ldW hStratoCl ent(
    v s b l yL brary: V s b l yL brary,
    dec der: Dec der,
    stratoCl ent: StratoCl ent,
    userS ce: UserS ce,
    userRelat onsh pS ce: UserRelat onsh pS ce
  ): BlenderV s b l yL brary = new BlenderV s b l yL brary(
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
  ): BlenderV s b l yL brary = new BlenderV s b l yL brary(
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
      vfL bType = VFL bType.BlenderV s b l yL brary)

    result.quotedT etV s b l yResult.map(quotedT etV s b l yResult =>
      verd ctLogger.scr beVerd ct(
        v s b l yResult = quotedT etV s b l yResult,
        v e r d = v e r d,
        safetyLevel = toThr ft(safetyLevel),
        vfL bType = VFL bType.BlenderV s b l yL brary))
  }
}

class BlenderV s b l yL brary(
  v s b l yL brary: V s b l yL brary,
  dec der: Dec der,
  stratoCl ent: StratoCl ent,
  userS ce: UserS ce,
  userRelat onsh pS ce: UserRelat onsh pS ce,
  safetyLabelMapS ceOpt on: Opt on[SafetyLabelMapS ce]) {

  val l braryStatsRece ver = v s b l yL brary.statsRece ver
  val stratoCl entStatsRece ver = v s b l yL brary.statsRece ver.scope("strato")
  val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")
  val bvlRequestCounter = l braryStatsRece ver.counter("bvl_requests")
  val vfLatencyOverallStat = l braryStatsRece ver.stat("vf_latency_overall")
  val vfLatencySt chBu ldStat = l braryStatsRece ver.stat("vf_latency_st ch_bu ld")
  val vfLatencySt chRunStat = l braryStatsRece ver.stat("vf_latency_st ch_run")
  val v s b l yDec derGates = V s b l yDec derGates(dec der)
  val verd ctLogger = BlenderV s b l yL brary.createVerd ctLogger(
    v s b l yDec derGates.enableVerd ctLoggerBVL,
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
  val blenderContextFeatures = new BlenderContextFeatures(l braryStatsRece ver)
  val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)
  val v e rFeatures = new V e rFeatures(userS ce, l braryStatsRece ver)
  val relat onsh pFeatures =
    new Relat onsh pFeatures(userRelat onsh pS ce, l braryStatsRece ver)
  val fonsrRelat onsh pFeatures =
    new FosnrRelat onsh pFeatures(
      t etLabels = t etLabels,
      userRelat onsh pS ce = userRelat onsh pS ce,
      statsRece ver = l braryStatsRece ver)
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

  def getComb nedV s b l yResult(
    bvRequest: BlenderV s b l yRequest
  ): St ch[Comb nedV s b l yResult] = {
    val elapsed = Stopwatch.start()
    bvlRequestCounter. ncr()

    val (
      requestT etV s b l yResult,
      quotedT etV s b l yResultOpt on,
      s ceT etV s b l yResultOpt on
    ) = getAllV s b l yResults(bvRequest: BlenderV s b l yRequest)

    val response: St ch[Comb nedV s b l yResult] = {
      (
        requestT etV s b l yResult,
        quotedT etV s b l yResultOpt on,
        s ceT etV s b l yResultOpt on) match {
        case (requestT etV sResult, So (quotedT etV sResult), So (s ceT etV sResult)) => {
          St ch
            .jo n(
              requestT etV sResult,
              quotedT etV sResult,
              s ceT etV sResult
            ).map {
              case (requestT etV sResult, quotedT etV sResult, s ceT etV sResult) => {
                requestT etV sResult.verd ct match {
                  case Allow =>
                    Comb nedV s b l yResult(s ceT etV sResult, So (quotedT etV sResult))
                  case _ =>
                    Comb nedV s b l yResult(requestT etV sResult, So (quotedT etV sResult))
                }
              }
            }
        }

        case (requestT etV sResult, None, So (s ceT etV sResult)) => {
          St ch
            .jo n(
              requestT etV sResult,
              s ceT etV sResult
            ).map {
              case (requestT etV sResult, s ceT etV sResult) => {
                requestT etV sResult.verd ct match {
                  case Allow =>
                    Comb nedV s b l yResult(s ceT etV sResult, None)
                  case _ =>
                    Comb nedV s b l yResult(requestT etV sResult, None)
                }
              }
            }
        }

        case (requestT etV sResult, So (quotedT etV sResult), None) => {
          St ch
            .jo n(
              requestT etV sResult,
              quotedT etV sResult
            ).map {
              case (requestT etV sResult, quotedT etV sResult) => {
                Comb nedV s b l yResult(requestT etV sResult, So (quotedT etV sResult))
              }
            }
        }

        case (requestT etV sResult, None, None) => {
          requestT etV sResult.map {
            Comb nedV s b l yResult(_, None)
          }
        }
      }
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
      .onSuccess(
        BlenderV s b l yL brary.scr beV s b l yVerd ct(
          _,
          v s b l yDec derGates.enableVerd ctScr b ngBVL,
          verd ctLogger,
          bvRequest.v e rContext.user d,
          bvRequest.safetyLevel))
  }

  def getContent d(v e r d: Opt on[Long], author d: Long, t et: T et): Content d = {
     f (v e r d.conta ns(author d))
      T et d(t et. d)
    else BlenderT et d(t et. d)
  }

  def getAllV s b l yResults(bvRequest: BlenderV s b l yRequest): (
    St ch[V s b l yResult],
    Opt on[St ch[V s b l yResult]],
    Opt on[St ch[V s b l yResult]]
  ) = {
    val t etContent d = getContent d(
      v e r d = bvRequest.v e rContext.user d,
      author d = bvRequest.t et.coreData.get.user d,
      t et = bvRequest.t et)

    val t etFeatureMap =
      bu ldFeatureMap(bvRequest, bvRequest.t et, OR G NAL)
    vfEng neCounter. ncr()
    val requestT etV s b l yResult = v s b l yL brary
      .runRuleEng ne(
        t etContent d,
        t etFeatureMap,
        bvRequest.v e rContext,
        bvRequest.safetyLevel
      ).map(handleComposableV s b l yResult)

    val quotedT etV s b l yResultOpt on = bvRequest.quotedT et.map(quotedT et => {
      val quotedT etContent d = getContent d(
        v e r d = bvRequest.v e rContext.user d,
        author d = quotedT et.coreData.get.user d,
        t et = quotedT et)

      val quoted nnerT etFeatureMap =
        bu ldFeatureMap(bvRequest, quotedT et, QUOTED)
      vfEng neCounter. ncr()
      v s b l yL brary
        .runRuleEng ne(
          quotedT etContent d,
          quoted nnerT etFeatureMap,
          bvRequest.v e rContext,
          bvRequest.safetyLevel
        )
        .map(handleComposableV s b l yResult)
        .map(handle nnerQuotedT etV s b l yResult)
    })

    val s ceT etV s b l yResultOpt on = bvRequest.ret etS ceT et.map(s ceT et => {
      val s ceT etContent d = getContent d(
        v e r d = bvRequest.v e rContext.user d,
        author d = s ceT et.coreData.get.user d,
        t et = s ceT et)

      val s ceT etFeatureMap =
        bu ldFeatureMap(bvRequest, s ceT et, SOURCE)
      vfEng neCounter. ncr()
      v s b l yL brary
        .runRuleEng ne(
          s ceT etContent d,
          s ceT etFeatureMap,
          bvRequest.v e rContext,
          bvRequest.safetyLevel
        )
        .map(handleComposableV s b l yResult)
    })

    (
      requestT etV s b l yResult,
      quotedT etV s b l yResultOpt on,
      s ceT etV s b l yResultOpt on)
  }

  def bu ldFeatureMap(
    bvRequest: BlenderV s b l yRequest,
    t et: T et,
    t etType: T etType
  ): FeatureMap = {
    val author d = t et.coreData.get.user d
    val v e r d = bvRequest.v e rContext.user d
    val  sRet et =  f (t etType.equals(OR G NAL)) bvRequest. sRet et else false
    val  sS ceT et = t etType.equals(SOURCE)
    val  sQuotedT et = t etType.equals(QUOTED)
    val t et d aKeys: Seq[Gener c d aKey] = t et. d a
      .getOrElse(Seq.empty)
      .flatMap(_. d aKey.map(Gener c d aKey.apply))

    v s b l yL brary.featureMapBu lder(
      Seq(
        v e rFeatures
          .forV e rBlenderContext(bvRequest.blenderVFRequestContext, bvRequest.v e rContext),
        relat onsh pFeatures.forAuthor d(author d, v e r d),
        fonsrRelat onsh pFeatures
          .forT etAndAuthor d(t et = t et, author d = author d, v e r d = v e r d),
        t etFeatures.forT et(t et),
         d aFeatures.for d aKeys(t et d aKeys),
        authorFeatures.forAuthor d(author d),
        blenderContextFeatures.forBlenderContext(bvRequest.blenderVFRequestContext),
        _.w hConstantFeature(T et sRet et,  sRet et),
        m s nfoPol cyFeatures.forT et(t et, bvRequest.v e rContext),
        exclus veT etFeatures.forT et(t et, bvRequest.v e rContext),
        trustedFr endsT etFeatures.forT et(t et, v e r d),
        ed T etFeatures.forT et(t et),
        _.w hConstantFeature(T et s nnerQuotedT et,  sQuotedT et),
        _.w hConstantFeature(T et sS ceT et,  sS ceT et),
      )
    )
  }

  def handleComposableV s b l yResult(result: V s b l yResult): V s b l yResult = {
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

  def handle nnerQuotedT etV s b l yResult(
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
