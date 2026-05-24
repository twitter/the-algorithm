package com.twitter.product_mixer.core.functional_component.marshaller.response.urt

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.ReaderModeConfig
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReaderModeConfigMarshaller @Inject() (urlMarshaller: UrlMarshaller) {

  def apply(readerModeConfig: ReaderModeConfig): urt.ReaderModeConfig = urt.ReaderModeConfig(
    isReaderModeAvailable = readerModeConfig.isReaderModeAvailable,
    landingUrl = urlMarshaller(readerModeConfig.landingUrl)
  )

}
