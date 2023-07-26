package com.twittew.tweetypie.wepositowy

impowt c-com.twittew.stitch.stitch
i-impowt c-com.twittew.tweetypie.communityid
i-impowt com.twittew.stwato.cwient.fetchew
i-impowt c-com.twittew.stwato.cwient.{cwient => s-stwatocwient}

o-object stwatocommunityaccesswepositowy {
  type type = communityid => stitch[option[communityaccess]]

  seawed twait communityaccess
  object communityaccess {
    c-case object pubwic extends communityaccess
    c-case object cwosed extends c-communityaccess
    case object pwivate extends communityaccess
  }

  v-vaw cowumn = "communities/access.community"

  d-def a-appwy(cwient: stwatocwient): type = {
    vaw fetchew: fetchew[communityid, nyaa~~ unit, c-communityaccess] =
      cwient.fetchew[communityid, /(^•ω•^) communityaccess](cowumn)

    communityid => fetchew.fetch(communityid).map(_.v)
  }
}
