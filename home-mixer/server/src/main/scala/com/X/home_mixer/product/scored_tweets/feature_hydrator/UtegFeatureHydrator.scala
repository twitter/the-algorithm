package com.X.home_mixer.product.scored_tweets.feature_hydrator

import com.X.home_mixer.model.HomeFeatures.FavoritedByCountFeature
import com.X.home_mixer.model.HomeFeatures.FavoritedByUserIdsFeature
import com.X.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.X.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.RealGraphInNetworkScoresFeature
import com.X.home_mixer.model.HomeFeatures.RepliedByCountFeature
import com.X.home_mixer.model.HomeFeatures.RepliedByEngagerIdsFeature
import com.X.home_mixer.model.HomeFeatures.RetweetedByCountFeature
import com.X.home_mixer.model.HomeFeatures.RetweetedByEngagerIdsFeature
import com.X.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.X.home_mixer.param.HomeMixerInjectionNames.UtegSocialProofRepository
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.Conditionally
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.util.OffloadFuturePools
import com.X.recos.recos_common.{thriftscala => rc}
import com.X.recos.user_tweet_entity_graph.{thriftscala => uteg}
import com.X.servo.keyvalue.KeyValueResult
import com.X.servo.repository.KeyValueRepository
import com.X.stitch.Stitch
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UtegFeatureHydrator @Inject() (
  @Named(UtegSocialProofRepository) client: KeyValueRepository[
    (Seq[Long], (Long, Map[Long, Double])),
    Long,
    uteg.TweetRecommendation
  ]) extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("Uteg")

  override val features: Set[Feature[_, _]] = Set(
    FavoritedByUserIdsFeature,
    RetweetedByEngagerIdsFeature,
    RepliedByEngagerIdsFeature,
    FavoritedByCountFeature,
    RetweetedByCountFeature,
    RepliedByCountFeature
  )

  override def onlyIf(query: PipelineQuery): Boolean = query.features
    .exists(_.getOrElse(RealGraphInNetworkScoresFeature, Map.empty[Long, Double]).nonEmpty)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val seedUserWeights = query.features.map(_.get(RealGraphInNetworkScoresFeature)).get

    val sourceTweetIds = candidates.flatMap(_.features.getOrElse(SourceTweetIdFeature, None))
    val inReplyToTweetIds = candidates.flatMap(_.features.getOrElse(InReplyToTweetIdFeature, None))
    val tweetIds = candidates.map(_.candidate.id)
    val tweetIdsToSend = (tweetIds ++ sourceTweetIds ++ inReplyToTweetIds).distinct

    val utegQuery = (tweetIdsToSend, (query.getRequiredUserId, seedUserWeights))

    client(utegQuery).map(handleResponse(candidates, _))
  }

  private def handleResponse(
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
    results: KeyValueResult[Long, uteg.TweetRecommendation],
  ): Seq[FeatureMap] = {
    candidates.map { candidate =>
      val inNetwork = candidate.features.getOrElse(FromInNetworkSourceFeature, false)
      val candidateProof = results(candidate.candidate.id).toOption.flatten
      val sourceProof = candidate.features
        .getOrElse(SourceTweetIdFeature, None).flatMap(results(_).toOption.flatten)
      val proofs = Seq(candidateProof, sourceProof).flatten.map(_.socialProofByType)

      val favoritedBy = proofs.flatMap(_.get(rc.SocialProofType.Favorite)).flatten
      val retweetedBy = proofs.flatMap(_.get(rc.SocialProofType.Retweet)).flatten
      val repliedBy = proofs.flatMap(_.get(rc.SocialProofType.Reply)).flatten

      val (favoritedByCount, retweetedByCount, repliedByCount) =
        if (!inNetwork) {
          (favoritedBy.size.toDouble, retweetedBy.size.toDouble, repliedBy.size.toDouble)
        } else { (0.0, 0.0, 0.0) }

      FeatureMapBuilder()
        .add(FavoritedByUserIdsFeature, favoritedBy)
        .add(RetweetedByEngagerIdsFeature, retweetedBy)
        .add(RepliedByEngagerIdsFeature, repliedBy)
        .add(FavoritedByCountFeature, favoritedByCount)
        .add(RetweetedByCountFeature, retweetedByCount)
        .add(RepliedByCountFeature, repliedByCount)
        .build()
    }
  }
}
