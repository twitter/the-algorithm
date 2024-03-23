package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator

import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.RealGraphFeatureRepository
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.servo.repository.Repository
import com.ExTwitter.timelines.real_graph.{thriftscala => rg}
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.model.UserId
import com.ExTwitter.timelines.real_graph.v1.thriftscala.RealGraphEdgeFeatures
import com.ExTwitter.user_session_store.{thriftscala => uss}

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

object RealGraphFeatures extends Feature[PipelineQuery, Option[Map[UserId, RealGraphEdgeFeatures]]]

@Singleton
class RealGraphQueryFeatureHydrator @Inject() (
  @Named(RealGraphFeatureRepository) repository: Repository[Long, Option[uss.UserSession]])
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RealGraphFeatures")

  override val features: Set[Feature[_, _]] = Set(RealGraphFeatures)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    Stitch.callFuture {
      repository(query.getRequiredUserId).map { userSession =>
        val realGraphFeaturesMap = userSession.flatMap { userSession =>
          userSession.realGraphFeatures.collect {
            case rg.RealGraphFeatures.V1(realGraphFeatures) =>
              val edgeFeatures = realGraphFeatures.edgeFeatures ++ realGraphFeatures.oonEdgeFeatures
              edgeFeatures.map { edge => edge.destId -> edge }.toMap
          }
        }

        FeatureMapBuilder().add(RealGraphFeatures, realGraphFeaturesMap).build()
      }
    }
  }
}
