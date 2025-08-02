package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.vertical_grid_item

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.vertical_grid_item.PivotVerticalGridItemTopicFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.vertical_grid_item.RecommendationVerticalGridItemTopicFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItemTopicFunctionalityType
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class VerticalGridItemTopicFunctionalityTypeMarshaller @Inject() () {

  def apply(
    verticalGridItemTopicFunctionalityType: VerticalGridItemTopicFunctionalityType
  ): urt.VerticalGridItemTopicFunctionalityType = verticalGridItemTopicFunctionalityType match {
    case PivotVerticalGridItemTopicFunctionalityType =>
      urt.VerticalGridItemTopicFunctionalityType.Pivot
    case RecommendationVerticalGridItemTopicFunctionalityType =>
      urt.VerticalGridItemTopicFunctionalityType.Recommendation
  }
}
