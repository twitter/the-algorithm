package com.X.follow_recommendations.common.clients.addressbook.models

import com.X.addressbook.{thriftscala => t}
import com.X.util.Time

case class Contact(
  id: Long,
  emails: Option[Set[String]],
  phoneNumbers: Option[Set[String]],
  firstName: Option[String],
  lastName: Option[String],
  name: Option[String],
  appId: Option[Long],
  appIds: Option[Set[Long]],
  importedTimestamp: Option[Time])

object Contact {
  def fromThrift(thriftContact: t.Contact): Contact = Contact(
    thriftContact.id,
    thriftContact.emails.map(_.toSet),
    thriftContact.phoneNumbers.map(_.toSet),
    thriftContact.firstName,
    thriftContact.lastName,
    thriftContact.name,
    thriftContact.appId,
    thriftContact.appIds.map(_.toSet),
    thriftContact.importedTimestamp.map(Time.fromMilliseconds)
  )
}
