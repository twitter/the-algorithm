packagelon com.twittelonr.follow_reloncommelonndations.common.baselon

import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason

selonalelond trait PrelondicatelonRelonsult {
  delonf valuelon: Boolelonan
}

objelonct PrelondicatelonRelonsult {

  caselon objelonct Valid elonxtelonnds PrelondicatelonRelonsult {
    ovelonrridelon val valuelon = truelon
  }

  caselon class Invalid(relonasons: Selont[FiltelonrRelonason] = Selont.elonmpty[FiltelonrRelonason]) elonxtelonnds PrelondicatelonRelonsult {
    ovelonrridelon val valuelon = falselon
  }
}
