package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.CallbackMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssagePrompt em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  ssagePrompt emMarshaller @ nject() (
   ssageContentMarshaller:  ssageContentMarshaller,
  callbackMarshaller: CallbackMarshaller) {

  def apply( ssagePrompt em:  ssagePrompt em): urt.T  l ne emContent =
    urt.T  l ne emContent. ssage(
      urt. ssagePrompt(
        content =  ssageContentMarshaller( ssagePrompt em.content),
         mpress onCallbacks =  ssagePrompt em. mpress onCallbacks.map { callbackL st =>
          callbackL st.map(callbackMarshaller(_))
        }
      )
    )
}
