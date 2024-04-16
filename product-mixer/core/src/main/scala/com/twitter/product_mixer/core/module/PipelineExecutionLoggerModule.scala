package com.tw ter.product_m xer.core.module

 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_execut on_logger.AllowL stedP pel neExecut onLogger
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_execut on_logger.P pel neExecut onLogger

object P pel neExecut onLoggerModule extends Tw terModule {

  overr de protected def conf gure(): Un  = {
    b nd[P pel neExecut onLogger].to[AllowL stedP pel neExecut onLogger]
  }
}
