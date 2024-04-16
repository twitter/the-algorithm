package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}

/**
 * Represents what t  language  s assoc ated w h.
 * For example, "user"  s one of t  scopes and "event"
 * could be anot r scope.
 */
object LanguageScope extends Enu rat on {

  // User scope  ans that t  language  s t  user's language.
  val User: Value = Value(thr ft.LanguageScope.User.value)

  // Event scope  ans that t  language  s t  event's language.
  val Event: Value = Value(thr ft.LanguageScope.Event.value)

  // l st of all LanguageScope values
  val All: ValueSet = LanguageScope.ValueSet(User, Event)

  def apply(scope: thr ft.LanguageScope): LanguageScope.Value = {
    scope match {
      case thr ft.LanguageScope.User =>
        User
      case thr ft.LanguageScope.Event =>
        Event
      case _ =>
        throw new  llegalArgu ntExcept on(s"Unsupported language scope: $scope")
    }
  }

  def fromThr ft(scope: thr ft.LanguageScope): LanguageScope.Value = {
    apply(scope)
  }

  def toThr ft(scope: LanguageScope.Value): thr ft.LanguageScope = {
    scope match {
      case LanguageScope.User =>
        thr ft.LanguageScope.User
      case LanguageScope.Event =>
        thr ft.LanguageScope.Event
      case _ =>
        throw new  llegalArgu ntExcept on(s"Unsupported language scope: $scope")
    }
  }
}
