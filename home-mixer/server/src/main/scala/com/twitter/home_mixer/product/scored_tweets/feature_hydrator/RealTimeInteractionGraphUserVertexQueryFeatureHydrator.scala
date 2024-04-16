package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.google. nject.na .Na d
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.RealT   nteract onGraphUserVertexCac 
 mport com.tw ter.ho _m xer.ut l.ObservedKeyValueResultHandler
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.servo.cac .ReadCac 
 mport com.tw ter.st ch.St ch
 mport com.tw ter.wtf.real_t  _ nteract on_graph.{thr ftscala =>  g}
 mport javax. nject. nject
 mport javax. nject.S ngleton

object RealT   nteract onGraphUserVertexQueryFeature
    extends Feature[P pel neQuery, Opt on[ g.UserVertex]]

@S ngleton
class RealT   nteract onGraphUserVertexQueryFeatureHydrator @ nject() (
  @Na d(RealT   nteract onGraphUserVertexCac ) cl ent: ReadCac [Long,  g.UserVertex],
  overr de val statsRece ver: StatsRece ver)
    extends QueryFeatureHydrator[P pel neQuery]
    w h ObservedKeyValueResultHandler {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("RealT   nteract onGraphUserVertex")

  overr de val features: Set[Feature[_, _]] = Set(RealT   nteract onGraphUserVertexQueryFeature)

  overr de val statScope: Str ng =  dent f er.toStr ng

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    val user d = query.getRequ redUser d

    St ch.callFuture(
      cl ent.get(Seq(user d)).map { results =>
        val feature = observedGet(key = So (user d), keyValueResult = results)
        FeatureMapBu lder()
          .add(RealT   nteract onGraphUserVertexQueryFeature, feature)
          .bu ld()
      }
    )
  }
}
