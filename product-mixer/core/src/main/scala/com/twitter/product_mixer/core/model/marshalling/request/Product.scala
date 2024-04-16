package com.tw ter.product_m xer.core.model.marshall ng.request

 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common. dent f er.Product dent f er

tra  Product extends Component {

  /**
   *  dent f er na s on products can be used to create Feature Sw ch rules by product,
   * wh ch useful  f bucket ng occurs  n a component shared by mult ple products.
   *
   * @see [[com.tw ter.product_m xer.core.product.ProductParamConf g.supportedCl entFSNa ]]
   */
  overr de val  dent f er: Product dent f er

  /**
   * To support Str ngCenter, overr de t  val to `So ("na -of-str ng-center-project")` and
   *  nclude t  `ProductScopeStr ngCenterModule`  n t  server's modules l st
   */
  val str ngCenterProject: Opt on[Str ng] = None
}

tra  HasProduct {
  def product: Product
}
