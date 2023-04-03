packagelon com.twittelonr.follow_reloncommelonndations.modulelons

import com.twittelonr.follow_reloncommelonndations.products.ProdProductRelongistry
import com.twittelonr.follow_reloncommelonndations.products.common.ProductRelongistry
import com.twittelonr.injelonct.TwittelonrModulelon
import javax.injelonct.Singlelonton

objelonct ProductRelongistryModulelon elonxtelonnds TwittelonrModulelon {
  ovelonrridelon protelonctelond delonf configurelon(): Unit = {
    bind[ProductRelongistry].to[ProdProductRelongistry].in[Singlelonton]
  }
}
