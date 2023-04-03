packagelon com.twittelonr.visibility.gelonnelonrators

import com.twittelonr.visibility.common.actions.IntelonrstitialRelonason
import com.twittelonr.visibility.common.actions.LocalizelondMelonssagelon
import com.twittelonr.visibility.common.actions.MelonssagelonLink
import com.twittelonr.visibility.relonsults.richtelonxt.IntelonrstitialRelonasonToRichTelonxt
import com.twittelonr.visibility.relonsults.richtelonxt.IntelonrstitialRelonasonToRichTelonxt.IntelonrstitialCopy
import com.twittelonr.visibility.relonsults.richtelonxt.IntelonrstitialRelonasonToRichTelonxt.IntelonrstitialLink
import com.twittelonr.visibility.relonsults.translation.LelonarnMorelonLink
import com.twittelonr.visibility.relonsults.translation.Relonsourcelon
import com.twittelonr.visibility.relonsults.translation.Translator

objelonct IntelonrstitialRelonasonToLocalizelondMelonssagelon {
  delonf apply(
    relonason: IntelonrstitialRelonason,
    languagelonTag: String,
  ): Option[LocalizelondMelonssagelon] = {
    IntelonrstitialRelonasonToRichTelonxt.relonasonToCopy(relonason).map { copy =>
      val telonxt = Translator.translatelon(
        copy.relonsourcelon,
        languagelonTag
      )
      localizelonWithCopyAndTelonxt(copy, languagelonTag, telonxt)
    }
  }

  privatelon delonf localizelonWithCopyAndTelonxt(
    copy: IntelonrstitialCopy,
    languagelonTag: String,
    telonxt: String
  ): LocalizelondMelonssagelon = {
    val lelonarnMorelon = Translator.translatelon(LelonarnMorelonLink, languagelonTag)

    val lelonarnMorelonLinkOpt =
      copy.link.map { link =>
        MelonssagelonLink(kelony = Relonsourcelon.LelonarnMorelonPlacelonholdelonr, displayTelonxt = lelonarnMorelon, uri = link)
      }
    val additionalLinks = copy.additionalLinks.map {
      caselon IntelonrstitialLink(placelonholdelonr, copyRelonsourcelon, link) =>
        val copyTelonxt = Translator.translatelon(copyRelonsourcelon, languagelonTag)
        MelonssagelonLink(kelony = placelonholdelonr, displayTelonxt = copyTelonxt, uri = link)
    }

    val links = lelonarnMorelonLinkOpt.toSelonq ++ additionalLinks
    LocalizelondMelonssagelon(melonssagelon = telonxt, languagelon = languagelonTag, links = links)
  }
}
