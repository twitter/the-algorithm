package com.tw ter.ho _m xer.funct onal_component.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Favor edByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Perspect veF lteredL kedByUser dsFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t  l neserv ce.T  l neServ ce
 mport com.tw ter.st ch.t  l neserv ce.T  l neServ ce.GetPerspect ves
 mport com.tw ter.t  l neserv ce.thr ftscala.Perspect veType
 mport com.tw ter.t  l neserv ce.thr ftscala.Perspect veType.Favor ed
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * F lter out unl ke edges from l ked-by t ets
 * Useful  f t  l kes co  from a cac  and because UTEG does not fully remove unl ke edges.
 */
@S ngleton
class Perspect veF lteredSoc alContextFeatureHydrator @ nject() (t  l neServ ce: T  l neServ ce)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("Perspect veF lteredSoc alContext")

  overr de val features: Set[Feature[_, _]] = Set(Perspect veF lteredL kedByUser dsFeature)

  pr vate val MaxCountUsers = 10
  pr vate val favor ePerspect veSet: Set[Perspect veType] = Set(Favor ed)

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadSt ch {
    val engag ngUser dtoT et d = cand dates.flatMap { cand date =>
      cand date.features
        .getOrElse(Favor edByUser dsFeature, Seq.empty).take(MaxCountUsers)
        .map(favor edBy => favor edBy -> cand date.cand date. d)
    }

    val quer es = engag ngUser dtoT et d.map {
      case (user d, t et d) =>
        GetPerspect ves.Query(user d = user d, t et d = t et d, types = favor ePerspect veSet)
    }

    St ch.collect(quer es.map(t  l neServ ce.getPerspect ve)).map { perspect veResults =>
      val val dUser dT et ds: Set[(Long, Long)] =
        quer es
          .z p(perspect veResults)
          .collect { case (query, perspect ve)  f perspect ve.favor ed => query }
          .map(query => (query.user d, query.t et d))
          .toSet

      cand dates.map { cand date =>
        val perspect veF lteredFavor edByUser ds: Seq[Long] = cand date.features
          .getOrElse(Favor edByUser dsFeature, Seq.empty).take(MaxCountUsers)
          .f lter { user d => val dUser dT et ds.conta ns((user d, cand date.cand date. d)) }

        FeatureMapBu lder()
          .add(Perspect veF lteredL kedByUser dsFeature, perspect veF lteredFavor edByUser ds)
          .bu ld()
      }
    }
  }
}
