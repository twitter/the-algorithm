package com.ExTwitter.follow_recommendations.common.clients.phone_storage_service

import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.follow_recommendations.common.clients.common.BaseClientModule
import com.ExTwitter.phonestorage.api.thriftscala.PhoneStorageService

object PhoneStorageServiceModule
    extends BaseClientModule[PhoneStorageService.MethodPerEndpoint]
    with MtlsClient {
  override val label = "phone-storage-service"
  override val dest = "/s/ibis-ds-api/ibis-ds-api:thrift2"
}
