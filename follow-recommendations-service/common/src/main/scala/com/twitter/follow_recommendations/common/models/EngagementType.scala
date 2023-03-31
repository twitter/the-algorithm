package com.twitter.follow_recommendations.common.models

import com.twitter.follow_recommendations.thriftscala.{EngagementType => TEngagementType}
import com.twitter.follow_recommendations.logging.thriftscala.{
  EngagementType => OfflineEngagementType
}
sealed trait EngagementType {
  def toThrift: TEngagementType
  def toOfflineThrift: OfflineEngagementType
}

object EngagementType {
  object Click extends EngagementType {
    override val toThrift: TEngagementType = TEngagementType.Click

    override val toOfflineThrift: OfflineEngagementType = OfflineEngagementType.Click
  }
  object Like extends EngagementType {
    override val toThrift: TEngagementType = TEngagementType.Like

    override val toOfflineThrift: OfflineEngagementType = OfflineEngagementType.Like
  }
  object Mention extends EngagementType {
    override val toThrift: TEngagementType = TEngagementType.Mention

    override val toOfflineThrift: OfflineEngagementType = OfflineEngagementType.Mention
  }
  object Retweet extends EngagementType {
    override val toThrift: TEngagementType = TEngagementType.Retweet

    override val toOfflineThrift: OfflineEngagementType = OfflineEngagementType.Retweet
  }
  object ProfileView extends EngagementType {
    override val toThrift: TEngagementType = TEngagementType.ProfileView

    override val toOfflineThrift: OfflineEngagementType = OfflineEngagementType.ProfileView
  }

  def fromThrift(engagementType: TEngagementType): EngagementType = engagementType match {
    case TEngagementType.Click => Click
    case TEngagementType.Like => Like
    case TEngagementType.Mention => Mention
    case TEngagementType.Retweet => Retweet
    case TEngagementType.ProfileView => ProfileView
    case TEngagementType.EnumUnknownEngagementType(i) =>
      throw new UnknownEngagementTypeException(
        s"Unknown engagement type thrift enum with value: ${i}")
  }

  def fromOfflineThrift(engagementType: OfflineEngagementType): EngagementType =
    engagementType match {
      case OfflineEngagementType.Click => Click
      case OfflineEngagementType.Like => Like
      case OfflineEngagementType.Mention => Mention
      case OfflineEngagementType.Retweet => Retweet
      case OfflineEngagementType.ProfileView => ProfileView
      case OfflineEngagementType.EnumUnknownEngagementType(i) =>
        throw new UnknownEngagementTypeException(
          s"Unknown engagement type offline thrift enum with value: ${i}")
    }
}
class UnknownEngagementTypeException(message: String) extends Exception(message)
