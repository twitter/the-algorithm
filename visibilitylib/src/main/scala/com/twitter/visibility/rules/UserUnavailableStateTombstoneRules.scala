package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.Enable nnerQuotedT etV e rBlocksAuthor nterst  alRuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.Enable nnerQuotedT etV e rMutesAuthor nterst  alRuleParam
 mport com.tw ter.v s b l y.rules.Cond  on.And
 mport com.tw ter.v s b l y.rules.Cond  on.AuthorBlocksV e r
 mport com.tw ter.v s b l y.rules.Cond  on.Deact vatedAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.ErasedAuthor
 mport com.tw ter.v s b l y.rules.Cond  on. sQuoted nnerT et
 mport com.tw ter.v s b l y.rules.Cond  on.OffboardedAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.ProtectedAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.Ret et
 mport com.tw ter.v s b l y.rules.Cond  on.SuspendedAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.Unava lableAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.V e rBlocksAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.V e rMutesAuthor

object UserUnava lableStateTombstoneRules {
  abstract class UserUnava lableStateT etTombstoneRule(ep aph: Ep aph, cond  on: Cond  on)
      extends RuleW hConstantAct on(Tombstone(ep aph), cond  on) {}

  abstract class UserUnava lableStateRet etTombstoneRule(ep aph: Ep aph, cond  on: Cond  on)
      extends RuleW hConstantAct on(Tombstone(ep aph), And(Ret et, cond  on)) {}

  abstract class UserUnava lableState nnerQuotedT etTombstoneRule(
    ep aph: Ep aph,
    cond  on: Cond  on)
      extends RuleW hConstantAct on(Tombstone(ep aph), And( sQuoted nnerT et, cond  on))

  abstract class UserUnava lableState nnerQuotedT et nterst  alRule(
    reason: Reason,
    cond  on: Cond  on)
      extends RuleW hConstantAct on( nterst  al(reason), And( sQuoted nnerT et, cond  on))

  object SuspendedUserUnava lableT etTombstoneRule
      extends UserUnava lableStateT etTombstoneRule(Ep aph.Suspended, SuspendedAuthor)

  object Deact vatedUserUnava lableT etTombstoneRule
      extends UserUnava lableStateT etTombstoneRule(Ep aph.Deact vated, Deact vatedAuthor)

  object OffBoardedUserUnava lableT etTombstoneRule
      extends UserUnava lableStateT etTombstoneRule(Ep aph.Offboarded, OffboardedAuthor)

  object ErasedUserUnava lableT etTombstoneRule
      extends UserUnava lableStateT etTombstoneRule(Ep aph.Deact vated, ErasedAuthor)

  object ProtectedUserUnava lableT etTombstoneRule
      extends UserUnava lableStateT etTombstoneRule(Ep aph.Protected, ProtectedAuthor)

  object AuthorBlocksV e rUserUnava lableT etTombstoneRule
      extends UserUnava lableStateT etTombstoneRule(Ep aph.BlockedBy, AuthorBlocksV e r)

  object UserUnava lableT etTombstoneRule
      extends UserUnava lableStateT etTombstoneRule(Ep aph.Unava lable, Unava lableAuthor)

  object SuspendedUserUnava lableRet etTombstoneRule
      extends UserUnava lableStateRet etTombstoneRule(Ep aph.Suspended, SuspendedAuthor)

  object Deact vatedUserUnava lableRet etTombstoneRule
      extends UserUnava lableStateRet etTombstoneRule(Ep aph.Deact vated, Deact vatedAuthor)

  object OffBoardedUserUnava lableRet etTombstoneRule
      extends UserUnava lableStateRet etTombstoneRule(Ep aph.Offboarded, OffboardedAuthor)

  object ErasedUserUnava lableRet etTombstoneRule
      extends UserUnava lableStateRet etTombstoneRule(Ep aph.Deact vated, ErasedAuthor)

  object ProtectedUserUnava lableRet etTombstoneRule
      extends UserUnava lableStateRet etTombstoneRule(Ep aph.Protected, ProtectedAuthor)

  object AuthorBlocksV e rUserUnava lableRet etTombstoneRule
      extends UserUnava lableStateRet etTombstoneRule(Ep aph.BlockedBy, AuthorBlocksV e r)

  object V e rBlocksAuthorUserUnava lableRet etTombstoneRule
      extends UserUnava lableStateRet etTombstoneRule(Ep aph.Unava lable, V e rBlocksAuthor)

  object V e rMutesAuthorUserUnava lableRet etTombstoneRule
      extends UserUnava lableStateRet etTombstoneRule(Ep aph.Unava lable, V e rMutesAuthor)

  object SuspendedUserUnava lable nnerQuotedT etTombstoneRule
      extends UserUnava lableState nnerQuotedT etTombstoneRule(Ep aph.Suspended, SuspendedAuthor)

  object Deact vatedUserUnava lable nnerQuotedT etTombstoneRule
      extends UserUnava lableState nnerQuotedT etTombstoneRule(
        Ep aph.Deact vated,
        Deact vatedAuthor)

  object OffBoardedUserUnava lable nnerQuotedT etTombstoneRule
      extends UserUnava lableState nnerQuotedT etTombstoneRule(
        Ep aph.Offboarded,
        OffboardedAuthor)

  object ErasedUserUnava lable nnerQuotedT etTombstoneRule
      extends UserUnava lableState nnerQuotedT etTombstoneRule(Ep aph.Deact vated, ErasedAuthor)

  object ProtectedUserUnava lable nnerQuotedT etTombstoneRule
      extends UserUnava lableState nnerQuotedT etTombstoneRule(Ep aph.Protected, ProtectedAuthor)

  object AuthorBlocksV e rUserUnava lable nnerQuotedT etTombstoneRule
      extends UserUnava lableState nnerQuotedT etTombstoneRule(
        Ep aph.BlockedBy,
        AuthorBlocksV e r)

  object V e rBlocksAuthorUserUnava lable nnerQuotedT et nterst  alRule
      extends UserUnava lableState nnerQuotedT et nterst  alRule(
        Reason.V e rBlocksAuthor,
        V e rBlocksAuthor) {
    overr de def enabled: Seq[RuleParam[Boolean]] =
      Seq(Enable nnerQuotedT etV e rBlocksAuthor nterst  alRuleParam)
  }

  object V e rMutesAuthorUserUnava lable nnerQuotedT et nterst  alRule
      extends UserUnava lableState nnerQuotedT et nterst  alRule(
        Reason.V e rMutesAuthor,
        V e rMutesAuthor) {
    overr de def enabled: Seq[RuleParam[Boolean]] =
      Seq(Enable nnerQuotedT etV e rMutesAuthor nterst  alRuleParam)
  }
}
