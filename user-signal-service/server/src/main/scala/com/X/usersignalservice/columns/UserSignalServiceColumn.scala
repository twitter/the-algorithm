package com.X.usersignalservice.columns

import com.X.stitch.NotFound
import com.X.stitch.Stitch
import com.X.strato.catalog.OpMetadata
import com.X.strato.catalog.Ops
import com.X.strato.config.Policy
import com.X.strato.config.ReadWritePolicy
import com.X.strato.data.Conv
import com.X.strato.data.Description
import com.X.strato.data.Lifecycle
import com.X.strato.fed.StratoFed
import com.X.strato.thrift.ScroogeConv
import com.X.usersignalservice.service.UserSignalService
import com.X.usersignalservice.thriftscala.BatchSignalRequest
import com.X.usersignalservice.thriftscala.BatchSignalResponse
import javax.inject.Inject

class UserSignalServiceColumn @Inject() (userSignalService: UserSignalService)
    extends StratoFed.Column(UserSignalServiceColumn.Path)
    with StratoFed.Fetch.Stitch {

  override val metadata: OpMetadata = OpMetadata(
    lifecycle = Some(Lifecycle.Production),
    description = Some(Description.PlainText("User Signal Service Federated Column")))

  override def ops: Ops = super.ops

  override type Key = BatchSignalRequest
  override type View = Unit
  override type Value = BatchSignalResponse

  override val keyConv: Conv[Key] = ScroogeConv.fromStruct[BatchSignalRequest]
  override val viewConv: Conv[View] = Conv.ofType
  override val valueConv: Conv[Value] = ScroogeConv.fromStruct[BatchSignalResponse]

  override def fetch(key: Key, view: View): Stitch[Result[Value]] = {
    userSignalService
      .userSignalServiceHandlerStoreStitch(key)
      .map(result => found(result))
      .handle {
        case NotFound => missing
      }
  }
}

object UserSignalServiceColumn {
  val Path = "recommendations/user-signal-service/signals"
}
