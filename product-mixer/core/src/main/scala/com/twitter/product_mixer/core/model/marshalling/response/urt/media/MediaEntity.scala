package com.twitter.product_mixer.core.model.marshalling.response.urt.media

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ImageVariant

sealed trait MediaEntity

case class TweetMedia(
  tweetId: Long,
  momentId: Option[Long])
    extends MediaEntity

case class BroadcastId(id: String) extends MediaEntity

case class Image(image: ImageVariant) extends MediaEntity
