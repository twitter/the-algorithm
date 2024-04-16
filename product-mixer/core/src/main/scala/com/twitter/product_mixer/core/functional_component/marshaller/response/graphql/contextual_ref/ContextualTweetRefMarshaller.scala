package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.graphql.contextual_ref

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.contextual_ref.ContextualT etRef
 mport com.tw ter.strato.graphql.contextual_refs.{thr ftscala => thr ft}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ContextualT etRefMarshaller @ nject() (
  t etHydrat onContextMarshaller: T etHydrat onContextMarshaller) {

  def apply(contextualT etRef: ContextualT etRef): thr ft.ContextualT etRef =
    thr ft.ContextualT etRef(
       d = contextualT etRef. d,
      hydrat onContext =
        contextualT etRef.hydrat onContext.map(t etHydrat onContextMarshaller(_)))
}
