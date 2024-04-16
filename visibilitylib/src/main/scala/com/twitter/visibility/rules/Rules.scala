package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableAuthorBlocksV e rDropRuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.Enable nnerQuotedT etV e rBlocksAuthor nterst  alRuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.Enable nnerQuotedT etV e rMutesAuthor nterst  alRuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableT  l neHo PromotedT et althEnforce ntRules
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableV e r sSoftUserDropRuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.PromotedT et althEnforce ntHoldback
 mport com.tw ter.v s b l y.rules.Cond  on.And
 mport com.tw ter.v s b l y.rules.Cond  on. sQuoted nnerT et
 mport com.tw ter.v s b l y.rules.Cond  on.NonAuthorV e r
 mport com.tw ter.v s b l y.rules.Cond  on.Not
 mport com.tw ter.v s b l y.rules.Cond  on.Ret et
 mport com.tw ter.v s b l y.rules.Cond  on.SoftV e r
 mport com.tw ter.v s b l y.rules.Reason._

object DropAllRule
    extends AlwaysActRule(
      Drop(Unspec f ed)
    )

object AllowAllRule
    extends AlwaysActRule(
      Allow
    )

object TestRule
    extends AlwaysActRule(
      Drop(Unspec f ed)
    )

object Deact vatedAuthorRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Deact vatedAuthor),
      Cond  on.Deact vatedAuthor
    )

object ErasedAuthorRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(ErasedAuthor),
      Cond  on.ErasedAuthor
    )

object OffboardedAuthorRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(OffboardedAuthor),
      Cond  on.OffboardedAuthor
    )

object DropNsfwUserAuthorRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Nsfw),
      Cond  on.NsfwUserAuthor
    )

object DropNsfwUserAuthorV e rOpt nF lter ngOnSearchRule
    extends V e rOpt nF lter ngOnSearchRule(
      Drop(Nsfw),
      Cond  on.NsfwUserAuthor
    )

object  nterst  alNsfwUserAuthorRule
    extends OnlyW nNotAuthorV e rRule(
       nterst  al(Nsfw),
      Cond  on.NsfwUserAuthor
    )

object DropNsfwAdm nAuthorRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Nsfw),
      Cond  on.NsfwAdm nAuthor
    )

object DropNsfwAdm nAuthorV e rOpt nF lter ngOnSearchRule
    extends V e rOpt nF lter ngOnSearchRule(
      Drop(Nsfw),
      Cond  on.NsfwAdm nAuthor
    )

object  nterst  alNsfwAdm nAuthorRule
    extends OnlyW nNotAuthorV e rRule(
       nterst  al(Nsfw),
      Cond  on.NsfwAdm nAuthor
    )

object ProtectedAuthorDropRule
    extends RuleW hConstantAct on(
      Drop(Reason.ProtectedAuthor),
      And(Cond  on.LoggedOutOrV e rNotFollow ngAuthor, Cond  on.ProtectedAuthor)
    )

object ProtectedAuthorTombstoneRule
    extends RuleW hConstantAct on(
      Tombstone(Ep aph.Protected),
      And(Cond  on.LoggedOutOrV e rNotFollow ngAuthor, Cond  on.ProtectedAuthor)
    )

object DropAllProtectedAuthorRule
    extends RuleW hConstantAct on(
      Drop(Reason.ProtectedAuthor),
      Cond  on.ProtectedAuthor
    ) {
  overr de def enableFa lClosed: Seq[RuleParam[Boolean]] = Seq(RuleParams.True)
}

object ProtectedQuoteT etAuthorRule
    extends RuleW hConstantAct on(
      Drop(Reason.ProtectedAuthor),
      And(Cond  on.OuterAuthorNotFollow ngAuthor, Cond  on.ProtectedAuthor)
    )

object DropProtectedV e r fPresentRule
    extends RuleW hConstantAct on(
      Drop(Reason.Unspec f ed),
      And(Cond  on.Logged nV e r, Cond  on.ProtectedV e r)
    ) {
  overr de def enableFa lClosed: Seq[RuleParam[Boolean]] = Seq(RuleParams.True)
}

object SuspendedAuthorRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(SuspendedAuthor),
      Cond  on.SuspendedAuthor
    )

object SuspendedV e rRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Unspec f ed),
      Cond  on.SuspendedV e r
    )

object Deact vatedV e rRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Unspec f ed),
      Cond  on.Deact vatedV e r
    )

object V e r sUn nt onedRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Reason.V e r sUn nt oned),
      Cond  on.V e r sUn nt oned
    )

abstract class AuthorBlocksV e rRule(overr de val act on: Act on)
    extends OnlyW nNotAuthorV e rRule(
      act on,
      Cond  on.AuthorBlocksV e r
    )

object AuthorBlocksV e rDropRule
    extends AuthorBlocksV e rRule(
      Drop(Reason.AuthorBlocksV e r)
    )

object Dec derableAuthorBlocksV e rDropRule
    extends AuthorBlocksV e rRule(
      Drop(Reason.AuthorBlocksV e r)
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableAuthorBlocksV e rDropRuleParam)
}

object AuthorBlocksV e rTombstoneRule
    extends AuthorBlocksV e rRule(
      Tombstone(Ep aph.BlockedBy)
    )

object V e rBlocksAuthorRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Reason.V e rBlocksAuthor),
      Cond  on.V e rBlocksAuthor
    )

object V e rBlocksAuthorV e rOpt nBlock ngOnSearchRule
    extends V e rOpt nBlock ngOnSearchRule(
      Drop(Reason.V e rBlocksAuthor),
      Cond  on.V e rBlocksAuthor
    )

object V e rMutesAuthorRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Reason.V e rMutesAuthor),
      Cond  on.V e rMutesAuthor
    )

object V e rMutesAuthorV e rOpt nBlock ngOnSearchRule
    extends V e rOpt nBlock ngOnSearchRule(
      Drop(Reason.V e rMutesAuthor),
      Cond  on.V e rMutesAuthor
    )

object AuthorBlocksOuterAuthorRule
    extends RuleW hConstantAct on(
      Drop(Reason.AuthorBlocksV e r),
      And(Not(Cond  on. sSelfQuote), Cond  on.AuthorBlocksOuterAuthor)
    )

object V e rMutesAndDoesNotFollowAuthorRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Reason.V e rHardMutedAuthor),
      And(Cond  on.V e rMutesAuthor, Not(Cond  on.V e rDoesFollowAuthor))
    )

object AuthorBlocksV e rUnspec f edRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Reason.Unspec f ed),
      Cond  on.AuthorBlocksV e r
    )

object V e rHasMatch ngMutedKeywordForNot f cat onsRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Reason.MutedKeyword),
      Cond  on.V e rHasMatch ngKeywordForNot f cat ons
    )

object V e rHasMatch ngMutedKeywordForHo T  l neRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Reason.MutedKeyword),
      Cond  on.V e rHasMatch ngKeywordForHo T  l ne
    )

tra  HasPromotedT et althEnforce nt extends W hGate {
  overr de def holdbacks: Seq[RuleParam[Boolean]] = Seq(PromotedT et althEnforce ntHoldback)
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
    EnableT  l neHo PromotedT et althEnforce ntRules)
}

object V e rHasMatch ngMutedKeywordForHo T  l nePromotedT etRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Reason.MutedKeyword),
      Cond  on.V e rHasMatch ngKeywordForHo T  l ne
    )
    w h HasPromotedT et althEnforce nt

object V e rHasMatch ngMutedKeywordForT etRepl esRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Reason.MutedKeyword),
      Cond  on.V e rHasMatch ngKeywordForT etRepl es
    )

object MutedKeywordForT etRepl es nterst  alRule
    extends OnlyW nNotAuthorV e rRule(
       nterst  al(Reason.MutedKeyword),
      Cond  on.V e rHasMatch ngKeywordForT etRepl es
    )

object MutedKeywordForQuotedT etT etDeta l nterst  alRule
    extends OnlyW nNotAuthorV e rRule(
       nterst  al(Reason.MutedKeyword),
      And(Cond  on. sQuoted nnerT et, Cond  on.V e rHasMatch ngKeywordForT etRepl es)
    )

object V e rMutesAuthor nterst  alRule
    extends OnlyW nNotAuthorV e rRule(
       nterst  al(Reason.V e rMutesAuthor),
      Cond  on.V e rMutesAuthor
    )

object V e rMutesAuthor nnerQuotedT et nterst  alRule
    extends OnlyW nNotAuthorV e rRule(
       nterst  al(Reason.V e rMutesAuthor),
      And(Cond  on.V e rMutesAuthor,  sQuoted nnerT et)
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(Enable nnerQuotedT etV e rMutesAuthor nterst  alRuleParam)
}

object V e rMutesAuthorHo T  l nePromotedT etRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Reason.V e rMutesAuthor),
      Cond  on.V e rMutesAuthor
    )
    w h HasPromotedT et althEnforce nt

object V e rBlocksAuthor nterst  alRule
    extends OnlyW nNotAuthorV e rRule(
       nterst  al(Reason.V e rBlocksAuthor),
      Cond  on.V e rBlocksAuthor
    )

object V e rBlocksAuthor nnerQuotedT et nterst  alRule
    extends OnlyW nNotAuthorV e rRule(
       nterst  al(Reason.V e rBlocksAuthor),
      And(Cond  on.V e rBlocksAuthor,  sQuoted nnerT et)
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(Enable nnerQuotedT etV e rBlocksAuthor nterst  alRuleParam)
}

object V e rBlocksAuthorHo T  l nePromotedT etRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Reason.V e rBlocksAuthor),
      Cond  on.V e rBlocksAuthor
    )
    w h HasPromotedT et althEnforce nt

object V e rReportsAuthor nterst  alRule
    extends OnlyW nNotAuthorV e rRule(
       nterst  al(Reason.V e rReportedAuthor),
      Cond  on.V e rReportsAuthor
    )

object V e r sAuthorDropRule
    extends RuleW hConstantAct on(Drop(Unspec f ed), Not(NonAuthorV e r))

object V e r sNotAuthorDropRule extends RuleW hConstantAct on(Drop(Unspec f ed), NonAuthorV e r)

object Ret etDropRule extends RuleW hConstantAct on(Drop(Unspec f ed), Ret et)

object V e r sSoftUserDropRule extends RuleW hConstantAct on(Drop(V e r sSoftUser), SoftV e r) {

  overr de val enabled: Seq[RuleParam[Boolean]] = Seq(EnableV e r sSoftUserDropRuleParam)
}
