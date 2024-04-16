package com.tw ter.v s b l y. nterfaces.t ets

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. d aserv ces. d a_ut l.Gener c d aKey
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.ut l.Stopwatch
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.Verd ctLogger
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.common.MutedKeywordFeatures
 mport com.tw ter.v s b l y.bu lder. d a._
 mport com.tw ter.v s b l y.bu lder.t ets.T etV s b l yNudgeS ceWrapper
 mport com.tw ter.v s b l y.bu lder.t ets._
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.bu lder.users.Relat onsh pFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rSearchSafetyFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rSens  ve d aSett ngsFeatures
 mport com.tw ter.v s b l y.common._
 mport com.tw ter.v s b l y.common.act ons.L m edAct on
 mport com.tw ter.v s b l y.common.act ons.L m edAct onType
 mport com.tw ter.v s b l y.common.act ons.L m edAct onsPol cy
 mport com.tw ter.v s b l y.rules.ComposableAct ons._
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y.features.T et s nnerQuotedT et
 mport com.tw ter.v s b l y.features.T et sRet et
 mport com.tw ter.v s b l y.features.T et sS ceT et
 mport com.tw ter.v s b l y.generators.Local zed nterst  alGenerator
 mport com.tw ter.v s b l y.generators.TombstoneGenerator
 mport com.tw ter.v s b l y. nterfaces.t ets.enr ch nts.Compl anceT etNot ceEnr ch nt
 mport com.tw ter.v s b l y. nterfaces.t ets.enr ch nts.L m edAct onsPol cyEnr ch nt
 mport com.tw ter.v s b l y. nterfaces.t ets.enr ch nts.T etV s b l yNudgeEnr ch nt
 mport com.tw ter.v s b l y.logg ng.thr ftscala.VFL bType
 mport com.tw ter.v s b l y.models.Content d.T et d
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.SafetyLevel.toThr ft
 mport com.tw ter.v s b l y.rules._

object T etV s b l yL brary {
  type Type = T etV s b l yRequest => St ch[V s b l yResult]

  def apply(
    v s b l yL brary: V s b l yL brary,
    userS ce: UserS ce,
    userRelat onsh pS ce: UserRelat onsh pS ce,
    keywordMatc r: KeywordMatc r.Matc r,
     nv edToConversat onRepo:  nv edToConversat onRepo,
    dec der: Dec der,
    stratoCl ent: StratoCl ent,
    local zat onS ce: Local zat onS ce,
    t etPerspect veS ce: T etPerspect veS ce,
    t et d a tadataS ce: T et d a tadataS ce,
    tombstoneGenerator: TombstoneGenerator,
     nterst  alGenerator: Local zed nterst  alGenerator,
    l m edAct onsFeatureSw c s: FeatureSw c s,
    enablePar yTest: Gate[Un ] = Gate.False
  ): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver
    val stratoCl entStatsRece ver = v s b l yL brary.statsRece ver.scope("strato")
    val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")
    val vfLatencyOverallStat = l braryStatsRece ver.stat("vf_latency_overall")
    val vfLatencySt chBu ldStat = l braryStatsRece ver.stat("vf_latency_st ch_bu ld")
    val vfLatencySt chRunStat = l braryStatsRece ver.stat("vf_latency_st ch_run")
    val v s b l yDec derGates = V s b l yDec derGates(dec der)
    val verd ctLogger =
      createVerd ctLogger(
        v s b l yDec derGates.enableVerd ctLoggerTVL,
        dec der,
        l braryStatsRece ver)

    val t etLabelMaps = new StratoT etLabelMaps(
      SafetyLabelMapS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver))

    val  d aLabelMaps = new Strato d aLabelMaps(
       d aSafetyLabelMapS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver))

    val t etFeatures = new T etFeatures(t etLabelMaps, l braryStatsRece ver)
    val t etPerspect veFeatures =
      new T etPerspect veFeatures(t etPerspect veS ce, l braryStatsRece ver)
    val  d aFeatures = new  d aFeatures( d aLabelMaps, l braryStatsRece ver)
    val t et d a tadataFeatures =
      new T et d a tadataFeatures(t et d a tadataS ce, l braryStatsRece ver)
    val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)
    val v e rFeatures = new V e rFeatures(userS ce, l braryStatsRece ver)
    val mutedKeywordFeatures =
      new MutedKeywordFeatures(
        userS ce,
        userRelat onsh pS ce,
        keywordMatc r,
        l braryStatsRece ver,
        v s b l yDec derGates.enableFollowC ck nMutedKeyword
      )
    val relat onsh pFeatures =
      new Relat onsh pFeatures(userRelat onsh pS ce, l braryStatsRece ver)
    val fonsrRelat onsh pFeatures =
      new FosnrRelat onsh pFeatures(
        t etLabels = t etLabelMaps,
        userRelat onsh pS ce = userRelat onsh pS ce,
        statsRece ver = l braryStatsRece ver)
    val conversat onControlFeatures =
      new Conversat onControlFeatures(
        relat onsh pFeatures,
         nv edToConversat onRepo,
        l braryStatsRece ver
      )
    val exclus veT etFeatures =
      new Exclus veT etFeatures(userRelat onsh pS ce, l braryStatsRece ver)

    val v e rSearchSafetyFeatures = new V e rSearchSafetyFeatures(
      UserSearchSafetyS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver),
      l braryStatsRece ver)

    val v e rSens  ve d aSett ngsFeatures = new V e rSens  ve d aSett ngsFeatures(
      UserSens  ve d aSett ngsS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver),
      l braryStatsRece ver)

    val tox cReplyF lterFeature = new Tox cReplyF lterFeature(statsRece ver = l braryStatsRece ver)

    val m s nfoPol cyS ce =
      M s nformat onPol cyS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver)
    val m s nfoPol cyFeatures =
      new M s nformat onPol cyFeatures(m s nfoPol cyS ce, stratoCl entStatsRece ver)

    val commun yT etFeatures = new Commun yT etFeaturesV2(
      commun  esS ce = Commun  esS ce.fromStrato(
        stratoCl ent,
        stratoCl entStatsRece ver
      )
    )

    val trustedFr endsT etFeatures = new TrustedFr endsFeatures(
      trustedFr endsS ce =
        TrustedFr endsS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver))

    val ed T etFeatures = new Ed T etFeatures(l braryStatsRece ver)

    val par yTest = new T etV s b l yL braryPar yTest(l braryStatsRece ver, stratoCl ent)

    val local zedNudgeS ce =
      Local zedNudgeS ce.fromLocal zat onS ce(local zat onS ce)
    val t etV s b l yNudgeFeatures =
      new T etV s b l yNudgeS ceWrapper(local zedNudgeS ce)

    val local zedL m edAct onsS ce =
      Local zedL m edAct onsS ce.fromLocal zat onS ce(local zat onS ce)

    { r: T etV s b l yRequest =>
      val elapsed = Stopwatch.start()
      var runSt chStartMs = 0L
      vfEng neCounter. ncr()

      val content d = T et d(r.t et. d)
      val v e r d = r.v e rContext.user d
      val author d = coreData.user d
      val t etGener c d aKeys = r.t et. d aRefs
        .getOrElse(Seq.empty)
        .flatMap {  d aRef =>
          Gener c d aKey.fromStr ngKey( d aRef.gener c d aKey)
        }

      val tpf =
        t etPerspect veFeatures.forT et(
          r.t et,
          v e r d,
          v s b l yDec derGates.enableFetchT etReportedPerspect ve())

      val featureMap =
        v s b l yL brary.featureMapBu lder(
          Seq(
            mutedKeywordFeatures.forT et(r.t et, v e r d, author d),
            v e rFeatures.forV e rContext(r.v e rContext),
            v e rSearchSafetyFeatures.forV e r d(v e r d),
            v e rSens  ve d aSett ngsFeatures.forV e r d(v e r d),
            relat onsh pFeatures.forAuthor d(author d, v e r d),
            fonsrRelat onsh pFeatures
              .forT etAndAuthor d(t et = r.t et, author d = author d, v e r d = v e r d),
            t etFeatures.forT et(r.t et),
            tpf,
             d aFeatures.for d aKeys(t etGener c d aKeys),
            authorFeatures.forAuthor d(author d),
            conversat onControlFeatures.forT et(r.t et, v e r d),
            _.w hConstantFeature(T et s nnerQuotedT et, r. s nnerQuotedT et),
            _.w hConstantFeature(T et sRet et, r. sRet et),
            _.w hConstantFeature(T et sS ceT et, r. sS ceT et),
            m s nfoPol cyFeatures.forT et(r.t et, r.v e rContext),
            exclus veT etFeatures.forT et(r.t et, r.v e rContext),
            commun yT etFeatures.forT et(r.t et, r.v e rContext),
            t et d a tadataFeatures
              .forT et(
                r.t et,
                t etGener c d aKeys,
                v s b l yDec derGates.enableFetchT et d a tadata()),
            trustedFr endsT etFeatures.forT et(r.t et, v e r d),
            ed T etFeatures.forT et(r.t et),
            tox cReplyF lterFeature.forT et(r.t et, v e r d),
          )
        )

      val languageCode = r.v e rContext.requestLanguageCode.getOrElse("en")
      val countryCode = r.v e rContext.requestCountryCode

      val response = v s b l yL brary
        .runRuleEng ne(
          content d,
          featureMap,
          r.v e rContext,
          r.safetyLevel
        )
        .map(
          T etV s b l yNudgeEnr ch nt(
            _,
            t etV s b l yNudgeFeatures,
            languageCode,
            countryCode))
        .map(verd ct => {
           f (v s b l yDec derGates.enableBackendL m edAct ons()) {
            L m edAct onsPol cyEnr ch nt(
              verd ct,
              local zedL m edAct onsS ce,
              languageCode,
              countryCode,
              l m edAct onsFeatureSw c s,
              l braryStatsRece ver)
          } else {
            verd ct
          }
        })
        .map(
          handleComposableV s b l yResult(
            _,
            v s b l yDec derGates.enable d a nterst  alCompos  on(),
            v s b l yDec derGates.enableBackendL m edAct ons()))
        .map(handle nnerQuotedT etV s b l yResult(_, r. s nnerQuotedT et))
        .map(tombstoneGenerator(_, languageCode))
        .map( nterst  alGenerator(_, languageCode))
        .map(Compl anceT etNot ceEnr ch nt(_, l braryStatsRece ver))
        .onSuccess(_ => {
          val overallStatMs = elapsed(). nM ll seconds
          vfLatencyOverallStat.add(overallStatMs)
          val runSt chEndMs = elapsed(). nM ll seconds
          vfLatencySt chRunStat.add(runSt chEndMs - runSt chStartMs)
        })
        .onSuccess(
          scr beV s b l yVerd ct(
            _,
            v s b l yDec derGates.enableVerd ctScr b ngTVL,
            verd ctLogger,
            r.v e rContext.user d,
            r.safetyLevel))

      runSt chStartMs = elapsed(). nM ll seconds
      val bu ldSt chStatMs = elapsed(). nM ll seconds
      vfLatencySt chBu ldStat.add(bu ldSt chStatMs)

       f (enablePar yTest()) {
        response.applyEffect { resp =>
          St ch.async(par yTest.runPar yTest(r, resp))
        }
      } else {
        response
      }
    }
  }

  def handleComposableV s b l yResult(
    result: V s b l yResult,
    enable d a nterst  alCompos  on: Boolean,
    enableBackendL m edAct ons: Boolean
  ): V s b l yResult = {
     f (result.secondaryVerd cts.nonEmpty || enableBackendL m edAct ons) {
      result.copy(verd ct = composeAct ons(
        result.verd ct,
        result.secondaryVerd cts,
        enable d a nterst  alCompos  on,
        enableBackendL m edAct ons))
    } else {
      result
    }
  }

  def handle nnerQuotedT etV s b l yResult(
    result: V s b l yResult,
     s nnerQuotedT et: Boolean
  ): V s b l yResult = {
    val newVerd ct: Act on =
      result.verd ct match {
        case  nterst  al(Reason.Nsfw | Reason.Nsfw d a, _, _)  f  s nnerQuotedT et =>
          Drop(Reason.Nsfw)
        case ComposableAct onsW h nterst  al(t et nterst  al)
             f  s nnerQuotedT et && (t et nterst  al.reason == Reason.Nsfw || t et nterst  al.reason == Reason.Nsfw d a) =>
          Drop(Reason.Nsfw)
        case verd ct => verd ct
      }

    result.copy(verd ct = newVerd ct)
  }

  def hasT etRules(safetyLevel: SafetyLevel): Boolean = RuleBase.hasT etRules(safetyLevel)

  def composeAct ons(
    pr mary: Act on,
    secondary: Seq[Act on],
    enable d a nterst  alCompos  on: Boolean,
    enableBackendL m edAct ons: Boolean
  ): Act on = {
     f (pr mary. sComposable && (secondary.nonEmpty || enableBackendL m edAct ons)) {
      val act ons = Seq[Act on] { pr mary } ++ secondary
      val  nterst  alOpt = Act on.getF rst nterst  al(act ons: _*)
      val soft ntervent onOpt = Act on.getF rstSoft ntervent on(act ons: _*)
      val downrankOpt = Act on.getF rstDownrankHo T  l ne(act ons: _*)
      val avo dOpt = Act on.getF rstAvo d(act ons: _*)
      val t etV s b l yNudgeOpt = Act on.getF rstT etV s b l yNudge(act ons: _*)

      val  d a nterst  alOpt = {
        val f rst d a nterst  alOpt = Act on.getF rst d a nterst  al(act ons: _*)
         f (enable d a nterst  alCompos  on &&  nterst  alOpt != f rst d a nterst  alOpt) {
          f rst d a nterst  alOpt
        } else {
          None
        }
      }

      val l m edEngage ntsOpt = enableBackendL m edAct ons match {
        case true => bu ldCompos eL m edEngage nts(Act on.getAllL m edEngage nts(act ons: _*))
        case false => Act on.getF rstL m edEngage nts(act ons: _*)
      }

      val abus veQual yOpt = {
         f (act ons.conta ns(Conversat onSect onAbus veQual y)) {
          So (Conversat onSect onAbus veQual y)
        } else {
          None
        }
      }

      val numAct ons =
        Seq[Opt on[_]](
           nterst  alOpt,
          soft ntervent onOpt,
          l m edEngage ntsOpt,
          downrankOpt,
          avo dOpt,
           d a nterst  alOpt,
          t etV s b l yNudgeOpt,
          abus veQual yOpt)
          .count(_. sDef ned)
       f (numAct ons > 1) {
        T et nterst  al(
           nterst  alOpt,
          soft ntervent onOpt,
          l m edEngage ntsOpt,
          downrankOpt,
          avo dOpt,
           d a nterst  alOpt,
          t etV s b l yNudgeOpt,
          abus veQual yOpt
        )
      } else {
         f (enableBackendL m edAct ons) {
          l m edEngage ntsOpt.getOrElse(pr mary)
        } else {
          pr mary
        }
      }
    } else {
      pr mary
    }
  }

  def scr beV s b l yVerd ct(
    result: V s b l yResult,
    enableVerd ctScr b ng: Gate[Un ],
    verd ctLogger: Verd ctLogger,
    v e r d: Opt on[Long],
    safetyLevel: SafetyLevel
  ): Un  =  f (enableVerd ctScr b ng()) {
    verd ctLogger.scr beVerd ct(
      v s b l yResult = result,
      v e r d = v e r d,
      safetyLevel = toThr ft(safetyLevel),
      vfL bType = VFL bType.T etV s b l yL brary)
  }

  def bu ldCompos eL m edEngage nts(
    l m edEngage nts: Seq[ sL m edEngage nts]
  ): Opt on[L m edEngage nts] = {
    l m edEngage nts. adOpt on.flatMap { l m edEngage nt =>
      val d st nctL m edAct ons = l m edEngage nts
        .collect({ case  sL m edEngage nts(So (pol cy), _) => pol cy.l m edAct ons })
        .flatten
        .foldR ght(Map.empty[L m edAct onType, L m edAct on])({ (l m edAct on, acc) =>
          acc + ((l m edAct on.l m edAct onType, l m edAct on))
        })
        .values
        .toSeq

       f (d st nctL m edAct ons.nonEmpty) {
        val l m edAct onsPol cy = So (L m edAct onsPol cy(d st nctL m edAct ons))
        So (L m edEngage nts(l m edEngage nt.getL m edEngage ntReason, l m edAct onsPol cy))
      } else {
        None
      }
    }
  }

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
}
