package com.twitter.visibility.interfaces.common.tweets

import com.twitter.spam.rtf.thriftscala.SafetyLabelMap
import com.twitter.strato.client.Fetcher
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.strato.thrift.ScroogeConvImplicits._

object StratoSafetyLabelMapFetcher {
  val column = "visibility/baseTweetSafetyLabelMap"

  def apply(client: StratoClient): SafetyLabelMapFetcherType = {
    val fetcher: Fetcher[Long, Unit, SafetyLabelMap] =
      client.fetcher[Long, SafetyLabelMap](column)

    tweetId => fetcher.fetch(tweetId).map(_.v)
  }
}
