package com.twitter.product_mixer.core.quality_factor

import com.twitter.util.Duration
import com.twitter.util.Try

/** Updates the [[QualityFactor]] */
trait QualityFactorObserver {

  /** The [[QualityFactor]] to update when observing */
  def qualityFactor: QualityFactor[_]

  /**
   * updates the [[qualityFactor]] given the result [[Try]] and the latency
   * @note implementations must be sure to correctly ignore
   *       [[QualityFactor.config]]'s [[QualityFactorConfig.ignorableFailures]]
   */
  def apply(result: Try[_], latency: Duration): Unit
}
