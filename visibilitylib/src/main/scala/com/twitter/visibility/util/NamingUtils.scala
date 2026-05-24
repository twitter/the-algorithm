package com.twitter.visibility.util

object NamingUtils {
  def getFriendlyName(a: Any): String = getFriendlyNameFromClass(a.getClass)
  def getFriendlyNameFromClass(a: Class[_]): String = a.getSimpleName.stripSuffix("$")
}
