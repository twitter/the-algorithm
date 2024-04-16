package com.tw ter.t etyp e.repos ory

 mport com.tw ter.spam.rtf.thr ftscala.F lteredReason
 mport com.tw ter.spam.rtf.thr ftscala.KeywordMatch
 mport com.tw ter.spam.rtf.thr ftscala.SafetyResult
 mport com.tw ter.t etyp e.core.F lteredState
 mport com.tw ter.t etyp e.core.F lteredState.Suppress
 mport com.tw ter.t etyp e.core.F lteredState.Unava lable
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.common.user_result.UserV s b l yResult lper
 mport com.tw ter.v s b l y.rules.Reason._
 mport com.tw ter.v s b l y.rules._
 mport com.tw ter.v s b l y.{thr ftscala => vfthr ft}

object V s b l yResultToF lteredState {
  def toF lteredStateUnava lable(
    v s b l yResult: V s b l yResult
  ): Opt on[F lteredState.Unava lable] = {
    val dropSafetyResult = So (
      Unava lable.Drop(F lteredReason.SafetyResult(v s b l yResult.getSafetyResult))
    )

    v s b l yResult.verd ct match {
      case Drop(Exclus veT et, _) =>
        dropSafetyResult

      case Drop(NsfwV e r sUnderage | NsfwV e rHasNoStatedAge | NsfwLoggedOut, _) =>
        dropSafetyResult

      case Drop(TrustedFr endsT et, _) =>
        dropSafetyResult

      case _: Local zedTombstone => dropSafetyResult

      case Drop(StaleT et, _) => dropSafetyResult

      // legacy drop act ons
      case dropAct on: Drop => unava lableFromDropAct on(dropAct on)

      // not an unava lable state that can be mapped
      case _ => None
    }
  }

  def toF lteredState(
    v s b l yResult: V s b l yResult,
    d sableLegacy nterst  alF lteredReason: Boolean
  ): Opt on[F lteredState] = {
    val suppressSafetyResult = So (
      Suppress(F lteredReason.SafetyResult(v s b l yResult.getSafetyResult))
    )
    val dropSafetyResult = So (
      Unava lable.Drop(F lteredReason.SafetyResult(v s b l yResult.getSafetyResult))
    )

    v s b l yResult.verd ct match {
      case _: Appealable => suppressSafetyResult

      case _: Prev ew => suppressSafetyResult

      case _:  nterst  alL m edEngage nts => suppressSafetyResult

      case _: E rgencyDynam c nterst  al => suppressSafetyResult

      case _: Soft ntervent on => suppressSafetyResult

      case _: L m edEngage nts => suppressSafetyResult

      case _: T et nterst  al => suppressSafetyResult

      case _: T etV s b l yNudge => suppressSafetyResult

      case  nterst  al(
            V e rBlocksAuthor | V e rReportedAuthor | V e rReportedT et | V e rMutesAuthor |
            V e rHardMutedAuthor | MutedKeyword |  nterst  alDevelop ntOnly | HatefulConduct |
            Abus veBehav or,
            _,
            _)  f d sableLegacy nterst  alF lteredReason =>
        suppressSafetyResult

      case  nterst  al(
            V e rBlocksAuthor | V e rReportedAuthor | V e rReportedT et |
             nterst  alDevelop ntOnly,
            _,
            _) =>
        suppressSafetyResult

      case _: Compl anceT etNot ce => suppressSafetyResult

      case Drop(Exclus veT et, _) =>
        dropSafetyResult

      case Drop(NsfwV e r sUnderage | NsfwV e rHasNoStatedAge | NsfwLoggedOut, _) =>
        dropSafetyResult

      case Drop(TrustedFr endsT et, _) =>
        dropSafetyResult

      case Drop(StaleT et, _) => dropSafetyResult

      case _: Local zedTombstone => dropSafetyResult

      case _: Avo d => suppressSafetyResult

      // legacy drop act ons
      case dropAct on: Drop => unava lableFromDropAct on(dropAct on)

      // legacy suppress act ons
      case act on => suppressFromV s b l yAct on(act on, !d sableLegacy nterst  alF lteredReason)
    }
  }

  def toF lteredState(
    userV s b l yResult: Opt on[vfthr ft.UserV s b l yResult]
  ): F lteredState.Unava lable =
    userV s b l yResult
      .collect {
        case blockedUser  f UserV s b l yResult lper. sDropAuthorBlocksV e r(blockedUser) =>
          Unava lable.Drop(F lteredReason.AuthorBlockV e r(true))

        /**
         * Reuse states for author v s b l y  ssues from t  [[UserRepos ory]] for cons stency w h
         * ot r log c for handl ng t  sa  types of author v s b l y f lter ng.
         */
        case protectedUser  f UserV s b l yResult lper. sDropProtectedAuthor(protectedUser) =>
          Unava lable.Author.Protected
        case suspendedUser  f UserV s b l yResult lper. sDropSuspendedAuthor(suspendedUser) =>
          Unava lable.Author.Suspended
        case nsfwUser  f UserV s b l yResult lper. sDropNsfwAuthor(nsfwUser) =>
          Unava lable.Drop(F lteredReason.Conta nNsfw d a(true))
        case mutedByV e r  f UserV s b l yResult lper. sDropV e rMutesAuthor(mutedByV e r) =>
          Unava lable.Drop(F lteredReason.V e rMutesAuthor(true))
        case blockedByV e r
             f UserV s b l yResult lper. sDropV e rBlocksAuthor(blockedByV e r) =>
          Unava lable.Drop(
            F lteredReason.SafetyResult(
              SafetyResult(
                None,
                vfthr ft.Act on.Drop(
                  vfthr ft.Drop(So (vfthr ft.DropReason.V e rBlocksAuthor(true)))
                ))))
      }
      .getOrElse(F lteredState.Unava lable.Drop(F lteredReason.Unspec f edReason(true)))

  pr vate def unava lableFromDropAct on(dropAct on: Drop): Opt on[F lteredState.Unava lable] =
    dropAct on match {
      case Drop(AuthorBlocksV e r, _) =>
        So (Unava lable.Drop(F lteredReason.AuthorBlockV e r(true)))
      case Drop(Unspec f ed, _) =>
        So (Unava lable.Drop(F lteredReason.Unspec f edReason(true)))
      case Drop(MutedKeyword, _) =>
        So (Unava lable.Drop(F lteredReason.T etMatc sV e rMutedKeyword(KeywordMatch(""))))
      case Drop(V e rMutesAuthor, _) =>
        So (Unava lable.Drop(F lteredReason.V e rMutesAuthor(true)))
      case Drop(Nsfw, _) =>
        So (Unava lable.Drop(F lteredReason.Conta nNsfw d a(true)))
      case Drop(Nsfw d a, _) =>
        So (Unava lable.Drop(F lteredReason.Conta nNsfw d a(true)))
      case Drop(Poss blyUndes rable, _) =>
        So (Unava lable.Drop(F lteredReason.Poss blyUndes rable(true)))
      case Drop(Bounce, _) =>
        So (Unava lable.Drop(F lteredReason.T et sBounced(true)))

      /**
       * Reuse states for author v s b l y  ssues from t  [[UserRepos ory]] for cons stency w h
       * ot r log c for handl ng t  sa  types of author v s b l y f lter ng.
       */
      case Drop(ProtectedAuthor, _) =>
        So (Unava lable.Author.Protected)
      case Drop(SuspendedAuthor, _) =>
        So (Unava lable.Author.Suspended)
      case Drop(OffboardedAuthor, _) =>
        So (Unava lable.Author.Offboarded)
      case Drop(Deact vatedAuthor, _) =>
        So (Unava lable.Author.Deact vated)
      case Drop(ErasedAuthor, _) =>
        So (Unava lable.Author.Deact vated)
      case _: Drop =>
        So (Unava lable.Drop(F lteredReason.Unspec f edReason(true)))
    }

  pr vate def suppressFromV s b l yAct on(
    act on: Act on,
    enableLegacyF lteredReason: Boolean
  ): Opt on[F lteredState.Suppress] =
    act on match {
      case  nterst  al:  nterst  al =>
         nterst  al.reason match {
          case MutedKeyword  f enableLegacyF lteredReason =>
            So (Suppress(F lteredReason.T etMatc sV e rMutedKeyword(KeywordMatch(""))))
          case V e rMutesAuthor  f enableLegacyF lteredReason =>
            So (Suppress(F lteredReason.V e rMutesAuthor(true)))
          case V e rHardMutedAuthor  f enableLegacyF lteredReason =>
            So (Suppress(F lteredReason.V e rMutesAuthor(true)))
          //  nterst  al t ets are cons dered suppressed by T etyp e. For
          // legacy behav or reasons, t se t ets should be dropped w n
          // appear ng as a quoted t et v a a call to getT ets.
          case Nsfw =>
            So (Suppress(F lteredReason.Conta nNsfw d a(true)))
          case Nsfw d a =>
            So (Suppress(F lteredReason.Conta nNsfw d a(true)))
          case Poss blyUndes rable =>
            So (Suppress(F lteredReason.Poss blyUndes rable(true)))
          case _ =>
            So (Suppress(F lteredReason.Poss blyUndes rable(true)))
        }
      case _ => None
    }
}
