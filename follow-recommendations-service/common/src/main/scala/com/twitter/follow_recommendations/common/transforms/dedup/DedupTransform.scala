package com.twittew.fowwow_wecommendations.common.twansfowms.dedup

impowt com.twittew.fowwow_wecommendations.common.base.twansfowm
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.univewsawnoun
i-impowt com.twittew.stitch.stitch
i-impowt scawa.cowwection.mutabwe

c-cwass deduptwansfowm[wequest, -.- c-candidate <: u-univewsawnoun[wong]]()
    e-extends t-twansfowm[wequest, (ˆ ﻌ ˆ)♡ candidate] {
  ovewwide def twansfowm(tawget: wequest, (⑅˘꒳˘) candidates: s-seq[candidate]): stitch[seq[candidate]] = {
    vaw seen = m-mutabwe.hashset[wong]()
    stitch.vawue(candidates.fiwtew(candidate => s-seen.add(candidate.id)))
  }
}
