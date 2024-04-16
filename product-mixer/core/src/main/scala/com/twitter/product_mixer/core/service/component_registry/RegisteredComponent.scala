package com.tw ter.product_m xer.core.serv ce.component_reg stry

 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er

object Reg steredComponent {
  // Sort by Component dent f er wh ch has  s own  mpl c  order ng def ned
   mpl c  val order ng: Order ng[Reg steredComponent] =
    Order ng.by[Reg steredComponent, Component dent f er](_.component. dent f er)
}

case class Reg steredComponent(
   dent f er: Component dent f er,
  component: Component,
  s ceF le: Str ng)
