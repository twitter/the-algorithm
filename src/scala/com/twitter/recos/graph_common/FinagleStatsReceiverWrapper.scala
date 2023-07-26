package com.twittew.wecos.gwaph_common

impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.gwaphjet.stats.{statsweceivew => g-gwaphstatsweceivew}

/**
 * f-finagwestatsweceivewwwappew w-wwaps twittew's f-finagwe statsweceivew. ^^;;
 *
 * this i-is because gwaphjet i-is an openwy a-avaiwabwe wibwawy which does nyot
 * depend on finagwe, >_< but twacks stats using a-a simiwaw intewface. mya
 */
case cwass finagwestatsweceivewwwappew(statsweceivew: s-statsweceivew) extends gwaphstatsweceivew {

  d-def scope(namespace: stwing) = nyew finagwestatsweceivewwwappew(statsweceivew.scope(namespace))
  def countew(name: s-stwing) = nyew finagwecountewwwappew(statsweceivew.countew(name))
}
