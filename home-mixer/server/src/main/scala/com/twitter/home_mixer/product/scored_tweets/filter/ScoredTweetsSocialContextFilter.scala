package com.tw ter.ho _m xer.product.scored_t ets.f lter

 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => st}

object ScoredT etsSoc alContextF lter extends F lter[P pel neQuery, T etCand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("ScoredT etsSoc alContext")

  // T ets from cand date s ces wh ch don't need gener c l ke/follow/top c proof
  pr vate val Allo dS ces: Set[st.SuggestType] = Set(
    st.SuggestType.RankedL stT et,
    st.SuggestType.Recom ndedTrendT et,
    st.SuggestType. d aT et
  )

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[F lterResult[T etCand date]] = {
    val val dT et ds = cand dates
      .f lter { cand date =>
        cand date.features.getOrElse( nNetworkFeature, true) ||
        cand date.features.getOrElse(SuggestTypeFeature, None).ex sts(Allo dS ces.conta ns) ||
        cand date.features.getOrElse( nReplyToT et dFeature, None). sDef ned ||
        hasL kedBySoc alContext(cand date.features) ||
        hasFollo dBySoc alContext(cand date.features) ||
        hasTop cSoc alContext(cand date.features)
      }.map(_.cand date. d).toSet

    val (kept, removed) =
      cand dates.map(_.cand date).part  on(cand date => val dT et ds.conta ns(cand date. d))

    St ch.value(F lterResult(kept = kept, removed = removed))
  }

  pr vate def hasL kedBySoc alContext(cand dateFeatures: FeatureMap): Boolean =
    cand dateFeatures
      .getOrElse(SGSVal dL kedByUser dsFeature, Seq.empty)
      .ex sts(
        cand dateFeatures
          .getOrElse(Perspect veF lteredL kedByUser dsFeature, Seq.empty)
          .toSet.conta ns
      )

  pr vate def hasFollo dBySoc alContext(cand dateFeatures: FeatureMap): Boolean =
    cand dateFeatures.getOrElse(SGSVal dFollo dByUser dsFeature, Seq.empty).nonEmpty

  pr vate def hasTop cSoc alContext(cand dateFeatures: FeatureMap): Boolean = {
    cand dateFeatures.getOrElse(Top c dSoc alContextFeature, None). sDef ned &&
    cand dateFeatures.getOrElse(Top cContextFunct onal yTypeFeature, None). sDef ned
  }
}
