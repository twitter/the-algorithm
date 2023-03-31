package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.icon

import com.twitter.product_mixer.core.model.marshalling.response.urt.icon._
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HorizonIconMarshaller @Inject() () {

  def apply(icon: HorizonIcon): urt.HorizonIcon = icon match {
    case Bookmark => urt.HorizonIcon.Bookmark
    case Moment => urt.HorizonIcon.Moment
    case Debug => urt.HorizonIcon.Debug
    case Error => urt.HorizonIcon.Error
    case Follow => urt.HorizonIcon.Follow
    case Unfollow => urt.HorizonIcon.Unfollow
    case Smile => urt.HorizonIcon.Smile
    case Frown => urt.HorizonIcon.Frown
    case Help => urt.HorizonIcon.Help
    case Link => urt.HorizonIcon.Link
    case Message => urt.HorizonIcon.Message
    case No => urt.HorizonIcon.No
    case Outgoing => urt.HorizonIcon.Outgoing
    case Pin => urt.HorizonIcon.Pin
    case Retweet => urt.HorizonIcon.Retweet
    case Speaker => urt.HorizonIcon.Speaker
    case Trashcan => urt.HorizonIcon.Trashcan
    case Feedback => urt.HorizonIcon.Feedback
    case FeedbackClose => urt.HorizonIcon.FeedbackClose
    case EyeOff => urt.HorizonIcon.EyeOff
    case Moderation => urt.HorizonIcon.Moderation
    case Topic => urt.HorizonIcon.Topic
    case TopicClose => urt.HorizonIcon.TopicClose
    case Flag => urt.HorizonIcon.Flag
    case TopicFilled => urt.HorizonIcon.TopicFilled
    case NotificationsFollow => urt.HorizonIcon.NotificationsFollow
    case Person => urt.HorizonIcon.Person
    case BalloonStroke => urt.HorizonIcon.BalloonStroke
    case Calendar => urt.HorizonIcon.Calendar
    case LocationStroke => urt.HorizonIcon.LocationStroke
    case PersonStroke => urt.HorizonIcon.PersonStroke
    case Safety => urt.HorizonIcon.Safety
    case Logo => urt.HorizonIcon.Logo
    case SparkleOn => urt.HorizonIcon.SparkleOn
    case StarRising => urt.HorizonIcon.StarRising
    case CameraVideo => urt.HorizonIcon.CameraVideo
    case ShoppingClock => urt.HorizonIcon.ShoppingClock
    case ArrowRight => urt.HorizonIcon.ArrowRight
    case SpeakerOff => urt.HorizonIcon.SpeakerOff
  }
}
