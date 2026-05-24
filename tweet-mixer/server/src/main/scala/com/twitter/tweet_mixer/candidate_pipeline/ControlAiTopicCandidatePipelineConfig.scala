package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseQueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.tweet_mixer.candidate_pipeline.ControlAiTopicCandidatePipelineConfig.Identifier
import com.twitter.tweet_mixer.candidate_source.text_embedding_ann.TextEmbeddingCandidateSource
import com.twitter.tweet_mixer.candidate_source.text_embedding_ann.TextEmbeddingQuery
import com.twitter.tweet_mixer.functional_component.hydrator.ControlAiTopicEmbeddingQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.hydrator.ControlAiTopicEmbeddings
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ControlAiEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ControlAiTopicEmbeddingANNMaxCandidates
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.ForYouGroupMap
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ControlAiTopicCandidatePipelineConfig @Inject() (
  controlAiTopicEmbeddingQueryFeatureHydrator: ControlAiTopicEmbeddingQueryFeatureHydrator,
  textEmbeddingCandidateSource: TextEmbeddingCandidateSource)
    extends CandidatePipelineConfig[
      PipelineQuery,
      Seq[TextEmbeddingQuery],
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = Identifier

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "ControlAiTopic",
      param = ControlAiEnabled
    )
  )

  override val queryFeatureHydration: Seq[BaseQueryFeatureHydrator[PipelineQuery, _]] =
    Seq(controlAiTopicEmbeddingQueryFeatureHydrator)

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    Seq[TextEmbeddingQuery]
  ] = { query =>
    val totalMaxCandidates = query.params(ControlAiTopicEmbeddingANNMaxCandidates)

    query.features
      .map(q => q.getOrElse(ControlAiTopicEmbeddings, Seq.empty))
      .map { embeddings =>
        val maxCandidates =
          if (embeddings.nonEmpty) Math.max(totalMaxCandidates / embeddings.size, 50) else 0
        embeddings.map { embedding =>
          TextEmbeddingQuery(vector = embedding, limit = maxCandidates)
        }
      }.getOrElse(Seq.empty)
  }

  override def candidateSource: CandidateSource[
    Seq[TextEmbeddingQuery],
    TweetMixerCandidate
  ] = textEmbeddingCandidateSource

  override def featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TweetMixerCandidate]
  ] = Seq(TweetMixerCandidateFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { candidate =>
    TweetCandidate(id = candidate.tweetId)
  }

  override val alerts: Seq[Alert] = Seq(
    defaultSuccessRateAlert()(ForYouGroupMap),
  )
}

object ControlAiTopicCandidatePipelineConfig {
  val Identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    CandidatePipelineConstants.ControlAiTopicTweets)
}
