package com.twitter.product_mixer.core.functional_component.marshaller.response.urt

import com.twitter.product_mixer.core.functional_component.marshaller.response.graphql.contextual_ref.ContextualTweetRefMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.graphql.contextual_ref.OuterTweetContextMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.graphql.contextual_ref.TweetHydrationContextMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.rtf.safety_level.SafetyLevelMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert.ShowAlertColorConfigurationMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert.ShowAlertDisplayLocationMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert.ShowAlertIconDisplayInfoMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert.ShowAlertIconMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert.ShowAlertNavigationMetadataMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert.ShowAlertTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.button.ButtonStyleMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.button.CtaButtonMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.button.IconCtaButtonMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.button.TextCtaButtonMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.color.ColorMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.color.ColorPaletteMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.color.RosettaColorMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.commerce.CommerceProductGroupItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.commerce.CommerceProductItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.cover.CoverContentMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.cover.CoverCtaBehaviorMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.cover.CoverCtaMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.cover.CoverImageMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.cover.FullCoverContentMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.cover.FullCoverDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.cover.HalfCoverContentMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.cover.HalfCoverDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.icon.HorizonIconMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.article.ArticleDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.article.ArticleItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.article.ArticleSeedTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.audio_space.AudioSpaceItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.card.CardDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.card.CardItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.conversation_annotation.ConversationAnnotationMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.conversation_annotation.ConversationAnnotationTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.event.EventSummaryDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.event.EventSummaryItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.forward_pivot.ForwardPivotDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.forward_pivot.ForwardPivotMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.forward_pivot.SoftInterventionDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.generic_summary_item.GenericSummaryActionMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.generic_summary_item.GenericSummaryContextMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.generic_summary_item.GenericSummaryDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.generic_summary_item.GenericSummaryItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.highlight.HighlightedSectionMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.icon_label.IconLabelItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.label.LabelDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.label.LabelItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.message._
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.moment.MomentAnnotationItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.prompt._
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.suggestion.SpellingActionTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.suggestion.SpellingItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.suggestion.TextResultMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.thread.ThreadHeaderContentMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.thread.ThreadHeaderItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tile.CallToActionTileContentMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tile.StandardTileContentMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tile.TileContentMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tile.TileItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tombstone.TombstoneDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tombstone.TombstoneInfoMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tombstone.TombstoneItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.topic.TopicDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.topic.TopicFollowPromptDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.topic.TopicFollowPromptItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.topic.TopicFunctionalityTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.topic.TopicItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.trend.TrendItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tweet.TimelinesScoreInfoMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tweet.TweetDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tweet.TweetHighlightsMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tweet.TweetItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tweet_composer.TweetComposerDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tweet_composer.TweetComposerItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.twitter_list.TwitterListDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.twitter_list.TwitterListItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.user.UserDisplayTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.user.UserItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.user.UserReactiveTriggersMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.vertical_grid_item.VerticalGridItemContentMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.vertical_grid_item.VerticalGridItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.vertical_grid_item.VerticalGridItemTileStyleMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.vertical_grid_item.VerticalGridItemTopicFunctionalityTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.vertical_grid_item.VerticalGridItemTopicTileMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.media.AspectRatioMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.media.BroadcastIdMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.media.MediaEntityMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.media.MediaKeyMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.media.MediaMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.media.RectMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.media.TweetMediaMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata._
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.operation.CursorDisplayTreatmentMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.operation.CursorItemMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.operation.CursorOperationMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.operation.CursorTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.AdMetadataContainerMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.CallToActionMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.ClickTrackingInfoMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.DisclaimerTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.DisclosureTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.DynamicPrerollTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.MediaInfoMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.PrerollMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.PrerollMetadataMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.PromotedMetadataMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.SkAdNetworkDataMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.SponsorshipTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.UrlOverrideTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.VideoVariantsMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.reaction.TimelineReactionMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext.ReferenceObjectMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextAlignmentMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextEntityMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextFormatMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.timeline_module._

/**
 * Convenience constructor for services not using dependency injection and unit tests. If using
 * dependency injection, instead `@Inject` an instance of [[UrtTransportMarshaller]] to construct.
 */
object UrtTransportMarshallerBuilder {
  val conversationSectionMarshaller = new ConversationSectionMarshaller
  val conversationDetailsMarshaller = new ConversationDetailsMarshaller(
    conversationSectionMarshaller)
  val timelinesDetailsMarshaller = new TimelinesDetailsMarshaller
  val articleDetailsMarshaller = new ArticleDetailsMarshaller
  val liveEventDetailsMarshaller = new LiveEventDetailsMarshaller
  val commerceDetailsMarshaller = new CommerceDetailsMarshaller
  val clientEventDetailsMarshaller =
    new ClientEventDetailsMarshaller(
      conversationDetailsMarshaller,
      timelinesDetailsMarshaller,
      articleDetailsMarshaller,
      liveEventDetailsMarshaller,
      commerceDetailsMarshaller)
  val clientEventInfoMarshaller = new ClientEventInfoMarshaller(clientEventDetailsMarshaller)

  val feedbackTypeMarshaller = new FeedbackTypeMarshaller
  val confirmationDisplayTypeMarshaller = new ConfirmationDisplayTypeMarshaller
  val horizonIconMarshaller = new HorizonIconMarshaller
  val richFeedbackBehaviorMarshaller = new RichFeedbackBehaviorMarshaller
  val childFeedbackActionMarshaller = new ChildFeedbackActionMarshaller(
    feedbackTypeMarshaller = feedbackTypeMarshaller,
    confirmationDisplayTypeMarshaller = confirmationDisplayTypeMarshaller,
    clientEventInfoMarshaller = clientEventInfoMarshaller,
    horizonIconMarshaller = horizonIconMarshaller,
    richFeedbackBehaviorMarshaller = richFeedbackBehaviorMarshaller
  )
  val feedbackActionMarshaller = new FeedbackActionMarshaller(
    childFeedbackActionMarshaller = childFeedbackActionMarshaller,
    feedbackTypeMarshaller = feedbackTypeMarshaller,
    confirmationDisplayTypeMarshaller = confirmationDisplayTypeMarshaller,
    clientEventInfoMarshaller = clientEventInfoMarshaller,
    horizonIconMarshaller = horizonIconMarshaller,
    richFeedbackBehaviorMarshaller = richFeedbackBehaviorMarshaller
  )
  val feedbackDisplayContextMarshaller = new FeedbackDisplayContextMarshaller
  val feedbackInfoMarshaller = new FeedbackInfoMarshaller(
    feedbackActionMarshaller = feedbackActionMarshaller,
    feedbackDisplayContextMarshaller = feedbackDisplayContextMarshaller,
    clientEventInfoMarshaller = clientEventInfoMarshaller
  )

  val urlTypeMarshaller = new UrlTypeMarshaller
  val urtEndpointOptionsMarshaller = new UrtEndpointOptionsMarshaller
  val urlMarshaller = new UrlMarshaller(
    urlTypeMarshaller = urlTypeMarshaller,
    urtEndpointOptionsMarshaller = urtEndpointOptionsMarshaller
  )
  val referenceObjectMarshaller = new ReferenceObjectMarshaller(urlMarshaller)
  val richTextFormatMarshaller = new RichTextFormatMarshaller
  val richTextEntityMarshaller =
    new RichTextEntityMarshaller(referenceObjectMarshaller, richTextFormatMarshaller)
  val richTextAlignmentMarshaller = new RichTextAlignmentMarshaller
  val richTextMarshaller =
    new RichTextMarshaller(richTextEntityMarshaller, richTextAlignmentMarshaller)

  val tombstoneInfoMarshaller = new TombstoneInfoMarshaller(richTextMarshaller = richTextMarshaller)

  val generalContextTypeMarshaller = new GeneralContextTypeMarshaller
  val generalContextMarshaller = new GeneralContextMarshaller(
    generalContextTypeMarshaller = generalContextTypeMarshaller,
    urlMarshaller = urlMarshaller
  )

  val timelineReactionMarshaller = new TimelineReactionMarshaller

  val topicContextMarshaller = new TopicContextMarshaller()

  val socialContextMarshaller = new SocialContextMarshaller(
    generalContextMarshaller = generalContextMarshaller,
    topicContextMarshaller = topicContextMarshaller
  )

  val highlightedSectionMarshaller = new HighlightedSectionMarshaller()
  val tweetHighlightsMarshaller = new TweetHighlightsMarshaller(highlightedSectionMarshaller)

  val topicDisplayTypeMarshaller = new TopicDisplayTypeMarshaller
  val topicFunctionalityTypeMarshaller = new TopicFunctionalityTypeMarshaller
  val topicItemMarshaller = new TopicItemMarshaller(
    displayTypeMarshaller = topicDisplayTypeMarshaller,
    functionalityTypeMarshaller = topicFunctionalityTypeMarshaller
  )

  val topicFollowPromptDisplayTypeMarshaller = new TopicFollowPromptDisplayTypeMarshaller
  val topicFollowPromptItemMarshaller = new TopicFollowPromptItemMarshaller(
    displayTypeMarshaller = topicFollowPromptDisplayTypeMarshaller
  )

  val rosettaColorMarshaller = new RosettaColorMarshaller()
  val badgeMarshaller = new BadgeMarshaller(
    rosettaColorMarshaller = rosettaColorMarshaller
  )
  val iconCtaButtonMarshaller = new IconCtaButtonMarshaller(horizonIconMarshaller, urlMarshaller)
  val textCtaButtonMarshaller = new TextCtaButtonMarshaller(urlMarshaller)
  val ctaButtonMarshaller =
    new CtaButtonMarshaller(iconCtaButtonMarshaller, textCtaButtonMarshaller)

  val standardTileContentMarshaller = new StandardTileContentMarshaller(
    badgeMarshaller = badgeMarshaller
  )
  val callToActionTileContentMarshaller = new CallToActionTileContentMarshaller(
    ctaButtonMarshaller = ctaButtonMarshaller,
    richTextMarshaller = richTextMarshaller
  )

  val tileContentMarshaller = new TileContentMarshaller(
    standardTileContentMarshaller = standardTileContentMarshaller,
    callToActionTileContentMarshaller = callToActionTileContentMarshaller
  )
  val colorMarshaller = new ColorMarshaller()
  val colorPaletteMarshaller = new ColorPaletteMarshaller(
    colorMarshaller = colorMarshaller
  )
  val imageVariantMarshaller = new ImageVariantMarshaller(
    colorPaletteMarshaller = colorPaletteMarshaller
  )
  val imageDisplayTypeMarshaller = new ImageDisplayTypeMarshaller()
  val imageAnimationTypeMarshaller = new ImageAnimationTypeMarshaller()

  val softInterventionDisplayTypeMarshaller = new SoftInterventionDisplayTypeMarshaller
  val forwardPivotDisplayTypeMarshaller = new ForwardPivotDisplayTypeMarshaller
  val forwardPivotMarshaller = new ForwardPivotMarshaller(
    urlMarshaller = urlMarshaller,
    richTextMarshaller = richTextMarshaller,
    forwardPivotDisplayTypeMarshaller = forwardPivotDisplayTypeMarshaller,
    imageVariantMarshaller = imageVariantMarshaller,
    badgeMarshaller = badgeMarshaller,
    rosettaColorMarshaller = rosettaColorMarshaller,
    softInterventionDisplayTypeMarshaller = softInterventionDisplayTypeMarshaller
  )

  val tweetDisplayTypeMarshaller = new TweetDisplayTypeMarshaller
  val timelinesScoreInfoMarshaller = new TimelinesScoreInfoMarshaller
  val disclosureTypeMarshaller = new DisclosureTypeMarshaller
  val dynamicPrerollTypeMarshaller = new DynamicPrerollTypeMarshaller
  val callToActionMarshaller = new CallToActionMarshaller
  val videoVariantsMarshaller = new VideoVariantsMarshaller
  val mediaInfoMarshaller = new MediaInfoMarshaller(
    callToActionMarshaller = callToActionMarshaller,
    videoVariantsMarshaller = videoVariantsMarshaller
  )
  val prerollMarshaller = new PrerollMarshaller(
    dynamicPrerollTypeMarshaller = dynamicPrerollTypeMarshaller,
    mediaInfoMarshaller = mediaInfoMarshaller
  )
  val sponsorshipTypeMarshaller = new SponsorshipTypeMarshaller
  val disclaimerTypeMarshaller = new DisclaimerTypeMarshaller
  val skAdNetworkDataMarshaller = new SkAdNetworkDataMarshaller
  val adMetadataContainerMarshaller = new AdMetadataContainerMarshaller(
    sponsorshipTypeMarshaller = sponsorshipTypeMarshaller,
    disclaimerTypeMarshaller = disclaimerTypeMarshaller,
    skAdNetworkDataMarshaller = skAdNetworkDataMarshaller
  )
  val urlOverrideTypeMarshaller = new UrlOverrideTypeMarshaller
  val clickTrackingInfoMarshaller = new ClickTrackingInfoMarshaller(
    urlOverrideTypeMarshaller = urlOverrideTypeMarshaller
  )
  val promotedMetadataMarshaller = new PromotedMetadataMarshaller(
    disclosureTypeMarshaller = disclosureTypeMarshaller,
    adMetadataContainerMarshaller = adMetadataContainerMarshaller,
    clickTrackingInfoMarshaller = clickTrackingInfoMarshaller
  )

  val conversationAnnotationTypeMarshaller = new ConversationAnnotationTypeMarshaller
  val conversationAnnotationMarshaller = new ConversationAnnotationMarshaller(
    conversationAnnotationTypeMarshaller = conversationAnnotationTypeMarshaller,
    richTextMarshaller = richTextMarshaller
  )

  val safetyLevelMarshaller = new SafetyLevelMarshaller
  val outerTweetContextMarshaller = new OuterTweetContextMarshaller
  val tweetHydrationContextMarshaller = new TweetHydrationContextMarshaller(
    safetyLevelMarshaller = safetyLevelMarshaller,
    outerTweetContextMarshaller = outerTweetContextMarshaller
  )
  val contextualTweetRefMarshaller = new ContextualTweetRefMarshaller(
    tweetHydrationContextMarshaller = tweetHydrationContextMarshaller
  )
  val prerollMetadataMarshaller = new PrerollMetadataMarshaller(
    prerollMarshaller = prerollMarshaller
  )

  val rectMarshaller = new RectMarshaller
  val mediaKeyMarshaller = new MediaKeyMarshaller
  val broadcastIdMarshaller = new BroadcastIdMarshaller
  val tweetMediaMarshaller = new TweetMediaMarshaller
  val mediaEntityMarshaller = new MediaEntityMarshaller(
    tweetMediaMarshaller = tweetMediaMarshaller,
    broadcastIdMarshaller = broadcastIdMarshaller,
    imageVariantMarshaller = imageVariantMarshaller)
  val aspectRatioMarshaller = new AspectRatioMarshaller
  val mediaMarshaller = new MediaMarshaller(
    mediaEntityMarshaller = mediaEntityMarshaller,
    mediaKeyMarshaller = mediaKeyMarshaller,
    rectMarshaller = rectMarshaller,
    aspectRatioMarshaller = aspectRatioMarshaller)

  val tweetItemMarshaller = new TweetItemMarshaller(
    tweetDisplayTypeMarshaller = tweetDisplayTypeMarshaller,
    socialContextMarshaller = socialContextMarshaller,
    tweetHighlightsMarshaller = tweetHighlightsMarshaller,
    tombstoneInfoMarshaller = tombstoneInfoMarshaller,
    timelinesScoreInfoMarshaller = timelinesScoreInfoMarshaller,
    forwardPivotMarshaller = forwardPivotMarshaller,
    promotedMetadataMarshaller = promotedMetadataMarshaller,
    conversationAnnotationMarshaller = conversationAnnotationMarshaller,
    contextualTweetRefMarshaller = contextualTweetRefMarshaller,
    prerollMetadataMarshaller = prerollMetadataMarshaller,
    badgeMarshaller = badgeMarshaller,
    urlMarshaller = urlMarshaller
  )

  val eventSummaryDisplayTypeMarshaller = new EventSummaryDisplayTypeMarshaller
  val eventSummaryItemMarshaller = new EventSummaryItemMarshaller(
    eventSummaryDisplayTypeMarshaller = eventSummaryDisplayTypeMarshaller,
    imageVariantMarshaller = imageVariantMarshaller,
    urlMarshaller = urlMarshaller
  )

  val trendItemMarshaller = new TrendItemMarshaller(
    promotedMetadataMarshaller = promotedMetadataMarshaller,
    urlMarshaller = urlMarshaller
  )

  val userDisplayTypeMarshaller = new UserDisplayTypeMarshaller
  val userReactiveTriggersMarshaller = new UserReactiveTriggersMarshaller(
    timelineReactionMarshaller)
  val userItemMarshaller = new UserItemMarshaller(
    userDisplayTypeMarshaller = userDisplayTypeMarshaller,
    promotedMetadataMarshaller = promotedMetadataMarshaller,
    socialContextMarshaller = socialContextMarshaller,
    userReactiveTriggersMarshaller = userReactiveTriggersMarshaller,
  )

  val verticalGridItemTileStyleMarshaller = new VerticalGridItemTileStyleMarshaller
  val verticalGridItemTopicFunctionalityTypeMarshaller =
    new VerticalGridItemTopicFunctionalityTypeMarshaller

  val verticalGridItemTopicTileMarshaller = new VerticalGridItemTopicTileMarshaller(
    styleMarshaller = verticalGridItemTileStyleMarshaller,
    functionalityTypeMarshaller = verticalGridItemTopicFunctionalityTypeMarshaller,
    urlMarshaller = urlMarshaller
  )

  val verticalGridItemContentMarshaller = new VerticalGridItemContentMarshaller(
    verticalGridItemTopicTileMarshaller)

  val verticalGridItemMarshaller = new VerticalGridItemMarshaller(verticalGridItemContentMarshaller)

  val tombstoneDisplayTypeMarshaller = new TombstoneDisplayTypeMarshaller
  val tombstoneItemMarshaller = new TombstoneItemMarshaller(
    displayTypeMarshaller = tombstoneDisplayTypeMarshaller,
    tombstoneInfoMarshaller = tombstoneInfoMarshaller,
    tweetItemMarshaller = tweetItemMarshaller)

  val iconLabelItemMarshaller = new IconLabelItemMarshaller(
    richTextMarshaller,
    horizonIconMarshaller
  )

  val labelDisplayTypeMarshaller = new LabelDisplayTypeMarshaller
  val labelItemMarshaller = new LabelItemMarshaller(
    displayTypeMarshaller = labelDisplayTypeMarshaller,
    urlMarshaller = urlMarshaller
  )

  val tileItemMarshaller = new TileItemMarshaller(
    tileContentMarshaller = tileContentMarshaller,
    urlMarshaller = urlMarshaller,
    imageVariantMarshaller = imageVariantMarshaller
  )

  val callbackMarshaller = new CallbackMarshaller
  val messageActionMarshaller = new MessageActionMarshaller(
    callbackMarshaller,
    clientEventInfoMarshaller
  )
  val messageTextActionMarshaller = new MessageTextActionMarshaller(messageActionMarshaller)
  val messageImageMarshaller = new MessageImageMarshaller(
    imageVariantMarshaller
  )
  val userFacepileDisplayTypeMarshaller = new UserFacepileDisplayTypeMarshaller()
  val messageActionTypeMarshaller = new MessageActionTypeMarshaller()
  val userFacepileMarshaller = new UserFacepileMarshaller(
    messageActionTypeMarshaller,
    messageTextActionMarshaller,
    userFacepileDisplayTypeMarshaller
  )
  val inlinePromptMessageContentMarshaller = new InlinePromptMessageContentMarshaller(
    messageTextActionMarshaller = messageTextActionMarshaller,
    richTextMarshaller = richTextMarshaller,
    socialContextMarshaller = socialContextMarshaller,
    userFacepileMarshaller = userFacepileMarshaller
  )
  val headerImagePromptMessageContentMarshaller = new HeaderImagePromptMessageContentMarshaller(
    messageImageMarshaller = messageImageMarshaller,
    messageTextActionMarshaller = messageTextActionMarshaller,
    messageActionMarshaller = messageActionMarshaller,
    richTextMarshaller = richTextMarshaller
  )
  val compactPromptMessageContentMarshaller = new CompactPromptMessageContentMarshaller(
    messageTextActionMarshaller = messageTextActionMarshaller,
    messageActionMarshaller = messageActionMarshaller,
    richTextMarshaller = richTextMarshaller
  )
  val messageContentMarshaller = new MessageContentMarshaller(
    inlinePromptMessageContentMarshaller = inlinePromptMessageContentMarshaller,
    headerImagePromptMessageContentMarshaller = headerImagePromptMessageContentMarshaller,
    compactPromptMessageContentMarshaller = compactPromptMessageContentMarshaller
  )
  val messagePromptItemMarshaller = new MessagePromptItemMarshaller(
    messageContentMarshaller = messageContentMarshaller,
    callbackMarshaller = callbackMarshaller
  )

  val tweetComposerDisplayTypeMarshaller = new TweetComposerDisplayTypeMarshaller
  val tweetComposerItemMarshaller = new TweetComposerItemMarshaller(
    tweetComposerDisplayTypeMarshaller = tweetComposerDisplayTypeMarshaller,
    urlMarshaller = urlMarshaller
  )

  val cursorTypeMarshaller = new CursorTypeMarshaller
  val cursorDisplayTreatmentMarshaller = new CursorDisplayTreatmentMarshaller
  val cursorItemMarshaller = new CursorItemMarshaller(
    cursorTypeMarshaller = cursorTypeMarshaller,
    cursorDisplayTreatmentMarshaller = cursorDisplayTreatmentMarshaller)
  val articleDisplayTypeMarshaller = new ArticleDisplayTypeMarshaller
  val articleSeedTypeMarshaller = new ArticleSeedTypeMarshaller
  val articleItemMarshaller =
    new ArticleItemMarshaller(
      articleDisplayTypeMarshaller,
      socialContextMarshaller,
      articleSeedTypeMarshaller)
  val audioSpaceItemMarshaller = new AudioSpaceItemMarshaller
  val cardDisplayTypeMarshaller = new CardDisplayTypeMarshaller
  val cardItemMarshaller = new CardItemMarshaller(
    cardDisplayTypeMarshaller = cardDisplayTypeMarshaller,
    urlMarshaller = urlMarshaller
  )

  val twitterListDisplayTypeMarshaller = new TwitterListDisplayTypeMarshaller
  val twitterListItemMarshaller = new TwitterListItemMarshaller(
    twitterListDisplayTypeMarshaller = twitterListDisplayTypeMarshaller)

  val threadHeaderItemMarshaller = new ThreadHeaderItemMarshaller(
    threadHeaderContentMarshaller = new ThreadHeaderContentMarshaller
  )

  val relevancePromptFollowUpTextInputMarshaller = new RelevancePromptFollowUpTextInputMarshaller(
    callbackMarshaller = callbackMarshaller
  )
  val relevancePromptFollowUpFeedbackTypeMarshaller =
    new RelevancePromptFollowUpFeedbackTypeMarshaller(
      relevancePromptFollowUpTextInputMarshaller = relevancePromptFollowUpTextInputMarshaller
    )
  val relevancePromptDisplayTypeMarshaller = new RelevancePromptDisplayTypeMarshaller
  val relevancePromptContentMarshaller = new RelevancePromptContentMarshaller(
    callbackMarshaller = callbackMarshaller,
    relevancePromptDisplayTypeMarshaller = relevancePromptDisplayTypeMarshaller,
    relevancePromptFollowUpFeedbackTypeMarshaller = relevancePromptFollowUpFeedbackTypeMarshaller
  )
  val promptContentMarshaller = new PromptContentMarshaller(
    relevancePromptContentMarshaller = relevancePromptContentMarshaller
  )
  val promptItemMarshaller = new PromptItemMarshaller(
    promptContentMarshaller = promptContentMarshaller,
    clientEventInfoMarshaller = clientEventInfoMarshaller,
    callbackMarshaller = callbackMarshaller
  )

  val textResultMarshaller = new TextResultMarshaller(highlightedSectionMarshaller)
  val spellingActionTypeMarshaller = new SpellingActionTypeMarshaller()
  val spellingItemMarshaller = new SpellingItemMarshaller(
    textResultMarshaller = textResultMarshaller,
    spellingActionTypeMarshaller = spellingActionTypeMarshaller)

  val momentAnnotationItemMarshaller = new MomentAnnotationItemMarshaller(richTextMarshaller)

  val genericSummaryDisplayTypeMarshaller = new GenericSummaryDisplayTypeMarshaller
  val genericSummaryActionMarshaller = new GenericSummaryActionMarshaller(
    urlMarshaller = urlMarshaller,
    clientEventInfoMarshaller = clientEventInfoMarshaller)
  val genericSummaryContextMarshaller = new GenericSummaryContextMarshaller(
    richTextMarshaller = richTextMarshaller,
    horizonIconMarshaller = horizonIconMarshaller
  )
  val genericSummaryItemMarshaller = new GenericSummaryItemMarshaller(
    genericSummaryDisplayTypeMarshaller = genericSummaryDisplayTypeMarshaller,
    genericSummaryContextMarshaller = genericSummaryContextMarshaller,
    genericSummaryActionMarshaller = genericSummaryActionMarshaller,
    mediaMarshaller = mediaMarshaller,
    promotedMetadataMarshaller = promotedMetadataMarshaller,
    richTextMarshaller = richTextMarshaller
  )

  val commerceProductItemMarshaller = new CommerceProductItemMarshaller
  val commerceProductGroupItemMarshaller = new CommerceProductGroupItemMarshaller

  val timelineItemMarshaller = new TimelineItemMarshaller(
    timelineItemContentMarshaller = new TimelineItemContentMarshaller(
      articleItemMarshaller = articleItemMarshaller,
      audioSpaceItemMarshaller = audioSpaceItemMarshaller,
      cardItemMarshaller = cardItemMarshaller,
      cursorItemMarshaller = cursorItemMarshaller,
      eventSummaryItemMarshaller = eventSummaryItemMarshaller,
      iconLabelItemMarshaller = iconLabelItemMarshaller,
      labelItemMarshaller = labelItemMarshaller,
      messagePromptItemMarshaller = messagePromptItemMarshaller,
      tileItemMarshaller = tileItemMarshaller,
      tombstoneItemMarshaller = tombstoneItemMarshaller,
      topicFollowPromptItemMarshaller = topicFollowPromptItemMarshaller,
      topicItemMarshaller = topicItemMarshaller,
      tweetComposerItemMarshaller = tweetComposerItemMarshaller,
      tweetItemMarshaller = tweetItemMarshaller,
      twitterListItemMarshaller = twitterListItemMarshaller,
      userItemMarshaller = userItemMarshaller,
      verticalGridItemMarshaller = verticalGridItemMarshaller,
      threadHeaderItemMarshaller = threadHeaderItemMarshaller,
      promptItemMarshaller = promptItemMarshaller,
      spellingItemMarshaller = spellingItemMarshaller,
      momentAnnotationItemMarshaller = momentAnnotationItemMarshaller,
      genericSummaryItemMarshaller = genericSummaryItemMarshaller,
      commerceProductItemMarshaller = commerceProductItemMarshaller,
      commerceProductGroupItemMarshaller = commerceProductGroupItemMarshaller,
      trendItemMarshaller = trendItemMarshaller
    ),
    clientEventInfoMarshaller = clientEventInfoMarshaller,
    feedbackInfoMarshaller = feedbackInfoMarshaller
  )

  val moduleDisplayTypeMarshaller = new ModuleDisplayTypeMarshaller
  val moduleItemTreeDisplayMarshaller =
    new ModuleItemTreeDisplayMarshaller(moduleDisplayTypeMarshaller)

  val moduleItemMarshaller = new ModuleItemMarshaller(
    timelineItemMarshaller = timelineItemMarshaller,
    moduleItemTreeDisplayMarshaller = moduleItemTreeDisplayMarshaller)

  val moduleHeaderDisplayTypeMarshaller = new ModuleHeaderDisplayTypeMarshaller
  val moduleHeaderMarshaller = new ModuleHeaderMarshaller(
    horizonIconMarshaller = horizonIconMarshaller,
    imageVariantMarshaller = imageVariantMarshaller,
    socialContextMarshaller = socialContextMarshaller,
    moduleHeaderDisplayTypeMarshaller = moduleHeaderDisplayTypeMarshaller
  )
  val moduleFooterMarshaller = new ModuleFooterMarshaller(urlMarshaller = urlMarshaller)
  val adsMetadataMarshaller = new AdsMetadataMarshaller
  val moduleConversationMetadataMarshaller = new ModuleConversationMetadataMarshaller(
    socialContextMarshaller = socialContextMarshaller)
  val gridCarouselMetadataMarshaller = new GridCarouselMetadataMarshaller
  val moduleMetadataMarshaller = new ModuleMetadataMarshaller(
    adsMetadataMarshaller = adsMetadataMarshaller,
    moduleConversationMetadataMarshaller = moduleConversationMetadataMarshaller,
    gridCarouselMetadataMarshaller = gridCarouselMetadataMarshaller
  )
  val moduleShowMoreBehaviorRevealByCountMarshaller =
    new ModuleShowMoreBehaviorRevealByCountMarshaller
  val moduleShowMoreBehaviorMarshaller = new ModuleShowMoreBehaviorMarshaller(
    moduleShowMoreBehaviorRevealByCountMarshaller = moduleShowMoreBehaviorRevealByCountMarshaller
  )
  val timelineModuleMarshaller = new TimelineModuleMarshaller(
    moduleItemMarshaller = moduleItemMarshaller,
    moduleDisplayTypeMarshaller = moduleDisplayTypeMarshaller,
    moduleHeaderMarshaller = moduleHeaderMarshaller,
    moduleFooterMarshaller = moduleFooterMarshaller,
    clientEventInfoMarshaller = clientEventInfoMarshaller,
    feedbackInfoMarshaller = feedbackInfoMarshaller,
    moduleMetadataMarshaller = moduleMetadataMarshaller,
    moduleShowMoreBehaviorMarshaller = moduleShowMoreBehaviorMarshaller
  )

  val halfCoverDisplayTypeMarshaller = new HalfCoverDisplayTypeMarshaller()
  val fullCoverDisplayTypeMarshaller = new FullCoverDisplayTypeMarshaller()
  val coverCtaBehaviorMarshaller = new CoverCtaBehaviorMarshaller(richTextMarshaller, urlMarshaller)
  val buttonStyleMarshaller = new ButtonStyleMarshaller()
  val coverCtaMarshaller = new CoverCtaMarshaller(
    coverCtaBehaviorMarshaller,
    callbackMarshaller,
    clientEventInfoMarshaller,
    horizonIconMarshaller,
    buttonStyleMarshaller)
  val coverImageMarshaller =
    new CoverImageMarshaller(
      imageVariantMarshaller,
      imageDisplayTypeMarshaller,
      imageAnimationTypeMarshaller)
  val dismissInfoMarshaller = new DismissInfoMarshaller(callbackMarshaller)

  val halfCoverContentMarshaller = new HalfCoverContentMarshaller(
    halfCoverDisplayTypeMarshaller,
    coverCtaMarshaller,
    richTextMarshaller,
    coverImageMarshaller,
    dismissInfoMarshaller,
    callbackMarshaller)
  val fullCoverContentMarshaller = new FullCoverContentMarshaller(
    fullCoverDisplayTypeMarshaller,
    coverCtaMarshaller,
    richTextMarshaller,
    imageVariantMarshaller,
    dismissInfoMarshaller,
    imageDisplayTypeMarshaller,
    callbackMarshaller)
  val coverContentMarshaller =
    new CoverContentMarshaller(fullCoverContentMarshaller, halfCoverContentMarshaller)
  val coverMarshaller = new CoverMarshaller(coverContentMarshaller, clientEventInfoMarshaller)

  val cursorOperationMarshaller = new CursorOperationMarshaller(
    cursorTypeMarshaller = cursorTypeMarshaller,
    cursorDisplayTreatmentMarshaller = cursorDisplayTreatmentMarshaller)
  val timelineOperationMarshaller = new TimelineOperationMarshaller(
    cursorOperationMarshaller = cursorOperationMarshaller)

  val timelineEntryMarshaller = new TimelineEntryMarshaller(
    timelineEntryContentMarshaller = new TimelineEntryContentMarshaller(
      timelineItemMarshaller = timelineItemMarshaller,
      timelineModuleMarshaller = timelineModuleMarshaller,
      timelineOperationMarshaller = timelineOperationMarshaller))

  val addEntriesInstructionMarshaller = new AddEntriesInstructionMarshaller(
    timelineEntryMarshaller = timelineEntryMarshaller)

  val markEntriesUnreadInstructionMarshaller = new MarkEntriesUnreadInstructionMarshaller()

  val addToModuleInstructionMarshaller = new AddToModuleInstructionMarshaller(
    moduleItemMarshaller = moduleItemMarshaller)

  val replaceEntryInstructionMarshaller = new ReplaceEntryInstructionMarshaller(
    timelineEntryMarshaller = timelineEntryMarshaller
  )

  val pinEntryInstructionMarshaller = new PinEntryInstructionMarshaller(
    timelineEntryMarshaller = timelineEntryMarshaller
  )

  val showAlertTypeMarshaller = new ShowAlertTypeMarshaller()
  val showAlertIconMarshaller = new ShowAlertIconMarshaller()
  val showAlertIconDisplayInfoMarshaller = new ShowAlertIconDisplayInfoMarshaller(
    showAlertIconMarshaller = showAlertIconMarshaller,
    rosettaColorMarshaller = rosettaColorMarshaller
  )
  val showAlertColorConfigurationMarshaller = new ShowAlertColorConfigurationMarshaller(
    rosettaColorMarshaller = rosettaColorMarshaller
  )
  val showAlertDisplayLocationMarshaller = new ShowAlertDisplayLocationMarshaller()
  val showAlertNavigationMetadataMarshaller = new ShowAlertNavigationMetadataMarshaller()
  val showAlertInstructionMarshaller = new ShowAlertInstructionMarshaller(
    showAlertTypeMarshaller = new ShowAlertTypeMarshaller(),
    clientEventInfoMarshaller = clientEventInfoMarshaller,
    richTextMarshaller = richTextMarshaller,
    showAlertIconDisplayInfoMarshaller = showAlertIconDisplayInfoMarshaller,
    showAlertColorConfigurationMarshaller = showAlertColorConfigurationMarshaller,
    showAlertDisplayLocationMarshaller = showAlertDisplayLocationMarshaller,
    showAlertNavigationMetadataMarshaller = showAlertNavigationMetadataMarshaller
  )

  val timelineInstructionMarshaller = new TimelineInstructionMarshaller(
    addEntriesInstructionMarshaller = addEntriesInstructionMarshaller,
    addToModuleInstructionMarshaller = addToModuleInstructionMarshaller,
    markEntriesUnreadInstructionMarshaller = markEntriesUnreadInstructionMarshaller,
    pinEntryInstructionMarshaller = pinEntryInstructionMarshaller,
    replaceEntryInstructionMarshaller = replaceEntryInstructionMarshaller,
    showAlertInstructionMarshaller = showAlertInstructionMarshaller,
    terminateTimelineInstructionMarshaller = new TerminateTimelineInstructionMarshaller,
    coverMarshaller = coverMarshaller,
  )

  val timelineScribeConfigMarshaller = new TimelineScribeConfigMarshaller

  val readerModeConfigMarshaller = new ReaderModeConfigMarshaller(urlMarshaller)

  val timelineMetadataMarshaller = new TimelineMetadataMarshaller(
    timelineScribeConfigMarshaller = timelineScribeConfigMarshaller,
    readerModeConfigMarshaller = readerModeConfigMarshaller
  )

  val marshaller: UrtTransportMarshaller =
    new UrtTransportMarshaller(
      timelineInstructionMarshaller = timelineInstructionMarshaller,
      feedbackActionMarshaller = feedbackActionMarshaller,
      childFeedbackActionMarshaller = childFeedbackActionMarshaller,
      timelineMetadataMarshaller = timelineMetadataMarshaller
    )
}
