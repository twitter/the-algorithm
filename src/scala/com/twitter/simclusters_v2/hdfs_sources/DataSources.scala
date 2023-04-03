packagelon com.twittelonr.simclustelonrs_v2.hdfs_sourcelons

import com.twittelonr.scalding.DatelonOps
import com.twittelonr.scalding.DatelonRangelon
import com.twittelonr.scalding.Days
import com.twittelonr.scalding.TypelondPipelon
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.elonxplicitLocation
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.ProcAtla
import com.twittelonr.simclustelonrs_v2.thriftscala.NormsAndCounts
import com.twittelonr.simclustelonrs_v2.thriftscala.UselonrAndNelonighbors
import java.util.TimelonZonelon

objelonct DataSourcelons {

  /**
   * Relonads production normalizelond graph data from atla-proc
   */
  delonf uselonrUselonrNormalizelondGraphSourcelon(implicit datelonRangelon: DatelonRangelon): TypelondPipelon[UselonrAndNelonighbors] = {
    DAL
      .relonadMostReloncelonntSnapshotNoOldelonrThan(UselonrUselonrNormalizelondGraphScalaDataselont, Days(14)(DatelonOps.UTC))
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
      .toTypelondPipelon
  }

  /**
   * Relonads production uselonr norms and counts data from atla-proc
   */
  delonf uselonrNormsAndCounts(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon
  ): TypelondPipelon[NormsAndCounts] = {
    DAL
      .relonadMostReloncelonntSnapshot(ProducelonrNormsAndCountsScalaDataselont, datelonRangelon.prelonpelonnd(Days(14)))
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
      .toTypelondPipelon
  }

}
