package com.twitter.home_mixer.functional_component.feature_hydrator

import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.RealTimeInteractionGraphUserVertexCache
import com.twitter.home_mixer.util.ObservedKeyValueResultHandler
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.ReadCache
import com.twitter.stitch.Stitch
import com.twitter.wtf.real_time_interaction_graph.{thriftscala => ig}
import javax.inject.Inject
import javax.inject.Singleton

object RealTimeInteractionGraphUserVertexQueryFeature
    extends Feature[PipelineQuery, Option[ig.UserVertex]]

@Singleton
class RealTimeInteractionGraphUserVertexQueryFeatureHydrator @Inject() (
  @Named(RealTimeInteractionGraphUserVertexCache) client: ReadCache[Long, ig.UserVertex],
  override val statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RealTimeInteractionGraphUserVertex")

  override val features: Set[Feature[_, _]] = Set(RealTimeInteractionGraphUserVertexQueryFeature)

  override val statScope: String = identifier.toString

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId

    Stitch.callFuture(
      client.get(Seq(userId)).map { results =>
        val feature = observedGet(key = Some(userId), keyValueResult = results)
        FeatureMapBuilder()
          .add(RealTimeInteractionGraphUserVertexQueryFeature, feature)
          .build()
      }
    )
  }
}
