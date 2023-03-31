package com.twitter.interaction_graph.scio.agg_address_book

import com.spotify.scio.ScioMetrics
import org.apache.beam.sdk.metrics.Counter

trait InteractionGraphAddressBookCountersTrait {
  val Namespace = "Interaction Graph Address Book"

  def emailFeatureInc(): Unit

  def phoneFeatureInc(): Unit

  def bothFeatureInc(): Unit
}

/**
 * SCIO counters are used to gather run time statistics
 */
case object InteractionGraphAddressBookCounters extends InteractionGraphAddressBookCountersTrait {
  val emailFeatureCounter: Counter =
    ScioMetrics.counter(Namespace, "Email Feature")

  val phoneFeatureCounter: Counter =
    ScioMetrics.counter(Namespace, "Phone Feature")

  val bothFeatureCounter: Counter =
    ScioMetrics.counter(Namespace, "Both Feature")

  override def emailFeatureInc(): Unit = emailFeatureCounter.inc()

  override def phoneFeatureInc(): Unit = phoneFeatureCounter.inc()

  override def bothFeatureInc(): Unit = bothFeatureCounter.inc()
}
