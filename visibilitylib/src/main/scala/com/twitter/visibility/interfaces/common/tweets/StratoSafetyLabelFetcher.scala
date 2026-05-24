package com.twitter.visibility.interfaces.common.tweets

import com.twitter.spam.rtf.thriftscala.SafetyLabel
import com.twitter.spam.rtf.thriftscala.SafetyLabelType
import com.twitter.strato.client.Fetcher
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.strato.thrift.ScroogeConvImplicits._
import com.twitter.util.Memoize

object StratoSafetyLabelFetcher {
  def apply(client: StratoClient): SafetyLabelFetcherType = {
    val getFetcher: SafetyLabelType => Fetcher[Long, Unit, SafetyLabel] =
      Memoize((safetyLabelType: SafetyLabelType) =>
        client.fetcher[Long, SafetyLabel](s"visibility/${safetyLabelType.name}.Tweet"))

    (tweetId, safetyLabelType) => getFetcher(safetyLabelType).fetch(tweetId).map(_.v)
  }
}
