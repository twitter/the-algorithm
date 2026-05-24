package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.vertical_grid_item

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItemTopicTile
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerticalGridItemTopicTileMarshaller @Inject() (
  styleMarshaller: VerticalGridItemTileStyleMarshaller,
  functionalityTypeMarshaller: VerticalGridItemTopicFunctionalityTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(verticalGridItemTopicTile: VerticalGridItemTopicTile): urt.VerticalGridItemContent =
    urt.VerticalGridItemContent.TopicTile(
      urt.VerticalGridItemTopicTile(
        topicId = verticalGridItemTopicTile.id.toString,
        style = verticalGridItemTopicTile.style
          .map(styleMarshaller(_)).getOrElse(urt.VerticalGridItemTileStyle.SingleStateDefault),
        functionalityType = verticalGridItemTopicTile.functionalityType
          .map(functionalityTypeMarshaller(_)).getOrElse(
            urt.VerticalGridItemTopicFunctionalityType.Pivot),
        url = verticalGridItemTopicTile.url.map(urlMarshaller(_))
      )
    )

}
