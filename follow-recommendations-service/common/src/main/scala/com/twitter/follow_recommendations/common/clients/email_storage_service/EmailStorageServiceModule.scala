package com.ExTwitter.follow_recommendations.common.clients.email_storage_service

import com.ExTwitter.emailstorage.api.thriftscala.EmailStorageService
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.follow_recommendations.common.clients.common.BaseClientModule

object EmailStorageServiceModule
    extends BaseClientModule[EmailStorageService.MethodPerEndpoint]
    with MtlsClient {
  override val label = "email-storage-service"
  override val dest = "/s/email-server/email-server"
}
