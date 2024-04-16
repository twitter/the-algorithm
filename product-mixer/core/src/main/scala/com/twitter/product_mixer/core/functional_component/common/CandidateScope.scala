package com.tw ter.product_m xer.core.funct onal_component.common

 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateP pel nes

/**
 * Spec f es w t r a funct on component (e.g, [[Gate]] or [[Selector]])
 * should apply to a g ven [[Cand dateW hDeta ls]]
 */
sealed tra  Cand dateScope {

  /**
   * returns True  f t  prov ded `cand date`  s  n scope
   */
  def conta ns(cand date: Cand dateW hDeta ls): Boolean

  /** part  ons `cand dates`  nto those that t  scope [[conta ns]] and those   does not */
  f nal def part  on(
    cand dates: Seq[Cand dateW hDeta ls]
  ): Cand dateScope.Part  onedCand dates = {
    val (cand dates nScope, cand datesOutOfScope) = cand dates.part  on(conta ns)
    Cand dateScope.Part  onedCand dates(cand dates nScope, cand datesOutOfScope)
  }
}

object Cand dateScope {
  case class Part  onedCand dates(
    cand dates nScope: Seq[Cand dateW hDeta ls],
    cand datesOutOfScope: Seq[Cand dateW hDeta ls])
}

/**
 * A [[Cand dateScope]] that appl es t  g ven funct onal component
 * to all cand dates regardless of wh ch p pel ne  s t  r [[com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls.s ce]].
 */
case object AllP pel nes extends Cand dateScope {
  overr de def conta ns(cand date: Cand dateW hDeta ls): Boolean = true
}

/**
 * A [[Cand dateScope]] that appl es t  g ven [[com.tw ter.product_m xer.core.funct onal_component.selector.Selector]]
 * only to cand dates whose [[com.tw ter.product_m xer.core.model.common.presentat on.Cand dateP pel nes]]
 * has an  dent f er  n t  [[p pel nes]] Set.
 *  n most cases w re cand dates are not pre- rged, t  Set conta ns t  cand date p pel ne  dent f er t  cand date
 * ca  from.  n t  case w re a cand date's feature maps  re  rged us ng [[Comb neFeatureMapsCand date rger]], t 
 * set conta ns all cand date p pel nes t   rged cand date ca  from and t  scope w ll  nclude t  cand date  f any
 * of t  p pel nes match.
 */
case class Spec f cP pel nes(p pel nes: Set[Cand dateP pel ne dent f er]) extends Cand dateScope {

  requ re(
    p pel nes.nonEmpty,
    "Expected `Spec f cP pel nes` have a non-empty Set of Cand dateP pel ne dent f ers.")

  overr de def conta ns(cand date: Cand dateW hDeta ls): Boolean = {
    cand date.features.get(Cand dateP pel nes).ex sts(p pel nes.conta ns)
  }
}

/**
 * A [[Cand dateScope]] that appl es t  g ven [[com.tw ter.product_m xer.core.funct onal_component.selector.Selector]]
 * only to cand dates whose [[com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls.s ce]]
 *  s [[p pel ne]].
 */
case class Spec f cP pel ne(p pel ne: Cand dateP pel ne dent f er) extends Cand dateScope {

  overr de def conta ns(cand date: Cand dateW hDeta ls): Boolean = cand date.features
    .get(Cand dateP pel nes).conta ns(p pel ne)
}

object Spec f cP pel nes {
  def apply(
    p pel ne: Cand dateP pel ne dent f er,
    p pel nes: Cand dateP pel ne dent f er*
  ): Cand dateScope = {
     f (p pel nes. sEmpty)
      Spec f cP pel ne(p pel ne)
    else
      Spec f cP pel nes((p pel ne +: p pel nes).toSet)
  }
}

/**
 * A [[Cand dateScope]] that appl es t  g ven [[com.tw ter.product_m xer.core.funct onal_component.selector.Selector]]
 * to all cand dates except for t  cand dates whose [[com.tw ter.product_m xer.core.model.common.presentat on.Cand dateP pel nes]]
 * has an  dent f er  n t  [[p pel nesToExclude]] Set.
 *  n most cases w re cand dates are not pre- rged, t  Set conta ns t  cand date p pel ne  dent f er t  cand date
 * ca  from.  n t  case w re a cand date's feature maps  re  rged us ng [[Comb neFeatureMapsCand date rger]], t 
 * set conta ns all cand date p pel nes t   rged cand date ca  from and t  scope w ll  nclude t  cand date  f any
 * of t  p pel nes match.
 */
case class AllExceptP pel nes(
  p pel nesToExclude: Set[Cand dateP pel ne dent f er])
    extends Cand dateScope {
  overr de def conta ns(cand date: Cand dateW hDeta ls): Boolean = !cand date.features
    .get(Cand dateP pel nes).ex sts(p pel nesToExclude.conta ns)
}
