package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.tweet_mixer.feature.RealGraphInNetworkScoresFeature
import com.twitter.tweet_mixer.model.ModuleNames.RealGraphInNetworkScoresOnPremRepo
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.repository.Repository
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.param.EarlybirdInNetworkTweetsParams.EarlybirdInNetworkTweetsEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.UTEGEnabled
import com.twitter.wtf.candidate.{thriftscala => wtf}

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
case class RealGraphInNetworkScoresQueryFeatureHydrator @Inject() (
  @Named(RealGraphInNetworkScoresOnPremRepo) realGraphRepo: Repository[Long, Option[
    wtf.CandidateSeq
  ]])
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RealGraphInNetworkScores")

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(UTEGEnabled) || query.params(EarlybirdInNetworkTweetsEnabled)

  override val features: Set[Feature[_, _]] = Set(RealGraphInNetworkScoresFeature)

  private val RealGraphCandidateCount = 1000

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {

    Stitch.callFuture(realGraphRepo.apply(query.getRequiredUserId)).map { realGraphFollowedUsers =>
      val realGraphScoresFeatures = realGraphFollowedUsers
        .map(_.candidates)
        .getOrElse(Seq.empty)
        .sortBy(-_.score)
        .map(candidate => candidate.userId -> scaleScore(candidate.score))
        .take(RealGraphCandidateCount)
        .toMap

      FeatureMapBuilder().add(RealGraphInNetworkScoresFeature, realGraphScoresFeatures).build()
    }
  }

  // Rescale Real Graph v2 scores from [0,1] to the v1 scores distribution [1,2.97]
  // v1 logic: src/scala/com/twitter/interaction_graph/scalding/jobs/scoring/InteractionGraphScoringJob.scala?L77-80
  private def scaleScore(score: Double): Double =
    if (score >= 0.0 && score <= 1.0) score * 1.97 + 1.0 else score

}
