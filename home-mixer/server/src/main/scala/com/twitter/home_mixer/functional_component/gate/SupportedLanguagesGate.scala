package com.twitter.home_mixer.functional_component.gate

import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object SupportedLanguagesGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("SupportedLanguages")

  // Production languages which have high translation coverage for strings used in Home Timeline.
  private val supportedLanguages: Set[String] = Set(
    "ar", // Arabic
    "ar-x-fm", // Arabic (Female)
    "bg", // Bulgarian
    "bn", // Bengali
    "ca", // Catalan
    "cs", // Czech
    "da", // Danish
    "de", // German
    "el", // Greek
    "en", // English
    "en-gb", // British English
    "en-ss", // English Screen shot
    "en-xx", // English Pseudo
    "es", // Spanish
    "eu", // Basque
    "fa", // Farsi (Persian)
    "fi", // Finnish
    "fil", // Filipino
    "fr", // French
    "ga", // Irish
    "gl", // Galician
    "gu", // Gujarati
    "he", // Hebrew
    "hi", // Hindi
    "hr", // Croatian
    "hu", // Hungarian
    "id", // Indonesian
    "it", // Italian
    "ja", // Japanese
    "kn", // Kannada
    "ko", // Korean
    "mr", // Marathi
    "msa", // Malay
    "nl", // Dutch
    "no", // Norwegian
    "pl", // Polish
    "pt", // Portuguese
    "ro", // Romanian
    "ru", // Russian
    "sk", // Slovak
    "sr", // Serbian
    "sv", // Swedish
    "ta", // Tamil
    "th", // Thai
    "tr", // Turkish
    "uk", // Ukrainian
    "ur", // Urdu
    "vi", // Vietnamese
    "zh-cn", // Simplified Chinese
    "zh-tw" // Traditional Chinese
  )

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] =
    Stitch.value(query.getLanguageCode.forall(supportedLanguages.contains))
}
