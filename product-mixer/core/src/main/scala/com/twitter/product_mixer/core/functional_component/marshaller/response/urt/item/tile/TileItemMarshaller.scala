package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tile

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.ImageVariantMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tile.TileItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TileItemMarshaller @Inject() (
  tileContentMarshaller: TileContentMarshaller,
  urlMarshaller: UrlMarshaller,
  imageVariantMarshaller: ImageVariantMarshaller) {

  def apply(tileItem: TileItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.Tile(
      urt.Tile(
        title = tileItem.title,
        supportingText = tileItem.supportingText,
        url = tileItem.url.map(urlMarshaller(_)),
        image = tileItem.image.map(imageVariantMarshaller(_)),
        badge = None,
        content = tileContentMarshaller(tileItem.content)
      )
    )
  }
}
