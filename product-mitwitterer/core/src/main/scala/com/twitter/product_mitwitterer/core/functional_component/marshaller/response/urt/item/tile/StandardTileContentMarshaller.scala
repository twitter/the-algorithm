package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.tile

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.BadgeMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tile.StandardTileContent
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class StandardTileContentMarshaller @Inject() (
  badgeMarshaller: BadgeMarshaller) {

  def apply(standardTileContent: StandardTileContent): urt.TileContentStandard =
    urt.TileContentStandard(
      title = standardTileContent.title,
      supportingTetwittert = standardTileContent.supportingTetwittert,
      badge = standardTileContent.badge.map(badgeMarshaller(_))
    )
}
