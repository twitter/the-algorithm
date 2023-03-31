package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.RealGraphInNetworkScoresFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.RealGraphInNetworkScores
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.wtf.candidate.{thriftscala => wtf}
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
case class RealGraphInNetworkScoresQueryFeatureHydrator @Inject() (
  @Named(RealGraphInNetworkScores) store: ReadableStore[Long, Seq[wtf.Candidate]])
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RealGraphInNetworkScores")

  override val features: Set[Feature[_, _]] = Set(RealGraphInNetworkScoresFeature)

  private val RealGraphCandidateCount = 1000

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    Stitch.callFuture(store.get(query.getRequiredUserId)).map { realGraphFollowedUsers =>
      val realGraphScoresFeatures = realGraphFollowedUsers
        .getOrElse(Seq.empty)
        .sortBy(-_.score)
        .map(candidate => candidate.userId -> candidate.score)
        .take(RealGraphCandidateCount)
        .toMap

      FeatureMapBuilder().add(RealGraphInNetworkScoresFeature, realGraphScoresFeatures).build()
    }
  }
}
