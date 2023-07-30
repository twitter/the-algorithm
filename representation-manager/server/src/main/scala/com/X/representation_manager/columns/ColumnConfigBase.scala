package com.X.representation_manager.columns

import com.X.strato.access.Access.LdapGroup
import com.X.strato.config.ContactInfo
import com.X.strato.config.FromColumns
import com.X.strato.config.Has
import com.X.strato.config.Prefix
import com.X.strato.config.ServiceIdentifierPattern

object ColumnConfigBase {

  /****************** Internal permissions *******************/
  val recosPermissions: Seq[com.X.strato.config.Policy] = Seq()

  /****************** External permissions *******************/
  // This is used to grant limited access to members outside of RP team.
  val externalPermissions: Seq[com.X.strato.config.Policy] = Seq()

  val contactInfo: ContactInfo = ContactInfo(
    description = "Please contact Relevance Platform for more details",
    contactEmail = "no-reply@X.com",
    ldapGroup = "ldap",
    jiraProject = "JIRA",
    links = Seq("http://go/rms-runbook")
  )
}
