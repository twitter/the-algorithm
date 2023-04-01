class RemoveHitsFromPreferences < ActiveRecord::Migration[5.1]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = Preference.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=preferences \\
          --alter "DROP COLUMN hide_all_hit_counts,
                   DROP COLUMN hide_private_hit_count,
                   DROP COLUMN hide_public_hit_count" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_preferences_old`;
      PTOSC
    else
      remove_column :preferences, :hide_all_hit_counts
      remove_column :preferences, :hide_private_hit_count
      remove_column :preferences, :hide_public_hit_count
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = Preference.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=preferences \\
          --alter "ADD COLUMN hide_all_hit_counts BOOLEAN NOT NULL DEFAULT 0,
                   ADD COLUMN hide_private_hit_count BOOLEAN NOT NULL DEFAULT 0,
                   ADD COLUMN hit_public_hit_count BOOLEAN NOT NULL DEFAULT 0" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_preferences_old`;
      PTOSC
    else
      add_column :preferences, :hide_all_hit_counts, :boolean, default: false, null: false
      add_column :preferences, :hide_private_hit_count, :boolean, default: false, null: false
      add_column :preferences, :hide_public_hit_count, :boolean, default: false, null: false
    end
  end
end
