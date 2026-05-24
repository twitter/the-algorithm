package com.twitter.usersignalservice

import com.google.inject.Module
import com.twitter.inject.thrift.modules.ThriftClientIdModule
import com.twitter.usersignalservice.columns.UserSignalServiceColumn
import com.twitter.strato.fed._
import com.twitter.strato.fed.server._
import com.twitter.usersignalservice.module.CacheModule
import com.twitter.usersignalservice.module.MHMtlsParamsModule
import com.twitter.usersignalservice.module.SocialGraphServiceClientModule
import com.twitter.usersignalservice.module.TimerModule

object UserSignalServiceStratoFedServerMain extends UserSignalServiceStratoFedServer

trait UserSignalServiceStratoFedServer extends StratoFedServer {
  override def dest: String = "/s/user-signal-service/user-signal-service"

  override def columns: Seq[Class[_ <: StratoFed.Column]] =
    Seq(
      classOf[UserSignalServiceColumn]
    )

  override def modules: Seq[Module] =
    Seq(
      CacheModule,
      MHMtlsParamsModule,
      SocialGraphServiceClientModule,
      ThriftClientIdModule,
      TimerModule,
    )

}
