package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.t et

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.contextual_ref.ContextualT etRefBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.t et.T etCand dateUrt emBu lder.T etCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date. sP nnedFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.t et.BaseEntry dToReplaceBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.t et.BaseT  l nesScore nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.t et.BaseT etH ghl ghtsBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseUrlBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.conversat on_annotat on.Conversat onAnnotat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot.ForwardP vot
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tombstone.Tombstone nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T etD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Badge
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Preroll tadata
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Promoted tadata
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case object T etCand dateUrt emBu lder {
  val T etCl entEvent nfoEle nt = "t et"
}

case class T etCand dateUrt emBu lder[Query <: P pel neQuery, Cand date <: BaseT etCand date](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Cand date],
  d splayType: T etD splayType = T et,
  entry dToReplaceBu lder: Opt on[BaseEntry dToReplaceBu lder[Query, Cand date]] = None,
  soc alContextBu lder: Opt on[BaseSoc alContextBu lder[Query, Cand date]] = None,
  h ghl ghtsBu lder: Opt on[BaseT etH ghl ghtsBu lder[Query, Cand date]] = None,
   nnerTombstone nfo: Opt on[Tombstone nfo] = None,
  t  l nesScore nfoBu lder: Opt on[BaseT  l nesScore nfoBu lder[Query, Cand date]] = None,
  hasModeratedRepl es: Opt on[Boolean] = None,
  forwardP vot: Opt on[ForwardP vot] = None,
   nnerForwardP vot: Opt on[ForwardP vot] = None,
  feedbackAct on nfoBu lder: Opt on[BaseFeedbackAct on nfoBu lder[Query, Cand date]] = None,
  promoted tadata: Opt on[Promoted tadata] = None,
  conversat onAnnotat on: Opt on[Conversat onAnnotat on] = None,
  contextualT etRefBu lder: Opt on[ContextualT etRefBu lder[Cand date]] = None,
  preroll tadata: Opt on[Preroll tadata] = None,
  replyBadge: Opt on[Badge] = None,
  dest nat onBu lder: Opt on[BaseUrlBu lder[Query, Cand date]] = None)
    extends Cand dateUrtEntryBu lder[Query, Cand date, T et em] {

  overr de def apply(
    p pel neQuery: Query,
    t etCand date: Cand date,
    cand dateFeatures: FeatureMap
  ): T et em = {
    val  sP nned = cand dateFeatures.getTry( sP nnedFeature).toOpt on

    T et em(
       d = t etCand date. d,
      entryNa space = T et em.T etEntryNa space,
      sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
      cl entEvent nfo = cl entEvent nfoBu lder(
        p pel neQuery,
        t etCand date,
        cand dateFeatures,
        So (T etCl entEvent nfoEle nt)),
      feedbackAct on nfo = feedbackAct on nfoBu lder.flatMap(
        _.apply(p pel neQuery, t etCand date, cand dateFeatures)),
       sP nned =  sP nned,
      entry dToReplace =
        entry dToReplaceBu lder.flatMap(_.apply(p pel neQuery, t etCand date, cand dateFeatures)),
      soc alContext =
        soc alContextBu lder.flatMap(_.apply(p pel neQuery, t etCand date, cand dateFeatures)),
      h ghl ghts =
        h ghl ghtsBu lder.flatMap(_.apply(p pel neQuery, t etCand date, cand dateFeatures)),
      d splayType = d splayType,
       nnerTombstone nfo =  nnerTombstone nfo,
      t  l nesScore nfo = t  l nesScore nfoBu lder
        .flatMap(_.apply(p pel neQuery, t etCand date, cand dateFeatures)),
      hasModeratedRepl es = hasModeratedRepl es,
      forwardP vot = forwardP vot,
       nnerForwardP vot =  nnerForwardP vot,
      promoted tadata = promoted tadata,
      conversat onAnnotat on = conversat onAnnotat on,
      contextualT etRef = contextualT etRefBu lder.flatMap(_.apply(t etCand date)),
      preroll tadata = preroll tadata,
      replyBadge = replyBadge,
      dest nat on =
        dest nat onBu lder.map(_.apply(p pel neQuery, t etCand date, cand dateFeatures))
    )
  }
}
