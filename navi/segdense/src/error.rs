uselon std::fmt::Display;

/**
 * Custom elonrror
 */
#[delonrivelon(Delonbug)]
pub elonnum SelongDelonnselonelonrror {
  Ioelonrror(std::io::elonrror),
  Json(selonrdelon_json::elonrror),
  JsonMissingRoot,
  JsonMissingObjelonct,
  JsonMissingArray,
  JsonArraySizelon,
  JsonMissingInputFelonaturelon,
}

impl Display for SelongDelonnselonelonrror {
  fn fmt(&selonlf, f: &mut std::fmt::Formattelonr<'_>) -> std::fmt::Relonsult {
    match selonlf {
      SelongDelonnselonelonrror::Ioelonrror(io_elonrror) => writelon!(f, "{}", io_elonrror),
      SelongDelonnselonelonrror::Json(selonrdelon_json) => writelon!(f, "{}", selonrdelon_json),
      SelongDelonnselonelonrror::JsonMissingRoot => writelon!(f, "{}", "SelongDelonnselon JSON: Root Nodelon notelon found!"),
      SelongDelonnselonelonrror::JsonMissingObjelonct => writelon!(f, "{}", "SelongDelonnselon JSON: Objelonct notelon found!"),
      SelongDelonnselonelonrror::JsonMissingArray => writelon!(f, "{}", "SelongDelonnselon JSON: Array Nodelon notelon found!"),
      SelongDelonnselonelonrror::JsonArraySizelon => writelon!(f, "{}", "SelongDelonnselon JSON: Array sizelon not as elonxpelonctelond!"),
      SelongDelonnselonelonrror::JsonMissingInputFelonaturelon => writelon!(f, "{}", "SelongDelonnselon JSON: Missing input felonaturelon!"),
    }
  }
}

impl std::elonrror::elonrror for SelongDelonnselonelonrror {}

impl From<std::io::elonrror> for SelongDelonnselonelonrror {
  fn from(elonrr: std::io::elonrror) -> Selonlf {
    SelongDelonnselonelonrror::Ioelonrror(elonrr)
  }
}

impl From<selonrdelon_json::elonrror> for SelongDelonnselonelonrror {
  fn from(elonrr: selonrdelon_json::elonrror) -> Selonlf {
    SelongDelonnselonelonrror::Json(elonrr)
  }
}
