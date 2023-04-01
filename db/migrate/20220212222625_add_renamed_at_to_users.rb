class AddRenamedAtToUsers < ActiveRecord::Migration[5.2]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = User.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=users \\
          --alter "ADD COLUMN renamed_at DATETIME" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_users_old`;
      PTOSC
    else
      add_column :users, :renamed_at, :datetime, null: true
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = User.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=users \\
          --alter "DROP COLUMN renamed_at" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_users_old`;
      PTOSC
    else
      remove_column :users, :renamed_at
    end
  end
end
