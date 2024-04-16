#[cfg(feature = "tf")]
pub mod tf {
    use arrayvec::ArrayVec;
    use  ertools:: ertools;
    use log::{debug, error,  nfo, warn};
    use prost:: ssage;
    use std::fmt;
    use std::fmt::D splay;
    use std::str ng::Str ng;
    use tensorflow:: o::{RecordReader, RecordReadError};
    use tensorflow::Operat on;
    use tensorflow::SavedModelBundle;
    use tensorflow::Sess onOpt ons;
    use tensorflow::Sess onRunArgs;
    use tensorflow::Tensor;
    use tensorflow::{DataType, FetchToken, Graph, Tensor nfo, TensorType};

    use std::thread::sleep;
    use std::t  ::Durat on;

    use crate::cl _args::{Args, ARGS,  NPUTS, MODEL_SPECS, OUTPUTS};
    use crate::tf_proto::tensorflow_serv ng::pred ct on_log::LogType;
    use crate::tf_proto::tensorflow_serv ng::{Pred ct onLog, Pred ctLog};
    use crate::tf_proto::Conf gProto;
    use anyhow::{Context, Result};
    use serde_json::Value;

    use crate::TensorReturnEnum;
    use crate::bootstrap::{Tensor nput, Tensor nputEnum};
    use crate:: tr cs::{
         NFERENCE_FA LED_REQUESTS_BY_MODEL, NUM_REQUESTS_FA LED, NUM_REQUESTS_FA LED_BY_MODEL,
    };
    use crate::pred ct_serv ce::Model;
    use crate::{MAX_NUM_ NPUTS, ut ls};

    #[der ve(Debug)]
    pub enum TFTensorEnum {
        Str ng(Tensor<Str ng>),
         nt(Tensor< 32>),
         nt64(Tensor< 64>),
        Float(Tensor<f32>),
        Double(Tensor<f64>),
        Boolean(Tensor<bool>),
    }

    #[der ve(Debug)]
    pub struct TFModel {
        pub model_ dx: us ze,
        pub bundle: SavedModelBundle,
        pub  nput_na s: ArrayVec<Str ng, MAX_NUM_ NPUTS>,
        pub  nput_ nfo: Vec<Tensor nfo>,
        pub  nput_ops: Vec<Operat on>,
        pub output_na s: Vec<Str ng>,
        pub output_ nfo: Vec<Tensor nfo>,
        pub output_ops: Vec<Operat on>,
        pub export_d r: Str ng,
        pub vers on:  64,
        pub  nter_op:  32,
        pub  ntra_op:  32,
    }

     mpl D splay for TFModel {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            wr e!(
                f,
                " dx: {}, tensorflow model_na :{}, export_d r:{}, vers on:{},  nter:{},  ntra:{}",
                self.model_ dx,
                MODEL_SPECS[self.model_ dx],
                self.export_d r,
                self.vers on,
                self. nter_op,
                self. ntra_op
            )
        }
    }

     mpl TFModel {
        pub fn new( dx: us ze, vers on: Str ng, model_conf g: &Value) -> Result<TFModel> {
            // Create  nput var ables for   add  on
            let conf g = Conf gProto {
                 ntra_op_parallel sm_threads: ut ls::get_conf g_or(
                    model_conf g,
                    " ntra_op_parallel sm",
                    &ARGS. ntra_op_parallel sm[ dx],
                )
                .parse()?,
                 nter_op_parallel sm_threads: ut ls::get_conf g_or(
                    model_conf g,
                    " nter_op_parallel sm",
                    &ARGS. nter_op_parallel sm[ dx],
                )
                .parse()?,
                ..Default::default()
            };
            let mut buf = Vec::new();
            buf.reserve(conf g.encoded_len());
            conf g.encode(&mut buf).unwrap();
            let mut opts = Sess onOpt ons::new();
            opts.set_conf g(&buf)?;
            let export_d r = format!("{}/{}", ARGS.model_d r[ dx], vers on);
            let mut graph = Graph::new();
            let bundle = SavedModelBundle::load(&opts, ["serve"], &mut graph, &export_d r)
                .context("error load model")?;
            let s gnature = bundle
                . ta_graph_def()
                .get_s gnature(&ARGS.serv ng_s g[ dx])
                .context("error f nd ng s gnature")?;
            let  nput_na s =  NPUTS[ dx]
                .get_or_ n (|| {
                    let  nput_spec = s gnature
                        . nputs()
                        . er()
                        .map(|p| p.0.clone())
                        .collect::<ArrayVec<Str ng, MAX_NUM_ NPUTS>>();
                     nfo!(
                        " nput not set from cl , now   set from model  tadata:{:?}",
                         nput_spec
                    );
                     nput_spec
                })
                .clone();
            let  nput_ nfo =  nput_na s
                . er()
                .map(| | {
                    s gnature
                        .get_ nput( )
                        .context("error f nd ng  nput op  nfo")
                        .unwrap()
                        .clone()
                })
                .collect_vec();

            let  nput_ops =  nput_ nfo
                . er()
                .map(| | {
                    graph
                        .operat on_by_na _requ red(& .na ().na )
                        .context("error f nd ng  nput op")
                        .unwrap()
                })
                .collect_vec();

             nfo!("Model  nput s ze: {}",  nput_ nfo.len());

            let output_na s = OUTPUTS[ dx].to_vec().clone();

            let output_ nfo = output_na s
                . er()
                .map(|o| {
                    s gnature
                        .get_output(o)
                        .context("error f nd ng output op  nfo")
                        .unwrap()
                        .clone()
                })
                .collect_vec();

            let output_ops = output_ nfo
                . er()
                .map(|o| {
                    graph
                        .operat on_by_na _requ red(&o.na ().na )
                        .context("error f nd ng output op")
                        .unwrap()
                })
                .collect_vec();

            let tf_model = TFModel {
                model_ dx:  dx,
                bundle,
                 nput_na s,
                 nput_ nfo,
                 nput_ops,
                output_na s,
                output_ nfo,
                output_ops,
                export_d r,
                vers on: Args::vers on_str_to_epoch(&vers on)?,
                 nter_op: conf g. nter_op_parallel sm_threads,
                 ntra_op: conf g. ntra_op_parallel sm_threads,
            };
            tf_model.warmup()?;
            Ok(tf_model)
        }

        #[ nl ne(always)]
        fn get_tftensor_d  ns ons<T>(
            t: &[T],
             nput_s ze: u64,
            batch_s ze: u64,
             nput_d ms: Opt on<Vec< 64>>,
        ) -> Vec<u64> {
            //  f  nput s ze  s 1,   just spec fy a s ngle d  ns on to outgo ng tensor match ng t 
            // s ze of t   nput tensor. T   s for backwards compat bl y w h ex st ng Nav  cl ents
            // wh ch spec fy  nput as a s ngle str ng tensor (l ke tfexample) and use batch ng support.
            let mut d ms = vec![];
             f  nput_s ze > 1 {
                 f batch_s ze == 1 &&  nput_d ms. s_so () {
                    // cl ent s de batch ng  s enabled?
                     nput_d ms
                        .unwrap()
                        . er()
                        .for_each(|ax s| d ms.push(*ax s as u64));
                } else {
                    d ms.push(batch_s ze);
                    d ms.push(t.len() as u64 / batch_s ze);
                }
            } else {
                d ms.push(t.len() as u64);
            }
            d ms
        }

        fn convert_to_tftensor_enum(
             nput: Tensor nput,
             nput_s ze: u64,
            batch_s ze: u64,
        ) -> TFTensorEnum {
            match  nput.tensor_data {
                Tensor nputEnum::Str ng(t) => {
                    let str ngs = t
                        . nto_ er()
                        .map(|x| unsafe { Str ng::from_utf8_unc cked(x) })
                        .collect_vec();
                    TFTensorEnum::Str ng(
                        Tensor::new(&TFModel::get_tftensor_d  ns ons(
                            str ngs.as_sl ce(),
                             nput_s ze,
                            batch_s ze,
                             nput.d ms,
                        ))
                        .w h_values(str ngs.as_sl ce())
                        .unwrap(),
                    )
                }
                Tensor nputEnum:: nt(t) => TFTensorEnum:: nt(
                    Tensor::new(&TFModel::get_tftensor_d  ns ons(
                        t.as_sl ce(),
                         nput_s ze,
                        batch_s ze,
                         nput.d ms,
                    ))
                    .w h_values(t.as_sl ce())
                    .unwrap(),
                ),
                Tensor nputEnum:: nt64(t) => TFTensorEnum:: nt64(
                    Tensor::new(&TFModel::get_tftensor_d  ns ons(
                        t.as_sl ce(),
                         nput_s ze,
                        batch_s ze,
                         nput.d ms,
                    ))
                    .w h_values(t.as_sl ce())
                    .unwrap(),
                ),
                Tensor nputEnum::Float(t) => TFTensorEnum::Float(
                    Tensor::new(&TFModel::get_tftensor_d  ns ons(
                        t.as_sl ce(),
                         nput_s ze,
                        batch_s ze,
                         nput.d ms,
                    ))
                    .w h_values(t.as_sl ce())
                    .unwrap(),
                ),
                Tensor nputEnum::Double(t) => TFTensorEnum::Double(
                    Tensor::new(&TFModel::get_tftensor_d  ns ons(
                        t.as_sl ce(),
                         nput_s ze,
                        batch_s ze,
                         nput.d ms,
                    ))
                    .w h_values(t.as_sl ce())
                    .unwrap(),
                ),
                Tensor nputEnum::Boolean(t) => TFTensorEnum::Boolean(
                    Tensor::new(&TFModel::get_tftensor_d  ns ons(
                        t.as_sl ce(),
                         nput_s ze,
                        batch_s ze,
                         nput.d ms,
                    ))
                    .w h_values(t.as_sl ce())
                    .unwrap(),
                ),
            }
        }
        fn fetch_output<T: TensorType>(
            args: &mut Sess onRunArgs,
            token_output: &FetchToken,
            batch_s ze: u64,
            output_s ze: u64,
        ) -> (Tensor<T>, u64) {
            let tensor_output = args.fetch::<T>(*token_output).expect("fetch output fa led");
            let mut tensor_w dth = tensor_output.d ms()[1];
             f batch_s ze == 1 && output_s ze > 1 {
                tensor_w dth = tensor_output.d ms(). er().fold(1, |mut total, &val| {
                    total *= val;
                    total
                });
            }
            (tensor_output, tensor_w dth)
        }
    }

     mpl Model for TFModel {
        fn warmup(&self) -> Result<()> {
            // warm up
            let warmup_f le = format!(
                "{}/assets.extra/tf_serv ng_warmup_requests",
                self.export_d r
            );
             f std::path::Path::new(&warmup_f le).ex sts() {
                use std:: o::Cursor;
                 nfo!(
                    "found warmup assets  n {}, now perform warm ng up",
                    warmup_f le
                );
                let f = std::fs::F le::open(warmup_f le).context("cannot open warmup f le")?;
                // let mut buf = Vec::new();
                let read = std:: o::BufReader::new(f);
                let mut reader = RecordReader::new(read);
                let mut warmup_cnt = 0;
                loop {
                    let next = reader.read_next_owned();
                    match next {
                        Ok(res) => match res {
                            So (vec) => {
                                //  nfo!("read one tfRecord");
                                match Pred ct onLog::decode(&mut Cursor::new(vec))
                                    .context("can't parse Pred ctonLog")?
                                {
                                    Pred ct onLog {
                                        log_ tadata: _,
                                        log_type:
                                            So (LogType::Pred ctLog(Pred ctLog {
                                                request: So (mut req),
                                                response: _,
                                            })),
                                    } => {
                                         f warmup_cnt == ARGS.max_warmup_records {
                                            //warm up to max_warmup_records  records
                                            warn!(
                                                "reac d max warmup {} records, ex  warmup for {}",
                                                ARGS.max_warmup_records,
                                                MODEL_SPECS[self.model_ dx]
                                            );
                                            break;
                                        }
                                        self.do_pred ct(
                                            vec![req.take_ nput_vals(&self. nput_na s)],
                                            1,
                                        );
                                        sleep(Durat on::from_m ll s(100));
                                        warmup_cnt += 1;
                                    }
                                    _ => error!("so  wrong record  n warm ng up f le"),
                                }
                            }
                            None => {
                                 nfo!("end of warmup f le, war d up w h records: {}", warmup_cnt);
                                break;
                            }
                        },
                        Err(RecordReadError::CorruptF le)
                        | Err(RecordReadError:: oError { .. }) => {
                            error!("read tfrecord error for warmup f les, sk p");
                        }
                        _ => {}
                    }
                }
            }
            Ok(())
        }

        #[ nl ne(always)]
        fn do_pred ct(
            &self,
             nput_tensors: Vec<Vec<Tensor nput>>,
            batch_s ze: u64,
        ) -> (Vec<TensorReturnEnum>, Vec<Vec<us ze>>) {
            // let mut batch_ends =  nput_tensors. er().map(|t| t.len()).collect::<Vec<us ze>>();
            let output_s ze = self.output_na s.len() as u64;
            let  nput_s ze = self. nput_na s.len() as u64;
            debug!(
                "Request for Tensorflow w h batch s ze: {} and  nput_s ze: {}",
                batch_s ze,  nput_s ze
            );
            // bu ld a set of  nput TF tensors

            let batch_end = (1us ze..= nput_tensors.len() as us ze)
                . nto_ er()
                .collect_vec();
            let mut batch_ends = vec![batch_end; output_s ze as us ze];

            let batc d_tensors = Tensor nputEnum:: rge_batch( nput_tensors)
                . nto_ er()
                .enu rate()
                .map(|(_,  )| TFModel::convert_to_tftensor_enum( ,  nput_s ze, batch_s ze))
                .collect_vec();

            let mut args = Sess onRunArgs::new();
            for ( ndex, tf_tensor)  n batc d_tensors. er().enu rate() {
                match tf_tensor {
                    TFTensorEnum::Str ng( nner) => args.add_feed(&self. nput_ops[ ndex], 0,  nner),
                    TFTensorEnum:: nt( nner) => args.add_feed(&self. nput_ops[ ndex], 0,  nner),
                    TFTensorEnum:: nt64( nner) => args.add_feed(&self. nput_ops[ ndex], 0,  nner),
                    TFTensorEnum::Float( nner) => args.add_feed(&self. nput_ops[ ndex], 0,  nner),
                    TFTensorEnum::Double( nner) => args.add_feed(&self. nput_ops[ ndex], 0,  nner),
                    TFTensorEnum::Boolean( nner) => args.add_feed(&self. nput_ops[ ndex], 0,  nner),
                }
            }
            // For output ops,   rece ve t  sa  op object by na . Actual tensor tokens are ava lable at d fferent offsets.
            // S nce  nd ces are ordered,  s  mportant to spec fy output flag to Nav   n t  sa  order.
            let token_outputs = self
                .output_ops
                . er()
                .enu rate()
                .map(|( dx, op)| args.request_fetch(op,  dx as  32))
                .collect_vec();
            match self.bundle.sess on.run(&mut args) {
                Ok(_) => (),
                Err(e) => {
                    NUM_REQUESTS_FA LED. nc_by(batch_s ze);
                    NUM_REQUESTS_FA LED_BY_MODEL
                        .w h_label_values(&[&MODEL_SPECS[self.model_ dx]])
                        . nc_by(batch_s ze);
                     NFERENCE_FA LED_REQUESTS_BY_MODEL
                        .w h_label_values(&[&MODEL_SPECS[self.model_ dx]])
                        . nc_by(batch_s ze);
                    pan c!("{model}: {e:?}", model = MODEL_SPECS[self.model_ dx], e = e);
                }
            }
            let mut pred ct_return = vec![];
            // C ck t  output.
            for ( ndex, token_output)  n token_outputs. er().enu rate() {
                // sa  ops, w h type  nfo at d fferent offsets.
                let (res, w dth) = match self.output_ops[ ndex].output_type( ndex) {
                    DataType::Float => {
                        let (tensor_output, tensor_w dth) =
                            TFModel::fetch_output(&mut args, token_output, batch_s ze, output_s ze);
                        (
                            TensorReturnEnum::FloatTensorReturn(Box::new(tensor_output)),
                            tensor_w dth,
                        )
                    }
                    DataType:: nt64 => {
                        let (tensor_output, tensor_w dth) =
                            TFModel::fetch_output(&mut args, token_output, batch_s ze, output_s ze);
                        (
                            TensorReturnEnum:: nt64TensorReturn(Box::new(tensor_output)),
                            tensor_w dth,
                        )
                    }
                    DataType:: nt32 => {
                        let (tensor_output, tensor_w dth) =
                            TFModel::fetch_output(&mut args, token_output, batch_s ze, output_s ze);
                        (
                            TensorReturnEnum:: nt32TensorReturn(Box::new(tensor_output)),
                            tensor_w dth,
                        )
                    }
                    DataType::Str ng => {
                        let (tensor_output, tensor_w dth) =
                            TFModel::fetch_output(&mut args, token_output, batch_s ze, output_s ze);
                        (
                            TensorReturnEnum::Str ngTensorReturn(Box::new(tensor_output)),
                            tensor_w dth,
                        )
                    }
                    _ => pan c!("Unsupported return type!"),
                };
                let w dth = w dth as us ze;
                for b  n batch_ends[ ndex]. er_mut() {
                    *b *= w dth;
                }
                pred ct_return.push(res)
            }
            //TODO: remove  n t  future
            //TODO: support actual mtl model outputs
            (pred ct_return, batch_ends)
        }
        #[ nl ne(always)]
        fn model_ dx(&self) -> us ze {
            self.model_ dx
        }
        #[ nl ne(always)]
        fn vers on(&self) ->  64 {
            self.vers on
        }
    }
}
