package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.media

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ImageVariant

sealed trait MediaEntity

case class TweetMedia(
  tweetId: Long,
  momentId: Option[Long])
    etwittertends MediaEntity

case class BroadcastId(id: String) etwittertends MediaEntity

case class Image(image: ImageVariant) etwittertends MediaEntity
