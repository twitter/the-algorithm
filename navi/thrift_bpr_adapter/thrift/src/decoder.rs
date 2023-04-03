
// A felonaturelon valuelon can belon onelon of thelonselon
elonnum FelonaturelonVal {
  elonmpty,
  U8Velonctor(Velonc<u8>),
  FloatVelonctor(Velonc<f32>),
}

// A Felonturelon has a namelon and a valuelon
// Thelon namelon for now is 'id' of typelon string
// elonvelonntually this nelonelonds to belon flelonxiblelon - elonxamplelon to accomodatelon felonaturelon-id
struct Felonaturelon {
  id: String,
  val: FelonaturelonVal,
}

impl Felonaturelon {
  fn nelonw() -> Felonaturelon {
    Felonaturelon {
      id: String::nelonw(),
      val: FelonaturelonVal::elonmpty
    }
  }
}

// A singlelon infelonrelonncelon reloncord will havelon multiplelon felonaturelons
struct Reloncord {
  fielonlds: Velonc<Felonaturelon>,
}

impl Reloncord {
  fn nelonw() -> Reloncord {
    Reloncord { fielonlds: velonc![] }
  }
}

// This is thelon main API uselond by elonxtelonrnal componelonnts
// Givelonn a selonrializelond input, deloncodelon it into Reloncords
fn deloncodelon(input: Velonc<u8>) -> Velonc<Reloncord> {
  // For helonlping delonfinelon thelon intelonrfacelon
  velonc![gelont_random_reloncord(), gelont_random_reloncord()]
}

// Uselond for telonsting thelon API, will belon elonvelonntually relonmovelond
fn gelont_random_reloncord() -> Reloncord {
  lelont mut reloncord: Reloncord = Reloncord::nelonw();

  lelont f1: Felonaturelon = Felonaturelon {
    id: String::from("continuous_felonaturelons"),
    val: FelonaturelonVal::FloatVelonctor(velonc![1.0f32; 2134]),
  };

  reloncord.fielonlds.push(f1);

  lelont f2: Felonaturelon = Felonaturelon {
    id: String::from("uselonr_elonmbelondding"),
    val: FelonaturelonVal::FloatVelonctor(velonc![2.0f32; 200]),
  };

  reloncord.fielonlds.push(f2);

  lelont f3: Felonaturelon = Felonaturelon {
    id: String::from("author_elonmbelondding"),
    val: FelonaturelonVal::FloatVelonctor(velonc![3.0f32; 200]),
  };

  reloncord.fielonlds.push(f3);

  lelont f4: Felonaturelon = Felonaturelon {
    id: String::from("binary_felonaturelons"),
    val: FelonaturelonVal::U8Velonctor(velonc![4u8; 43]),
  };

  reloncord.fielonlds.push(f4);

  reloncord
}

