package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.audio_space

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.audio_space.AudioSpaceItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioSpaceItemMarshaller @Inject() () {

  def apply(audioSpaceItem: AudioSpaceItem): urt.TimelineItemContent =
    urt.TimelineItemContent.AudioSpace(
      urt.AudioSpace(
        id = audioSpaceItem.id
      )
    )
}
