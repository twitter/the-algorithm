package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.product_m xer.core.model.common. dent f er.Product dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.{Product => ProductM xerProduct}

object Product {
  case object Mag cRecs extends ProductM xerProduct {
    overr de val  dent f er: Product dent f er = Product dent f er("Mag cRecs")
    overr de val str ngCenterProject: Opt on[Str ng] = So ("people-d scovery")
  }

  case object PlaceholderProductM xerProduct extends ProductM xerProduct {
    overr de val  dent f er: Product dent f er = Product dent f er("PlaceholderProductM xerProduct")
  }
}
