package com.twitter.visibility.generators

import com.twitter.visibility.common.actions.InterstitialReason
import com.twitter.visibility.common.actions.LocalizedMessage
import com.twitter.visibility.common.actions.MessageLink
import com.twitter.visibility.results.richtetwittert.InterstitialReasonToRichTetwittert
import com.twitter.visibility.results.richtetwittert.InterstitialReasonToRichTetwittert.InterstitialCopy
import com.twitter.visibility.results.richtetwittert.InterstitialReasonToRichTetwittert.InterstitialLink
import com.twitter.visibility.results.translation.LearnMoreLink
import com.twitter.visibility.results.translation.Resource
import com.twitter.visibility.results.translation.Translator

object InterstitialReasonToLocalizedMessage {
  def apply(
    reason: InterstitialReason,
    languageTag: String,
  ): Option[LocalizedMessage] = {
    InterstitialReasonToRichTetwittert.reasonToCopy(reason).map { copy =>
      val tetwittert = Translator.translate(
        copy.resource,
        languageTag
      )
      localizeWithCopyAndTetwittert(copy, languageTag, tetwittert)
    }
  }

  private def localizeWithCopyAndTetwittert(
    copy: InterstitialCopy,
    languageTag: String,
    tetwittert: String
  ): LocalizedMessage = {
    val learnMore = Translator.translate(LearnMoreLink, languageTag)

    val learnMoreLinkOpt =
      copy.link.map { link =>
        MessageLink(key = Resource.LearnMorePlaceholder, displayTetwittert = learnMore, uri = link)
      }
    val additionalLinks = copy.additionalLinks.map {
      case InterstitialLink(placeholder, copyResource, link) =>
        val copyTetwittert = Translator.translate(copyResource, languageTag)
        MessageLink(key = placeholder, displayTetwittert = copyTetwittert, uri = link)
    }

    val links = learnMoreLinkOpt.toSeq ++ additionalLinks
    LocalizedMessage(message = tetwittert, language = languageTag, links = links)
  }
}
