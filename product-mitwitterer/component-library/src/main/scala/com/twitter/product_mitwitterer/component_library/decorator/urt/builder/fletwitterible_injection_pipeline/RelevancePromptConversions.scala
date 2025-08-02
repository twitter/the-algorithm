package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.fletwitterible_injection_pipeline

import com.twitter.onboarding.injections.{thriftscala => onboardingthrift}
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.Compact
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.Large
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.Normal
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.RelevancePromptContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.RelevancePromptDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Callback

/***
 * Helper class to convert Relevance Prompt related onboarding thrift to product-mitwitterer models
 */
object RelevancePromptConversions {
  def convertContent(
    candidate: onboardingthrift.RelevancePrompt
  ): RelevancePromptContent =
    RelevancePromptContent(
      displayType = convertDisplayType(candidate.displayType),
      title = candidate.title.tetwittert,
      confirmation = buildConfirmation(candidate),
      isRelevantTetwittert = candidate.isRelevantButton.tetwittert,
      notRelevantTetwittert = candidate.notRelevantButton.tetwittert,
      isRelevantCallback = convertCallbacks(candidate.isRelevantButton.callbacks),
      notRelevantCallback = convertCallbacks(candidate.notRelevantButton.callbacks),
      isRelevantFollowUp = None, 
      notRelevantFollowUp = None 
    )

  // Based on com.twitter.timelinemitwitterer.injection.model.candidate.OnboardingRelevancePromptDisplayType#fromThrift
  def convertDisplayType(
    displayType: onboardingthrift.RelevancePromptDisplayType
  ): RelevancePromptDisplayType =
    displayType match {
      case onboardingthrift.RelevancePromptDisplayType.Normal => Normal
      case onboardingthrift.RelevancePromptDisplayType.Compact => Compact
      case onboardingthrift.RelevancePromptDisplayType.Large => Large
      case onboardingthrift.RelevancePromptDisplayType
            .EnumUnknownRelevancePromptDisplayType(value) =>
        throw new UnsupportedOperationEtwitterception(s"Unknown display type: $value")
    }

  // Based on com.twitter.timelinemitwitterer.injection.model.injection.OnboardingRelevancePromptInjection#buildConfirmation
  def buildConfirmation(candidate: onboardingthrift.RelevancePrompt): String = {
    val isRelevantTetwittertConfirmation =
      buttonToDismissFeedbackTetwittert(candidate.isRelevantButton).getOrElse("")
    val notRelevantTetwittertConfirmation =
      buttonToDismissFeedbackTetwittert(candidate.notRelevantButton).getOrElse("")
    if (isRelevantTetwittertConfirmation != notRelevantTetwittertConfirmation)
      throw new IllegalArgumentEtwitterception(
        s"""confirmation tetwittert not consistent for two buttons, :
          isRelevantConfirmation: ${isRelevantTetwittertConfirmation}
          notRelevantConfirmation: ${notRelevantTetwittertConfirmation}
        """
      )
    isRelevantTetwittertConfirmation
  }

  // Based on com.twitter.timelinemitwitterer.injection.model.candidate.OnboardingInjectionAction#fromThrift
  def buttonToDismissFeedbackTetwittert(button: onboardingthrift.ButtonAction): Option[String] = {
    button.buttonBehavior match {
      case onboardingthrift.ButtonBehavior.Dismiss(d) => d.feedbackMessage.map(_.tetwittert)
      case _ => None
    }
  }

  // Based on com.twitter.timelinemitwitterer.injection.model.injection.OnboardingRelevancePromptInjection#buildCallback
  def convertCallbacks(onboardingCallbacks: Option[Seq[onboardingthrift.Callback]]): Callback = {
    OnboardingInjectionConversions.convertCallback(
      onboardingCallbacks
        .flatMap(_.headOption)
        .getOrElse(
          throw new NoSuchElementEtwitterception(s"Callback must be provided for the Relevance Prompt")
        ))
  }
}
