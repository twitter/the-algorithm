package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.vertical_grid_item

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItemTopicTile
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class VerticalGridItemContentMarshaller @Inject() (
  verticalGridItemTopicTileMarshaller: VerticalGridItemTopicTileMarshaller) {

  def apply(item: VerticalGridItem): urt.VerticalGridItemContent = item match {
    case verticalGridItemTopicTile: VerticalGridItemTopicTile =>
      verticalGridItemTopicTileMarshaller(verticalGridItemTopicTile)
  }
}
