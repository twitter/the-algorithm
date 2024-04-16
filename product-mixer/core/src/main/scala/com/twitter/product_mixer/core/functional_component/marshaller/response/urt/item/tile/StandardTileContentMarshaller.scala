package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.t le

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.BadgeMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t le.StandardT leContent
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class StandardT leContentMarshaller @ nject() (
  badgeMarshaller: BadgeMarshaller) {

  def apply(standardT leContent: StandardT leContent): urt.T leContentStandard =
    urt.T leContentStandard(
      t le = standardT leContent.t le,
      support ngText = standardT leContent.support ngText,
      badge = standardT leContent.badge.map(badgeMarshaller(_))
    )
}
