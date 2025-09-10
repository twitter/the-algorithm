package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.tweet_mixer.feature.AuthorIdFeature
import com.twitter.tweet_mixer.feature.FromInNetworkSourceFeature
import com.twitter.tweet_mixer.feature.InReplyToTweetIdFeature
import com.twitter.tweet_mixer.feature.SourceTweetIdFeature
import com.twitter.search.earlybird.{thriftscala => eb}

object ReplyFeature extends Feature[TweetCandidate, Boolean]

object EarlybirdInNetworkResponseFeatureTransformer
    extends CandidateFeatureTransformer[eb.ThriftSearchResult] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("EarlybirdInNetworkResponse")

  override def features: Set[Feature[_, _]] =
    Set(
      AuthorIdFeature,
      SourceTweetIdFeature,
      FromInNetworkSourceFeature,
      ReplyFeature,
      InReplyToTweetIdFeature)

  override def transform(input: eb.ThriftSearchResult): FeatureMap = {
    val isRetweet = input.metadata.flatMap(_.isRetweet)
    val isReply = input.metadata.flatMap(_.isReply)
    val inReplyToTweetId =
      if (isReply.getOrElse(false)) input.metadata.map(_.sharedStatusId) else None
    val sourceTweetId =
      if (isRetweet.getOrElse(false))
        input.metadata.map(_.sharedStatusId)
      else Some(input.id)
    val authorId =
      if (isRetweet.getOrElse(false))
        input.metadata.map(_.referencedTweetAuthorId)
      else input.metadata.map(_.fromUserId)
    FeatureMapBuilder(sizeHint = 5)
      .add(AuthorIdFeature, authorId)
      .add(ReplyFeature, isReply.getOrElse(false))
      .add(SourceTweetIdFeature, sourceTweetId)
      .add(InReplyToTweetIdFeature, inReplyToTweetId)
      .add(FromInNetworkSourceFeature, true)
      .build()
  }
}
