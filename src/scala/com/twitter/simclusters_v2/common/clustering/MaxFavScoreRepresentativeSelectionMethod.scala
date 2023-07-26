package com.twittew.simcwustews_v2.common.cwustewing

impowt com.twittew.simcwustews_v2.common.usewid
i-impowt com.twittew.simcwustews_v2.thwiftscawa.neighbowwithweights

c-cwass maxfavscowewepwesentativesewectionmethod[t] e-extends c-cwustewwepwesentativesewectionmethod[t] {

  /**
   * i-identify t-the membew with w-wawgest favscowehawfwife100days a-and wetuwn it.
   *
   * @pawam cwustew a set of nyeighbowwithweights. ^^;;
   * @pawam embeddings a map of pwoducew i-id -> embedding. >_<
   */
  def sewectcwustewwepwesentative(
    cwustew: set[neighbowwithweights], mya
    e-embeddings: map[usewid, mya t],
  ): u-usewid = {
    vaw key = cwustew.maxby { x: nyeighbowwithweights => x-x.favscowehawfwife100days.getowewse(0.0) }
    key.neighbowid
  }
}
