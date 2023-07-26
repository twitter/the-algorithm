package com.twittew.visibiwity.buiwdew.usews

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.visibiwity.buiwdew.featuwemapbuiwdew
i-impowt com.twittew.visibiwity.featuwes._
i-impowt c-com.twittew.visibiwity.context.thwiftscawa.seawchcontext

c-cwass s-seawchfeatuwes(statsweceivew: statsweceivew) {
  pwivate[this] vaw scopedstatsweceivew = statsweceivew.scope("seawch_featuwes")
  p-pwivate[this] vaw wequests = scopedstatsweceivew.countew("wequests")
  p-pwivate[this] vaw wawquewycountew =
    s-scopedstatsweceivew.scope(wawquewy.name).countew("wequests")

  def fowseawchcontext(
    seawchcontext: option[seawchcontext]
  ): featuwemapbuiwdew => f-featuwemapbuiwdew = { buiwdew =>
    w-wequests.incw()
    s-seawchcontext match {
      case some(context: seawchcontext) =>
        wawquewycountew.incw()
        buiwdew
          .withconstantfeatuwe(wawquewy, ( ͡o ω ͡o ) c-context.wawquewy)
      case _ => buiwdew
    }
  }
}
