package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.tweet_mixer.candidate_source.evergreen_videos.EvergreenVideosSearchByTweetQuery
import com.twitter.tweet_mixer.functional_component.hydrator.SeedsTextFeatures
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ShortFormMaxDurationParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ShortFormMaxResultParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ShortFormMinDurationParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ShortFormMinHeightParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ShortFormMinWidthParam

case class TwitterClipV0ShortVideoQueryTransformer[Query <: PipelineQuery](
  signalFn: PipelineQuery => Seq[Long],
  candidatePipelineIdentifier: CandidatePipelineIdentifier)
    extends CandidatePipelineQueryTransformer[Query, EvergreenVideosSearchByTweetQuery] {

  override def transform(query: Query): EvergreenVideosSearchByTweetQuery = {
    val textMap = query.features.get.getOrElse(SeedsTextFeatures, None)

    EvergreenVideosSearchByTweetQuery(
      tweetIds = signalFn(query),
      textMap = textMap,
      size = query.params(ShortFormMaxResultParam),
      minWidth = query.params(ShortFormMinWidthParam),
      minHeight = query.params(ShortFormMinHeightParam),
      minDurationSec = query.params(ShortFormMinDurationParam).inSeconds,
      maxDurationSec = query.params(ShortFormMaxDurationParam).inSeconds
    )
  }
}
