package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.EntityRealGraphClientStore
import com.twitter.home_mixer.util.ObservedKeyValueResultHandler
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.recos.entities.{thriftscala => ent}
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.wtf.entity_real_graph.{thriftscala => erg}
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

object RealTimeEntityRealGraphFeatures
    extends Feature[PipelineQuery, Option[Map[ent.Entity, Map[erg.EngagementType, erg.Features]]]]

@Singleton
class RealTimeEntityRealGraphQueryFeatureHydrator @Inject() (
  @Named(EntityRealGraphClientStore) client: ReadableStore[
    erg.EntityRealGraphRequest,
    erg.EntityRealGraphResponse
  ],
  override val statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RealTimeEntityRealGraphFeatures")

  override val features: Set[Feature[_, _]] = Set(RealTimeEntityRealGraphFeatures)

  override val statScope: String = identifier.toString

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val entityRealGraphRequest = erg.EntityRealGraphRequest(
      userId = query.getRequiredUserId,
      entityTypes = Set(erg.EntityType.SemanticCore),
      normalizeCounts = Some(true))
    Stitch.callFuture {
      client.get(entityRealGraphRequest).map { response =>
        val engagements = response.map(_.response.mapValues(_.toMap).toMap)
        FeatureMapBuilder().add(RealTimeEntityRealGraphFeatures, engagements).build()
      }
    }
  }
}
