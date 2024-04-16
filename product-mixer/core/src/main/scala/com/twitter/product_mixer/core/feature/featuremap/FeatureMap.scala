package com.tw ter.product_m xer.core.feature.featuremap

 mport com.fasterxml.jackson.datab nd.annotat on.JsonSer al ze
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.featurestorev1.featurevalue.FeatureStoreV1Response
 mport com.tw ter.product_m xer.core.feature.featurestorev1.featurevalue.{
  FeatureStoreV1ResponseFeature => FSv1Feature
}
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try

/**
 * A set of features and t  r values. Assoc ated w h a spec f c  nstance of an Ent y, though
 * that assoc at on  s ma nta ned by t  fra work.
 *
 * [[FeatureMapBu lder]]  s used to bu ld new FeatureMap  nstances
 */
@JsonSer al ze(us ng = classOf[FeatureMapSer al zer])
case class FeatureMap pr vate[feature] (
  pr vate[core] val underly ngMap: Map[Feature[_, _], Try[_]]) {

  /**
   * Returns t  [[Value]] assoc ated w h t  Feature
   *
   *  f t  Feature  s m ss ng from t  feature map,   throws a [[M ss ngFeatureExcept on]].
   *  f t  Feature fa led and  sn't a [[FeatureW hDefaultOnFa lure]] t  w ll throw t  underly ng except on
   * that t  feature fa led w h dur ng hydrat on.
   */
  def get[Value](feature: Feature[_, Value]): Value =
    getOrElse(feature, throw M ss ngFeatureExcept on(feature), None)

  /**
   * Returns t  [[Value]] assoc ated w h t  Feature w h t  sa  semant cs as
   * [[FeatureMap.get()]], but t  underly ng [[Try]]  s returned to allow for c ck ng t  success
   * or error state of a feature hydrat on. T   s  lpful for  mple nt ng fall-back behav or  n
   * case t  feature  s m ss ng or hydrat on fa led w hout a [[FeatureW hDefaultOnFa lure]] set.
   *
   * @note T  [[FeatureMap.getOrElse()]] log c  s dupl cated  re to avo d unpack ng and repack ng
   *       t  [[Try]] that  s already ava lable  n t  [[underly ngMap]]
   */
  def getTry[Value](feature: Feature[_, Value]): Try[Value] =
    underly ngMap.get(feature) match {
      case None => Throw(M ss ngFeatureExcept on(feature))
      case So (value @ Return(_)) => value.as nstanceOf[Return[Value]]
      case So (value @ Throw(_)) =>
        feature match {
          case f: FeatureW hDefaultOnFa lure[_, Value] @unc cked => Return(f.defaultValue)
          case _ => value.as nstanceOf[Throw[Value]]
        }
    }

  /**
   * Returns t  [[Value]] assoc ated w h t  feature or a default  f t  key  s not conta ned  n t  map
   * `default` can also be used to throw an except on.
   *
   *  e.g. `.getOrElse(feature, throw new  CustomExcept on())`
   *
   * @note for [[FeatureW hDefaultOnFa lure]], t  [[FeatureW hDefaultOnFa lure.defaultValue]]
   *       w ll be returned  f t  [[Feature]] fa led, but  f    s m ss ng/never hydrated,
   *       t n t  `default` prov ded  re w ll be used.
   */
  def getOrElse[Value](feature: Feature[_, Value], default: => Value): Value = {
    getOrElse(feature, default, So (default))
  }

  /**
   * Pr vate  lper for gett ng features from t  feature map, allow ng us to def ne a default
   * w n t  feature  s m ss ng from t  feature map, vs w n  s  n t  feature map but fa led.
   *  n t  case of fa led features,  f t  feature  s a [FeatureW hDefaultOnFa lure],   w ll
   * pr or  ze that default.
   * @param feature T  feature to retr eve
   * @param m ss ngDefault T  default value to use w n t  feature  s m ss ng from t  map.
   * @param fa lureDefault T  default value to use w n t  feature  s present but fa led.
   * @tparam Value T  value type of t  feature.
   * @return T  value stored  n t  map.
   */
  pr vate def getOrElse[Value](
    feature: Feature[_, Value],
    m ss ngDefault: => Value,
    fa lureDefault: => Opt on[Value]
  ): Value =
    underly ngMap.get(feature) match {
      case None => m ss ngDefault
      case So (Return(value)) => value.as nstanceOf[Value]
      case So (Throw(err)) =>
        feature match {
          case f: FeatureW hDefaultOnFa lure[_, Value] @unc cked => f.defaultValue
          case _ => fa lureDefault.getOrElse(throw err)
        }
    }

  /**
   * returns a new FeatureMap w h
   * - t  new Feature and Value pa r  f t  Feature was not present
   * - overr d ng t  prev ous Value  f that Feature was prev ously present
   */
  def +[V](key: Feature[_, V], value: V): FeatureMap =
    new FeatureMap(underly ngMap + (key -> Return(value)))

  /**
   * returns a new FeatureMap w h all t  ele nts of current FeatureMap and `ot r`.
   *
   * @note  f a [[Feature]] ex sts  n both maps, t  Value from `ot r` takes precedence
   */
  def ++(ot r: FeatureMap): FeatureMap = {
     f (ot r. sEmpty) {
      t 
    } else  f ( sEmpty) {
      ot r
    } else  f (t .getFeatures.conta ns(FSv1Feature) && ot r.getFeatures.conta ns(FSv1Feature)) {
      val  rgedResponse =
        FeatureStoreV1Response. rge(t .get(FSv1Feature), ot r.get(FSv1Feature))
      val  rgedResponseFeatureMap = FeatureMapBu lder().add(FSv1Feature,  rgedResponse).bu ld()
      new FeatureMap(underly ngMap ++ ot r.underly ngMap ++  rgedResponseFeatureMap.underly ngMap)
    } else {
      new FeatureMap(underly ngMap ++ ot r.underly ngMap)
    }
  }

  /** returns t  keySet of Features  n t  map */
  def getFeatures: Set[Feature[_, _]] = underly ngMap.keySet

  /**
   * T  Set of Features  n t  FeatureMap that have a successfully returned value. Fa led features
   * w ll obv ously not be  re.
   */
  def getSuccessfulFeatures: Set[Feature[_, _]] = underly ngMap.collect {
    case (feature, Return(_)) => feature
  }.toSet

  def  sEmpty: Boolean = underly ngMap. sEmpty

  overr de def toStr ng: Str ng = s"FeatureMap(${underly ngMap.toStr ng})"
}

object FeatureMap {
  // Restr ct access to t  apply  thod.
  // T  shouldn't be requ red after scala 2.13.2 (https://g hub.com/scala/scala/pull/7702)
  pr vate[feature] def apply(underly ngMap: Map[Feature[_, _], Try[_]]): FeatureMap =
    FeatureMap(underly ngMap)

  /**  rges an arb rary number of [[FeatureMap]]s from left-to-r ght */
  def  rge(featureMaps: TraversableOnce[FeatureMap]): FeatureMap = {
    val bu lder = FeatureMapBu lder()

    /**
     *  rge t  current [[FSv1Feature]] w h t  ex st ng accumulated one
     * and add t  rest of t  [[FeatureMap]]'s [[Feature]]s to t  `bu lder`
     */
    def  rge nternal(
      featureMap: FeatureMap,
      accumulatedFsV1Response: Opt on[FeatureStoreV1Response]
    ): Opt on[FeatureStoreV1Response] = {
       f (featureMap. sEmpty) {
        accumulatedFsV1Response
      } else {

        val currentFsV1Response =
           f (featureMap.getFeatures.conta ns(FSv1Feature))
            So (featureMap.get(FSv1Feature))
          else
            None

        val  rgedFsV1Response = (accumulatedFsV1Response, currentFsV1Response) match {
          case (So ( rged), So (current)) =>
            // both present so  rge t m
            So (FeatureStoreV1Response. rge( rged, current))
          case ( rged, current) =>
            // one or both are m ss ng so use wh c ver  s ava lable
             rged.orElse(current)
        }

        featureMap.underly ngMap.foreach {
          case (FSv1Feature, _) => // FSv1Feature  s only added once at t  very end
          case (feature, value) => bu lder.addW houtVal dat on(feature, value)
        }
         rgedFsV1Response
      }
    }

    featureMaps
      .foldLeft[Opt on[FeatureStoreV1Response]](None) {
        case (fsV1Response, featureMap) =>  rge nternal(featureMap, fsV1Response)
      }.foreach(
        // add  rged `FSv1Feature` to t  `bu lder` at t  end
        bu lder.add(FSv1Feature, _)
      )

    bu lder.bu ld()
  }

  val empty = new FeatureMap(Map.empty)
}
