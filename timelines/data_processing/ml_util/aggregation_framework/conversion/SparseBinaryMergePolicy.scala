package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.convers on

 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.TypedAggregateGroup
 mport scala.collect on.JavaConverters._

/**
 * W n us ng t  aggregates fra work to group by sparse b nary keys,
 *   generate d fferent aggregate feature values for each poss ble
 * value of t  sparse key.  nce, w n jo n ng back t  aggregate
 * features w h a tra n ng data set, each  nd v dual tra n ng record
 * has mult ple aggregate features to choose from, for each value taken
 * by t  sparse key(s)  n t  tra n ng record. T   rge pol cy tra 
 * below spec f es how to condense/comb ne t  var able number of
 * aggregate features  nto a constant number of features for tra n ng.
 * So  s mple pol c es m ght be: p ck t  f rst feature set (randomly),
 * p ck t  top sorted by so  attr bute, or take so  average.
 *
 * Example: suppose   group by (ADVERT SER_ D,  NTEREST_ D) w re  NTEREST_ D
 *  s t  sparse key, and compute a "CTR" aggregate feature for each such
 * pa r  asur ng t  cl ck through rate on ads w h (ADVERT SER_ D,  NTEREST_ D).
 * Say   have t  follow ng aggregate records:
 *
 * (ADVERT SER_ D = 1,  NTEREST_ D = 1, CTR = 5%)
 * (ADVERT SER_ D = 1,  NTEREST_ D = 2, CTR = 15%)
 * (ADVERT SER_ D = 2,  NTEREST_ D = 1, CTR = 1%)
 * (ADVERT SER_ D = 2,  NTEREST_ D = 2, CTR = 10%)
 * ...
 * At tra n ng t  , each tra n ng record has one value for ADVERT SER_ D, but  
 * has mult ple values for  NTEREST_ D e.g.
 *
 * (ADVERT SER_ D = 1,  NTEREST_ DS = (1,2))
 *
 * T re are mult ple potent al CTRs   can get w n jo n ng  n t  aggregate features:
 *  n t  case 2 values (5% and 15%) but  n general   could be many depend ng on how
 * many  nterests t  user has. W n jo n ng back t  CTR features, t   rge pol cy says how to
 * comb ne all t se CTRs to eng neer features.
 *
 * "P ck f rst" would say - p ck so  random CTR (whatever  s f rst  n t  l st, maybe 5%)
 * for tra n ng (probably not a good pol cy). "Sort by CTR" could be a pol cy
 * that just p cks t  top CTR and uses   as a feature ( re 15%). S m larly,   could
 *  mag ne "Top K sorted by CTR" (use both 5 and 15%) or "Avg CTR" (10%) or ot r pol c es,
 * all of wh ch are def ned as objects/case classes that overr de t  tra .
 */
tra  SparseB nary rgePol cy {

  /**
   * @param mutable nputRecord  nput record to add aggregates to
   * @param aggregateRecords Aggregate feature records
   * @param aggregateContext Context for aggregate records
   */
  def  rgeRecord(
    mutable nputRecord: DataRecord,
    aggregateRecords: L st[DataRecord],
    aggregateContext: FeatureContext
  ): Un 

  def aggregateFeaturesPost rge(aggregateContext: FeatureContext): Set[Feature[_]]

  /**
   * @param  nputContext Context for  nput record
   * @param aggregateContext Context for aggregate records
   * @return Context for record returned by  rgeRecord()
   */
  def  rgeContext(
     nputContext: FeatureContext,
    aggregateContext: FeatureContext
  ): FeatureContext = new FeatureContext(
    ( nputContext.getAllFeatures.asScala.toSet ++ aggregateFeaturesPost rge(
      aggregateContext)).toSeq.asJava
  )

  def allOutputFeaturesPost rgePol cy[T](conf g: TypedAggregateGroup[T]): Set[Feature[_]] = {
    val conta nsSparseB nary = conf g.keysToAggregate
      .ex sts(_.getFeatureType == FeatureType.SPARSE_B NARY)

     f (!conta nsSparseB nary) conf g.allOutputFeatures
    else aggregateFeaturesPost rge(new FeatureContext(conf g.allOutputFeatures.toSeq.asJava))
  }
}
