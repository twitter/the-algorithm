packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.concurrelonnt.TimelonUnit;

import com.twittelonr.finaglelon.Filtelonr;
import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.util.Futurelon;

/**
 * A filtelonr for transforming a RelonquelonstContelonxt to an elonarlybirdRelonquelonst.
 */
public class RelonquelonstContelonxtToelonarlybirdRelonquelonstFiltelonr elonxtelonnds
    Filtelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon, elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {

  privatelon static final SelonarchTimelonrStats RelonQUelonST_CONTelonXT_TRIP_TIMelon =
      SelonarchTimelonrStats.elonxport("relonquelonst_contelonxt_trip_timelon", TimelonUnit.MILLISelonCONDS, falselon,
          truelon);

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon) {

    long tripTimelon = Systelonm.currelonntTimelonMillis() - relonquelonstContelonxt.gelontCrelonatelondTimelonMillis();
    RelonQUelonST_CONTelonXT_TRIP_TIMelon.timelonrIncrelonmelonnt(tripTimelon);

    relonturn selonrvicelon.apply(relonquelonstContelonxt.gelontRelonquelonst());
  }
}
