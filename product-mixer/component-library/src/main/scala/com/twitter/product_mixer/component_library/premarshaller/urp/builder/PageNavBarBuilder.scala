package com.twittew.pwoduct_mixew.component_wibwawy.pwemawshawwew.uwp.buiwdew

impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.pwesentation.candidatewithdetaiws
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwp.pagenavbaw
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy

/**
 * t-twait fow o-ouw buiwdew which g-given a quewy a-and sewections w-wiww wetuwn an `option[pagenavbaw]`
 *
 * @tpawam quewy
 */
twait pagenavbawbuiwdew[-quewy <: pipewinequewy] {

  def buiwd(
    quewy: quewy,
    s-sewections: seq[candidatewithdetaiws]
  ): option[pagenavbaw]
}
