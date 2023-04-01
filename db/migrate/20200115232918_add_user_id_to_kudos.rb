class AddUserIdToKudos < ActiveRecord::Migration[5.1]
  def up
    if Rails.env.staging? || Rails.env.production?
=begin
      pt-online-schema-change -uroot --alter \
      "ADD COLUMN user_id INT, ADD INDEX index_kudos_on_user_id (user_id)" \
      D=$DATABASE,t=kudos --chunk-size=10k --max-flow-ctl 0 \
      --pause-file /tmp/pauseme --max-load Threads_running=25 \
      --critical-load Threads_running=400 --set-vars innodb_lock_wait_timeout=2 \
      --alter-foreign-keys-method=auto --execute
=end
    else
      add_column :kudos, :user_id, :int
      add_index :kudos, :user_id
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
=begin
      pt-online-schema-change -uroot --alter "DROP COLUMN user_id" \
      D=$DATABASE,t=kudos --chunk-size=10k --max-flow-ctl 0 \
      --pause-file /tmp/pauseme --max-load Threads_running=25 \
      --critical-load Threads_running=400 --set-vars innodb_lock_wait_timeout=2 \
      --alter-foreign-keys-method=auto --execute
=end
    else
      remove_column :kudos, :user_id
    end
  end
end
