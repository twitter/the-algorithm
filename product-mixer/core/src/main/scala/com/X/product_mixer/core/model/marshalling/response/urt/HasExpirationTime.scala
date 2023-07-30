package com.X.product_mixer.core.model.marshalling.response.urt

import com.X.util.Time

trait HasExpirationTime {
  def expirationTime: Option[Time] = None

  final def expirationTimeInMillis: Option[Long] = expirationTime.map(_.inMillis)
}
