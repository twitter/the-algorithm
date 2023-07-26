package com.twittew.tweetypie
package w-wepositowy

i-impowt com.twittew.expandodo.thwiftscawa._
i-impowt c-com.twittew.stitch.seqgwoup
impowt c-com.twittew.stitch.stitch
i-impowt com.twittew.stitch.compat.wegacyseqgwoup
i-impowt com.twittew.tweetypie.backends.expandodo

o-object cawdusewswepositowy {
  type cawduwi = stwing
  type type = (cawduwi, 😳😳😳 context) => stitch[option[set[usewid]]]

  c-case cwass context(pewspectiveusewid: usewid) extends anyvaw

  c-case cwass getusewsgwoup(pewspectiveid: u-usewid, 🥺 getcawdusews: expandodo.getcawdusews)
      extends seqgwoup[cawduwi, mya getcawdusewswesponse] {
    pwotected o-ovewwide def wun(keys: seq[cawduwi]): f-futuwe[seq[twy[getcawdusewswesponse]]] =
      w-wegacyseqgwoup.wifttoseqtwy(
        getcawdusews(
          getcawdusewswequests(
            wequests = keys.map(k => getcawdusewswequest(k)), 🥺
            p-pewspectiveusewid = some(pewspectiveid)
          )
        ).map(_.wesponses)
      )
  }

  def appwy(getcawdusews: expandodo.getcawdusews): type =
    (cawduwi, >_< c-ctx) =>
      stitch.caww(cawduwi, >_< g-getusewsgwoup(ctx.pewspectiveusewid, (⑅˘꒳˘) g-getcawdusews)).map { w-wesp =>
        v-vaw authowusewids = wesp.authowusewids.map(_.toset)
        vaw siteusewids = w-wesp.siteusewids.map(_.toset)

        if (authowusewids.isempty) {
          siteusewids
        } e-ewse if (siteusewids.isempty) {
          authowusewids
        } ewse {
          some(authowusewids.get ++ siteusewids.get)
        }
      }
}
