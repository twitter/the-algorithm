package com.twitter.product_mixer.component_library.model.cursor

import com.twitter.product_mixer.core.pipeline.PipelineCursor
import com.twitter.product_mixer.core.pipeline.UrtPipelineCursor
import com.twitter.search.common.util.bloomfilter.AdaptiveLongIntBloomFilter

/**
 * Cursor model that may be used when cursoring over a unordered candidate source. On each server
 * round-trip, the server will add the IDs of the candidates into a space efficient bloom filter.
 * Then on subsequent requests the client will return the cursor, and the bloom filter can be sent to
 * the downstream's bloom filter parameter in serialized form, or exclude candidates locally via a
 * filter on the candidate source pipeline.
 *
 * @param initialSortIndex See [[UrtPipelineCursor]]
 * @param longIntBloomFilter the bloom filter to use to dedup candidate from the candidate list
 */
case class UrtUnorderedBloomFilterCursor(
  override val initialSortIndex: Long,
  // space-efficient and mutable variant of the BloomFilter class used for storing long integers.
  longIntBloomFilter: AdaptiveLongIntBloomFilter)
    extends UrtPipelineCursor

case class UnorderedBloomFilterCursor(
  longIntBloomFilter: AdaptiveLongIntBloomFilter)
    extends PipelineCursor
