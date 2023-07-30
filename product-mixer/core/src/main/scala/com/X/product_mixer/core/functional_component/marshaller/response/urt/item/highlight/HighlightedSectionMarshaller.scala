package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.highlight

import com.X.product_mixer.core.model.marshalling.response.urt.item.highlight.HighlightedSection
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HighlightedSectionMarshaller @Inject() () {

  def apply(highlightedSection: HighlightedSection): urt.HighlightedSection =
    urt.HighlightedSection(
      startIndex = highlightedSection.startIndex,
      endIndex = highlightedSection.endIndex
    )
}
