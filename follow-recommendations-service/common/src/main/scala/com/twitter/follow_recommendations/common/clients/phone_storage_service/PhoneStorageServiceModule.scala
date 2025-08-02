package com.twitter.follow_recommendations.common.clients.phone_storage_service

import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.follow_recommendations.common.clients.common.BaseClientModule
import com.twitter.phonestorage.api.thriftscala.PhoneStorageService

object PhoneStorageServiceModule
    etwittertends BaseClientModule[PhoneStorageService.MethodPerEndpoint]
    with MtlsClient {
  override val label = "phone-storage-service"
  override val dest = "/s/ibis-ds-api/ibis-ds-api:thrift2"
}
