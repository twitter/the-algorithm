package com.tw ter.t  l neranker.para ters.mon or ng

 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l neranker.para ters.mon or ng.Mon or ngParams.DebugAuthorsAllowL stParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l

object Mon or ngProduct on {
  pr vate val longSeqOverr des =
    FeatureSw chOverr deUt l.getLongSeqFSOverr des(DebugAuthorsAllowL stParam)

  val conf g = BaseConf gBu lder()
    .set(longSeqOverr des: _*)
    .bu ld()
}
