package com.X.follow_recommendations.common.clients.addressbook.models

import com.X.addressbook.datatypes.{thriftscala => t}

case class RecordIdentifier(
  userId: Option[Long],
  email: Option[String],
  phoneNumber: Option[String]) {
  def toThrift: t.RecordIdentifier = t.RecordIdentifier(userId, email, phoneNumber)
}
