package com.tw ter.v s b l y.models

sealed tra  Content d

object Content d {
  case class T et d( d: Long) extends Content d
  case class User d( d: Long) extends Content d
  case class Card d(url: Str ng) extends Content d
  case class QuotedT etRelat onsh p(outer: Long,  nner: Long) extends Content d
  case class Not f cat on d(t et d: Opt on[Long]) extends Content d
  case class Dm d( d: Long) extends Content d
  case class BlenderT et d( d: Long) extends Content d
  case class Space d( d: Str ng) extends Content d
  case class SpacePlusUser d( d: Str ng) extends Content d
  case class DmConversat on d( d: Str ng) extends Content d
  case class DmEvent d( d: Long) extends Content d
  case class UserUnava lableState(t et d: Long) extends Content d
  case class Tw terArt cle d( d: Long) extends Content d
  case class DeleteT et d(t et d: Long) extends Content d
  case class  d a d( d: Str ng) extends Content d
  case class Commun y d(commun y d: Long) extends Content d
}
