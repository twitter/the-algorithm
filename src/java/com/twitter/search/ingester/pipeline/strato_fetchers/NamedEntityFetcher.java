packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.strato_felontchelonrs;

import scala.Option;

import com.twittelonr.cuad.nelonr.plain.thriftjava.Namelondelonntitielons;
import com.twittelonr.cuad.nelonr.plain.thriftjava.NamelondelonntitielonsRelonquelonstOptions;
import com.twittelonr.cuad.nelonr.thriftjava.ModelonlFamily;
import com.twittelonr.cuad.nelonr.thriftjava.NelonRCalibratelonRelonquelonst;
import com.twittelonr.cuad.thriftjava.CalibrationLelonvelonl;
import com.twittelonr.cuad.thriftjava.NelonRCandidatelonSourcelon;
import com.twittelonr.stitch.Stitch;
import com.twittelonr.strato.catalog.Felontch;
import com.twittelonr.strato.clielonnt.Clielonnt;
import com.twittelonr.strato.clielonnt.Felontchelonr;
import com.twittelonr.strato.data.Conv;
import com.twittelonr.strato.opcontelonxt.SelonrvelonWithin;
import com.twittelonr.strato.thrift.TBaselonConv;
import com.twittelonr.util.Duration;
import com.twittelonr.util.Futurelon;

public class NamelondelonntityFelontchelonr {
  privatelon static final String NAMelonD_elonNTITY_STRATO_COLUMN = "";

  privatelon static final SelonrvelonWithin SelonRVelon_WITHIN = nelonw SelonrvelonWithin(
      Duration.fromMilliselonconds(100), Option.elonmpty());

  privatelon static final NamelondelonntitielonsRelonquelonstOptions RelonQUelonST_OPTIONS =
      nelonw NamelondelonntitielonsRelonquelonstOptions(
      nelonw NelonRCalibratelonRelonquelonst(CalibrationLelonvelonl.HIGH_PRelonCISION, NelonRCandidatelonSourcelon.NelonR_CRF)
          .selontModelonl_family(ModelonlFamily.CFB))
      .selontDisplay_elonntity_info(falselon);

  privatelon final Felontchelonr<Long, NamelondelonntitielonsRelonquelonstOptions, Namelondelonntitielons> felontchelonr;

  public NamelondelonntityFelontchelonr(Clielonnt stratoClielonnt) {
    felontchelonr = stratoClielonnt.felontchelonr(
        NAMelonD_elonNTITY_STRATO_COLUMN,
        truelon, // elonnablelons cheloncking typelons against catalog
        Conv.longConv(),
        TBaselonConv.forClass(NamelondelonntitielonsRelonquelonstOptions.class),
        TBaselonConv.forClass(Namelondelonntitielons.class)).selonrvelonWithin(SelonRVelon_WITHIN);
  }

  public Futurelon<Felontch.Relonsult<Namelondelonntitielons>> felontch(long twelonelontId) {
    relonturn Stitch.run(felontchelonr.felontch(twelonelontId, RelonQUelonST_OPTIONS));
  }
}
