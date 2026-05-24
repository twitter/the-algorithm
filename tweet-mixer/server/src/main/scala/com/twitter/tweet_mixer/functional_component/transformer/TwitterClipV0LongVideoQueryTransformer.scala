package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.tweet_mixer.candidate_source.evergreen_videos.EvergreenVideosSearchByTweetQuery
import com.twitter.tweet_mixer.functional_component.hydrator.SeedsTextFeatures
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LongFormMaxDurationParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LongFormMaxResultParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LongFormMinDurationParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LongFormMinHeightParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LongFormMinWidthParam

case class TwitterClipV0LongVideoQueryTransformer[Query <: PipelineQuery](
  signalFn: PipelineQuery => Seq[Long],
  candidatePipelineIdentifier: CandidatePipelineIdentifier)
    extends CandidatePipelineQueryTransformer[Query, EvergreenVideosSearchByTweetQuery] {

  override def transform(query: Query): EvergreenVideosSearchByTweetQuery = {
    val textMap = query.features.get.getOrElse(SeedsTextFeatures, None)

    EvergreenVideosSearchByTweetQuery(
      tweetIds = signalFn(query),
      textMap = textMap,
      size = query.params(LongFormMaxResultParam),
      minWidth = query.params(LongFormMinWidthParam),
      minHeight = query.params(LongFormMinHeightParam),
      minDurationSec = query.params(LongFormMinDurationParam).inSeconds,
      maxDurationSec = query.params(LongFormMaxDurationParam).inSeconds
    )
  }
}
