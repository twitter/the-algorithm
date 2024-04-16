package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.prompt

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.CallbackMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Cl entEvent nfoMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt.Prompt em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Prompt emMarshaller @ nject() (
  promptContentMarshaller: PromptContentMarshaller,
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller,
  callbackMarshaller: CallbackMarshaller) {

  def apply(relevancePrompt em: Prompt em): urt.T  l ne emContent = {
    urt.T  l ne emContent.Prompt(
      urt.Prompt(
        content = promptContentMarshaller(relevancePrompt em.content),
        cl entEvent nfo = relevancePrompt em.cl entEvent nfo.map(cl entEvent nfoMarshaller(_)),
         mpress onCallbacks = relevancePrompt em. mpress onCallbacks.map { callbackL st =>
          callbackL st.map(callbackMarshaller(_))
        }
      ))
  }
}
