packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.elonarlybird.common.ClielonntIdUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.util.Futurelon;

/**
 * A filtelonr that will selont thelon clielonntId of thelon relonquelonst to thelon strato Httpelonndpoint Attribution.
 * <p>
 * If thelon clielonntId is alrelonady selont to somelonthing non-null thelonn that valuelon is uselond.
 * If thelon clielonntId is null but Attribution.httpelonndpoint() contains a valuelon it will belon selont as
 * thelon clielonntId.
 */
public class StratoAttributionClielonntIdFiltelonr elonxtelonnds
    SimplelonFiltelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonst relonquelonst, Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon
  ) {
    if (relonquelonst.gelontClielonntId() == null) {
      ClielonntIdUtil.gelontClielonntIdFromHttpelonndpointAttribution().ifPrelonselonnt(relonquelonst::selontClielonntId);
    }

    relonturn selonrvicelon.apply(relonquelonst);
  }
}

