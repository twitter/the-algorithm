package com.tw ter.t  l nes.pred ct on.common.aggregates

 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.Aggregat onConf g
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateGroup
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.TypedAggregateGroup

tra  T  l nesAggregat onConf gTra 
    extends T  l nesAggregat onConf gDeta ls
    w h Aggregat onConf g {
  pr vate val aggregateGroups = Set(
    authorTop cAggregates,
    userTop cAggregates,
    userTop cAggregatesV2,
    user nferredTop cAggregates,
    user nferredTop cAggregatesV2,
    userAggregatesV2,
    userAggregatesV5Cont nuous,
    userRec procalEngage ntAggregates,
    userAuthorAggregatesV5,
    userOr g nalAuthorRec procalEngage ntAggregates,
    or g nalAuthorRec procalEngage ntAggregates,
    t etS ceUserAuthorAggregatesV1,
    userEngagerAggregates,
    user nt onAggregates,
    tw terW deUserAggregates,
    tw terW deUserAuthorAggregates,
    userRequestH Aggregates,
    userRequestDowAggregates,
    userL stAggregates,
    user d aUnderstand ngAnnotat onAggregates,
  ) ++ userAuthorAggregatesV2

  val aggregatesToComputeL st: Set[L st[TypedAggregateGroup[_]]] =
    aggregateGroups.map(_.bu ldTypedAggregateGroups())

  overr de val aggregatesToCompute: Set[TypedAggregateGroup[_]] = aggregatesToComputeL st.flatten

  /*
   * Feature select on conf g to save storage space and manhattan query bandw dth.
   * Only t  most  mportant features found us ng offl ne RCE s mulat ons are used
   * w n actually tra n ng and serv ng. T  selector  s used by
   * [[com.tw ter.t  l nes.data_process ng.jobs.t  l ne_rank ng_user_features.T  l neRank ngAggregatesV2FeaturesProdJob]]
   * but def ned  re to keep    n sync w h t  conf g that computes t  aggregates.
   */
  val AggregatesV2FeatureSelector = FeatureSelectorConf g.AggregatesV2ProdFeatureSelector

  def f lterAggregatesGroups(storeNa s: Set[Str ng]): Set[AggregateGroup] = {
    aggregateGroups.f lter(aggregateGroup => storeNa s.conta ns(aggregateGroup.outputStore.na ))
  }
}
