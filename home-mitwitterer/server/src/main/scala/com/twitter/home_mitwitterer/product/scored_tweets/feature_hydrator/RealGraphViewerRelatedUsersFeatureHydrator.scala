package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.DirectedAtUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.MentionUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mitwitterer.util.CandidatesUtil
import com.twitter.ml.api.DataRecord
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.adapters.real_graph.RealGraphEdgeFeaturesCombineAdapter
import com.twitter.timelines.real_graph.v1.{thriftscala => v1}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.collection.JavaConverters._

object RealGraphViewerRelatedUsersDataRecordFeature
    etwittertends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class RealGraphViewerRelatedUsersFeatureHydrator @Inject() ()
    etwittertends CandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RealGraphViewerRelatedUsers")

  override val features: Set[Feature[_, _]] = Set(RealGraphViewerRelatedUsersDataRecordFeature)

  private val RealGraphEdgeFeaturesCombineAdapter = new RealGraphEdgeFeaturesCombineAdapter

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    etwitteristingFeatures: FeatureMap
  ): Stitch[FeatureMap] = OffloadFuturePools.offload {
    val realGraphQueryFeatures = query.features
      .flatMap(_.getOrElse(RealGraphFeatures, None))
      .getOrElse(Map.empty[Long, v1.RealGraphEdgeFeatures])

    val allRelatedUserIds = getRelatedUserIds(etwitteristingFeatures)
    val realGraphFeatures = RealGraphViewerAuthorFeatureHydrator.getCombinedRealGraphFeatures(
      allRelatedUserIds,
      realGraphQueryFeatures
    )
    val realGraphFeaturesDataRecord = RealGraphEdgeFeaturesCombineAdapter
      .adaptToDataRecords(Some(realGraphFeatures)).asScala.headOption
      .getOrElse(new DataRecord)

    FeatureMapBuilder()
      .add(RealGraphViewerRelatedUsersDataRecordFeature, realGraphFeaturesDataRecord)
      .build()
  }

  private def getRelatedUserIds(features: FeatureMap): Seq[Long] = {
    (CandidatesUtil.getEngagerUserIds(features) ++
      features.getOrElse(AuthorIdFeature, None) ++
      features.getOrElse(MentionUserIdFeature, Seq.empty) ++
      features.getOrElse(SourceUserIdFeature, None) ++
      features.getOrElse(DirectedAtUserIdFeature, None)).distinct
  }
}
