package com.twitter.home_mixer.product.for_you.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.ArticleIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ArticlePreviewTextFeature
import com.twitter.home_mixer.product.for_you.feature_hydrator.ArticlePreviewTextFeatureHydrator.ArticlePreviewTextColumn
import com.twitter.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlePreviewTextFeatureHydrator @Inject() (
  stratoClient: StratoClient)
    extends CandidateFeatureHydrator[PipelineQuery, BaseTweetCandidate] {

  private val fetcher =
    stratoClient.fetcher[Long, Unit, String](ArticlePreviewTextColumn)

  override val features: Set[Feature[_, _]] =
    Set(ArticlePreviewTextFeature)

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ArticlePreviewText")

  override def apply(
    query: PipelineQuery,
    candidate: BaseTweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {

    existingFeatures.get(ArticleIdFeature) match {
      case Some(articleId) =>
        fetcher.fetch(articleId).map { fetchResult =>
          val articlePreviewText: Option[String] = fetchResult.v
          FeatureMap(ArticlePreviewTextFeature, articlePreviewText)
        }
      case None =>
        Stitch.value(FeatureMap(ArticlePreviewTextFeature, None))
    }

  }
}

object ArticlePreviewTextFeatureHydrator {
  final val ArticlePreviewTextColumn: String = "article/fields/previewText.ArticleEntity"
}
