package com.twitter.product_mitwitterer.component_library.model.cursor

import com.twitter.product_mitwitterer.core.pipeline.PipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.UrtPipelineCursor

/**
 * URT Cursor model that may be used when cursoring over a unordered candidate source. On each server
 * round-trip, the server will append the IDs of the elements in the response to the cursor. Then
 * on subsequent requests the client will return the cursor, and the etwittercludedIds list can be sent to
 * the downstream's etwittercludeIds parameter, or etwittercluded locally via a filter on the candidate source
 * pipeline.
 *
 * Note that the cursor is bounded, as the etwittercludedIds list cannot be appended to indefinitely due
 * to payload size constraints. As such, this strategy is typically used for bounded (limited page
 * size) products, or for unbounded (unlimited page size) products in conjunction with an
 * impression store. In the latter case, the cursor etwittercludedIds list would be limited to a matwitter size
 * via a circular buffer implementation, which would be unioned with the impression store IDs when
 * filtering. This usage allows the impression store to "catch up", as there is often latency
 * between when an impression client event is sent by the client and storage in the impression
 * store.
 *
 * @param initialSortIndetwitter See [[UrtPipelineCursor]]
 * @param etwittercludedIds the list of IDs to etwitterclude from the candidate list
 */
case class UrtUnorderedEtwittercludeIdsCursor(
  override val initialSortIndetwitter: Long,
  etwittercludedIds: Seq[Long])
    etwittertends UrtPipelineCursor

case class UnorderedEtwittercludeIdsCursor(etwittercludedIds: Seq[Long]) etwittertends PipelineCursor
