package com.X.visibility.interfaces.common

import com.X.search.blender.services.strato.UserSearchSafetySettings
import com.X.spam.rtf.thriftscala.SafetyLabel
import com.X.spam.rtf.thriftscala.SafetyLabelMap
import com.X.spam.rtf.thriftscala.SafetyLabelType
import com.X.stitch.Stitch

package object tweets {
  type SafetyLabelFetcherType = (Long, SafetyLabelType) => Stitch[Option[SafetyLabel]]
  type SafetyLabelMapFetcherType = Long => Stitch[Option[SafetyLabelMap]]
  type UserSearchSafetySettingsFetcherType = Long => Stitch[UserSearchSafetySettings]
}
