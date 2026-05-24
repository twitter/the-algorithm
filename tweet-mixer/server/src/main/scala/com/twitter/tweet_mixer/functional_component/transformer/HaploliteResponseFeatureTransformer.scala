package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.timelineservice.model.Tweet
import com.twitter.tweet_mixer.feature.AuthorIdFeature
import com.twitter.tweet_mixer.feature.FromInNetworkSourceFeature
import com.twitter.tweet_mixer.feature.InReplyToTweetIdFeature
import com.twitter.tweet_mixer.feature.SourceTweetIdFeature
object HaploliteResponseFeatureTransformer extends CandidateFeatureTransformer[Tweet] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("HaploliteResponse")

  override def features: Set[Feature[_, _]] =
    Set(
      AuthorIdFeature,
      SourceTweetIdFeature,
      FromInNetworkSourceFeature,
      ReplyFeature,
      InReplyToTweetIdFeature)

  override def transform(input: Tweet): FeatureMap = {
    val isRetweet = input.isRetweet
    val isReply = input.isReply
    val sourceTweetId =
      if (isRetweet)
        input.sourceTweetId
      else Some(input.tweetId)
    val authorId =
      if (isRetweet)
        input.sourceUserId
      else input.userId
    FeatureMapBuilder(sizeHint = 5)
      .add(AuthorIdFeature, authorId)
      .add(ReplyFeature, isReply)
      .add(SourceTweetIdFeature, sourceTweetId)
      .add(FromInNetworkSourceFeature, true)
      .add(InReplyToTweetIdFeature, input.inReplyToTweetId)
      .build()
  }
}
