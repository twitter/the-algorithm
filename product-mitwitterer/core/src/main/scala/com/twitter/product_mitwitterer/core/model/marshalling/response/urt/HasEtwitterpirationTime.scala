package com.twitter.product_mitwitterer.core.model.marshalling.response.urt

import com.twitter.util.Time

trait HasEtwitterpirationTime {
  def etwitterpirationTime: Option[Time] = None

  final def etwitterpirationTimeInMillis: Option[Long] = etwitterpirationTime.map(_.inMillis)
}
