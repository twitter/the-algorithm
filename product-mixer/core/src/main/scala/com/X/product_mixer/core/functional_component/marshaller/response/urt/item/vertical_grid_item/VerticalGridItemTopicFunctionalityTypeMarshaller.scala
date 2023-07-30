package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.vertical_grid_item

import com.X.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.PivotVerticalGridItemTopicFunctionalityType
import com.X.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.RecommendationVerticalGridItemTopicFunctionalityType
import com.X.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItemTopicFunctionalityType
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

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
