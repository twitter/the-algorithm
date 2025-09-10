package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.util.ObservedKeyValueResultHandler
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.topic_recos.common.LocaleUtil
import com.twitter.topiclisting.SemanticCoreEntityId
import com.twitter.topiclisting.TopicListingViewerContext
import com.twitter.tsp.stores.UttTopicFilterStore
import com.twitter.tsp.{thriftscala => tsp}
import javax.inject.Inject
import javax.inject.Singleton

object FollowableUttTopicsFeatures
    extends Feature[PipelineQuery, Option[Map[SemanticCoreEntityId, Option[tsp.TopicFollowType]]]]

@Singleton
class FollowableUttTopicsQueryFeatureHydrator @Inject() (
  uttStore: UttTopicFilterStore,
  override val statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("FollowableUttTopics")

  override val features: Set[Feature[_, _]] = Set(FollowableUttTopicsFeatures)

  override val statScope: String = identifier.toString

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val context = TopicListingViewerContext.fromClientContext(query.clientContext)

    Stitch.callFuture {
      uttStore
        .getAllowListTopicsForUser(
          userId = query.getRequiredUserId,
          topicListingSetting = tsp.TopicListingSetting.Followable,
          context = context
            .copy(languageCode = LocaleUtil.getStandardLanguageCode(context.languageCode)),
          bypassModes = None
        ).map { topics =>
          FeatureMapBuilder().add(FollowableUttTopicsFeatures, Some(topics)).build()
        }
    }
  }
}
