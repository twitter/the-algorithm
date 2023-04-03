packagelon com.twittelonr.ann.selonrvicelon.quelonry_selonrvelonr.common

import com.twittelonr.ann.common.thriftscala.BadRelonquelonst
import com.twittelonr.melondiaselonrvicelons.commons._

objelonct RuntimelonelonxcelonptionTransform elonxtelonnds elonxcelonptionTransformelonr {
  ovelonrridelon delonf transform = {
    caselon elon: BadRelonquelonst =>
      MisuselonelonxcelonptionInfo(elon)
  }

  ovelonrridelon delonf gelontStatNamelon: PartialFunction[elonxcelonption, String] = {
    caselon elon: BadRelonquelonst => elonxcelonptionNamelon(elon, elon.codelon.namelon)
  }
}
