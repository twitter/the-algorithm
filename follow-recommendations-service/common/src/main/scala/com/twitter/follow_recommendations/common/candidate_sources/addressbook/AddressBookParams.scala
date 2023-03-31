package com.twitter.follow_recommendations.common.candidate_sources.addressbook

import com.twitter.timelines.configapi.FSParam

object AddressBookParams {
  // Used by display locations that want only to read from the ABV2 Client and ignore Manhattan
  // Currently the only display location that does this is the ABUploadInjection DisplayLocation
  object ReadFromABV2Only extends FSParam[Boolean]("addressbook_read_only_from_abv2", false)
}
