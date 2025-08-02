package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.ml.api.DataRecord
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.adapters.realtime_interaction_graph.RealTimeInteractionGraphFeaturesAdapter
import com.twitter.timelines.prediction.features.realtime_interaction_graph.RealTimeInteractionGraphEdgeFeatures
import com.twitter.util.Time
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.collection.JavaConverters._

object RealTimeInteractionGraphEdgeFeature
    etwittertends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class RealTimeInteractionGraphEdgeFeatureHydrator @Inject() ()
    etwittertends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RealTimeInteractionGraphEdge")

  override val features: Set[Feature[_, _]] = Set(RealTimeInteractionGraphEdgeFeature)

  private val realTimeInteractionGraphFeaturesAdapter = new RealTimeInteractionGraphFeaturesAdapter

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offload {
    val userVertetwitter =
      query.features.flatMap(_.getOrElse(RealTimeInteractionGraphUserVertetwitterQueryFeature, None))
    val realTimeInteractionGraphFeaturesMap =
      userVertetwitter.map(RealTimeInteractionGraphEdgeFeatures(_, Time.now))

    candidates.map { candidate =>
      val feature = candidate.features.getOrElse(AuthorIdFeature, None).flatMap { authorId =>
        realTimeInteractionGraphFeaturesMap.flatMap(_.get(authorId))
      }

      val dataRecordFeature =
        realTimeInteractionGraphFeaturesAdapter.adaptToDataRecords(feature).asScala.head

      FeatureMapBuilder().add(RealTimeInteractionGraphEdgeFeature, dataRecordFeature).build()
    }
  }
}
