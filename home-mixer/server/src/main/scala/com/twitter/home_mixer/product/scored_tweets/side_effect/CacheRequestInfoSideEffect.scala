package com.twitter.home_mixer.product.scored_tweets.side_effect

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.home_mixer.model.HomeFeatures.IsReadFromCacheFeature
import com.twitter.home_mixer.model.PredictedFavoriteScoreFeature
import com.twitter.home_mixer.model.PredictedReplyScoreFeature
import com.twitter.home_mixer.model.PredictedRetweetScoreFeature
import com.twitter.home_mixer.model.PredictedShareScoreFeature
import com.twitter.home_mixer.model.PredictedVideoQualityViewScoreFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithLongTimeout
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableCacheRequestInfoParam
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Client
import com.twitter.strato.generated.client.home_mixer.StoreRequestInfoClientColumn
import com.twitter.strato.generated.client.home_mixer.StoreRequestInfoClientColumn.FavAndRetweetAndReplyAndShareAndVqv
import com.twitter.strato.generated.client.home_mixer.StoreRequestInfoClientColumn.PostIdAndHeavyRankerScores
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CacheRequestInfoSideEffect @Inject() (
  @Named(BatchedStratoClientWithLongTimeout) stratoClient: Client,
  serviceIdentifier: ServiceIdentifier)
    extends PipelineResultSideEffect[PipelineQuery, HasMarshalling]
    with Conditionally[PipelineQuery, HasMarshalling] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("CacheRequestInfo")

  private val isProdEnv = serviceIdentifier.environment == "prod"

  private val retrievalSignalExecutor = new StoreRequestInfoClientColumn(stratoClient).executer

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Boolean = query.params(EnableCacheRequestInfoParam) && isProdEnv

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, HasMarshalling]
  ): Stitch[Unit] = {
    val posts = inputs.selectedCandidates.collect {
      case candidate if !candidate.features.getOrElse(IsReadFromCacheFeature, false) =>
        PostIdAndHeavyRankerScores(
          postId = candidate.candidateIdLong,
          heavyRankerScores = Some(
            FavAndRetweetAndReplyAndShareAndVqv(
              fav = candidate.features.getOrElse(PredictedFavoriteScoreFeature, None),
              retweet = candidate.features.getOrElse(PredictedRetweetScoreFeature, None),
              reply = candidate.features.getOrElse(PredictedReplyScoreFeature, None),
              share = candidate.features.getOrElse(PredictedShareScoreFeature, None),
              vqv = candidate.features.getOrElse(PredictedVideoQualityViewScoreFeature, None)
            )
          )
        )
    }
    val arg = StoreRequestInfoClientColumn.Arg(
      userId = inputs.query.getRequiredUserId,
      posts = posts,
      requestTimestampMs = inputs.query.queryTime.inMilliseconds
    )
    retrievalSignalExecutor.execute(arg)
  }
}
