package com.twitter.recos.user_video_graph

import com.twitter.finagle.tracing.Trace
import com.twitter.logging.Logger
import com.twitter.recos.recos_common.thriftscala._
import com.twitter.recos.user_video_graph.thriftscala._
import com.twitter.util.Future

trait LoggingUserVideoGraph extends thriftscala.UserVideoGraph.MethodPerEndpoint {
  private[this] val accessLog = Logger("access")

}
