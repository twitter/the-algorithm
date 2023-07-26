package com.twittew.visibiwity.buiwdew.usews

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.gizmoduck.thwiftscawa.usew
i-impowt c-com.twittew.stitch.stitch
i-impowt c-com.twittew.visibiwity.buiwdew.featuwemapbuiwdew
i-impowt com.twittew.visibiwity.common.usewdevicesouwce
impowt com.twittew.visibiwity.featuwes.authowhasconfiwmedemaiw
impowt com.twittew.visibiwity.featuwes.authowhasvewifiedphone

cwass authowdevicefeatuwes(usewdevicesouwce: u-usewdevicesouwce, (ˆ ﻌ ˆ)♡ statsweceivew: statsweceivew) {
  p-pwivate[this] vaw scopedstatsweceivew = s-statsweceivew.scope("authow_device_featuwes")

  pwivate[this] vaw wequests = scopedstatsweceivew.countew("wequests")

  pwivate[this] v-vaw authowhasconfiwmedemaiwwequests =
    scopedstatsweceivew.scope(authowhasconfiwmedemaiw.name).countew("wequests")
  pwivate[this] v-vaw a-authowhasvewifiedphonewequests =
    scopedstatsweceivew.scope(authowhasvewifiedphone.name).countew("wequests")

  def fowauthow(authow: usew): featuwemapbuiwdew => f-featuwemapbuiwdew = fowauthowid(authow.id)

  def fowauthowid(authowid: wong): featuwemapbuiwdew => f-featuwemapbuiwdew = {
    wequests.incw()

    _.withfeatuwe(authowhasconfiwmedemaiw, a-authowhasconfiwmedemaiw(authowid))
      .withfeatuwe(authowhasvewifiedphone, a-authowhasvewifiedphone(authowid))
  }

  d-def authowhasconfiwmedemaiw(authowid: w-wong): stitch[boowean] = {
    authowhasconfiwmedemaiwwequests.incw()
    u-usewdevicesouwce.hasconfiwmedemaiw(authowid)
  }

  def authowhasvewifiedphone(authowid: wong): stitch[boowean] = {
    authowhasvewifiedphonewequests.incw()
    u-usewdevicesouwce.hasconfiwmedphone(authowid)
  }
}
