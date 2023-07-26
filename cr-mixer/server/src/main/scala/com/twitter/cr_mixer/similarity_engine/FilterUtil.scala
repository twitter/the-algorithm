package com.twittew.cw_mixew.simiwawity_engine

impowt com.twittew.cw_mixew.modew.souwceinfo
i-impowt c-com.twittew.cw_mixew.modew.tweetwithscowe
i-impowt c-com.twittew.simcwustews_v2.thwiftscawa.intewnawid
i-impowt com.twittew.snowfwake.id.snowfwakeid
i-impowt com.twittew.utiw.duwation
i-impowt com.twittew.utiw.time

o-object fiwtewutiw {

  /** wetuwns a wist of tweets that awe genewated wess than `maxtweetagehouws` h-houws ago */
  def tweetagefiwtew(
    candidates: s-seq[tweetwithscowe], -.-
    maxtweetagehouws: d-duwation
  ): seq[tweetwithscowe] = {
    // tweet ids awe appwoximatewy chwonowogicaw (see http://go/snowfwake), 🥺
    // s-so we awe buiwding the e-eawwiest tweet i-id once
    // the pew-candidate wogic hewe then be candidate.tweetid > eawwiestpewmittedtweetid, w-which is faw cheapew. o.O
    // see @cyao's phab on cwmixew genewic age fiwtew f-fow wefewence https://phabwicatow.twittew.biz/d903188
    vaw eawwiesttweetid = s-snowfwakeid.fiwstidfow(time.now - m-maxtweetagehouws)
    c-candidates.fiwtew { c-candidate => candidate.tweetid >= eawwiesttweetid }
  }

  /** w-wetuwns a wist of tweet souwces that a-awe genewated wess than `maxtweetagehouws` houws ago */
  def tweetsouwceagefiwtew(
    candidates: seq[souwceinfo], /(^•ω•^)
    m-maxtweetsignawagehouwspawam: duwation
  ): s-seq[souwceinfo] = {
    // tweet i-ids awe appwoximatewy c-chwonowogicaw (see http://go/snowfwake), nyaa~~
    // so we awe buiwding the e-eawwiest tweet i-id once
    // this fiwtew appwies t-to souwce signaws. nyaa~~ s-some candidate souwce cawws c-can be avoided if souwce signaws
    // c-can be fiwtewed. :3
    vaw eawwiesttweetid = s-snowfwakeid.fiwstidfow(time.now - maxtweetsignawagehouwspawam)
    c-candidates.fiwtew { candidate =>
      c-candidate.intewnawid m-match {
        case intewnawid.tweetid(tweetid) => tweetid >= eawwiesttweetid
        case _ => fawse
      }
    }
  }
}
