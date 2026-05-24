package com.twitter.product_mixer.core.quality_factor

import com.twitter.util.Duration
import com.twitter.util.Try

case class QueriesPerSecondBasedQualityFactorObserver(
  override val qualityFactor: QueriesPerSecondBasedQualityFactor)
    extends QualityFactorObserver {
  override def apply(
    result: Try[_],
    latency: Duration
  ): Unit = {
    result
      .onSuccess(_ => qualityFactor.update())
      .onFailure {
        case t if qualityFactor.config.ignorableFailures.isDefinedAt(t) => ()
        // Degrade qf as a proactive mitigation for any non ignorable failures.
        case _ => qualityFactor.update(Int.MaxValue)
      }
  }
}
