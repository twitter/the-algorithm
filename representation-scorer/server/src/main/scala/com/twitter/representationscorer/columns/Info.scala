package com.twitter.representationscorer.columns

import com.twitter.strato.config.{ContactInfo => StratoContactInfo}

object Info {
  val contactInfo: StratoContactInfo = StratoContactInfo(
    description = "Please contact Relevance Platform team for more details",
    contactEmail = "no-reply@twitter.com",
    ldapGroup = "representation-scorer-admins",
    jiraProject = "JIRA",
    links = Seq("http://go.twitter.biz/rsx-runbook")
  )
}
