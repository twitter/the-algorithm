use std::collect ons::BTreeSet;
use std::collect ons::BTreeMap;

use bpr_thr ft::data::DataRecord;
use bpr_thr ft::pred ct on_serv ce::BatchPred ct onRequest;
use thr ft::OrderedFloat;

use thr ft::protocol::TB nary nputProtocol;
use thr ft::protocol::TSer al zable;
use thr ft::transport::TBufferChannel;
use thr ft::Result;

fn ma n() {
  let data_path = "/tmp/current/t  l nes/output-1";
  let b n_data: Vec<u8> = std::fs::read(data_path).expect("Could not read f le!"); 

  pr ntln!("Length : {}", b n_data.len());

  let mut bc = TBufferChannel::w h_capac y(b n_data.len(), 0);

  bc.set_readable_bytes(&b n_data);

  let mut protocol = TB nary nputProtocol::new(bc, true); 

  let result: Result<BatchPred ct onRequest> =
    BatchPred ct onRequest::read_from_ n_protocol(&mut protocol);

  match result {
    Ok(bpr) => logBP(bpr),
    Err(err) => pr ntln!("Error {}", err),
  }
}

fn logBP(bpr: BatchPred ct onRequest) {
  pr ntln!("-------[OUTPUT]---------------");
  pr ntln!("data {:?}", bpr);
  pr ntln!("------------------------------");

  /* 
  let common = bpr.common_features;
  let recs = bpr. nd v dual_features_l st;

  pr ntln!("--------[Len : {}]------------------", recs.len());

  pr ntln!("-------[COMMON]---------------");
  match common {
    So (dr) => logDR(dr),
    None => pr ntln!("None"),
  }
  pr ntln!("------------------------------");
  for rec  n recs {
    logDR(rec);
  }
  pr ntln!("------------------------------");
  */
}

fn logDR(dr: DataRecord) {
  pr ntln!("--------[DR]------------------");

  match dr.b nary_features {
    So (bf) => logB n(bf),
    _ => (),
  }

  match dr.cont nuous_features {
    So (cf) => logCF(cf),
    _ => (),
  }
  pr ntln!("------------------------------");
}

fn logB n(b n: BTreeSet< 64>) {
  pr ntln!("B: {:?}", b n)
}

fn logCF(cf: BTreeMap< 64, OrderedFloat<f64>>) {
  for ( d, fs)  n cf {
    pr ntln!("C: {} -> [{}]",  d, fs);
  }
}
