package com.X.follow_recommendations.common.clients.addressbook

import com.X.addressbook.thriftscala.Addressbook2
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.follow_recommendations.common.clients.common.BaseClientModule

object AddressbookModule extends BaseClientModule[Addressbook2.MethodPerEndpoint] with MtlsClient {
  override val label = "addressbook"
  override val dest = "/s/addressbook/addressbook2"
}
