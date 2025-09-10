package com.twitter.home_mixer.candidate_pipeline

import com.twitter.home_mixer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.twitter.home_mixer.functional_component.feature_hydrator.NamesFeatureHydrator
import com.twitter.home_mixer.model.HomeFeatures.PersistenceEntriesFeature
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.product_mixer.component_library.candidate_source.stale_tweets.StaleTweetsCacheCandidateSource
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.contextual_ref.ContextualTweetRefBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.EmptyClientEventInfoBuilder
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.transformer.stale_tweets.EditedTweetsCandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.rtf.safety_level.TimelineFocalTweetSafetyLevel
import com.twitter.product_mixer.core.model.marshalling.response.urt.contextual_ref.TweetHydrationContext
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.DependentCandidatePipelineConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Candidate Pipeline Config that fetches edited tweets from the Stale Tweets Cache
 */
@Singleton
case class EditedTweetsCandidatePipelineConfig @Inject() (
  staleTweetsCacheCandidateSource: StaleTweetsCacheCandidateSource,
  namesFeatureHydrator: NamesFeatureHydrator,
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder)
    extends DependentCandidatePipelineConfig[
      PipelineQuery,
      Seq[Long],
      Long,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("EditedTweets")

  override val candidateSource: BaseCandidateSource[Seq[Long], Long] =
    staleTweetsCacheCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[PipelineQuery, Seq[Long]] =
    EditedTweetsCandidatePipelineQueryTransformer(PersistenceEntriesFeature)

  override val resultTransformer: CandidatePipelineResultsTransformer[Long, TweetCandidate] = {
    candidate => TweetCandidate(id = candidate)
  }

  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[PipelineQuery, TweetCandidate, _]
  ] = Seq(namesFeatureHydrator)

  override val decorator: Option[CandidateDecorator[PipelineQuery, TweetCandidate]] = {
    val tweetItemBuilder = TweetCandidateUrtItemBuilder[PipelineQuery, TweetCandidate](
      clientEventInfoBuilder = EmptyClientEventInfoBuilder,
      entryIdToReplaceBuilder = Some((_, candidate, _) =>
        Some(s"${TweetItem.TweetEntryNamespace}-${candidate.id.toString}")),
      contextualTweetRefBuilder = Some(
        ContextualTweetRefBuilder(
          TweetHydrationContext(
            // Apply safety level that includes canonical VF treatments that apply regardless of context.
            safetyLevelOverride = Some(TimelineFocalTweetSafetyLevel),
            outerTweetContext = None
          )
        )
      ),
      feedbackActionInfoBuilder = Some(homeFeedbackActionInfoBuilder)
    )

    Some(UrtItemCandidateDecorator(tweetItemBuilder))
  }

  override val alerts =
    Seq(HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.5, 50, 60, 60))
}
