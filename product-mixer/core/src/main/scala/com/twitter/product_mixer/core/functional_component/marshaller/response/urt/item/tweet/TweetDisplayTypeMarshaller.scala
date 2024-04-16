package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.t et

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et._
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T etD splayTypeMarshaller @ nject() () {

  def apply(t etD splayType: T etD splayType): urt.T etD splayType = t etD splayType match {
    case T et => urt.T etD splayType.T et
    case T etFollowOnly => urt.T etD splayType.T etFollowOnly
    case  d a => urt.T etD splayType. d a
    case Mo ntT  l neT et => urt.T etD splayType.Mo ntT  l neT et
    case Emphas zedPromotedT et => urt.T etD splayType.Emphas zedPromotedT et
    case QuotedT et => urt.T etD splayType.QuotedT et
    case SelfThread => urt.T etD splayType.SelfThread
    case CompactPromotedT et => urt.T etD splayType.CompactPromotedT et
    case T etW houtCard => urt.T etD splayType.T etW houtCard
    case ReaderModeRoot => urt.T etD splayType.ReaderModeRoot
    case ReaderMode => urt.T etD splayType.ReaderMode
    case CondensedT et => urt.T etD splayType.CondensedT et
  }
}
