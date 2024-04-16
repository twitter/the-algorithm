package com.tw ter.v s b l y.rules.generators

 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.SafetyLevelGroup
 mport com.tw ter.v s b l y.models.V olat onLevel
 mport com.tw ter.v s b l y.rules.FreedomOfSpeechNotReachAct ons
 mport com.tw ter.v s b l y.rules.FreedomOfSpeechNotReachRules
 mport com.tw ter.v s b l y.rules.Rule
 mport com.tw ter.v s b l y.rules.generators.T etRuleGenerator.v olat onLevelPol c es

object T etRuleGenerator {
  pr vate val level3L m edAct ons: Seq[Str ng] = Seq(
    "l ke",
    "reply",
    "ret et",
    "quote_t et",
    "share_t et_v a",
    "add_to_bookmarks",
    "p n_to_prof le",
    "copy_l nk",
    "send_v a_dm")
  pr vate val v olat onLevelPol c es: Map[
    V olat onLevel,
    Map[UserType, T etV s b l yPol cy]
  ] = Map(
    V olat onLevel.Level1 -> Map(
      UserType.Follo r -> T etV s b l yPol cy
        .bu lder()
        .addGlobalRule(FreedomOfSpeechNotReachAct ons.Soft ntervent onAvo dAct on())
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.Not f cat ons,
          FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.Recom ndat ons,
          FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.Search,
          FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.Top cRecom ndat ons,
          FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelRule(
          SafetyLevel.T  l neHo Recom ndat ons,
          FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelRule(
          SafetyLevel.TrendsRepresentat veT et,
          FreedomOfSpeechNotReachAct ons.DropAct on())
        .bu ld,
      UserType.Author -> T etV s b l yPol cy
        .bu lder()
        .addGlobalRule(FreedomOfSpeechNotReachAct ons.AppealableAct on())
        .bu ld,
      UserType.Ot r -> T etV s b l yPol cy
        .bu lder()
        .addGlobalRule(FreedomOfSpeechNotReachAct ons.Soft ntervent onAvo dAct on())
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.Not f cat ons,
          FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.Recom ndat ons,
          FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.T  l neHo ,
          FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.Search,
          FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.Top cRecom ndat ons,
          FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelRule(
          SafetyLevel.TrendsRepresentat veT et,
          FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelRule(
          SafetyLevel.Conversat onReply,
          FreedomOfSpeechNotReachAct ons.Soft ntervent onAvo dAbus veQual yReplyAct on())
        .bu ld,
    ),
    V olat onLevel.Level3 -> Map(
      UserType.Follo r -> T etV s b l yPol cy
        .bu lder()
        .addGlobalRule(FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.T  l neProf le,
          FreedomOfSpeechNotReachAct ons.Soft ntervent onAvo dL m edEngage ntsAct on(
            l m edAct onStr ngs = So (level3L m edAct ons))
        )
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.T etDeta ls,
          FreedomOfSpeechNotReachAct ons.Soft ntervent onAvo dL m edEngage ntsAct on(
            l m edAct onStr ngs = So (level3L m edAct ons))
        )
        .addSafetyLevelRule(
          SafetyLevel.Conversat onReply,
          FreedomOfSpeechNotReachAct ons.Soft ntervent onAvo dL m edEngage ntsAct on(
            l m edAct onStr ngs = So (level3L m edAct ons))
        )
        .addSafetyLevelRule(
          SafetyLevel.Conversat onFocalT et,
          FreedomOfSpeechNotReachAct ons.Soft ntervent onAvo dL m edEngage ntsAct on(
            l m edAct onStr ngs = So (level3L m edAct ons))
        )
        .bu ld,
      UserType.Author -> T etV s b l yPol cy
        .bu lder()
        .addGlobalRule(
          FreedomOfSpeechNotReachAct ons.AppealableAvo dL m edEngage ntsAct on(
            l m edAct onStr ngs = So (level3L m edAct ons))
        )
        .bu ld,
      UserType.Ot r -> T etV s b l yPol cy
        .bu lder()
        .addGlobalRule(FreedomOfSpeechNotReachAct ons.DropAct on())
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.T  l neProf le,
          FreedomOfSpeechNotReachAct ons
            . nterst  alL m edEngage ntsAvo dAct on(l m edAct onStr ngs =
              So (level3L m edAct ons))
        )
        .addSafetyLevelGroupRule(
          SafetyLevelGroup.T etDeta ls,
          FreedomOfSpeechNotReachAct ons
            . nterst  alL m edEngage ntsAvo dAct on(l m edAct onStr ngs =
              So (level3L m edAct ons))
        )
        .addSafetyLevelRule(
          SafetyLevel.Conversat onReply,
          FreedomOfSpeechNotReachAct ons
            . nterst  alL m edEngage ntsAvo dAct on(l m edAct onStr ngs =
              So (level3L m edAct ons))
        )
        .addSafetyLevelRule(
          SafetyLevel.Conversat onFocalT et,
          FreedomOfSpeechNotReachAct ons
            . nterst  alL m edEngage ntsAvo dAct on(l m edAct onStr ngs =
              So (level3L m edAct ons))
        )
        .bu ld,
    ),
  )
}
sealed tra  UserType
object UserType {
  case object Author extends UserType

  case object Follo r extends UserType

  case object Ot r extends UserType
}
class T etRuleGenerator extends RuleGenerator {

  pr vate[rules] val t etRulesForSurface: Map[SafetyLevel, Seq[Rule]] = generateT etPol c es()

  pr vate[rules] def getV olat onLevelPol c es = v olat onLevelPol c es

  overr de def rulesForSurface(safetyLevel: SafetyLevel): Seq[Rule] =
    t etRulesForSurface.getOrElse(safetyLevel, Seq())

  pr vate def generateRulesForPol cy(
    v olat onLevel: V olat onLevel,
    userType: UserType,
    t etV s b l yPol cy: T etV s b l yPol cy
  ): Seq[(SafetyLevel, Rule)] = {
    t etV s b l yPol cy
      .getRules()
      .map {
        case (safetyLevel, act onBu lder) =>
          safetyLevel -> (userType match {
            case UserType.Author =>
              FreedomOfSpeechNotReachRules.V e r sAuthorAndT etHasV olat onOfLevel(
                v olat onLevel = v olat onLevel,
                act onBu lder = act onBu lder.w hV olat onLevel(v olat onLevel = v olat onLevel))
            case UserType.Follo r =>
              FreedomOfSpeechNotReachRules.V e r sFollo rAndT etHasV olat onOfLevel(
                v olat onLevel = v olat onLevel,
                act onBu lder = act onBu lder.w hV olat onLevel(v olat onLevel = v olat onLevel))
            case UserType.Ot r =>
              FreedomOfSpeechNotReachRules.V e r sNonFollo rNonAuthorAndT etHasV olat onOfLevel(
                v olat onLevel = v olat onLevel,
                act onBu lder = act onBu lder.w hV olat onLevel(v olat onLevel = v olat onLevel))
          })
      }.toSeq
  }

  pr vate def generatePol c esForV olat onLevel(
    v olat onLevel: V olat onLevel
  ): Seq[(SafetyLevel, Rule)] = {
    getV olat onLevelPol c es
      .get(v olat onLevel).map { pol c esPerUserType =>
        Seq(UserType.Author, UserType.Follo r, UserType.Ot r).foldLeft(
          L st.empty[(UserType, SafetyLevel, Rule)]) {
          case (rulesForAllUserTypes, userType) =>
            rulesForAllUserTypes ++ generateRulesForPol cy(
              v olat onLevel = v olat onLevel,
              userType = userType,
              t etV s b l yPol cy = pol c esPerUserType(userType)).map {
              case (safetyLevel, rule) => (userType, safetyLevel, rule)
            }
        }
      }
      .map(pol cy => opt m zePol cy(pol cy = pol cy, v olat onLevel = v olat onLevel))
      .getOrElse(L st())
  }

  pr vate def  njectFallbackRule(rules: Seq[Rule]): Seq[Rule] = {
    rules :+ FreedomOfSpeechNotReachRules.T etHasV olat onOfAnyLevelFallbackDropRule
  }

  pr vate def opt m zePol cy(
    pol cy: Seq[(UserType, SafetyLevel, Rule)],
    v olat onLevel: V olat onLevel
  ): Seq[(SafetyLevel, Rule)] = {
    val pol c esByUserType = pol cy.groupBy { case (userType, _, _) => userType }.map {
      case (userType, aggregated) =>
        (userType, aggregated.map { case (_, safetyLevel, rules) => (safetyLevel, rules) })
    }
    val follo rPol c es = aggregateRulesBySafetyLevel(
      pol c esByUserType.getOrElse(UserType.Follo r, Seq()))
    val ot rPol c es = aggregateRulesBySafetyLevel(
      pol c esByUserType.getOrElse(UserType.Ot r, Seq()))
    pol c esByUserType(UserType.Author) ++
      follo rPol c es.collect {
        case (safetyLevel, rule)  f !ot rPol c es.conta ns(safetyLevel) =>
          (safetyLevel, rule)
      } ++
      ot rPol c es.collect {
        case (safetyLevel, rule)  f !follo rPol c es.conta ns(safetyLevel) =>
          (safetyLevel, rule)
      } ++
      follo rPol c es.keySet
        . ntersect(ot rPol c es.keySet).foldLeft(L st.empty[(SafetyLevel, Rule)]) {
          case (aggr, safetyLevel)
               f follo rPol c es(safetyLevel).act onBu lder == ot rPol c es(
                safetyLevel).act onBu lder =>
            (
              safetyLevel,
              FreedomOfSpeechNotReachRules.V e r sNonAuthorAndT etHasV olat onOfLevel(
                v olat onLevel = v olat onLevel,
                act onBu lder = follo rPol c es(safetyLevel).act onBu lder
              )) :: aggr
          case (aggr, safetyLevel) =>
            (safetyLevel, follo rPol c es(safetyLevel)) ::
              (safetyLevel, ot rPol c es(safetyLevel)) :: aggr
        }
  }

  pr vate def aggregateRulesBySafetyLevel(
    pol cy: Seq[(SafetyLevel, Rule)]
  ): Map[SafetyLevel, Rule] = {
    pol cy
      .groupBy {
        case (safetyLevel, _) => safetyLevel
      }.map {
        case (safetyLevel, Seq((_, rule))) =>
          (safetyLevel, rule)
        case _ => throw new Except on("Pol cy opt m zat on fa lure")
      }
  }

  pr vate def generateT etPol c es(): Map[SafetyLevel, Seq[Rule]] = {
    Seq(V olat onLevel.Level4, V olat onLevel.Level3, V olat onLevel.Level2, V olat onLevel.Level1)
      .foldLeft(L st.empty[(SafetyLevel, Rule)]) {
        case (rulesForAllV olat onLevels, v olat onLevel) =>
          rulesForAllV olat onLevels ++
            generatePol c esForV olat onLevel(v olat onLevel)
      }
      .groupBy { case (safetyLevel, _) => safetyLevel }
      .map {
        case (safetyLevel, l st) =>
          (safetyLevel,  njectFallbackRule(l st.map { case (_, rule) => rule }))
      }
  }
}
