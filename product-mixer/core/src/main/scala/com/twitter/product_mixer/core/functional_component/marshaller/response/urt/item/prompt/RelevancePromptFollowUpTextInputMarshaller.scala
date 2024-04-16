package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.prompt

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.CallbackMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt.RelevancePromptFollowUpText nput
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RelevancePromptFollowUpText nputMarshaller @ nject() (
  callbackMarshaller: CallbackMarshaller) {

  def apply(
    relevancePromptFollowUpText nput: RelevancePromptFollowUpText nput
  ): urt.RelevancePromptFollowUpText nput = urt.RelevancePromptFollowUpText nput(
    context = relevancePromptFollowUpText nput.context,
    textF eldPlaceholder = relevancePromptFollowUpText nput.textF eldPlaceholder,
    sendTextCallback = callbackMarshaller(relevancePromptFollowUpText nput.sendTextCallback)
  )
}
