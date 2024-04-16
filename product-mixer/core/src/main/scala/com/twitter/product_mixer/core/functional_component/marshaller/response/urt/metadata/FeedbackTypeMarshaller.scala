package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata._
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class FeedbackTypeMarshaller @ nject() () {

  def apply(feedbackType: FeedbackType): urt.FeedbackType = feedbackType match {
    case D sm ss => urt.FeedbackType.D sm ss
    case SeeFe r => urt.FeedbackType.SeeFe r
    case DontL ke => urt.FeedbackType.DontL ke
    case NotRelevant => urt.FeedbackType.NotRelevant
    case SeeMore => urt.FeedbackType.SeeMore
    case NotCred ble => urt.FeedbackType.NotCred ble
    case G veFeedback => urt.FeedbackType.G veFeedback
    case NotRecent => urt.FeedbackType.NotRecent
    case UnfollowEnt y => urt.FeedbackType.UnfollowEnt y
    case Relevant => urt.FeedbackType.Relevant
    case Moderate => urt.FeedbackType.Moderate
    case R chBehav or => urt.FeedbackType.R chBehav or
    case NotAboutTop c => urt.FeedbackType.NotAboutTop c
    case Gener c => urt.FeedbackType.Gener c
  }
}
