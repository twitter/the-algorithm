package com.X.unified_user_actions.adapter.user_modification

import com.X.finagle.stats.NullStatsReceiver
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.kafka.serde.UnKeyed
import com.X.gizmoduck.thriftscala.UserModification
import com.X.unified_user_actions.adapter.AbstractAdapter
import com.X.unified_user_actions.adapter.user_modification_event.UserCreate
import com.X.unified_user_actions.adapter.user_modification_event.UserUpdate
import com.X.unified_user_actions.thriftscala.UnifiedUserAction

class UserModificationAdapter
    extends AbstractAdapter[UserModification, UnKeyed, UnifiedUserAction] {

  import UserModificationAdapter._

  override def adaptOneToKeyedMany(
    input: UserModification,
    statsReceiver: StatsReceiver = NullStatsReceiver
  ): Seq[(UnKeyed, UnifiedUserAction)] =
    adaptEvent(input).map { e => (UnKeyed, e) }
}

object UserModificationAdapter {

  def adaptEvent(input: UserModification): Seq[UnifiedUserAction] =
    Option(input).toSeq.flatMap { e =>
      if (e.create.isDefined) { // User create
        Some(UserCreate.getUUA(input))
      } else if (e.update.isDefined) { // User updates
        Some(UserUpdate.getUUA(input))
      } else if (e.destroy.isDefined) {
        None
      } else if (e.erase.isDefined) {
        None
      } else {
        throw new IllegalArgumentException(
          "None of the possible events is defined, there must be something with the source")
      }
    }
}
