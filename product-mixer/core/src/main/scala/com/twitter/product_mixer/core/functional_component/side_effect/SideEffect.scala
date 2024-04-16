package com.tw ter.product_m xer.core.funct onal_component.s de_effect

 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.st ch.St ch

/**
 * A s de-effect  s a anc llary act on that doesn't affect t  result of execut on d rectly.
 *
 * For example: Logg ng,  tory stores
 *
 *  mple nt ng components can express fa lures by throw ng an except on. T se except ons
 * w ll be caught and not affect t  request process ng.
 *
 * @note S de effects execute asynchronously  n a f re-and-forget way,  's  mportant to add alerts
 *       to t  [[S deEffect]] component  self s nce a fa lures wont show up  n  tr cs
 *       that just mon or y  p pel ne as a whole.
 *
 * @see [[ExecuteSynchronously]] for mod fy ng a [[S deEffect]] to execute w h synchronously w h
 *      t  request wa  ng on t  s de effect to complete, t  w ll  mpact t  overall request's latency
 **/
tra  S deEffect[- nputs] extends Component {

  /** @see [[S deEffect dent f er]] */
  overr de val  dent f er: S deEffect dent f er

  def apply( nputs:  nputs): St ch[Un ]
}
