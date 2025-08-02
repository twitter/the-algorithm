package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.ReaderModeConfig
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ReaderModeConfigMarshaller @Inject() (urlMarshaller: UrlMarshaller) {

  def apply(readerModeConfig: ReaderModeConfig): urt.ReaderModeConfig = urt.ReaderModeConfig(
    isReaderModeAvailable = readerModeConfig.isReaderModeAvailable,
    landingUrl = urlMarshaller(readerModeConfig.landingUrl)
  )

}
