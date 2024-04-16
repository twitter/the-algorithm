package com.tw ter.t etyp e.federated
package columns

 mport com.tw ter.bouncer.thr ftscala.Bounce
 mport com.tw ter.f nagle.http.Status
 mport com.tw ter.f natra.ap 11
 mport com.tw ter.f natra.ap 11.Ap Error
 mport com.tw ter.strato.response.Err

object Ap Errors {
  // Errs ported from StatusesRet etController
  val Gener cAccessDen edErr = toErr(Ap Error.Gener cAccessDen ed)
  val AlreadyRet etedErr = toErr(Ap Error.AlreadyRet eted)
  val Dupl cateStatusErr = toErr(Ap Error.Dupl cateStatusError)
  val  nval dRet etForStatusErr = toErr(Ap Error. nval dRet etForStatus)
  val StatusNotFoundErr = toErr(Ap Error.StatusNotFound)
  val BlockedUserErr =
    toErr(Ap Error.BlockedUserError, "ret et ng t  user's t ets at t  r request")
  val Cl entNotPr v legedErr = toErr(Ap Error.Cl entNotPr v leged)
  val UserDen edRet etErr = toErr(Ap Error.CurrentUserSuspended)

  // Errs ported from StatusesUpdateController
  val RateL m ExceededErr = toErr(Ap Error.OverStatusUpdateL m , "User")
  val T etUrlSpamErr = toErr(Ap Error.T eredAct onT etUrlSpam)
  val T etSpam rErr = toErr(Ap Error.T eredAct onT etSpam r)
  val CaptchaChallengeErr = toErr(Ap Error.T eredAct onChallengeCaptcha)
  val SafetyRateL m ExceededErr = toErr(Ap Error.UserAct onRateL m Exceeded, "User")
  val T etCannotBeBlankErr = // was M ss ngRequ redPara terExcept on
    toErr(Ap Error.Forb ddenM ss ngPara ter, "t et_text or  d a")
  val T etTextTooLongErr = toErr(Ap Error.StatusTooLongError)
  val MalwareT etErr = toErr(Ap Error.StatusMalwareError)
  val Dupl cateT etErr = toErr(Ap Error.Dupl cateStatusError)
  val CurrentUserSuspendedErr = toErr(Ap Error.CurrentUserSuspended)
  val  nt onL m ExceededErr = toErr(Ap Error. nt onL m  nT etExceeded)
  val UrlL m ExceededErr = toErr(Ap Error.UrlL m  nT etExceeded)
  val HashtagL m ExceededErr = toErr(Ap Error.HashtagL m  nT etExceeded)
  val CashtagL m ExceededErr = toErr(Ap Error.CashtagL m  nT etExceeded)
  val HashtagLengthL m ExceededErr = toErr(Ap Error.HashtagLengthL m  nT etExceeded)
  val TooManyAttach ntTypesErr = toErr(Ap Error.Attach ntTypesL m  nT etExceeded)
  val  nval dAttach ntUrlErr = toErr(Ap Error. nval dPara ter("attach nt_url"))
  val  nReplyToT etNotFoundErr = toErr(Ap Error. nReplyToT etNotFound)
  val  nval dAdd  onalF eldErr = toErr(Ap Error.Gener cBadRequest)
  def  nval dAdd  onalF eldW hReasonErr(fa lureReason: Str ng) =
    toErr(Ap Error.Gener cBadRequest.copy( ssage = fa lureReason))
  val  nval dUrlErr = toErr(Ap Error. nval dUrl)
  val  nval dCoord natesErr = toErr(Ap Error. nval dCoord nates)
  val  nval dGeoSearchRequest dErr =
    toErr(Ap Error. nval dPara ter("geo_search_request_ d"))
  val Conversat onControlNotAuthor zedErr = toErr(Ap Error.Conversat onControlNotAuthor zed)
  val Conversat onControl nval dErr = toErr(Ap Error.Conversat onControl nval d)
  val Conversat onControlReplyRestr cted = toErr(Ap Error.Conversat onControlReplyRestr cted)

  // Errors ported from StatusesDestroyController
  val DeletePerm ss onErr = toErr(Ap Error.StatusAct onPerm ss onError("delete"))

  // See StatusesUpdateController#Gener cErrorExcept on
  val Gener cT etCreateErr = toErr(Ap Error.Unknown nterpreterError, "T et creat on fa led")
  val  nval dBatchModePara terErr = toErr(Ap Error. nval dPara ter("batch_mode"))
  val CannotConvoControlAndCommun  esErr =
    toErr(Ap Error.Commun y nval dParams, "conversat on_control")
  val TooManyCommun  esErr = toErr(Ap Error.Commun y nval dParams, "commun  es")
  val Commun yReplyT etNotAllo dErr = toErr(Ap Error.Commun yReplyT etNotAllo d)
  val Conversat onControlNotSupportedErr = toErr(Ap Error.Conversat onControlNotSupported)
  val Commun yUserNotAuthor zedErr = toErr(Ap Error.Commun yUserNotAuthor zed)
  val Commun yNotFoundErr = toErr(Ap Error.Commun yNotFound)
  val Commun yProtectedUserCannotT etErr = toErr(Ap Error.Commun yProtectedUserCannotT et)

  val SuperFollowCreateNotAuthor zedErr = toErr(Ap Error.SuperFollowsCreateNotAuthor zed)
  val SuperFollow nval dParamsErr = toErr(Ap Error.SuperFollows nval dParams)
  val Exclus veT etEngage ntNotAllo dErr = toErr(Ap Error.Exclus veT etEngage ntNotAllo d)

  val SafetyLevelM ss ngErr = toErr(Ap Error.M ss ngPara ter("safety_level"))

  def accessDen edByBouncerErr(bounce: Bounce) =
    toErr(Ap Error.AccessDen edByBouncer, bounce.error ssage.getOrElse(Seq.empty))

  def t etEngage ntL m edErr(fa lureReason: Str ng) =
    toErr(Ap Error.T etEngage ntsL m ed(fa lureReason))

  def  nval d d aErr(fa lureReason: Opt on[Str ng]) =
    toErr(Ap Error. nval d d a d(fa lureReason))

  val TrustedFr ends nval dParamsErr = toErr(Ap Error.TrustedFr ends nval dParams)
  val TrustedFr endsRet etNotAllo dErr = toErr(Ap Error.TrustedFr endsRet etNotAllo d)
  val TrustedFr endsEngage ntNotAllo dErr = toErr(Ap Error.TrustedFr endsEngage ntNotAllo d)
  val TrustedFr endsCreateNotAllo dErr = toErr(Ap Error.TrustedFr endsCreateNotAllo d)
  val TrustedFr endsQuoteT etNotAllo dErr = toErr(Ap Error.TrustedFr endsQuoteT etNotAllo d)

  val StaleT etEngage ntNotAllo dErr = toErr(Ap Error.StaleT etEngage ntNotAllo d)
  val StaleT etQuoteT etNotAllo dErr = toErr(Ap Error.StaleT etQuoteT etNotAllo d)
  val StaleT etRet etNotAllo dErr = toErr(Ap Error.StaleT etRet etNotAllo d)

  val CollabT et nval dParamsErr = toErr(Ap Error.CollabT et nval dParams)

  val F eldEd NotAllo dErr = toErr(Ap Error.F eldEd NotAllo d)
  val NotEl g bleForEd Err = toErr(Ap Error.NotEl g bleForEd )

  def toErr(ap Error: ap 11.Ap Error, args: Any*): Err = {
    val errCode = ap Error.status match {
      case Status.Forb dden => Err.Author zat on
      case Status.Unauthor zed => Err.Aut nt cat on
      case Status.NotFound => Err.BadRequest
      case Status.BadRequest => Err.BadRequest
      case _ => Err.BadRequest
    }
    val err ssage = s"${ap Error. ssage.format(args.mkStr ng(","))} (${ap Error.code})"
    val errContext = So (Err.Context.Ap 11Error(ap Error.code))
    Err(errCode, err ssage, errContext)
  }
}
