package com.twitter.visibility.models

case class UserAge(ageInYears: Option[Int]) {
  def hasAge: Boolean = ageInYears.isDefined

  def isGte(ageToCompare: Int): Boolean =
    ageInYears
      .collectFirst {
        case age if age > ageToCompare => true
      }.getOrElse(false)

  def unapply(userAge: UserAge): Option[Int] = {
    ageInYears
  }
}
