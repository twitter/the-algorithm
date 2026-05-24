package com.twitter.product_mixer.core.model.marshalling.request

/**
 * Allow clients to pass in a set of IDs that would be excluded from the results.
 */
trait HasExcludedIds {
  val excludedIds: Set[Long] = Set.empty
}
