package com.tw ter.product_m xer.component_l brary.feature_hydrator.query. mpressed_t ets

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes. mpress onstore.thr ftscala. mpress onL st
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Query Feature to store  ds of t  t ets  mpressed by t  user.
 */
case object  mpressedT ets extends FeatureW hDefaultOnFa lure[P pel neQuery, Seq[Long]] {
  overr de val defaultValue: Seq[Long] = Seq.empty
}

/**
 * Enr ch t  query w h a l st of t et  ds that t  user has already seen.
 */
@S ngleton
case class  mpressedT etsQueryFeatureHydrator @ nject() (
  t et mpress onStore: ReadableStore[Long,  mpress onL st])
    extends QueryFeatureHydrator[P pel neQuery] {
  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("T etsToExclude")

  overr de val features: Set[Feature[_, _]] = Set( mpressedT ets)

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    query.getOpt onalUser d match {
      case So (user d) =>
        val featureMapResult: Future[FeatureMap] = t et mpress onStore
          .get(user d).map {  mpress onL stOpt =>
            val t et dsOpt = for {
               mpress onL st <-  mpress onL stOpt
               mpress ons <-  mpress onL st. mpress ons
            } y eld {
               mpress ons.map(_.t et d)
            }
            val t et ds = t et dsOpt.getOrElse(Seq.empty)
            FeatureMapBu lder().add( mpressedT ets, t et ds).bu ld()
          }
        St ch.callFuture(featureMapResult)
      // Non-logged- n users do not have user d, returns empty feature

      case None =>
        val featureMapResult = FeatureMapBu lder().add( mpressedT ets, Seq.empty).bu ld()
        St ch.value(featureMapResult)
    }
  }
}
