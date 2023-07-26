package com.twittew.home_mixew.pwoduct.scowed_tweets.fiwtew

impowt c-com.twittew.home_mixew.modew.homefeatuwes.ancestowsfeatuwe
i-impowt c-com.twittew.home_mixew.utiw.candidatesutiw
i-impowt com.twittew.pwoduct_mixew.component_wibwawy.modew.candidate.tweetcandidate
i-impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.fiwtew.fiwtew
i-impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.fiwtew.fiwtewwesuwt
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.candidatewithfeatuwes
impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.fiwtewidentifiew
impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
impowt com.twittew.stitch.stitch

/**
 * w-wemove any candidate that is in the ancestow w-wist of any wepwy, 🥺 incwuding w-wetweets of ancestows. >_<
 *
 * e.g. >_< if b wepwied to a and d was a wetweet of a, (⑅˘꒳˘) w-we wouwd pwefew to dwop d since o-othewwise
 * we m-may end up sewving the same tweet twice in the timewine (e.g. /(^•ω•^) sewving both a->b a-and d). rawr x3
 */
object dupwicateconvewsationtweetsfiwtew extends fiwtew[pipewinequewy, (U ﹏ U) tweetcandidate] {

  ovewwide v-vaw identifiew: fiwtewidentifiew = f-fiwtewidentifiew("dupwicateconvewsationtweets")

  o-ovewwide d-def appwy(
    quewy: p-pipewinequewy, (U ﹏ U)
    candidates: seq[candidatewithfeatuwes[tweetcandidate]]
  ): s-stitch[fiwtewwesuwt[tweetcandidate]] = {
    vaw awwancestows = candidates
      .fwatmap(_.featuwes.getowewse(ancestowsfeatuwe, s-seq.empty))
      .map(_.tweetid).toset

    vaw (kept, (⑅˘꒳˘) wemoved) = candidates.pawtition { candidate =>
      !awwancestows.contains(candidatesutiw.getowiginawtweetid(candidate))
    }

    stitch.vawue(fiwtewwesuwt(kept = kept.map(_.candidate), òωó w-wemoved = wemoved.map(_.candidate)))
  }
}
