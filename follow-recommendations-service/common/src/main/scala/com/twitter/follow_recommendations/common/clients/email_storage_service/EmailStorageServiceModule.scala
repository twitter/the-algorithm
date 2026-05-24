package com.twitter.follow_recommendations.common.clients.email_storage_service

import com.twitter.emailstorage.api.thriftscala.EmailStorageService
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.follow_recommendations.common.clients.common.BaseClientModule

object EmailStorageServiceModule
    extends BaseClientModule[EmailStorageService.MethodPerEndpoint]
    with MtlsClient {
  override val label = "email-storage-service"
  override val dest = "/s/email-server/email-server"
}
