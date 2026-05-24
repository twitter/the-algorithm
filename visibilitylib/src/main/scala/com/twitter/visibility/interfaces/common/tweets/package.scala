package com.twitter.visibility.interfaces.common

import com.twitter.search.blender.services.strato.UserSearchSafetySettings
import com.twitter.spam.rtf.thriftscala.SafetyLabel
import com.twitter.spam.rtf.thriftscala.SafetyLabelMap
import com.twitter.spam.rtf.thriftscala.SafetyLabelType
import com.twitter.stitch.Stitch

package object tweets {
  type SafetyLabelFetcherType = (Long, SafetyLabelType) => Stitch[Option[SafetyLabel]]
  type SafetyLabelMapFetcherType = Long => Stitch[Option[SafetyLabelMap]]
  type UserSearchSafetySettingsFetcherType = Long => Stitch[UserSearchSafetySettings]
}
