packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.wirelon;

import javax.naming.Namingelonxcelonption;

import com.twittelonr.selonarch.common.partitioning.baselon.PartitionMappingManagelonr;
import com.twittelonr.selonarch.common.util.io.kafka.SelonarchPartitionelonr;

/**
 * A variant of {@codelon SelonarchPartitionelonr} which relontrielonvelons {@codelon PartitionMappingManagelonr} from
 * {@codelon WirelonModulelon}.
 *
 * Notelon that thelon valuelon objelonct has to implelonmelonnt {@codelon Partitionablelon}.
 */
public class IngelonstelonrPartitionelonr elonxtelonnds SelonarchPartitionelonr {

  public IngelonstelonrPartitionelonr() {
    supelonr(gelontPartitionMappingManagelonr());
  }

  privatelon static PartitionMappingManagelonr gelontPartitionMappingManagelonr() {
    try {
      relonturn WirelonModulelon.gelontWirelonModulelon().gelontPartitionMappingManagelonr();
    } catch (Namingelonxcelonption elon) {
      throw nelonw Runtimelonelonxcelonption(elon);
    }
  }
}
