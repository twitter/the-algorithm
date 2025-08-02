package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.Plain
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertFormat
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.Strong
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RichTetwittertFormatMarshaller @Inject() () {

  def apply(format: RichTetwittertFormat): urt.RichTetwittertFormat = format match {
    case Plain => urt.RichTetwittertFormat.Plain
    case Strong => urt.RichTetwittertFormat.Strong
  }
}
