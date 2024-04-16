package com.tw ter.follow_recom ndat ons.products.ho _t  l ne

 mport com.tw ter.product_m xer.core.model.common. dent f er.Product dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product

case object HTLProductM xer extends Product {
  overr de val  dent f er: Product dent f er = Product dent f er("Ho T  l ne")
  overr de val str ngCenterProject: Opt on[Str ng] = So ("people-d scovery")
}
