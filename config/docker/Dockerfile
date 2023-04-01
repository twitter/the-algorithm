FROM ruby:3.0.5-buster

# Install additional packages
RUN apt-get update             && \
    apt-get install -y            \
        calibre                   \
        default-mysql-client      \
        phantomjs                 \
        shared-mime-info          \
        wkhtmltopdf               \
        zip

# Clean and mount repository at /otwa
RUN rm -rf /otwa && mkdir -p /otwa
WORKDIR /otwa
VOLUME /otwa

# Install ruby packages
COPY Gemfile .
COPY Gemfile.lock .
RUN gem install bundler -v 2.2.33 && bundle install

# Default command to run in a new container
EXPOSE 3000
CMD bundle exec rails s -p 3000
