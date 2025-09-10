package com.twitter.home_mixer.product.for_you.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.CurrentDisplayedGrokTopicFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.interests.FollowedGrokTopicsWithNameOnUserClientColumn
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random

@Singleton
class DisplayedGrokTopicQueryFeatureHydrator @Inject() (
  followedGrokTopicsWithNameOnUserClientColumn: FollowedGrokTopicsWithNameOnUserClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "FollowedGrokTopics")

  private val fetcher = followedGrokTopicsWithNameOnUserClientColumn.fetcher

  override def features: Set[Feature[_, _]] =
    Set(CurrentDisplayedGrokTopicFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    fetcher
      .fetch(query.getRequiredUserId)
      .map { result =>
        val topicResult = result.v.map(_.toSeq).getOrElse(Seq.empty)
        val randomTopic = Random.shuffle(topicResult).take(1).headOption
        FeatureMap(CurrentDisplayedGrokTopicFeature, randomTopic)
      }
  }
}
