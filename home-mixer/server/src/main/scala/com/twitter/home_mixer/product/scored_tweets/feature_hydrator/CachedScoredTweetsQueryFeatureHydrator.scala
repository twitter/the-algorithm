package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Cac dScoredT ets
 mport com.tw ter.ho _m xer.{thr ftscala => hmt}
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.servo.cac .TtlCac 
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.T  

 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Fetch scored T ets from cac  and exclude t  exp red ones
 */
@S ngleton
case class Cac dScoredT etsQueryFeatureHydrator @ nject() (
  scoredT etsCac : TtlCac [Long, hmt.ScoredT etsResponse])
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("Cac dScoredT ets")

  overr de val features: Set[Feature[_, _]] = Set(Cac dScoredT etsFeature)

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    val user d = query.getRequ redUser d
    val t etScoreTtl = query.params(Cac dScoredT ets.TTLParam)

    St ch.callFuture(scoredT etsCac .get(Seq(user d))).map { keyValueResult =>
      keyValueResult(user d) match {
        case Return(cac dCand datesOpt) =>
          val cac dScoredT ets = cac dCand datesOpt.map(_.scoredT ets).getOrElse(Seq.empty)
          val nonExp redT ets = cac dScoredT ets.f lter { t et =>
            t et.lastScoredT  stampMs.ex sts(T  .fromM ll seconds(_).unt lNow < t etScoreTtl)
          }
          FeatureMapBu lder().add(Cac dScoredT etsFeature, nonExp redT ets).bu ld()
        case Throw(except on) => throw except on
      }
    }
  }
}
