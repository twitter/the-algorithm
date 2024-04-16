package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.spam.rtf.thr ftscala.{SafetyLevel => Thr ftSafetyLevel}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory.V s b l yResultToF lteredState.toF lteredState
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y. nterfaces.t ets.QuotedT etV s b l yL brary
 mport com.tw ter.v s b l y. nterfaces.t ets.QuotedT etV s b l yRequest
 mport com.tw ter.v s b l y. nterfaces.t ets.T etAndAuthor
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext

/**
 * T  repos ory handles v s b l y f lter ng of  nner quoted t ets
 * based on relat onsh ps bet en t   nner and outer t ets. T   s
 * add  ve to  ndependent v s b l y f lter ng of t   nner t et.
 */
object QuotedT etV s b l yRepos ory {
  type Type = Request => St ch[Opt on[F lteredState]]

  case class Request(
    outerT et d: T et d,
    outerAuthor d: User d,
     nnerT et d: T et d,
     nnerAuthor d: User d,
    v e r d: Opt on[User d],
    safetyLevel: Thr ftSafetyLevel)

  def apply(
    quotedT etV s b l yL brary: QuotedT etV s b l yL brary.Type,
    v s b l yDec derGates: V s b l yDec derGates,
  ): QuotedT etV s b l yRepos ory.Type = { request: Request =>
    quotedT etV s b l yL brary(
      QuotedT etV s b l yRequest(
        quotedT et = T etAndAuthor(request. nnerT et d, request. nnerAuthor d),
        outerT et = T etAndAuthor(request.outerT et d, request.outerAuthor d),
        V e rContext.fromContextW hV e r dFallback(request.v e r d),
        safetyLevel = SafetyLevel.fromThr ft(request.safetyLevel)
      )
    ).map(v s b l yResult =>
      toF lteredState(
        v s b l yResult = v s b l yResult,
        d sableLegacy nterst  alF lteredReason =
          v s b l yDec derGates.d sableLegacy nterst  alF lteredReason()))
  }
}
