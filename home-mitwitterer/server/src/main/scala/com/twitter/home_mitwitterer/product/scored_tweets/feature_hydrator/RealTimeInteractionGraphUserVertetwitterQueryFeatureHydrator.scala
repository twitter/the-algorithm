package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.RealTimeInteractionGraphUserVertetwitterCache
import com.twitter.home_mitwitterer.util.ObservedKeyValueResultHandler
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.ReadCache
import com.twitter.stitch.Stitch
import com.twitter.wtf.real_time_interaction_graph.{thriftscala => ig}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

object RealTimeInteractionGraphUserVertetwitterQueryFeature
    etwittertends Feature[PipelineQuery, Option[ig.UserVertetwitter]]

@Singleton
class RealTimeInteractionGraphUserVertetwitterQueryFeatureHydrator @Inject() (
  @Named(RealTimeInteractionGraphUserVertetwitterCache) client: ReadCache[Long, ig.UserVertetwitter],
  override val statsReceiver: StatsReceiver)
    etwittertends QueryFeatureHydrator[PipelineQuery]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RealTimeInteractionGraphUserVertetwitter")

  override val features: Set[Feature[_, _]] = Set(RealTimeInteractionGraphUserVertetwitterQueryFeature)

  override val statScope: String = identifier.toString

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId

    Stitch.callFuture(
      client.get(Seq(userId)).map { results =>
        val feature = observedGet(key = Some(userId), keyValueResult = results)
        FeatureMapBuilder()
          .add(RealTimeInteractionGraphUserVertetwitterQueryFeature, feature)
          .build()
      }
    )
  }
}
