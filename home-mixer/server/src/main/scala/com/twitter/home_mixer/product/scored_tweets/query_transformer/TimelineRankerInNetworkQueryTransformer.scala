package com.twittew.home_mixew.pwoduct.scowed_tweets.quewy_twansfowmew

impowt com.twittew.convewsions.duwationops._
i-impowt com.twittew.cowe_wowkfwows.usew_modew.{thwiftscawa => u-um}
impowt com.twittew.home_mixew.modew.homefeatuwes.usewstatefeatuwe
i-impowt com.twittew.home_mixew.modew.wequest.hasdevicecontext
i-impowt com.twittew.home_mixew.pwoduct.scowed_tweets.pawam.scowedtweetspawam
i-impowt com.twittew.home_mixew.pwoduct.scowed_tweets.quewy_twansfowmew.timewinewankewinnetwowkquewytwansfowmew._
i-impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.twansfowmew.candidatepipewinequewytwansfowmew
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.candidatepipewineidentifiew
impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
impowt com.twittew.pwoduct_mixew.cowe.quawity_factow.hasquawityfactowstatus
impowt com.twittew.timewinewankew.{thwiftscawa => t-t}
impowt com.twittew.timewines.common.modew.tweetkindoption
impowt c-com.twittew.timewines.modew.candidate.candidatetweetsouwceid

object timewinewankewinnetwowkquewytwansfowmew {
  p-pwivate vaw defauwtsinceduwation = 24.houws
  pwivate vaw expandedsinceduwation = 48.houws
  pwivate vaw maxtweetstofetch = 600

  pwivate vaw t-tweetkindoptions: tweetkindoption.vawueset = tweetkindoption(
    i-incwudewepwies = t-twue, ^^
    incwudewetweets = twue, 😳😳😳
    incwudeowiginawtweetsandquotes = twue, mya
    incwudeextendedwepwies = twue
  )

  pwivate v-vaw usewstatesfowextendedsinceduwation: set[um.usewstate] = set(
    um.usewstate.wight, 😳
    um.usewstate.mediumnontweetew, -.-
    um.usewstate.mediumtweetew, 🥺
    u-um.usewstate.neawzewo, o.O
    um.usewstate.new,
    u-um.usewstate.vewywight
  )
}

c-case cwass timewinewankewinnetwowkquewytwansfowmew[
  q-quewy <: p-pipewinequewy with hasquawityfactowstatus with h-hasdevicecontext
](
  ovewwide vaw candidatepipewineidentifiew: c-candidatepipewineidentifiew, /(^•ω•^)
  ovewwide vaw maxtweetstofetch: int = maxtweetstofetch)
    extends candidatepipewinequewytwansfowmew[quewy, nyaa~~ t-t.wecapquewy]
    with t-timewinewankewquewytwansfowmew[quewy] {

  o-ovewwide v-vaw candidatetweetsouwceid = candidatetweetsouwceid.wecycwedtweet
  ovewwide vaw options = t-tweetkindoptions

  o-ovewwide def gettensowfwowmodew(quewy: q-quewy): o-option[stwing] = {
    some(quewy.pawams(scowedtweetspawam.eawwybiwdtensowfwowmodew.innetwowkpawam))
  }

  o-ovewwide def twansfowm(input: quewy): t-t.wecapquewy = {
    vaw usewstate = input.featuwes.get.getowewse(usewstatefeatuwe, nyaa~~ n-nyone)

    vaw sinceduwation =
      i-if (usewstate.exists(usewstatesfowextendedsinceduwation.contains)) expandedsinceduwation
      ewse d-defauwtsinceduwation

    b-buiwdtimewinewankewquewy(input, :3 sinceduwation).tothwiftwecapquewy
  }
}
