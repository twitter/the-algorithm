package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.EtwitterternalUrl
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.UrlType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertAlignment
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertEntity
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.Strong

/*
 * RichTetwittertMarkupUtil facilitates building a Product Mitwitterer URT RichTetwittert object out of
 * a string with inline twitterML markup.
 *
 * This allows us to use a string like "Our system <a href="#promitwitter">Product Mitwitterer</a> is the <b>best</b>". Using
 * inline markup like this is advantageous since the string can go through translation/localization and the
 * translators will move the tags around in each language as appropriate.
 *
 * This class is derived from the OCF (onboarding/serve)'s RichTetwittertUtil, but they diverge because:
 * - We generate ProMitwitter URT structures, not OCF URT structures
 * - The OCF supports some internal OCF tags, like <data>
 * - The OCF has additional legacy support and processing that we don't need
 */

object RichTetwittertMarkupUtil {

  // Matches a anchor element, etwittertracting the 'a' tag and the display tetwittert.
  // First group is the tag
  // Second group is the display tetwittert
  // Allows any character in the display tetwittert, but matches reluctantly
  private val LinkAnchorRegetwitter = """(?i)(?s)<a\s+href\s*=\s*"#([\w-]*)">(.*?)</a>""".r

  // Matches a <b>bold tetwittert section</b>
  private val BoldRegetwitter = """(?i)(?s)<b>(.*?)</b>""".r

  def richTetwittertFromMarkup(
    tetwittert: String,
    linkMap: Map[String, String],
    rtl: Option[Boolean] = None,
    alignment: Option[RichTetwittertAlignment] = None,
    linkTypeMap: Map[String, UrlType] = Map.empty
  ): RichTetwittert = {

    // Mutable!
    var currentTetwittert = tetwittert
    val entities = scala.collection.mutable.ArrayBuffer.empty[RichTetwittertEntity]

    // Using a while loop since we want to etwitterecute the regetwitter after each iteration, so our indetwitteres remain consistent

    // Handle Links
    var matchOpt = LinkAnchorRegetwitter.findFirstMatchIn(currentTetwittert)
    while (matchOpt.isDefined) {
      matchOpt.foreach { linkMatch =>
        val tag = linkMatch.group(1)
        val displayTetwittert = linkMatch.group(2)

        currentTetwittert = currentTetwittert.substring(0, linkMatch.start) + displayTetwittert + currentTetwittert
          .substring(linkMatch.end)

        adjustEntities(
          entities,
          linkMatch.start,
          linkMatch.end - (linkMatch.start + displayTetwittert.length))

        entities.append(
          RichTetwittertEntity(
            fromIndetwitter = linkMatch.start,
            toIndetwitter = linkMatch.start + displayTetwittert.length,
            ref = linkMap.get(tag).map { url =>
              Url(
                urlType = linkTypeMap.getOrElse(tag, EtwitterternalUrl),
                url = url
              )
            },
            format = None
          )
        )
      }
      matchOpt = LinkAnchorRegetwitter.findFirstMatchIn(currentTetwittert)
    }

    // Handle Bold
    matchOpt = BoldRegetwitter.findFirstMatchIn(currentTetwittert)
    while (matchOpt.isDefined) {
      matchOpt.foreach { boldMatch =>
        val tetwittert = boldMatch.group(1)

        currentTetwittert =
          currentTetwittert.substring(0, boldMatch.start) + tetwittert + currentTetwittert.substring(boldMatch.end)

        adjustEntities(entities, boldMatch.start, boldMatch.end - (boldMatch.start + tetwittert.length))

        entities.append(
          RichTetwittertEntity(
            fromIndetwitter = boldMatch.start,
            toIndetwitter = boldMatch.start + tetwittert.length,
            ref = None,
            format = Some(Strong),
          )
        )
      }

      matchOpt = BoldRegetwitter.findFirstMatchIn(currentTetwittert)
    }

    RichTetwittert(
      currentTetwittert,
      entities.sortBy(_.fromIndetwitter).toList, // always return immutable copies!
      rtl,
      alignment
    )
  }

  /* When we create a new entity, we need to adjust
   * any already etwitteristing entities that have been moved.
   * Entities cannot overlap, so we can just compare start positions.
   */
  private def adjustEntities(
    entities: scala.collection.mutable.ArrayBuffer[RichTetwittertEntity],
    start: Int,
    length: Int
  ): Unit = {
    for (i <- entities.indices) {
      if (entities(i).fromIndetwitter > start) {
        val old = entities(i)
        entities.update(
          i,
          entities(i).copy(
            fromIndetwitter = old.fromIndetwitter - length,
            toIndetwitter = old.toIndetwitter - length
          ))
      }
    }
  }
}
