package com.twitter.visibility.generators

import com.twitter.visibility.common.actions.LocalizedMessage
import com.twitter.visibility.common.actions.MessageLink
import com.twitter.visibility.results.translation.Translator
import com.twitter.visibility.results.richtetwittert.EpitaphToRichTetwittert
import com.twitter.visibility.results.translation.Resource
import com.twitter.visibility.results.translation.LearnMoreLink
import com.twitter.visibility.rules.Epitaph
import com.twitter.visibility.results.richtetwittert.EpitaphToRichTetwittert.Copy

object EpitaphToLocalizedMessage {
  def apply(
    epitaph: Epitaph,
    languageTag: String,
  ): LocalizedMessage = {
    val copy =
      EpitaphToRichTetwittert.EpitaphToPolicyMap.getOrElse(epitaph, EpitaphToRichTetwittert.FallbackPolicy)
    val tetwittert = Translator.translate(
      copy.resource,
      languageTag
    )
    localizeWithCopyAndTetwittert(copy, languageTag, tetwittert)
  }

  def apply(
    epitaph: Epitaph,
    languageTag: String,
    applicableCountries: Seq[String],
  ): LocalizedMessage = {
    val copy =
      EpitaphToRichTetwittert.EpitaphToPolicyMap.getOrElse(epitaph, EpitaphToRichTetwittert.FallbackPolicy)
    val tetwittert = Translator.translateWithSimplePlaceholderReplacement(
      copy.resource,
      languageTag,
      Map((Resource.ApplicableCountriesPlaceholder -> applicableCountries.mkString(", ")))
    )
    localizeWithCopyAndTetwittert(copy, languageTag, tetwittert)
  }

  private def localizeWithCopyAndTetwittert(
    copy: Copy,
    languageTag: String,
    tetwittert: String
  ): LocalizedMessage = {
    val learnMore = Translator.translate(LearnMoreLink, languageTag)

    val links = copy.additionalLinks match {
      case links if links.nonEmpty =>
        MessageLink(Resource.LearnMorePlaceholder, learnMore, copy.link) +:
          links.map {
            case EpitaphToRichTetwittert.Link(placeholder, copyResource, link) =>
              val copyTetwittert = Translator.translate(copyResource, languageTag)
              MessageLink(placeholder, copyTetwittert, link)
          }
      case _ =>
        Seq(
          MessageLink(
            key = Resource.LearnMorePlaceholder,
            displayTetwittert = learnMore,
            uri = copy.link))
    }

    LocalizedMessage(message = tetwittert, language = languageTag, links = links)
  }
}
