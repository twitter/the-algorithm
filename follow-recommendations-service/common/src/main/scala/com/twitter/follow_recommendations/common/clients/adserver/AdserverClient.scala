package com.twittew.fowwow_wecommendations.common.cwients.adsewvew

impowt com.twittew.adsewvew.thwiftscawa.newadsewvew
i-impowt com.twittew.adsewvew.{thwiftscawa => t-t}
impowt com.twittew.stitch.stitch
i-impowt javax.inject.{inject, rawr x3 s-singweton}

@singweton
c-cwass a-adsewvewcwient @inject() (adsewvewsewvice: n-nyewadsewvew.methodpewendpoint) {
  d-def getadimpwessions(adwequest: adwequest): stitch[seq[t.adimpwession]] = {
    stitch
      .cawwfutuwe(
        adsewvewsewvice.makeadwequest(adwequest.tothwift)
      ).map(_.impwessions)
  }
}
