package com.tw ter.ho _m xer.funct onal_component.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Favor edByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Follo dByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dFollo dByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dL kedByUser dsFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.soc algraph.{thr ftscala => sg}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.soc algraph.Soc alGraph
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * T  hydrator takes l ked-by and follo d-by user  ds and c cks v a SGS that t  v e r  s
 * follow ng t  engager, that t  v e r  s not block ng t  engager, that t  engager  s not
 * block ng t  v e r, and that t  v e r has not muted t  engager.
 */
@S ngleton
class SGSVal dSoc alContextFeatureHydrator @ nject() (
  soc alGraph: Soc alGraph)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("SGSVal dSoc alContext")

  overr de val features: Set[Feature[_, _]] = Set(
    SGSVal dFollo dByUser dsFeature,
    SGSVal dL kedByUser dsFeature
  )

  pr vate val MaxCountUsers = 10

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadSt ch {
    val allSoc alContextUser ds =
      cand dates.flatMap { cand date =>
        cand date.features.getOrElse(Favor edByUser dsFeature, N l).take(MaxCountUsers) ++
          cand date.features.getOrElse(Follo dByUser dsFeature, N l).take(MaxCountUsers)
      }.d st nct

    getVal dUser ds(query.getRequ redUser d, allSoc alContextUser ds).map { val dUser ds =>
      cand dates.map { cand date =>
        val sgsF lteredL kedByUser ds =
          cand date.features
            .getOrElse(Favor edByUser dsFeature, N l).take(MaxCountUsers)
            .f lter(val dUser ds.conta ns)

        val sgsF lteredFollo dByUser ds =
          cand date.features
            .getOrElse(Follo dByUser dsFeature, N l).take(MaxCountUsers)
            .f lter(val dUser ds.conta ns)

        FeatureMapBu lder()
          .add(SGSVal dFollo dByUser dsFeature, sgsF lteredFollo dByUser ds)
          .add(SGSVal dL kedByUser dsFeature, sgsF lteredL kedByUser ds)
          .bu ld()
      }
    }
  }

  pr vate def getVal dUser ds(
    v e r d: Long,
    soc alProofUser ds: Seq[Long]
  ): St ch[Seq[Long]] = {
     f (soc alProofUser ds.nonEmpty) {
      val request = sg. dsRequest(
        relat onsh ps = Seq(
          sg.SrcRelat onsh p(
            v e r d,
            sg.Relat onsh pType.Follow ng,
            targets = So (soc alProofUser ds),
            hasRelat onsh p = true),
          sg.SrcRelat onsh p(
            v e r d,
            sg.Relat onsh pType.Block ng,
            targets = So (soc alProofUser ds),
            hasRelat onsh p = false),
          sg.SrcRelat onsh p(
            v e r d,
            sg.Relat onsh pType.BlockedBy,
            targets = So (soc alProofUser ds),
            hasRelat onsh p = false),
          sg.SrcRelat onsh p(
            v e r d,
            sg.Relat onsh pType.Mut ng,
            targets = So (soc alProofUser ds),
            hasRelat onsh p = false)
        ),
        pageRequest = So (sg.PageRequest(selectAll = So (true)))
      )
      soc alGraph. ds(request).map(_. ds)
    } else St ch.N l
  }
}
