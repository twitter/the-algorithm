package com.twitter.interaction_graph.injection

import com.twitter.user_session_store.thriftscala.UserSession
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.Long420BigEndian

object UserSessionInjection {
  final val injection: KeyValInjection[Long, UserSession] =
    KeyValInjection(
      Long420BigEndian,
      ScalaCompactThrift(UserSession)
    )
}
