package com.tw ter.v s b l y. nterfaces.not f cat ons

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Throwables
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.t ets.Commun yNot f cat onFeatures
 mport com.tw ter.v s b l y.bu lder.t ets.Un nt onNot f cat onFeatures
 mport com.tw ter.v s b l y.bu lder.users.AuthorDev ceFeatures
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.bu lder.users.Relat onsh pFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rAdvancedF lter ngFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rFeatures
 mport com.tw ter.v s b l y.common.UserDev ceS ce
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.features.AuthorUserLabels
 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.models.V e rContext
 mport com.tw ter.v s b l y.rules.State.FeatureFa led
 mport com.tw ter.v s b l y.rules.State.M ss ngFeature
 mport com.tw ter.v s b l y.rules.Act on
 mport com.tw ter.v s b l y.rules.RuleResult
 mport com.tw ter.v s b l y.rules.{Allow => AllowAct on}

object Not f cat onsPlatformV s b l yL brary {
  type Not f cat onsPlatformVFType =
    Not f cat onVFRequest => St ch[Not f cat onsPlatformF lter ngResponse]

  pr vate val AllowResponse: St ch[Not f cat onsPlatformF lter ngResponse] =
    St ch.value(AllowVerd ct)

  def apply(
    userS ce: UserS ce,
    userRelat onsh pS ce: UserRelat onsh pS ce,
    userDev ceS ce: UserDev ceS ce,
    v s b l yL brary: V s b l yL brary,
    enableSh mFeatureHydrat on: Gate[Un ] = Gate.False
  ): Not f cat onsPlatformVFType = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver
    val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")

    val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)
    val authorDev ceFeatures = new AuthorDev ceFeatures(userDev ceS ce, l braryStatsRece ver)
    val v e rFeatures = new V e rFeatures(userS ce, l braryStatsRece ver)

    val v e rAdvancedF lter ngFeatures =
      new V e rAdvancedF lter ngFeatures(userS ce, l braryStatsRece ver)
    val relat onsh pFeatures =
      new Relat onsh pFeatures(userRelat onsh pS ce, l braryStatsRece ver)

    val  sSh mFeatureHydrat onEnabled = enableSh mFeatureHydrat on()

    def runRuleEng ne(cand date: Not f cat onVFRequest): St ch[V s b l yResult] = {
      val featureMap =
        v s b l yL brary.featureMapBu lder(
          Seq(
            v e rFeatures.forV e r d(So (cand date.rec p ent d)),
            v e rAdvancedF lter ngFeatures.forV e r d(So (cand date.rec p ent d)),
            authorFeatures.forAuthor d(cand date.subject. d),
            authorDev ceFeatures.forAuthor d(cand date.subject. d),
            relat onsh pFeatures.forAuthor d(cand date.subject. d, So (cand date.rec p ent d)),
            Commun yNot f cat onFeatures.ForNonCommun yT etNot f cat on,
            Un nt onNot f cat onFeatures.ForNonUn nt onNot f cat onFeatures
          )
        )

      vfEng neCounter. ncr()

       f ( sSh mFeatureHydrat onEnabled) {
        FeatureMap.resolve(featureMap, l braryStatsRece ver).flatMap { resolvedFeatureMap =>
          v s b l yL brary.runRuleEng ne(
            content d = cand date.subject,
            featureMap = resolvedFeatureMap,
            v e rContext =
              V e rContext.fromContextW hV e r dFallback(So (cand date.rec p ent d)),
            safetyLevel = cand date.safetyLevel
          )
        }
      } else {
        v s b l yL brary.runRuleEng ne(
          content d = cand date.subject,
          featureMap = featureMap,
          v e rContext =
            V e rContext.fromContextW hV e r dFallback(So (cand date.rec p ent d)),
          safetyLevel = cand date.safetyLevel
        )
      }
    }

    {
      case cand date: Not f cat onVFRequest =>
        runRuleEng ne(cand date).flatMap(fa lCloseForFa lures(_, l braryStatsRece ver))
      case _ =>
        AllowResponse
    }
  }

  pr vate def fa lCloseForFa lures(
    v s b l yResult: V s b l yResult,
    stats: StatsRece ver
  ): St ch[Not f cat onsPlatformF lter ngResponse] = {
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

    val fa ledOrM ss ngFeatures: Map[Feature[_], Str ng] = fa ledRuleResults
      .collect {
        case RuleResult(_, FeatureFa led(features)) =>
          features.map {
            case (feature: Feature[_], throwable: Throwable) =>
              feature -> Throwables.mkStr ng(throwable).mkStr ng(" -> ")
          }.toSet
        case RuleResult(_, M ss ngFeature(features)) => features.map(_ -> "Feature m ss ng.")
      }.flatten.toMap

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

        St ch.value(Fa ledVerd ct(fa ledOrM ss ngFeatures))
      case act on: Act on =>
        vfEng neF ltered. ncr()
        St ch.value(F lteredVerd ct(act on))
    }
  }
}
