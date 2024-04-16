package com.tw ter.follow_recom ndat ons.assembler.models

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}

tra  WTFPresentat on {
  def toThr ft: t.WTFPresentat on
}

case class UserL st(
  userB oEnabled: Boolean,
  userB oTruncated: Boolean,
  userB oMaxL nes: Opt on[Long],
  feedbackAct on: Opt on[FeedbackAct on])
    extends WTFPresentat on {
  def toThr ft: t.WTFPresentat on = {
    t.WTFPresentat on.UserB oL st(
      t.UserL st(userB oEnabled, userB oTruncated, userB oMaxL nes, feedbackAct on.map(_.toThr ft)))
  }
}

object UserL st {
  def fromUserL stOpt ons(
    userL stOpt ons: UserL stOpt ons
  ): UserL st = {
    UserL st(
      userL stOpt ons.userB oEnabled,
      userL stOpt ons.userB oTruncated,
      userL stOpt ons.userB oMaxL nes,
      None)
  }
}

case class Carousel(
  feedbackAct on: Opt on[FeedbackAct on])
    extends WTFPresentat on {
  def toThr ft: t.WTFPresentat on = {
    t.WTFPresentat on.Carousel(t.Carousel(feedbackAct on.map(_.toThr ft)))
  }
}

object Carousel {
  def fromCarouselOpt ons(
    carouselOpt ons: CarouselOpt ons
  ): Carousel = {
    Carousel(None)
  }
}
