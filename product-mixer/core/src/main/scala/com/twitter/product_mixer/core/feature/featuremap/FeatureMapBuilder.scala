package com.tw ter.product_m xer.core.feature.featuremap

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try
 mport scala.collect on.mutable

/**
 * [[FeatureMapBu lder]]  s a typesafe way (  c cks types vs t  [[Feature]]s on `.add`) to bu ld a [[FeatureMap]].
 *
 * Throws a [[Dupl cateFeatureExcept on]]  f   try to add t  sa  [[Feature]] more than once.
 *
 * T se bu lders are __not__ reusable.
 */

class FeatureMapBu lder {
  pr vate val underly ng = Map.newBu lder[Feature[_, _], Try[Any]]
  pr vate val keys = mutable.HashSet.empty[Feature[_, _]]
  pr vate var bu lt = false

  /**
   * Add a [[Try]] of a [[Feature]] `value` to t  map,
   * handl ng both t  [[Return]] and [[Throw]] cases.
   *
   * Throws a [[Dupl cateFeatureExcept on]]  f  's already present.
   *
   * @note  f   have a [[Feature]] w h a non-opt onal value type `Feature[_, V]`
   *       but have an `Opt on[V]`   can use [[Try.orThrow]] to convert t  [[Opt on]]
   *       to a [[Try]], wh ch w ll store t  successful or fa led [[Feature]]  n t  map.
   */
  def add[V](feature: Feature[_, V], value: Try[V]): FeatureMapBu lder = addTry(feature, value)

  /**
   * Add a successful [[Feature]] `value` to t  map
   *
   * Throws a [[Dupl cateFeatureExcept on]]  f  's already present.
   *
   * @note  f   have a [[Feature]] w h a non-opt onal value type `Feature[_, V]`
   *       but have an `Opt on[V]`   can use [[Opt on.get]] or [[Opt on.getOrElse]]
   *       to convert t  [[Opt on]] to extract t  underly ng value,
   *       wh ch w ll throw  m d ately  f  's [[None]] or add t  successful [[Feature]]  n t  map.
   */
  def add[V](feature: Feature[_, V], value: V): FeatureMapBu lder =
    addTry(feature, Return(value))

  /**
   * Add a fa led [[Feature]] `value` to t  map
   *
   * Throws a [[Dupl cateFeatureExcept on]]  f  's already present.
   */
  def addFa lure(feature: Feature[_, _], throwable: Throwable): FeatureMapBu lder =
    addTry(feature, Throw(throwable))

  /**
   * [[add]] but for w n t  [[Feature]] types aren't known
   *
   * Add a [[Try]] of a [[Feature]] `value` to t  map,
   * handl ng both t  [[Return]] and [[Throw]] cases.
   *
   * Throws a [[Dupl cateFeatureExcept on]]  f  's already present.
   *
   * @note  f   have a [[Feature]] w h a non-opt onal value type `Feature[_, V]`
   *       but have an `Opt on[V]`   can use [[Try.orThrow]] to convert t  [[Opt on]]
   *       to a [[Try]], wh ch w ll store t  successful or fa led [[Feature]]  n t  map.
   */
  def addTry(feature: Feature[_, _], value: Try[_]): FeatureMapBu lder = {
     f (keys.conta ns(feature)) {
      throw new Dupl cateFeatureExcept on(feature)
    }
    addW houtVal dat on(feature, value)
  }

  /**
   * [[addTry]] but w hout a [[Dupl cateFeatureExcept on]] c ck
   *
   * @note Only for use  nternally w h n [[FeatureMap. rge]]
   */
  pr vate[featuremap] def addW houtVal dat on(
    feature: Feature[_, _],
    value: Try[_]
  ): FeatureMapBu lder = {
    keys += feature
    underly ng += ((feature, value))
    t 
  }

  /** Bu lds t  FeatureMap */
  def bu ld(): FeatureMap = {
     f (bu lt) {
      throw ReusedFeatureMapBu lderExcept on
    }

    bu lt = true
    new FeatureMap(underly ng.result())
  }
}

object FeatureMapBu lder {

  /** Returns a new [[FeatureMapBu lder]] for mak ng [[FeatureMap]]s */
  def apply(): FeatureMapBu lder = new FeatureMapBu lder
}

class Dupl cateFeatureExcept on(feature: Feature[_, _])
    extends UnsupportedOperat onExcept on(s"Feature $feature already ex sts  n FeatureMap")

object ReusedFeatureMapBu lderExcept on
    extends UnsupportedOperat onExcept on(
      "bu ld() cannot be called more than once s nce FeatureMapBu lders are not reusable")
