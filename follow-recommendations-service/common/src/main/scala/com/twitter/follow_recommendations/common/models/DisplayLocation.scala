package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.adserver.thr ftscala.{D splayLocat on => AdD splayLocat on}
 mport com.tw ter.follow_recom ndat ons.logg ng.thr ftscala.{
  Offl neD splayLocat on => TOffl neD splayLocat on
}
 mport com.tw ter.follow_recom ndat ons.thr ftscala.{D splayLocat on => TD splayLocat on}

sealed tra  D splayLocat on {
  def toThr ft: TD splayLocat on

  def toOffl neThr ft: TOffl neD splayLocat on

  def toFsNa : Str ng

  // correspond ng d splay locat on  n adserver  f ava lable
  // make sure to be cons stent w h t  def n  on  re
  def toAdD splayLocat on: Opt on[AdD splayLocat on] = None
}

/**
 * Make sure   add t  new DL to t  follow ng f les and redeploy   attr but on jobs
 *  - follow-recom ndat ons-serv ce/thr ft/src/ma n/thr ft/d splay_locat on.thr ft
 *  - follow-recom ndat ons-serv ce/thr ft/src/ma n/thr ft/logg ng/d splay_locat on.thr ft
 *  - follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/models/D splayLocat on.scala
 */

object D splayLocat on {

  case object Prof leS debar extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Prof leS debar
    overr de val toOffl neThr ft: TOffl neD splayLocat on = TOffl neD splayLocat on.Prof leS debar
    overr de val toFsNa : Str ng = "Prof leS debar"

    overr de val toAdD splayLocat on: Opt on[AdD splayLocat on] = So (
      AdD splayLocat on.Prof leAccountsS debar
    )
  }

  case object Ho T  l ne extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Ho T  l ne
    overr de val toOffl neThr ft: TOffl neD splayLocat on = TOffl neD splayLocat on.Ho T  l ne
    overr de val toFsNa : Str ng = "Ho T  l ne"
    overr de val toAdD splayLocat on: Opt on[AdD splayLocat on] = So (
      //    s based on t  log c that HTL DL should correspond to S debar:
      AdD splayLocat on.WtfS debar
    )
  }

  case object React veFollow extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.React veFollow
    overr de val toOffl neThr ft: TOffl neD splayLocat on = TOffl neD splayLocat on.React veFollow
    overr de val toFsNa : Str ng = "React veFollow"
  }

  case object ExploreTab extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.ExploreTab
    overr de val toOffl neThr ft: TOffl neD splayLocat on = TOffl neD splayLocat on.ExploreTab
    overr de val toFsNa : Str ng = "ExploreTab"
  }

  case object Mag cRecs extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Mag cRecs
    overr de val toOffl neThr ft: TOffl neD splayLocat on = TOffl neD splayLocat on.Mag cRecs
    overr de val toFsNa : Str ng = "Mag cRecs"
  }

  case object AbUpload nject on extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.AbUpload nject on
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.AbUpload nject on
    overr de val toFsNa : Str ng = "AbUpload nject on"
  }

  case object RuxLand ngPage extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.RuxLand ngPage
    overr de val toOffl neThr ft: TOffl neD splayLocat on = TOffl neD splayLocat on.RuxLand ngPage
    overr de val toFsNa : Str ng = "RuxLand ngPage"
  }

  case object Prof leBonusFollow extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Prof leBonusFollow
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.Prof leBonusFollow
    overr de val toFsNa : Str ng = "Prof leBonusFollow"
  }

  case object Elect onExploreWtf extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Elect onExploreWtf
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.Elect onExploreWtf
    overr de val toFsNa : Str ng = "Elect onExploreWtf"
  }

  case object ClusterFollow extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.ClusterFollow
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.ClusterFollow
    overr de val toFsNa : Str ng = "ClusterFollow"
    overr de val toAdD splayLocat on: Opt on[AdD splayLocat on] = So (
      AdD splayLocat on.ClusterFollow
    )
  }

  case object HtlBonusFollow extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.HtlBonusFollow
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.HtlBonusFollow
    overr de val toFsNa : Str ng = "HtlBonusFollow"
  }

  case object Top cLand ngPage ader extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Top cLand ngPage ader
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.Top cLand ngPage ader
    overr de val toFsNa : Str ng = "Top cLand ngPage ader"
  }

  case object NewUserSarusBackf ll extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.NewUserSarusBackf ll
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.NewUserSarusBackf ll
    overr de val toFsNa : Str ng = "NewUserSarusBackf ll"
  }

  case object NuxPymk extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.NuxPymk
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.NuxPymk
    overr de val toFsNa : Str ng = "NuxPymk"
  }

  case object Nux nterests extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Nux nterests
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.Nux nterests
    overr de val toFsNa : Str ng = "Nux nterests"
  }

  case object NuxTop cBonusFollow extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.NuxTop cBonusFollow
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.NuxTop cBonusFollow
    overr de val toFsNa : Str ng = "NuxTop cBonusFollow"
  }

  case object S debar extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.S debar
    overr de val toOffl neThr ft: TOffl neD splayLocat on = TOffl neD splayLocat on.S debar
    overr de val toFsNa : Str ng = "S debar"

    overr de val toAdD splayLocat on: Opt on[AdD splayLocat on] = So (
      AdD splayLocat on.WtfS debar
    )
  }

  case object Campa gnForm extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Campa gnForm
    overr de val toOffl neThr ft: TOffl neD splayLocat on = TOffl neD splayLocat on.Campa gnForm
    overr de val toFsNa : Str ng = "Campa gnForm"
  }

  case object Prof leTopFollo rs extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Prof leTopFollo rs
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.Prof leTopFollo rs
    overr de val toFsNa : Str ng = "Prof leTopFollo rs"
  }

  case object Prof leTopFollow ng extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Prof leTopFollow ng
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.Prof leTopFollow ng
    overr de val toFsNa : Str ng = "Prof leTopFollow ng"
  }

  case object RuxPymk extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.RuxPymk
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.RuxPymk
    overr de val toFsNa : Str ng = "RuxPymk"
  }

  case object  nd aCov d19CuratedAccountsWtf extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on. nd aCov d19CuratedAccountsWtf
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on. nd aCov d19CuratedAccountsWtf
    overr de val toFsNa : Str ng = " nd aCov d19CuratedAccountsWtf"
  }

  case object PeoplePlusPlus extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.PeoplePlusPlus
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.PeoplePlusPlus
    overr de val toFsNa : Str ng = "PeoplePlusPlus"
  }

  case object T etNot f cat onRecs extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.T etNot f cat onRecs
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.T etNot f cat onRecs
    overr de val toFsNa : Str ng = "T etNot f cat onRecs"
  }

  case object Prof leDev ceFollow extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Prof leDev ceFollow
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.Prof leDev ceFollow
    overr de val toFsNa : Str ng = "Prof leDev ceFollow"
  }

  case object RecosBackf ll extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.RecosBackf ll
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.RecosBackf ll
    overr de val toFsNa : Str ng = "RecosBackf ll"
  }

  case object HtlSpaceHosts extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.HtlSpaceHosts
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.HtlSpaceHosts
    overr de val toFsNa : Str ng = "HtlSpaceHosts"
  }

  case object PostNuxFollowTask extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.PostNuxFollowTask
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.PostNuxFollowTask
    overr de val toFsNa : Str ng = "PostNuxFollowTask"
  }

  case object Top cLand ngPage extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Top cLand ngPage
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.Top cLand ngPage
    overr de val toFsNa : Str ng = "Top cLand ngPage"
  }

  case object UserTypea adPrefetch extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.UserTypea adPrefetch
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.UserTypea adPrefetch
    overr de val toFsNa : Str ng = "UserTypea adPrefetch"
  }

  case object Ho T  l neRelatableAccounts extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Ho T  l neRelatableAccounts
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.Ho T  l neRelatableAccounts
    overr de val toFsNa : Str ng = "Ho T  l neRelatableAccounts"
  }

  case object NuxGeoCategory extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.NuxGeoCategory
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.NuxGeoCategory
    overr de val toFsNa : Str ng = "NuxGeoCategory"
  }

  case object Nux nterestsCategory extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Nux nterestsCategory
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.Nux nterestsCategory
    overr de val toFsNa : Str ng = "Nux nterestsCategory"
  }

  case object TopArt cles extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.TopArt cles
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.TopArt cles
    overr de val toFsNa : Str ng = "TopArt cles"
  }

  case object NuxPymkCategory extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.NuxPymkCategory
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.NuxPymkCategory
    overr de val toFsNa : Str ng = "NuxPymkCategory"
  }

  case object Ho T  l neT etRecs extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Ho T  l neT etRecs
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.Ho T  l neT etRecs
    overr de val toFsNa : Str ng = "Ho T  l neT etRecs"
  }

  case object HtlBulkFr endFollows extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.HtlBulkFr endFollows
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.HtlBulkFr endFollows
    overr de val toFsNa : Str ng = "HtlBulkFr endFollows"
  }

  case object NuxAutoFollow extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.NuxAutoFollow
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.NuxAutoFollow
    overr de val toFsNa : Str ng = "NuxAutoFollow"
  }

  case object SearchBonusFollow extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.SearchBonusFollow
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.SearchBonusFollow
    overr de val toFsNa : Str ng = "SearchBonusFollow"
  }

  case object ContentRecom nder extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.ContentRecom nder
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.ContentRecom nder
    overr de val toFsNa : Str ng = "ContentRecom nder"
  }

  case object Ho T  l neReverseChron extends D splayLocat on {
    overr de val toThr ft: TD splayLocat on = TD splayLocat on.Ho T  l neReverseChron
    overr de val toOffl neThr ft: TOffl neD splayLocat on =
      TOffl neD splayLocat on.Ho T  l neReverseChron
    overr de val toFsNa : Str ng = "Ho T  l neReverseChron"
  }

  def fromThr ft(d splayLocat on: TD splayLocat on): D splayLocat on = d splayLocat on match {
    case TD splayLocat on.Prof leS debar => Prof leS debar
    case TD splayLocat on.Ho T  l ne => Ho T  l ne
    case TD splayLocat on.Mag cRecs => Mag cRecs
    case TD splayLocat on.AbUpload nject on => AbUpload nject on
    case TD splayLocat on.RuxLand ngPage => RuxLand ngPage
    case TD splayLocat on.Prof leBonusFollow => Prof leBonusFollow
    case TD splayLocat on.Elect onExploreWtf => Elect onExploreWtf
    case TD splayLocat on.ClusterFollow => ClusterFollow
    case TD splayLocat on.HtlBonusFollow => HtlBonusFollow
    case TD splayLocat on.React veFollow => React veFollow
    case TD splayLocat on.Top cLand ngPage ader => Top cLand ngPage ader
    case TD splayLocat on.NewUserSarusBackf ll => NewUserSarusBackf ll
    case TD splayLocat on.NuxPymk => NuxPymk
    case TD splayLocat on.Nux nterests => Nux nterests
    case TD splayLocat on.NuxTop cBonusFollow => NuxTop cBonusFollow
    case TD splayLocat on.ExploreTab => ExploreTab
    case TD splayLocat on.S debar => S debar
    case TD splayLocat on.Campa gnForm => Campa gnForm
    case TD splayLocat on.Prof leTopFollo rs => Prof leTopFollo rs
    case TD splayLocat on.Prof leTopFollow ng => Prof leTopFollow ng
    case TD splayLocat on.RuxPymk => RuxPymk
    case TD splayLocat on. nd aCov d19CuratedAccountsWtf =>  nd aCov d19CuratedAccountsWtf
    case TD splayLocat on.PeoplePlusPlus => PeoplePlusPlus
    case TD splayLocat on.T etNot f cat onRecs => T etNot f cat onRecs
    case TD splayLocat on.Prof leDev ceFollow => Prof leDev ceFollow
    case TD splayLocat on.RecosBackf ll => RecosBackf ll
    case TD splayLocat on.HtlSpaceHosts => HtlSpaceHosts
    case TD splayLocat on.PostNuxFollowTask => PostNuxFollowTask
    case TD splayLocat on.Top cLand ngPage => Top cLand ngPage
    case TD splayLocat on.UserTypea adPrefetch => UserTypea adPrefetch
    case TD splayLocat on.Ho T  l neRelatableAccounts => Ho T  l neRelatableAccounts
    case TD splayLocat on.NuxGeoCategory => NuxGeoCategory
    case TD splayLocat on.Nux nterestsCategory => Nux nterestsCategory
    case TD splayLocat on.TopArt cles => TopArt cles
    case TD splayLocat on.NuxPymkCategory => NuxPymkCategory
    case TD splayLocat on.Ho T  l neT etRecs => Ho T  l neT etRecs
    case TD splayLocat on.HtlBulkFr endFollows => HtlBulkFr endFollows
    case TD splayLocat on.NuxAutoFollow => NuxAutoFollow
    case TD splayLocat on.SearchBonusFollow => SearchBonusFollow
    case TD splayLocat on.ContentRecom nder => ContentRecom nder
    case TD splayLocat on.Ho T  l neReverseChron => Ho T  l neReverseChron
    case TD splayLocat on.EnumUnknownD splayLocat on( ) =>
      throw new UnknownD splayLocat onExcept on(
        s"Unknown d splay locat on thr ft enum w h value: ${ }")
  }

  def fromOffl neThr ft(d splayLocat on: TOffl neD splayLocat on): D splayLocat on =
    d splayLocat on match {
      case TOffl neD splayLocat on.Prof leS debar => Prof leS debar
      case TOffl neD splayLocat on.Ho T  l ne => Ho T  l ne
      case TOffl neD splayLocat on.Mag cRecs => Mag cRecs
      case TOffl neD splayLocat on.AbUpload nject on => AbUpload nject on
      case TOffl neD splayLocat on.RuxLand ngPage => RuxLand ngPage
      case TOffl neD splayLocat on.Prof leBonusFollow => Prof leBonusFollow
      case TOffl neD splayLocat on.Elect onExploreWtf => Elect onExploreWtf
      case TOffl neD splayLocat on.ClusterFollow => ClusterFollow
      case TOffl neD splayLocat on.HtlBonusFollow => HtlBonusFollow
      case TOffl neD splayLocat on.Top cLand ngPage ader => Top cLand ngPage ader
      case TOffl neD splayLocat on.NewUserSarusBackf ll => NewUserSarusBackf ll
      case TOffl neD splayLocat on.NuxPymk => NuxPymk
      case TOffl neD splayLocat on.Nux nterests => Nux nterests
      case TOffl neD splayLocat on.NuxTop cBonusFollow => NuxTop cBonusFollow
      case TOffl neD splayLocat on.ExploreTab => ExploreTab
      case TOffl neD splayLocat on.React veFollow => React veFollow
      case TOffl neD splayLocat on.S debar => S debar
      case TOffl neD splayLocat on.Campa gnForm => Campa gnForm
      case TOffl neD splayLocat on.Prof leTopFollo rs => Prof leTopFollo rs
      case TOffl neD splayLocat on.Prof leTopFollow ng => Prof leTopFollow ng
      case TOffl neD splayLocat on.RuxPymk => RuxPymk
      case TOffl neD splayLocat on. nd aCov d19CuratedAccountsWtf =>  nd aCov d19CuratedAccountsWtf
      case TOffl neD splayLocat on.PeoplePlusPlus => PeoplePlusPlus
      case TOffl neD splayLocat on.T etNot f cat onRecs => T etNot f cat onRecs
      case TOffl neD splayLocat on.Prof leDev ceFollow => Prof leDev ceFollow
      case TOffl neD splayLocat on.RecosBackf ll => RecosBackf ll
      case TOffl neD splayLocat on.HtlSpaceHosts => HtlSpaceHosts
      case TOffl neD splayLocat on.PostNuxFollowTask => PostNuxFollowTask
      case TOffl neD splayLocat on.Top cLand ngPage => Top cLand ngPage
      case TOffl neD splayLocat on.UserTypea adPrefetch => UserTypea adPrefetch
      case TOffl neD splayLocat on.Ho T  l neRelatableAccounts => Ho T  l neRelatableAccounts
      case TOffl neD splayLocat on.NuxGeoCategory => NuxGeoCategory
      case TOffl neD splayLocat on.Nux nterestsCategory => Nux nterestsCategory
      case TOffl neD splayLocat on.TopArt cles => TopArt cles
      case TOffl neD splayLocat on.NuxPymkCategory => NuxPymkCategory
      case TOffl neD splayLocat on.Ho T  l neT etRecs => Ho T  l neT etRecs
      case TOffl neD splayLocat on.HtlBulkFr endFollows => HtlBulkFr endFollows
      case TOffl neD splayLocat on.NuxAutoFollow => NuxAutoFollow
      case TOffl neD splayLocat on.SearchBonusFollow => SearchBonusFollow
      case TOffl neD splayLocat on.ContentRecom nder => ContentRecom nder
      case TOffl neD splayLocat on.Ho T  l neReverseChron => Ho T  l neReverseChron
      case TOffl neD splayLocat on.EnumUnknownOffl neD splayLocat on( ) =>
        throw new UnknownD splayLocat onExcept on(
          s"Unknown offl ne d splay locat on thr ft enum w h value: ${ }")
    }
}

class UnknownD splayLocat onExcept on( ssage: Str ng) extends Except on( ssage)
