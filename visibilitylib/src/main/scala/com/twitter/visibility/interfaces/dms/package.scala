package com.twitter.visibility.interfaces

import com.twitter.stitch.Stitch
import com.twitter.visibility.common.DmId
import com.twitter.visibility.safety_label_store.thriftscala.DmSafetyLabelMap

package object dms {
  type DmSafetyLabelMapFetcherType = DmId => Stitch[Option[DmSafetyLabelMap]]

  val DmSafetyLabelMapFetcherStratoColumn =
    "visibility/safety-label-store/vflib/dm/safetyLabelMap.Dm"
}
