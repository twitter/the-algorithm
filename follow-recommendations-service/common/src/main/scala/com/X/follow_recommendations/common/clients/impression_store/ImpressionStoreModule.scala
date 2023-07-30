package com.X.follow_recommendations.common.clients.impression_store

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.follow_recommendations.thriftscala.DisplayLocation
import com.X.inject.XModule
import com.X.strato.catalog.Scan.Slice
import com.X.strato.client.Client
import com.X.strato.thrift.ScroogeConvImplicits._

object ImpressionStoreModule extends XModule {

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
