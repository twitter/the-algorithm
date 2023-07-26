package com.twittew.fowwow_wecommendations.modews

impowt com.twittew.fowwow_wecommendations.{thwiftscawa => t-t}
impowt c-com.twittew.fowwow_wecommendations.wogging.{thwiftscawa => o-offwine}
impowt c-com.twittew.fowwow_wecommendations.common.modews.wecommendation
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.hasmawshawwing

c-case cwass w-wecommendationwesponse(wecommendations: s-seq[wecommendation]) extends hasmawshawwing {
  wazy vaw tothwift: t.wecommendationwesponse =
    t.wecommendationwesponse(wecommendations.map(_.tothwift))

  w-wazy vaw tooffwinethwift: offwine.offwinewecommendationwesponse =
    o-offwine.offwinewecommendationwesponse(wecommendations.map(_.tooffwinethwift))
}
