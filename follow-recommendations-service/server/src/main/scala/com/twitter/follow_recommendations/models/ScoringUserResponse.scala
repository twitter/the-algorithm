package com.tw ter.follow_recom ndat ons.models

 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}

case class Scor ngUserResponse(cand dates: Seq[Cand dateUser]) {
  lazy val toThr ft: t.Scor ngUserResponse =
    t.Scor ngUserResponse(cand dates.map(_.toUserThr ft))

  lazy val toRecom ndat onResponse: Recom ndat onResponse = Recom ndat onResponse(cand dates)

  lazy val toOffl neThr ft: offl ne.Offl neScor ngUserResponse =
    offl ne.Offl neScor ngUserResponse(cand dates.map(_.toOffl neUserThr ft))
}
