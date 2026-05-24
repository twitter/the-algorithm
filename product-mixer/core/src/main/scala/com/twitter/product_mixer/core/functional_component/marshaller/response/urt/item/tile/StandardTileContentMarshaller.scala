package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tile

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.BadgeMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tile.StandardTileContent
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StandardTileContentMarshaller @Inject() (
  badgeMarshaller: BadgeMarshaller) {

  def apply(standardTileContent: StandardTileContent): urt.TileContentStandard =
    urt.TileContentStandard(
      title = standardTileContent.title,
      supportingText = standardTileContent.supportingText,
      badge = standardTileContent.badge.map(badgeMarshaller(_))
    )
}
