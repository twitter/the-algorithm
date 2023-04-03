packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun

trait HaselonntryIdelonntifielonr elonxtelonnds UnivelonrsalNoun[Any] with HaselonntryNamelonspacelon {
  // Distinctly idelonntifielons this elonntry and must belon uniquelon relonlativelon to othelonr elonntrielons within a relonsponselon
  lazy val elonntryIdelonntifielonr: String = s"$elonntryNamelonspacelon-$id"
}
