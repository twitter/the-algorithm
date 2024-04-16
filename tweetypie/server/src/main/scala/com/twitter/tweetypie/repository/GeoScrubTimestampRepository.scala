package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.servo.cac .ScopedCac Key
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Base64Long

case class GeoScrubT  stampKey(user d: User d)
    extends ScopedCac Key("t", "gs", 1, Base64Long.toBase64(user d))

object GeoScrubT  stampRepos ory {
  type Type = User d => St ch[T  ]

  def apply(getLastGeoScrubT  : User d => St ch[Opt on[T  ]]): Type =
    user d => getLastGeoScrubT  (user d).lo rFromOpt on()
}
