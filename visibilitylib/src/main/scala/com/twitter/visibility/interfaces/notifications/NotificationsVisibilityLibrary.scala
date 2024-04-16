package com.tw ter.v s b l y. nterfaces.not f cat ons

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.not f cat onserv ce.model.not f cat on.Not f cat on
 mport com.tw ter.not f cat onserv ce.model.not f cat on.Not f cat onType
 mport com.tw ter.not f cat onserv ce.model.not f cat on.S mpleAct v yNot f cat on
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.t ets.Commun yNot f cat onFeatures
 mport com.tw ter.v s b l y.bu lder.t ets.Un nt onNot f cat onFeatures
 mport com.tw ter.v s b l y.bu lder.users.AuthorDev ceFeatures
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.bu lder.users.Relat onsh pFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rAdvancedF lter ngFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rFeatures
 mport com.tw ter.v s b l y.common.T etS ce
 mport com.tw ter.v s b l y.common.UserDev ceS ce
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.features.AuthorUserLabels
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.models.Content d.Not f cat on d
 mport com.tw ter.v s b l y.models.SafetyLevel.Not f cat onsWr erV2
 mport com.tw ter.v s b l y.models.V e rContext
 mport com.tw ter.v s b l y.rules.State.FeatureFa led
 mport com.tw ter.v s b l y.rules.State.M ss ngFeature
 mport com.tw ter.v s b l y.rules.Act on
 mport com.tw ter.v s b l y.rules.RuleResult
 mport com.tw ter.v s b l y.rules.{Allow => AllowAct on}

object Not f cat onsV s b l yL brary {
  type Type = Not f cat on => St ch[Not f cat onsF lter ngResponse]

  pr vate val AllowResponse: St ch[Not f cat onsF lter ngResponse] = St ch.value(Allow)

  def  sAppl cableOrgan cNot f cat onType(not f cat onType: Not f cat onType): Boolean = {
    Not f cat onType. sTlsAct v yType(not f cat onType) ||
    Not f cat onType. sReact onType(not f cat onType)
  }

  def apply(
    v s b l yL brary: V s b l yL brary,
    userS ce: UserS ce,
    userRelat onsh pS ce: UserRelat onsh pS ce,
    userDev ceS ce: UserDev ceS ce,
    t etS ce: T etS ce,
    enableSh mFeatureHydrat on: Gate[Un ] = Gate.False,
    enableCommun yT etHydrat on: Gate[Long] = Gate.False,
    enableUn nt onHydrat on: Gate[Long] = Gate.False,
  ): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver
    lazy val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")

    val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)
    val authorDev ceFeatures = new AuthorDev ceFeatures(userDev ceS ce, l braryStatsRece ver)
    val v e rFeatures = new V e rFeatures(userS ce, l braryStatsRece ver)
    val commun yNot f cat onFeatures =
      new Commun yNot f cat onFeatures(
        t etS ce,
        enableCommun yT etHydrat on,
        l braryStatsRece ver)

    val un nt onNot f cat onFeatures = new Un nt onNot f cat onFeatures(
      t etS ce = t etS ce,
      enableUn nt onHydrat on = enableUn nt onHydrat on,
      statsRece ver = l braryStatsRece ver
    )

    val v e rAdvancedF lter ngFeatures =
      new V e rAdvancedF lter ngFeatures(userS ce, l braryStatsRece ver)
    val relat onsh pFeatures =
      new Relat onsh pFeatures(userRelat onsh pS ce, l braryStatsRece ver)

    val  sSh mFeatureHydrat onEnabled = enableSh mFeatureHydrat on()

    def runRuleEng ne(
      v s b l yL brary: V s b l yL brary,
      cand date: Not f cat on
    ): St ch[V s b l yResult] = {
      cand date match {
        case not f cat on: S mpleAct v yNot f cat on[_] =>
          vfEng neCounter. ncr()

          val featureMap = v s b l yL brary.featureMapBu lder(
            Seq(
              v e rFeatures.forV e r d(So (not f cat on.target)),
              v e rAdvancedF lter ngFeatures.forV e r d(So (not f cat on.target)),
              authorFeatures.forAuthor d(not f cat on.subject d),
              authorDev ceFeatures.forAuthor d(not f cat on.subject d),
              relat onsh pFeatures
                .forAuthor d(not f cat on.subject d, So (not f cat on.target)),
              commun yNot f cat onFeatures.forNot f cat on(not f cat on),
              un nt onNot f cat onFeatures.forNot f cat on(not f cat on)
            )
          )

           f ( sSh mFeatureHydrat onEnabled) {
            FeatureMap.resolve(featureMap, l braryStatsRece ver).flatMap { resolvedFeatureMap =>
              v s b l yL brary.runRuleEng ne(
                content d =
                featureMap = resolvedFeatureMap,
                v e rContext =
                  V e rContext.fromContextW hV e r dFallback(So (not f cat on.target)),
                safetyLevel = Not f cat onsWr erV2
              )
            }
          } else {
            v s b l yL brary.runRuleEng ne(
              content d = Not f cat on d(t et d = None),
              featureMap = featureMap,
              v e rContext =
                V e rContext.fromContextW hV e r dFallback(So (not f cat on.target)),
              safetyLevel = Not f cat onsWr erV2
            )
          }
      }
    }

    {
      case cand date  f  sAppl cableOrgan cNot f cat onType(cand date.not f cat onType) =>
        runRuleEng ne(v s b l yL brary, cand date)
          .flatMap(fa lCloseForFa lures(_, l braryStatsRece ver))
      case _ =>
        AllowResponse
    }
  }

  def fa lCloseForFa lures(
    v s b l yResult: V s b l yResult,
    stats: StatsRece ver
  ): St ch[Not f cat onsF lter ngResponse] = {
    lazy val vfEng neSuccess = stats.counter("vf_eng ne_success")
    lazy val vfEng neFa lures = stats.counter("vf_eng ne_fa lures")
    lazy val vfEng neFa luresM ss ng = stats.scope("vf_eng ne_fa lures").counter("m ss ng")
    lazy val vfEng neFa luresFa led = stats.scope("vf_eng ne_fa lures").counter("fa led")
    lazy val vfEng neF ltered = stats.counter("vf_eng ne_f ltered")

    val  sFa ledOrM ss ngFeature: RuleResult => Boolean = {
      case RuleResult(_, FeatureFa led(features)) =>
        !(features.conta ns(AuthorUserLabels) && features.s ze == 1)
      case RuleResult(_, M ss ngFeature(_)) => true
      case _ => false
    }

    val fa ledRuleResults =
      v s b l yResult.ruleResultMap.values.f lter( sFa ledOrM ss ngFeature(_))

    val (fa ledFeatures, m ss ngFeatures) = fa ledRuleResults.part  on {
      case RuleResult(_, FeatureFa led(_)) => true
      case RuleResult(_, M ss ngFeature(_)) => false
      case _ => false
    }

    val fa ledOrM ss ngFeatures = fa ledRuleResults
      .collect {
        case RuleResult(_, FeatureFa led(features)) => features.keySet
        case RuleResult(_, M ss ngFeature(features)) => features
      }.toSet.flatten

    v s b l yResult.verd ct match {
      case AllowAct on  f fa ledOrM ss ngFeatures. sEmpty =>
        vfEng neSuccess. ncr()
        AllowResponse
      case AllowAct on  f fa ledOrM ss ngFeatures.nonEmpty =>
        vfEng neFa lures. ncr()
         f (m ss ngFeatures.nonEmpty) {
          vfEng neFa luresM ss ng. ncr()
        }
         f (fa ledFeatures.nonEmpty) {
          vfEng neFa luresFa led. ncr()
        }

        St ch.value(Fa led(fa ledOrM ss ngFeatures))
      case act on: Act on =>
        vfEng neF ltered. ncr()
        St ch.value(F ltered(act on))
    }
  }
}
