package com.tw ter.t etyp e
package handler

 mport com.tw ter.t etyp e.core.T etCreateFa lure
 mport com.tw ter.t etyp e.thr ftscala.CollabControl
 mport com.tw ter.t etyp e.thr ftscala.CollabControlOpt ons
 mport com.tw ter.t etyp e.thr ftscala.Collab nv at on
 mport com.tw ter.t etyp e.thr ftscala.Collab nv at onOpt ons
 mport com.tw ter.t etyp e.thr ftscala.Collab nv at onStatus
 mport com.tw ter.t etyp e.thr ftscala.CollabT et
 mport com.tw ter.t etyp e.thr ftscala.CollabT etOpt ons
 mport com.tw ter.t etyp e.thr ftscala.Commun  es
 mport com.tw ter.t etyp e.thr ftscala.Exclus veT etControl
 mport com.tw ter.t etyp e.thr ftscala. nv edCollaborator
 mport com.tw ter.t etyp e.thr ftscala.TrustedFr endsControl
 mport com.tw ter.t etyp e.thr ftscala.T etCreateConversat onControl
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState.CollabT et nval dParams
 mport com.tw ter.t etyp e.ut l.Commun yUt l

object CollabControlBu lder {
  type Type = Request => Future[Opt on[CollabControl]]

  case class Request(
    collabControlOpt ons: Opt on[CollabControlOpt ons],
    replyResult: Opt on[ReplyBu lder.Result],
    commun  es: Opt on[Commun  es],
    trustedFr endsControl: Opt on[TrustedFr endsControl],
    conversat onControl: Opt on[T etCreateConversat onControl],
    exclus veT etControl: Opt on[Exclus veT etControl],
    user d: User d)

  def apply(): Type = { request =>
    val collabControl = convertToCollabControl(request.collabControlOpt ons, request.user d)

    val dateCollabControlParams(
      collabControl,
      request.replyResult,
      request.commun  es,
      request.trustedFr endsControl,
      request.conversat onControl,
      request.exclus veT etControl,
      request.user d
    ) map { _ => collabControl }
  }

  def convertToCollabControl(
    collabT etOpt ons: Opt on[CollabControlOpt ons],
    author d: User d
  ): Opt on[CollabControl] = {
    collabT etOpt ons flatMap {
      case CollabControlOpt ons.Collab nv at on(
            collab nv at onOpt ons: Collab nv at onOpt ons) =>
        So (
          CollabControl.Collab nv at on(
            Collab nv at on(
               nv edCollaborators = collab nv at onOpt ons.collaboratorUser ds.map(user d => {
                 nv edCollaborator(
                  collaboratorUser d = user d,
                  collab nv at onStatus =
                     f (user d == author d)
                      Collab nv at onStatus.Accepted
                    else Collab nv at onStatus.Pend ng
                )
              })
            )
          )
        )
      case CollabControlOpt ons.CollabT et(collabT etOpt ons: CollabT etOpt ons) =>
        So (
          CollabControl.CollabT et(
            CollabT et(
              collaboratorUser ds = collabT etOpt ons.collaboratorUser ds
            )
          )
        )
      case _ => None
    }
  }

  def val dateCollabControlParams(
    collabControl: Opt on[CollabControl],
    replyResult: Opt on[ReplyBu lder.Result],
    commun  es: Opt on[Commun  es],
    trustedFr endsControl: Opt on[TrustedFr endsControl],
    conversat onControl: Opt on[T etCreateConversat onControl],
    exclus veT etControl: Opt on[Exclus veT etControl],
    user d: User d
  ): Future[Un ] = {
    val  s nReplyToT et = replyResult.ex sts(_.reply. nReplyToStatus d. sDef ned)

    collabControl match {
      case So (_: CollabControl)
           f ( s nReplyToT et ||
            Commun yUt l.hasCommun y(commun  es) ||
            exclus veT etControl. sDef ned ||
            trustedFr endsControl. sDef ned ||
            conversat onControl. sDef ned) =>
        Future.except on(T etCreateFa lure.State(CollabT et nval dParams))
      case So (CollabControl.Collab nv at on(collab_ nv at on))
           f collab_ nv at on. nv edCollaborators. ad.collaboratorUser d != user d =>
        Future.except on(T etCreateFa lure.State(CollabT et nval dParams))
      case So (CollabControl.CollabT et(collab_t et))
           f collab_t et.collaboratorUser ds. ad != user d =>
        Future.except on(T etCreateFa lure.State(CollabT et nval dParams))
      case _ =>
        Future.Un 
    }
  }
}
