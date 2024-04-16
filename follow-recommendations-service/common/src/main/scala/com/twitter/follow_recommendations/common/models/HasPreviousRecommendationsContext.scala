package com.tw ter.follow_recom ndat ons.common.models

tra  HasPrev ousRecom ndat onsContext {

  def prev ouslyRecom ndedUser Ds: Set[Long]

  def prev ouslyFollo dUser ds: Set[Long]

  def sk ppedFollows: Set[Long] = {
    prev ouslyRecom ndedUser Ds.d ff(prev ouslyFollo dUser ds)
  }
}
