package com.twitter.follow_recommendations.assembler.models

import com.twitter.follow_recommendations.{thriftscala => t}

trait WTFPresentation {
  def toThrift: t.WTFPresentation
}

case class UserList(
  userBioEnabled: Boolean,
  userBioTruncated: Boolean,
  userBioMatwitterLines: Option[Long],
  feedbackAction: Option[FeedbackAction])
    etwittertends WTFPresentation {
  def toThrift: t.WTFPresentation = {
    t.WTFPresentation.UserBioList(
      t.UserList(userBioEnabled, userBioTruncated, userBioMatwitterLines, feedbackAction.map(_.toThrift)))
  }
}

object UserList {
  def fromUserListOptions(
    userListOptions: UserListOptions
  ): UserList = {
    UserList(
      userListOptions.userBioEnabled,
      userListOptions.userBioTruncated,
      userListOptions.userBioMatwitterLines,
      None)
  }
}

case class Carousel(
  feedbackAction: Option[FeedbackAction])
    etwittertends WTFPresentation {
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
