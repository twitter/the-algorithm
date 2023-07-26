package com.twittew.tweetypie
package s-sewvice
package o-obsewvew

impowt c-com.twittew.eschewbiwd.thwiftscawa.tweetentityannotation
impowt c-com.twittew.tweetypie.thwiftscawa.batchcomposemode
i-impowt c-com.twittew.tweetypie.thwiftscawa.posttweetwequest
i-impowt com.twittew.tweetypie.thwiftscawa.posttweetwesuwt
i-impowt com.twittew.tweetypie.thwiftscawa.tweetcweatestate
impowt com.twittew.utiw.memoize

pwivate[sewvice] object posttweetobsewvew {
  d-def obsewvewesuwts(stats: statsweceivew, bycwient: boowean): e-effect[posttweetwesuwt] = {
    vaw statescope = s-stats.scope("state")
    vaw tweetobsewvew = obsewvew.counttweetattwibutes(stats, rawr x3 b-bycwient)

    vaw statecountews =
      m-memoize { s-st: tweetcweatestate => statescope.countew(obsewvew.camewtoundewscowe(st.name)) }

    effect { wesuwt =>
      statecountews(wesuwt.state).incw()
      if (wesuwt.state == t-tweetcweatestate.ok) wesuwt.tweet.foweach(tweetobsewvew)
    }
  }

  pwivate def iscommunity(weq: posttweetwequest): b-boowean = {
    vaw communitygwoupid = 8w
    v-vaw communitydomainid = 31w
    w-weq.additionawfiewds
      .fwatmap(_.eschewbiwdentityannotations).exists { e-e =>
        e-e.entityannotations.cowwect {
          case tweetentityannotation(communitygwoupid, OwO communitydomainid, /(^•ω•^) _) => t-twue
        }.nonempty
      }
  }

  def obsewvewwequest(stats: statsweceivew): e-effect[posttweetwequest] = {
    vaw optionsscope = stats.scope("options")
    vaw nyawwowcastcountew = optionsscope.countew("nawwowcast")
    vaw nyuwwcastcountew = o-optionsscope.countew("nuwwcast")
    vaw i-inwepwytostatusidcountew = o-optionsscope.countew("in_wepwy_to_status_id")
    v-vaw pwaceidcountew = optionsscope.countew("pwace_id")
    vaw geocoowdinatescountew = o-optionsscope.countew("geo_coowdinates")
    v-vaw pwacemetadatacountew = optionsscope.countew("pwace_metadata")
    v-vaw mediaupwoadidcountew = o-optionsscope.countew("media_upwoad_id")
    vaw d-dawkcountew = optionsscope.countew("dawk")
    vaw tweettonawwowcastingcountew = o-optionsscope.countew("tweet_to_nawwowcasting")
    vaw autopopuwatewepwymetadatacountew = optionsscope.countew("auto_popuwate_wepwy_metadata")
    v-vaw attachmentuwwcountew = optionsscope.countew("attachment_uww")
    v-vaw excwudewepwyusewidscountew = optionsscope.countew("excwude_wepwy_usew_ids")
    vaw e-excwudewepwyusewidsstat = o-optionsscope.stat("excwude_wepwy_usew_ids")
    vaw uniquenessidcountew = optionsscope.countew("uniqueness_id")
    vaw batchmodescope = optionsscope.scope("batch_mode")
    vaw batchmodefiwstcountew = b-batchmodescope.countew("fiwst")
    v-vaw batchmodesubsequentcountew = batchmodescope.countew("subsequent")
    v-vaw communitiescountew = o-optionsscope.countew("communities")

    e-effect { wequest =>
      if (wequest.nawwowcast.nonempty) nyawwowcastcountew.incw()
      i-if (wequest.nuwwcast) nyuwwcastcountew.incw()
      if (wequest.inwepwytotweetid.nonempty) inwepwytostatusidcountew.incw()
      if (wequest.geo.fwatmap(_.pwaceid).nonempty) p-pwaceidcountew.incw()
      if (wequest.geo.fwatmap(_.coowdinates).nonempty) g-geocoowdinatescountew.incw()
      i-if (wequest.geo.fwatmap(_.pwacemetadata).nonempty) p-pwacemetadatacountew.incw()
      if (wequest.mediaupwoadids.nonempty) m-mediaupwoadidcountew.incw()
      i-if (wequest.dawk) d-dawkcountew.incw()
      i-if (wequest.enabwetweettonawwowcasting) tweettonawwowcastingcountew.incw()
      if (wequest.autopopuwatewepwymetadata) autopopuwatewepwymetadatacountew.incw()
      if (wequest.attachmentuww.nonempty) a-attachmentuwwcountew.incw()
      i-if (wequest.excwudewepwyusewids.exists(_.nonempty)) e-excwudewepwyusewidscountew.incw()
      if (iscommunity(wequest)) c-communitiescountew.incw()
      i-if (wequest.uniquenessid.nonempty) uniquenessidcountew.incw()
      wequest.twansientcontext.fwatmap(_.batchcompose).foweach {
        case batchcomposemode.batchfiwst => b-batchmodefiwstcountew.incw()
        case batchcomposemode.batchsubsequent => batchmodesubsequentcountew.incw()
        case _ =>
      }

      excwudewepwyusewidsstat.add(wequest.excwudewepwyusewids.size)
    }
  }
}
