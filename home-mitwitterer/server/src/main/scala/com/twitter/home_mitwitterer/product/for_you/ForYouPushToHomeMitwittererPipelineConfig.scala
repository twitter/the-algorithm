package com.twitter.home_mitwitterer.product.for_you

import com.twitter.home_mitwitterer.product.for_you.model.ForYouQuery
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.UrtDomainMarshaller
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.AddEntriesInstructionBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.ClearCacheInstructionBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.OrderedBottomCursorBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.OrderedTopCursorBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.ParamGatedIncludeInstruction
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.StaticTimelineScribeConfigBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.UrtMetadataBuilder
import com.twitter.product_mitwitterer.component_library.selector.InsertAppendResults
import com.twitter.product_mitwitterer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.UrtTransportMarshaller
import com.twitter.product_mitwitterer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.MitwittererPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.mitwitterer.MitwittererPipelineConfig
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ForYouPushToHomeMitwittererPipelineConfig @Inject() (
  forYouPushToHomeTweetCandidatePipelineConfig: ForYouPushToHomeTweetCandidatePipelineConfig,
  urtTransportMarshaller: UrtTransportMarshaller)
    etwittertends MitwittererPipelineConfig[ForYouQuery, Timeline, urt.TimelineResponse] {

  override val identifier: MitwittererPipelineIdentifier = MitwittererPipelineIdentifier("ForYouPushToHome")

  override val candidatePipelines: Seq[CandidatePipelineConfig[ForYouQuery, _, _, _]] =
    Seq(forYouPushToHomeTweetCandidatePipelineConfig)

  override val resultSelectors: Seq[Selector[ForYouQuery]] =
    Seq(InsertAppendResults(forYouPushToHomeTweetCandidatePipelineConfig.identifier))

  override val domainMarshaller: DomainMarshaller[ForYouQuery, Timeline] = {
    val instructionBuilders = Seq(
      ClearCacheInstructionBuilder(
        ParamGatedIncludeInstruction(ForYouParam.EnableClearCacheOnPushToHome)),
      AddEntriesInstructionBuilder())

    val idSelector: PartialFunction[UniversalNoun[_], Long] = { case item: TweetItem => item.id }
    val topCursorBuilder = OrderedTopCursorBuilder(idSelector)
    val bottomCursorBuilder = OrderedBottomCursorBuilder(idSelector)

    val metadataBuilder = UrtMetadataBuilder(
      title = None,
      scribeConfigBuilder = Some(
        StaticTimelineScribeConfigBuilder(
          TimelineScribeConfig(
            page = Some("for_you_push_to_home"),
            section = None,
            entityToken = None)
        )
      )
    )

    UrtDomainMarshaller(
      instructionBuilders = instructionBuilders,
      metadataBuilder = Some(metadataBuilder),
      cursorBuilders = Seq(topCursorBuilder, bottomCursorBuilder)
    )
  }

  override val transportMarshaller: TransportMarshaller[Timeline, urt.TimelineResponse] =
    urtTransportMarshaller
}
