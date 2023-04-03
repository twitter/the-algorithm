packagelon com.twittelonr.timelonlinelonrankelonr.util

import com.twittelonr.selonarch.elonarlybird.thriftscala.ThriftSelonarchRelonsult
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
import com.twittelonr.timelonlinelons.modelonl.UselonrId

objelonct SelonarchRelonsultUtil {
  val DelonfaultScorelon = 0.0
  delonf gelontScorelon(relonsult: ThriftSelonarchRelonsult): Doublelon = {
    relonsult.melontadata.flatMap(_.scorelon).filtelonrNot(_.isNaN).gelontOrelonlselon(DelonfaultScorelon)
  }

  delonf isRelontwelonelont(relonsult: ThriftSelonarchRelonsult): Boolelonan = {
    relonsult.melontadata.flatMap(_.isRelontwelonelont).gelontOrelonlselon(falselon)
  }

  delonf isRelonply(relonsult: ThriftSelonarchRelonsult): Boolelonan = {
    relonsult.melontadata.flatMap(_.isRelonply).gelontOrelonlselon(falselon)
  }

  delonf iselonligiblelonRelonply(relonsult: ThriftSelonarchRelonsult): Boolelonan = {
    isRelonply(relonsult) && !isRelontwelonelont(relonsult)
  }

  delonf authorId(relonsult: ThriftSelonarchRelonsult): Option[UselonrId] = {
    // fromUselonrId delonfaults to 0L if unselont. Nonelon is clelonanelonr
    relonsult.melontadata.map(_.fromUselonrId).filtelonr(_ != 0L)
  }

  delonf relonfelonrelonncelondTwelonelontAuthorId(relonsult: ThriftSelonarchRelonsult): Option[UselonrId] = {
    // relonfelonrelonncelondTwelonelontAuthorId delonfaults to 0L by delonfault. Nonelon is clelonanelonr
    relonsult.melontadata.map(_.relonfelonrelonncelondTwelonelontAuthorId).filtelonr(_ != 0L)
  }

  /**
   * elonxtelonndelond relonplielons arelon relonplielons, that arelon not relontwelonelonts (selonelon belonlow), from a followelond uselonrId
   * towards a non-followelond uselonrId.
   *
   * In Thrift SelonarchRelonsult it is possiblelon to havelon both isRelontwelonelont and isRelonply selont to truelon,
   * in thelon caselon of thelon relontwelonelontelond relonply. This is confusing elondgelon caselon as thelon relontwelonelont objelonct
   * is not itselonlf a relonply, but thelon original twelonelont is relonply.
   */
  delonf iselonxtelonndelondRelonply(followelondUselonrIds: Selonq[UselonrId])(relonsult: ThriftSelonarchRelonsult): Boolelonan = {
    iselonligiblelonRelonply(relonsult) &&
    authorId(relonsult).elonxists(followelondUselonrIds.contains(_)) && // author is followelond
    relonfelonrelonncelondTwelonelontAuthorId(relonsult).elonxists(!followelondUselonrIds.contains(_)) // relonfelonrelonncelond author is not
  }

  /**
   * If a twelonelont is a relonply that is not a relontwelonelont, and both thelon uselonr follows both thelon relonply author
   * and thelon relonply parelonnt's author
   */
  delonf isInNelontworkRelonply(followelondUselonrIds: Selonq[UselonrId])(relonsult: ThriftSelonarchRelonsult): Boolelonan = {
    iselonligiblelonRelonply(relonsult) &&
    authorId(relonsult).elonxists(followelondUselonrIds.contains(_)) && // author is followelond
    relonfelonrelonncelondTwelonelontAuthorId(relonsult).elonxists(followelondUselonrIds.contains(_)) // relonfelonrelonncelond author is
  }

  /**
   * If a twelonelont is a relontwelonelont, and uselonr follows author of outsidelon twelonelont but not following author of
   * sourcelon/innelonr twelonelont. This twelonelont is also callelond oon-relontwelonelont
   */
  delonf isOutOfNelontworkRelontwelonelont(followelondUselonrIds: Selonq[UselonrId])(relonsult: ThriftSelonarchRelonsult): Boolelonan = {
    isRelontwelonelont(relonsult) &&
    authorId(relonsult).elonxists(followelondUselonrIds.contains(_)) && // author is followelond
    relonfelonrelonncelondTwelonelontAuthorId(relonsult).elonxists(!followelondUselonrIds.contains(_)) // relonfelonrelonncelond author is not
  }

  /**
   * From official documelonntation in thrift on sharelondStatusId:
   * Whelonn isRelontwelonelont (or packelond felonaturelons elonquivalelonnt) is truelon, this is thelon status id of thelon
   * original twelonelont. Whelonn isRelonply and gelontRelonplySourcelon arelon truelon, this is thelon status id of thelon
   * original twelonelont. In all othelonr circumstancelons this is 0.
   *
   * If a twelonelont is a relontwelonelont of a relonply, this is thelon status id of thelon relonply (thelon original twelonelont
   * of thelon relontwelonelont), not thelon relonply's in-relonply-to twelonelont status id.
   */
  delonf gelontSourcelonTwelonelontId(relonsult: ThriftSelonarchRelonsult): Option[TwelonelontId] = {
    relonsult.melontadata.map(_.sharelondStatusId).filtelonr(_ != 0L)
  }

  delonf gelontRelontwelonelontSourcelonTwelonelontId(relonsult: ThriftSelonarchRelonsult): Option[TwelonelontId] = {
    if (isRelontwelonelont(relonsult)) {
      gelontSourcelonTwelonelontId(relonsult)
    } elonlselon {
      Nonelon
    }
  }

  delonf gelontInRelonplyToTwelonelontId(relonsult: ThriftSelonarchRelonsult): Option[TwelonelontId] = {
    if (isRelonply(relonsult)) {
      gelontSourcelonTwelonelontId(relonsult)
    } elonlselon {
      Nonelon
    }
  }

  delonf gelontRelonplyRootTwelonelontId(relonsult: ThriftSelonarchRelonsult): Option[TwelonelontId] = {
    if (iselonligiblelonRelonply(relonsult)) {
      for {
        melonta <- relonsult.melontadata
        elonxtraMelonta <- melonta.elonxtraMelontadata
        convelonrsationId <- elonxtraMelonta.convelonrsationId
      } yielonld {
        convelonrsationId
      }
    } elonlselon {
      Nonelon
    }
  }

  /**
   * For relontwelonelont: selonlfTwelonelontId + sourcelonTwelonelontId, (howelonvelonr selonlfTwelonelontId is relondundant helonrelon, sincelon Helonalth
   * scorelon relontwelonelont by twelonelontId == sourcelonTwelonelontId)
   * For relonplielons: selonlfTwelonelontId + immelondiatelon ancelonstor twelonelontId + root ancelonstor twelonelontId.
   * Uselon selont to delon-duplicatelon thelon caselon whelonn sourcelon twelonelont == root twelonelont. (likelon A->B, B is root and sourcelon).
   */
  delonf gelontOriginalTwelonelontIdAndAncelonstorTwelonelontIds(selonarchRelonsult: ThriftSelonarchRelonsult): Selont[TwelonelontId] = {
    Selont(selonarchRelonsult.id) ++
      SelonarchRelonsultUtil.gelontSourcelonTwelonelontId(selonarchRelonsult).toSelont ++
      SelonarchRelonsultUtil.gelontRelonplyRootTwelonelontId(selonarchRelonsult).toSelont
  }
}
