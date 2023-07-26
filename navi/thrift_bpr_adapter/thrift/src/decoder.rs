
// a featuwe vawue can be one of t-these
enum featuwevaw {
  e-empty, 😳😳😳
  u-u8vectow(vec<u8>), mya
  f-fwoatvectow(vec<f32>), 😳
}

// a-a fetuwe has a-a nyame and a v-vawue
// the nyame f-fow nyow is 'id' of type stwing
// eventuawwy this nyeeds to be fwexibwe - exampwe t-to accomodate featuwe-id
stwuct featuwe {
  i-id: stwing, -.-
  vaw: featuwevaw, 🥺
}

i-impw featuwe {
  fn nyew() -> featuwe {
    featuwe {
      i-id: stwing::new(), o.O
      vaw: featuwevaw::empty
    }
  }
}

// a-a singwe infewence w-wecowd wiww have muwtipwe featuwes
stwuct wecowd {
  fiewds: vec<featuwe>, /(^•ω•^)
}

i-impw wecowd {
  fn nyew() -> wecowd {
    wecowd { fiewds: vec![] }
  }
}

// this is the main a-api used by extewnaw components
// g-given a sewiawized i-input, nyaa~~ decode i-it into wecowds
f-fn decode(input: vec<u8>) -> vec<wecowd> {
  // f-fow hewping define the intewface
  vec![get_wandom_wecowd(), nyaa~~ g-get_wandom_wecowd()]
}

// used fow testing the api, :3 wiww be eventuawwy wemoved
fn get_wandom_wecowd() -> w-wecowd {
  wet mut wecowd: w-wecowd = w-wecowd::new();

  w-wet f1: featuwe = featuwe {
    id: stwing::fwom("continuous_featuwes"), 😳😳😳
    vaw: featuwevaw::fwoatvectow(vec![1.0f32; 2134]), (˘ω˘)
  };

  w-wecowd.fiewds.push(f1);

  w-wet f2: featuwe = featuwe {
    i-id: stwing::fwom("usew_embedding"), ^^
    v-vaw: featuwevaw::fwoatvectow(vec![2.0f32; 200]), :3
  };

  w-wecowd.fiewds.push(f2);

  wet f3: featuwe = f-featuwe {
    id: stwing::fwom("authow_embedding"), -.-
    vaw: featuwevaw::fwoatvectow(vec![3.0f32; 200]), 😳
  };

  w-wecowd.fiewds.push(f3);

  wet f-f4: featuwe = featuwe {
    id: s-stwing::fwom("binawy_featuwes"), mya
    v-vaw: featuwevaw::u8vectow(vec![4u8; 43]), (˘ω˘)
  };

  wecowd.fiewds.push(f4);

  wecowd
}

