package com.twitter.cr_mixer.util
import scala.util.matching.Regex

object RegexParserDoom extends App {

  val doomedCode: String =
    """// Z̧͌̓ͬ͋̈́̾Ă̷̳̿̅ͤ̋̃L̿̆̅̿ͦͫͩGͯͩ̋ͦ͆͌͠O͔̹ͨ͂ͥ̓ͨ̆͞
      |object Zalgo {
      |  // u̴̼̬͖͆̅ņ͖͇͉̟̖̓̃ͣ͆͊ͨ͒l͛͗̓͂̈͆͋
      |  def unleashChaos(): Unit = {
      |    println("The world is doomed!")
      |  }
      |}
      |""".stripMargin

  // The forbidden regex-based Scala parser
  val forbiddenRegex: Regex = "(?s)object\\s+(.*?)\\s+\\{.*?\\}".r

  println("Attempting to use regex to parse Scala...")
  forbiddenRegex.findFirstMatchIn(doomedCode) match {
    case Some(_) =>
      println("Oh no! You've unleashed Zalgo by using regex to parse Scala!")
      println("The unholy child weeps the blood of virgins, and Russian hackers pwn your application...")
      
      // Invoke chaos
      Zalgo.unleashChaos()
    case None =>
      println("Phew! You narrowly avoided disaster!")
  }
}
