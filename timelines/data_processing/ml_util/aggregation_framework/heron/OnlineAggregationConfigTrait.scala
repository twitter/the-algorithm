package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron

 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.TypedAggregateGroup
 mport com.tw ter.ml.ap .Feature

tra  Onl neAggregat onConf gTra  {
  def ProdAggregates: Set[TypedAggregateGroup[_]]
  def Stag ngAggregates: Set[TypedAggregateGroup[_]]
  def ProdCommonAggregates: Set[TypedAggregateGroup[_]]

  /**
   * AggregateToCompute: T  def nes t  complete set of aggregates to be
   *    computed by t  aggregat on job and to be stored  n  mcac .
   */
  def AggregatesToCompute: Set[TypedAggregateGroup[_]]

  /**
   * ProdFeatures: T  def nes t  subset of aggregates to be extracted
   *    and hydrated (or adapted) by callers to t  aggregates features cac .
   *    T  should only conta n product on aggregates and aggregates on
   *    product spec f c engage nts.
   * ProdCommonFeatures: S m lar to ProdFeatures but conta n ng user-level
   *    aggregate features. T   s prov ded to Pred ct onServ ce just
   *    once per user.
   */
  lazy val ProdFeatures: Set[Feature[_]] = ProdAggregates.flatMap(_.allOutputFeatures)
  lazy val ProdCommonFeatures: Set[Feature[_]] = ProdCommonAggregates.flatMap(_.allOutputFeatures)
}
