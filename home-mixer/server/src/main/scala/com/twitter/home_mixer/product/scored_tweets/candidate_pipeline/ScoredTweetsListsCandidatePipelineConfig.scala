package com.twitter.home_mixer.product.scored_tweets.candidate_pipeline

import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.TweetypieStaticEntitiesFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.ReplyFilter
import com.twitter.home_mixer.functional_component.filter.RetweetFilter
import com.twitter.home_mixer.product.scored_tweets.candidate_source.ListsCandidateSource
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.ListIdsFeature
import com.twitter.home_mixer.product.scored_tweets.gate.MinCachedTweetsGate
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CandidatePipeline
import com.twitter.home_mixer.product.scored_tweets.response_transformer.ScoredTweetsListsResponseFeatureTransformer
import com.twitter.product_mixer.component_library.gate.NonEmptySeqFeatureGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.decider.DeciderParam
import com.twitter.timelineservice.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsListsCandidatePipelineConfig @Inject() (
  listsCandidateSource: ListsCandidateSource,
  tweetypieStaticEntitiesHydrator: TweetypieStaticEntitiesFeatureHydrator)
    extends CandidatePipelineConfig[
      ScoredTweetsQuery,
      Seq[t.TimelineQuery],
      t.Tweet,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsLists")

  private val MaxTweetsToFetchPerList = 20

  override val enabledDeciderParam: Option[DeciderParam[Boolean]] =
    Some(CandidatePipeline.EnableListsParam)

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    NonEmptySeqFeatureGate(ListIdsFeature),
    MinCachedTweetsGate(identifier, CachedScoredTweets.MinCachedTweetsParam)
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    Seq[t.TimelineQuery]
  ] = { query =>
    val listIds = query.features.map(_.get(ListIdsFeature)).get
    listIds.map { listId =>
      t.TimelineQuery(
        timelineType = t.TimelineType.List,
        timelineId = listId,
        maxCount = MaxTweetsToFetchPerList.toShort,
        options = Some(t.TimelineQueryOptions(query.clientContext.userId)),
        timelineId2 = Some(t.TimelineId(t.TimelineType.List, listId, None))
      )
    }
  }

  override def candidateSource: CandidateSource[Seq[t.TimelineQuery], t.Tweet] =
    listsCandidateSource

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.Tweet]
  ] = Seq(ScoredTweetsListsResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[t.Tweet, TweetCandidate] = {
    sourceResult => TweetCandidate(id = sourceResult.statusId)
  }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(tweetypieStaticEntitiesHydrator)

  override val filters: Seq[Filter[ScoredTweetsQuery, TweetCandidate]] =
    Seq(ReplyFilter, RetweetFilter)
}
