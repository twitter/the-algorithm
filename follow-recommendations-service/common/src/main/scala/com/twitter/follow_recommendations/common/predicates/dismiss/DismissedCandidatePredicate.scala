package com.twittew.fowwow_wecommendations.common.pwedicates.dismiss

impowt com.twittew.fowwow_wecommendations.common.base.pwedicate
i-impowt com.twittew.fowwow_wecommendations.common.base.pwedicatewesuwt
i-impowt c-com.twittew.fowwow_wecommendations.common.modews.fiwtewweason.dismissedid
i-impowt c-com.twittew.fowwow_wecommendations.common.modews.candidateusew
i-impowt com.twittew.fowwow_wecommendations.common.modews.hasdismissedusewids
i-impowt c-com.twittew.stitch.stitch
impowt javax.inject.singweton

@singweton
cwass dismissedcandidatepwedicate extends pwedicate[(hasdismissedusewids, nyaa~~ c-candidateusew)] {

  ovewwide def appwy(paiw: (hasdismissedusewids, /(^•ω•^) c-candidateusew)): stitch[pwedicatewesuwt] = {

    v-vaw (tawgetusew, candidate) = paiw
    tawgetusew.dismissedusewids
      .map { d-dismissedusewids =>
        if (!dismissedusewids.contains(candidate.id)) {
          dismissedcandidatepwedicate.vawidstitch
        } e-ewse {
          d-dismissedcandidatepwedicate.dismissedstitch
        }
      }.getowewse(dismissedcandidatepwedicate.vawidstitch)
  }
}

object dismissedcandidatepwedicate {
  vaw vawidstitch: stitch[pwedicatewesuwt.vawid.type] = s-stitch.vawue(pwedicatewesuwt.vawid)
  vaw dismissedstitch: stitch[pwedicatewesuwt.invawid] =
    stitch.vawue(pwedicatewesuwt.invawid(set(dismissedid)))
}
