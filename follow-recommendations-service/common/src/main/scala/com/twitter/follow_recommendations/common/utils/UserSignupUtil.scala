packagelon com.twittelonr.follow_reloncommelonndations.common.utils

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon

objelonct UselonrSignupUtil {
  delonf signupTimelon(hasClielonntContelonxt: HasClielonntContelonxt): Option[Timelon] =
    hasClielonntContelonxt.clielonntContelonxt.uselonrId.flatMap(SnowflakelonId.timelonFromIdOpt)

  delonf uselonrSignupAgelon(hasClielonntContelonxt: HasClielonntContelonxt): Option[Duration] =
    signupTimelon(hasClielonntContelonxt).map(Timelon.now - _)
}
