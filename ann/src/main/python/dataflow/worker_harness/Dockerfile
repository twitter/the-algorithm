FROM --platform=linux/amd64 nvidia/cuda:11.2.2-cudnn8-runtime-ubuntu20.04

RUN \
  # Add Deadsnakes repository that has a variety of Python packages for Ubuntu.
  # See: https://launchpad.net/~deadsnakes/+archive/ubuntu/ppa
  apt-key adv --keyserver keyserver.ubuntu.com --recv-keys F23C5A6CF475977595C89F51BA6932366A755776 \
  && echo "deb http://ppa.launchpad.net/deadsnakes/ppa/ubuntu focal main" >> /etc/apt/sources.list.d/custom.list \
  && echo "deb-src http://ppa.launchpad.net/deadsnakes/ppa/ubuntu focal main" >> /etc/apt/sources.list.d/custom.list \
  && apt-get update \
  && apt-get install -y curl \
  python3.7 \
  # With python3.8 package, distutils need to be installed separately.
  python3.7-distutils \
  python3-dev \
  python3.7-dev \
  libpython3.7-dev \
  python3-apt \
  gcc \
  g++ \
  && rm -rf /var/lib/apt/lists/*
RUN update-alternatives --install /usr/bin/python python /usr/bin/python3.7 10
RUN rm -f /usr/bin/python3 && ln -s /usr/bin/python3.7 /usr/bin/python3
RUN \
  curl https://bootstrap.pypa.io/get-pip.py | python \
  && pip3 install pip==22.0.3 \
  && python3 -m pip install --no-cache-dir apache-beam[gcp]==2.39.0
# Verify that there are no conflicting dependencies.
RUN pip3 check

# Copy the Apache Beam worker dependencies from the Beam Python 3.7 SDK image.
COPY --from=apache/beam_python3.7_sdk:2.39.0 /opt/apache/beam /opt/apache/beam

# Set the entrypoint to Apache Beam SDK worker launcher.
ENTRYPOINT [ "/opt/apache/beam/boot" ]