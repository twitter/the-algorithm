package com.twitter.frigate.pushservice.module

import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsThriftWebFormsModule
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.frigate.pushservice.thriftscala.PushService

class ThriftWebFormsModule(server: ThriftServer)
    etwittertends MtlsThriftWebFormsModule[PushService.MethodPerEndpoint](server) {
}
