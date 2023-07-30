package com.X.product_mixer.component_library.module.http

import com.google.inject.Provides
import com.X.finagle.http.ProxyCredentials
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.util.security.{Credentials => CredentialsUtil}
import java.io.File
import javax.inject.Singleton

object ProxyCredentialsModule extends XModule {
  final val HttpClientWithProxyCredentialsPath = "http_client.proxy.proxy_credentials_path"

  flag[String](HttpClientWithProxyCredentialsPath, "", "Path the load the proxy credentials")

  @Provides
  @Singleton
  def providesProxyCredentials(
    @Flag(HttpClientWithProxyCredentialsPath) proxyCredentialsPath: String,
  ): ProxyCredentials = {
    val credentialsFile = new File(proxyCredentialsPath)
    ProxyCredentials(CredentialsUtil(credentialsFile))
      .getOrElse(throw MissingProxyCredentialsException)
  }

  object MissingProxyCredentialsException extends Exception("Proxy Credentials not found")
}
