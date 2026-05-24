package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.content_understanding.api.PostAnnotationsOnTweetClientColumn
import javax.inject.Inject
import javax.inject.Singleton

object GrokIsNsfwFeature extends Feature[TweetCandidate, Option[Boolean]]

object GrokIsSoftNsfwFeature extends Feature[TweetCandidate, Option[Boolean]]

@Singleton
class GrokBooleanFeatureHydrator @Inject() (
  postAnnotationsOnTweetClientColumn: PostAnnotationsOnTweetClientColumn)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("GrokBoolean")

  override val features: Set[Feature[_, _]] =
    Set(GrokIsNsfwFeature, GrokIsSoftNsfwFeature)

  private val fetcher = postAnnotationsOnTweetClientColumn.fetcher

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {

    if (candidates.isEmpty) {
      Stitch.value(Seq.empty)
    } else {
      val tweetIds = candidates.map(_.candidate.id)

      val stitchedResults = tweetIds.map { tweetId =>
        fetcher
          .fetch(tweetId).map { response =>
            val resultOpt = response.v

            val (isNsfw, isSoftNsfw) = resultOpt match {
              case Some(result) =>
                val boolMetadata = result.annotations.tweetBoolMetadata
                val nsfw = boolMetadata.flatMap(_.isNsfw)
                val softNsfw = boolMetadata.flatMap(_.isSoftNsfw)
                (nsfw, softNsfw)
              case None =>
                (Some(false), Some(false))
            }
            FeatureMapBuilder()
              .add(GrokIsNsfwFeature, isNsfw)
              .add(GrokIsSoftNsfwFeature, isSoftNsfw)
              .build()
          }.handle {
            case _ =>
              FeatureMapBuilder()
                .add(GrokIsNsfwFeature, Some(false))
                .add(GrokIsSoftNsfwFeature, Some(false))
                .build()
          }
      }

      Stitch.collect(stitchedResults)
    }
  }
}
