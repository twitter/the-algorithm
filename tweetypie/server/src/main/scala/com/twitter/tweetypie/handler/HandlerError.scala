package com.tw ter.t etyp e
package handler

 mport com.tw ter.servo.except on.thr ftscala.Cl entError
 mport com.tw ter.servo.except on.thr ftscala.Cl entErrorCause
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.F lteredState.Unava lable._

pr vate[t etyp e] object HandlerError {

  def translateNotFoundToCl entError[U](t et d: T et d): Part alFunct on[Throwable, St ch[U]] = {
    case NotFound =>
      St ch.except on(HandlerError.t etNotFound(t et d))
    case T etDeleted | BounceDeleted =>
      St ch.except on(HandlerError.t etNotFound(t et d, true))
    case S ceT etNotFound(deleted) =>
      St ch.except on(HandlerError.t etNotFound(t et d, deleted))
  }

  def t etNotFound(t et d: T et d, deleted: Boolean = false): Cl entError =
    Cl entError(
      Cl entErrorCause.BadRequest,
      s"t et ${ f (deleted) "deleted" else "not found"}: $t et d"
    )

  def userNotFound(user d: User d): Cl entError =
    Cl entError(Cl entErrorCause.BadRequest, s"user not found: $user d")

  def t etNotFoundExcept on(t et d: T et d): Future[Noth ng] =
    Future.except on(t etNotFound(t et d))

  def userNotFoundExcept on(user d: User d): Future[Noth ng] =
    Future.except on(userNotFound(user d))

  def getRequ red[A, B](
    opt onFutureArrow: FutureArrow[A, Opt on[B]],
    notFound: A => Future[B]
  ): FutureArrow[A, B] =
    FutureArrow(key =>
      opt onFutureArrow(key).flatMap {
        case So (x) => Future.value(x)
        case None => notFound(key)
      })
}
