package com.tw ter.ho _m xer

 mport com.tw ter.f natra.http.rout ng.HttpWarmup
 mport com.tw ter.f natra.httpcl ent.RequestBu lder._
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter. nject.ut ls.Handler
 mport com.tw ter.ut l.Try
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ho M xerHttpServerWarmupHandler @ nject() (warmup: HttpWarmup) extends Handler w h Logg ng {

  overr de def handle(): Un  = {
    Try(warmup.send(get("/adm n/product-m xer/product-p pel nes"), adm n = true)())
      .onFa lure(e => error(e.get ssage, e))
  }
}
