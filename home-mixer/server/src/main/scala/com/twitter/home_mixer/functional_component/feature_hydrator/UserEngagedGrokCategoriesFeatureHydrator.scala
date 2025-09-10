package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.trends.trip.EngagedGrokTopicsAndTagsMonthlyOnUserClientColumn
import javax.inject.Inject
import javax.inject.Singleton

object UserSubLevelCategoriesFeature extends Feature[TweetCandidate, Seq[(Long, Double)]]

@Singleton
class UserEngagedGrokCategoriesFeatureHydrator @Inject() (
  engagedGrokTopicsAndTagMonthlysOnUserClientColumn: EngagedGrokTopicsAndTagsMonthlyOnUserClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserEngagedGrokCategories")

  override val features: Set[Feature[_, _]] =
    Set(UserSubLevelCategoriesFeature)

  private def fetchSubLevelEntities(userId: Long): Stitch[Seq[(Long, Double)]] = {
    engagedGrokTopicsAndTagMonthlysOnUserClientColumn.fetcher.fetch(userId).map { result =>
      result.v
        .flatMap(_.subLevelEntities).getOrElse(Seq.empty)
        .take(3)
        .map(entityInfo => (entityInfo.entityId, entityInfo.score))
    }
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    fetchSubLevelEntities(userId).map { subEntities =>
      FeatureMapBuilder()
        .add(UserSubLevelCategoriesFeature, subEntities)
        .build()
    }
  }
}
