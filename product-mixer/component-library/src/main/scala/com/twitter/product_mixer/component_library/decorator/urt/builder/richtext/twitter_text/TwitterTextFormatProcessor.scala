package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext.tw ter_text

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.Pla n
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextFormat
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.Strong
 mport scala.collect on.mutable

object Tw terTextFormatProcessor {
  lazy val defaultFormatProcessor = Tw terTextFormatProcessor()
}

/**
 * Add t  correspond ng [[R chTextFormat]] extract on log c  nto [[Tw terTextRenderer]].
 * T  [[Tw terTextRenderer]] after be ng processed w ll extract t  def ned ent  es. 
 */
case class Tw terTextFormatProcessor(
  formats: Set[R chTextFormat] = Set(Pla n, Strong),
) extends Tw terTextRendererProcessor {

  pr vate val formatMap = formats.map { format => format.na .toLo rCase -> format }.toMap

  pr vate[t ] val formatMatc r = {
    val formatNa s = formatMap.keys.toSet
    s"<(/?)(${formatNa s.mkStr ng("|")})>".r
  }

  def renderText(text: Str ng): R chText = {
    process(Tw terTextRenderer(text)).bu ld
  }

  def process(r chTextBu lder: Tw terTextRenderer): Tw terTextRenderer = {
    val text = r chTextBu lder.text
    val nodeStack = mutable.ArrayStack[(R chTextFormat,  nt)]()
    var offset = 0

    formatMatc r.f ndAllMatch n(text).foreach { m =>
      formatMap.get(m.group(2)) match {
        case So (format) => {
           f (m.group(1).nonEmpty) {
             f (!nodeStack. adOpt on.ex sts {
                case (formatFromStack, _) => formatFromStack == format
              }) {
              throw Unmatc dFormatTag(format)
            }
            val (_, start ndex) = nodeStack.pop
            r chTextBu lder. rgeFormat(start ndex, m.start + offset, format)
          } else {
            nodeStack.push((format, m.start + offset))
          }
          r chTextBu lder.remove(m.start + offset, m.end + offset)
          offset -= m.end - m.start
        }
        case _ => //  f format  s not found, sk p t  format
      }
    }

     f (nodeStack.nonEmpty) {
      throw Unmatc dFormatTag(nodeStack. ad._1)
    }

    r chTextBu lder
  }
}

case class Unmatc dFormatTag(format: R chTextFormat)
    extends Except on(s"Unmatc d format start and end tags for $format")
