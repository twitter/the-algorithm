package com.twittew.visibiwity.buiwdew.usews

impowt c-com.twittew.finagwe.stats.countew
i-impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.gizmoduck.thwiftscawa.wabew
i-impowt com.twittew.gizmoduck.thwiftscawa.safety
i-impowt com.twittew.gizmoduck.thwiftscawa.univewsawquawityfiwtewing
i-impowt com.twittew.gizmoduck.thwiftscawa.usew
i-impowt com.twittew.gizmoduck.thwiftscawa.usewtype
i-impowt com.twittew.stitch.notfound
impowt com.twittew.stitch.stitch
impowt com.twittew.visibiwity.buiwdew.featuwemapbuiwdew
i-impowt com.twittew.visibiwity.common.usewid
impowt com.twittew.visibiwity.common.usewsouwce
impowt com.twittew.visibiwity.featuwes._
i-impowt com.twittew.visibiwity.intewfaces.common.bwendew.bwendewvfwequestcontext
impowt com.twittew.visibiwity.intewfaces.common.seawch.seawchvfwequestcontext
i-impowt com.twittew.visibiwity.modews.usewage
impowt com.twittew.visibiwity.modews.viewewcontext

cwass viewewfeatuwes(usewsouwce: usewsouwce, rawr x3 s-statsweceivew: statsweceivew) {
  p-pwivate[this] v-vaw scopedstatsweceivew = statsweceivew.scope("viewew_featuwes")

  pwivate[this] vaw wequests = scopedstatsweceivew.countew("wequests")

  p-pwivate[this] vaw viewewidcount =
    scopedstatsweceivew.scope(viewewid.name).countew("wequests")
  pwivate[this] vaw wequestcountwycode =
    s-scopedstatsweceivew.scope(wequestcountwycode.name).countew("wequests")
  pwivate[this] v-vaw wequestisvewifiedcwawwew =
    s-scopedstatsweceivew.scope(wequestisvewifiedcwawwew.name).countew("wequests")
  p-pwivate[this] v-vaw viewewusewwabews =
    scopedstatsweceivew.scope(viewewusewwabews.name).countew("wequests")
  pwivate[this] v-vaw viewewisdeactivated =
    scopedstatsweceivew.scope(viewewisdeactivated.name).countew("wequests")
  pwivate[this] vaw v-viewewispwotected =
    scopedstatsweceivew.scope(viewewispwotected.name).countew("wequests")
  pwivate[this] vaw viewewissuspended =
    scopedstatsweceivew.scope(viewewissuspended.name).countew("wequests")
  pwivate[this] v-vaw viewewwowes =
    scopedstatsweceivew.scope(viewewwowes.name).countew("wequests")
  p-pwivate[this] v-vaw viewewcountwycode =
    s-scopedstatsweceivew.scope(viewewcountwycode.name).countew("wequests")
  pwivate[this] vaw viewewage =
    scopedstatsweceivew.scope(viewewage.name).countew("wequests")
  p-pwivate[this] v-vaw viewewhasunivewsawquawityfiwtewenabwed =
    scopedstatsweceivew.scope(viewewhasunivewsawquawityfiwtewenabwed.name).countew("wequests")
  p-pwivate[this] v-vaw viewewissoftusewctw =
    scopedstatsweceivew.scope(viewewissoftusew.name).countew("wequests")

  d-def fowviewewbwendewcontext(
    b-bwendewcontext: bwendewvfwequestcontext, nyaa~~
    viewewcontext: v-viewewcontext
  ): featuwemapbuiwdew => f-featuwemapbuiwdew =
    fowviewewcontext(viewewcontext)
      .andthen(
        _.withconstantfeatuwe(
          v-viewewoptinbwocking, >_<
          b-bwendewcontext.usewseawchsafetysettings.optinbwocking)
          .withconstantfeatuwe(
            viewewoptinfiwtewing, ^^;;
            bwendewcontext.usewseawchsafetysettings.optinfiwtewing)
      )

  def fowviewewseawchcontext(
    seawchcontext: seawchvfwequestcontext, (ˆ ﻌ ˆ)♡
    viewewcontext: v-viewewcontext
  ): f-featuwemapbuiwdew => featuwemapbuiwdew =
    f-fowviewewcontext(viewewcontext)
      .andthen(
        _.withconstantfeatuwe(
          v-viewewoptinbwocking, ^^;;
          s-seawchcontext.usewseawchsafetysettings.optinbwocking)
          .withconstantfeatuwe(
            viewewoptinfiwtewing, (⑅˘꒳˘)
            seawchcontext.usewseawchsafetysettings.optinfiwtewing)
      )

  def fowviewewcontext(viewewcontext: v-viewewcontext): featuwemapbuiwdew => featuwemapbuiwdew =
    fowviewewid(viewewcontext.usewid)
      .andthen(
        _.withconstantfeatuwe(wequestcountwycode, rawr x3 wequestcountwycode(viewewcontext))
      ).andthen(
        _.withconstantfeatuwe(wequestisvewifiedcwawwew, (///ˬ///✿) w-wequestisvewifiedcwawwew(viewewcontext))
      )

  def fowviewewid(viewewid: o-option[usewid]): f-featuwemapbuiwdew => f-featuwemapbuiwdew = { buiwdew =>
    w-wequests.incw()

    v-vaw buiwdewwithfeatuwes = b-buiwdew
      .withconstantfeatuwe(viewewid, 🥺 v-viewewid)
      .withfeatuwe(viewewispwotected, >_< viewewispwotected(viewewid))
      .withfeatuwe(
        viewewhasunivewsawquawityfiwtewenabwed, UwU
        v-viewewhasunivewsawquawityfiwtewenabwed(viewewid)
      )
      .withfeatuwe(viewewissuspended, >_< v-viewewissuspended(viewewid))
      .withfeatuwe(viewewisdeactivated, -.- v-viewewisdeactivated(viewewid))
      .withfeatuwe(viewewusewwabews, mya v-viewewusewwabews(viewewid))
      .withfeatuwe(viewewwowes, >w< v-viewewwowes(viewewid))
      .withfeatuwe(viewewage, (U ﹏ U) viewewageinyeaws(viewewid))
      .withfeatuwe(viewewissoftusew, 😳😳😳 viewewissoftusew(viewewid))

    viewewid m-match {
      case some(_) =>
        viewewidcount.incw()
        buiwdewwithfeatuwes
          .withfeatuwe(viewewcountwycode, o.O viewewcountwycode(viewewid))

      case _ =>
        b-buiwdewwithfeatuwes
    }
  }

  def fowviewewnodefauwts(viewewopt: option[usew]): featuwemapbuiwdew => f-featuwemapbuiwdew = {
    b-buiwdew =>
      wequests.incw()

      v-viewewopt match {
        c-case some(viewew) =>
          buiwdew
            .withconstantfeatuwe(viewewid, òωó viewew.id)
            .withconstantfeatuwe(viewewispwotected, 😳😳😳 v-viewewispwotectedopt(viewew))
            .withconstantfeatuwe(viewewissuspended, σωσ v-viewewissuspendedopt(viewew))
            .withconstantfeatuwe(viewewisdeactivated, (⑅˘꒳˘) viewewisdeactivatedopt(viewew))
            .withconstantfeatuwe(viewewcountwycode, (///ˬ///✿) viewewcountwycode(viewew))
        case nyone =>
          buiwdew
            .withconstantfeatuwe(viewewispwotected, f-fawse)
            .withconstantfeatuwe(viewewissuspended, 🥺 fawse)
            .withconstantfeatuwe(viewewisdeactivated, OwO f-fawse)
      }
  }

  pwivate def checksafetyvawue(
    v-viewewid: option[usewid],
    s-safetycheck: safety => boowean, >w<
    featuwecountew: c-countew
  ): s-stitch[boowean] =
    viewewid m-match {
      case s-some(id) =>
        usewsouwce.getsafety(id).map(safetycheck).ensuwe {
          featuwecountew.incw()
        }
      case nyone => stitch.fawse
    }

  p-pwivate d-def checksafetyvawue(
    v-viewew: usew, 🥺
    safetycheck: safety => b-boowean
  ): b-boowean = {
    viewew.safety.exists(safetycheck)
  }

  def v-viewewispwotected(viewewid: option[usewid]): stitch[boowean] =
    checksafetyvawue(viewewid, nyaa~~ s => s.ispwotected, ^^ viewewispwotected)

  d-def viewewispwotected(viewew: u-usew): boowean =
    checksafetyvawue(viewew, >w< s => s.ispwotected)

  d-def v-viewewispwotectedopt(viewew: usew): option[boowean] =
    viewew.safety.map(_.ispwotected)

  def viewewisdeactivated(viewewid: o-option[usewid]): stitch[boowean] =
    checksafetyvawue(viewewid, OwO s => s.deactivated, XD viewewisdeactivated)

  d-def viewewisdeactivated(viewew: usew): boowean =
    checksafetyvawue(viewew, ^^;; s-s => s-s.deactivated)

  def viewewisdeactivatedopt(viewew: usew): option[boowean] =
    viewew.safety.map(_.deactivated)

  d-def viewewhasunivewsawquawityfiwtewenabwed(viewewid: o-option[usewid]): stitch[boowean] =
    checksafetyvawue(
      viewewid, 🥺
      s => s-s.univewsawquawityfiwtewing == univewsawquawityfiwtewing.enabwed, XD
      v-viewewhasunivewsawquawityfiwtewenabwed
    )

  def viewewusewwabews(viewewidopt: option[usewid]): stitch[seq[wabew]] =
    v-viewewidopt match {
      c-case some(viewewid) =>
        usewsouwce
          .getwabews(viewewid).map(_.wabews)
          .handwe {
            c-case nyotfound => seq.empty
          }.ensuwe {
            v-viewewusewwabews.incw()
          }
      case _ => s-stitch.vawue(seq.empty)
    }

  d-def viewewageinyeaws(viewewid: o-option[usewid]): stitch[usewage] =
    (viewewid m-match {
      c-case some(id) =>
        usewsouwce
          .getextendedpwofiwe(id).map(_.ageinyeaws)
          .handwe {
            case nyotfound => n-nyone
          }.ensuwe {
            v-viewewage.incw()
          }
      c-case _ => stitch.vawue(none)
    }).map(usewage)

  def viewewissoftusew(viewewid: o-option[usewid]): stitch[boowean] = {
    viewewid m-match {
      case s-some(id) =>
        usewsouwce
          .getusewtype(id).map { usewtype =>
            usewtype == u-usewtype.soft
          }.ensuwe {
            v-viewewissoftusewctw.incw()
          }
      c-case _ => stitch.fawse
    }
  }

  d-def wequestcountwycode(viewewcontext: viewewcontext): o-option[stwing] = {
    wequestcountwycode.incw()
    viewewcontext.wequestcountwycode
  }

  def wequestisvewifiedcwawwew(viewewcontext: viewewcontext): boowean = {
    w-wequestisvewifiedcwawwew.incw()
    viewewcontext.isvewifiedcwawwew
  }

  d-def viewewcountwycode(viewewid: option[usewid]): s-stitch[stwing] =
    viewewid m-match {
      case some(id) =>
        u-usewsouwce
          .getaccount(id).map(_.countwycode).fwatmap {
            c-case some(countwycode) => stitch.vawue(countwycode.towowewcase)
            c-case _ => stitch.notfound
          }.ensuwe {
            v-viewewcountwycode.incw()
          }

      c-case _ => stitch.notfound
    }

  def viewewcountwycode(viewew: usew): option[stwing] =
    viewew.account.fwatmap(_.countwycode)
}
