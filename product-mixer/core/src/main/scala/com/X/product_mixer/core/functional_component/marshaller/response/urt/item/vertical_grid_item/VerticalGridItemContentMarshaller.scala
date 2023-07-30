package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.vertical_grid_item

import com.X.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItemTopicTile
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerticalGridItemContentMarshaller @Inject() (
  verticalGridItemTopicTileMarshaller: VerticalGridItemTopicTileMarshaller) {

  def apply(item: VerticalGridItem): urt.VerticalGridItemContent = item match {
    case verticalGridItemTopicTile: VerticalGridItemTopicTile =>
      verticalGridItemTopicTileMarshaller(verticalGridItemTopicTile)
  }
}
