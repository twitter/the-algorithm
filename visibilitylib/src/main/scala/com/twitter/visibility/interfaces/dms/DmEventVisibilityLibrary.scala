package com.tw ter.v s b l y. nterfaces.dms

 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.dms.DmConversat onFeatures
 mport com.tw ter.v s b l y.bu lder.dms.DmEventFeatures
 mport com.tw ter.v s b l y.bu lder.dms. nval dDmEventFeatureExcept on
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.common.dm_s ces.DmConversat onS ce
 mport com.tw ter.v s b l y.common.dm_s ces.DmEventS ce
 mport com.tw ter.v s b l y.common.st ch.St ch lpers
 mport com.tw ter.v s b l y.models.Content d.DmEvent d
 mport com.tw ter.v s b l y.rules.Drop
 mport com.tw ter.v s b l y.rules.Reason
 mport com.tw ter.v s b l y.rules.RuleBase

object DmEventV s b l yL brary {
  type Type = DmEventV s b l yRequest => St ch[V s b l yResult]

  def apply(
    v s b l yL brary: V s b l yL brary,
    stratoCl ent: StratoCl ent,
    userS ce: UserS ce
  ): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver
    val stratoCl entStatsRece ver = v s b l yL brary.statsRece ver.scope("strato")
    val vfLatencyStatsRece ver = v s b l yL brary.statsRece ver.scope("vf_latency")
    val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")
    val dmConversat onS ce = {
      DmConversat onS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver)
    }
    val dmEventS ce = {
      DmEventS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver)
    }
    val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)
    val dmConversat onFeatures = new DmConversat onFeatures(dmConversat onS ce, authorFeatures)
    val dmEventFeatures =
      new DmEventFeatures(
        dmEventS ce,
        dmConversat onS ce,
        authorFeatures,
        dmConversat onFeatures,
        l braryStatsRece ver)

    { req: DmEventV s b l yRequest =>
      val dmEvent d = req.dmEvent d
      val content d = DmEvent d(dmEvent d)
      val safetyLevel = req.safetyLevel

       f (!RuleBase.hasDmEventRules(safetyLevel)) {
        St ch.value(V s b l yResult(content d = content d, verd ct = Drop(Reason.Unspec f ed)))
      } else {
        vfEng neCounter. ncr()

        val v e rContext = req.v e rContext
        val v e r dOpt = v e rContext.user d

        v e r dOpt match {
          case So (v e r d) =>
            val featureMap = v s b l yL brary.featureMapBu lder(
              Seq(dmEventFeatures.forDmEvent d(dmEvent d, v e r d)))

            val resp = v s b l yL brary
              .runRuleEng ne(
                content d,
                featureMap,
                v e rContext,
                safetyLevel
              )
            St ch lpers.prof leSt ch(resp, Seq(vfLatencyStatsRece ver))

          case None => St ch.except on( nval dDmEventFeatureExcept on("V e r  d  s m ss ng"))
        }
      }
    }
  }
}
