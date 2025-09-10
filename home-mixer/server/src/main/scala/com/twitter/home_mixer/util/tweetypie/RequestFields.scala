package com.twitter.home_mixer.util.tweetypie

import com.twitter.tweetypie.{thriftscala => tp}

object RequestFields {

  val CoreTweetFields: Set[tp.TweetInclude] = Set[tp.TweetInclude](
    tp.TweetInclude.TweetFieldId(tp.Tweet.IdField.id),
    tp.TweetInclude.TweetFieldId(tp.Tweet.CoreDataField.id)
  )
  val MediaFields: Set[tp.TweetInclude] = Set[tp.TweetInclude](
    tp.TweetInclude.TweetFieldId(tp.Tweet.MediaField.id),
  )
  val SelfThreadFields: Set[tp.TweetInclude] = Set[tp.TweetInclude](
    tp.TweetInclude.TweetFieldId(tp.Tweet.SelfThreadMetadataField.id)
  )
  val MentionsTweetFields: Set[tp.TweetInclude] = Set[tp.TweetInclude](
    tp.TweetInclude.TweetFieldId(tp.Tweet.MentionsField.id)
  )
  val SemanticAnnotationTweetFields: Set[tp.TweetInclude] = Set[tp.TweetInclude](
    tp.TweetInclude.TweetFieldId(tp.Tweet.EscherbirdEntityAnnotationsField.id)
  )
  val NsfwLabelFields: Set[tp.TweetInclude] = Set[tp.TweetInclude](
    // Tweet fields containing NSFW related attributes.
    tp.TweetInclude.TweetFieldId(tp.Tweet.NsfwHighRecallLabelField.id),
    tp.TweetInclude.TweetFieldId(tp.Tweet.NsfwHighPrecisionLabelField.id),
    tp.TweetInclude.TweetFieldId(tp.Tweet.NsfaHighRecallLabelField.id)
  )
  val SafetyLabelFields: Set[tp.TweetInclude] = Set[tp.TweetInclude](
    // Tweet fields containing RTF labels for abuse and spam.
    tp.TweetInclude.TweetFieldId(tp.Tweet.SpamLabelField.id),
    tp.TweetInclude.TweetFieldId(tp.Tweet.AbusiveLabelField.id)
  )
  val ConversationControlField: Set[tp.TweetInclude] =
    Set[tp.TweetInclude](tp.TweetInclude.TweetFieldId(tp.Tweet.ConversationControlField.id))

  val TweetTPHydrationFields: Set[tp.TweetInclude] = CoreTweetFields ++
    NsfwLabelFields ++
    SafetyLabelFields ++
    SemanticAnnotationTweetFields ++
    Set(
      tp.TweetInclude.TweetFieldId(tp.Tweet.TakedownCountryCodesField.id),
      // QTs imply a TweetyPie -> SGS request dependency
      tp.TweetInclude.TweetFieldId(tp.Tweet.QuotedTweetField.id),
      tp.TweetInclude.TweetFieldId(tp.Tweet.CommunitiesField.id),
      // Field required for determining if a Tweet was created via News Camera.
      tp.TweetInclude.TweetFieldId(tp.Tweet.ComposerSourceField.id),
      tp.TweetInclude.TweetFieldId(tp.Tweet.LanguageField.id)
    )

  val TweetStaticEntitiesFields: Set[tp.TweetInclude] =
    MentionsTweetFields ++ CoreTweetFields ++ SemanticAnnotationTweetFields ++ MediaFields

  val ContentFields: Set[tp.TweetInclude] = CoreTweetFields ++ MediaFields ++ SelfThreadFields ++
    ConversationControlField ++ SemanticAnnotationTweetFields ++
    Set[tp.TweetInclude](
      tp.TweetInclude.MediaEntityFieldId(tp.MediaEntity.AdditionalMetadataField.id))
}
