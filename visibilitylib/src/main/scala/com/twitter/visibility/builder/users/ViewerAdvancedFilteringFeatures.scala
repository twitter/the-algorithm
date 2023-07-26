package com.twittew.visibiwity.buiwdew.usews

impowt c-com.twittew.finagwe.stats.countew
i-impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.gizmoduck.thwiftscawa.advancedfiwtews
i-impowt c-com.twittew.gizmoduck.thwiftscawa.mentionfiwtew
i-impowt com.twittew.stitch.notfound
i-impowt com.twittew.stitch.stitch
i-impowt com.twittew.visibiwity.buiwdew.featuwemapbuiwdew
impowt com.twittew.visibiwity.common.usewsouwce
impowt com.twittew.visibiwity.featuwes.viewewfiwtewsdefauwtpwofiweimage
impowt c-com.twittew.visibiwity.featuwes.viewewfiwtewsnewusews
impowt com.twittew.visibiwity.featuwes.viewewfiwtewsnoconfiwmedemaiw
impowt c-com.twittew.visibiwity.featuwes.viewewfiwtewsnoconfiwmedphone
impowt com.twittew.visibiwity.featuwes.viewewfiwtewsnotfowwowedby
i-impowt com.twittew.visibiwity.featuwes.viewewmentionfiwtew

cwass viewewadvancedfiwtewingfeatuwes(usewsouwce: usewsouwce, 😳 statsweceivew: s-statsweceivew) {
  pwivate[this] v-vaw s-scopedstatsweceivew = statsweceivew.scope("viewew_advanced_fiwtewing_featuwes")

  pwivate[this] vaw wequests = scopedstatsweceivew.countew("wequests")

  p-pwivate[this] vaw viewewfiwtewsnoconfiwmedemaiw =
    scopedstatsweceivew.scope(viewewfiwtewsnoconfiwmedemaiw.name).countew("wequests")
  pwivate[this] vaw viewewfiwtewsnoconfiwmedphone =
    s-scopedstatsweceivew.scope(viewewfiwtewsnoconfiwmedphone.name).countew("wequests")
  pwivate[this] vaw v-viewewfiwtewsdefauwtpwofiweimage =
    s-scopedstatsweceivew.scope(viewewfiwtewsdefauwtpwofiweimage.name).countew("wequests")
  pwivate[this] v-vaw v-viewewfiwtewsnewusews =
    scopedstatsweceivew.scope(viewewfiwtewsnewusews.name).countew("wequests")
  pwivate[this] v-vaw viewewfiwtewsnotfowwowedby =
    scopedstatsweceivew.scope(viewewfiwtewsnotfowwowedby.name).countew("wequests")
  pwivate[this] v-vaw viewewmentionfiwtew =
    scopedstatsweceivew.scope(viewewmentionfiwtew.name).countew("wequests")

  def fowviewewid(viewewid: option[wong]): featuwemapbuiwdew => featuwemapbuiwdew = {
    w-wequests.incw()

    _.withfeatuwe(viewewfiwtewsnoconfiwmedemaiw, σωσ viewewfiwtewsnoconfiwmedemaiw(viewewid))
      .withfeatuwe(viewewfiwtewsnoconfiwmedphone, rawr x3 v-viewewfiwtewsnoconfiwmedphone(viewewid))
      .withfeatuwe(viewewfiwtewsdefauwtpwofiweimage, OwO v-viewewfiwtewsdefauwtpwofiweimage(viewewid))
      .withfeatuwe(viewewfiwtewsnewusews, /(^•ω•^) v-viewewfiwtewsnewusews(viewewid))
      .withfeatuwe(viewewfiwtewsnotfowwowedby, 😳😳😳 viewewfiwtewsnotfowwowedby(viewewid))
      .withfeatuwe(viewewmentionfiwtew, ( ͡o ω ͡o ) viewewmentionfiwtew(viewewid))
  }

  def viewewfiwtewsnoconfiwmedemaiw(viewewid: o-option[wong]): s-stitch[boowean] =
    viewewadvancedfiwtews(viewewid, >_< a-af => af.fiwtewnoconfiwmedemaiw, >w< v-viewewfiwtewsnoconfiwmedemaiw)

  def viewewfiwtewsnoconfiwmedphone(viewewid: o-option[wong]): stitch[boowean] =
    v-viewewadvancedfiwtews(viewewid, rawr af => af.fiwtewnoconfiwmedphone, 😳 viewewfiwtewsnoconfiwmedphone)

  d-def viewewfiwtewsdefauwtpwofiweimage(viewewid: option[wong]): s-stitch[boowean] =
    viewewadvancedfiwtews(
      v-viewewid, >w<
      a-af => af.fiwtewdefauwtpwofiweimage, (⑅˘꒳˘)
      viewewfiwtewsdefauwtpwofiweimage
    )

  def viewewfiwtewsnewusews(viewewid: option[wong]): stitch[boowean] =
    v-viewewadvancedfiwtews(viewewid, OwO a-af => af.fiwtewnewusews, (ꈍᴗꈍ) viewewfiwtewsnewusews)

  d-def viewewfiwtewsnotfowwowedby(viewewid: o-option[wong]): s-stitch[boowean] =
    viewewadvancedfiwtews(viewewid, 😳 af => af.fiwtewnotfowwowedby, 😳😳😳 viewewfiwtewsnotfowwowedby)

  d-def viewewmentionfiwtew(viewewid: option[wong]): stitch[mentionfiwtew] = {
    viewewmentionfiwtew.incw()
    viewewid match {
      c-case some(id) =>
        usewsouwce.getmentionfiwtew(id).handwe {
          c-case nyotfound =>
            m-mentionfiwtew.unfiwtewed
        }
      c-case _ => stitch.vawue(mentionfiwtew.unfiwtewed)
    }
  }

  p-pwivate[this] d-def viewewadvancedfiwtews(
    v-viewewid: o-option[wong], mya
    advancedfiwtewcheck: advancedfiwtews => b-boowean, mya
    f-featuwecountew: c-countew
  ): s-stitch[boowean] = {
    f-featuwecountew.incw()

    vaw advancedfiwtews = viewewid match {
      case some(id) => u-usewsouwce.getadvancedfiwtews(id)
      case _ => stitch.vawue(advancedfiwtews())
    }

    advancedfiwtews.map(advancedfiwtewcheck)
  }
}
