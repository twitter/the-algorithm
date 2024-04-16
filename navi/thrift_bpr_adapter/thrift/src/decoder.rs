
// A feature value can be one of t se
enum FeatureVal {
  Empty,
  U8Vector(Vec<u8>),
  FloatVector(Vec<f32>),
}

// A Feture has a na  and a value
// T  na  for now  s ' d' of type str ng
// Eventually t  needs to be flex ble - example to accomodate feature- d
struct Feature {
   d: Str ng,
  val: FeatureVal,
}

 mpl Feature {
  fn new() -> Feature {
    Feature {
       d: Str ng::new(),
      val: FeatureVal::Empty
    }
  }
}

// A s ngle  nference record w ll have mult ple features
struct Record {
  f elds: Vec<Feature>,
}

 mpl Record {
  fn new() -> Record {
    Record { f elds: vec![] }
  }
}

// T   s t  ma n AP  used by external components
// G ven a ser al zed  nput, decode    nto Records
fn decode( nput: Vec<u8>) -> Vec<Record> {
  // For  lp ng def ne t   nterface
  vec![get_random_record(), get_random_record()]
}

// Used for test ng t  AP , w ll be eventually removed
fn get_random_record() -> Record {
  let mut record: Record = Record::new();

  let f1: Feature = Feature {
     d: Str ng::from("cont nuous_features"),
    val: FeatureVal::FloatVector(vec![1.0f32; 2134]),
  };

  record.f elds.push(f1);

  let f2: Feature = Feature {
     d: Str ng::from("user_embedd ng"),
    val: FeatureVal::FloatVector(vec![2.0f32; 200]),
  };

  record.f elds.push(f2);

  let f3: Feature = Feature {
     d: Str ng::from("author_embedd ng"),
    val: FeatureVal::FloatVector(vec![3.0f32; 200]),
  };

  record.f elds.push(f3);

  let f4: Feature = Feature {
     d: Str ng::from("b nary_features"),
    val: FeatureVal::U8Vector(vec![4u8; 43]),
  };

  record.f elds.push(f4);

  record
}

