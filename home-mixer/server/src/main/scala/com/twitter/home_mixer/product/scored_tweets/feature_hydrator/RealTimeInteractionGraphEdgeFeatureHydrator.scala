package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator

import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.ExTwitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.util.OffloadFuturePools
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.prediction.adapters.realtime_interaction_graph.RealTimeInteractionGraphFeaturesAdapter
import com.ExTwitter.timelines.prediction.features.realtime_interaction_graph.RealTimeInteractionGraphEdgeFeatures
import com.ExTwitter.util.Time
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._

object RealTimeInteractionGraphEdgeFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class RealTimeInteractionGraphEdgeFeatureHydrator @Inject() ()
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RealTimeInteractionGraphEdge")

  override val features: Set[Feature[_, _]] = Set(RealTimeInteractionGraphEdgeFeature)

  private val realTimeInteractionGraphFeaturesAdapter = new RealTimeInteractionGraphFeaturesAdapter

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offload {
    val userVertex =
      query.features.flatMap(_.getOrElse(RealTimeInteractionGraphUserVertexQueryFeature, None))
    val realTimeInteractionGraphFeaturesMap =
      userVertex.map(RealTimeInteractionGraphEdgeFeatures(_, Time.now))

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
