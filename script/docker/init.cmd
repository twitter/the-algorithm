:: Change directory to root of the repo
cd /D "%~dp0\..\.."

copy config\docker\database.yml config\database.yml
copy config\docker\redis.yml config\redis.yml
copy config\docker\local.yml config\local.yml

docker-compose up -d

timeout 60

docker-compose run -e RAILS_ENV=development web script/reset_database.sh
docker-compose run -e RAILS_ENV=test web script/reset_database.sh
