use anyhow::Result;
use log::{ nfo, warn};
use x509_parser::{prelude::{parse_x509_pem}, parse_x509_cert f cate};
use std::collect ons::HashMap;
use tok o::t  :: nstant;
use ton c::{
    Request,
    Response, Status, transport::{Cert f cate,  dent y, Server, ServerTlsConf g},
};

// protobuf related
use crate::tf_proto::tensorflow_serv ng::{
    Class f cat onRequest, Class f cat onResponse, GetModel tadataRequest,
    GetModel tadataResponse, Mult  nferenceRequest, Mult  nferenceResponse, Pred ctRequest,
    Pred ctResponse, Regress onRequest, Regress onResponse,
};
use crate::{kf_serv ng::{
    grpc_ nference_serv ce_server::Grpc nferenceServ ce, Model nferRequest, Model nferResponse,
    Model tadataRequest, Model tadataResponse, ModelReadyRequest, ModelReadyResponse,
    ServerL veRequest, ServerL veResponse, Server tadataRequest, Server tadataResponse,
    ServerReadyRequest, ServerReadyResponse,
}, ModelFactory, tf_proto::tensorflow_serv ng::pred ct on_serv ce_server::{
    Pred ct onServ ce, Pred ct onServ ceServer,
}, VERS ON, NAME};

use crate::Pred ctResult;
use crate::cl _args::{ARGS,  NPUTS, OUTPUTS};
use crate:: tr cs::{
    NAV _VERS ON, NUM_PRED CT ONS, NUM_REQUESTS_FA LED, NUM_REQUESTS_FA LED_BY_MODEL,
    NUM_REQUESTS_RECE VED, NUM_REQUESTS_RECE VED_BY_MODEL, RESPONSE_T ME_COLLECTOR,
    CERT_EXP RY_EPOCH
};
use crate::pred ct_serv ce::{Model, Pred ctServ ce};
use crate::tf_proto::tensorflow_serv ng::model_spec::Vers onCho ce::Vers on;
use crate::tf_proto::tensorflow_serv ng::ModelSpec;

#[der ve(Debug)]
pub enum Tensor nputEnum {
    Str ng(Vec<Vec<u8>>),
     nt(Vec< 32>),
     nt64(Vec< 64>),
    Float(Vec<f32>),
    Double(Vec<f64>),
    Boolean(Vec<bool>),
}

#[der ve(Debug)]
pub struct Tensor nput {
    pub tensor_data: Tensor nputEnum,
    pub na : Str ng,
    pub d ms: Opt on<Vec< 64>>,
}

 mpl Tensor nput {
    pub fn new(tensor_data: Tensor nputEnum, na : Str ng, d ms: Opt on<Vec< 64>>) -> Tensor nput {
        Tensor nput {
            tensor_data,
            na ,
            d ms,
        }
    }
}

 mpl Tensor nputEnum {
    #[ nl ne(always)]
    pub(crate) fn extend(&mut self, anot r: Tensor nputEnum) {
        match (self, anot r) {
            (Self::Str ng( nput), Self::Str ng(ex)) =>  nput.extend(ex),
            (Self:: nt( nput), Self:: nt(ex)) =>  nput.extend(ex),
            (Self:: nt64( nput), Self:: nt64(ex)) =>  nput.extend(ex),
            (Self::Float( nput), Self::Float(ex)) =>  nput.extend(ex),
            (Self::Double( nput), Self::Double(ex)) =>  nput.extend(ex),
            (Self::Boolean( nput), Self::Boolean(ex)) =>  nput.extend(ex),
            x => pan c!(" nput enum type not matc d.  nput:{:?}, ex:{:?}", x.0, x.1),
        }
    }
    #[ nl ne(always)]
    pub(crate) fn  rge_batch( nput_tensors: Vec<Vec<Tensor nput>>) -> Vec<Tensor nput> {
         nput_tensors
            . nto_ er()
            .reduce(|mut acc, e| {
                for ( , ext)  n acc. er_mut().z p(e) {
                     .tensor_data.extend(ext.tensor_data);
                }
                acc
            })
            .unwrap() // nvar ant:   expect t re's always rows  n  nput_tensors
    }
}


///entry po nt for tfServ ng gRPC
#[ton c::async_tra ]
 mpl<T: Model> Grpc nferenceServ ce for Pred ctServ ce<T> {
    async fn server_l ve(
        &self,
        _request: Request<ServerL veRequest>,
    ) -> Result<Response<ServerL veResponse>, Status> {
        un mple nted!()
    }
    async fn server_ready(
        &self,
        _request: Request<ServerReadyRequest>,
    ) -> Result<Response<ServerReadyResponse>, Status> {
        un mple nted!()
    }

    async fn model_ready(
        &self,
        _request: Request<ModelReadyRequest>,
    ) -> Result<Response<ModelReadyResponse>, Status> {
        un mple nted!()
    }

    async fn server_ tadata(
        &self,
        _request: Request<Server tadataRequest>,
    ) -> Result<Response<Server tadataResponse>, Status> {
        un mple nted!()
    }

    async fn model_ tadata(
        &self,
        _request: Request<Model tadataRequest>,
    ) -> Result<Response<Model tadataResponse>, Status> {
        un mple nted!()
    }

    async fn model_ nfer(
        &self,
        _request: Request<Model nferRequest>,
    ) -> Result<Response<Model nferResponse>, Status> {
        un mple nted!()
    }
}

#[ton c::async_tra ]
 mpl<T: Model> Pred ct onServ ce for Pred ctServ ce<T> {
    async fn class fy(
        &self,
        _request: Request<Class f cat onRequest>,
    ) -> Result<Response<Class f cat onResponse>, Status> {
        un mple nted!()
    }
    async fn regress(
        &self,
        _request: Request<Regress onRequest>,
    ) -> Result<Response<Regress onResponse>, Status> {
        un mple nted!()
    }
    async fn pred ct(
        &self,
        request: Request<Pred ctRequest>,
    ) -> Result<Response<Pred ctResponse>, Status> {
        NUM_REQUESTS_RECE VED. nc();
        let start =  nstant::now();
        let mut req = request. nto_ nner();
        let (model_spec, vers on) = req.take_model_spec();
        NUM_REQUESTS_RECE VED_BY_MODEL
            .w h_label_values(&[&model_spec])
            . nc();
        let  dx = Pred ctServ ce::<T>::get_model_ ndex(&model_spec).ok_or_else(|| {
            Status::fa led_precond  on(format!("model spec not found:{}", model_spec))
        })?;
        let  nput_spec = match  NPUTS[ dx].get() {
            So ( nput) =>  nput,
            _ => return Err(Status::not_found(format!("model  nput spec {}",  dx))),
        };
        let  nput_val = req.take_ nput_vals( nput_spec);
        self.pred ct( dx, vers on,  nput_val, start)
            .awa 
            .map_or_else(
                |e| {
                    NUM_REQUESTS_FA LED. nc();
                    NUM_REQUESTS_FA LED_BY_MODEL
                        .w h_label_values(&[&model_spec])
                        . nc();
                    Err(Status:: nternal(e.to_str ng()))
                },
                |res| {
                    RESPONSE_T ME_COLLECTOR
                        .w h_label_values(&[&model_spec])
                        .observe(start.elapsed().as_m ll s() as f64);

                    match res {
                        Pred ctResult::Ok(tensors, vers on) => {
                            let mut outputs = HashMap::new();
                            NUM_PRED CT ONS.w h_label_values(&[&model_spec]). nc();
                            //F XME: uncom nt w n pred ct on scores are normal
                            // PRED CT ON_SCORE_SUM
                            // .w h_label_values(&[&model_spec])
                            // . nc_by(tensors[0]as f64);
                            for (tp, output_na )  n tensors
                                . nto_ er()
                                .map(|tensor| tensor.create_tensor_proto())
                                .z p(OUTPUTS[ dx]. er())
                            {
                                outputs. nsert(output_na .to_owned(), tp);
                            }
                            let reply = Pred ctResponse {
                                model_spec: So (ModelSpec {
                                    vers on_cho ce: So (Vers on(vers on)),
                                    ..Default::default()
                                }),
                                outputs,
                            };
                            Ok(Response::new(reply))
                        }
                        Pred ctResult::DropDueToOverload => Err(Status::res ce_exhausted("")),
                        Pred ctResult::ModelNotFound( dx) => {
                            Err(Status::not_found(format!("model  ndex {}",  dx)))
                        },
                        Pred ctResult::ModelNotReady( dx) => {
                            Err(Status::unava lable(format!("model  ndex {}",  dx)))
                        }
                        Pred ctResult::ModelVers onNotFound( dx, vers on) => Err(
                            Status::not_found(format!("model  ndex:{}, vers on {}",  dx, vers on)),
                        ),
                    }
                },
            )
    }

    async fn mult _ nference(
        &self,
        _request: Request<Mult  nferenceRequest>,
    ) -> Result<Response<Mult  nferenceResponse>, Status> {
        un mple nted!()
    }
    async fn get_model_ tadata(
        &self,
        _request: Request<GetModel tadataRequest>,
    ) -> Result<Response<GetModel tadataResponse>, Status> {
        un mple nted!()
    }
}

// A funct on that takes a t  stamp as  nput and returns a t cker stream
fn report_exp ry(exp ry_t  :  64) {
     nfo!("Cert f cate exp res at epoch: {:?}", exp ry_t  );
    CERT_EXP RY_EPOCH.set(exp ry_t   as  64);
}

pub fn bootstrap<T: Model>(model_factory: ModelFactory<T>) -> Result<()> {
     nfo!("package: {}, vers on: {}, args: {:?}", NAME, VERS ON, *ARGS);
    //  follow SemVer. So  re   assu  MAJOR.M NOR.PATCH
    let parts = VERS ON
        .spl (".")
        .map(|v| v.parse::< 64>())
        .collect::<std::result::Result<Vec<_>, _>>()?;
     f let [major, m nor, patch] = &parts[..] {
        NAV _VERS ON.set(major * 1000_000 + m nor * 1000 + patch);
    } else {
        warn!(
            "vers on {} doesn't follow SemVer convers on of MAJOR.M NOR.PATCH",
            VERS ON
        );
    }

    
    tok o::runt  ::Bu lder::new_mult _thread()
        .thread_na ("async worker")
        .worker_threads(ARGS.num_worker_threads)
        .max_block ng_threads(ARGS.max_block ng_threads)
        .enable_all()
        .bu ld()
        .unwrap()
        .block_on(async {
            #[cfg(feature = "nav _console")]
            console_subscr ber:: n ();
            let addr = format!("0.0.0.0:{}", ARGS.port).parse()?;

            let ps = Pred ctServ ce:: n (model_factory).awa ;

            let mut bu lder =  f ARGS.ssl_d r. s_empty() {
                Server::bu lder()
            } else {
                // Read t  pem f le as a str ng
                let pem_str = std::fs::read_to_str ng(format!("{}/server.crt", ARGS.ssl_d r)).unwrap();
                let res = parse_x509_pem(&pem_str.as_bytes());
                match res {
                    Ok((rem, pem_2)) => {
                        assert!(rem. s_empty());
                        assert_eq!(pem_2.label, Str ng::from("CERT F CATE"));
                        let res_x509 = parse_x509_cert f cate(&pem_2.contents);
                         nfo!("Cert f cate label: {}", pem_2.label);
                        assert!(res_x509. s_ok());
                        report_exp ry(res_x509.unwrap().1.val d y().not_after.t  stamp());
                    },
                    _ => pan c!("PEM pars ng fa led: {:?}", res),
                }

                let key = tok o::fs::read(format!("{}/server.key", ARGS.ssl_d r))
                    .awa 
                    .expect("can't f nd key f le");
                let crt = tok o::fs::read(format!("{}/server.crt", ARGS.ssl_d r))
                    .awa 
                    .expect("can't f nd crt f le");
                let cha n = tok o::fs::read(format!("{}/server.cha n", ARGS.ssl_d r))
                    .awa 
                    .expect("can't f nd cha n f le");
                let mut pem = Vec::new();
                pem.extend(crt);
                pem.extend(cha n);
                let  dent y =  dent y::from_pem(pem.clone(), key);
                let cl ent_ca_cert = Cert f cate::from_pem(pem.clone());
                let tls = ServerTlsConf g::new()
                    . dent y( dent y) 
                    .cl ent_ca_root(cl ent_ca_cert);
                Server::bu lder()
                    .tls_conf g(tls)
                    .expect("fa l to conf g SSL")
            };

             nfo!(
                "Pro t us server started: 0.0.0.0: {}",
                ARGS.pro t us_port
            );

            let ps_server = bu lder
                .add_serv ce(Pred ct onServ ceServer::new(ps).accept_gz p().send_gz p())
                .serve(addr);
             nfo!("Pred ct on server started: {}", addr);
            ps_server.awa .map_err(anyhow::Error::msg)
        })
}
