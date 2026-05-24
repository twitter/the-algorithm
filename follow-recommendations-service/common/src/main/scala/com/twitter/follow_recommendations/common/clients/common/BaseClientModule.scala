package com.twitter.follow_recommendations.common.clients.common

import com.twitter.finagle.ThriftMux
import com.twitter.finagle.thrift.Protocols
import com.twitter.follow_recommendations.common.constants.ServiceConstants._
import com.twitter.inject.thrift.modules.ThriftClientModule
import scala.reflect.ClassTag

/**
 * basic client configurations that we apply for all of our clients go in here
 */
abstract class BaseClientModule[T: ClassTag] extends ThriftClientModule[T] {
  def configureThriftMuxClient(client: ThriftMux.Client): ThriftMux.Client = {
    client
      .withProtocolFactory(
        Protocols.binaryFactory(
          stringLengthLimit = StringLengthLimit,
          containerLengthLimit = ContainerLengthLimit))
  }
}
