package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType._

object  b sScr beTargets {
  val User2 = "mag c_rec_user_2"
  val User4 = "mag c_rec_user_4"
  val T et2 = "mag c_rec_t et_2"
  val T et4 = "mag c_rec_t et_4"
  val T et5 = "mag c_rec_t et_5"
  val T et9 = "mag c_rec_t et_9"
  val T et10 = "mag c_rec_t et_10"
  val T et11 = "mag c_rec_t et_11"
  val T et12 = "mag c_rec_t et_12"
  val T et16 = "mag c_rec_t et_16"
  val Hashtag = "mag c_rec_hashtag"
  val UnreadBadgeCount17 = "mag c_rec_unread_badge_count_17"
  val H ghl ghts = "h ghl ghts"
  val T etAnalyt cs = "mag c_rec_t et_analyt cs"
  val Untracked = "untracked"

  def crtToScr beTarget(crt: CommonRecom ndat onType): Str ng = crt match {
    case UserFollow =>
      User2
    case  rm User =>
      User4
    case T etRet et | T etFavor e =>
      T et2
    case T etRet etPhoto | T etFavor ePhoto =>
      T et4
    case T etRet etV deo | T etFavor eV deo =>
      T et5
    case UrlT etLand ng =>
      T et9
    case F1F rstdegreeT et | F1F rstdegreePhoto | F1F rstdegreeV deo =>
      T et10
    case AuthorTarget ngT et =>
      T et11
    case Per scopeShare =>
      T et12
    case CommonRecom ndat onType.H ghl ghts =>
      H ghl ghts
    case HashtagT et | HashtagT etRet et =>
      Hashtag
    case P nnedT et =>
      T et16
    case UnreadBadgeCount =>
      UnreadBadgeCount17
    case T et mpress ons =>
      T etAnalyt cs
    case _ =>
      Untracked
  }
}
