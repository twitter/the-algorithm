package com.twitter.home_mixer.functional_component.decorator.builder

import com.twitter.product_mixer.component_library.decorator.urt.builder.item.message.InlinePromptCandidateUrtItemStringCenterBuilder.InlinePromptClientEventInfoElement
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.message.MessageTextActionBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.stringcenter.Str
import com.twitter.product_mixer.component_library.model.candidate.InlinePromptCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.InlinePromptMessageContent
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.MessagePromptItem
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stringcenter.client.ExternalStringRegistry
import com.twitter.stringcenter.client.StringCenter

case class VerifiedPromptBuilder(
  clientEventInfoBuilder: BaseClientEventInfoBuilder[PipelineQuery, InlinePromptCandidate],
  stringCenter: StringCenter,
  externalStringRegistry: ExternalStringRegistry)
    extends CandidateUrtEntryBuilder[
      PipelineQuery,
      InlinePromptCandidate,
      MessagePromptItem
    ] {
  private val VerifiedUrl = ""

  private val headerExternalStr = externalStringRegistry.createProdString("verified_prompt_header")
  private val bodyExternalStr = externalStringRegistry.createProdString("verified_prompt_body")
  private val buttonExternalStr =
    externalStringRegistry.createProdString("creator_subscriptions_teaser_button")

  override def apply(
    query: PipelineQuery,
    candidate: InlinePromptCandidate,
    candidateFeatures: FeatureMap
  ): MessagePromptItem = {
    val headerStr = Str(headerExternalStr, stringCenter).apply(query, candidate, candidateFeatures)
    val bodyStr = Str(bodyExternalStr, stringCenter).apply(query, candidate, candidateFeatures)
    val buttonStrBuilder = Str(buttonExternalStr, stringCenter)

    val button = MessageTextActionBuilder(
      textBuilder = buttonStrBuilder,
      dismissOnClick = false,
      url = Some(VerifiedUrl)
    ).apply(query, candidate, candidateFeatures)

    MessagePromptItem(
      id = candidate.id,
      sortIndex = None, // Sort indexes are automatically set in the domain marshaller phase
      clientEventInfo = clientEventInfoBuilder(
        query = query,
        candidate = candidate,
        candidateFeatures = candidateFeatures,
        element = Some(InlinePromptClientEventInfoElement)
      ),
      isPinned = None,
      content = InlinePromptMessageContent(
        headerText = headerStr,
        bodyText = Some(bodyStr),
        primaryButtonAction = Some(button),
        secondaryButtonAction = None,
        headerRichText = None,
        bodyRichText = None,
        socialContext = None,
        userFacepile = None
      ),
      impressionCallbacks = None,
      feedbackActionInfo = None
    )
  }
}
