package com.tw ter.v s b l y. nterfaces.cards

 mport com.tw ter.appsec.san  zat on.URLSafety
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.{thr ftscala => t etyp ethr ft}
 mport com.tw ter.ut l.Stopwatch
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.t ets.Commun yT etFeatures
 mport com.tw ter.v s b l y.bu lder.t ets.Commun yT etFeaturesV2
 mport com.tw ter.v s b l y.bu lder.t ets.N lT etLabelMaps
 mport com.tw ter.v s b l y.bu lder.t ets.T etFeatures
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.bu lder.users.Relat onsh pFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rFeatures
 mport com.tw ter.v s b l y.common.Commun  esS ce
 mport com.tw ter.v s b l y.common.User d
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y.features.Card sPoll
 mport com.tw ter.v s b l y.features.CardUr Host
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.models.Content d.Card d
 mport com.tw ter.v s b l y.models.V e rContext

object CardV s b l yL brary {
  type Type = CardV s b l yRequest => St ch[V s b l yResult]

  pr vate[t ] def getAuthorFeatures(
    author dOpt: Opt on[Long],
    authorFeatures: AuthorFeatures
  ): FeatureMapBu lder => FeatureMapBu lder = {
    author dOpt match {
      case So (author d) => authorFeatures.forAuthor d(author d)
      case _ => authorFeatures.forNoAuthor()
    }
  }

  pr vate[t ] def getCardUr Features(
    cardUr : Str ng,
    enableCardV s b l yL braryCardUr Pars ng: Boolean,
    trackCardUr Host: Opt on[Str ng] => Un 
  ): FeatureMapBu lder => FeatureMapBu lder = {
     f (enableCardV s b l yL braryCardUr Pars ng) {
      val safeCardUr Host = URLSafety.getHostSafe(cardUr )
      trackCardUr Host(safeCardUr Host)

      _.w hConstantFeature(CardUr Host, safeCardUr Host)
    } else {
       dent y
    }
  }

  pr vate[t ] def getRelat onsh pFeatures(
    author dOpt: Opt on[Long],
    v e r dOpt: Opt on[Long],
    relat onsh pFeatures: Relat onsh pFeatures
  ): FeatureMapBu lder => FeatureMapBu lder = {
    author dOpt match {
      case So (author d) => relat onsh pFeatures.forAuthor d(author d, v e r dOpt)
      case _ => relat onsh pFeatures.forNoAuthor()
    }
  }

  pr vate[t ] def getT etFeatures(
    t etOpt: Opt on[t etyp ethr ft.T et],
    t etFeatures: T etFeatures
  ): FeatureMapBu lder => FeatureMapBu lder = {
    t etOpt match {
      case So (t et) => t etFeatures.forT et(t et)
      case _ =>  dent y
    }
  }

  pr vate[t ] def getCommun yFeatures(
    t etOpt: Opt on[t etyp ethr ft.T et],
    v e rContext: V e rContext,
    commun yT etFeatures: Commun yT etFeatures
  ): FeatureMapBu lder => FeatureMapBu lder = {
    t etOpt match {
      case So (t et) => commun yT etFeatures.forT et(t et, v e rContext)
      case _ =>  dent y
    }
  }

  def apply(
    v s b l yL brary: V s b l yL brary,
    userS ce: UserS ce = UserS ce.empty,
    userRelat onsh pS ce: UserRelat onsh pS ce = UserRelat onsh pS ce.empty,
    commun  esS ce: Commun  esS ce = Commun  esS ce.empty,
    enableVfPar yTest: Gate[Un ] = Gate.False,
    enableVfFeatureHydrat on: Gate[Un ] = Gate.False,
    dec der: Dec der
  ): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver
    val vfLatencyOverallStat = l braryStatsRece ver.stat("vf_latency_overall")
    val vfLatencySt chBu ldStat = l braryStatsRece ver.stat("vf_latency_st ch_bu ld")
    val vfLatencySt chRunStat = l braryStatsRece ver.stat("vf_latency_st ch_run")
    val cardUr Stats = l braryStatsRece ver.scope("card_ur ")
    val v s b l yDec derGates = V s b l yDec derGates(dec der)

    val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)
    val v e rFeatures = new V e rFeatures(userS ce, l braryStatsRece ver)
    val t etFeatures = new T etFeatures(N lT etLabelMaps, l braryStatsRece ver)
    val commun yT etFeatures = new Commun yT etFeaturesV2(
      commun  esS ce = commun  esS ce,
    )
    val relat onsh pFeatures =
      new Relat onsh pFeatures(userRelat onsh pS ce, l braryStatsRece ver)
    val par yTest = new CardV s b l yL braryPar yTest(l braryStatsRece ver)

    { r: CardV s b l yRequest =>
      val elapsed = Stopwatch.start()
      var runSt chStartMs = 0L

      val v e r d: Opt on[User d] = r.v e rContext.user d

      val featureMap =
        v s b l yL brary
          .featureMapBu lder(
            Seq(
              v e rFeatures.forV e r d(v e r d),
              getAuthorFeatures(r.author d, authorFeatures),
              getCardUr Features(
                cardUr  = r.cardUr ,
                enableCardV s b l yL braryCardUr Pars ng =
                  v s b l yDec derGates.enableCardV s b l yL braryCardUr Pars ng(),
                trackCardUr Host = { safeCardUr Host: Opt on[Str ng] =>
                   f (safeCardUr Host. sEmpty) {
                    cardUr Stats.counter("empty"). ncr()
                  }
                }
              ),
              getCommun yFeatures(r.t etOpt, r.v e rContext, commun yT etFeatures),
              getRelat onsh pFeatures(r.author d, r.v e rContext.user d, relat onsh pFeatures),
              getT etFeatures(r.t etOpt, t etFeatures),
              _.w hConstantFeature(Card sPoll, r. sPollCardType)
            )
          )

      val response = v s b l yL brary
        .runRuleEng ne(
          Card d(r.cardUr ),
          featureMap,
          r.v e rContext,
          r.safetyLevel
        )
        .onSuccess(_ => {
          val overallStatMs = elapsed(). nM ll seconds
          vfLatencyOverallStat.add(overallStatMs)
          val runSt chEndMs = elapsed(). nM ll seconds
          vfLatencySt chRunStat.add(runSt chEndMs - runSt chStartMs)
        })

      runSt chStartMs = elapsed(). nM ll seconds
      val bu ldSt chStatMs = elapsed(). nM ll seconds
      vfLatencySt chBu ldStat.add(bu ldSt chStatMs)

      lazy val hydratedFeatureResponse: St ch[V s b l yResult] =
        FeatureMap.resolve(featureMap, l braryStatsRece ver).flatMap { resolvedFeatureMap =>
          v s b l yL brary.runRuleEng ne(
            Card d(r.cardUr ),
            resolvedFeatureMap,
            r.v e rContext,
            r.safetyLevel
          )
        }

      val  sVfPar yTestEnabled = enableVfPar yTest()
      val  sVfFeatureHydrat onEnabled = enableVfFeatureHydrat on()

       f (! sVfPar yTestEnabled && ! sVfFeatureHydrat onEnabled) {
        response
      } else  f ( sVfPar yTestEnabled && ! sVfFeatureHydrat onEnabled) {
        response.applyEffect { resp =>
          St ch.async(par yTest.runPar yTest(hydratedFeatureResponse, resp))
        }
      } else {
        hydratedFeatureResponse
      }
    }
  }
}
