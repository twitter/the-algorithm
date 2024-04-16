package com.tw ter.product_m xer.component_l brary.model.presentat on.urt

 mport com.tw ter.product_m xer.core.model.common.presentat on.urt.BaseUrt emPresentat on
 mport com.tw ter.product_m xer.core.model.common.presentat on.urt.BaseUrtModulePresentat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em

case class Urt emPresentat on(
  overr de val t  l ne em: T  l ne em,
  overr de val modulePresentat on: Opt on[BaseUrtModulePresentat on] = None)
    extends BaseUrt emPresentat on
