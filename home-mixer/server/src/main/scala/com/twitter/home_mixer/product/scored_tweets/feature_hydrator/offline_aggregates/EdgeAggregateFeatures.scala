package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates

 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.offl ne_aggregates.PassThroughAdapter
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.offl ne_aggregates.SparseAggregatesToDenseAdapter
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nt onScreenNa Feature
 mport com.tw ter.ho _m xer.model.Ho Features.Top c dSoc alContextFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.TSP nferredTop cFeature
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateType
 mport com.tw ter.t  l nes.pred ct on.common.aggregates.T  l nesAggregat onConf g
 mport com.tw ter.t  l nes.pred ct on.common.aggregates.T  l nesAggregat onConf g.Comb neCountPol c es

object EdgeAggregateFeatures {

  object UserAuthorAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = T  l nesAggregat onConf g.userAuthorAggregatesV2 ++ Set(
          T  l nesAggregat onConf g.userAuthorAggregatesV5,
          T  l nesAggregat onConf g.t etS ceUserAuthorAggregatesV1,
          T  l nesAggregat onConf g.tw terW deUserAuthorAggregates
        ),
        aggregateType = AggregateType.UserAuthor,
        extractMapFn = _.userAuthorAggregates,
        adapter = PassThroughAdapter,
        getSecondaryKeysFn = _.features.getOrElse(Author dFeature, None).toSeq
      )

  object UserOr g nalAuthorAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(T  l nesAggregat onConf g.userOr g nalAuthorAggregatesV1),
        aggregateType = AggregateType.UserOr g nalAuthor,
        extractMapFn = _.userOr g nalAuthorAggregates,
        adapter = PassThroughAdapter,
        getSecondaryKeysFn = cand date =>
          Cand datesUt l.getOr g nalAuthor d(cand date.features).toSeq
      )

  object UserTop cAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(
          T  l nesAggregat onConf g.userTop cAggregates,
          T  l nesAggregat onConf g.userTop cAggregatesV2,
        ),
        aggregateType = AggregateType.UserTop c,
        extractMapFn = _.userTop cAggregates,
        adapter = PassThroughAdapter,
        getSecondaryKeysFn = cand date =>
          cand date.features.getOrElse(Top c dSoc alContextFeature, None).toSeq
      )

  object User nt onAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(T  l nesAggregat onConf g.user nt onAggregates),
        aggregateType = AggregateType.User nt on,
        extractMapFn = _.user nt onAggregates,
        adapter = new SparseAggregatesToDenseAdapter(Comb neCountPol c es. nt onCountsPol cy),
        getSecondaryKeysFn = cand date =>
          cand date.features.getOrElse( nt onScreenNa Feature, Seq.empty).map(_.hashCode.toLong)
      )

  object User nferredTop cAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(
          T  l nesAggregat onConf g.user nferredTop cAggregates,
        ),
        aggregateType = AggregateType.User nferredTop c,
        extractMapFn = _.user nferredTop cAggregates,
        adapter = new SparseAggregatesToDenseAdapter(
          Comb neCountPol c es.User nferredTop cCountsPol cy),
        getSecondaryKeysFn = cand date =>
          cand date.features.getOrElse(TSP nferredTop cFeature, Map.empty[Long, Double]).keys.toSeq
      )

  object User nferredTop cAggregateV2Feature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(
          T  l nesAggregat onConf g.user nferredTop cAggregatesV2
        ),
        aggregateType = AggregateType.User nferredTop c,
        extractMapFn = _.user nferredTop cAggregates,
        adapter = new SparseAggregatesToDenseAdapter(
          Comb neCountPol c es.User nferredTop cV2CountsPol cy),
        getSecondaryKeysFn = cand date =>
          cand date.features.getOrElse(TSP nferredTop cFeature, Map.empty[Long, Double]).keys.toSeq
      )

  object User d aUnderstand ngAnnotat onAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(
          T  l nesAggregat onConf g.user d aUnderstand ngAnnotat onAggregates),
        aggregateType = AggregateType.User d aUnderstand ngAnnotat on,
        extractMapFn = _.user d aUnderstand ngAnnotat onAggregates,
        adapter = new SparseAggregatesToDenseAdapter(
          Comb neCountPol c es.User d aUnderstand ngAnnotat onCountsPol cy),
        getSecondaryKeysFn = cand date =>
          Cand datesUt l.get d aUnderstand ngAnnotat on ds(cand date.features)
      )

  object UserEngagerAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(T  l nesAggregat onConf g.userEngagerAggregates),
        aggregateType = AggregateType.UserEngager,
        extractMapFn = _.userEngagerAggregates,
        adapter = new SparseAggregatesToDenseAdapter(Comb neCountPol c es.EngagerCountsPol cy),
        getSecondaryKeysFn = cand date => Cand datesUt l.getEngagerUser ds(cand date.features)
      )

  object UserEngagerGoodCl ckAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(T  l nesAggregat onConf g.userEngagerGoodCl ckAggregates),
        aggregateType = AggregateType.UserEngager,
        extractMapFn = _.userEngagerAggregates,
        adapter = new SparseAggregatesToDenseAdapter(
          Comb neCountPol c es.EngagerGoodCl ckCountsPol cy),
        getSecondaryKeysFn = cand date => Cand datesUt l.getEngagerUser ds(cand date.features)
      )
}
