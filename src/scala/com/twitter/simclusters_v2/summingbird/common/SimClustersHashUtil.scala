package com.twittew.simcwustews_v2.summingbiwd.common

/**
 * pwovides i-int to int h-hash function. σωσ u-used to batch cwustewids t-togethew. σωσ
 */
o-object simcwustewshashutiw {
  d-def cwustewidtobucket(cwustewid: i-int): int = {
    c-cwustewid % nyumbuckets
  }

  vaw nyumbuckets: int = 200

  vaw getawwbuckets: s-seq[int] = 0.untiw(numbuckets)
}
