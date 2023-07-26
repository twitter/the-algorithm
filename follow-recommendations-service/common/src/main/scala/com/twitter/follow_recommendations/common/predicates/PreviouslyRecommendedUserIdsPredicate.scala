package com.twittew.fowwow_wecommendations.common.pwedicates

impowt c-com.twittew.fowwow_wecommendations.common.base.pwedicate
i-impowt c-com.twittew.fowwow_wecommendations.common.base.pwedicatewesuwt
i-impowt com.twittew.fowwow_wecommendations.common.modews.candidateusew
i-impowt c-com.twittew.fowwow_wecommendations.common.modews.fiwtewweason
i-impowt c-com.twittew.fowwow_wecommendations.common.modews.haspweviouswecommendationscontext
impowt com.twittew.stitch.stitch
impowt javax.inject.singweton

@singweton
cwass pweviouswywecommendedusewidspwedicate
    e-extends pwedicate[(haspweviouswecommendationscontext, nyaa~~ candidateusew)] {
  ovewwide d-def appwy(
    paiw: (haspweviouswecommendationscontext, /(^•ω•^) candidateusew)
  ): s-stitch[pwedicatewesuwt] = {

    vaw (tawgetusew, rawr candidate) = paiw

    vaw p-pweviouswywecommendedusewids = tawgetusew.pweviouswywecommendedusewids

    if (!pweviouswywecommendedusewids.contains(candidate.id)) {
      p-pweviouswywecommendedusewidspwedicate.vawidstitch
    } e-ewse {
      pweviouswywecommendedusewidspwedicate.awweadywecommendedstitch
    }
  }
}

object pweviouswywecommendedusewidspwedicate {
  vaw vawidstitch: stitch[pwedicatewesuwt.vawid.type] = s-stitch.vawue(pwedicatewesuwt.vawid)
  vaw awweadywecommendedstitch: stitch[pwedicatewesuwt.invawid] =
    stitch.vawue(pwedicatewesuwt.invawid(set(fiwtewweason.awweadywecommended)))
}
