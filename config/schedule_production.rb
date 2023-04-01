set :set_path_automatically, false
set :cron_log, "#{path}/log/whenever.log"

# Resend signup emails
every 1.day, at: '6:41 am' do
  rake "admin:resend_signup_emails"
end
