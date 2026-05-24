package com.twitter.tweet_mixer.marshaller.response.common

import com.twitter.tweet_mixer.model.response.TweetResult
import com.twitter.tweet_mixer.{thriftscala => t}
import javax.inject.Singleton

@Singleton
class TweetResultMarshaller {
  def apply(tweetResult: TweetResult): t.TweetResult = t.TweetResult(
    tweetId = tweetResult.id,
    score = Some(tweetResult.score),
    metricTags = Some(tweetResult.metricTags),
    tweetMetadata = tweetResult.metadata.map { metadata =>
      t.TweetMetadata(
        sourceSignalId = metadata.sourceSignalId,
        signalType = metadata.signalType,
        servedType = metadata.servedType,
        signalEntity = metadata.signalEntity,
        authorId = metadata.authorId,
      )
    },
    inReplyToTweetId = tweetResult.inReplyToTweetId,
    authorId = tweetResult.authorId
  )
}
