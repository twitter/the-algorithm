package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.prompt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt._
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RelevancePromptFollowUpFeedbackTypeMarshaller @ nject() (
  relevancePromptFollowUpText nputMarshaller: RelevancePromptFollowUpText nputMarshaller) {

  def apply(
    relevancePromptFollowUpFeedbackType: RelevancePromptFollowUpFeedbackType
  ): urt.RelevancePromptFollowUpFeedbackType = relevancePromptFollowUpFeedbackType match {
    case relevancePromptFollowUpText nput: RelevancePromptFollowUpText nput =>
      urt.RelevancePromptFollowUpFeedbackType.FollowUpText nput(
        relevancePromptFollowUpText nputMarshaller(relevancePromptFollowUpText nput))
  }
}
