package com.ExTwitter.follow_recommendations.common.clients.addressbook.models

import com.ExTwitter.addressbook.datatypes.{thriftscala => t}

case class RecordIdentifier(
  userId: Option[Long],
  email: Option[String],
  phoneNumber: Option[String]) {
  def toThrift: t.RecordIdentifier = t.RecordIdentifier(userId, email, phoneNumber)
}
