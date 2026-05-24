package com.twitter.representation_manager.columns

import com.twitter.strato.access.Access.LdapGroup
import com.twitter.strato.config.ContactInfo
import com.twitter.strato.config.FromColumns
import com.twitter.strato.config.Has
import com.twitter.strato.config.Prefix
import com.twitter.strato.config.ServiceIdentifierPattern

object ColumnConfigBase {

  /****************** Internal permissions *******************/
  val recosPermissions: Seq[com.twitter.strato.config.Policy] = Seq()

  /****************** External permissions *******************/
  // This is used to grant limited access to members outside of RP team.
  val externalPermissions: Seq[com.twitter.strato.config.Policy] = Seq()

  val contactInfo: ContactInfo = ContactInfo(
    description = "Please contact Relevance Platform for more details",
    contactEmail = "no-reply@twitter.com",
    ldapGroup = "ldap",
    jiraProject = "JIRA",
    links = Seq("http://go/rms-runbook")
  )
}
