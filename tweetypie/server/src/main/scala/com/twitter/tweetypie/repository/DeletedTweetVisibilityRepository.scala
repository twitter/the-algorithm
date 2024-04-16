package com.tw ter.t etyp e.repos ory

 mport com.tw ter.spam.rtf.thr ftscala.F lteredReason
 mport com.tw ter.spam.rtf.thr ftscala.{SafetyLevel => Thr ftSafetyLevel}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.T et d
 mport com.tw ter.t etyp e.core.F lteredState.HasF lteredReason
 mport com.tw ter.t etyp e.core.F lteredState.Unava lable.BounceDeleted
 mport com.tw ter.t etyp e.core.F lteredState.Unava lable.S ceT etNotFound
 mport com.tw ter.t etyp e.core.F lteredState.Unava lable.T etDeleted
 mport com.tw ter.t etyp e.repos ory.V s b l yResultToF lteredState.toF lteredStateUnava lable
 mport com.tw ter.v s b l y. nterfaces.t ets.DeletedT etV s b l yL brary
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.T etDeleteReason
 mport com.tw ter.v s b l y.models.T etDeleteReason.T etDeleteReason
 mport com.tw ter.v s b l y.models.V e rContext

/**
 *  Generate F lteredReason for t et ent  es  n follow ng delete states:
 *  com.tw ter.t etyp e.core.F lteredState.Unava lable
 *    - S ceT etNotFound(true)
 *    - T etDeleted
 *    - BounceDeleted
 *
 *  Callers of t  repos ory should be ready to handle empty response (St ch.None)
 *  from t  underly ng VF l brary w n:
 *  1.t  t et should not NOT be f ltered for t  g ven safety level
 *  2.t  t et  s not a relevant content to be handled by t  l brary
 */
object DeletedT etV s b l yRepos ory {
  type Type = V s b l yRequest => St ch[Opt on[F lteredReason]]

  case class V s b l yRequest(
    f lteredState: Throwable,
    t et d: T et d,
    safetyLevel: Opt on[Thr ftSafetyLevel],
    v e r d: Opt on[Long],
     s nnerQuotedT et: Boolean)

  def apply(
    v s b l yL brary: DeletedT etV s b l yL brary.Type
  ): Type = { request =>
    toV s b l yT etDeleteState(request.f lteredState, request. s nnerQuotedT et)
      .map { deleteReason =>
        val safetyLevel = SafetyLevel.fromThr ft(
          request.safetyLevel.getOrElse(Thr ftSafetyLevel.F lterDefault)
        )
        val  sRet et = request.f lteredState == S ceT etNotFound(true)
        v s b l yL brary(
          DeletedT etV s b l yL brary.Request(
            request.t et d,
            safetyLevel,
            V e rContext.fromContextW hV e r dFallback(request.v e r d),
            deleteReason,
             sRet et,
            request. s nnerQuotedT et
          )
        ).map(toF lteredStateUnava lable)
          .map {
            //Accept F lteredReason
            case So (fs)  f fs. s nstanceOf[HasF lteredReason] =>
              So (fs.as nstanceOf[HasF lteredReason].f lteredReason)
            case _ => None
          }
      }
      .getOrElse(St ch.None)
  }

  /**
   * @return map an error from t et hydrat on to a VF model T etDeleteReason,
   *         None w n t  error  s not related to delete state t ets.
   */
  pr vate def toV s b l yT etDeleteState(
    t etDeleteState: Throwable,
     s nnerQuotedT et: Boolean
  ): Opt on[T etDeleteReason] = {
    t etDeleteState match {
      case T etDeleted => So (T etDeleteReason.Deleted)
      case BounceDeleted => So (T etDeleteReason.BounceDeleted)
      case S ceT etNotFound(true)  f ! s nnerQuotedT et => So (T etDeleteReason.Deleted)
      case _ => None
    }
  }
}
