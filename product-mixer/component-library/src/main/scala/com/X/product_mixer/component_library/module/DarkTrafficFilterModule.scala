package com.X.product_mixer.component_library.module

import com.X.decider.Decider
import com.X.decider.RandomRecipient
import com.X.finagle.thrift.ClientId
import com.X.finagle.thrift.service.Filterable
import com.X.finagle.thrift.service.ReqRepServicePerEndpointBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.annotations.Flags
import com.X.inject.thrift.modules.ReqRepDarkTrafficFilterModule
import scala.reflect.ClassTag

class DarkTrafficFilterModule[MethodIface <: Filterable[MethodIface]: ClassTag](
  implicit serviceBuilder: ReqRepServicePerEndpointBuilder[MethodIface])
    extends ReqRepDarkTrafficFilterModule
    with MtlsClient {

  override protected def enableSampling(injector: Injector): Any => Boolean = _ => {
    val decider = injector.instance[Decider]
    val deciderKey =
      injector.instance[String](Flags.named("thrift.dark.traffic.filter.decider_key"))
    val fromProxy = ClientId.current
      .map(_.name).exists(name => name.contains("diffy") || name.contains("darktraffic"))
    !fromProxy && decider.isAvailable(deciderKey, recipient = Some(RandomRecipient))
  }
}
