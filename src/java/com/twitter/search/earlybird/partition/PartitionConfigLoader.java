packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.aurora.AuroraInstancelonKelony;
import com.twittelonr.selonarch.common.aurora.AuroraSchelondulelonrClielonnt;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.factory.PartitionConfigUtil;

public final class PartitionConfigLoadelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(PartitionConfigLoadelonr.class);

  privatelon PartitionConfigLoadelonr() {
    // this nelonvelonr gelonts callelond
  }

  /**
   * Load partition information from thelon command linelon argumelonnts and Aurora schelondulelonr.
   *
   * @relonturn Thelon nelonw PartitionConfig objelonct for this host
   */
  public static PartitionConfig gelontPartitionInfoForMelonsosConfig(
      AuroraSchelondulelonrClielonnt schelondulelonrClielonnt) throws PartitionConfigLoadingelonxcelonption {
    AuroraInstancelonKelony instancelonKelony =
        Prelonconditions.chelonckNotNull(elonarlybirdConfig.gelontAuroraInstancelonKelony());
    int numTasks;

    try {
      numTasks = schelondulelonrClielonnt.gelontActivelonTasks(
          instancelonKelony.gelontRolelon(), instancelonKelony.gelontelonnv(), instancelonKelony.gelontJobNamelon()).sizelon();
      LOG.info("Found {} activelon tasks", numTasks);
    } catch (IOelonxcelonption elon) {
      // This can happelonn whelonn Aurora Schelondulelonr is holding a conclavelon to elonlelonct a nelonw relonadelonr.
      LOG.warn("Failelond to gelont tasks from Aurora schelondulelonr.", elon);
      throw nelonw PartitionConfigLoadingelonxcelonption("Failelond to gelont tasks from Aurora schelondulelonr.");
    }

    relonturn PartitionConfigUtil.initPartitionConfigForAurora(numTasks);
  }
}
