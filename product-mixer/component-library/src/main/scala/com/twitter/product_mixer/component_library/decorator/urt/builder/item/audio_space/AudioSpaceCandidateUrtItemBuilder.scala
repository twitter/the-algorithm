package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.aud o_space

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.aud o_space.Aud oSpaceCand dateUrt emBu lder.Aud oSpaceCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.Aud oSpaceCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.aud o_space.Aud oSpace em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Aud oSpaceCand dateUrt emBu lder {
  val Aud oSpaceCl entEvent nfoEle nt: Str ng = "aud ospace"
}

case class Aud oSpaceCand dateUrt emBu lder[-Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Un versalNoun[Any]],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, Un versalNoun[Any]]
  ] = None)
    extends Cand dateUrtEntryBu lder[Query, Aud oSpaceCand date, Aud oSpace em] {

  overr de def apply(
    query: Query,
    aud oSpaceCand date: Aud oSpaceCand date,
    cand dateFeatures: FeatureMap
  ): Aud oSpace em = Aud oSpace em(
     d = aud oSpaceCand date. d,
    sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
    cl entEvent nfo = cl entEvent nfoBu lder(
      query,
      aud oSpaceCand date,
      cand dateFeatures,
      So (Aud oSpaceCl entEvent nfoEle nt)),
    feedbackAct on nfo =
      feedbackAct on nfoBu lder.flatMap(_.apply(query, aud oSpaceCand date, cand dateFeatures))
  )
}
