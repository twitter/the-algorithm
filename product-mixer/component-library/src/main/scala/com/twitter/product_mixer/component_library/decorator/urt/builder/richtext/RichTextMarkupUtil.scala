package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.ExternalUrl
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.UrlType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextAl gn nt
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextEnt y
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.Strong

/*
 * R chTextMarkupUt l fac l ates bu ld ng a Product M xer URT R chText object out of
 * a str ng w h  nl ne XML markup.
 *
 * T  allows us to use a str ng l ke "  system <a href="#prom x">Product M xer</a>  s t  <b>best</b>". Us ng
 *  nl ne markup l ke t   s advantageous s nce t  str ng can go through translat on/local zat on and t 
 * translators w ll move t  tags around  n each language as appropr ate.
 *
 * T  class  s der ved from t  OCF (onboard ng/serve)'s R chTextUt l, but t y d verge because:
 * -   generate ProM x URT structures, not OCF URT structures
 * - T  OCF supports so   nternal OCF tags, l ke <data>
 * - T  OCF has add  onal legacy support and process ng that   don't need
 */

object R chTextMarkupUt l {

  // Matc s a anchor ele nt, extract ng t  'a' tag and t  d splay text.
  // F rst group  s t  tag
  // Second group  s t  d splay text
  // Allows any character  n t  d splay text, but matc s reluctantly
  pr vate val L nkAnchorRegex = """(? )(?s)<a\s+href\s*=\s*"#([\w-]*)">(.*?)</a>""".r

  // Matc s a <b>bold text sect on</b>
  pr vate val BoldRegex = """(? )(?s)<b>(.*?)</b>""".r

  def r chTextFromMarkup(
    text: Str ng,
    l nkMap: Map[Str ng, Str ng],
    rtl: Opt on[Boolean] = None,
    al gn nt: Opt on[R chTextAl gn nt] = None,
    l nkTypeMap: Map[Str ng, UrlType] = Map.empty
  ): R chText = {

    // Mutable!
    var currentText = text
    val ent  es = scala.collect on.mutable.ArrayBuffer.empty[R chTextEnt y]

    // Us ng a wh le loop s nce   want to execute t  regex after each  erat on, so    ndexes rema n cons stent

    // Handle L nks
    var matchOpt = L nkAnchorRegex.f ndF rstMatch n(currentText)
    wh le (matchOpt. sDef ned) {
      matchOpt.foreach { l nkMatch =>
        val tag = l nkMatch.group(1)
        val d splayText = l nkMatch.group(2)

        currentText = currentText.substr ng(0, l nkMatch.start) + d splayText + currentText
          .substr ng(l nkMatch.end)

        adjustEnt  es(
          ent  es,
          l nkMatch.start,
          l nkMatch.end - (l nkMatch.start + d splayText.length))

        ent  es.append(
          R chTextEnt y(
            from ndex = l nkMatch.start,
            to ndex = l nkMatch.start + d splayText.length,
            ref = l nkMap.get(tag).map { url =>
              Url(
                urlType = l nkTypeMap.getOrElse(tag, ExternalUrl),
                url = url
              )
            },
            format = None
          )
        )
      }
      matchOpt = L nkAnchorRegex.f ndF rstMatch n(currentText)
    }

    // Handle Bold
    matchOpt = BoldRegex.f ndF rstMatch n(currentText)
    wh le (matchOpt. sDef ned) {
      matchOpt.foreach { boldMatch =>
        val text = boldMatch.group(1)

        currentText =
          currentText.substr ng(0, boldMatch.start) + text + currentText.substr ng(boldMatch.end)

        adjustEnt  es(ent  es, boldMatch.start, boldMatch.end - (boldMatch.start + text.length))

        ent  es.append(
          R chTextEnt y(
            from ndex = boldMatch.start,
            to ndex = boldMatch.start + text.length,
            ref = None,
            format = So (Strong),
          )
        )
      }

      matchOpt = BoldRegex.f ndF rstMatch n(currentText)
    }

    R chText(
      currentText,
      ent  es.sortBy(_.from ndex).toL st, // always return  mmutable cop es!
      rtl,
      al gn nt
    )
  }

  /* W n   create a new ent y,   need to adjust
   * any already ex st ng ent  es that have been moved.
   * Ent  es cannot overlap, so   can just compare start pos  ons.
   */
  pr vate def adjustEnt  es(
    ent  es: scala.collect on.mutable.ArrayBuffer[R chTextEnt y],
    start:  nt,
    length:  nt
  ): Un  = {
    for (  <- ent  es. nd ces) {
       f (ent  es( ).from ndex > start) {
        val old = ent  es( )
        ent  es.update(
           ,
          ent  es( ).copy(
            from ndex = old.from ndex - length,
            to ndex = old.to ndex - length
          ))
      }
    }
  }
}
