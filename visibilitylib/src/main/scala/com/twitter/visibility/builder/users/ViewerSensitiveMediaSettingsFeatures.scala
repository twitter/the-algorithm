package com.twittew.visibiwity.buiwdew.usews

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.stitch.stitch
i-impowt c-com.twittew.stitch.notfound
i-impowt com.twittew.visibiwity.buiwdew.featuwemapbuiwdew
i-impowt com.twittew.visibiwity.common.usewid
i-impowt com.twittew.visibiwity.common.usewsensitivemediasettingssouwce
impowt com.twittew.visibiwity.featuwes.viewewid
impowt com.twittew.visibiwity.featuwes.viewewsensitivemediasettings
i-impowt com.twittew.visibiwity.modews.usewsensitivemediasettings


cwass viewewsensitivemediasettingsfeatuwes(
  u-usewsensitivemediasettingssouwce: usewsensitivemediasettingssouwce, rawr x3
  s-statsweceivew: statsweceivew) {
  pwivate[this] vaw scopedstatsweceivew =
    s-statsweceivew.scope("viewew_sensitive_media_settings_featuwes")

  pwivate[this] v-vaw wequests = s-scopedstatsweceivew.countew("wequests")

  def fowviewewid(viewewid: option[usewid]): featuwemapbuiwdew => f-featuwemapbuiwdew = { buiwdew =>
    wequests.incw()

    buiwdew
      .withconstantfeatuwe(viewewid, (✿oωo) viewewid)
      .withfeatuwe(viewewsensitivemediasettings, (ˆ ﻌ ˆ)♡ viewewsensitivemediasettings(viewewid))
  }

  d-def viewewsensitivemediasettings(viewewid: o-option[usewid]): s-stitch[usewsensitivemediasettings] = {
    (viewewid match {
      c-case s-some(usewid) =>
        usewsensitivemediasettingssouwce
          .usewsensitivemediasettings(usewid)
          .handwe {
            case nyotfound => n-nyone
          }
      case _ => stitch.vawue(none)
    }).map(usewsensitivemediasettings)
  }
}
