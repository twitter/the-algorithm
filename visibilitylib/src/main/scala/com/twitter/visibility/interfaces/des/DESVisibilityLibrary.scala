package com.tw ter.v s b l y. nterfaces.des

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.t ets.StratoT etLabelMaps
 mport com.tw ter.v s b l y.bu lder.t ets.T etFeatures
 mport com.tw ter.v s b l y.common.SafetyLabelMapS ce
 mport com.tw ter.v s b l y.features.Author d
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y. nterfaces.common.t ets.SafetyLabelMapFetc rType
 mport com.tw ter.v s b l y.models.Content d.T et d
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext

case class DESV s b l yRequest(
  t et: T et,
  v s b l ySurface: SafetyLevel,
  v e rContext: V e rContext)

object DESV s b l yL brary {
  type Type = DESV s b l yRequest => St ch[V s b l yResult]

  def apply(
    v s b l yL brary: V s b l yL brary,
    getLabelMap: SafetyLabelMapFetc rType,
    enableSh mFeatureHydrat on: Any => Boolean = _ => false
  ): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver
    val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")

    val t etLabelMap = new StratoT etLabelMaps(
      SafetyLabelMapS ce.fromSafetyLabelMapFetc r(getLabelMap))
    val t etFeatures = new T etFeatures(t etLabelMap, l braryStatsRece ver)

    { request: DESV s b l yRequest =>
      vfEng neCounter. ncr()

      val content d = T et d(request.t et. d)
      val author d = coreData.user d

      val featureMap =
        v s b l yL brary.featureMapBu lder(
          Seq(
            t etFeatures.forT et(request.t et),
            _.w hConstantFeature(Author d, Set(author d))
          )
        )

      val  sSh mFeatureHydrat onEnabled = enableSh mFeatureHydrat on()

       f ( sSh mFeatureHydrat onEnabled) {
        FeatureMap.resolve(featureMap, l braryStatsRece ver).flatMap { resolvedFeatureMap =>
          v s b l yL brary.runRuleEng ne(
            content d,
            resolvedFeatureMap,
            request.v e rContext,
            request.v s b l ySurface
          )
        }
      } else {
        v s b l yL brary.runRuleEng ne(
          content d,
          featureMap,
          request.v e rContext,
          request.v s b l ySurface
        )
      }
    }
  }
}
