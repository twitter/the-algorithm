package com.X.interaction_graph.injection

import com.X.user_session_store.thriftscala.UserSession
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.Long2BigEndian

object UserSessionInjection {
  final val injection: KeyValInjection[Long, UserSession] =
    KeyValInjection(
      Long2BigEndian,
      ScalaCompactThrift(UserSession)
    )
}
