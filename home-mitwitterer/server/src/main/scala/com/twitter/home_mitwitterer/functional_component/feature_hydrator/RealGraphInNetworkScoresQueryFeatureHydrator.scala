package com.twitter.home_mitwitterer.functional_component.feature_hydrator

import com.twitter.home_mitwitterer.model.HomeFeatures.RealGraphInNetworkScoresFeature
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.RealGraphInNetworkScores
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.wtf.candidate.{thriftscala => wtf}
import javatwitter.inject.Inject
import javatwitter.inject.Named
import javatwitter.inject.Singleton

@Singleton
case class RealGraphInNetworkScoresQueryFeatureHydrator @Inject() (
  @Named(RealGraphInNetworkScores) store: ReadableStore[Long, Seq[wtf.Candidate]])
    etwittertends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RealGraphInNetworkScores")

  override val features: Set[Feature[_, _]] = Set(RealGraphInNetworkScoresFeature)

  private val RealGraphCandidateCount = 1000

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    Stitch.callFuture(store.get(query.getRequiredUserId)).map { realGraphFollowedUsers =>
      val realGraphScoresFeatures = realGraphFollowedUsers
        .getOrElse(Seq.empty)
        .sortBy(-_.score)
        .map(candidate => candidate.userId -> scaleScore(candidate.score))
        .take(RealGraphCandidateCount)
        .toMap

      FeatureMapBuilder().add(RealGraphInNetworkScoresFeature, realGraphScoresFeatures).build()
    }
  }

  // Rescale Real Graph v2 scores from [0,1] to the v1 scores distribution [1,2.97]
  private def scaleScore(score: Double): Double =
    if (score >= 0.0 && score <= 1.0) score * 1.97 + 1.0 else score
}
