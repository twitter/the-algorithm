package com.tw ter.ho _m xer.module

 mport com.tw ter.f natra.thr ft.except ons.Except onMapper
 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.ProductD sabled
 mport com.tw ter.scrooge.Thr ftExcept on
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton

@S ngleton
class P pel neFa lureExcept onMapper
    extends Except onMapper[P pel neFa lure, Thr ftExcept on]
    w h Logg ng {

  def handleExcept on(throwable: P pel neFa lure): Future[Thr ftExcept on] = {
    throwable match {
      // Sl ceServ ce (unl ke UrtServ ce) throws an except on w n t  requested product  s d sabled
      case P pel neFa lure(ProductD sabled, reason, _, _) =>
        Future.except on(
          t.Val dat onExcept onL st(errors =
            Seq(t.Val dat onExcept on(t.Val dat onErrorCode.ProductD sabled, reason))))
      case _ =>
        error("Unhandled P pel neFa lure", throwable)
        Future.except on(throwable)
    }
  }
}
