package com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder

import com.twitter.product_mitwitterer.component_library.model.cursor.UrtUnorderedEtwittercludeIdsCursor
import com.twitter.product_mitwitterer.component_library.premarshaller.cursor.UrtCursorSerializer
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.pipeline.PipelineCursorSerializer
import com.twitter.timelines.configapi.Param

/**
 * Builds [[UrtUnorderedEtwittercludeIdsCursor]] in the Bottom position when we want to also etwitterclude ids
 * of items inside a module. The reason we cannot use [[UnorderedEtwittercludeIdsBottomCursorBuilder]] in
 * such case is that the etwittercludeIdsSelector of [[UnorderedEtwittercludeIdsBottomCursorBuilder]] is doing a
 * one to one mapping between entries and etwittercluded ids, but in case of having a module, a module
 * entry can result in etwittercluding a sequence of entries.
 *
 * @param etwittercludedIdsMatwitterLengthParam The matwitterimum length of the cursor
 * @param etwittercludeIdsSelector Specifies the entry Ids to populate on the `etwittercludedIds` field
 * @param serializer Converts the cursor to an encoded string
 */
case class UnorderedEtwittercludeIdsSeqBottomCursorBuilder(
  override val etwittercludedIdsMatwitterLengthParam: Param[Int],
  etwittercludeIdsSelector: PartialFunction[UniversalNoun[_], Seq[Long]],
  override val serializer: PipelineCursorSerializer[UrtUnorderedEtwittercludeIdsCursor] =
    UrtCursorSerializer)
    etwittertends BaseUnorderedEtwittercludeIdsBottomCursorBuilder {

  override def etwittercludeEntriesCollector(entries: Seq[TimelineEntry]): Seq[Long] =
    entries.collect(etwittercludeIdsSelector).flatten
}
