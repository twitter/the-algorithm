package com.twitter.usersignalservice
package service

import com.google.inject.Inject
import com.google.inject.Singleton
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.stitch.storehaus.StitchOfReadableStore
import com.twitter.usersignalservice.config.SignalFetcherConfig
import com.twitter.usersignalservice.handler.UserSignalHandler
import com.twitter.usersignalservice.thriftscala.BatchSignalRequest
import com.twitter.usersignalservice.thriftscala.BatchSignalResponse
import com.twitter.util.Timer

@Singleton
class UserSignalService @Inject() (
  signalFetcherConfig: SignalFetcherConfig,
  timer: Timer,
  stats: StatsReceiver) {

  private val userSignalHandler =
    new UserSignalHandler(signalFetcherConfig, timer, stats)

  val userSignalServiceHandlerStoreStitch: BatchSignalRequest => com.twitter.stitch.Stitch[
    BatchSignalResponse
  ] = StitchOfReadableStore(userSignalHandler.toReadableStore)
}
