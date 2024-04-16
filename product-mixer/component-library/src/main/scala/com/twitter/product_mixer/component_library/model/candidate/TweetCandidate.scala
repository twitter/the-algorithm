package com.tw ter.product_m xer.component_l brary.model.cand date

 mport com.fasterxml.jackson.annotat on.JsonTypeNa 
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun

// JSON type annotat ons are needed for  dent fy ng renderable ent  es to Turntable, most cand dates
// do not need t m.
@JsonTypeNa ("t et")
tra  BaseT etCand date extends Un versalNoun[Long]

/**
 * Canon cal T etCand date model. Always prefer t  vers on over all ot r var ants.
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
f nal class T etCand date pr vate (
  overr de val  d: Long)
    extends BaseT etCand date {

  /**
   * @ n r doc
   */
  overr de def canEqual(that: Any): Boolean = that. s nstanceOf[T etCand date]

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
      case cand date: T etCand date =>
        ((t  eq cand date)
          || ((hashCode == cand date.hashCode) && ( d == cand date. d)))
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

object T etCand date {
  def apply( d: Long): T etCand date = new T etCand date( d)
}

/**
 * T et Author User  D of a g ven T et Cand date. T   s typ cally needed w n hydrat ng t et
 * author extended features  n Feature Store (e.g, [[T etCand dateAuthor dEnt y]]). T  feature
 *  s typ cally extracted by hydrat ng   from T etyp e, or extract ng    n y  cand date s ce
 *  f   returns t  Author  D alongs de T et  D us ng a [[Cand dateP pel neResultsTransfor r]]
 */
object T etAuthor dFeature extends Feature[T etCand date, Long]

/**
 * W t r t  t et should be p nned w n marshalled to URT or not.
 * See [[com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.t et.T etCand dateUrt emBu lder]]
 */
object  sP nnedFeature extends Feature[T etCand date, Boolean]
