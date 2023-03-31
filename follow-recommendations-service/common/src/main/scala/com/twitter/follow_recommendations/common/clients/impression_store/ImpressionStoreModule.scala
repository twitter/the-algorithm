package com.twitter.follow_recommendations.common.clients.impression_store

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.follow_recommendations.thriftscala.DisplayLocation
import com.twitter.inject.TwitterModule
import com.twitter.strato.catalog.Scan.Slice
import com.twitter.strato.client.Client
import com.twitter.strato.thrift.ScroogeConvImplicits._

object ImpressionStoreModule extends TwitterModule {

  val columnPath: String = "onboarding/userrecs/wtfImpressionCountsStore"

  type PKey = (Long, DisplayLocation)
  type LKey = Long
  type Value = (Long, Int)

  @Provides
  @Singleton
  def providesImpressionStore(stratoClient: Client): WtfImpressionStore = {
    new WtfImpressionStore(
      stratoClient.scanner[
        (PKey, Slice[LKey]),
        Unit,
        (PKey, LKey),
        Value
      ](columnPath)
    )
  }
}
