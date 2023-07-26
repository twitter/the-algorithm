package com.twittew.fowwow_wecommendations.common.pwedicates.sgs

impowt com.twittew.fowwow_wecommendations.common.base.pwedicate
i-impowt com.twittew.fowwow_wecommendations.common.base.pwedicatewesuwt
i-impowt com.twittew.fowwow_wecommendations.common.modews.candidateusew
i-impowt c-com.twittew.fowwow_wecommendations.common.modews.fiwtewweason
i-impowt com.twittew.fowwow_wecommendations.common.modews.haswecentfowwowedusewids
i-impowt com.twittew.stitch.stitch
i-impowt javax.inject.singweton

@singweton
c-cwass wecentfowwowingpwedicate extends pwedicate[(haswecentfowwowedusewids, rawr x3 candidateusew)] {

  ovewwide d-def appwy(paiw: (haswecentfowwowedusewids, nyaa~~ candidateusew)): stitch[pwedicatewesuwt] = {

    v-vaw (tawgetusew, /(^•ω•^) candidate) = p-paiw
    tawgetusew.wecentfowwowedusewidsset match {
      case some(usews) =>
        if (!usews.contains(candidate.id)) {
          w-wecentfowwowingpwedicate.vawidstitch
        } ewse {
          w-wecentfowwowingpwedicate.awweadyfowwowedstitch
        }
      c-case nyone => wecentfowwowingpwedicate.vawidstitch
    }
  }
}

object wecentfowwowingpwedicate {
  vaw vawidstitch: stitch[pwedicatewesuwt.vawid.type] = s-stitch.vawue(pwedicatewesuwt.vawid)
  vaw awweadyfowwowedstitch: stitch[pwedicatewesuwt.invawid] =
    stitch.vawue(pwedicatewesuwt.invawid(set(fiwtewweason.awweadyfowwowed)))
}
