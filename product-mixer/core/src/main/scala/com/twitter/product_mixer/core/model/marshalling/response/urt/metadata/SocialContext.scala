package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

sealed trait SocialContext

trait HasSocialContext {
  def socialContext: Option[SocialContext]
}

sealed trait GeneralContextType
case object LikeGeneralContextType extends GeneralContextType
case object FollowGeneralContextType extends GeneralContextType
case object MomentGeneralContextType extends GeneralContextType
case object ReplyGeneralContextType extends GeneralContextType
case object ConversationGeneralContextType extends GeneralContextType
case object PinGeneralContextType extends GeneralContextType
case object TextOnlyGeneralContextType extends GeneralContextType
case object FacePileGeneralContextType extends GeneralContextType
case object MegaPhoneGeneralContextType extends GeneralContextType
case object BirdGeneralContextType extends GeneralContextType
case object FeedbackGeneralContextType extends GeneralContextType
case object TopicGeneralContextType extends GeneralContextType
case object ListGeneralContextType extends GeneralContextType
case object RetweetGeneralContextType extends GeneralContextType
case object LocationGeneralContextType extends GeneralContextType
case object CommunityGeneralContextType extends GeneralContextType
case object NewUserGeneralContextType extends GeneralContextType
case object SmartblockExpirationGeneralContextType extends GeneralContextType
case object TrendingGeneralContextType extends GeneralContextType
case object SparkleGeneralContextType extends GeneralContextType
case object SpacesGeneralContextType extends GeneralContextType
case object ReplyPinGeneralContextType extends GeneralContextType

case class GeneralContext(
  contextType: GeneralContextType,
  text: String,
  url: Option[String],
  contextImageUrls: Option[List[String]],
  landingUrl: Option[Url])
    extends SocialContext

sealed trait TopicContextFunctionalityType
case object BasicTopicContextFunctionalityType extends TopicContextFunctionalityType
case object RecommendationTopicContextFunctionalityType extends TopicContextFunctionalityType
case object RecWithEducationTopicContextFunctionalityType extends TopicContextFunctionalityType

case class TopicContext(
  topicId: String,
  functionalityType: Option[TopicContextFunctionalityType])
    extends SocialContext
