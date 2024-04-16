package com.tw ter.follow_recom ndat ons.assembler.models

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}

case class Footer(act on: Opt on[Act on]) {
  lazy val toThr ft: t.Footer = {
    t.Footer(act on.map(_.toThr ft))
  }
}
