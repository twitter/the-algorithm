package com.twitter.timelineranker.model

import com.twitter.timelineranker.{thriftscala => thrift}

/**
 * Represents what this language is associated with.
 * For example, "user" is one of the scopes and "event"
 * could be another scope.
 */
object LanguageScope extends Enumeration {

  // User scope means that the language is the user's language.
  val User: Value = Value(thrift.LanguageScope.User.value)

  // Event scope means that the language is the event's language.
  val Event: Value = Value(thrift.LanguageScope.Event.value)

  // list of all LanguageScope values
  val All: ValueSet = LanguageScope.ValueSet(User, Event)

  def apply(scope: thrift.LanguageScope): LanguageScope.Value = {
    scope match {
      case thrift.LanguageScope.User =>
        User
      case thrift.LanguageScope.Event =>
        Event
      case _ =>
        throw new IllegalArgumentException(s"Unsupported language scope: $scope")
    }
  }

  def fromThrift(scope: thrift.LanguageScope): LanguageScope.Value = {
    apply(scope)
  }

  def toThrift(scope: LanguageScope.Value): thrift.LanguageScope = {
    scope match {
      case LanguageScope.User =>
        thrift.LanguageScope.User
      case LanguageScope.Event =>
        thrift.LanguageScope.Event
      case _ =>
        throw new IllegalArgumentException(s"Unsupported language scope: $scope")
    }
  }
}
