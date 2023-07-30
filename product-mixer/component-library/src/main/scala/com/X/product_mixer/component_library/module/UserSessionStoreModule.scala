package com.X.product_mixer.component_library.module

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.user_session_store.ReadOnlyUserSessionStore
import com.X.user_session_store.ReadWriteUserSessionStore
import com.X.user_session_store.UserSessionDataset
import com.X.user_session_store.UserSessionDataset.UserSessionDataset
import com.X.user_session_store.config.manhattan.UserSessionStoreManhattanConfig
import com.X.user_session_store.impl.manhattan.readonly.ReadOnlyManhattanUserSessionStoreBuilder
import com.X.user_session_store.impl.manhattan.readwrite.ReadWriteManhattanUserSessionStoreBuilder

import javax.inject.Singleton

object UserSessionStoreModule extends XModule {
  private val ReadWriteAppId = "timelineservice_user_session_store"
  private val ReadWriteStagingDataset = "tls_user_session_store_nonprod"
  private val ReadWriteProdDataset = "tls_user_session_store"
  private val ReadOnlyAppId = "user_session_store"
  private val ReadOnlyDataset = "user_session_fields"

  @Provides
  @Singleton
  def providesReadWriteUserSessionStore(
    injectedServiceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): ReadWriteUserSessionStore = {
    val scopedStatsReceiver = statsReceiver.scope(injectedServiceIdentifier.service)

    val dataset = injectedServiceIdentifier.environment.toLowerCase match {
      case "prod" => ReadWriteProdDataset
      case _ => ReadWriteStagingDataset
    }

    val clientReadWriteConfig = new UserSessionStoreManhattanConfig.Prod.ReadWrite.Omega {
      override val appId = ReadWriteAppId
      override val defaultMaxTimeout = 400.milliseconds
      override val maxRetryCount = 1
      override val serviceIdentifier = injectedServiceIdentifier
      override val datasetNamesById = Map[UserSessionDataset, String](
        UserSessionDataset.ActiveDaysInfo -> dataset,
        UserSessionDataset.NonPollingTimes -> dataset
      )
    }

    ReadWriteManhattanUserSessionStoreBuilder
      .buildReadWriteUserSessionStore(clientReadWriteConfig, statsReceiver, scopedStatsReceiver)
  }

  @Provides
  @Singleton
  def providesReadOnlyUserSessionStore(
    injectedServiceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): ReadOnlyUserSessionStore = {
    val scopedStatsReceiver = statsReceiver.scope(injectedServiceIdentifier.service)

    val clientReadOnlyConfig = new UserSessionStoreManhattanConfig.Prod.ReadOnly.Athena {
      override val appId = ReadOnlyAppId
      override val defaultMaxTimeout = 400.milliseconds
      override val maxRetryCount = 1
      override val serviceIdentifier = injectedServiceIdentifier
      override val datasetNamesById = Map[UserSessionDataset, String](
        UserSessionDataset.UserHealth -> ReadOnlyDataset
      )
    }

    ReadOnlyManhattanUserSessionStoreBuilder
      .buildReadOnlyUserSessionStore(clientReadOnlyConfig, statsReceiver, scopedStatsReceiver)
  }
}
