package com.tw ter.product_m xer.component_l brary.model.cand date.suggest on

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Typea ad tadata

/**
 * Represents a query suggest on  n typea ad
 */
sealed tra  BaseQuerySuggest onCand date[+T] extends Un versalNoun[T]

/**
 * Canon cal QuerySuggest onCand date model. Always prefer t  vers on over all ot r var ants.
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
f nal class QuerySuggest onCand date pr vate (
  overr de val  d: Str ng,
  val  tadata: Opt on[Typea ad tadata])
    extends BaseQuerySuggest onCand date[Str ng] {

  /**
   * @ n r doc
   */
  overr de def canEqual(that: Any): Boolean = that. s nstanceOf[QuerySuggest onCand date]

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
      case cand date: QuerySuggest onCand date =>
        (
          (t  eq cand date)
            || ((hashCode == cand date.hashCode)
              && ( d == cand date. d &&  tadata == cand date. tadata))
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
    ) +  tadata.##
}

object QuerySuggest onCand date {
  def apply(
     d: Str ng,
     tadata: Opt on[Typea ad tadata] = None
  ): QuerySuggest onCand date = new QuerySuggest onCand date( d,  tadata)
}

/**
 * Canon cal Typea adQueryCand date model. Always prefer t  vers on over all ot r var ants.
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
 *
 */
f nal class Typea adQueryCand date(
  overr de val  d: Str ng,
  val score: Opt on[Double])
    extends BaseQuerySuggest onCand date[Str ng] {

  /**
   * @ n r doc
   */
  overr de def canEqual(that: Any): Boolean = that. s nstanceOf[Typea adQueryCand date]

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
      case cand date: Typea adQueryCand date =>
        (
          (t  eq cand date)
            || ((hashCode == cand date.hashCode)
              && ( d == cand date. d && score == cand date.score))
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
  overr de val hashCode:  nt =
    31 * (
       d.##
    ) + score.##
}

object Typea adQueryCand date {
  def apply( d: Str ng, score: Opt on[Double]): Typea adQueryCand date =
    new Typea adQueryCand date( d, score)
}

f nal class Typea adEventCand date pr vate (
  overr de val  d: Long,
  val  tadata: Opt on[Typea ad tadata])
    extends BaseQuerySuggest onCand date[Long] {

  /**
   * @ n r doc
   */
  overr de def canEqual(that: Any): Boolean = that. s nstanceOf[Typea adQueryCand date]

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
      case cand date: Typea adEventCand date =>
        (
          (t  eq cand date)
            || ((hashCode == cand date.hashCode)
              && ( d == cand date. d &&  tadata == cand date. tadata))
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
  overr de val hashCode:  nt =
    31 * (
       d.##
    ) +  tadata.##
}

object Typea adEventCand date {
  def apply(
     d: Long,
     tadata: Opt on[Typea ad tadata] = None
  ): Typea adEventCand date = new Typea adEventCand date( d,  tadata)
}

/**
 * Canon cal T etAnnotat onQueryCand date model. Always prefer t  vers on over all ot r var ants.
 *
 * TODO Remove score from t  cand date and use a Feature  nstead
 */
f nal class T etAnnotat onQueryCand date pr vate (
  overr de val  d: Str ng,
  val score: Opt on[Double])
    extends BaseQuerySuggest onCand date[Str ng] {

  /**
   * @ n r doc
   */
  overr de def canEqual(that: Any): Boolean = that. s nstanceOf[T etAnnotat onQueryCand date]

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
      case cand date: T etAnnotat onQueryCand date =>
        (
          (t  eq cand date)
            || ((hashCode == cand date.hashCode)
              && ( d == cand date. d && score == cand date.score))
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
  overr de val hashCode:  nt =
    31 * (
       d.##
    ) + score.##
}

object T etAnnotat onQueryCand date {
  def apply( d: Str ng, score: Opt on[Double]): T etAnnotat onQueryCand date =
    new T etAnnotat onQueryCand date( d, score)
}
