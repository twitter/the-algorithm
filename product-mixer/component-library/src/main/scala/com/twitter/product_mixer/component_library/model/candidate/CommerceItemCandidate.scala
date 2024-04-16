package com.tw ter.product_m xer.component_l brary.model.cand date

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun

/**
 * Canon cal Com rceProductCand date model wh ch encapsulates  nformat on about a spec f c Product.
 * Always prefer t  vers on over all ot r var ants. For example,  Phone 14, 128 GB, Wh e. W n a
 * user cl cks on a Com rceProduct, t y w ll be taken to t  spec f c product page.
 *
 * @note Both Com rceProduct and Com rceProductGroups (below) can be shown  n t  sa 
 *       T  l neModule ( .e Carousel)
 *
 * @note Any add  onal f elds should be added as a [[com.tw ter.product_m xer.core.feature.Feature]]
 *       on t  cand date's [[com.tw ter.product_m xer.core.feature.featuremap.FeatureMap]].  f t 
 *       features co  from t  cand date s ce  self (as opposed to hydrated v a a
 *       [[com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator]]),
 *       t n [[com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g.featuresFromCand dateS ceTransfor rs]]
 *       can be used to extract features from t  cand date s ce response.
 *
 * @note T  class should always rema n `f nal`.  f for any reason t  `f nal` mod f er  s removed,
 *       t  equals()  mple ntat on must be updated  n order to handle class  n r or equal y
 *       (see note on t  equals  thod below)
 */
f nal class Com rceProductCand date pr vate (
  overr de val  d: Long)
    extends Un versalNoun[Long] {

  /**
   * @ n r doc
   */
  overr de def canEqual(that: Any): Boolean = that. s nstanceOf[Com rceProductCand date]

  /**
   * H gh performance  mple ntat on of equals  thod that leverages:
   *  - Referent al equal y short c rcu 
   *  - Cac d hashcode equal y short c rcu 
   *  - F eld values are only c cked  f t  hashCodes are equal to handle t  unl kely case
   *    of a hashCode coll s on
   *  - Removal of c ck for `that` be ng an equals-compat ble descendant s nce t  class  s f nal
   *
   * @note `cand date.canEqual(t )`  s not necessary because t  class  s f nal
   * @see [[http://www.art ma.com/p ns1ed/object-equal y.html Programm ng  n Scala,
   *      Chapter 28]] for d scuss on and des gn.
   */
  overr de def equals(that: Any): Boolean =
    that match {
      case cand date: Com rceProductCand date =>
        (
          (t  eq cand date)
            || ((hashCode == cand date.hashCode) && ( d == cand date. d))
        )
      case _ =>
        false
    }

  /**
   * Leverage doma n-spec f c constra nts (see notes below) to safely construct and cac  t 
   * hashCode as a val, such that    s  nstant ated once on object construct on. T  prevents t 
   * need to recompute t  hashCode on each hashCode()  nvocat on, wh ch  s t  behav or of t 
   * Scala comp ler case class-generated hashCode() s nce   cannot make assumpt ons regard ng f eld
   * object mutab l y and hashCode  mple ntat ons.
   *
   * @note Cach ng t  hashCode  s only safe  f all of t  f elds used to construct t  hashCode
   *       are  mmutable. T   ncludes:
   *       -  nab l y to mutate t  object reference on for an ex st ng  nstant ated cand date
   *       ( .e. each f eld  s a val)
   *       -  nab l y to mutate t  f eld object  nstance  self ( .e. each f eld  s an  mmutable
   *       -  nab l y to mutate t  f eld object  nstance  self ( .e. each f eld  s an  mmutable
   *       data structure), assum ng stable hashCode  mple ntat ons for t se objects
   *
   * @note  n order for t  hashCode to be cons stent w h object equal y, `##` must be used for
   *       boxed nu r c types and null. As such, always prefer `.##` over `.hashCode()`.
   */
  overr de val hashCode:  nt =  d.##
}

object Com rceProductCand date {
  def apply( d: Long): Com rceProductCand date = new Com rceProductCand date( d)
}

/**
 * Canon cal Com rceProductGroupCand date model wh ch encapsulates  nformat on about a S ngle
 * Product Type and  s correspond ng vers ons. Always prefer t  vers on over all ot r var ants.
 * For example:
 *  Phone 14
 *   - 128 GB, Wh e
 *   - 128 GB, Blue
 *   - 1TB, Grey
 * W n a user cl cks on a Product Group, t y w ll be shown  nformat on about all of t  poss ble
 * vers ons of t  top level product.
 *
 * @note Both Com rceProduct (above) and Com rceProductGroups can be shown  n t  sa 
 *       T  l neModule ( .e Carousel)
 *
 * @note Any add  onal f elds should be added as a [[com.tw ter.product_m xer.core.feature.Feature]]
 *       on t  cand date's [[com.tw ter.product_m xer.core.feature.featuremap.FeatureMap]].  f t 
 *       features co  from t  cand date s ce  self (as opposed to hydrated v a a
 *       [[com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator]]),
 *       t n [[com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g.featuresFromCand dateS ceTransfor rs]]
 *       can be used to extract features from t  cand date s ce response.
 *
 * @note T  class should always rema n `f nal`.  f for any reason t  `f nal` mod f er  s removed,
 *       t  equals()  mple ntat on must be updated  n order to handle class  n r or equal y
 *       (see note on t  equals  thod below)
 */
f nal class Com rceProductGroupCand date pr vate (
  overr de val  d: Long)
    extends Un versalNoun[Long] {

  /**
   * @ n r doc
   */
  overr de def canEqual(that: Any): Boolean = that. s nstanceOf[Com rceProductGroupCand date]

  /**
   * H gh performance  mple ntat on of equals  thod that leverages:
   *  - Referent al equal y short c rcu 
   *  - Cac d hashcode equal y short c rcu 
   *  - F eld values are only c cked  f t  hashCodes are equal to handle t  unl kely case
   *    of a hashCode coll s on
   *  - Removal of c ck for `that` be ng an equals-compat ble descendant s nce t  class  s f nal
   *
   * @note `cand date.canEqual(t )`  s not necessary because t  class  s f nal
   * @see [[http://www.art ma.com/p ns1ed/object-equal y.html Programm ng  n Scala,
   *      Chapter 28]] for d scuss on and des gn.
   */
  overr de def equals(that: Any): Boolean =
    that match {
      case cand date: Com rceProductGroupCand date =>
        (
          (t  eq cand date)
            || ((hashCode == cand date.hashCode) && ( d == cand date. d))
        )
      case _ =>
        false
    }

  /**
   * Leverage doma n-spec f c constra nts (see notes below) to safely construct and cac  t 
   * hashCode as a val, such that    s  nstant ated once on object construct on. T  prevents t 
   * need to recompute t  hashCode on each hashCode()  nvocat on, wh ch  s t  behav or of t 
   * Scala comp ler case class-generated hashCode() s nce   cannot make assumpt ons regard ng f eld
   * object mutab l y and hashCode  mple ntat ons.
   *
   * @note Cach ng t  hashCode  s only safe  f all of t  f elds used to construct t  hashCode
   *       are  mmutable. T   ncludes:
   *       -  nab l y to mutate t  object reference on for an ex st ng  nstant ated cand date
   *         ( .e. each f eld  s a val)
   *       -  nab l y to mutate t  f eld object  nstance  self ( .e. each f eld  s an  mmutable
   *       -  nab l y to mutate t  f eld object  nstance  self ( .e. each f eld  s an  mmutable
   *         data structure), assum ng stable hashCode  mple ntat ons for t se objects
   * @note  n order for t  hashCode to be cons stent w h object equal y, `##` must be used for
   *       boxed nu r c types and null. As such, always prefer `.##` over `.hashCode()`.
   */
  overr de val hashCode:  nt =  d.##
}

object Com rceProductGroupCand date {
  def apply( d: Long): Com rceProductGroupCand date = new Com rceProductGroupCand date( d)
}
