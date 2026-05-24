package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.interests.FetchXAICategoriesClientColumn
import javax.inject.Inject
import javax.inject.Singleton

object GrokCategoriesFeature extends Feature[PipelineQuery, Option[Seq[Long]]]

@Singleton
class GrokCategoriesQueryFeatureHydrator @Inject() (
  fetchXAICategoriesClientColumn: FetchXAICategoriesClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("GrokCategories")

  override val features: Set[Feature[_, _]] = Set(GrokCategoriesFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    fetchXAICategoriesClientColumn.fetcher.fetch().map { response =>
      val categories = response.v.map { s => s.map(_._1) }
      FeatureMap(GrokCategoriesFeature, categories)
    }
  }
}
