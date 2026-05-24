package com.twitter.product_mixer.component_library.premarshaller.urt.builder

import com.twitter.product_mixer.component_library.model.cursor.UrtUnorderedExcludeIdsCursor
import com.twitter.product_mixer.component_library.premarshaller.cursor.UrtCursorSerializer
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.pipeline.PipelineCursorSerializer
import com.twitter.timelines.configapi.Param

/**
 * Builds [[UrtUnorderedExcludeIdsCursor]] in the Bottom position
 *
 * @param excludedIdsMaxLengthParam The maximum length of the cursor
 * @param excludeIdsSelector Specifies the entry Ids to populate on the `excludedIds` field
 * @param serializer Converts the cursor to an encoded string
 */
case class UnorderedExcludeIdsBottomCursorBuilder(
  override val excludedIdsMaxLengthParam: Param[Int],
  excludeIdsSelector: PartialFunction[UniversalNoun[_], Long],
  override val serializer: PipelineCursorSerializer[UrtUnorderedExcludeIdsCursor] =
    UrtCursorSerializer)
    extends BaseUnorderedExcludeIdsBottomCursorBuilder {

  override def excludeEntriesCollector(entries: Seq[TimelineEntry]): Seq[Long] =
    entries.collect(excludeIdsSelector)
}
