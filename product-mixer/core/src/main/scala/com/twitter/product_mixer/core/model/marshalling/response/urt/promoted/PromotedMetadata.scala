package com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted

/*
 * As per d scuss on w h #revenue-serv ng on 9/22/2017, ` mpress on d` should be set from ` mpress onStr ng`.
 *  mpress on d often returns None from adserver, as  's been replaced w h  mpress onStr ng.
 *
 * Ho ver, Andro d (at least) cras s w hout  mpress on d f lled out  n t  response.
 *
 * So,  've removed ` mpress on d` from t  case class, and   marshaller w ll set both ` mpress on d`
 * and ` mpress onStr ng`  n t  render thr ft from ` mpress onStr ng`.
 */

case class Promoted tadata(
  advert ser d: Long,
  d sclosureType: Opt on[D sclosureType],
  exper  ntValues: Opt on[Map[Str ng, Str ng]],
  promotedTrend d: Opt on[Long],
  promotedTrendNa : Opt on[Str ng],
  promotedTrendQueryTerm: Opt on[Str ng],
  ad tadataConta ner: Opt on[Ad tadataConta ner],
  promotedTrendDescr pt on: Opt on[Str ng],
   mpress onStr ng: Opt on[Str ng],
  cl ckTrack ng nfo: Opt on[Cl ckTrack ng nfo])
