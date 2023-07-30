package com.X.usersignalservice
package service

import com.google.inject.Inject
import com.google.inject.Singleton
import com.X.finagle.stats.StatsReceiver
import com.X.stitch.storehaus.StitchOfReadableStore
import com.X.usersignalservice.config.SignalFetcherConfig
import com.X.usersignalservice.handler.UserSignalHandler
import com.X.usersignalservice.thriftscala.BatchSignalRequest
import com.X.usersignalservice.thriftscala.BatchSignalResponse
import com.X.util.Timer

@Singleton
class UserSignalService @Inject() (
  signalFetcherConfig: SignalFetcherConfig,
  timer: Timer,
  stats: StatsReceiver) {

  private val userSignalHandler =
    new UserSignalHandler(signalFetcherConfig, timer, stats)

  val userSignalServiceHandlerStoreStitch: BatchSignalRequest => com.X.stitch.Stitch[
    BatchSignalResponse
  ] = StitchOfReadableStore(userSignalHandler.toReadableStore)
}
