package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.prompt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt.PromptContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt.RelevancePromptContent
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PromptContentMarshaller @ nject() (
  relevancePromptContentMarshaller: RelevancePromptContentMarshaller) {

  def apply(promptContent: PromptContent): urt.PromptContent = promptContent match {
    case relevancePromptContent: RelevancePromptContent =>
      urt.PromptContent.RelevancePrompt(relevancePromptContentMarshaller(relevancePromptContent))
  }
}
