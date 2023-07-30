package com.X.frigate.pushservice.target

import com.X.permissions_storage.thriftscala.AppPermission
import com.X.util.Future

trait TargetAppPermissions {

  def appPermissions: Future[Option[AppPermission]]

}
