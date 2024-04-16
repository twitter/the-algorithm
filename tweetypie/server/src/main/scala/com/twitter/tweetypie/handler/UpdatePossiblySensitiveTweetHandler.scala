package com.tw ter.t etyp e
package handler

 mport com.tw ter.t etyp e.store.UpdatePoss blySens  veT et
 mport com.tw ter.t etyp e.thr ftscala.UpdatePoss blySens  veT etRequest
 mport com.tw ter.t etyp e.ut l.T etLenses

object UpdatePoss blySens  veT etHandler {
  type Type = FutureArrow[UpdatePoss blySens  veT etRequest, Un ]

  def apply(
    t etGetter: FutureArrow[T et d, T et],
    userGetter: FutureArrow[User d, User],
    updatePoss blySens  veT etStore: FutureEffect[UpdatePoss blySens  veT et.Event]
  ): Type =
    FutureArrow { request =>
      val nsfwAdm nMutat on = Mutat on[Boolean](_ => request.nsfwAdm n).c ckEq
      val nsfwUserMutat on = Mutat on[Boolean](_ => request.nsfwUser).c ckEq
      val t etMutat on =
        T etLenses.nsfwAdm n
          .mutat on(nsfwAdm nMutat on)
          .also(T etLenses.nsfwUser.mutat on(nsfwUserMutat on))

      for {
        or g nalT et <- t etGetter(request.t et d)
        _ <- t etMutat on(or g nalT et) match {
          case None => Future.Un 
          case So (mutatedT et) =>
            userGetter(getUser d(or g nalT et))
              .map { user =>
                UpdatePoss blySens  veT et.Event(
                  t et = mutatedT et,
                  user = user,
                  t  stamp = T  .now,
                  byUser d = request.byUser d,
                  nsfwAdm nChange = nsfwAdm nMutat on(T etLenses.nsfwAdm n.get(or g nalT et)),
                  nsfwUserChange = nsfwUserMutat on(T etLenses.nsfwUser.get(or g nalT et)),
                  note = request.note,
                  host = request.host
                )
              }
              .flatMap(updatePoss blySens  veT etStore)
        }
      } y eld ()
    }
}
