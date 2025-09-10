package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch

case class DependentBulkCandidateFeatureHydrator[Query <: PipelineQuery](
  parentCandidateFeatureHydrator: BulkCandidateFeatureHydrator[Query, TweetCandidate],
  childrenCandidateFeatureHydrators: Seq[
    BulkCandidateFeatureHydrator[Query, TweetCandidate] with WithDefaultFeatureMap
  ]) extends BulkCandidateFeatureHydrator[Query, TweetCandidate]
    with Conditionally[Query] {

  override final val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "Dependent" +
      parentCandidateFeatureHydrator.identifier.name)

  override def onlyIf(query: Query): Boolean = {
    parentCandidateFeatureHydrator match {
      case candidateHydrator: BulkCandidateFeatureHydrator[_, _] with Conditionally[Query] =>
        candidateHydrator.onlyIf(query)
      case _ => true
    }
  }

  override val features: Set[Feature[_, _]] =
    childrenCandidateFeatureHydrators.foldLeft(parentCandidateFeatureHydrator.features) {
      (features, childFeatureHydrator) => features ++ childFeatureHydrator.features
    }

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] =
    OffloadFuturePools.offloadStitch {
      val parentFeatureMapsStitch = parentCandidateFeatureHydrator.apply(query, candidates)
      parentFeatureMapsStitch.flatMap { parentFeatureMaps =>
        val updatedCandidates = candidates.zip(parentFeatureMaps).map {
          case (tweetCandidate, featureMap) =>
            new CandidateWithFeatures[TweetCandidate] {
              override val candidate = tweetCandidate.candidate
              override val features = tweetCandidate.features ++ featureMap
            }
        }
        val (childrenCandidateFeatureHydratorsFiltered, childrenCandidateFeatureHydratorsDefault) =
          childrenCandidateFeatureHydrators.partition {
            case candidateFeatureHydrator: BulkCandidateFeatureHydrator[_, _] with Conditionally[
                  Query
                ] =>
              candidateFeatureHydrator.onlyIf(query)

            case _: BulkCandidateFeatureHydrator[_, _] =>
              true

            case _ => false
          }
        val childrenFeatureMapsDefault = childrenCandidateFeatureHydratorsDefault.map {
          featureHydrator => Seq.fill(candidates.size)(featureHydrator.defaultFeatureMap)
        }
        val childrenFeatureMapsStitch = Stitch.traverse(childrenCandidateFeatureHydratorsFiltered) {
          _.apply(query, updatedCandidates)
        }
        childrenFeatureMapsStitch.map { childrenFeatureMaps =>
          val allFeatureMaps =
            (parentFeatureMaps +: childrenFeatureMaps) ++ childrenFeatureMapsDefault
          allFeatureMaps.transpose.map(FeatureMap.merge(_))
        }
      }
    }
}
