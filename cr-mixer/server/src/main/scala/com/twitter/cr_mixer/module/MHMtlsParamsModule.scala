package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import javax.inject.Singleton

object MHMtlsParamsModule extends ExTwitterModule {
  @Singleton
  @Provides
  def providesManhattanMtlsParams(
    serviceIdentifier: ServiceIdentifier
  ): ManhattanKVClientMtlsParams = {
    ManhattanKVClientMtlsParams(serviceIdentifier)
  }
}
