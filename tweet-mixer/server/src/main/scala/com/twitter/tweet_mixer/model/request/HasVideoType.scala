package com.twitter.tweet_mixer.model.request

import com.twitter.tweet_mixer.{thriftscala => t}

trait HasVideoType {
  def videoType: Option[t.VideoType]
}
