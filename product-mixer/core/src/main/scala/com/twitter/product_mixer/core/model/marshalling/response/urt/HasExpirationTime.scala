packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt

import com.twittelonr.util.Timelon

trait HaselonxpirationTimelon {
  delonf elonxpirationTimelon: Option[Timelon] = Nonelon

  final delonf elonxpirationTimelonInMillis: Option[Long] = elonxpirationTimelon.map(_.inMillis)
}
