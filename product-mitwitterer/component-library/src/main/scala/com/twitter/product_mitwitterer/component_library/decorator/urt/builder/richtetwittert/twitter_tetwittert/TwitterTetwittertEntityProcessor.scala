package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert.twitter_tetwittert

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert.RichTetwittertReferenceObjectBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert.twitter_tetwittert.TwitterTetwittertEntityProcessor.DefaultReferenceObjectBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.EtwitterternalUrl
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.ReferenceObject
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertCashtag
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertHashtag
import com.twitter.twittertetwittert.Etwittertractor
import scala.collection.convert.ImplicitConversions._

object TwitterTetwittertEntityProcessor {
  object DefaultReferenceObjectBuilder etwittertends RichTetwittertReferenceObjectBuilder {
    def apply(twitterEntity: Etwittertractor.Entity): Option[ReferenceObject] = {
      twitterEntity.getType match {
        case Etwittertractor.Entity.Type.URL =>
          Some(Url(EtwitterternalUrl, twitterEntity.getValue))
        case Etwittertractor.Entity.Type.HASHTAG =>
          Some(RichTetwittertHashtag(twitterEntity.getValue))
        case Etwittertractor.Entity.Type.CASHTAG =>
          Some(RichTetwittertCashtag(twitterEntity.getValue))
        case _ => None
      }
    }
  }
}

/**
 * Add the corresponding  [[RichTetwittertEntity]] etwittertraction logic into [[TwitterTetwittertRenderer]].
 * The [[TwitterTetwittertRenderer]] after being processed will etwittertract the defined entities.
 */
case class TwitterTetwittertEntityProcessor(
  twitterTetwittertReferenceObjectBuilder: RichTetwittertReferenceObjectBuilder = DefaultReferenceObjectBuilder)
    etwittertends TwitterTetwittertRendererProcessor {

  private[this] val etwittertractor = new Etwittertractor()

  def process(
    twitterTetwittertRenderer: TwitterTetwittertRenderer
  ): TwitterTetwittertRenderer = {
    val twitterEntities = etwittertractor.etwittertractEntitiesWithIndices(twitterTetwittertRenderer.tetwittert)

    twitterEntities.foreach { twitterEntity =>
      twitterTetwittertReferenceObjectBuilder(twitterEntity).foreach { refObject =>
        twitterTetwittertRenderer.setRefObject(twitterEntity.getStart, twitterEntity.getEnd, refObject)
      }
    }
    twitterTetwittertRenderer
  }
}
