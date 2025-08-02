package com.twitter.usersignalservice.module

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.inject.TwitterModule
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.google.inject.Provides
import javatwitter.inject.Singleton

object MHMtlsParamsModule etwittertends TwitterModule {

  @Singleton
  @Provides
  def providesManhattanMtlsParams(
    serviceIdentifier: ServiceIdentifier
  ): ManhattanKVClientMtlsParams = {
    ManhattanKVClientMtlsParams(serviceIdentifier)
  }
}
