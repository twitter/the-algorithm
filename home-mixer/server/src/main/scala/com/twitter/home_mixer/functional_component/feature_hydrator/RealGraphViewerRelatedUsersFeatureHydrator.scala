package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.DirectedAtUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.MentionUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableRealGraphViewerRelatedUsersFeaturesParam
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.adapters.real_graph.RealGraphEdgeFeaturesCombineAdapter
import com.twitter.timelines.real_graph.v1.{thriftscala => v1}
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._

object RealGraphViewerRelatedUsersDataRecordFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class RealGraphViewerRelatedUsersFeatureHydrator @Inject() ()
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery]
    with WithDefaultFeatureMap {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RealGraphViewerRelatedUsers")

  override val features: Set[Feature[_, _]] = Set(RealGraphViewerRelatedUsersDataRecordFeature)

  override val defaultFeatureMap: FeatureMap = FeatureMap(
    RealGraphViewerRelatedUsersDataRecordFeature,
    RealGraphViewerRelatedUsersDataRecordFeature.defaultValue)

  private val RealGraphEdgeFeaturesCombineAdapter = new RealGraphEdgeFeaturesCombineAdapter

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableRealGraphViewerRelatedUsersFeaturesParam)

  val batchSize = 64

  def getFeatureMap(
    candidate: CandidateWithFeatures[TweetCandidate],
    realGraphQueryFeatures: Map[Long, v1.RealGraphEdgeFeatures]
  ): FeatureMap = {
    val allRelatedUserIds = getRelatedUserIds(candidate.features)
    val realGraphFeatures =
      RealGraphViewerAuthorFeatureHydrator.getCombinedRealGraphFeatures(
        allRelatedUserIds,
        realGraphQueryFeatures
      )
    val realGraphFeaturesDataRecord = RealGraphEdgeFeaturesCombineAdapter
      .adaptToDataRecords(Some(realGraphFeatures)).asScala.headOption
      .getOrElse(new DataRecord)

    FeatureMap(RealGraphViewerRelatedUsersDataRecordFeature, realGraphFeaturesDataRecord)
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val realGraphQueryFeatures = query.features
      .flatMap(_.getOrElse(RealGraphFeatures, None))
      .getOrElse(Map.empty[Long, v1.RealGraphEdgeFeatures])

    OffloadFuturePools.offloadBatchElementToElement(
      candidates,
      getFeatureMap(_, realGraphQueryFeatures),
      batchSize)
  }

  private def getRelatedUserIds(features: FeatureMap): Seq[Long] = {
    (CandidatesUtil.getEngagerUserIds(features) ++
      features.getOrElse(AuthorIdFeature, None) ++
      features.getOrElse(MentionUserIdFeature, Seq.empty) ++
      features.getOrElse(SourceUserIdFeature, None) ++
      features.getOrElse(DirectedAtUserIdFeature, None)).distinct
  }
}
