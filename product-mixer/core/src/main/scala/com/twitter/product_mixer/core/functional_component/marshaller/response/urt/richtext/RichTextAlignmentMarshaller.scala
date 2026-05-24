package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext

import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.Center
import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.Natural
import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichTextAlignment
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RichTextAlignmentMarshaller @Inject() () {

  def apply(alignment: RichTextAlignment): urt.RichTextAlignment = alignment match {
    case Natural => urt.RichTextAlignment.Natural
    case Center => urt.RichTextAlignment.Center
  }
}
