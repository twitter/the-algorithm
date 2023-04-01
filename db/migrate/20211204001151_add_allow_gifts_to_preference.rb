class AddAllowGiftsToPreference < ActiveRecord::Migration[5.2]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = Preference.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=preferences \\
          --alter "ADD COLUMN allow_gifts BOOLEAN NOT NULL DEFAULT 1" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_preferences_old`;
      PTOSC
    else
      add_column :preferences, :allow_gifts, :boolean, default: true, null: true
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = Preference.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=preferences \\
          --alter "DROP COLUMN allow_gifts" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_preferences_old`;
      PTOSC
    else
      remove_column :preferences, :allow_gifts
    end
  end
end
