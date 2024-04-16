package com.tw ter.product_m xer.core.model.common

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.HasComponent dent f er

/**
 * Components are very gener cally reusable composable p eces
 * Components are un quely  dent f able and centrally reg stered
 */
tra  Component extends HasComponent dent f er {

  /** @see [[Component dent f er]] */
  overr de val  dent f er: Component dent f er

  /** t  [[Alert]]s that w ll be used for t  component. */
  val alerts: Seq[Alert] = Seq.empty
}
