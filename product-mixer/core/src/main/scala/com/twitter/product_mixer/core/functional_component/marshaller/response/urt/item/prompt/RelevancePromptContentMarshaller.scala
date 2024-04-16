package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.prompt

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.CallbackMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt.RelevancePromptContent
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RelevancePromptContentMarshaller @ nject() (
  callbackMarshaller: CallbackMarshaller,
  relevancePromptD splayTypeMarshaller: RelevancePromptD splayTypeMarshaller,
  relevancePromptFollowUpFeedbackTypeMarshaller: RelevancePromptFollowUpFeedbackTypeMarshaller) {

  def apply(relevancePromptContent: RelevancePromptContent): urt.RelevancePrompt =
    urt.RelevancePrompt(
      t le = relevancePromptContent.t le,
      conf rmat on = relevancePromptContent.conf rmat on,
       sRelevantText = relevancePromptContent. sRelevantText,
      notRelevantText = relevancePromptContent.notRelevantText,
       sRelevantCallback = callbackMarshaller(relevancePromptContent. sRelevantCallback),
      notRelevantCallback = callbackMarshaller(relevancePromptContent.notRelevantCallback),
      d splayType = relevancePromptD splayTypeMarshaller(relevancePromptContent.d splayType),
       sRelevantFollowUp = relevancePromptContent. sRelevantFollowUp.map(
        relevancePromptFollowUpFeedbackTypeMarshaller(_)),
      notRelevantFollowUp = relevancePromptContent.notRelevantFollowUp.map(
        relevancePromptFollowUpFeedbackTypeMarshaller(_))
    )
}
