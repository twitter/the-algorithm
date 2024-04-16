package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work

 mport com.tw ter.ml.ap ._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Aggregat on tr c
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Easy tr c
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Max tr c
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.OneToSo Transform
 mport com.tw ter.ut l.Durat on
 mport java.lang.{Boolean => JBoolean}
 mport java.lang.{Long => JLong}
 mport scala.language.ex stent als

/**
 * A wrapper for [[com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.TypedAggregateGroup]]
 * (see TypedAggregateGroup.scala) w h so  conven ent syntact c sugar that avo ds
 * t  user hav ng to spec fy d fferent groups for d fferent types of features.
 * Gets translated  nto mult ple strongly typed TypedAggregateGroup(s)
 * by t  bu ldTypedAggregateGroups()  thod def ned below.
 *
 * @param  nputS ce S ce to compute t  aggregate over
 * @param preTransforms Sequence of [[ Transform]] that  s appl ed to
 * data records pre-aggregat on (e.g. d scret zat on, renam ng)
 * @param sampl ngTransformOpt Opt onal [[OneToSo Transform]] that samples data record
 * @param aggregatePref x Pref x to use for nam ng resultant aggregate features
 * @param keys Features to group by w n comput ng t  aggregates
 * (e.g. USER_ D, AUTHOR_ D). T se must be e  r d screte, str ng or sparse b nary.
 * Group ng by a sparse b nary feature  s d fferent than group ng by a d screte or str ng
 * feature. For example,  f   have a sparse b nary feature WORDS_ N_TWEET wh ch  s
 * a set of all words  n a t et, t n group ng by t  feature generates a
 * separate aggregate  an/count/etc for each value of t  feature (each word), and
 * not just a s ngle aggregate count for d fferent "sets of words"
 * @param features Features to aggregate (e.g. blender_score or  s_photo).
 * @param labels Labels to cross t  features w h to make pa r features,  f any.
 * @param  tr cs Aggregat on  tr cs to compute (e.g. count,  an)
 * @param halfL ves Half l ves to use for t  aggregat ons, to be crossed w h t  above.
 * use Durat on.Top for "forever" aggregat ons over an  nf n e t   w ndow (no decay).
 * @param outputStore Store to output t  aggregate to
 * @param  ncludeAnyFeature Aggregate label counts for any feature value
 * @param  ncludeAnyLabel Aggregate feature counts for any label value (e.g. all  mpress ons)
 * @param  ncludeT  stampFeature compute max aggregate on t  stamp feature
 * @param aggExclus onRegex Sequence of Regexes, wh ch def ne features to
 */
case class AggregateGroup(
   nputS ce: AggregateS ce,
  aggregatePref x: Str ng,
  keys: Set[Feature[_]],
  features: Set[Feature[_]],
  labels: Set[_ <: Feature[JBoolean]],
   tr cs: Set[Easy tr c],
  halfL ves: Set[Durat on],
  outputStore: AggregateStore,
  preTransforms: Seq[OneToSo Transform] = Seq.empty,
   ncludeAnyFeature: Boolean = true,
   ncludeAnyLabel: Boolean = true,
   ncludeT  stampFeature: Boolean = false,
  aggExclus onRegex: Seq[Str ng] = Seq.empty) {

  pr vate def toStrongType[T](
     tr cs: Set[Easy tr c],
    features: Set[Feature[_]],
    featureType: FeatureType
  ): TypedAggregateGroup[_] = {
    val underly ng tr cs: Set[Aggregat on tr c[T, _]] =
       tr cs.flatMap(_.forFeatureType[T](featureType))
    val underly ngFeatures: Set[Feature[T]] = features
      .map(_.as nstanceOf[Feature[T]])

    TypedAggregateGroup[T](
       nputS ce =  nputS ce,
      aggregatePref x = aggregatePref x,
      keysToAggregate = keys,
      featuresToAggregate = underly ngFeatures,
      labels = labels,
       tr cs = underly ng tr cs,
      halfL ves = halfL ves,
      outputStore = outputStore,
      preTransforms = preTransforms,
       ncludeAnyFeature,
       ncludeAnyLabel,
      aggExclus onRegex
    )
  }

  pr vate def t  stampTypedAggregateGroup: TypedAggregateGroup[_] = {
    val  tr cs: Set[Aggregat on tr c[JLong, _]] =
      Set(Max tr c.forFeatureType[JLong](TypedAggregateGroup.t  stampFeature.getFeatureType).get)

    TypedAggregateGroup[JLong](
       nputS ce =  nputS ce,
      aggregatePref x = aggregatePref x,
      keysToAggregate = keys,
      featuresToAggregate = Set(TypedAggregateGroup.t  stampFeature),
      labels = Set.empty,
       tr cs =  tr cs,
      halfL ves = Set(Durat on.Top),
      outputStore = outputStore,
      preTransforms = preTransforms,
       ncludeAnyFeature = false,
       ncludeAnyLabel = true,
      aggExclus onRegex = Seq.empty
    )
  }

  def bu ldTypedAggregateGroups(): L st[TypedAggregateGroup[_]] = {
    val typedAggregateGroupsL st = {
       f (features. sEmpty) {
        L st(toStrongType( tr cs, features, FeatureType.B NARY))
      } else {
        features
          .groupBy(_.getFeatureType())
          .toL st
          .map {
            case (featureType, features) =>
              toStrongType( tr cs, features, featureType)
          }
      }
    }

    val opt onalT  stampTypedAggregateGroup =
       f ( ncludeT  stampFeature) L st(t  stampTypedAggregateGroup) else L st()

    typedAggregateGroupsL st ++ opt onalT  stampTypedAggregateGroup
  }
}
