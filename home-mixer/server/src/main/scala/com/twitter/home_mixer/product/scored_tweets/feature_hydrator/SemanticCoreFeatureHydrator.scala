package com.twitter.home_mixer.product.scored_tweets.feature_hydrator

import com.twitter.home_mixer.functional_component.feature_hydrator.RealTimeEntityRealGraphFeatures
import com.twitter.home_mixer.functional_component.feature_hydrator.WithDefaultFeatureMap
import com.twitter.home_mixer.model.HomeFeatures.SemanticAnnotationIdsFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableRealTimeEntityRealGraphFeaturesParam
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.util.SRichDataRecord
import com.twitter.ml.api.{Feature => mlFeature}
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
import com.twitter.recos.entities.{thriftscala => ent}
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.features.semantic_core_features.SemanticCoreFeatures
import com.twitter.wtf.entity_real_graph.{thriftscala => erg}

object SemanticCoreDataRecordFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

object SemanticCoreFeatureHydrator
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery]
    with WithDefaultFeatureMap {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("SemanticCore")

  override val features: Set[Feature[_, _]] = Set(SemanticCoreDataRecordFeature)

  override val defaultFeatureMap: FeatureMap =
    FeatureMap(SemanticCoreDataRecordFeature, SemanticCoreDataRecordFeature.defaultValue)

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableRealTimeEntityRealGraphFeaturesParam)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offload {
    val viewerErgFeatures =
      query.features.flatMap(_.getOrElse(RealTimeEntityRealGraphFeatures, None))
    candidates.map { candidate =>
      val candidateEntities = candidate.features.getOrElse(SemanticAnnotationIdsFeature, Seq.empty)

      val engagements: Seq[Map[erg.EngagementType, erg.Features]] = {
        viewerErgFeatures
          .map { engagementsByEntity =>
            engagementsByEntity.collect {
              case (ent.Entity.SemanticCore(sc), featuresByEngagementType)
                  if candidateEntities.contains(sc.entityId) =>
                featuresByEngagementType
            }.toSeq
          }.getOrElse(Seq.empty)
      }

      val rawFeaturesByEngagementType: Map[erg.EngagementType, Seq[erg.Features]] =
        SemanticCoreFeatures.engagementTypes.map { engagementType =>
          val features = engagements.flatMap(_.get(engagementType))
          engagementType -> features
        }.toMap

      val decayedScoreFeatureValues: Map[mlFeature[_], List[Double]] =
        SemanticCoreFeatures.decayedScoreFeatures.mapValues { engagementType =>
          val valuesOpt: Option[Seq[Double]] =
            rawFeaturesByEngagementType.get(engagementType).map(_.map(_.scores.oneDayHalfLife))
          valuesOpt match {
            case Some(values) => values.toList
            case None => List.empty[Double]
          }
        }
      val normalizedCountFeatureValues: Map[mlFeature[_], List[Double]] =
        SemanticCoreFeatures.normalizedCountFeatures.mapValues { engagementType =>
          val valuesOpt: Option[Seq[Double]] =
            rawFeaturesByEngagementType.get(engagementType).map(_.flatMap(_.normalizedCount))
          valuesOpt match {
            case Some(values) => values.toList
            case None => List.empty[Double]
          }
        }

      buildFeatureMap(decayedScoreFeatureValues ++ normalizedCountFeatureValues)
    }
  }

  private def buildFeatureMap(
    featureValuesMap: Map[mlFeature[_], List[Double]]
  ): FeatureMap = {
    val featureContext = new FeatureContext(SemanticCoreFeatures.outputFeaturesPostMerge.toSeq: _*)
    val richRecord = new SRichDataRecord(new DataRecord, featureContext)

    SemanticCoreFeatures.hydrateCountFeatures(
      richRecord = richRecord,
      features = SemanticCoreFeatures.precomputedCountFeatures,
      featureValuesMap = featureValuesMap
    )

    FeatureMap(SemanticCoreDataRecordFeature, richRecord.getRecord)
  }

}
