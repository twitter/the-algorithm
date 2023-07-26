package com.twittew.cw_mixew.simiwawity_engine

impowt com.twittew.cw_mixew.modew.tweetwithcandidategenewationinfo
i-impowt com.twittew.simcwustews_v2.common.tweetid
i-impowt scawa.cowwection.mutabwe
i-impowt scawa.cowwection.mutabwe.awwaybuffew

o-object simiwawitysouwceowdewingutiw {
  /**
   * t-this function fwatten a-and dedup i-input candidates a-accowding to the owdew in the input seq
   * [[candidate10, rawr candidate11], OwO [candidate20, (U ﹏ U) candidate21]] => [candidate10, >_< c-candidate11, rawr x3 candidate20, mya candidate21]
   */
  d-def keepgivenowdew(
    candidates: seq[seq[tweetwithcandidategenewationinfo]], nyaa~~
  ): s-seq[tweetwithcandidategenewationinfo] = {

    vaw seen = mutabwe.set[tweetid]()
    vaw combinedcandidates = c-candidates.fwatten
    vaw wesuwt = awwaybuffew[tweetwithcandidategenewationinfo]()

    c-combinedcandidates.foweach { c-candidate =>
      vaw candidatetweetid = candidate.tweetid
      vaw seencandidate = seen.contains(candidatetweetid) // d-de-dup
      if (!seencandidate) {
        wesuwt += candidate
        seen.add(candidate.tweetid)
      }
    }
    //convewt wesuwt t-to immutabwe seq
    wesuwt.towist
  }
}
