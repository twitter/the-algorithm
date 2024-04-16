package com.tw ter.ho _m xer.model.request

 mport com.tw ter.product_m xer.core.model.common. dent f er.Product dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product

/**
 *  dent f er na s on products can be used to create Feature Sw ch rules by product,
 * wh ch useful  f bucket ng occurs  n a component shared by mult ple products.
 * @see [[Product. dent f er]]
 */

case object Follow ngProduct extends Product {
  overr de val  dent f er: Product dent f er = Product dent f er("Follow ng")
  overr de val str ngCenterProject: Opt on[Str ng] = So ("t  l nem xer")
}

case object For Product extends Product {
  overr de val  dent f er: Product dent f er = Product dent f er("For ")
  overr de val str ngCenterProject: Opt on[Str ng] = So ("t  l nem xer")
}

case object ScoredT etsProduct extends Product {
  overr de val  dent f er: Product dent f er = Product dent f er("ScoredT ets")
  overr de val str ngCenterProject: Opt on[Str ng] = So ("t  l nem xer")
}

case object L stT etsProduct extends Product {
  overr de val  dent f er: Product dent f er = Product dent f er("L stT ets")
  overr de val str ngCenterProject: Opt on[Str ng] = So ("t  l nem xer")
}

case object L stRecom ndedUsersProduct extends Product {
  overr de val  dent f er: Product dent f er = Product dent f er("L stRecom ndedUsers")
  overr de val str ngCenterProject: Opt on[Str ng] = So ("t  l nem xer")
}

case object Subscr bedProduct extends Product {
  overr de val  dent f er: Product dent f er = Product dent f er("Subscr bed")
  overr de val str ngCenterProject: Opt on[Str ng] = So ("t  l nem xer")
}
