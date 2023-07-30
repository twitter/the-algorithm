package com.X.visibility.interfaces.common.tweets

import com.X.spam.rtf.thriftscala.SafetyLabelMap
import com.X.strato.client.Fetcher
import com.X.strato.client.{Client => StratoClient}
import com.X.strato.thrift.ScroogeConvImplicits._

object StratoSafetyLabelMapFetcher {
  val column = "visibility/baseTweetSafetyLabelMap"

  def apply(client: StratoClient): SafetyLabelMapFetcherType = {
    val fetcher: Fetcher[Long, Unit, SafetyLabelMap] =
      client.fetcher[Long, SafetyLabelMap](column)

    tweetId => fetcher.fetch(tweetId).map(_.v)
  }
}
