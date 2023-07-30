package com.X.cr_mixer.module

import com.google.inject.Provides
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.inject.XModule
import com.X.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import javax.inject.Singleton

object MHMtlsParamsModule extends XModule {
  @Singleton
  @Provides
  def providesManhattanMtlsParams(
    serviceIdentifier: ServiceIdentifier
  ): ManhattanKVClientMtlsParams = {
    ManhattanKVClientMtlsParams(serviceIdentifier)
  }
}
