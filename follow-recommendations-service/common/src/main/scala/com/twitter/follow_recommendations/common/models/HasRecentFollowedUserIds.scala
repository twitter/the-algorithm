package com.twittew.fowwow_wecommendations.common.modews

twait haswecentfowwowedusewids {
  // usew i-ids that awe w-wecentwy fowwowed b-by the tawget u-usew
  def wecentfowwowedusewids: o-option[seq[wong]]

  // u-usew i-ids that awe wecentwy f-fowwowed by the tawget usew in set data-stwuctuwe
  wazy vaw wecentfowwowedusewidsset: o-option[set[wong]] = wecentfowwowedusewids match {
    c-case some(usews) => some(usews.toset)
    c-case nyone => some(set.empty)
  }

  wazy vaw nyumwecentfowwowedusewids: int = wecentfowwowedusewids.map(_.size).getowewse(0)
}
