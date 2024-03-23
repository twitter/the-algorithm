package com.ExTwitter.follow_recommendations.common.clients.addressbook.models

import com.ExTwitter.addressbook.{thriftscala => t}

case class QueryOption(
  onlyDiscoverableInExpansion: Boolean,
  onlyConfirmedInExpansion: Boolean,
  onlyDiscoverableInResult: Boolean,
  onlyConfirmedInResult: Boolean,
  fetchGlobalApiNamespace: Boolean,
  isDebugRequest: Boolean,
  resolveEmails: Boolean,
  resolvePhoneNumbers: Boolean) {
  def toThrift: t.QueryOption = t.QueryOption(
    onlyDiscoverableInExpansion,
    onlyConfirmedInExpansion,
    onlyDiscoverableInResult,
    onlyConfirmedInResult,
    fetchGlobalApiNamespace,
    isDebugRequest,
    resolveEmails,
    resolvePhoneNumbers
  )
}
