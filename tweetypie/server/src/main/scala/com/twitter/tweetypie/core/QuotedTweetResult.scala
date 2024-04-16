package com.tw ter.t etyp e.core

 mport com.tw ter.spam.rtf.thr ftscala.F lteredReason
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try

/**
 * T  data about a quoted t et that needs to be carr ed forward to
 * T etyp e cl ents.
 */
sealed tra  QuotedT etResult {
  def f lteredReason: Opt on[F lteredReason]
  def toOpt on: Opt on[T etResult]
  def map(f: T etResult => T etResult): QuotedT etResult
}

object QuotedT etResult {
  case object NotFound extends QuotedT etResult {
    def f lteredReason: None.type = None
    def toOpt on: None.type = None
    def map(f: T etResult => T etResult): NotFound.type = t 
  }
  case class F ltered(state: F lteredState.Unava lable) extends QuotedT etResult {
    def f lteredReason: Opt on[F lteredReason] =
      state match {
        case st: F lteredState.HasF lteredReason => So (st.f lteredReason)
        case _ => None
      }
    def toOpt on: None.type = None
    def map(f: T etResult => T etResult): F ltered = t 
  }
  case class Found(result: T etResult) extends QuotedT etResult {
    def f lteredReason: Opt on[F lteredReason] = result.value.suppress.map(_.f lteredReason)
    def toOpt on: Opt on[T etResult] = So (result)
    def map(f: T etResult => T etResult): QuotedT etResult = Found(f(result))
  }

  def fromTry(tryResult: Try[T etResult]): Try[QuotedT etResult] =
    tryResult match {
      case Return(result) => Return(Found(result))
      case Throw(state: F lteredState.Unava lable) => Return(F ltered(state))
      case Throw(com.tw ter.st ch.NotFound) => Return(NotFound)
      case Throw(e) => Throw(e)
    }
}
