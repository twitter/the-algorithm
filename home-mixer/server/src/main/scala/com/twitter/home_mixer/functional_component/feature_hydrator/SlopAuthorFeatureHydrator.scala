package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.abuse.detection.scoring.{thriftscala => t}
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.DebugStringFeature
import com.twitter.home_mixer.model.HomeFeatures.SlopAuthorFeature
import com.twitter.home_mixer.model.HomeFeatures.SlopAuthorScoreFeature
import com.twitter.home_mixer.param.HomeGlobalParams.SlopMaxScore
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.hss.user_scores.api.HealthModelScoresOnUserClientColumn

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SlopAuthorFeatureHydrator @Inject() (
  healthModelScoresOnUserClientColumn: HealthModelScoresOnUserClientColumn)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("SlopAuthor")

  override val features: Set[Feature[_, _]] =
    Set(SlopAuthorFeature, DebugStringFeature, SlopAuthorScoreFeature)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val authorIds = candidates.flatMap(_.features.getOrElse(AuthorIdFeature, None)).distinct
    val slopThreshold = query.params(SlopMaxScore)
    Stitch
      .collectToTry {
        authorIds.map { authorId =>
          healthModelScoresOnUserClientColumn.fetcher
            .fetch(authorId, Seq(t.UserHealthModel.NsfwConsumerFollowerScore))
            .map { response =>
              authorId -> response.v.flatMap { scoresMap =>
                scoresMap.get(t.UserHealthModel.NsfwConsumerFollowerScore)
              }
            }
        }
      }.map { authorNsfwScores =>
        val authorIdsToNsfwScoresMap = authorNsfwScores.flatMap(_.toOption).toMap
        candidates.map { candidate =>
          val debugStringFeature =
            candidate.features.getOrElse(DebugStringFeature, None).getOrElse("")
          candidate.features.getOrElse(AuthorIdFeature, None) match {
            case Some(authorId) =>
              val slopAuthorScore =
                authorIdsToNsfwScoresMap.getOrElse(authorId, None).getOrElse(0.0)
              FeatureMap(
                SlopAuthorFeature,
                slopAuthorScore > slopThreshold,
                DebugStringFeature,
                Some("%s Slop %.3f".format(debugStringFeature, slopAuthorScore)),
                SlopAuthorScoreFeature,
                Some(slopAuthorScore)
              )
            case _ =>
              FeatureMap(
                SlopAuthorFeature,
                false,
                DebugStringFeature,
                Some(debugStringFeature),
                SlopAuthorScoreFeature,
                None
              )
          }
        }
      }
  }
}
