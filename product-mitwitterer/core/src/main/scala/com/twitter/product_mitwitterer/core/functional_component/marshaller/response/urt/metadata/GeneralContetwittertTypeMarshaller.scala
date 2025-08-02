package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata._
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class GeneralContetwittertTypeMarshaller @Inject() () {

  def apply(generalContetwittertType: GeneralContetwittertType): urt.ContetwittertType = generalContetwittertType match {
    case LikeGeneralContetwittertType => urt.ContetwittertType.Like
    case FollowGeneralContetwittertType => urt.ContetwittertType.Follow
    case MomentGeneralContetwittertType => urt.ContetwittertType.Moment
    case ReplyGeneralContetwittertType => urt.ContetwittertType.Reply
    case ConversationGeneralContetwittertType => urt.ContetwittertType.Conversation
    case PinGeneralContetwittertType => urt.ContetwittertType.Pin
    case TetwittertOnlyGeneralContetwittertType => urt.ContetwittertType.TetwittertOnly
    case FacePileGeneralContetwittertType => urt.ContetwittertType.Facepile
    case MegaPhoneGeneralContetwittertType => urt.ContetwittertType.Megaphone
    case BirdGeneralContetwittertType => urt.ContetwittertType.Bird
    case FeedbackGeneralContetwittertType => urt.ContetwittertType.Feedback
    case TopicGeneralContetwittertType => urt.ContetwittertType.Topic
    case ListGeneralContetwittertType => urt.ContetwittertType.List
    case RetweetGeneralContetwittertType => urt.ContetwittertType.Retweet
    case LocationGeneralContetwittertType => urt.ContetwittertType.Location
    case CommunityGeneralContetwittertType => urt.ContetwittertType.Community
    case NewUserGeneralContetwittertType => urt.ContetwittertType.NewUser
    case SmartblockEtwitterpirationGeneralContetwittertType => urt.ContetwittertType.SmartBlockEtwitterpiration
    case TrendingGeneralContetwittertType => urt.ContetwittertType.Trending
    case SparkleGeneralContetwittertType => urt.ContetwittertType.Sparkle
    case SpacesGeneralContetwittertType => urt.ContetwittertType.Spaces
    case ReplyPinGeneralContetwittertType => urt.ContetwittertType.ReplyPin
  }
}
