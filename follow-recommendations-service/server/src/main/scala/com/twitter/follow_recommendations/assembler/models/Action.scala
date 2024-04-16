package com.tw ter.follow_recom ndat ons.assembler.models

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}

case class Act on(text: Str ng, act onURL: Str ng) {
  lazy val toThr ft: t.Act on = {
    t.Act on(text, act onURL)
  }
}
