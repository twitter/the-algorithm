package com.tw ter.v s b l y. nterfaces.dms

 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.dms.DmConversat onFeatures
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.common.dm_s ces.DmConversat onS ce
 mport com.tw ter.v s b l y.common.st ch.St ch lpers
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.models.Content d.DmConversat on d
 mport com.tw ter.v s b l y.rules.Drop
 mport com.tw ter.v s b l y.rules.Evaluat onContext
 mport com.tw ter.v s b l y.rules.Reason
 mport com.tw ter.v s b l y.rules.RuleBase
 mport com.tw ter.v s b l y.rules.prov ders.Prov dedEvaluat onContext
 mport com.tw ter.v s b l y.rules.ut ls.Sh mUt ls

object DmConversat onV s b l yL brary {
  type Type = DmConversat onV s b l yRequest => St ch[V s b l yResult]

  def apply(
    v s b l yL brary: V s b l yL brary,
    stratoCl ent: StratoCl ent,
    userS ce: UserS ce,
    enableVfFeatureHydrat on nSh m: Gate[Un ] = Gate.False
  ): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver
    val stratoCl entStatsRece ver = v s b l yL brary.statsRece ver.scope("strato")
    val vfLatencyStatsRece ver = v s b l yL brary.statsRece ver.scope("vf_latency")
    val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")

    val dmConversat onS ce =
      DmConversat onS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver)
    val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)
    val dmConversat onFeatures = new DmConversat onFeatures(dmConversat onS ce, authorFeatures)

    { req: DmConversat onV s b l yRequest =>
      val dmConversat on d = req.dmConversat on d
      val content d = DmConversat on d(dmConversat on d)
      val safetyLevel = req.safetyLevel

       f (!RuleBase.hasDmConversat onRules(safetyLevel)) {
        St ch.value(V s b l yResult(content d = content d, verd ct = Drop(Reason.Unspec f ed)))
      } else {
        vfEng neCounter. ncr()

        val v e rContext = req.v e rContext
        val v e r d = v e rContext.user d
        val  sVfFeatureHydrat onEnabled: Boolean =
          enableVfFeatureHydrat on nSh m()

        val featureMap = v s b l yL brary.featureMapBu lder(
          Seq(dmConversat onFeatures.forDmConversat on d(dmConversat on d, v e r d)))

        val resp =  f ( sVfFeatureHydrat onEnabled) {
          val evaluat onContext = Prov dedEvaluat onContext. njectRunt  Rules ntoEvaluat onContext(
            evaluat onContext = Evaluat onContext(
              safetyLevel,
              v s b l yL brary.getParams(v e rContext, safetyLevel),
              v s b l yL brary.statsRece ver)
          )

          val preF lteredFeatureMap =
            Sh mUt ls.preF lterFeatureMap(featureMap, safetyLevel, content d, evaluat onContext)

          FeatureMap.resolve(preF lteredFeatureMap, l braryStatsRece ver).flatMap {
            resolvedFeatureMap =>
              v s b l yL brary
                .runRuleEng ne(
                  content d,
                  resolvedFeatureMap,
                  v e rContext,
                  safetyLevel
                )
          }
        } else {
          v s b l yL brary
            .runRuleEng ne(
              content d,
              featureMap,
              v e rContext,
              safetyLevel
            )
        }

        St ch lpers.prof leSt ch(resp, Seq(vfLatencyStatsRece ver))
      }
    }
  }
}
