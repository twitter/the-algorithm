package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata._
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeneralContextTypeMarshaller @Inject() () {

  def apply(generalContextType: GeneralContextType): urt.ContextType = generalContextType match {
    case LikeGeneralContextType => urt.ContextType.Like
    case FollowGeneralContextType => urt.ContextType.Follow
    case MomentGeneralContextType => urt.ContextType.Moment
    case ReplyGeneralContextType => urt.ContextType.Reply
    case ConversationGeneralContextType => urt.ContextType.Conversation
    case PinGeneralContextType => urt.ContextType.Pin
    case TextOnlyGeneralContextType => urt.ContextType.TextOnly
    case FacePileGeneralContextType => urt.ContextType.Facepile
    case MegaPhoneGeneralContextType => urt.ContextType.Megaphone
    case BirdGeneralContextType => urt.ContextType.Bird
    case FeedbackGeneralContextType => urt.ContextType.Feedback
    case TopicGeneralContextType => urt.ContextType.Topic
    case ListGeneralContextType => urt.ContextType.List
    case RetweetGeneralContextType => urt.ContextType.Retweet
    case LocationGeneralContextType => urt.ContextType.Location
    case CommunityGeneralContextType => urt.ContextType.Community
    case NewUserGeneralContextType => urt.ContextType.NewUser
    case SmartblockExpirationGeneralContextType => urt.ContextType.SmartBlockExpiration
    case TrendingGeneralContextType => urt.ContextType.Trending
    case SparkleGeneralContextType => urt.ContextType.Sparkle
    case SpacesGeneralContextType => urt.ContextType.Spaces
    case ReplyPinGeneralContextType => urt.ContextType.ReplyPin
  }
}
