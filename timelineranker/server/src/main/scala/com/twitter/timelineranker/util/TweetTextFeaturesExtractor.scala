package com.tw ter.t  l neranker.ut l

 mport com.tw ter.common.text.tagger.Un versalPOS
 mport com.tw ter.common.text.token.attr bute.TokenType
 mport com.tw ter.common_ nternal.text.p pel ne.Tw terTextNormal zer
 mport com.tw ter.common_ nternal.text.p pel ne.Tw terTextToken zer
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on
 mport com.tw ter.search.common.ut l.text.Language dent f er lper
 mport com.tw ter.search.common.ut l.text.PhraseExtractor
 mport com.tw ter.search.common.ut l.text.Token zer lper
 mport com.tw ter.search.common.ut l.text.Token zerResult
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t etyp e.{thr ftscala => t etyp e}
 mport com.tw ter.ut l.Try
 mport java.ut l.Locale
 mport scala.collect on.JavaConvers ons._

object T etTextFeaturesExtractor {

  pr vate[t ] val threadLocaltoken zer = new ThreadLocal[Opt on[Tw terTextToken zer]] {
    overr de protected def  n  alValue(): Opt on[Tw terTextToken zer] =
      Try {
        val normal zer = new Tw terTextNormal zer.Bu lder(pengu nVers on).bu ld
        Token zer lper
          .getToken zerBu lder(pengu nVers on)
          .enablePOSTagger
          .enableStopwordF lterW hNormal zer(normal zer)
          .setStopwordRes cePath("com/tw ter/ml/feature/generator/stopwords_extended_{LANG}.txt")
          .enableStem r
          .bu ld
      }.toOpt on
  }

  val pengu nVers on: Pengu nVers on = Pengu nVers on.PENGU N_6

  def addTextFeaturesFromT et(
     nputFeatures: ContentFeatures,
    t et: t etyp e.T et,
    hydratePengu nTextFeatures: Boolean,
    hydrateTokens: Boolean,
    hydrateT etText: Boolean
  ): ContentFeatures = {
    t et.coreData
      .map { coreData =>
        val t etText = coreData.text
        val hasQuest on = hasQuest onCharacter(t etText)
        val length = getLength(t etText).toShort
        val numCaps = getCaps(t etText).toShort
        val numWh eSpaces = getSpaces(t etText).toShort
        val numNewl nes = So (getNumNewl nes(t etText))
        val t etTextOpt = getT etText(t etText, hydrateT etText)

         f (hydratePengu nTextFeatures) {
          val locale = getLocale(t etText)
          val token zerOpt = threadLocaltoken zer.get

          val token zerResult = token zerOpt.flatMap { token zer =>
            token zeW hPosTagger(token zer, locale, t etText)
          }

          val normal zedTokensOpt =  f (hydrateTokens) {
            token zerOpt.flatMap { token zer =>
              token zedStr ngsW hNormal zerAndStem r(token zer, locale, t etText)
            }
          } else None

          val emot conTokensOpt = token zerResult.map(getEmot cons)
          val emoj TokensOpt = token zerResult.map(getEmoj s)
          val posUn gramsOpt = token zerResult.map(getPosUn grams)
          val posB gramsOpt = posUn gramsOpt.map(getPosB grams)
          val tokensOpt = normal zedTokensOpt

           nputFeatures.copy(
            emoj Tokens = emoj TokensOpt,
            emot conTokens = emot conTokensOpt,
            hasQuest on = hasQuest on,
            length = length,
            numCaps = numCaps,
            numWh eSpaces = numWh eSpaces,
            numNewl nes = numNewl nes,
            posUn grams = posUn gramsOpt.map(_.toSet),
            posB grams = posB gramsOpt.map(_.toSet),
            tokens = tokensOpt.map(_.toSeq),
            t etText = t etTextOpt
          )
        } else {
           nputFeatures.copy(
            hasQuest on = hasQuest on,
            length = length,
            numCaps = numCaps,
            numWh eSpaces = numWh eSpaces,
            numNewl nes = numNewl nes,
            t etText = t etTextOpt
          )
        }
      }
      .getOrElse( nputFeatures)
  }

  pr vate def token zeW hPosTagger(
    token zer: Tw terTextToken zer,
    locale: Locale,
    text: Str ng
  ): Opt on[Token zerResult] = {
    token zer.enableStem r(false)
    token zer.enableStopwordF lter(false)

    Try { Token zer lper.token zeT et(token zer, text, locale) }.toOpt on
  }

  pr vate def token zedStr ngsW hNormal zerAndStem r(
    token zer: Tw terTextToken zer,
    locale: Locale,
    text: Str ng
  ): Opt on[Seq[Str ng]] = {
    token zer.enableStem r(true)
    token zer.enableStopwordF lter(true)

    Try { token zer.token zeToStr ngs(text, locale).toSeq }.toOpt on
  }

  def getLocale(text: Str ng): Locale = Language dent f er lper. dent fyLanguage(text)

  def getTokens(token zerResult: Token zerResult): L st[Str ng] =
    token zerResult.rawSequence.getTokenStr ngs().toL st

  def getEmot cons(token zerResult: Token zerResult): Set[Str ng] =
    token zerResult.sm leys.toSet

  def getEmoj s(token zerResult: Token zerResult): Set[Str ng] =
    token zerResult.rawSequence.getTokenStr ngsOf(TokenType.EMOJ ).toSet

  def getPhrases(token zerResult: Token zerResult, locale: Locale): Set[Str ng] = {
    PhraseExtractor.getPhrases(token zerResult.rawSequence, locale).map(_.toStr ng).toSet
  }

  def getPosUn grams(token zerResult: Token zerResult): L st[Str ng] =
    token zerResult.tokenSequence.getTokens.toL st
      .map { token =>
        Opt on(token.getPartOfSpeech)
          .map(_.toStr ng)
          .getOrElse(Un versalPOS.X.toStr ng) // Un versalPOS.X  s unknown POS tag
      }

  def getPosB grams(tagsL st: L st[Str ng]): L st[Str ng] = {
     f (tagsL st.nonEmpty) {
      tagsL st
        .z p(tagsL st.ta l)
        .map(tagPa r => Seq(tagPa r._1, tagPa r._2).mkStr ng(" "))
    } else {
      tagsL st
    }
  }

  def getLength(text: Str ng):  nt =
    text.codePo ntCount(0, text.length())

  def getCaps(text: Str ng):  nt = text.count(Character. sUpperCase)

  def getSpaces(text: Str ng):  nt = text.count(Character. sWh espace)

  def hasQuest onCharacter(text: Str ng): Boolean = {
    // L st based on https://un code-search.net/un code-na search.pl?term=quest on
    val QUEST ON_MARK_CHARS = Seq(
      "\u003F",
      "\u00BF",
      "\u037E",
      "\u055E",
      "\u061F",
      "\u1367",
      "\u1945",
      "\u2047",
      "\u2048",
      "\u2049",
      "\u2753",
      "\u2754",
      "\u2CFA",
      "\u2CFB",
      "\u2E2E",
      "\uA60F",
      "\uA6F7",
      "\uFE16",
      "\uFE56",
      "\uFF1F",
      "\u1114",
      "\u1E95"
    )
    QUEST ON_MARK_CHARS.ex sts(text.conta ns)
  }

  def getNumNewl nes(text: Str ng): Short = {
    val newl neRegex = "\r\n|\r|\n".r
    newl neRegex.f ndAll n(text).length.toShort
  }

  pr vate[t ] def getT etText(t etText: Str ng, hydrateT etText: Boolean): Opt on[Str ng] = {
     f (hydrateT etText) So (t etText) else None
  }
}
