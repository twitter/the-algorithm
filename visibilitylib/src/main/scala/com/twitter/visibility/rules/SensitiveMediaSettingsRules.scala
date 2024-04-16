package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.rules.Cond  on.V e rHasAdult d aSett ngLevel
 mport com.tw ter.v s b l y.rules.Cond  on.V e rHasV olent d aSett ngLevel
 mport com.tw ter.v s b l y.rules.Cond  on.V e rHasOt rSens  ve d aSett ngLevel
 mport com.tw ter.v s b l y.rules.Cond  on.Logged nV e r
 mport com.tw ter.v s b l y.rules.Cond  on.LoggedOutV e r
 mport com.tw ter.v s b l y.rules.Cond  on.T etHasNsfwUserAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.T etHasNsfwAdm nAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.And
 mport com.tw ter.v s b l y.rules.Cond  on.Or
 mport com.tw ter.v s b l y.rules.Cond  on.Not
 mport com.tw ter.v s b l y.rules.Cond  on.NonAuthorV e r
 mport com.tw ter.v s b l y.rules.Cond  on.T etHas d a
 mport com.tw ter.v s b l y.rules.Reason.Nsfw
 mport com.tw ter.v s b l y.models.T etSafetyLabelType
 mport com.tw ter.content alth.sens  ve d asett ngs.thr ftscala.Sens  ve d aSett ngsLevel


abstract class Adult d aT etLabelDropRule(t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
      Drop(Nsfw),
      And(Logged nV e r, V e rHasAdult d aSett ngLevel(Sens  ve d aSett ngsLevel.Drop)),
      t etSafetyLabelType
    )

abstract class V olent d aT etLabelDropRule(t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
      Drop(Nsfw),
      And(Logged nV e r, V e rHasV olent d aSett ngLevel(Sens  ve d aSett ngsLevel.Drop)),
      t etSafetyLabelType
    )

abstract class Ot rSens  ve d aT etLabelDropRule(cond  on: Cond  on)
    extends RuleW hConstantAct on(
      Drop(Nsfw),
      And(
        cond  on,
        And(
          T etHas d a,
          Logged nV e r,
          V e rHasOt rSens  ve d aSett ngLevel(Sens  ve d aSett ngsLevel.Drop)))
    )

abstract class Adult d aT etLabel nterst  alRule(t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
       nterst  al(Nsfw),
      Or(
        LoggedOutV e r,
        V e rHasAdult d aSett ngLevel(Sens  ve d aSett ngsLevel.Warn),
        Not(V e rHasAdult d aSett ngLevel(Sens  ve d aSett ngsLevel.Allow))
      ),
      t etSafetyLabelType
    )

abstract class V olent d aT etLabel nterst  alRule(t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
       nterst  al(Nsfw),
      Or(
        LoggedOutV e r,
        V e rHasV olent d aSett ngLevel(Sens  ve d aSett ngsLevel.Warn),
        Not(V e rHasV olent d aSett ngLevel(Sens  ve d aSett ngsLevel.Allow))
      ),
      t etSafetyLabelType
    )

abstract class Ot rSens  ve d aT etLabel nterst  alRule(cond  on: Cond  on)
    extends RuleW hConstantAct on(
       nterst  al(Nsfw),
      And(
        cond  on,
        T etHas d a,
        Or(
          LoggedOutV e r,
          V e rHasOt rSens  ve d aSett ngLevel(Sens  ve d aSett ngsLevel.Warn)
        )
      )
    )

abstract class Adult d aT etLabelDropSett ngLevelTombstoneRule(
  t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
      Tombstone(Ep aph.Adult d a),
      And(
        Logged nV e r,
        NonAuthorV e r,
        V e rHasAdult d aSett ngLevel(Sens  ve d aSett ngsLevel.Drop)),
      t etSafetyLabelType
    )

abstract class V olent d aT etLabelDropSett ngLevelTombstoneRule(
  t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
      Tombstone(Ep aph.V olent d a),
      And(
        Logged nV e r,
        NonAuthorV e r,
        V e rHasV olent d aSett ngLevel(Sens  ve d aSett ngsLevel.Drop)),
      t etSafetyLabelType
    )

abstract class Ot rSens  ve d aT etLabelDropSett ngLevelTombstoneRule(cond  on: Cond  on)
    extends RuleW hConstantAct on(
      Tombstone(Ep aph.Ot rSens  ve d a),
      And(
        cond  on,
        And(
          T etHas d a,
          Logged nV e r,
          NonAuthorV e r,
          V e rHasOt rSens  ve d aSett ngLevel(Sens  ve d aSett ngsLevel.Drop))
      )
    )

case object Sens  ve d aT etDropRules {


  object Adult d aNsfwH ghPrec s onT etLabelDropRule
      extends Adult d aT etLabelDropRule(
        T etSafetyLabelType.NsfwH ghPrec s on
      )

  object Adult d aNsfwCard mageT etLabelDropRule
      extends Adult d aT etLabelDropRule(
        T etSafetyLabelType.NsfwCard mage
      )

  object Adult d aNsfwReported ur st csT etLabelDropRule
      extends Adult d aT etLabelDropRule(
        T etSafetyLabelType.NsfwReported ur st cs
      )

  object Adult d aNsfwV deoT etLabelDropRule
      extends Adult d aT etLabelDropRule(
        T etSafetyLabelType.NsfwV deo
      )

  object Adult d aNsfwH ghRecallT etLabelDropRule
      extends Adult d aT etLabelDropRule(
        T etSafetyLabelType.NsfwH ghRecall
      )

  object Adult d aNsfwTextT etLabelDropRule
      extends Adult d aT etLabelDropRule(
        T etSafetyLabelType.NsfwText
      )

  object V olent d aGoreAndV olenceH ghPrec s onDropRule
      extends V olent d aT etLabelDropRule(
        T etSafetyLabelType.GoreAndV olenceH ghPrec s on
      )

  object V olent d aGoreAndV olenceReported ur st csDropRule
      extends V olent d aT etLabelDropRule(
        T etSafetyLabelType.GoreAndV olenceReported ur st cs
      )

  object Ot rSens  ve d aNsfwUserT etFlagDropRule
      extends Ot rSens  ve d aT etLabelDropRule(
        T etHasNsfwUserAuthor
      )

  object Ot rSens  ve d aNsfwAdm nT etFlagDropRule
      extends Ot rSens  ve d aT etLabelDropRule(
        T etHasNsfwAdm nAuthor
      )
}

case object Sens  ve d aT et nterst  alRules {

  object Adult d aNsfwH ghPrec s onT etLabel nterst  alRule
      extends Adult d aT etLabel nterst  alRule(
        T etSafetyLabelType.NsfwH ghPrec s on
      )
      w h DoesLogVerd ct

  object Adult d aNsfwCard mageT etLabel nterst  alRule
      extends Adult d aT etLabel nterst  alRule(
        T etSafetyLabelType.NsfwCard mage
      )

  object Adult d aNsfwReported ur st csT etLabel nterst  alRule
      extends Adult d aT etLabel nterst  alRule(
        T etSafetyLabelType.NsfwReported ur st cs
      )

  object Adult d aNsfwV deoT etLabel nterst  alRule
      extends Adult d aT etLabel nterst  alRule(
        T etSafetyLabelType.NsfwV deo
      )

  object Adult d aNsfwH ghRecallT etLabel nterst  alRule
      extends Adult d aT etLabel nterst  alRule(
        T etSafetyLabelType.NsfwH ghRecall
      )

  object Adult d aNsfwTextT etLabel nterst  alRule
      extends Adult d aT etLabel nterst  alRule(
        T etSafetyLabelType.NsfwText
      )

  object V olent d aGoreAndV olenceH ghPrec s on nterst  alRule
      extends V olent d aT etLabel nterst  alRule(
        T etSafetyLabelType.GoreAndV olenceH ghPrec s on
      )
      w h DoesLogVerd ct

  object V olent d aGoreAndV olenceReported ur st cs nterst  alRule
      extends V olent d aT etLabel nterst  alRule(
        T etSafetyLabelType.GoreAndV olenceReported ur st cs
      )

  object Ot rSens  ve d aNsfwUserT etFlag nterst  alRule
      extends Ot rSens  ve d aT etLabel nterst  alRule(
        T etHasNsfwUserAuthor
      )

  object Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule
      extends Ot rSens  ve d aT etLabel nterst  alRule(
        T etHasNsfwAdm nAuthor
      )

}

case object Sens  ve d aT etDropSett ngLevelTombstoneRules {


  object Adult d aNsfwH ghPrec s onT etLabelDropSett ngLevelTombstoneRule
      extends Adult d aT etLabelDropSett ngLevelTombstoneRule(
        T etSafetyLabelType.NsfwH ghPrec s on
      )

  object Adult d aNsfwCard mageT etLabelDropSett ngLevelTombstoneRule
      extends Adult d aT etLabelDropSett ngLevelTombstoneRule(
        T etSafetyLabelType.NsfwCard mage
      )

  object Adult d aNsfwReported ur st csT etLabelDropSett ngLevelTombstoneRule
      extends Adult d aT etLabelDropSett ngLevelTombstoneRule(
        T etSafetyLabelType.NsfwReported ur st cs
      )

  object Adult d aNsfwV deoT etLabelDropSett ngLevelTombstoneRule
      extends Adult d aT etLabelDropSett ngLevelTombstoneRule(
        T etSafetyLabelType.NsfwV deo
      )

  object Adult d aNsfwH ghRecallT etLabelDropSett ngLevelTombstoneRule
      extends Adult d aT etLabelDropSett ngLevelTombstoneRule(
        T etSafetyLabelType.NsfwH ghRecall
      )

  object Adult d aNsfwTextT etLabelDropSett ngLevelTombstoneRule
      extends Adult d aT etLabelDropSett ngLevelTombstoneRule(
        T etSafetyLabelType.NsfwText
      )

  object V olent d aGoreAndV olenceH ghPrec s onDropSett ngLeveTombstoneRule
      extends V olent d aT etLabelDropSett ngLevelTombstoneRule(
        T etSafetyLabelType.GoreAndV olenceH ghPrec s on
      )

  object V olent d aGoreAndV olenceReported ur st csDropSett ngLevelTombstoneRule
      extends V olent d aT etLabelDropSett ngLevelTombstoneRule(
        T etSafetyLabelType.GoreAndV olenceReported ur st cs
      )

  object Ot rSens  ve d aNsfwUserT etFlagDropSett ngLevelTombstoneRule
      extends Ot rSens  ve d aT etLabelDropSett ngLevelTombstoneRule(
        T etHasNsfwUserAuthor
      )

  object Ot rSens  ve d aNsfwAdm nT etFlagDropSett ngLevelTombstoneRule
      extends Ot rSens  ve d aT etLabelDropSett ngLevelTombstoneRule(
        T etHasNsfwAdm nAuthor
      )
}
