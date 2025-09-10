package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.content_understanding.GroxUserInterestsMhClientColumn
import javax.inject.Inject
import javax.inject.Singleton

object UserInterestSummaryEmbeddingFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Seq[Seq[Double]]]] {
  override def defaultValue: Option[Seq[Seq[Double]]] = None
}

@Singleton
class UserInterestSummaryEmbeddingQueryFeatureHydratorFactory @Inject() (
  embeddingClientColumn: GroxUserInterestsMhClientColumn) {

  def build(): UserInterestSummaryEmbeddingQueryFeatureHydrator = {
    new UserInterestSummaryEmbeddingQueryFeatureHydrator(
      embeddingClientColumn
    )
  }
}

class UserInterestSummaryEmbeddingQueryFeatureHydrator(
  groxUserInterestsMhClientColumn: GroxUserInterestsMhClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserInterestSummaryEmbedding")

  override val features: Set[Feature[_, _]] = Set(UserInterestSummaryEmbeddingFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    getEmbedding(userId)
      .map { embedding =>
        new FeatureMapBuilder()
          .add(UserInterestSummaryEmbeddingFeature, embedding)
          .build()
      }
  }

  private def getEmbedding(userId: Long): Stitch[Option[Seq[Seq[Double]]]] = {
    groxUserInterestsMhClientColumn.fetcher
      .fetch(userId).map { result =>
        result.v match {
          case Some(userInterests) if userInterests.seedPostSummaries.isDefined =>
            userInterests.seedPostSummaries.map { summaries =>
              summaries.flatMap(_.summaryEmbedding).filter(_.nonEmpty)
            }
          case other =>
            None
        }
      }
  }
}
