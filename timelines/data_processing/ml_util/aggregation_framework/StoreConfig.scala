package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work

 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureType

/**
 * Conven ence class to descr be t  stores that make up a part cular type of aggregate.
 *
 * For example, as of 2018/07, user aggregates are generate by  rg ng t   nd v dual
 * "user_aggregates", "rect et_user_aggregates", and, "tw ter_w de_user_aggregates".
 *
 * @param storeNa s Na  of t  stores.
 * @param aggregateType Type of aggregate, usually d fferent ated by t  aggregat on key.
 * @param shouldHash Used at T  l neRank ngAggregatesUt l.extractSecondary w n extract ng t 
 *                   secondary key value.
 */
case class StoreConf g[T](
  storeNa s: Set[Str ng],
  aggregateType: AggregateType.Value,
  shouldHash: Boolean = false
)(
   mpl c  store rger: Store rger) {
  requ re(store rger. sVal dTo rge(storeNa s))

  pr vate val representat veStore = storeNa s. ad

  val aggregat onKey ds: Set[Long] = store rger.getAggregateKeys(representat veStore)
  val aggregat onKeyFeatures: Set[Feature[_]] =
    store rger.getAggregateKeyFeatures(representat veStore)
  val secondaryKeyFeatureOpt: Opt on[Feature[_]] = store rger.getSecondaryKey(representat veStore)
}

tra  Store rger {
  def aggregat onConf g: Aggregat onConf g

  def getAggregateKeyFeatures(storeNa : Str ng): Set[Feature[_]] =
    aggregat onConf g.aggregatesToCompute
      .f lter(_.outputStore.na  == storeNa )
      .flatMap(_.keysToAggregate)

  def getAggregateKeys(storeNa : Str ng): Set[Long] =
    TypedAggregateGroup.getKeyFeature ds(getAggregateKeyFeatures(storeNa ))

  def getSecondaryKey(storeNa : Str ng): Opt on[Feature[_]] = {
    val keys = getAggregateKeyFeatures(storeNa )
    requ re(keys.s ze <= 2, "Only s ngleton or b nary aggregat on keys are supported.")
    requ re(keys.conta ns(SharedFeatures.USER_ D), "USER_ D must be one of t  aggregat on keys.")
    keys
      .f lterNot(_ == SharedFeatures.USER_ D)
      . adOpt on
      .map { poss blySparseKey =>
         f (poss blySparseKey.getFeatureType != FeatureType.SPARSE_B NARY) {
          poss blySparseKey
        } else {
          TypedAggregateGroup.sparseFeature(poss blySparseKey)
        }
      }
  }

  /**
   * Stores may only be  rged  f t y have t  sa  aggregat on key.
   */
  def  sVal dTo rge(storeNa s: Set[Str ng]): Boolean = {
    val expectedKeyOpt = storeNa s. adOpt on.map(getAggregateKeys)
    storeNa s.forall(v => getAggregateKeys(v) == expectedKeyOpt.get)
  }
}
