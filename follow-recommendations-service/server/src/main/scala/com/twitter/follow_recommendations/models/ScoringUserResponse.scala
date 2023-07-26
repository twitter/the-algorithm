package com.twittew.fowwow_wecommendations.modews

impowt com.twittew.fowwow_wecommendations.common.modews.candidateusew
i-impowt com.twittew.fowwow_wecommendations.wogging.{thwiftscawa => o-offwine}
i-impowt com.twittew.fowwow_wecommendations.{thwiftscawa => t-t}

c-case cwass scowingusewwesponse(candidates: s-seq[candidateusew]) {
  w-wazy vaw tothwift: t-t.scowingusewwesponse =
    t.scowingusewwesponse(candidates.map(_.tousewthwift))

  wazy vaw towecommendationwesponse: wecommendationwesponse = wecommendationwesponse(candidates)

  w-wazy vaw tooffwinethwift: offwine.offwinescowingusewwesponse =
    o-offwine.offwinescowingusewwesponse(candidates.map(_.tooffwineusewthwift))
}
