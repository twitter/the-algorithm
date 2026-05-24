package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.vertical_grid_item

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.SingleStateDefaultVerticalGridItemTileStyle
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.DoubleStateDefaultVerticalGridItemTileStyle
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItemTileStyle
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerticalGridItemTileStyleMarshaller @Inject() () {

  def apply(verticalGridItemTileStyle: VerticalGridItemTileStyle): urt.VerticalGridItemTileStyle =
    verticalGridItemTileStyle match {
      case SingleStateDefaultVerticalGridItemTileStyle =>
        urt.VerticalGridItemTileStyle.SingleStateDefault
      case DoubleStateDefaultVerticalGridItemTileStyle =>
        urt.VerticalGridItemTileStyle.DoubleStateDefault
    }
}
