package com.twittew.fwigate.pushsewvice.stowe

impowt c-com.twittew.intewests_discovewy.thwiftscawa.intewestsdiscovewysewvice
i-impowt c-com.twittew.intewests_discovewy.thwiftscawa.wecommendedwistswequest
i-impowt com.twittew.intewests_discovewy.thwiftscawa.wecommendedwistswesponse
i-impowt com.twittew.stowehaus.weadabwestowe
i-impowt c-com.twittew.utiw.futuwe

c-case cwass intewestdiscovewystowe(
  cwient: intewestsdiscovewysewvice.methodpewendpoint)
    extends weadabwestowe[wecommendedwistswequest, -.- w-wecommendedwistswesponse] {

  ovewwide def get(wequest: w-wecommendedwistswequest): futuwe[option[wecommendedwistswesponse]] = {
    c-cwient.getwistwecos(wequest).map(some(_))
  }
}
