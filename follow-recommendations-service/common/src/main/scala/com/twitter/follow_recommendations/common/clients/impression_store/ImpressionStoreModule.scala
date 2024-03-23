package com.ExTwitter.follow_recommendations.common.clients.impression_store

import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.follow_recommendations.thriftscala.DisplayLocation
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.strato.catalog.Scan.Slice
import com.ExTwitter.strato.client.Client
import com.ExTwitter.strato.thrift.ScroogeConvImplicits._

object ImpressionStoreModule extends ExTwitterModule {

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
