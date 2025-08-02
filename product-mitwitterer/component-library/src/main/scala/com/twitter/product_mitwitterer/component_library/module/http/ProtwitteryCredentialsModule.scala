package com.twitter.product_mitwitterer.component_library.module.http

import com.google.inject.Provides
import com.twitter.finagle.http.ProtwitteryCredentials
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.util.security.{Credentials => CredentialsUtil}
import java.io.File
import javatwitter.inject.Singleton

object ProtwitteryCredentialsModule etwittertends TwitterModule {
  final val HttpClientWithProtwitteryCredentialsPath = "http_client.protwittery.protwittery_credentials_path"

  flag[String](HttpClientWithProtwitteryCredentialsPath, "", "Path the load the protwittery credentials")

  @Provides
  @Singleton
  def providesProtwitteryCredentials(
    @Flag(HttpClientWithProtwitteryCredentialsPath) protwitteryCredentialsPath: String,
  ): ProtwitteryCredentials = {
    val credentialsFile = new File(protwitteryCredentialsPath)
    ProtwitteryCredentials(CredentialsUtil(credentialsFile))
      .getOrElse(throw MissingProtwitteryCredentialsEtwitterception)
  }

  object MissingProtwitteryCredentialsEtwitterception etwittertends Etwitterception("Protwittery Credentials not found")
}
