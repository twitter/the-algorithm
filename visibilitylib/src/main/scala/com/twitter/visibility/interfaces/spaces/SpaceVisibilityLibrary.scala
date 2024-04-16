package com.tw ter.v s b l y. nterfaces.spaces

 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.common.MutedKeywordFeatures
 mport com.tw ter.v s b l y.bu lder.spaces.SpaceFeatures
 mport com.tw ter.v s b l y.bu lder.spaces.StratoSpaceLabelMaps
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.bu lder.users.Relat onsh pFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rFeatures
 mport com.tw ter.v s b l y.common._
 mport com.tw ter.v s b l y.common.st ch.St ch lpers
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.models.Content d.Space d
 mport com.tw ter.v s b l y.models.Content d.SpacePlusUser d
 mport com.tw ter.v s b l y.rules.Evaluat onContext
 mport com.tw ter.v s b l y.rules.prov ders.Prov dedEvaluat onContext
 mport com.tw ter.v s b l y.rules.ut ls.Sh mUt ls

object SpaceV s b l yL brary {
  type Type = SpaceV s b l yRequest => St ch[V s b l yResult]

  def apply(
    v s b l yL brary: V s b l yL brary,
    stratoCl ent: StratoCl ent,
    userS ce: UserS ce,
    userRelat onsh pS ce: UserRelat onsh pS ce,
    enableVfFeatureHydrat onSpaceSh m: Gate[Un ] = Gate.False
  ): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver
    val stratoCl entStatsRece ver = v s b l yL brary.statsRece ver.scope("strato")
    val vfLatencyStatsRece ver = v s b l yL brary.statsRece ver.scope("vf_latency")
    val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")

    val spaceLabelMaps = new StratoSpaceLabelMaps(
      SpaceSafetyLabelMapS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver),
      l braryStatsRece ver)
    val aud oSpaceS ce = Aud oSpaceS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver)

    val v e rFeatures = new V e rFeatures(userS ce, l braryStatsRece ver)
    val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)
    val relat onsh pFeatures =
      new Relat onsh pFeatures(userRelat onsh pS ce, l braryStatsRece ver)
    val mutedKeywordFeatures = new MutedKeywordFeatures(
      userS ce,
      userRelat onsh pS ce,
      KeywordMatc r.matc r(l braryStatsRece ver),
      l braryStatsRece ver,
      Gate.False
    )
    val spaceFeatures =
      new SpaceFeatures(
        spaceLabelMaps,
        authorFeatures,
        relat onsh pFeatures,
        mutedKeywordFeatures,
        aud oSpaceS ce)

    { r: SpaceV s b l yRequest =>
      vfEng neCounter. ncr()

      val  sVfFeatureHydrat onEnabled = enableVfFeatureHydrat onSpaceSh m()
      val v e r d = r.v e rContext.user d
      val author ds: Opt on[Seq[Long]] = r.spaceHostAndAdm nUser ds
      val content d = {
        (v e r d, author ds) match {
          case (So (v e r), So (authors))  f authors.conta ns(v e r) => Space d(r.space d)
          case _ => SpacePlusUser d(r.space d)
        }
      }

      val featureMap =
        v s b l yL brary.featureMapBu lder(
          Seq(
            spaceFeatures.forSpaceAndAuthor ds(r.space d, v e r d, author ds),
            v e rFeatures.forV e rContext(r.v e rContext),
          )
        )

      val resp =  f ( sVfFeatureHydrat onEnabled) {
        val evaluat onContext = Prov dedEvaluat onContext. njectRunt  Rules ntoEvaluat onContext(
          evaluat onContext = Evaluat onContext(
            r.safetyLevel,
            v s b l yL brary.getParams(r.v e rContext, r.safetyLevel),
            v s b l yL brary.statsRece ver)
        )

        val preF lteredFeatureMap =
          Sh mUt ls.preF lterFeatureMap(featureMap, r.safetyLevel, content d, evaluat onContext)

        FeatureMap
          .resolve(preF lteredFeatureMap, l braryStatsRece ver).flatMap { resolvedFeatureMap =>
            v s b l yL brary
              .runRuleEng ne(
                content d,
                resolvedFeatureMap,
                r.v e rContext,
                r.safetyLevel
              )
          }
      } else {
        v s b l yL brary
          .runRuleEng ne(
            content d,
            featureMap,
            r.v e rContext,
            r.safetyLevel
          )
      }

      St ch lpers.prof leSt ch(resp, Seq(vfLatencyStatsRece ver))
    }
  }
}
