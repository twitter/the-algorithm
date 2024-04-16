package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.g zmoduck.thr ftscala.UserType
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.cl ent.User d
 mport com.tw ter.strato.conf g.FlockCursors.ByS ce.Beg n
 mport com.tw ter.strato.conf g.FlockCursors.Cont nue
 mport com.tw ter.strato.conf g.FlockCursors.End
 mport com.tw ter.strato.conf g.FlockPage
 mport com.tw ter.strato.generated.cl ent.soc algraph.serv ce.soft_users.softUserFollows.EdgeByS ceCl entColumn
 mport com.tw ter.ut l.Future

object SoftUserFollow ngStore {
  type V e rFollow ngCursor = EdgeByS ceCl entColumn.Cursor
  val MaxPagesToFetch = 2
  val PageL m  = 50
}

class SoftUserFollow ngStore(stratoCl ent: Cl ent) extends ReadableStore[User, Seq[Long]] {
   mport SoftUserFollow ngStore._
  pr vate val softUserFollow ngEdgesPag nator = new EdgeByS ceCl entColumn(stratoCl ent).pag nator

  pr vate def accumulate ds(cursor: V e rFollow ngCursor, pagesToFetch:  nt): St ch[Seq[Long]] =
    softUserFollow ngEdgesPag nator.pag nate(cursor).flatMap {
      case FlockPage(data, next, _) =>
        next match {
          case cont: Cont nue  f pagesToFetch > 1 =>
            St ch
              .jo n(
                St ch.value(data.map(_.to).map(_.value)),
                accumulate ds(cont, pagesToFetch - 1))
              .map {
                case (a, b) => a ++ b
              }

          case _: End | _: Cont nue =>
            // end pag nat on  f last page has been fetc d or [[MaxPagesToFetch]] have been fetc d
            St ch.value(data.map(_.to).map(_.value))
        }
    }

  pr vate def softFollow ngFromStrato(
    s ce d: Long,
    pageL m :  nt,
    pagesToFetch:  nt
  ): St ch[Seq[Long]] = {
    val beg n = Beg n[User d, User d](User d(s ce d), pageL m )
    accumulate ds(beg n, pagesToFetch)
  }

  overr de def get(user: User): Future[Opt on[Seq[Long]]] = {
    user.userType match {
      case UserType.Soft =>
        St ch.run(softFollow ngFromStrato(user. d, PageL m , MaxPagesToFetch)).map(Opt on(_))
      case _ => Future.None
    }
  }
}
