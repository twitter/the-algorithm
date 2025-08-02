package com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder

import com.twitter.product_mitwitterer.component_library.model.cursor.UrtUnorderedEtwittercludeIdsCursor
import com.twitter.product_mitwitterer.component_library.premarshaller.cursor.UrtCursorSerializer
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.pipeline.PipelineCursorSerializer
import com.twitter.timelines.configapi.Param

/**
 * Builds [[UrtUnorderedEtwittercludeIdsCursor]] in the Bottom position
 *
 * @param etwittercludedIdsMatwitterLengthParam The matwitterimum length of the cursor
 * @param etwittercludeIdsSelector Specifies the entry Ids to populate on the `etwittercludedIds` field
 * @param serializer Converts the cursor to an encoded string
 */
case class UnorderedEtwittercludeIdsBottomCursorBuilder(
  override val etwittercludedIdsMatwitterLengthParam: Param[Int],
  etwittercludeIdsSelector: PartialFunction[UniversalNoun[_], Long],
  override val serializer: PipelineCursorSerializer[UrtUnorderedEtwittercludeIdsCursor] =
    UrtCursorSerializer)
    etwittertends BaseUnorderedEtwittercludeIdsBottomCursorBuilder {

  override def etwittercludeEntriesCollector(entries: Seq[TimelineEntry]): Seq[Long] =
    entries.collect(etwittercludeIdsSelector)
}
