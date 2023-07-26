package com.twittew.visibiwity.buiwdew.tweets

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.seawch.common.constants.thwiftscawa.thwiftquewysouwce
i-impowt com.twittew.visibiwity.buiwdew.featuwemapbuiwdew
i-impowt c-com.twittew.visibiwity.featuwes.seawchcandidatecount
i-impowt c-com.twittew.visibiwity.featuwes.seawchquewyhasusew
impowt com.twittew.visibiwity.featuwes.seawchquewysouwce
impowt com.twittew.visibiwity.featuwes.seawchwesuwtspagenumbew
impowt c-com.twittew.visibiwity.intewfaces.common.seawch.seawchvfwequestcontext

cwass seawchcontextfeatuwes(
  s-statsweceivew: statsweceivew) {
  p-pwivate[this] vaw scopedstatsweceivew = statsweceivew.scope("seawch_context_featuwes")
  pwivate[this] v-vaw wequests = scopedstatsweceivew.countew("wequests")
  p-pwivate[this] v-vaw seawchwesuwtspagenumbew =
    scopedstatsweceivew.scope(seawchwesuwtspagenumbew.name).countew("wequests")
  pwivate[this] vaw seawchcandidatecount =
    scopedstatsweceivew.scope(seawchcandidatecount.name).countew("wequests")
  p-pwivate[this] vaw seawchquewysouwce =
    scopedstatsweceivew.scope(seawchquewysouwce.name).countew("wequests")
  pwivate[this] vaw seawchquewyhasusew =
    scopedstatsweceivew.scope(seawchquewyhasusew.name).countew("wequests")

  d-def fowseawchcontext(
    seawchcontext: s-seawchvfwequestcontext
  ): f-featuwemapbuiwdew => f-featuwemapbuiwdew = {
    w-wequests.incw()
    seawchwesuwtspagenumbew.incw()
    seawchcandidatecount.incw()
    s-seawchquewysouwce.incw()
    seawchquewyhasusew.incw()

    _.withconstantfeatuwe(seawchwesuwtspagenumbew, mya seawchcontext.wesuwtspagenumbew)
      .withconstantfeatuwe(seawchcandidatecount, 🥺 seawchcontext.candidatecount)
      .withconstantfeatuwe(
        s-seawchquewysouwce, >_<
        seawchcontext.quewysouwceoption match {
          case some(quewysouwce) => quewysouwce
          case _ => thwiftquewysouwce.unknown
        })
      .withconstantfeatuwe(seawchquewyhasusew, >_< s-seawchcontext.quewyhasusew)
  }
}
