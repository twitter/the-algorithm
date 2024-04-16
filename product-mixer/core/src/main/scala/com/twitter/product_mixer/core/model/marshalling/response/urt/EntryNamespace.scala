package com.tw ter.product_m xer.core.model.marshall ng.response.urt

 mport scala.ut l.match ng.Regex

/**
 * Entry  dent f ers (commonly entry  ds) are a type of  dent f er used  n URT to  dent fy
 * un que t  l ne entr es - t ets, users, modules, etc.
 *
 * Entry  dent f ers are for d from two parts - a na space (EntryNa space) and an underly ng
 *  d.
 *
 * A Entry Na space  s restr cted to:
 * - 3 to 60 characters to ensure reasonable length
 * - a-z and das s (kebab-case)
 * - Examples  nclude "user" and "t et"
 *
 * W n spec f c entr es  dent f ers are created, t y w ll be appended w h a dash and t  r
 * own  d, l ke user-12 or t et-20
 */

tra  HasEntryNa space {
  val entryNa space: EntryNa space
}

// sealed abstract case class  s bas cally a scala 2.12 opaque type -
//   can only create t m v a t  factory  thod on t  compan on
// allow ng us to enforce val dat on
sealed abstract case class EntryNa space(overr de val toStr ng: Str ng)

object EntryNa space {
  val Allo dCharacters: Regex = "[a-z-]+".r // Allows for kebab-case

  def apply(str: Str ng): EntryNa space = {
    val  sVal d = str match {
      case n  f n.length < 3 =>
        false
      case n  f n.length > 60 =>
        false
      case Allo dCharacters() =>
        true
      case _ =>
        false
    }

     f ( sVal d)
      new EntryNa space(str) {}
    else
      throw new  llegalArgu ntExcept on(s" llegal EntryNa space: $str")
  }
}
