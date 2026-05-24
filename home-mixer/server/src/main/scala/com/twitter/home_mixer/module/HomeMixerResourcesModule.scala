package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.config.yaml.YamlMap
import com.twitter.inject.TwitterModule
import javax.inject.Singleton

case class ViralContentCreatorsConfig(creators: Set[Long])
case class SupportAccountsConfig(accounts: Set[Long])

object HomeMixerResourcesModule extends TwitterModule {

  private val ConfigFilePath = "/config/ids.yml"
  private val SupportAccountsKey = "support_accounts"
  private val ViralContentCreatorsKey = "viral_content_creators"

  private val yaml: YamlMap = YamlMap.load(ConfigFilePath)

  @Singleton
  @Provides
  def providesViralContentCreatorsConfig: ViralContentCreatorsConfig = {
    val contentCreators = yaml.longSeq(ViralContentCreatorsKey).toSet
    ViralContentCreatorsConfig(contentCreators)
  }

  @Singleton
  @Provides
  def providesSupportAccountsConfig: SupportAccountsConfig = {
    val supportAccounts = yaml.longSeq(SupportAccountsKey).toSet
    SupportAccountsConfig(supportAccounts)
  }
}
