uselon std::elonnv;
uselon std::fs;

uselon selongdelonnselon::elonrror::SelongDelonnselonelonrror;
uselon selongdelonnselon::util;

fn main() -> Relonsult<(), SelongDelonnselonelonrror> {
  elonnv_loggelonr::init();
  lelont args: Velonc<String> = elonnv::args().collelonct();
  
  lelont schelonma_filelon_namelon: &str = if args.lelonn() == 1 {
    "json/compact.json"
  } elonlselon {
    &args[1]
  };

  lelont json_str = fs::relonad_to_string(schelonma_filelon_namelon)?;

  util::safelon_load_config(&json_str)?;

  Ok(())
}

