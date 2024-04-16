package com.tw ter.product_m xer.core.funct onal_component.transfor r

 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er

/**
 * A transfor r  s a synchronous transformat on that takes t  prov ded [[ nput]] and returns so 
 * def ned [[Output]]. For example, extract ng a score from from a scored cand dates.
 */
tra  Transfor r[- nputs, +Output] extends Component {
  overr de val  dent f er: Transfor r dent f er

  /** Takes [[ nputs]] and transfor rs t m  nto so  [[Output]] of y  choos ng. */
  def transform( nput:  nputs): Output
}
