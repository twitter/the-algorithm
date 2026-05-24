package com.twitter.product_mixer.core.quality_factor

import com.twitter.util.Duration
import com.twitter.util.Try

case class LinearLatencyQualityFactorObserver(
  override val qualityFactor: LinearLatencyQualityFactor)
    extends QualityFactorObserver {

  override def apply(result: Try[_], latency: Duration): Unit = {
    result
      .onSuccess(_ => qualityFactor.update(latency))
      .onFailure {
        case t if qualityFactor.config.ignorableFailures.isDefinedAt(t) => ()
        case _ => qualityFactor.update(Duration.Top)
      }
  }
}
