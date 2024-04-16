package com.tw ter.ann.serv ce.query_server.common

 mport com.tw ter.ann.common.thr ftscala.BadRequest
 mport com.tw ter. d aserv ces.commons._

object Runt  Except onTransform extends Except onTransfor r {
  overr de def transform = {
    case e: BadRequest =>
      M suseExcept on nfo(e)
  }

  overr de def getStatNa : Part alFunct on[Except on, Str ng] = {
    case e: BadRequest => except onNa (e, e.code.na )
  }
}
