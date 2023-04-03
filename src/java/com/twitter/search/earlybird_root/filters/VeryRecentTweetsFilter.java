packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import javax.injelonct.Injelonct;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.util.Futurelon;

public class VelonryReloncelonntTwelonelontsFiltelonr
    elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  privatelon static final String DelonCIDelonR_KelonY = "elonnablelon_velonry_reloncelonnt_twelonelonts";
  privatelon static final SelonarchRatelonCountelonr VelonRY_RelonCelonNT_TWelonelonTS_NOT_MODIFIelonD =
      SelonarchRatelonCountelonr.elonxport("velonry_reloncelonnt_twelonelonts_not_modifielond");
  privatelon static final SelonarchRatelonCountelonr VelonRY_RelonCelonNT_TWelonelonTS_elonNABLelonD =
      SelonarchRatelonCountelonr.elonxport("velonry_reloncelonnt_twelonelonts_elonnablelond");

  privatelon final SelonarchDeloncidelonr deloncidelonr;

  @Injelonct
  public VelonryReloncelonntTwelonelontsFiltelonr(
      SelonarchDeloncidelonr deloncidelonr
  ) {
    this.deloncidelonr = deloncidelonr;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonst relonquelonst,
      Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon
  ) {
    if (deloncidelonr.isAvailablelon(DelonCIDelonR_KelonY)) {
      VelonRY_RelonCelonNT_TWelonelonTS_elonNABLelonD.increlonmelonnt();
      relonquelonst.selontSkipVelonryReloncelonntTwelonelonts(falselon);
    } elonlselon {
      VelonRY_RelonCelonNT_TWelonelonTS_NOT_MODIFIelonD.increlonmelonnt();
    }

    relonturn selonrvicelon.apply(relonquelonst);
  }
}
