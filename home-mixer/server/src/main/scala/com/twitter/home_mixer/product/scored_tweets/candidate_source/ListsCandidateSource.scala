package com.twittew.home_mixew.pwoduct.scowed_tweets.candidate_souwce

impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.candidate_souwce.candidatesouwce
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.candidatesouwceidentifiew
i-impowt c-com.twittew.stitch.stitch
i-impowt c-com.twittew.stitch.timewinesewvice.timewinesewvice
i-impowt com.twittew.timewinesewvice.{thwiftscawa => t-tws}

impowt j-javax.inject.inject
impowt javax.inject.singweton

@singweton
cwass wistscandidatesouwce @inject() (timewinesewvice: timewinesewvice)
    extends c-candidatesouwce[seq[tws.timewinequewy], mya tws.tweet] {

  ovewwide vaw identifiew: candidatesouwceidentifiew = c-candidatesouwceidentifiew("wists")

  ovewwide d-def appwy(wequests: seq[tws.timewinequewy]): stitch[seq[tws.tweet]] = {
    vaw timewines = stitch.twavewse(wequests) { w-wequest => timewinesewvice.gettimewine(wequest) }

    t-timewines.map {
      _.fwatmap {
        _.entwies.cowwect { c-case tws.timewineentwy.tweet(tweet) => tweet }
      }
    }
  }
}
