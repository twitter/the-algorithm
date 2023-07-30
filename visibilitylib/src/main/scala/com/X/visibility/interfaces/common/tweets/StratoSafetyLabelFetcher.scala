package com.X.visibility.interfaces.common.tweets

import com.X.spam.rtf.thriftscala.SafetyLabel
import com.X.spam.rtf.thriftscala.SafetyLabelType
import com.X.strato.client.Fetcher
import com.X.strato.client.{Client => StratoClient}
import com.X.strato.thrift.ScroogeConvImplicits._
import com.X.util.Memoize

object StratoSafetyLabelFetcher {
  def apply(client: StratoClient): SafetyLabelFetcherType = {
    val getFetcher: SafetyLabelType => Fetcher[Long, Unit, SafetyLabel] =
      Memoize((safetyLabelType: SafetyLabelType) =>
        client.fetcher[Long, SafetyLabel](s"visibility/${safetyLabelType.name}.Tweet"))

    (tweetId, safetyLabelType) => getFetcher(safetyLabelType).fetch(tweetId).map(_.v)
  }
}
