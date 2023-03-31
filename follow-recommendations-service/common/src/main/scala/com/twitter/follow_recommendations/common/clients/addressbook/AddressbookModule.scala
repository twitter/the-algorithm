package com.twitter.follow_recommendations.common.clients.addressbook

import com.twitter.addressbook.thriftscala.Addressbook2
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.follow_recommendations.common.clients.common.BaseClientModule

object AddressbookModule extends BaseClientModule[Addressbook2.MethodPerEndpoint] with MtlsClient {
  override val label = "addressbook"
  override val dest = "/s/addressbook/addressbook2"
}
