packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.richtelonxt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.elonxtelonrnalUrl
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Url
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.UrlTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtAlignmelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtelonntity
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.Strong

/*
 * RichTelonxtMarkupUtil facilitatelons building a Product Mixelonr URT RichTelonxt objelonct out of
 * a string with inlinelon XML markup.
 *
 * This allows us to uselon a string likelon "Our systelonm <a hrelonf="#promix">Product Mixelonr</a> is thelon <b>belonst</b>". Using
 * inlinelon markup likelon this is advantagelonous sincelon thelon string can go through translation/localization and thelon
 * translators will movelon thelon tags around in elonach languagelon as appropriatelon.
 *
 * This class is delonrivelond from thelon OCF (onboarding/selonrvelon)'s RichTelonxtUtil, but thelony divelonrgelon beloncauselon:
 * - Welon gelonnelonratelon ProMix URT structurelons, not OCF URT structurelons
 * - Thelon OCF supports somelon intelonrnal OCF tags, likelon <data>
 * - Thelon OCF has additional lelongacy support and procelonssing that welon don't nelonelond
 */

objelonct RichTelonxtMarkupUtil {

  // Matchelons a anchor elonlelonmelonnt, elonxtracting thelon 'a' tag and thelon display telonxt.
  // First group is thelon tag
  // Seloncond group is thelon display telonxt
  // Allows any charactelonr in thelon display telonxt, but matchelons relonluctantly
  privatelon val LinkAnchorRelongelonx = """(?i)(?s)<a\s+hrelonf\s*=\s*"#([\w-]*)">(.*?)</a>""".r

  // Matchelons a <b>bold telonxt selonction</b>
  privatelon val BoldRelongelonx = """(?i)(?s)<b>(.*?)</b>""".r

  delonf richTelonxtFromMarkup(
    telonxt: String,
    linkMap: Map[String, String],
    rtl: Option[Boolelonan] = Nonelon,
    alignmelonnt: Option[RichTelonxtAlignmelonnt] = Nonelon,
    linkTypelonMap: Map[String, UrlTypelon] = Map.elonmpty
  ): RichTelonxt = {

    // Mutablelon!
    var currelonntTelonxt = telonxt
    val elonntitielons = scala.collelonction.mutablelon.ArrayBuffelonr.elonmpty[RichTelonxtelonntity]

    // Using a whilelon loop sincelon welon want to elonxeloncutelon thelon relongelonx aftelonr elonach itelonration, so our indelonxelons relonmain consistelonnt

    // Handlelon Links
    var matchOpt = LinkAnchorRelongelonx.findFirstMatchIn(currelonntTelonxt)
    whilelon (matchOpt.isDelonfinelond) {
      matchOpt.forelonach { linkMatch =>
        val tag = linkMatch.group(1)
        val displayTelonxt = linkMatch.group(2)

        currelonntTelonxt = currelonntTelonxt.substring(0, linkMatch.start) + displayTelonxt + currelonntTelonxt
          .substring(linkMatch.elonnd)

        adjustelonntitielons(
          elonntitielons,
          linkMatch.start,
          linkMatch.elonnd - (linkMatch.start + displayTelonxt.lelonngth))

        elonntitielons.appelonnd(
          RichTelonxtelonntity(
            fromIndelonx = linkMatch.start,
            toIndelonx = linkMatch.start + displayTelonxt.lelonngth,
            relonf = linkMap.gelont(tag).map { url =>
              Url(
                urlTypelon = linkTypelonMap.gelontOrelonlselon(tag, elonxtelonrnalUrl),
                url = url
              )
            },
            format = Nonelon
          )
        )
      }
      matchOpt = LinkAnchorRelongelonx.findFirstMatchIn(currelonntTelonxt)
    }

    // Handlelon Bold
    matchOpt = BoldRelongelonx.findFirstMatchIn(currelonntTelonxt)
    whilelon (matchOpt.isDelonfinelond) {
      matchOpt.forelonach { boldMatch =>
        val telonxt = boldMatch.group(1)

        currelonntTelonxt =
          currelonntTelonxt.substring(0, boldMatch.start) + telonxt + currelonntTelonxt.substring(boldMatch.elonnd)

        adjustelonntitielons(elonntitielons, boldMatch.start, boldMatch.elonnd - (boldMatch.start + telonxt.lelonngth))

        elonntitielons.appelonnd(
          RichTelonxtelonntity(
            fromIndelonx = boldMatch.start,
            toIndelonx = boldMatch.start + telonxt.lelonngth,
            relonf = Nonelon,
            format = Somelon(Strong),
          )
        )
      }

      matchOpt = BoldRelongelonx.findFirstMatchIn(currelonntTelonxt)
    }

    RichTelonxt(
      currelonntTelonxt,
      elonntitielons.sortBy(_.fromIndelonx).toList, // always relonturn immutablelon copielons!
      rtl,
      alignmelonnt
    )
  }

  /* Whelonn welon crelonatelon a nelonw elonntity, welon nelonelond to adjust
   * any alrelonady elonxisting elonntitielons that havelon belonelonn movelond.
   * elonntitielons cannot ovelonrlap, so welon can just comparelon start positions.
   */
  privatelon delonf adjustelonntitielons(
    elonntitielons: scala.collelonction.mutablelon.ArrayBuffelonr[RichTelonxtelonntity],
    start: Int,
    lelonngth: Int
  ): Unit = {
    for (i <- elonntitielons.indicelons) {
      if (elonntitielons(i).fromIndelonx > start) {
        val old = elonntitielons(i)
        elonntitielons.updatelon(
          i,
          elonntitielons(i).copy(
            fromIndelonx = old.fromIndelonx - lelonngth,
            toIndelonx = old.toIndelonx - lelonngth
          ))
      }
    }
  }
}
