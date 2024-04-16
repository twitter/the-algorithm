package com.tw ter.product_m xer.core.funct onal_component.s de_effect

/**
 * A mod f er for any [[S deEffect]] so that t  request wa s for   to complete before be ng returned
 *
 * @note t  w ll make t  [[S deEffect]]'s latency  mpact t  overall request's latency
 *
 * @example {{{
 * class  S deEffect extends P pel neResultS deEffect[T] w h ExecuteSynchronously {...}
 * }}}
 *
 * @example {{{
 * class  S deEffect extends Scr beLogEventS deEffect[T] w h ExecuteSynchronously {...}
 * }}}
 */
tra  ExecuteSynchronously { _: S deEffect[_] => }

/**
 * A mod f er for any [[ExecuteSynchronously]] [[S deEffect]] that makes   so fa lures w ll be
 * reported  n t  results but wont cause t  request as a whole to fa l.
 */
tra  Fa lOpen { _: ExecuteSynchronously => }
