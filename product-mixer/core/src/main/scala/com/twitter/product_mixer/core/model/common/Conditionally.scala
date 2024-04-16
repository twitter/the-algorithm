package com.tw ter.product_m xer.core.model.common

/**
 * A m x n tra  that can be added to a [[Component]] that's marked w h [[SupportsCond  onally]]
 * A [[Component]] w h [[SupportsCond  onally]] and [[Cond  onally]] w ll only be run w n `only f` returns true
 *  f `only f` returns false, t  [[Component]]  s sk pped and no stats are recorded for  .
 *
 * @note  f an except on  s thrown w n evaluat ng `only f`,   w ll bubble up to t  conta n ng `P pel ne`,
 *       ho ver t  [[Component]]'s stats w ll not be  ncre nted. Because of t  `only f` should never throw.
 *
 * @note each [[Component]] that [[SupportsCond  onally]] has an  mple ntat on w h  n t 
 *       component l brary that w ll cond  onally run t  component based on a [[com.tw ter.t  l nes.conf gap .Param]]
 *
 * @note [[Cond  onally]] funct onal y  s w red  nto t  Component's Executor.
 *
 * @tparam  nput t   nput that  s used to gate a component on or off
 */
tra  Cond  onally[- nput] { _: SupportsCond  onally[ nput] =>

  /**
   *  f `only f` returns true, t  underl ng [[Component]]  s run, ot rw se  's sk pped
   * @note must not throw
   */
  def only f(query:  nput): Boolean
}

/**
 * Marker tra  added  to t  base type for each [[Component]] wh ch supports t  [[Cond  onally]] m x n
 *
 * @note t   s `pr vate[core]` because   can only be added to t  base  mple ntat on of components by t  Product M xer team
 *
 * @tparam  nput t   nput that  s used to gate a component on or off  f [[Cond  onally]]  s m xed  n
 */
pr vate[core] tra  SupportsCond  onally[- nput] { _: Component => }

object Cond  onally {

  /**
   *  lper  thod for comb n ng t  [[Cond  onally.only f]] of an underly ng [[Component]] w h an add  onal pred cate
   */
  def and[ComponentType <: Component,  nput](
    query:  nput,
    component: ComponentType w h SupportsCond  onally[ nput],
    only f: Boolean
  ): Boolean =
    only f && {
      component match {
        // @unc cked  s safe  re because t  type para ter  s guaranteed by
        // t  `SupportsCond  onally[ nput]` type para ter
        case underly ng: Cond  onally[ nput @unc cked] =>
          underly ng.only f(query)
        case _ =>
          true
      }
    }

}
