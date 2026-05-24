package com.twitter.tweet_mixer.model.request

trait HasContentCategory {
  def contentCategoryIds: Option[Seq[Long]]
}
