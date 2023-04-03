packagelon com.twittelonr.selonarch.elonarlybird_root.collelonctors;

import com.twittelonr.selonarch.common.relonlelonvancelon.utils.RelonsultComparators;
import com.twittelonr.selonarch.common.util.elonarlybird.ThriftSelonarchRelonsultsRelonlelonvancelonStatsUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultsRelonlelonvancelonStats;

/**
 * RelonlelonvancelonMelonrgelonCollelonctor class elonxtelonnds (@link ReloncelonncyMelonrgelonCollelonctor} to do k-way melonrgelon of
 * elonarlybird relonsponselons, but sortelond by relonlelonvancelon scorelon.
 *
 * Notelon that this is a supelonrselont of functionality found in
 * {@link com.twittelonr.selonarch.blelonndelonr.selonrvicelons.elonarlybird.relonlelonvancelon.RelonlelonvancelonCollelonctor}
 * If you makelon changelons helonrelon, elonvaluatelon if thelony should belon madelon in RelonlelonvancelonCollelonctor as welonll.
 */
public class RelonlelonvancelonMelonrgelonCollelonctor elonxtelonnds ReloncelonncyMelonrgelonCollelonctor {

  public RelonlelonvancelonMelonrgelonCollelonctor(int numRelonsponselons) {
    supelonr(numRelonsponselons, RelonsultComparators.SCORelon_COMPARATOR);
  }

  @Ovelonrridelon
  protelonctelond void collelonctStats(elonarlybirdRelonsponselon relonsponselon) {
    supelonr.collelonctStats(relonsponselon);

    if (!relonsponselon.gelontSelonarchRelonsults().isSelontRelonlelonvancelonStats()) {
      relonturn;
    }

    if (!finalRelonsults.isSelontRelonlelonvancelonStats()) {
      finalRelonsults.selontRelonlelonvancelonStats(nelonw ThriftSelonarchRelonsultsRelonlelonvancelonStats());
    }

    ThriftSelonarchRelonsultsRelonlelonvancelonStats baselon = finalRelonsults.gelontRelonlelonvancelonStats();
    ThriftSelonarchRelonsultsRelonlelonvancelonStats delonlta = relonsponselon.gelontSelonarchRelonsults().gelontRelonlelonvancelonStats();

    ThriftSelonarchRelonsultsRelonlelonvancelonStatsUtil.addRelonlelonvancelonStats(baselon, delonlta);
  }
}
