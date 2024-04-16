package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em. ssage

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em. ssage.CompactPromptCand dateUrt emStr ngCenterBu lder.CompactPromptCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.CompactPromptCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseStr
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.r chtext.BaseR chTextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage.CompactPrompt ssageContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssagePrompt em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object CompactPromptCand dateUrt emStr ngCenterBu lder {
  val CompactPromptCl entEvent nfoEle nt: Str ng = " ssage"
}

case class CompactPromptCand dateUrt emStr ngCenterBu lder[-Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, CompactPromptCand date],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, CompactPromptCand date]
  ] = None,
   aderTextBu lder: BaseStr[Query, CompactPromptCand date],
  bodyTextBu lder: Opt on[BaseStr[Query, CompactPromptCand date]] = None,
   aderR chTextBu lder: Opt on[BaseR chTextBu lder[Query, CompactPromptCand date]] = None,
  bodyR chTextBu lder: Opt on[BaseR chTextBu lder[Query, CompactPromptCand date]] = None)
    extends Cand dateUrtEntryBu lder[Query, CompactPromptCand date,  ssagePrompt em] {

  overr de def apply(
    query: Query,
    compactPromptCand date: CompactPromptCand date,
    cand dateFeatures: FeatureMap
  ):  ssagePrompt em =
     ssagePrompt em(
       d = compactPromptCand date. d.toStr ng,
      sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
      cl entEvent nfo = cl entEvent nfoBu lder(
        query,
        compactPromptCand date,
        cand dateFeatures,
        So (CompactPromptCl entEvent nfoEle nt)),
      feedbackAct on nfo = feedbackAct on nfoBu lder.flatMap(
        _.apply(query, compactPromptCand date, cand dateFeatures)),
       sP nned = None,
      content = CompactPrompt ssageContent(
         aderText =  aderTextBu lder.apply(query, compactPromptCand date, cand dateFeatures),
        bodyText = bodyTextBu lder.map(_.apply(query, compactPromptCand date, cand dateFeatures)),
        pr maryButtonAct on = None,
        secondaryButtonAct on = None,
        act on = None,
         aderR chText =
           aderR chTextBu lder.map(_.apply(query, compactPromptCand date, cand dateFeatures)),
        bodyR chText =
          bodyR chTextBu lder.map(_.apply(query, compactPromptCand date, cand dateFeatures))
      ),
       mpress onCallbacks = None
    )
}
