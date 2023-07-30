package com.X.home_mixer.functional_component.decorator

import com.X.home_mixer.functional_component.decorator.builder.HomeConversationModuleMetadataBuilder
import com.X.home_mixer.functional_component.decorator.builder.HomeTimelinesScoreInfoBuilder
import com.X.home_mixer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.X.home_mixer.model.HomeFeatures.ConversationModuleFocalTweetIdFeature
import com.X.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.X.product_mixer.component_library.decorator.urt.UrtMultipleModulesDecorator
import com.X.product_mixer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.timeline_module.StaticModuleDisplayTypeBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.VerticalConversation
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.timelines.injection.scribe.InjectionScribeUtil
import com.X.timelineservice.suggests.{thriftscala => st}

object HomeConversationServiceCandidateDecorator {

  private val ConversationModuleNamespace = EntryNamespace("home-conversation")

  def apply(
    homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder
  ): Some[UrtMultipleModulesDecorator[PipelineQuery, TweetCandidate, Long]] = {
    val suggestType = st.SuggestType.RankedOrganicTweet
    val component = InjectionScribeUtil.scribeComponent(suggestType).get
    val clientEventInfoBuilder = ClientEventInfoBuilder(component)
    val tweetItemBuilder = TweetCandidateUrtItemBuilder(
      clientEventInfoBuilder = clientEventInfoBuilder,
      timelinesScoreInfoBuilder = Some(HomeTimelinesScoreInfoBuilder),
      feedbackActionInfoBuilder = Some(homeFeedbackActionInfoBuilder)
    )

    val moduleBuilder = TimelineModuleBuilder(
      entryNamespace = ConversationModuleNamespace,
      clientEventInfoBuilder = clientEventInfoBuilder,
      displayTypeBuilder = StaticModuleDisplayTypeBuilder(VerticalConversation),
      metadataBuilder = Some(HomeConversationModuleMetadataBuilder())
    )

    Some(
      UrtMultipleModulesDecorator(
        urtItemCandidateDecorator = UrtItemCandidateDecorator(tweetItemBuilder),
        moduleBuilder = moduleBuilder,
        groupByKey = (_, _, candidateFeatures) =>
          candidateFeatures.getOrElse(ConversationModuleFocalTweetIdFeature, None)
      ))
  }
}
