package com.twittew.home_mixew.functionaw_component.decowatow.buiwdew

impowt com.twittew.home_mixew.modew.homefeatuwes.scowefeatuwe
i-impowt com.twittew.home_mixew.pawam.homegwobawpawams.enabwesendscowestocwient
i-impowt com.twittew.pwoduct_mixew.component_wibwawy.modew.candidate.tweetcandidate
i-impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwemap.featuwemap
i-impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.decowatow.uwt.buiwdew.item.tweet.basetimewinesscoweinfobuiwdew
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.tweet.timewinesscoweinfo
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy

o-object hometimewinesscoweinfobuiwdew
    e-extends basetimewinesscoweinfobuiwdew[pipewinequewy, >_< tweetcandidate] {

  pwivate vaw undefinedtweetscowe = -1.0

  o-ovewwide def appwy(
    quewy: pipewinequewy,
    candidate: tweetcandidate, mya
    c-candidatefeatuwes: featuwemap
  ): o-option[timewinesscoweinfo] = {
    if (quewy.pawams(enabwesendscowestocwient)) {
      vaw scowe = candidatefeatuwes.getowewse(scowefeatuwe, mya n-nyone).getowewse(undefinedtweetscowe)
      some(timewinesscoweinfo(scowe))
    } e-ewse nyone
  }
}
