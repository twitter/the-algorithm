package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.graphql.contextual_ref

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.contextual_ref.OuterT etContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.contextual_ref.QuoteT et d
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.contextual_ref.Ret et d
 mport com.tw ter.strato.graphql.contextual_refs.{thr ftscala => thr ft}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class OuterT etContextMarshaller @ nject() () {

  def apply(outerT etContext: OuterT etContext): thr ft.OuterT etContext =
    outerT etContext match {
      case QuoteT et d( d) => thr ft.OuterT etContext.QuoteT et d( d)
      case Ret et d( d) => thr ft.OuterT etContext.Ret et d( d)
    }
}
