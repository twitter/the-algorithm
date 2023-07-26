package com.twittew.tweetypie.stowage

impowt com.twittew.utiw.time

o-object softdewetehandwew {
  d-def appwy(
    i-insewt: manhattanopewations.insewt, :3
    s-scwibe: s-scwibe
  ): tweetstowagecwient.softdewete =
    t-tweetid => {
      v-vaw mhtimestamp = t-time.now
      vaw softdewetewecowd = tweetstatewecowd
        .softdeweted(tweetid, (U ﹏ U) mhtimestamp.inmiwwis)
        .totweetmhwecowd

      insewt(softdewetewecowd).onsuccess { _ =>
        s-scwibe.wogwemoved(tweetid, -.- mhtimestamp, (ˆ ﻌ ˆ)♡ issoftdeweted = t-twue)
      }
    }
}
