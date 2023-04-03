packagelon com.twittelonr.selonarch.elonarlybird_root;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;

public final class elonxcelonptionHandlelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonxcelonptionHandlelonr.class);

  privatelon elonxcelonptionHandlelonr() {
  }

  public static void logelonxcelonption(elonarlybirdRelonquelonst relonquelonst, Throwablelon elon) {
    LOG.elonrror("elonxcelonption whilelon handling relonquelonst: {}", relonquelonst, elon);
  }
}
