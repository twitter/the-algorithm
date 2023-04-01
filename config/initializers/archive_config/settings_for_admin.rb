begin
  # If we have no database, fall through to rescue
  ActiveRecord::Base.connection
  AdminSetting.default if AdminSetting.table_exists?
rescue ActiveRecord::ConnectionNotEstablished
rescue ActiveRecord::NoDatabaseError
  # This happens if we are running rake db:create
end
