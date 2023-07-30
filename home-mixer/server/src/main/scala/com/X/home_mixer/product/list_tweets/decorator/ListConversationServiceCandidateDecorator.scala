package com.X.home_mixer.product.list_tweets.decorator

import com.X.home_mixer.functional_component.decorator.builder.HomeConversationModuleMetadataBuilder
import com.X.home_mixer.functional_component.decorator.builder.ListClientEventDetailsBuilder
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

object ListConversationServiceCandidateDecorator {

  private val ConversationModuleNamespace = EntryNamespace("list-conversation")

  def apply(): Some[UrtMultipleModulesDecorator[PipelineQuery, TweetCandidate, Long]] = {
    val suggestType = st.SuggestType.OrganicListTweet
    val component = InjectionScribeUtil.scribeComponent(suggestType).get
    val clientEventInfoBuilder = ClientEventInfoBuilder(
      component = component,
      detailsBuilder = Some(ListClientEventDetailsBuilder(st.SuggestType.OrganicListTweet))
    )
    val tweetItemBuilder = TweetCandidateUrtItemBuilder(
      clientEventInfoBuilder = clientEventInfoBuilder
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
