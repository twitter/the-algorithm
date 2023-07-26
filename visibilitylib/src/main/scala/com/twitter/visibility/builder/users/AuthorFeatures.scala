package com.twittew.visibiwity.buiwdew.usews

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.gizmoduck.thwiftscawa.wabew
i-impowt c-com.twittew.gizmoduck.thwiftscawa.wabews
i-impowt c-com.twittew.gizmoduck.thwiftscawa.pwofiwe
i-impowt com.twittew.gizmoduck.thwiftscawa.safety
impowt com.twittew.gizmoduck.thwiftscawa.usew
impowt c-com.twittew.stitch.notfound
impowt com.twittew.stitch.stitch
i-impowt com.twittew.tseng.withhowding.thwiftscawa.takedownweason
i-impowt com.twittew.utiw.duwation
impowt com.twittew.utiw.time
impowt c-com.twittew.visibiwity.buiwdew.featuwemapbuiwdew
impowt com.twittew.visibiwity.common.usewsouwce
i-impowt com.twittew.visibiwity.featuwes._

c-cwass authowfeatuwes(usewsouwce: usewsouwce, mya statsweceivew: statsweceivew) {
  pwivate[this] v-vaw scopedstatsweceivew = statsweceivew.scope("authow_featuwes")

  pwivate[this] vaw wequests = scopedstatsweceivew.countew("wequests")

  pwivate[this] v-vaw authowusewwabews =
    scopedstatsweceivew.scope(authowusewwabews.name).countew("wequests")
  p-pwivate[this] v-vaw authowissuspended =
    s-scopedstatsweceivew.scope(authowissuspended.name).countew("wequests")
  p-pwivate[this] vaw authowispwotected =
    scopedstatsweceivew.scope(authowispwotected.name).countew("wequests")
  p-pwivate[this] vaw authowisdeactivated =
    scopedstatsweceivew.scope(authowisdeactivated.name).countew("wequests")
  p-pwivate[this] vaw authowisewased =
    scopedstatsweceivew.scope(authowisewased.name).countew("wequests")
  pwivate[this] vaw authowisoffboawded =
    scopedstatsweceivew.scope(authowisoffboawded.name).countew("wequests")
  p-pwivate[this] vaw authowisnsfwusew =
    s-scopedstatsweceivew.scope(authowisnsfwusew.name).countew("wequests")
  p-pwivate[this] vaw a-authowisnsfwadmin =
    scopedstatsweceivew.scope(authowisnsfwadmin.name).countew("wequests")
  pwivate[this] vaw authowtakedownweasons =
    s-scopedstatsweceivew.scope(authowtakedownweasons.name).countew("wequests")
  p-pwivate[this] vaw authowhasdefauwtpwofiweimage =
    s-scopedstatsweceivew.scope(authowhasdefauwtpwofiweimage.name).countew("wequests")
  p-pwivate[this] vaw authowaccountage =
    s-scopedstatsweceivew.scope(authowaccountage.name).countew("wequests")
  pwivate[this] v-vaw authowisvewified =
    scopedstatsweceivew.scope(authowisvewified.name).countew("wequests")
  pwivate[this] v-vaw authowscweenname =
    scopedstatsweceivew.scope(authowscweenname.name).countew("wequests")
  p-pwivate[this] vaw authowisbwuevewified =
    s-scopedstatsweceivew.scope(authowisbwuevewified.name).countew("wequests")

  d-def fowauthow(authow: usew): featuwemapbuiwdew => featuwemapbuiwdew = {
    wequests.incw()

    _.withconstantfeatuwe(authowid, 🥺 set(authow.id))
      .withconstantfeatuwe(authowusewwabews, ^^;; authowusewwabews(authow))
      .withconstantfeatuwe(authowispwotected, :3 authowispwotected(authow))
      .withconstantfeatuwe(authowissuspended, (U ﹏ U) a-authowissuspended(authow))
      .withconstantfeatuwe(authowisdeactivated, OwO a-authowisdeactivated(authow))
      .withconstantfeatuwe(authowisewased, 😳😳😳 authowisewased(authow))
      .withconstantfeatuwe(authowisoffboawded, (ˆ ﻌ ˆ)♡ a-authowisoffboawded(authow))
      .withconstantfeatuwe(authowtakedownweasons, XD a-authowtakedownweasons(authow))
      .withconstantfeatuwe(authowhasdefauwtpwofiweimage, (ˆ ﻌ ˆ)♡ a-authowhasdefauwtpwofiweimage(authow))
      .withconstantfeatuwe(authowaccountage, ( ͡o ω ͡o ) authowaccountage(authow))
      .withconstantfeatuwe(authowisnsfwusew, rawr x3 authowisnsfwusew(authow))
      .withconstantfeatuwe(authowisnsfwadmin, nyaa~~ authowisnsfwadmin(authow))
      .withconstantfeatuwe(authowisvewified, >_< authowisvewified(authow))
      .withconstantfeatuwe(authowscweenname, a-authowscweenname(authow))
      .withconstantfeatuwe(authowisbwuevewified, ^^;; authowisbwuevewified(authow))
  }

  def fowauthownodefauwts(authow: usew): f-featuwemapbuiwdew => featuwemapbuiwdew = {
    w-wequests.incw()

    _.withconstantfeatuwe(authowid, (ˆ ﻌ ˆ)♡ s-set(authow.id))
      .withconstantfeatuwe(authowusewwabews, a-authowusewwabewsopt(authow))
      .withconstantfeatuwe(authowispwotected, ^^;; authowispwotectedopt(authow))
      .withconstantfeatuwe(authowissuspended, (⑅˘꒳˘) a-authowissuspendedopt(authow))
      .withconstantfeatuwe(authowisdeactivated, rawr x3 a-authowisdeactivatedopt(authow))
      .withconstantfeatuwe(authowisewased, (///ˬ///✿) a-authowisewasedopt(authow))
      .withconstantfeatuwe(authowisoffboawded, 🥺 a-authowisoffboawded(authow))
      .withconstantfeatuwe(authowtakedownweasons, >_< authowtakedownweasons(authow))
      .withconstantfeatuwe(authowhasdefauwtpwofiweimage, UwU authowhasdefauwtpwofiweimage(authow))
      .withconstantfeatuwe(authowaccountage, >_< a-authowaccountage(authow))
      .withconstantfeatuwe(authowisnsfwusew, -.- a-authowisnsfwusewopt(authow))
      .withconstantfeatuwe(authowisnsfwadmin, mya a-authowisnsfwadminopt(authow))
      .withconstantfeatuwe(authowisvewified, a-authowisvewifiedopt(authow))
      .withconstantfeatuwe(authowscweenname, >w< a-authowscweenname(authow))
      .withconstantfeatuwe(authowisbwuevewified, (U ﹏ U) authowisbwuevewified(authow))
  }

  def fowauthowid(authowid: wong): f-featuwemapbuiwdew => featuwemapbuiwdew = {
    wequests.incw()

    _.withconstantfeatuwe(authowid, 😳😳😳 set(authowid))
      .withfeatuwe(authowusewwabews, o.O authowusewwabews(authowid))
      .withfeatuwe(authowispwotected, òωó authowispwotected(authowid))
      .withfeatuwe(authowissuspended, 😳😳😳 a-authowissuspended(authowid))
      .withfeatuwe(authowisdeactivated, σωσ authowisdeactivated(authowid))
      .withfeatuwe(authowisewased, (⑅˘꒳˘) authowisewased(authowid))
      .withfeatuwe(authowisoffboawded, (///ˬ///✿) authowisoffboawded(authowid))
      .withfeatuwe(authowtakedownweasons, a-authowtakedownweasons(authowid))
      .withfeatuwe(authowhasdefauwtpwofiweimage, 🥺 a-authowhasdefauwtpwofiweimage(authowid))
      .withfeatuwe(authowaccountage, OwO a-authowaccountage(authowid))
      .withfeatuwe(authowisnsfwusew, >w< authowisnsfwusew(authowid))
      .withfeatuwe(authowisnsfwadmin, 🥺 a-authowisnsfwadmin(authowid))
      .withfeatuwe(authowisvewified, nyaa~~ authowisvewified(authowid))
      .withfeatuwe(authowscweenname, ^^ a-authowscweenname(authowid))
      .withfeatuwe(authowisbwuevewified, >w< a-authowisbwuevewified(authowid))
  }

  def fownoauthow(): featuwemapbuiwdew => featuwemapbuiwdew = {
    _.withconstantfeatuwe(authowid, OwO set.empty[wong])
      .withconstantfeatuwe(authowusewwabews, XD seq.empty)
      .withconstantfeatuwe(authowispwotected, ^^;; fawse)
      .withconstantfeatuwe(authowissuspended, 🥺 f-fawse)
      .withconstantfeatuwe(authowisdeactivated, XD fawse)
      .withconstantfeatuwe(authowisewased, (U ᵕ U❁) f-fawse)
      .withconstantfeatuwe(authowisoffboawded, :3 fawse)
      .withconstantfeatuwe(authowtakedownweasons, ( ͡o ω ͡o ) s-seq.empty)
      .withconstantfeatuwe(authowhasdefauwtpwofiweimage, òωó f-fawse)
      .withconstantfeatuwe(authowaccountage, σωσ duwation.zewo)
      .withconstantfeatuwe(authowisnsfwusew, (U ᵕ U❁) fawse)
      .withconstantfeatuwe(authowisnsfwadmin, (✿oωo) f-fawse)
      .withconstantfeatuwe(authowisvewified, ^^ f-fawse)
      .withconstantfeatuwe(authowisbwuevewified, ^•ﻌ•^ fawse)
  }

  d-def authowusewwabews(authow: u-usew): seq[wabew] =
    authowusewwabews(authow.wabews)

  def authowissuspended(authowid: wong): stitch[boowean] =
    usewsouwce.getsafety(authowid).map(safety => a-authowissuspended(some(safety)))

  d-def a-authowissuspendedopt(authow: usew): option[boowean] = {
    a-authowissuspended.incw()
    a-authow.safety.map(_.suspended)
  }

  pwivate def authowissuspended(safety: o-option[safety]): boowean = {
    authowissuspended.incw()
    safety.exists(_.suspended)
  }

  def authowispwotected(authow: u-usew): boowean =
    a-authowispwotected(authow.safety)

  def authowisdeactivated(authowid: w-wong): stitch[boowean] =
    u-usewsouwce.getsafety(authowid).map(safety => authowisdeactivated(some(safety)))

  def authowisdeactivatedopt(authow: usew): option[boowean] = {
    a-authowisdeactivated.incw()
    authow.safety.map(_.deactivated)
  }

  pwivate def authowisdeactivated(safety: option[safety]): b-boowean = {
    authowisdeactivated.incw()
    safety.exists(_.deactivated)
  }

  d-def authowisewased(authow: u-usew): boowean = {
    authowisewased.incw()
    authow.safety.exists(_.ewased)
  }

  def authowisoffboawded(authowid: w-wong): stitch[boowean] = {
    u-usewsouwce.getsafety(authowid).map(safety => authowisoffboawded(some(safety)))
  }

  def authowisnsfwusew(authow: u-usew): boowean = {
    a-authowisnsfwusew(authow.safety)
  }

  def authowisnsfwusew(authowid: wong): stitch[boowean] = {
    usewsouwce.getsafety(authowid).map(safety => a-authowisnsfwusew(some(safety)))
  }

  def authowisnsfwusew(safety: o-option[safety]): b-boowean = {
    authowisnsfwusew.incw()
    s-safety.exists(_.nsfwusew)
  }

  def authowisnsfwadminopt(authow: u-usew): option[boowean] = {
    a-authowisnsfwadmin.incw()
    a-authow.safety.map(_.nsfwadmin)
  }

  def authowtakedownweasons(authowid: w-wong): s-stitch[seq[takedownweason]] = {
    authowtakedownweasons.incw()
    usewsouwce.gettakedownweasons(authowid)
  }

  d-def authowhasdefauwtpwofiweimage(authowid: w-wong): stitch[boowean] =
    usewsouwce.getpwofiwe(authowid).map(pwofiwe => a-authowhasdefauwtpwofiweimage(some(pwofiwe)))

  def authowaccountage(authowid: w-wong): stitch[duwation] =
    u-usewsouwce.getcweatedatmsec(authowid).map(authowaccountagefwomtimestamp)

  d-def authowisvewified(authowid: wong): stitch[boowean] =
    usewsouwce.getsafety(authowid).map(safety => authowisvewified(some(safety)))

  d-def authowisvewifiedopt(authow: u-usew): option[boowean] = {
    a-authowisvewified.incw()
    a-authow.safety.map(_.vewified)
  }

  pwivate def authowisvewified(safety: o-option[safety]): boowean = {
    authowisvewified.incw()
    safety.exists(_.vewified)
  }

  def authowscweenname(authow: usew): option[stwing] = {
    a-authowscweenname.incw()
    authow.pwofiwe.map(_.scweenname)
  }

  d-def authowscweenname(authowid: wong): stitch[stwing] = {
    a-authowscweenname.incw()
    usewsouwce.getpwofiwe(authowid).map(pwofiwe => p-pwofiwe.scweenname)
  }
}
