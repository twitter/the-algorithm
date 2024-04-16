package com.tw ter.follow_recom ndat ons.common.cl ents.adserver

 mport com.tw ter.adserver.thr ftscala.NewAdServer
 mport com.tw ter.adserver.{thr ftscala => t}
 mport com.tw ter.st ch.St ch
 mport javax. nject.{ nject, S ngleton}

@S ngleton
class AdserverCl ent @ nject() (adserverServ ce: NewAdServer. thodPerEndpo nt) {
  def getAd mpress ons(adRequest: AdRequest): St ch[Seq[t.Ad mpress on]] = {
    St ch
      .callFuture(
        adserverServ ce.makeAdRequest(adRequest.toThr ft)
      ).map(_. mpress ons)
  }
}
