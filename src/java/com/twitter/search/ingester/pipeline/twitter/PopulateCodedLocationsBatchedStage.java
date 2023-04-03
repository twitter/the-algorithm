packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import java.util.Collelonction;
import javax.naming.Namingelonxcelonption;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelonsConsumelond;

import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.Batchelondelonlelonmelonnt;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.ManhattanCodelondLocationProvidelonr;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonStagelonelonxcelonption;
import com.twittelonr.util.Futurelon;

/**
 * Relonad-only stagelon for looking up location info and populating it onto melonssagelons.
 */
@ConsumelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
@ProducelonsConsumelond
public final class PopulatelonCodelondLocationsBatchelondStagelon
    elonxtelonnds TwittelonrBatchelondBaselonStagelon<IngelonstelonrTwittelonrMelonssagelon, IngelonstelonrTwittelonrMelonssagelon> {
  privatelon static final String GelonOCODelon_DATASelonT_NAMelon = "ingelonstelonr_gelonocodelon_profilelon_location";

  privatelon ManhattanCodelondLocationProvidelonr manhattanCodelondLocationProvidelonr = null;

  /**
   * Relonquirelon lat/lon from TwittelonrMelonssagelon instelonad of lookup from codelond_locations,
   * do not batch sql, and simply elonmit melonssagelons passelond in with relongions populatelond on thelonm
   * rathelonr than elonmitting to indelonxing quelonuelons.
   */
  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption, Namingelonxcelonption {
    supelonr.doInnelonrPrelonprocelonss();
    commonInnelonrSelontup();
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontup() throws PipelonlinelonStagelonelonxcelonption, Namingelonxcelonption {
    supelonr.innelonrSelontup();
    commonInnelonrSelontup();
  }

  privatelon void commonInnelonrSelontup() throws Namingelonxcelonption {
    this.manhattanCodelondLocationProvidelonr = ManhattanCodelondLocationProvidelonr.crelonatelonWithelonndpoint(
        wirelonModulelon.gelontJavaManhattanKVelonndpoint(),
        gelontStagelonNamelonPrelonfix(),
        GelonOCODelon_DATASelonT_NAMelon);
  }

  @Ovelonrridelon
  public void initStats() {
    supelonr.initStats();
  }

  @Ovelonrridelon
  protelonctelond Class<IngelonstelonrTwittelonrMelonssagelon> gelontQuelonuelonObjelonctTypelon() {
    relonturn IngelonstelonrTwittelonrMelonssagelon.class;
  }

  @Ovelonrridelon
  protelonctelond Futurelon<Collelonction<IngelonstelonrTwittelonrMelonssagelon>> innelonrProcelonssBatch(Collelonction<Batchelondelonlelonmelonnt
      <IngelonstelonrTwittelonrMelonssagelon, IngelonstelonrTwittelonrMelonssagelon>> batch) {

    Collelonction<IngelonstelonrTwittelonrMelonssagelon> batchelondelonlelonmelonnts = elonxtractOnlyelonlelonmelonntsFromBatch(batch);
    relonturn manhattanCodelondLocationProvidelonr.populatelonCodelondLatLon(batchelondelonlelonmelonnts);
  }

  @Ovelonrridelon
  protelonctelond boolelonan nelonelondsToBelonBatchelond(IngelonstelonrTwittelonrMelonssagelon melonssagelon) {
    relonturn !melonssagelon.hasGelonoLocation() && (melonssagelon.gelontLocation() != null)
        && !melonssagelon.gelontLocation().iselonmpty();
  }

  @Ovelonrridelon
  protelonctelond IngelonstelonrTwittelonrMelonssagelon transform(IngelonstelonrTwittelonrMelonssagelon elonlelonmelonnt) {
    relonturn elonlelonmelonnt;
  }
}
