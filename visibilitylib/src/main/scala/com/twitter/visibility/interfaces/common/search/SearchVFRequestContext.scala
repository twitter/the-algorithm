package com.twitter.visibility.interfaces.common.search

import com.twitter.search.blender.services.strato.UserSearchSafetySettings
import com.twitter.search.common.constants.thriftscala.ThriftQuerySource

case class SearchVFRequestContext(
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
