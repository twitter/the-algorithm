package com.twittew.pwoduct_mixew.cowe.utiw

impowt c-com.twittew.snowfwake.id.snowfwakeid
i-impowt com.twittew.utiw.time

o-object sowtindexbuiwdew {

  /** t-the [[time]] f-fwom a [[snowfwakeid]] */
  d-def idtotime(id: w-wong): time =
    t-time.fwommiwwiseconds(snowfwakeid.unixtimemiwwisowfwoowfwomid(id))

  /** the fiwst [[snowfwakeid]] possibwe fow a given [[time]]  */
  d-def timetoid(time: time): wong = snowfwakeid.fiwstidfow(time)

  /** t-the fiwst [[snowfwakeid]] possibwe f-fow a given unix epoch miwwis  */
  def timetoid(timemiwwis: wong): wong = snowfwakeid.fiwstidfow(timemiwwis)
}
