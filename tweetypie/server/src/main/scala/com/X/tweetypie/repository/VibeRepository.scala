package com.X.tweetypie.repository

import com.X.stitch.Stitch
import com.X.strato.client.Fetcher
import com.X.strato.client.{Client => StratoClient}
import com.X.tweetypie.thriftscala.Tweet
import com.X.strato.thrift.ScroogeConvImplicits._
import com.X.vibes.thriftscala.VibeV2

object VibeRepository {
  type Type = Tweet => Stitch[Option[VibeV2]]

  val column = "vibes/vibe.Tweet"
  case class VibeView(viewerId: Option[Long])

  /**
   * Creates a function that applies the vibes/vibe.Tweet strato column fetch on the given
   * Tweet. Strato column source: go/vibe.strato
   * @param client Strato client
   * @return
   */
  def apply(client: StratoClient): Type = {
    val fetcher: Fetcher[Long, VibeView, VibeV2] =
      client.fetcher[Long, VibeView, VibeV2](column)
    tweet =>
      fetcher
        .fetch(tweet.id, VibeView(None))
        .map(_.v)
  }
}
