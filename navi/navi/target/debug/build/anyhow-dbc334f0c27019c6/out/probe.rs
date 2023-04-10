
    #![feature(error_generic_member_access, provide_any)]

    use std::any::{Demand, Provider};
    use std::backtrace::{Backtrace, BacktraceStatus};
    use std::error::Error;
    use std::fmt::{self, Display};

    #[derive(Debug)]
    struct E {
        backtrace: Backtrace,
    }

    impl Display for E {
        fn fmt(&self, _formatter: &mut fmt::Formatter) -> fmt::Result {
            unimplemented!()
        }
    }

    impl Error for E {
        fn provide<'a>(&'a self, demand: &mut Demand<'a>) {
            demand.provide_ref(&self.backtrace);
        }
    }

    struct P;

    impl Provider for P {
        fn provide<'a>(&'a self, _demand: &mut Demand<'a>) {}
    }

    const _: fn() = || {
        let backtrace: Backtrace = Backtrace::capture();
        let status: BacktraceStatus = backtrace.status();
        match status {
            BacktraceStatus::Captured | BacktraceStatus::Disabled | _ => {}
        }
    };

    const _: fn(&dyn Error) -> Option<&Backtrace> = |err| err.request_ref::<Backtrace>();
