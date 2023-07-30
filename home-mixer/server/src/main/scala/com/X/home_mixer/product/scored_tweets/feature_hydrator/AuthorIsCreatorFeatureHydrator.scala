package com.X.home_mixer.product.scored_tweets.feature_hydrator

import com.X.finagle.stats.StatsReceiver
import com.X.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.AuthorIsCreatorFeature
import com.X.home_mixer.util.MissingKeyException
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.util.OffloadFuturePools
import com.X.stitch.Stitch
import com.X.strato.generated.client.audiencerewards.audienceRewardsService.GetSuperFollowEligibilityOnUserClientColumn
import com.X.util.Throw
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorIsCreatorFeatureHydrator @Inject() (
  getSuperFollowEligibilityOnUserClientColumn: GetSuperFollowEligibilityOnUserClientColumn,
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("AuthorIsCreator")

  override val features: Set[Feature[_, _]] =
    Set(AuthorIsCreatorFeature)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyFailureCounter = scopedStatsReceiver.counter("key/failure")

  private val MissingKeyFeatureMap = FeatureMapBuilder()
    .add(AuthorIsCreatorFeature, Throw(MissingKeyException))
    .build()

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(AuthorIsCreatorFeature, false)
    .build()

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    OffloadFuturePools.offloadStitch {
      val authorIds = candidates.flatMap(_.features.getOrElse(AuthorIdFeature, None)).distinct
      Stitch
        .collect {
          authorIds.map { authorId =>
            getSuperFollowEligibilityOnUserClientColumn.fetcher
              .fetch(authorId)
              .map { authorId -> _.v }
          }
        }.map { authorIdsToIsCreator =>
          val authorIdsToIsCreatorMap = authorIdsToIsCreator.toMap
          candidates.map { candidate =>
            candidate.features.getOrElse(AuthorIdFeature, None) match {
              case Some(authorId) =>
                authorIdsToIsCreatorMap.get(authorId) match {
                  case Some(response) =>
                    keyFoundCounter.incr()
                    FeatureMapBuilder()
                      .add(AuthorIsCreatorFeature, response.getOrElse(false)).build()
                  case _ =>
                    keyFailureCounter.incr()
                    DefaultFeatureMap
                }
              case _ => MissingKeyFeatureMap
            }
          }
        }
    }
  }
}
