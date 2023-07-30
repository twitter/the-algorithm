package com.X.recos.user_video_graph

import com.X.finagle.tracing.Trace
import com.X.logging.Logger
import com.X.recos.recos_common.thriftscala._
import com.X.recos.user_video_graph.thriftscala._
import com.X.util.Future

trait LoggingUserVideoGraph extends thriftscala.UserVideoGraph.MethodPerEndpoint {
  private[this] val accessLog = Logger("access")

}
