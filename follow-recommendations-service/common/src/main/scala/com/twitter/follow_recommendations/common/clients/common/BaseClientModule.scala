package com.twitter.follow_recommendations.common.clients.common

import com.twitter.finagle.ThriftMutwitter
import com.twitter.finagle.thrift.Protocols
import com.twitter.follow_recommendations.common.constants.ServiceConstants._
import com.twitter.inject.thrift.modules.ThriftClientModule
import scala.reflect.ClassTag

/**
 * basic client configurations that we apply for all of our clients go in here
 */
abstract class BaseClientModule[T: ClassTag] etwittertends ThriftClientModule[T] {
  def configureThriftMutwitterClient(client: ThriftMutwitter.Client): ThriftMutwitter.Client = {
    client
      .withProtocolFactory(
        Protocols.binaryFactory(
          stringLengthLimit = StringLengthLimit,
          containerLengthLimit = ContainerLengthLimit))
  }
}
