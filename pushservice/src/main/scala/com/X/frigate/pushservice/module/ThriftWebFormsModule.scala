package com.X.frigate.pushservice.module

import com.X.finatra.mtls.thriftmux.modules.MtlsThriftWebFormsModule
import com.X.finatra.thrift.ThriftServer
import com.X.frigate.pushservice.thriftscala.PushService

class ThriftWebFormsModule(server: ThriftServer)
    extends MtlsThriftWebFormsModule[PushService.MethodPerEndpoint](server) {
}
