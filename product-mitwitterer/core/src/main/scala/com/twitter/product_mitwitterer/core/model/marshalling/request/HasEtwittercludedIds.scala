package com.twitter.product_mitwitterer.core.model.marshalling.request

/**
 * Allow clients to pass in a set of IDs that would be etwittercluded from the results.
 */
trait HasEtwittercludedIds {
  val etwittercludedIds: Set[Long] = Set.empty
}
