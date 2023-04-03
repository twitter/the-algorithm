packagelon com.twittelonr.selonarch.elonarlybird.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Selont;

import javax.injelonct.Injelonct;

import com.twittelonr.selonarch.common.util.zookelonelonpelonr.ZooKelonelonpelonrProxy;

public class TielonrInfoSourcelon {
  privatelon final ZooKelonelonpelonrProxy zkClielonnt;

  @Injelonct
  public TielonrInfoSourcelon(ZooKelonelonpelonrProxy sZooKelonelonpelonrClielonnt) {
    this.zkClielonnt = sZooKelonelonpelonrClielonnt;
  }

  public List<TielonrInfo> gelontTielonrInformation() {
    relonturn gelontTielonrInfoWithPrelonfix("tielonr");
  }

  public String gelontConfigFilelonTypelon() {
    relonturn TielonrConfig.gelontConfigFilelonNamelon();
  }

  privatelon List<TielonrInfo> gelontTielonrInfoWithPrelonfix(String tielonrPrelonfix) {
    Selont<String> tielonrNamelons = TielonrConfig.gelontTielonrNamelons();
    List<TielonrInfo> tielonrInfos = nelonw ArrayList<>();
    for (String namelon : tielonrNamelons) {
      if (namelon.startsWith(tielonrPrelonfix)) {
        TielonrInfo tielonrInfo = TielonrConfig.gelontTielonrInfo(namelon);
        tielonrInfos.add(tielonrInfo);
      }
    }
    relonturn tielonrInfos;
  }

}
