package com.twittew.pwoduct_mixew.cowe.functionaw_component.common.awewt

impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.common.awewt.pwedicate.thwoughputpwedicate

/**
 * [[thwoughputawewt]] t-twiggews w-when the wequests/sec f-fow the c-component this i-is used
 * with i-is outside of the p-pwedicate set by a [[thwoughputpwedicate]] fow
 * the configuwed amount of time
 */
c-case cwass thwoughputawewt(
  ovewwide vaw n-nyotificationgwoup: nyotificationgwoup, mya
  o-ovewwide vaw wawnpwedicate: thwoughputpwedicate, nyaa~~
  ovewwide vaw cwiticawpwedicate: thwoughputpwedicate, (⑅˘꒳˘)
  o-ovewwide vaw wunbookwink: o-option[stwing] = n-none)
    extends awewt
    with isobsewvabwefwomstwato {
  ovewwide vaw awewttype: a-awewttype = thwoughput
  wequiwe(
    wawnpwedicate.thweshowd >= 0, rawr x3
    s"thwoughputawewt pwedicates must be >= 0 b-but got wawnpwedicate = ${wawnpwedicate.thweshowd}")
  wequiwe(
    c-cwiticawpwedicate.thweshowd >= 0, (✿oωo)
    s-s"thwoughputawewt p-pwedicates must b-be >= 0 but got cwiticawpwedicate = ${cwiticawpwedicate.thweshowd}")
}
