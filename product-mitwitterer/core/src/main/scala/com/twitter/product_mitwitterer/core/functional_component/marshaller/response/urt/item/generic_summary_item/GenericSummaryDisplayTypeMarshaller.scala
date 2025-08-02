package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.generic_summary_item

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.generic_summary.GenericSummaryItemDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.generic_summary.HeroDisplayType
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class GenericSummaryDisplayTypeMarshaller @Inject() () {

  def apply(
    genericSummaryItemDisplayType: GenericSummaryItemDisplayType
  ): urt.GenericSummaryDisplayType =
    genericSummaryItemDisplayType match {
      case HeroDisplayType => urt.GenericSummaryDisplayType.Hero
    }
}
