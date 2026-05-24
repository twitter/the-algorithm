package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tile

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tile.CallToActionTileContent
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tile.StandardTileContent
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tile.TileContent
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TileContentMarshaller @Inject() (
  standardTileContentMarshaller: StandardTileContentMarshaller,
  callToActionTileContentMarshaller: CallToActionTileContentMarshaller) {

  def apply(tileContent: TileContent): urt.TileContent = tileContent match {
    case tileCont: StandardTileContent =>
      urt.TileContent.Standard(standardTileContentMarshaller(tileCont))
    case tileCont: CallToActionTileContent =>
      urt.TileContent.CallToAction(callToActionTileContentMarshaller(tileCont))
  }
}
