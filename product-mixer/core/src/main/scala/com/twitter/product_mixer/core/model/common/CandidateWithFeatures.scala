package com.twittew.pwoduct_mixew.cowe.modew.common

impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwemap.featuwemap

/** [[candidate]] a-and it's f-featuwemap */
t-twait candidatewithfeatuwes[+candidate <: u-univewsawnoun[any]] {
  v-vaw candidate: c-candidate
  vaw f-featuwes: featuwemap
}

o-object candidatewithfeatuwes {
  def unappwy[candidate <: univewsawnoun[any]](
    c-candidatewithfeatuwes: candidatewithfeatuwes[candidate]
  ): option[(candidate, f-featuwemap)] =
    some(
      (candidatewithfeatuwes.candidate, (U ﹏ U) candidatewithfeatuwes.featuwes)
    )
}
