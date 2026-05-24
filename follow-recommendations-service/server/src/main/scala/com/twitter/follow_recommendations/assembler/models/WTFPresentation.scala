package com.twitter.follow_recommendations.assembler.models

import com.twitter.follow_recommendations.{thriftscala => t}

trait WTFPresentation {
  def toThrift: t.WTFPresentation
}

case class UserList(
  userBioEnabled: Boolean,
  userBioTruncated: Boolean,
  userBioMaxLines: Option[Long],
  feedbackAction: Option[FeedbackAction])
    extends WTFPresentation {
  def toThrift: t.WTFPresentation = {
    t.WTFPresentation.UserBioList(
      t.UserList(userBioEnabled, userBioTruncated, userBioMaxLines, feedbackAction.map(_.toThrift)))
  }
}

object UserList {
  def fromUserListOptions(
    userListOptions: UserListOptions
  ): UserList = {
    UserList(
      userListOptions.userBioEnabled,
      userListOptions.userBioTruncated,
      userListOptions.userBioMaxLines,
      None)
  }
}

case class Carousel(
  feedbackAction: Option[FeedbackAction])
    extends WTFPresentation {
  def toThrift: t.WTFPresentation = {
    t.WTFPresentation.Carousel(t.Carousel(feedbackAction.map(_.toThrift)))
  }
}

object Carousel {
  def fromCarouselOptions(
    carouselOptions: CarouselOptions
  ): Carousel = {
    Carousel(None)
  }
}
