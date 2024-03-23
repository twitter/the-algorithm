package com.ExTwitter.product_mixer.component_library.decorator.urt.builder.richtext.ExTwitter_text

import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.richtext.RichTextReferenceObjectBuilder
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.richtext.ExTwitter_text.ExTwitterTextEntityProcessor.DefaultReferenceObjectBuilder
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.ExternalUrl
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.richtext.ReferenceObject
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.richtext.RichTextCashtag
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.richtext.RichTextHashtag
import com.ExTwitter.ExTwittertext.Extractor
import scala.collection.convert.ImplicitConversions._

object ExTwitterTextEntityProcessor {
  object DefaultReferenceObjectBuilder extends RichTextReferenceObjectBuilder {
    def apply(ExTwitterEntity: Extractor.Entity): Option[ReferenceObject] = {
      ExTwitterEntity.getType match {
        case Extractor.Entity.Type.URL =>
          Some(Url(ExternalUrl, ExTwitterEntity.getValue))
        case Extractor.Entity.Type.HASHTAG =>
          Some(RichTextHashtag(ExTwitterEntity.getValue))
        case Extractor.Entity.Type.CASHTAG =>
          Some(RichTextCashtag(ExTwitterEntity.getValue))
        case _ => None
      }
    }
  }
}

/**
 * Add the corresponding  [[RichTextEntity]] extraction logic into [[ExTwitterTextRenderer]].
 * The [[ExTwitterTextRenderer]] after being processed will extract the defined entities.
 */
case class ExTwitterTextEntityProcessor(
  ExTwitterTextReferenceObjectBuilder: RichTextReferenceObjectBuilder = DefaultReferenceObjectBuilder)
    extends ExTwitterTextRendererProcessor {

  private[this] val extractor = new Extractor()

  def process(
    ExTwitterTextRenderer: ExTwitterTextRenderer
  ): ExTwitterTextRenderer = {
    val ExTwitterEntities = extractor.extractEntitiesWithIndices(ExTwitterTextRenderer.text)

    ExTwitterEntities.foreach { ExTwitterEntity =>
      ExTwitterTextReferenceObjectBuilder(ExTwitterEntity).foreach { refObject =>
        ExTwitterTextRenderer.setRefObject(ExTwitterEntity.getStart, ExTwitterEntity.getEnd, refObject)
      }
    }
    ExTwitterTextRenderer
  }
}
