package com.twitter.tweet_mixer.functional_component.side_effect

import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.hydra.PublishGroxMultimodalEmbedRequestKafkaClientColumn
import com.twitter.strato.columns.content_understanding.content_exploration.thriftscala.MultimodalEmbeddingRequest
import com.twitter.tweet_mixer.functional_component.hydrator.MultimodalEmbeddingFeature
import com.twitter.tweet_mixer.model.response.TweetMixerResponse
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationMultimodalEnabled
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestMultimodalEmbeddingSideEffect @Inject() (
  publishGroxMultimodalEmbedRequestKafkaClientColumn: PublishGroxMultimodalEmbedRequestKafkaClientColumn)
    extends PipelineResultSideEffect[PipelineQuery, TweetMixerResponse]
    with PipelineResultSideEffect.Conditionally[PipelineQuery, TweetMixerResponse] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("RequestMultimodalEmbedding")

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: TweetMixerResponse
  ): Boolean = {
    query.params(ContentExplorationMultimodalEnabled) && {
      query.features.flatMap(_.getOrElse(MultimodalEmbeddingFeature, None)) match {
        case Some(embeddings) =>
          embeddings.values.exists(_.isEmpty) // Check if any embedding is None/missing
        case None => false
      }
    }
  }

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, TweetMixerResponse]
  ): Stitch[Unit] = {
    val maybeEmbeddings =
      inputs.query.features.flatMap(_.getOrElse(MultimodalEmbeddingFeature, None))

    val missingPostIds = maybeEmbeddings match {
      case Some(embeddings) =>
        embeddings.filter { case (_, embeddingOpt) => embeddingOpt.isEmpty }.keys.toSeq
      case None => Seq.empty[Long]
    }

    if (missingPostIds.nonEmpty) {
      // Parallelize Kafka insertions for each missing post ID (one per request)
      val publishStitches = missingPostIds.map { postId =>
        publishGroxMultimodalEmbedRequestKafkaClientColumn.inserter.insert(
          postId,
          MultimodalEmbeddingRequest(postId = postId)
        )
      }

      // Execute all insertions in parallel and collect results
      Stitch.traverse(publishStitches) { stitch => stitch }.map(_ => ())
    } else Stitch.Unit // No missing embeddings, no need to publish
  }
}
