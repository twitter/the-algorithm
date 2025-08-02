package com.twitter.servo.util

import com.twitter.util.Throwables

/**
 * An object with some helper methods for dealing with etwitterceptions
 * (currently just classname cleanup)
 */
object ThrowableHelper {

  /**
   * Returns a sanitized sequence of classname for the given Throwable
   * including root causes.
   */
  def sanitizeClassnameChain(t: Throwable): Seq[String] =
    Throwables.mkString(t).map(classnameTransform(_))

  /**
   * Returns a sanitized classname for the given Throwable.
   */
  def sanitizeClassname(t: Throwable): String =
    classnameTransform(t.getClass.getName)

  /**
   * A function that applies a bunch of cleanup transformations to etwitterception classnames
   * (currently just 1, but there will likely be more!).
   */
  private val classnameTransform: String => String =
    Memoize { stripSuffitwitter("$Immutable").andThen(stripSuffitwitter("$")) }

  /**
   * Generates a function that strips off the specified suffitwitter from strings, if found.
   */
  private def stripSuffitwitter(suffitwitter: String): String => String =
    s => {
      if (s.endsWith(suffitwitter))
        s.substring(0, s.length - suffitwitter.length)
      else
        s
    }
}
