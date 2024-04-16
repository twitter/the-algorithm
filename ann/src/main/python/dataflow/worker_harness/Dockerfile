FROM --platform=l nux/amd64 nv d a/cuda:11.2.2-cudnn8-runt  -ubuntu20.04

RUN \
  # Add Deadsnakes repos ory that has a var ety of Python packages for Ubuntu.
  # See: https://launchpad.net/~deadsnakes/+arch ve/ubuntu/ppa
  apt-key adv --keyserver keyserver.ubuntu.com --recv-keys F23C5A6CF475977595C89F51BA6932366A755776 \
  && echo "deb http://ppa.launchpad.net/deadsnakes/ppa/ubuntu focal ma n" >> /etc/apt/s ces.l st.d/custom.l st \
  && echo "deb-src http://ppa.launchpad.net/deadsnakes/ppa/ubuntu focal ma n" >> /etc/apt/s ces.l st.d/custom.l st \
  && apt-get update \
  && apt-get  nstall -y curl \
  python3.7 \
  # W h python3.8 package, d stut ls need to be  nstalled separately.
  python3.7-d stut ls \
  python3-dev \
  python3.7-dev \
  l bpython3.7-dev \
  python3-apt \
  gcc \
  g++ \
  && rm -rf /var/l b/apt/l sts/*
RUN update-alternat ves -- nstall /usr/b n/python python /usr/b n/python3.7 10
RUN rm -f /usr/b n/python3 && ln -s /usr/b n/python3.7 /usr/b n/python3
RUN \
  curl https://bootstrap.pypa. o/get-p p.py | python \
  && p p3  nstall p p==22.0.3 \
  && python3 -m p p  nstall --no-cac -d r apac -beam[gcp]==2.39.0
# Ver fy that t re are no confl ct ng dependenc es.
RUN p p3 c ck

# Copy t  Apac  Beam worker dependenc es from t  Beam Python 3.7 SDK  mage.
COPY --from=apac /beam_python3.7_sdk:2.39.0 /opt/apac /beam /opt/apac /beam

# Set t  entrypo nt to Apac  Beam SDK worker launc r.
ENTRYPO NT [ "/opt/apac /beam/boot" ]