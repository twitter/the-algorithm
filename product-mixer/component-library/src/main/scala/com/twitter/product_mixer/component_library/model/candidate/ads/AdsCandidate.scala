package com.tw ter.product_m xer.component_l brary.model.cand date.ads

 mport com.tw ter.adserver.{thr ftscala => adsthr ft}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun

/**
 * An [[AdsCand date]] represents a p ece of promoted content.
 *
 * T  cand date class stores a reference to t  ad mpress on, wh ch  s t  common thr ft structure
 * used by t  Ads team to represent an ad.
 *
 * Goldf nch, t  ads- nject on l brary, consu s t  [[Ad mpress on]].
 */
sealed tra  AdsCand date extends Un versalNoun[Any] {
  val ad mpress on: adsthr ft.Ad mpress on
}

/**
 * Canon cal AdsT etCand date model. Always prefer t  vers on over all ot r var ants.
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
f nal class AdsT etCand date pr vate (
  overr de val  d: Long,
  overr de val ad mpress on: adsthr ft.Ad mpress on)
    extends AdsCand date
    w h BaseT etCand date {

  /**
   * @ n r doc
   */
  overr de def canEqual(that: Any): Boolean = that. s nstanceOf[AdsT etCand date]

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
      case cand date: AdsT etCand date =>
        (
          (t  eq cand date)
            || ((hashCode == cand date.hashCode)
              && ( d == cand date. d && ad mpress on == cand date.ad mpress on))
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
  overr de val hashCode:  nt =
    31 * (
       d.##
    ) + ad mpress on.##
}

object AdsT etCand date {
  def apply( d: Long, ad mpress on: adsthr ft.Ad mpress on): AdsT etCand date =
    new AdsT etCand date( d, ad mpress on)
}
