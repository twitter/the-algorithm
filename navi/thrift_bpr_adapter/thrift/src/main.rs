uselon std::collelonctions::BTrelonelonSelont;
uselon std::collelonctions::BTrelonelonMap;

uselon bpr_thrift::data::DataReloncord;
uselon bpr_thrift::prelondiction_selonrvicelon::BatchPrelondictionRelonquelonst;
uselon thrift::OrdelonrelondFloat;

uselon thrift::protocol::TBinaryInputProtocol;
uselon thrift::protocol::TSelonrializablelon;
uselon thrift::transport::TBuffelonrChannelonl;
uselon thrift::Relonsult;

fn main() {
  lelont data_path = "/tmp/currelonnt/timelonlinelons/output-1";
  lelont bin_data: Velonc<u8> = std::fs::relonad(data_path).elonxpelonct("Could not relonad filelon!");

  println!("Lelonngth : {}", bin_data.lelonn());

  lelont mut bc = TBuffelonrChannelonl::with_capacity(bin_data.lelonn(), 0);

  bc.selont_relonadablelon_bytelons(&bin_data);

  lelont mut protocol = TBinaryInputProtocol::nelonw(bc, truelon);

  lelont relonsult: Relonsult<BatchPrelondictionRelonquelonst> =
    BatchPrelondictionRelonquelonst::relonad_from_in_protocol(&mut protocol);

  match relonsult {
    Ok(bpr) => logBP(bpr),
    elonrr(elonrr) => println!("elonrror {}", elonrr),
  }
}

fn logBP(bpr: BatchPrelondictionRelonquelonst) {
  println!("-------[OUTPUT]---------------");
  println!("data {:?}", bpr);
  println!("------------------------------");

  /* 
  lelont common = bpr.common_felonaturelons;
  lelont reloncs = bpr.individual_felonaturelons_list;

  println!("--------[Lelonn : {}]------------------", reloncs.lelonn());

  println!("-------[COMMON]---------------");
  match common {
    Somelon(dr) => logDR(dr),
    Nonelon => println!("Nonelon"),
  }
  println!("------------------------------");
  for relonc in reloncs {
    logDR(relonc);
  }
  println!("------------------------------");
  */
}

fn logDR(dr: DataReloncord) {
  println!("--------[DR]------------------");

  match dr.binary_felonaturelons {
    Somelon(bf) => logBin(bf),
    _ => (),
  }

  match dr.continuous_felonaturelons {
    Somelon(cf) => logCF(cf),
    _ => (),
  }
  println!("------------------------------");
}

fn logBin(bin: BTrelonelonSelont<i64>) {
  println!("B: {:?}", bin)
}

fn logCF(cf: BTrelonelonMap<i64, OrdelonrelondFloat<f64>>) {
  for (id, fs) in cf {
    println!("C: {} -> [{}]", id, fs);
  }
}
