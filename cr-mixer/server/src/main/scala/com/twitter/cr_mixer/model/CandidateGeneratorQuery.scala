package com.tw ter.cr_m xer.model

 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.cr_m xer.thr ftscala.Product
 mport com.tw ter.product_m xer.core.thr ftscala.Cl entContext
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.Top c d
 mport com.tw ter.t  l nes.conf gap .Params

sealed tra  Cand dateGeneratorQuery {
  val product: Product
  val maxNumResults:  nt
  val  mpressedT etL st: Set[T et d]
  val params: Params
  val requestUU D: Long
}

sealed tra  HasUser d {
  val user d: User d
}

case class CrCand dateGeneratorQuery(
  user d: User d,
  product: Product,
  userState: UserState,
  maxNumResults:  nt,
   mpressedT etL st: Set[T et d],
  params: Params,
  requestUU D: Long,
  languageCode: Opt on[Str ng] = None)
    extends Cand dateGeneratorQuery
    w h HasUser d

case class UtegT etCand dateGeneratorQuery(
  user d: User d,
  product: Product,
  userState: UserState,
  maxNumResults:  nt,
   mpressedT etL st: Set[T et d],
  params: Params,
  requestUU D: Long)
    extends Cand dateGeneratorQuery
    w h HasUser d

case class RelatedT etCand dateGeneratorQuery(
   nternal d:  nternal d,
  cl entContext: Cl entContext, // To scr be Log n/LogOut requests
  product: Product,
  maxNumResults:  nt,
   mpressedT etL st: Set[T et d],
  params: Params,
  requestUU D: Long)
    extends Cand dateGeneratorQuery

case class RelatedV deoT etCand dateGeneratorQuery(
   nternal d:  nternal d,
  cl entContext: Cl entContext, // To scr be Log n/LogOut requests
  product: Product,
  maxNumResults:  nt,
   mpressedT etL st: Set[T et d],
  params: Params,
  requestUU D: Long)
    extends Cand dateGeneratorQuery

case class FrsT etCand dateGeneratorQuery(
  user d: User d,
  product: Product,
  maxNumResults:  nt,
   mpressedUserL st: Set[User d],
   mpressedT etL st: Set[T et d],
  params: Params,
  languageCodeOpt: Opt on[Str ng] = None,
  countryCodeOpt: Opt on[Str ng] = None,
  requestUU D: Long)
    extends Cand dateGeneratorQuery

case class AdsCand dateGeneratorQuery(
  user d: User d,
  product: Product,
  userState: UserState,
  maxNumResults:  nt,
  params: Params,
  requestUU D: Long)

case class Top cT etCand dateGeneratorQuery(
  user d: User d,
  top c ds: Set[Top c d],
  product: Product,
  maxNumResults:  nt,
   mpressedT etL st: Set[T et d],
  params: Params,
  requestUU D: Long,
   sV deoOnly: Boolean)
    extends Cand dateGeneratorQuery
