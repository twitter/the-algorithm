package com.tw ter.product_m xer.core.feature.featuremap.asyncfeaturemap

 mport com.fasterxml.jackson.datab nd.annotat on.JsonSer al ze
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.st ch.St ch

 mport scala.collect on. mmutable.Queue

/**
 * An  nternal representat on of an async [[FeatureMap]] conta n ng [[St ch]]s of [[FeatureMap]]s
 * wh ch are already runn ng  n t  background.
 *
 * Async features are added by prov d ng t  [[P pel neStep dent f er]] of t  [[com.tw ter.product_m xer.core.p pel ne.P pel neBu lder.Step Step]]
 * before wh ch t  async [[Feature]]s are needed, and a [[St ch]] of t  async [[FeatureMap]].
 *  's expected that t  [[St ch]] has already been started and  s runn ng  n t  background.
 *
 * Wh le not essent al to  's core behav or, [[AsyncFeatureMap]] also keeps track of t  [[FeatureHydrator dent f er]]
 * and t  Set of [[Feature]]s wh ch w ll be hydrated for each [[St ch]] of a [[FeatureMap]]  's g ven.
 *
 * @param asyncFeatureMaps t  [[FeatureMap]]s for [[P pel neStep dent f er]]s wh ch have not been reac d yet
 *
 * @note [[P pel neStep dent f er]]s must only refer to [[com.tw ter.product_m xer.core.p pel ne.P pel neBu lder.Step Step]]s
 *        n t  current [[com.tw ter.product_m xer.core.p pel ne.P pel ne P pel ne]].
 *       Only pla n [[FeatureMap]]s are passed  nto underly ng [[com.tw ter.product_m xer.core.model.common.Component Component]]s and
 *       [[com.tw ter.product_m xer.core.p pel ne.P pel ne P pel ne]]s so [[AsyncFeatureMap]]s are scoped
 *       for a spec f c [[com.tw ter.product_m xer.core.p pel ne.P pel ne P pel ne]] only.
 */
@JsonSer al ze(us ng = classOf[AsyncFeatureMapSer al zer])
pr vate[core] case class AsyncFeatureMap(
  asyncFeatureMaps: Map[P pel neStep dent f er, Queue[
    (FeatureHydrator dent f er, Set[Feature[_, _]], St ch[FeatureMap])
  ]]) {

  def ++(r ght: AsyncFeatureMap): AsyncFeatureMap = {
    val map = Map.newBu lder[
      P pel neStep dent f er,
      Queue[(FeatureHydrator dent f er, Set[Feature[_, _]], St ch[FeatureMap])]]
    (asyncFeatureMaps.keys erator ++ r ght.asyncFeatureMaps.keys erator).foreach { key =>
      val currentT nR ghtAsyncFeatureMaps =
        asyncFeatureMaps.getOrElse(key, Queue.empty) ++
          r ght.asyncFeatureMaps.getOrElse(key, Queue.empty)
      map += (key -> currentT nR ghtAsyncFeatureMaps)
    }
    AsyncFeatureMap(map.result())
  }

  /**
   * Returns a new [[AsyncFeatureMap]] wh ch now keeps track of t  prov ded `features`
   * and w ll make t m ava lable w n call ng [[hydrate]] w h `hydrateBefore`.
   *
   * @param featureHydrator dent f er t  [[FeatureHydrator dent f er]] of t  [[com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.FeatureHydrator FeatureHydrator]]
   *                                  wh ch t se [[Feature]]s are from
   * @param hydrateBefore             t  [[P pel neStep dent f er]] before wh ch t  [[Feature]]s need to be hydrated
   * @param featuresToHydrate         a Set of t  [[Feature]]s wh ch w ll be hydrated
   * @param features                  a [[St ch]] of t  [[FeatureMap]]
   */
  def addAsyncFeatures(
    featureHydrator dent f er: FeatureHydrator dent f er,
    hydrateBefore: P pel neStep dent f er,
    featuresToHydrate: Set[Feature[_, _]],
    features: St ch[FeatureMap]
  ): AsyncFeatureMap = {
    val featureMapL st =
      asyncFeatureMaps.getOrElse(hydrateBefore, Queue.empty) :+
        ((featureHydrator dent f er, featuresToHydrate, features))
    AsyncFeatureMap(asyncFeatureMaps + (hydrateBefore -> featureMapL st))
  }

  /**
   * T  current state of t  [[AsyncFeatureMap]] exclud ng t  [[St ch]]s.
   */
  def features: Map[P pel neStep dent f er, Seq[(FeatureHydrator dent f er, Set[Feature[_, _]])]] =
    asyncFeatureMaps.mapValues(_.map {
      case (featureHydrator dent f er, features, _) => (featureHydrator dent f er, features)
    })

  /**
   * Returns a [[So ]] conta n ng a [[St ch]] w h a [[FeatureMap]] hold ng t  [[Feature]]s that are
   * supposed to be hydrated at ` dent f er`  f t re are [[Feature]]s to hydrate at ` dent f er`
   *
   * Returns [[None]]  f t re are no [[Feature]]s to hydrate at t  prov ded ` dent f er`,
   * t  allows for determ n ng  f t re  s work to do w hout runn ng a [[St ch]].
   *
   * @note t  only hydrates t  [[Feature]]s for t  spec f c ` dent f er`,   does not hydrate
   *       [[Feature]]s for earl er Steps.
   * @param  dent f er t  [[P pel neStep dent f er]] to hydrate [[Feature]]s for
   */
  def hydrate(
     dent f er: P pel neStep dent f er
  ): Opt on[St ch[FeatureMap]] =
    asyncFeatureMaps.get( dent f er) match {
      case So (Queue((_, _, featureMap))) =>
        //  f t re  s only 1 `FeatureMap`   dont need to do a collect so just return that St ch
        So (featureMap)
      case So (featureMapL st) =>
        //  f t re are mult ple `FeatureMap`s   need to collect and  rge t m toget r
        So (
          St ch
            .collect(featureMapL st.map { case (_, _, featureMap) => featureMap })
            .map { featureMapL st => FeatureMap. rge(featureMapL st) })
      case None =>
        // No results for t  prov ded ` dent f er` so return `None`
        None
    }
}

pr vate[core] object AsyncFeatureMap {
  val empty: AsyncFeatureMap = AsyncFeatureMap(Map.empty)

  /**
   * Bu lds t  an [[AsyncFeatureMap]] from a Seq of [[St ch]] of [[FeatureMap]]
   * tupled w h t  relevant  tadata   use to bu ld t  necessary state.
   *
   * T   s pr mar ly for conven ence, s nce  n most cases an [[AsyncFeatureMap]]
   * w ll be bu lt from t  result of  nd v dual [[com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.FeatureHydrator FeatureHydrator]]s
   * and comb n ng t m  nto t  correct  nternal state.
   */
  def fromFeatureMaps(
    asyncFeatureMaps: Seq[
      (FeatureHydrator dent f er, P pel neStep dent f er, Set[Feature[_, _]], St ch[FeatureMap])
    ]
  ): AsyncFeatureMap =
    AsyncFeatureMap(
      asyncFeatureMaps
        .groupBy { case (_, hydrateBefore, _, _) => hydrateBefore }
        .mapValues(featureMaps =>
          Queue(featureMaps.map {
            case (hydrator dent f er, _, featuresToHydrate, st ch) =>
              (hydrator dent f er, featuresToHydrate, st ch)
          }: _*)))
}
