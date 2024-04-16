package com.tw ter.v s b l y.generators

 mport com.tw ter.v s b l y.common.act ons. nterst  alReason
 mport com.tw ter.v s b l y.common.act ons.Local zed ssage
 mport com.tw ter.v s b l y.common.act ons. ssageL nk
 mport com.tw ter.v s b l y.results.r chtext. nterst  alReasonToR chText
 mport com.tw ter.v s b l y.results.r chtext. nterst  alReasonToR chText. nterst  alCopy
 mport com.tw ter.v s b l y.results.r chtext. nterst  alReasonToR chText. nterst  alL nk
 mport com.tw ter.v s b l y.results.translat on.LearnMoreL nk
 mport com.tw ter.v s b l y.results.translat on.Res ce
 mport com.tw ter.v s b l y.results.translat on.Translator

object  nterst  alReasonToLocal zed ssage {
  def apply(
    reason:  nterst  alReason,
    languageTag: Str ng,
  ): Opt on[Local zed ssage] = {
     nterst  alReasonToR chText.reasonToCopy(reason).map { copy =>
      val text = Translator.translate(
        copy.res ce,
        languageTag
      )
      local zeW hCopyAndText(copy, languageTag, text)
    }
  }

  pr vate def local zeW hCopyAndText(
    copy:  nterst  alCopy,
    languageTag: Str ng,
    text: Str ng
  ): Local zed ssage = {
    val learnMore = Translator.translate(LearnMoreL nk, languageTag)

    val learnMoreL nkOpt =
      copy.l nk.map { l nk =>
         ssageL nk(key = Res ce.LearnMorePlaceholder, d splayText = learnMore, ur  = l nk)
      }
    val add  onalL nks = copy.add  onalL nks.map {
      case  nterst  alL nk(placeholder, copyRes ce, l nk) =>
        val copyText = Translator.translate(copyRes ce, languageTag)
         ssageL nk(key = placeholder, d splayText = copyText, ur  = l nk)
    }

    val l nks = learnMoreL nkOpt.toSeq ++ add  onalL nks
    Local zed ssage( ssage = text, language = languageTag, l nks = l nks)
  }
}
