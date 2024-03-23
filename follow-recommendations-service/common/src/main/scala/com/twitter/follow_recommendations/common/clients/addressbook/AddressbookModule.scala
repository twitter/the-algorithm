package com.ExTwitter.follow_recommendations.common.clients.addressbook

import com.ExTwitter.addressbook.thriftscala.Addressbook2
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.follow_recommendations.common.clients.common.BaseClientModule

object AddressbookModule extends BaseClientModule[Addressbook2.MethodPerEndpoint] with MtlsClient {
  override val label = "addressbook"
  override val dest = "/s/addressbook/addressbook2"
}
