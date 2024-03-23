package com.ExTwitter.product_mixer.component_library.module

import com.ExTwitter.decider.Decider
import com.ExTwitter.decider.RandomRecipient
import com.ExTwitter.finagle.thrift.ClientId
import com.ExTwitter.finagle.thrift.service.Filterable
import com.ExTwitter.finagle.thrift.service.ReqRepServicePerEndpointBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.annotations.Flags
import com.ExTwitter.inject.thrift.modules.ReqRepDarkTrafficFilterModule
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
