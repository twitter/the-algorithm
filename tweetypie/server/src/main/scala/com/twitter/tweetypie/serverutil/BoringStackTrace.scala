package com.tw ter.t etyp e.serverut l

 mport com.tw ter.f nagle.ChannelExcept on
 mport com.tw ter.f nagle.T  outExcept on
 mport com.tw ter.scrooge.Thr ftExcept on
 mport java.net.SocketExcept on
 mport java.n o.channels.CancelledKeyExcept on
 mport java.n o.channels.ClosedChannelExcept on
 mport java.ut l.concurrent.Cancellat onExcept on
 mport java.ut l.concurrent.{T  outExcept on => JT  outExcept on}
 mport org.apac .thr ft.TAppl cat onExcept on
 mport scala.ut l.control.NoStackTrace

object Bor ngStackTrace {

  /**
   * T se except ons are bor ng because t y are expected to
   * occas onally (or even regularly) happen dur ng normal operat on
   * of t  serv ce. T   ntent on  s to make   eas er to debug
   * problems by mak ng  nterest ng except ons eas er to see.
   *
   * T  best way to mark an except on as bor ng  s to extend from
   * NoStackTrace, s nce that  s a good  nd cat on that   don't care
   * about t  deta ls.
   */
  def  sBor ng(t: Throwable): Boolean =
    t match {
      case _: NoStackTrace => true
      case _: T  outExcept on => true
      case _: Cancellat onExcept on => true
      case _: JT  outExcept on => true
      case _: ChannelExcept on => true
      case _: SocketExcept on => true
      case _: ClosedChannelExcept on => true
      case _: CancelledKeyExcept on => true
      case _: Thr ftExcept on => true
      // Deadl neExceededExcept ons are propagated as:
      // org.apac .thr ft.TAppl cat onExcept on:  nternal error process ng  ssue3: 'com.tw ter.f nagle.serv ce.Deadl neF lter$Deadl neExceededExcept on: exceeded request deadl ne of 100.m ll seconds by 4.m ll seconds. Deadl ne exp red at 2020-08-27 17:07:46 +0000 and now    s 2020-08-27 17:07:46 +0000.'
      case e: TAppl cat onExcept on =>
        e.get ssage != null && e.get ssage.conta ns("Deadl neExceededExcept on")
      case _ => false
    }
}
