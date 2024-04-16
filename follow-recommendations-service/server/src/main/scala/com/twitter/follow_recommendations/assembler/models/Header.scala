package com.tw ter.follow_recom ndat ons.assembler.models

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}

case class  ader(t le: T le) {
  lazy val toThr ft: t. ader = {
    t. ader(t le.toThr ft)
  }
}
