package com.twitter.visibility.interfaces.common.blender

import com.twitter.search.blender.services.strato.UserSearchSafetySettings
import com.twitter.search.common.constants.thriftscala.ThriftQuerySource

case class BlenderVFRequestContext(
  resultsPageNumber: Int,
  candidateCount: Int,
  querySourceOption: Option[ThriftQuerySource],
  userSearchSafetySettings: UserSearchSafetySettings,
  queryHasUser: Boolean = false) {

  def this(
    resultsPageNumber: Int,
    candidateCount: Int,
    querySourceOption: Option[ThriftQuerySource],
    userSearchSafetySettings: UserSearchSafetySettings
  ) = this(resultsPageNumber, candidateCount, querySourceOption, userSearchSafetySettings, false)
}
