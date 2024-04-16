package com.tw ter.v s b l y.generators

 mport com.tw ter.v s b l y.common.act ons.Local zed ssage
 mport com.tw ter.v s b l y.common.act ons. ssageL nk
 mport com.tw ter.v s b l y.results.translat on.Translator
 mport com.tw ter.v s b l y.results.r chtext.Ep aphToR chText
 mport com.tw ter.v s b l y.results.translat on.Res ce
 mport com.tw ter.v s b l y.results.translat on.LearnMoreL nk
 mport com.tw ter.v s b l y.rules.Ep aph
 mport com.tw ter.v s b l y.results.r chtext.Ep aphToR chText.Copy

object Ep aphToLocal zed ssage {
  def apply(
    ep aph: Ep aph,
    languageTag: Str ng,
  ): Local zed ssage = {
    val copy =
      Ep aphToR chText.Ep aphToPol cyMap.getOrElse(ep aph, Ep aphToR chText.FallbackPol cy)
    val text = Translator.translate(
      copy.res ce,
      languageTag
    )
    local zeW hCopyAndText(copy, languageTag, text)
  }

  def apply(
    ep aph: Ep aph,
    languageTag: Str ng,
    appl cableCountr es: Seq[Str ng],
  ): Local zed ssage = {
    val copy =
      Ep aphToR chText.Ep aphToPol cyMap.getOrElse(ep aph, Ep aphToR chText.FallbackPol cy)
    val text = Translator.translateW hS mplePlaceholderReplace nt(
      copy.res ce,
      languageTag,
      Map((Res ce.Appl cableCountr esPlaceholder -> appl cableCountr es.mkStr ng(", ")))
    )
    local zeW hCopyAndText(copy, languageTag, text)
  }

  pr vate def local zeW hCopyAndText(
    copy: Copy,
    languageTag: Str ng,
    text: Str ng
  ): Local zed ssage = {
    val learnMore = Translator.translate(LearnMoreL nk, languageTag)

    val l nks = copy.add  onalL nks match {
      case l nks  f l nks.nonEmpty =>
         ssageL nk(Res ce.LearnMorePlaceholder, learnMore, copy.l nk) +:
          l nks.map {
            case Ep aphToR chText.L nk(placeholder, copyRes ce, l nk) =>
              val copyText = Translator.translate(copyRes ce, languageTag)
               ssageL nk(placeholder, copyText, l nk)
          }
      case _ =>
        Seq(
           ssageL nk(
            key = Res ce.LearnMorePlaceholder,
            d splayText = learnMore,
            ur  = copy.l nk))
    }

    Local zed ssage( ssage = text, language = languageTag, l nks = l nks)
  }
}
