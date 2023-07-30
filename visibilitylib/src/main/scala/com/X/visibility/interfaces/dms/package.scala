package com.X.visibility.interfaces

import com.X.stitch.Stitch
import com.X.visibility.common.DmId
import com.X.visibility.safety_label_store.thriftscala.DmSafetyLabelMap

package object dms {
  type DmSafetyLabelMapFetcherType = DmId => Stitch[Option[DmSafetyLabelMap]]

  val DmSafetyLabelMapFetcherStratoColumn =
    "visibility/safety-label-store/vflib/dm/safetyLabelMap.Dm"
}
