package com.tw ter.s mclusters_v2.common

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.servo.dec der.{Dec derGateBu lder, Dec derKeyNa }
 mport com.tw ter.servo.ut l.Gate

class Dec derGateBu lderW h dHash ng(dec der: Dec der) extends Dec derGateBu lder(dec der) {

  def  dGateW hHash ng[T](key: Dec derKeyNa ): Gate[T] = {
    val feature = keyToFeature(key)
    // Only  f t  dec der  s ne  r fully on / off  s t  object has d
    // T  does requ re an add  onal call to get t  dec der ava lab l y but that  s comparat vely c aper
    val convertToHash: T => Long = (obj: T) => {
      val ava lab l y = feature.ava lab l y.getOrElse(0)
       f (ava lab l y == 10000 || ava lab l y == 0) ava lab l y
      else obj.hashCode
    }
     dGate(key).contramap[T](convertToHash)
  }

}
