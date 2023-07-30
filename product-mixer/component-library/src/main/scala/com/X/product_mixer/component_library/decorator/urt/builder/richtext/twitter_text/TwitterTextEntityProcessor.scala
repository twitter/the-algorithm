package com.X.product_mixer.component_library.decorator.urt.builder.richtext.X_text

import com.X.product_mixer.component_library.decorator.urt.builder.richtext.RichTextReferenceObjectBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.richtext.X_text.XTextEntityProcessor.DefaultReferenceObjectBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ExternalUrl
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.ReferenceObject
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichTextCashtag
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichTextHashtag
import com.X.Xtext.Extractor
import scala.collection.convert.ImplicitConversions._

object XTextEntityProcessor {
  object DefaultReferenceObjectBuilder extends RichTextReferenceObjectBuilder {
    def apply(XEntity: Extractor.Entity): Option[ReferenceObject] = {
      XEntity.getType match {
        case Extractor.Entity.Type.URL =>
          Some(Url(ExternalUrl, XEntity.getValue))
        case Extractor.Entity.Type.HASHTAG =>
          Some(RichTextHashtag(XEntity.getValue))
        case Extractor.Entity.Type.CASHTAG =>
          Some(RichTextCashtag(XEntity.getValue))
        case _ => None
      }
    }
  }
}

/**
 * Add the corresponding  [[RichTextEntity]] extraction logic into [[XTextRenderer]].
 * The [[XTextRenderer]] after being processed will extract the defined entities.
 */
case class XTextEntityProcessor(
  XTextReferenceObjectBuilder: RichTextReferenceObjectBuilder = DefaultReferenceObjectBuilder)
    extends XTextRendererProcessor {

  private[this] val extractor = new Extractor()

  def process(
    XTextRenderer: XTextRenderer
  ): XTextRenderer = {
    val XEntities = extractor.extractEntitiesWithIndices(XTextRenderer.text)

    XEntities.foreach { XEntity =>
      XTextReferenceObjectBuilder(XEntity).foreach { refObject =>
        XTextRenderer.setRefObject(XEntity.getStart, XEntity.getEnd, refObject)
      }
    }
    XTextRenderer
  }
}
