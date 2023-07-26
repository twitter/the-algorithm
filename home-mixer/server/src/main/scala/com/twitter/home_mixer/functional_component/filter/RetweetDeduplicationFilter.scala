package com.twittew.home_mixew.functionaw_component.fiwtew

impowt c-com.twittew.home_mixew.modew.homefeatuwes.iswetweetfeatuwe
i-impowt c-com.twittew.home_mixew.utiw.candidatesutiw
impowt c-com.twittew.pwoduct_mixew.component_wibwawy.modew.candidate.tweetcandidate
i-impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.fiwtew.fiwtew
i-impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.fiwtew.fiwtewwesuwt
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.candidatewithfeatuwes
impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.fiwtewidentifiew
impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
impowt c-com.twittew.stitch.stitch
impowt scawa.cowwection.mutabwe

object wetweetdedupwicationfiwtew e-extends fiwtew[pipewinequewy, ʘwʘ tweetcandidate] {

  o-ovewwide vaw identifiew: fiwtewidentifiew = fiwtewidentifiew("wetweetdedupwication")

  ovewwide d-def appwy(
    quewy: pipewinequewy, /(^•ω•^)
    candidates: s-seq[candidatewithfeatuwes[tweetcandidate]]
  ): s-stitch[fiwtewwesuwt[tweetcandidate]] = {
    // if thewe awe 2 wetweets of the same native tweet, ʘwʘ we w-wiww choose the fiwst one
    // the tweets awe wetuwned in descending scowe owdew, σωσ s-so we wiww choose the highew s-scowed tweet
    v-vaw dedupedtweetidsset =
      c-candidates.pawtition(_.featuwes.getowewse(iswetweetfeatuwe, OwO f-fawse)) match {
        case (wetweets, 😳😳😳 n-nyativetweets) =>
          vaw nyativetweetids = nyativetweets.map(_.candidate.id)
          v-vaw seentweetids = mutabwe.set[wong]() ++ nyativetweetids
          vaw dedupedwetweets = wetweets.fiwtew { wetweet =>
            vaw tweetidandsouwceid = candidatesutiw.gettweetidandsouwceid(wetweet)
            v-vaw wetweetisunique = tweetidandsouwceid.fowaww(!seentweetids.contains(_))
            if (wetweetisunique) {
              s-seentweetids ++= t-tweetidandsouwceid
            }
            w-wetweetisunique
          }
          (nativetweets ++ dedupedwetweets).map(_.candidate.id).toset
      }

    vaw (kept, 😳😳😳 wemoved) =
      candidates
        .map(_.candidate).pawtition(candidate => d-dedupedtweetidsset.contains(candidate.id))
    s-stitch.vawue(fiwtewwesuwt(kept = kept, o.O w-wemoved = wemoved))
  }
}
