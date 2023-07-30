package com.X.usersignalservice

import com.google.inject.Module
import com.X.inject.thrift.modules.ThriftClientIdModule
import com.X.usersignalservice.columns.UserSignalServiceColumn
import com.X.strato.fed._
import com.X.strato.fed.server._
import com.X.usersignalservice.module.CacheModule
import com.X.usersignalservice.module.MHMtlsParamsModule
import com.X.usersignalservice.module.SocialGraphServiceClientModule
import com.X.usersignalservice.module.TimerModule

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
