package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.servo.cac .ScopedCac Key
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.Place

case class PlaceKey(place d: Place d, language: Str ng)
    extends ScopedCac Key("t", "geo", 1, place d, language)

object PlaceRepos ory {
  type Type = PlaceKey => St ch[Place]
}
