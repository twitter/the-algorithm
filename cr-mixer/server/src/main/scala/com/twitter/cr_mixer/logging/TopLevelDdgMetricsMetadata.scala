package com.twitter.cr_mixer
package logging

import com.twitter.cr_mixer.thriftscala.CrMixerTweetRequest
import com.twitter.cr_mixer.thriftscala.Product

case class TopLevelDdgMetricsMetadata(
  userId: Option[Long],
  product: Product,
  clientApplicationId: Option[Long],
  countryCode: Option[String])

object TopLevelDdgMetricsMetadata {
  def from(request: CrMixerTweetRequest): TopLevelDdgMetricsMetadata = {
    TopLevelDdgMetricsMetadata(
      userId = request.clientContext.userId,
      product = request.product,
      clientApplicationId = request.clientContext.appId,
      countryCode = request.clientContext.countryCode
    )
  }
}
