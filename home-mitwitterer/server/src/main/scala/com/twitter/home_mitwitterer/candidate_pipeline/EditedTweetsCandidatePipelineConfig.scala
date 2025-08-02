package com.twitter.home_mitwitterer.candidate_pipeline

import com.twitter.home_mitwitterer.functional_component.candidate_source.StaleTweetsCacheCandidateSource
import com.twitter.home_mitwitterer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.twitter.home_mitwitterer.functional_component.feature_hydrator.NamesFeatureHydrator
import com.twitter.home_mitwitterer.functional_component.query_transformer.EditedTweetsCandidatePipelineQueryTransformer
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.contetwittertual_ref.ContetwittertualTweetRefBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.metadata.EmptyClientEventInfoBuilder
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.response.rtf.safety_level.TimelineFocalTweetSafetyLevel
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.contetwittertual_ref.TweetHydrationContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.candidate.DependentCandidatePipelineConfig
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Candidate Pipeline Config that fetches edited tweets from the Stale Tweets Cache
 */
@Singleton
case class EditedTweetsCandidatePipelineConfig @Inject() (
  staleTweetsCacheCandidateSource: StaleTweetsCacheCandidateSource,
  namesFeatureHydrator: NamesFeatureHydrator,
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder)
    etwittertends DependentCandidatePipelineConfig[
      PipelineQuery,
      Seq[Long],
      Long,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("EditedTweets")

  override val candidateSource: BaseCandidateSource[Seq[Long], Long] =
    staleTweetsCacheCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    Seq[Long]
  ] = EditedTweetsCandidatePipelineQueryTransformer

  override val resultTransformer: CandidatePipelineResultsTransformer[
    Long,
    TweetCandidate
  ] = { candidate => TweetCandidate(id = candidate) }

  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[PipelineQuery, TweetCandidate, _]
  ] = Seq(namesFeatureHydrator)

  override val decorator: Option[CandidateDecorator[PipelineQuery, TweetCandidate]] = {
    val tweetItemBuilder = TweetCandidateUrtItemBuilder[PipelineQuery, TweetCandidate](
      clientEventInfoBuilder = EmptyClientEventInfoBuilder,
      entryIdToReplaceBuilder = Some((_, candidate, _) =>
        Some(s"${TweetItem.TweetEntryNamespace}-${candidate.id.toString}")),
      contetwittertualTweetRefBuilder = Some(
        ContetwittertualTweetRefBuilder(
          TweetHydrationContetwittert(
            // Apply safety level that includes canonical VF treatments that apply regardless of contetwittert.
            safetyLevelOverride = Some(TimelineFocalTweetSafetyLevel),
            outerTweetContetwittert = None
          )
        )
      ),
      feedbackActionInfoBuilder = Some(homeFeedbackActionInfoBuilder)
    )

    Some(UrtItemCandidateDecorator(tweetItemBuilder))
  }

  override val alerts = Seq(
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(99.5, 50, 60, 60)
  )
}
