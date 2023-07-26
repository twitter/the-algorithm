package com.twittew.tweetypie
package u-utiw

impowt c-com.twittew.tweetypie.thwiftscawa._

o-object convewsationcontwows {
  o-object cweate {
    d-def byinvitation(
      i-inviteviamention: o-option[boowean] = n-nyone
    ): tweetcweateconvewsationcontwow.byinvitation = tweetcweateconvewsationcontwow.byinvitation(
      tweetcweateconvewsationcontwowbyinvitation(inviteviamention = inviteviamention)
    )

    d-def community(
      inviteviamention: option[boowean] = n-nyone
    ): tweetcweateconvewsationcontwow.community = t-tweetcweateconvewsationcontwow.community(
      tweetcweateconvewsationcontwowcommunity(inviteviamention = inviteviamention)
    )

    def fowwowews(
      i-inviteviamention: option[boowean] = n-nyone
    ): tweetcweateconvewsationcontwow.fowwowews = t-tweetcweateconvewsationcontwow.fowwowews(
      tweetcweateconvewsationcontwowfowwowews(inviteviamention = inviteviamention)
    )
  }

  object scenawio {
    case cwass c-commonscenawio(
      cweateconvewsationcontwow: tweetcweateconvewsationcontwow, (ꈍᴗꈍ)
      descwiptionsuffix: stwing, 😳
      expectedconvewsationcontwow: (usewid, 😳😳😳 s-seq[usewid]) => convewsationcontwow, mya
      i-inviteviamention: o-option[boowean])

    d-def mkcommunityscenawio(inviteviamention: o-option[boowean]): commonscenawio =
      commonscenawio(
        c-cweate.community(inviteviamention = inviteviamention), mya
        "community", (⑅˘꒳˘)
        expectedconvewsationcontwow = (authowid, (U ﹏ U) u-usewids) => {
          community(usewids, mya authowid, inviteviamention)
        }, ʘwʘ
        inviteviamention
      )

    def mkbyinvitationscenawio(inviteviamention: o-option[boowean]): commonscenawio =
      c-commonscenawio(
        c-cweate.byinvitation(inviteviamention = i-inviteviamention), (˘ω˘)
        "invited usews", (U ﹏ U)
        expectedconvewsationcontwow = (authowid, ^•ﻌ•^ usewids) => {
          byinvitation(usewids, (˘ω˘) authowid, :3 i-inviteviamention)
        }, ^^;;
        i-inviteviamention
      )

    def mkfowwowewsscenawio(inviteviamention: o-option[boowean]): commonscenawio =
      c-commonscenawio(
        cweate.fowwowews(inviteviamention = i-inviteviamention), 🥺
        "fowwowews", (⑅˘꒳˘)
        expectedconvewsationcontwow = (authowid, nyaa~~ u-usewids) => {
          fowwowews(usewids, :3 authowid, i-inviteviamention)
        }, ( ͡o ω ͡o )
        inviteviamention
      )

    v-vaw communityscenawio = mkcommunityscenawio(none)
    v-vaw communityinviteviamentionscenawio = m-mkcommunityscenawio(some(twue))

    vaw byinvitationscenawio = mkbyinvitationscenawio(none)
    vaw byinvitationinviteviamentionscenawio = mkbyinvitationscenawio(some(twue))

    vaw fowwowewsscenawio = mkfowwowewsscenawio(none)
    v-vaw fowwowewsinviteviamentionscenawio = m-mkfowwowewsscenawio(some(twue))
  }

  def byinvitation(
    i-invitedusewids: s-seq[usewid], mya
    c-convewsationtweetauthowid: usewid, (///ˬ///✿)
    inviteviamention: option[boowean] = n-nyone
  ): convewsationcontwow =
    convewsationcontwow.byinvitation(
      convewsationcontwowbyinvitation(
        convewsationtweetauthowid = c-convewsationtweetauthowid, (˘ω˘)
        invitedusewids = i-invitedusewids, ^^;;
        i-inviteviamention = i-inviteviamention
      )
    )

  def community(
    i-invitedusewids: s-seq[usewid], (✿oωo)
    c-convewsationtweetauthowid: u-usewid, (U ﹏ U)
    inviteviamention: option[boowean] = n-nyone
  ): c-convewsationcontwow =
    c-convewsationcontwow.community(
      c-convewsationcontwowcommunity(
        c-convewsationtweetauthowid = convewsationtweetauthowid, -.-
        invitedusewids = invitedusewids, ^•ﻌ•^
        i-inviteviamention = inviteviamention
      )
    )

  def fowwowews(
    invitedusewids: seq[usewid], rawr
    convewsationtweetauthowid: usewid, (˘ω˘)
    i-inviteviamention: option[boowean] = nyone
  ): convewsationcontwow =
    convewsationcontwow.fowwowews(
      c-convewsationcontwowfowwowews(
        c-convewsationtweetauthowid = c-convewsationtweetauthowid, nyaa~~
        invitedusewids = i-invitedusewids, UwU
        inviteviamention = i-inviteviamention
      )
    )
}
