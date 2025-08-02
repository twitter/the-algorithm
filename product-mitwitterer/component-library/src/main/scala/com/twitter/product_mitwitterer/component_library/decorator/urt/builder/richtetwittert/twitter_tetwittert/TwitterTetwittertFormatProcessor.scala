package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert.twitter_tetwittert

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.Plain
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertFormat
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.Strong
import scala.collection.mutable

object TwitterTetwittertFormatProcessor {
  lazy val defaultFormatProcessor = TwitterTetwittertFormatProcessor()
}

/**
 * Add the corresponding [[RichTetwittertFormat]] etwittertraction logic into [[TwitterTetwittertRenderer]].
 * The [[TwitterTetwittertRenderer]] after being processed will etwittertract the defined entities. 
 */
case class TwitterTetwittertFormatProcessor(
  formats: Set[RichTetwittertFormat] = Set(Plain, Strong),
) etwittertends TwitterTetwittertRendererProcessor {

  private val formatMap = formats.map { format => format.name.toLowerCase -> format }.toMap

  private[this] val formatMatcher = {
    val formatNames = formatMap.keys.toSet
    s"<(/?)(${formatNames.mkString("|")})>".r
  }

  def renderTetwittert(tetwittert: String): RichTetwittert = {
    process(TwitterTetwittertRenderer(tetwittert)).build
  }

  def process(richTetwittertBuilder: TwitterTetwittertRenderer): TwitterTetwittertRenderer = {
    val tetwittert = richTetwittertBuilder.tetwittert
    val nodeStack = mutable.ArrayStack[(RichTetwittertFormat, Int)]()
    var offset = 0

    formatMatcher.findAllMatchIn(tetwittert).foreach { m =>
      formatMap.get(m.group(2)) match {
        case Some(format) => {
          if (m.group(1).nonEmpty) {
            if (!nodeStack.headOption.etwitterists {
                case (formatFromStack, _) => formatFromStack == format
              }) {
              throw UnmatchedFormatTag(format)
            }
            val (_, startIndetwitter) = nodeStack.pop
            richTetwittertBuilder.mergeFormat(startIndetwitter, m.start + offset, format)
          } else {
            nodeStack.push((format, m.start + offset))
          }
          richTetwittertBuilder.remove(m.start + offset, m.end + offset)
          offset -= m.end - m.start
        }
        case _ => // if format is not found, skip this format
      }
    }

    if (nodeStack.nonEmpty) {
      throw UnmatchedFormatTag(nodeStack.head._1)
    }

    richTetwittertBuilder
  }
}

case class UnmatchedFormatTag(format: RichTetwittertFormat)
    etwittertends Etwitterception(s"Unmatched format start and end tags for $format")
