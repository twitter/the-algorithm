package com.twitter.tweetypie.federated.columns

import com.twitter.strato.config.ContactInfo

object TweetypieContactInfo
    extends ContactInfo(
      contactEmail = "",
      ldapGroup = "",
      jiraProject = "",
      slackRoomId = ""
    )
