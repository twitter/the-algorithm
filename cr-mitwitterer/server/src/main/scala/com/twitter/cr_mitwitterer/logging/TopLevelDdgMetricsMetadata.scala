package com.twitter.cr_mitwitterer
package logging

import com.twitter.cr_mitwitterer.thriftscala.CrMitwittererTweetRequest
import com.twitter.cr_mitwitterer.thriftscala.Product

case class TopLevelDdgMetricsMetadata(
  userId: Option[Long],
  product: Product,
  clientApplicationId: Option[Long],
  countryCode: Option[String])

object TopLevelDdgMetricsMetadata {
  def from(request: CrMitwittererTweetRequest): TopLevelDdgMetricsMetadata = {
    TopLevelDdgMetricsMetadata(
      userId = request.clientContetwittert.userId,
      product = request.product,
      clientApplicationId = request.clientContetwittert.appId,
      countryCode = request.clientContetwittert.countryCode
    )
  }
}
