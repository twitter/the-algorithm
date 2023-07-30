package com.X.follow_recommendations.common.clients.email_storage_service

import com.X.emailstorage.api.thriftscala.EmailStorageService
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.follow_recommendations.common.clients.common.BaseClientModule

object EmailStorageServiceModule
    extends BaseClientModule[EmailStorageService.MethodPerEndpoint]
    with MtlsClient {
  override val label = "email-storage-service"
  override val dest = "/s/email-server/email-server"
}
