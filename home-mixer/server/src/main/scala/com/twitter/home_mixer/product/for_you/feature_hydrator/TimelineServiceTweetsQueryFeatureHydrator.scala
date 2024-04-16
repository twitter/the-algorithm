package com.tw ter.ho _m xer.product.for_ .feature_hydrator

 mport com.tw ter.ho _m xer.marshaller.t  l nes.Dev ceContextMarshaller
 mport com.tw ter.ho _m xer.model.Ho Features.T  l neServ ceT etsFeature
 mport com.tw ter.ho _m xer.model.request.Dev ceContext
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t  l neserv ce.T  l neServ ce
 mport com.tw ter.t  l neserv ce.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class T  l neServ ceT etsQueryFeatureHydrator @ nject() (
  t  l neServ ce: T  l neServ ce,
  dev ceContextMarshaller: Dev ceContextMarshaller)
    extends QueryFeatureHydrator[P pel neQuery w h HasDev ceContext] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("T  l neServ ceT ets")

  overr de val features: Set[Feature[_, _]] = Set(T  l neServ ceT etsFeature)

  pr vate val MaxT  l neServ ceT ets = 200

  overr de def hydrate(query: P pel neQuery w h HasDev ceContext): St ch[FeatureMap] = {
    val dev ceContext = query.dev ceContext.getOrElse(Dev ceContext.Empty)

    val t  l neQueryOpt ons = t.T  l neQueryOpt ons(
      contextualUser d = query.cl entContext.user d,
      dev ceContext = So (dev ceContextMarshaller(dev ceContext, query.cl entContext))
    )

    val t  l neServ ceQuery = t.T  l neQuery(
      t  l neType = t.T  l neType.Ho ,
      t  l ne d = query.getRequ redUser d,
      maxCount = MaxT  l neServ ceT ets.toShort,
      cursor2 = None,
      opt ons = So (t  l neQueryOpt ons),
      t  l ne d2 = query.cl entContext.user d.map(t.T  l ne d(t.T  l neType.Ho , _, None)),
    )

    t  l neServ ce.getT  l ne(t  l neServ ceQuery).map { t  l ne =>
      val t ets = t  l ne.entr es.collect {
        case t.T  l neEntry.T et(t et) => t et.status d
      }

      FeatureMapBu lder().add(T  l neServ ceT etsFeature, t ets).bu ld()
    }
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.7)
  )
}
