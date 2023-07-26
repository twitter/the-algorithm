package com.twittew.fowwow_wecommendations.common.wankews.common

impowt com.twittew.pwoduct_mixew.cowe.modew.common.univewsawnoun
i-impowt scawa.cowwection.mutabwe

o-object dedupcandidates {
  d-def a-appwy[c <: univewsawnoun[wong]](input: s-seq[c]): s-seq[c] = {
    v-vaw seen = mutabwe.hashset[wong]()
    i-input.fiwtew { candidate => seen.add(candidate.id) }
  }
}
