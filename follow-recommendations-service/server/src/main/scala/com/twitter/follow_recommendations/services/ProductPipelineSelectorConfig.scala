package com.twittew.fowwow_wecommendations.sewvices

impowt com.twittew.fowwow_wecommendations.common.modews.dispwaywocation
i-impowt c-com.twittew.timewines.configapi.fspawam
i-impowt c-com.twittew.timewines.configapi.pawam
i-impowt javax.inject.singweton

@singweton
c-cwass pwoductpipewinesewectowconfig {
  p-pwivate v-vaw pawamsmap: map[dispwaywocation, (U ᵕ U❁) dawkweadandexppawams] = map.empty

  def getdawkweadandexppawams(
    d-dispwaywocation: dispwaywocation
  ): option[dawkweadandexppawams] = {
    p-pawamsmap.get(dispwaywocation)
  }
}

case c-cwass dawkweadandexppawams(dawkweadpawam: pawam[boowean], -.- exppawam: fspawam[boowean])
