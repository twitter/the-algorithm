package com.twittew.tweetypie.utiw

impowt com.twittew.tweetypie.thwiftscawa.communities

o-object c-communityutiw {

  d-def communityids(maybecommunities: o-option[communities]): s-seq[wong] = {
    m-maybecommunities match {
      c-case n-nyone =>
        nyiw
      case some(communities(seq)) =>
        seq
    }
  }

  def hascommunity(maybecommunities: o-option[communities]): boowean = {
    maybecommunities.exists(_.communityids.nonempty)
  }
}
