package com.tw ter.ho _m xer.funct onal_component.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.D sm ss nfoFeature
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.t  l nem xer.cl ents.manhattan. nject on toryCl ent
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nem xer.cl ents.manhattan.D sm ss nfo
 mport com.tw ter.t  l neserv ce.suggests.thr ftscala.SuggestType
 mport javax. nject. nject
 mport javax. nject.S ngleton

object D sm ss nfoQueryFeatureHydrator {
  val D sm ss nfoSuggestTypes = Seq(SuggestType.WhoToFollow)
}

@S ngleton
case class D sm ss nfoQueryFeatureHydrator @ nject() (
  d sm ss nfoCl ent:  nject on toryCl ent)
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("D sm ss nfo")

  overr de val features: Set[Feature[_, _]] = Set(D sm ss nfoFeature)

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] =
    St ch.callFuture {
      d sm ss nfoCl ent
        .readD sm ss nfoEntr es(
          query.getRequ redUser d,
          D sm ss nfoQueryFeatureHydrator.D sm ss nfoSuggestTypes).map { response =>
          val d sm ss nfoMap = response.mapValues(D sm ss nfo.fromThr ft)
          FeatureMapBu lder().add(D sm ss nfoFeature, d sm ss nfoMap).bu ld()
        }
    }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.8, 50, 60, 60)
  )
}
