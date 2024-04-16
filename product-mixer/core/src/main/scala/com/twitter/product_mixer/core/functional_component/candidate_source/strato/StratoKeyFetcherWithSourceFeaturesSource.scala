package com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ceW hExtractedFeatures
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand datesW hS ceFeatures
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r

/**
 * A [[Cand dateS ce]] for gett ng Cand dates from Strato w re t 
 * Strato column's V ew  s [[Un ]] and t  Value  s a [[StratoValue]]
 *
 * A [[stratoResultTransfor r]] must be def ned to convert t 
 * [[StratoValue]]  nto a Seq of [[Cand date]]
 *
 * A [[extractFeaturesFromStratoResult]] must be def ned to extract a
 * [[FeatureMap]] from t  [[StratoValue]].  f   don't need to do that,
 * use a [[StratoKeyFetc rS ce]]  nstead.
 *
 * @tparam StratoKey t  column's Key type
 * @tparam StratoValue t  column's Value type
 */
tra  StratoKeyFetc rW hS ceFeaturesS ce[StratoKey, StratoValue, Cand date]
    extends Cand dateS ceW hExtractedFeatures[StratoKey, Cand date] {

  val fetc r: Fetc r[StratoKey, Un , StratoValue]

  /**
   * Transforms t  value type returned by Strato  nto a Seq[Cand date].
   *
   * T  m ght be as s mple as `Seq(stratoResult)`  f   always return ng a s ngle cand date.
   *
   * Often,   just extracts a Seq from w h n a larger wrapper object.
   *
   *  f t re  s global  tadata that   need to  nclude, see [[extractFeaturesFromStratoResult]]
   * below to put that  nto a Feature.
   */
  protected def stratoResultTransfor r(stratoResult: StratoValue): Seq[Cand date]

  /***
   * Transforms t  value type returned by Strato  nto a FeatureMap.
   *
   * Overr de t  to extract global  tadata l ke cursors and place t  results
   *  nto a Feature.
   *
   * For example, a cursor.
   */
  protected def extractFeaturesFromStratoResult(stratoResult: StratoValue): FeatureMap

  overr de def apply(key: StratoKey): St ch[Cand datesW hS ceFeatures[Cand date]] = {
    fetc r
      .fetch(key)
      .map { result =>
        val cand dates = result.v
          .map(stratoResultTransfor r)
          .getOrElse(Seq.empty)

        val features = result.v
          .map(extractFeaturesFromStratoResult)
          .getOrElse(FeatureMap.empty)

        Cand datesW hS ceFeatures(cand dates, features)
      }.rescue(StratoErrCategor zer.Categor zeStratoExcept on)
  }
}
