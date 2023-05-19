package com.twitter.frigate.pushservice.target

import com.twitter.permissions_storage.thriftscala.AppPermission
import com.twitter.util.Future

trait TargetAppPermissions {

  def appPermissions: Future[Option[AppPermission]]

}
