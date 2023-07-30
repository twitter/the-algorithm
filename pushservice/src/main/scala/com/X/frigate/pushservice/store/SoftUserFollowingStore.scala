package com.X.frigate.pushservice.store

import com.X.gizmoduck.thriftscala.User
import com.X.gizmoduck.thriftscala.UserType
import com.X.stitch.Stitch
import com.X.storehaus.ReadableStore
import com.X.strato.client.Client
import com.X.strato.client.UserId
import com.X.strato.config.FlockCursors.BySource.Begin
import com.X.strato.config.FlockCursors.Continue
import com.X.strato.config.FlockCursors.End
import com.X.strato.config.FlockPage
import com.X.strato.generated.client.socialgraph.service.soft_users.softUserFollows.EdgeBySourceClientColumn
import com.X.util.Future

object SoftUserFollowingStore {
  type ViewerFollowingCursor = EdgeBySourceClientColumn.Cursor
  val MaxPagesToFetch = 2
  val PageLimit = 50
}

class SoftUserFollowingStore(stratoClient: Client) extends ReadableStore[User, Seq[Long]] {
  import SoftUserFollowingStore._
  private val softUserFollowingEdgesPaginator = new EdgeBySourceClientColumn(stratoClient).paginator

  private def accumulateIds(cursor: ViewerFollowingCursor, pagesToFetch: Int): Stitch[Seq[Long]] =
    softUserFollowingEdgesPaginator.paginate(cursor).flatMap {
      case FlockPage(data, next, _) =>
        next match {
          case cont: Continue if pagesToFetch > 1 =>
            Stitch
              .join(
                Stitch.value(data.map(_.to).map(_.value)),
                accumulateIds(cont, pagesToFetch - 1))
              .map {
                case (a, b) => a ++ b
              }

          case _: End | _: Continue =>
            // end pagination if last page has been fetched or [[MaxPagesToFetch]] have been fetched
            Stitch.value(data.map(_.to).map(_.value))
        }
    }

  private def softFollowingFromStrato(
    sourceId: Long,
    pageLimit: Int,
    pagesToFetch: Int
  ): Stitch[Seq[Long]] = {
    val begin = Begin[UserId, UserId](UserId(sourceId), pageLimit)
    accumulateIds(begin, pagesToFetch)
  }

  override def get(user: User): Future[Option[Seq[Long]]] = {
    user.userType match {
      case UserType.Soft =>
        Stitch.run(softFollowingFromStrato(user.id, PageLimit, MaxPagesToFetch)).map(Option(_))
      case _ => Future.None
    }
  }
}
