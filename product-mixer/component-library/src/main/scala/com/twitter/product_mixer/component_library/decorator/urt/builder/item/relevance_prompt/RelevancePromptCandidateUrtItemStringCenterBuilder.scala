package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.relevance_prompt

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.relevance_prompt.RelevancePromptCand dateUrt emStr ngCenterBu lder.RelevancePromptCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.RelevancePromptCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseStr
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt.Prompt em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt.RelevancePromptContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt.RelevancePromptD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt.RelevancePromptFollowUpFeedbackType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Callback
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object RelevancePromptCand dateUrt emStr ngCenterBu lder {
  val RelevancePromptCl entEvent nfoEle nt: Str ng = "relevance_prompt"
}

case class RelevancePromptCand dateUrt emStr ngCenterBu lder[-Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, RelevancePromptCand date],
  t leTextBu lder: BaseStr[Query, RelevancePromptCand date],
  conf rmat onTextBu lder: BaseStr[Query, RelevancePromptCand date],
   sRelevantTextBu lder: BaseStr[Query, RelevancePromptCand date],
  notRelevantTextBu lder: BaseStr[Query, RelevancePromptCand date],
  d splayType: RelevancePromptD splayType,
   sRelevantCallback: Callback,
  notRelevantCallback: Callback,
   sRelevantFollowUp: Opt on[RelevancePromptFollowUpFeedbackType] = None,
  notRelevantFollowUp: Opt on[RelevancePromptFollowUpFeedbackType] = None,
   mpress onCallbacks: Opt on[L st[Callback]] = None)
    extends Cand dateUrtEntryBu lder[Query, RelevancePromptCand date, Prompt em] {

  overr de def apply(
    query: Query,
    relevancePromptCand date: RelevancePromptCand date,
    cand dateFeatures: FeatureMap
  ): Prompt em =
    Prompt em(
       d = relevancePromptCand date. d,
      sort ndex = None,
      cl entEvent nfo = cl entEvent nfoBu lder(
        query,
        relevancePromptCand date,
        cand dateFeatures,
        So (RelevancePromptCl entEvent nfoEle nt)),
      feedbackAct on nfo = None,
      content = RelevancePromptContent(
        t le = t leTextBu lder(query, relevancePromptCand date, cand dateFeatures),
        conf rmat on = conf rmat onTextBu lder(query, relevancePromptCand date, cand dateFeatures),
         sRelevantText =  sRelevantTextBu lder(query, relevancePromptCand date, cand dateFeatures),
        notRelevantText =
          notRelevantTextBu lder(query, relevancePromptCand date, cand dateFeatures),
         sRelevantCallback =  sRelevantCallback,
        notRelevantCallback = notRelevantCallback,
        d splayType = d splayType,
         sRelevantFollowUp =  sRelevantFollowUp,
        notRelevantFollowUp = notRelevantFollowUp,
      ),
       mpress onCallbacks =  mpress onCallbacks
    )
}
