package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. con

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con._
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Hor zon conMarshaller @ nject() () {

  def apply( con: Hor zon con): urt.Hor zon con =  con match {
    case Bookmark => urt.Hor zon con.Bookmark
    case Mo nt => urt.Hor zon con.Mo nt
    case Debug => urt.Hor zon con.Debug
    case Error => urt.Hor zon con.Error
    case Follow => urt.Hor zon con.Follow
    case Unfollow => urt.Hor zon con.Unfollow
    case Sm le => urt.Hor zon con.Sm le
    case Frown => urt.Hor zon con.Frown
    case  lp => urt.Hor zon con. lp
    case L nk => urt.Hor zon con.L nk
    case  ssage => urt.Hor zon con. ssage
    case No => urt.Hor zon con.No
    case Outgo ng => urt.Hor zon con.Outgo ng
    case P n => urt.Hor zon con.P n
    case Ret et => urt.Hor zon con.Ret et
    case Speaker => urt.Hor zon con.Speaker
    case Trashcan => urt.Hor zon con.Trashcan
    case Feedback => urt.Hor zon con.Feedback
    case FeedbackClose => urt.Hor zon con.FeedbackClose
    case EyeOff => urt.Hor zon con.EyeOff
    case Moderat on => urt.Hor zon con.Moderat on
    case Top c => urt.Hor zon con.Top c
    case Top cClose => urt.Hor zon con.Top cClose
    case Flag => urt.Hor zon con.Flag
    case Top cF lled => urt.Hor zon con.Top cF lled
    case Not f cat onsFollow => urt.Hor zon con.Not f cat onsFollow
    case Person => urt.Hor zon con.Person
    case BalloonStroke => urt.Hor zon con.BalloonStroke
    case Calendar => urt.Hor zon con.Calendar
    case Locat onStroke => urt.Hor zon con.Locat onStroke
    case PersonStroke => urt.Hor zon con.PersonStroke
    case Safety => urt.Hor zon con.Safety
    case Logo => urt.Hor zon con.Logo
    case SparkleOn => urt.Hor zon con.SparkleOn
    case StarR s ng => urt.Hor zon con.StarR s ng
    case Ca raV deo => urt.Hor zon con.Ca raV deo
    case Shopp ngClock => urt.Hor zon con.Shopp ngClock
    case ArrowR ght => urt.Hor zon con.ArrowR ght
    case SpeakerOff => urt.Hor zon con.SpeakerOff
  }
}
