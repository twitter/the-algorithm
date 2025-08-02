package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.RealGraphFeatureRepository
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.servo.repository.Repository
import com.twitter.timelines.real_graph.{thriftscala => rg}
import com.twitter.stitch.Stitch
import com.twitter.timelines.model.UserId
import com.twitter.timelines.real_graph.v1.thriftscala.RealGraphEdgeFeatures
import com.twitter.user_session_store.{thriftscala => uss}

import javatwitter.inject.Inject
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object RealGraphFeatures etwittertends Feature[PipelineQuery, Option[Map[UserId, RealGraphEdgeFeatures]]]

@Singleton
class RealGraphQueryFeatureHydrator @Inject() (
  @Named(RealGraphFeatureRepository) repository: Repository[Long, Option[uss.UserSession]])
    etwittertends QueryFeatureHydrator[PipelineQuery] {

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
