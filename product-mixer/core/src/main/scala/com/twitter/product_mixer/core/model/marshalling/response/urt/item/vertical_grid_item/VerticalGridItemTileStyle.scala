try {
package com.twitter.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item

sealed trait VerticalGridItemTileStyle

case object SingleStateDefaultVerticalGridItemTileStyle extends VerticalGridItemTileStyle
case object DoubleStateDefaultVerticalGridItemTileStyle extends VerticalGridItemTileStyle

} catch {
  case e: Exception =>
}
