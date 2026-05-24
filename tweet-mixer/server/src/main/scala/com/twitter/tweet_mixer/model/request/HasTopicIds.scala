package com.twitter.tweet_mixer.model.request

trait HasTopicIds {
  def topicIds: Option[Seq[Long]]
}
