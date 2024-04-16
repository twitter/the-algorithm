package com.tw ter.product_m xer.core.funct onal_component.decorator

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common.presentat on.Un versalPresentat on

/**
 * Decorat on assoc ates spec f c [[Un versalPresentat on]] w h a cand date
 */
case class Decorat on(
  cand date: Un versalNoun[Any],
  presentat on: Un versalPresentat on)
