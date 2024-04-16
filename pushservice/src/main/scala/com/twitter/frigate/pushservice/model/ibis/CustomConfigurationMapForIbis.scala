package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter. b s2.l b.ut l.JsonMarshal
 mport com.tw ter.ut l.Future

tra  CustomConf gurat onMapFor b s {
  self: PushCand date =>

  lazy val customConf gMapsJsonFut: Future[Str ng] = {
    customF eldsMapFut.map { customF elds =>
      JsonMarshal.toJson(customF elds)
    }
  }

  lazy val customConf gMapsFut: Future[Map[Str ng, Str ng]] = {
     f (self.target. sLoggedOutUser) {
      Future.value(Map.empty[Str ng, Str ng])
    } else {
      customConf gMapsJsonFut.map { customConf gMapsJson =>
        Map("custom_conf g" -> customConf gMapsJson)
      }
    }
  }
}
