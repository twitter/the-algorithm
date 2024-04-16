package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.logg ng.Logger
 mport com.tw ter.spam.rtf.thr ftscala.{SafetyLevel => Thr ftSafetyLevel}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory.V s b l yResultToF lteredState.toF lteredState
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y. nterfaces.t ets.T etV s b l yL brary
 mport com.tw ter.v s b l y. nterfaces.t ets.T etV s b l yRequest
 mport com.tw ter.v s b l y.models.SafetyLevel.DeprecatedSafetyLevel
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext

/**
 * T  repos ory handles v s b l y f lter ng of t ets
 *
 *  .e. dec d ng w t r to drop/suppress t ets based on v e r
 * and safety level for  nstance. Rules  n VF l brary can be thought as:
 *
 * (SafetyLevel)(V e r, Content, Features) => Act on
 *
 * SafetyLevel represents t  product context  n wh ch t  V e r  s
 * request ng to v ew t  Content. Example: T  l neHo , T etDeta l,
 * Recom ndat ons, Not f cat ons
 *
 * Content  re  s ma nly t ets (can be users, not f cat ons, cards etc)
 *
 * Features m ght  nclude safety labels and ot r  tadata of a T et,
 * flags set on a User ( nclud ng t  V e r), relat onsh ps bet en Users
 * (e.g. block, follow), relat onsh ps bet en Users and Content
 * (e.g. reported for spam)
 *
 *    n  al ze V s b l yL brary us ng UserS ce and UserRelat onsh pS ce:
 * St ch  nterfaces that prov de  thods to retr eve user and relat onsh p
 *  nformat on  n G zmoduck and Soc alGraph repos or es, respect vely.
 * T  user and relat onsh p  nfo along w h T et labels, prov de necessary
 * features to take a f lter ng dec s on.
 *
 * Act ons supported  n T etyp e r ght now are Drop and Suppress.
 *  n t  future,   m ght want to surface ot r granular act ons such as
 * Tombstone and Downrank wh ch are supported  n VF l b.
 *
 * T  T etV s b l yRepos ory has t  follow ng format:
 *
 * Request(T et, Opt on[SafetyLevel], Opt on[User d]) => St ch[Opt on[F lteredState]]
 *
 * SafetyLevel  s plumbed from t  t et query opt ons.
 *
 *  n add  on to t  latency stats and rpc counts from VF l brary,   also capture
 * unsupported and deprecated safety level stats  re to  nform t  relevant cl ents.
 *
 * go/v s b l yf lter ng, go/v s b l yf lter ngdocs
 *
 */
object T etV s b l yRepos ory {
  type Type = Request => St ch[Opt on[F lteredState]]

  case class Request(
    t et: T et,
    v e r d: Opt on[User d],
    safetyLevel: Thr ftSafetyLevel,
     s nnerQuotedT et: Boolean,
     sRet et: Boolean,
    hydrateConversat onControl: Boolean,
     sS ceT et: Boolean)

  def apply(
    v s b l yL brary: T etV s b l yL brary.Type,
    v s b l yDec derGates: V s b l yDec derGates,
    log: Logger,
    statsRece ver: StatsRece ver
  ): T etV s b l yRepos ory.Type = {

    val noT etRulesCounter = statsRece ver.counter("no_t et_rules_requests")
    val deprecatedScope = statsRece ver.scope("deprecated_safety_level")

    request: Request =>
      SafetyLevel.fromThr ft(request.safetyLevel) match {
        case DeprecatedSafetyLevel =>
          deprecatedScope.counter(request.safetyLevel.na .toLo rCase()). ncr()
          log.warn ng("Deprecated SafetyLevel (%s) requested".format(request.safetyLevel.na ))
          St ch.None
        case safetyLevel: SafetyLevel =>
           f (!T etV s b l yL brary.hasT etRules(safetyLevel)) {
            noT etRulesCounter. ncr()
            St ch.None
          } else {
            v s b l yL brary(
              T etV s b l yRequest(
                t et = request.t et,
                safetyLevel = safetyLevel,
                v e rContext = V e rContext.fromContextW hV e r dFallback(request.v e r d),
                 s nnerQuotedT et = request. s nnerQuotedT et,
                 sRet et = request. sRet et,
                hydrateConversat onControl = request.hydrateConversat onControl,
                 sS ceT et = request. sS ceT et
              )
            ).map(v s b l yResult =>
              toF lteredState(
                v s b l yResult = v s b l yResult,
                d sableLegacy nterst  alF lteredReason =
                  v s b l yDec derGates.d sableLegacy nterst  alF lteredReason()))
          }
      }
  }

  /**
   *   can sk p v s b l y f lter ng w n any of t  follow ng  s true:
   *
   * - SafetyLevel  s deprecated
   * - SafetyLevel has no t et rules
   */
  def canSk pV s b l yF lter ng(thr ftSafetyLevel: Thr ftSafetyLevel): Boolean =
    SafetyLevel.fromThr ft(thr ftSafetyLevel) match {
      case DeprecatedSafetyLevel =>
        true
      case safetyLevel: SafetyLevel =>
        !T etV s b l yL brary.hasT etRules(safetyLevel)
    }
}
