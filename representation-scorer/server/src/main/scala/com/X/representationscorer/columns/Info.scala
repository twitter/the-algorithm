package com.X.representationscorer.columns

import com.X.strato.config.{ContactInfo => StratoContactInfo}

object Info {
  val contactInfo: StratoContactInfo = StratoContactInfo(
    description = "Please contact Relevance Platform team for more details",
    contactEmail = "no-reply@X.com",
    ldapGroup = "representation-scorer-admins",
    jiraProject = "JIRA",
    links = Seq("http://go.X.biz/rsx-runbook")
  )
}
