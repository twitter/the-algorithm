package com.tw ter.ho _m xer.model.request

 mport com.tw ter.dspb dder.commons.thr ftscala.DspCl entContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.ProductContext

case class Follow ngProductContext(
  dev ceContext: Opt on[Dev ceContext],
  seenT et ds: Opt on[Seq[Long]],
  dspCl entContext: Opt on[DspCl entContext])
    extends ProductContext

case class For ProductContext(
  dev ceContext: Opt on[Dev ceContext],
  seenT et ds: Opt on[Seq[Long]],
  dspCl entContext: Opt on[DspCl entContext],
  pushToHo T et d: Opt on[Long])
    extends ProductContext

case class ScoredT etsProductContext(
  dev ceContext: Opt on[Dev ceContext],
  seenT et ds: Opt on[Seq[Long]],
  servedT et ds: Opt on[Seq[Long]],
  backf llT et ds: Opt on[Seq[Long]])
    extends ProductContext

case class L stT etsProductContext(
  l st d: Long,
  dev ceContext: Opt on[Dev ceContext],
  dspCl entContext: Opt on[DspCl entContext])
    extends ProductContext

case class L stRecom ndedUsersProductContext(
  l st d: Long,
  selectedUser ds: Opt on[Seq[Long]],
  excludedUser ds: Opt on[Seq[Long]],
  l stNa : Opt on[Str ng])
    extends ProductContext

case class Subscr bedProductContext(
  dev ceContext: Opt on[Dev ceContext],
  seenT et ds: Opt on[Seq[Long]])
    extends ProductContext
