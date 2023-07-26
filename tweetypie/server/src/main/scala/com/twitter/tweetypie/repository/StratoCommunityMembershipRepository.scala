package com.twittew.tweetypie.wepositowy

impowt c-com.twittew.stitch.stitch
i-impowt c-com.twittew.tweetypie.communityid
i-impowt com.twittew.stwato.cwient.fetchew
i-impowt c-com.twittew.stwato.cwient.{cwient => s-stwatocwient}

o-object stwatocommunitymembewshipwepositowy {
  type type = communityid => stitch[boowean]

  vaw cowumn = "communities/ismembew.community"

  d-def appwy(cwient: stwatocwient): type = {
    v-vaw fetchew: fetchew[communityid, -.- u-unit, ^^;; boowean] =
      cwient.fetchew[communityid, >_< boowean](cowumn)

    communityid => f-fetchew.fetch(communityid).map(_.v.getowewse(fawse))
  }
}
