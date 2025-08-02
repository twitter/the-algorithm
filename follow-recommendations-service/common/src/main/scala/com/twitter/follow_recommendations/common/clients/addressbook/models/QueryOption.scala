package com.twitter.follow_recommendations.common.clients.addressbook.models

import com.twitter.addressbook.{thriftscala => t}

case class QueryOption(
  onlyDiscoverableInEtwitterpansion: Boolean,
  onlyConfirmedInEtwitterpansion: Boolean,
  onlyDiscoverableInResult: Boolean,
  onlyConfirmedInResult: Boolean,
  fetchGlobalApiNamespace: Boolean,
  isDebugRequest: Boolean,
  resolveEmails: Boolean,
  resolvePhoneNumbers: Boolean) {
  def toThrift: t.QueryOption = t.QueryOption(
    onlyDiscoverableInEtwitterpansion,
    onlyConfirmedInEtwitterpansion,
    onlyDiscoverableInResult,
    onlyConfirmedInResult,
    fetchGlobalApiNamespace,
    isDebugRequest,
    resolveEmails,
    resolvePhoneNumbers
  )
}
