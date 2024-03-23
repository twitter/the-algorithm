package com.ExTwitter.follow_recommendations.common.clients.common

import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finagle.thrift.Protocols
import com.ExTwitter.follow_recommendations.common.constants.ServiceConstants._
import com.ExTwitter.inject.thrift.modules.ThriftClientModule
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
