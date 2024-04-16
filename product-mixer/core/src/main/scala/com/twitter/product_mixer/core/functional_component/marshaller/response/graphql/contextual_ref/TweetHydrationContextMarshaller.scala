package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.graphql.contextual_ref

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.rtf.safety_level.SafetyLevelMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.contextual_ref.T etHydrat onContext
 mport com.tw ter.strato.graphql.contextual_refs.{thr ftscala => thr ft}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T etHydrat onContextMarshaller @ nject() (
  safetyLevelMarshaller: SafetyLevelMarshaller,
  outerT etContextMarshaller: OuterT etContextMarshaller) {

  def apply(t etHydrat onContext: T etHydrat onContext): thr ft.T etHydrat onContext =
    thr ft.T etHydrat onContext(
      safetyLevelOverr de = t etHydrat onContext.safetyLevelOverr de.map(safetyLevelMarshaller(_)),
      outerT etContext =
        t etHydrat onContext.outerT etContext.map(outerT etContextMarshaller(_))
    )
}
