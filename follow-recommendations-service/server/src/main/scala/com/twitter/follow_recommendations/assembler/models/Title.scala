package com.tw ter.follow_recom ndat ons.assembler.models

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}

case class T le(text: Str ng) {
  lazy val toThr ft: t.T le = {
    t.T le(text)
  }
}
