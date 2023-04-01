class DropLastVisitorFromStatCounters < ActiveRecord::Migration[5.1]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = StatCounter.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=stat_counters \\
          --alter "DROP COLUMN last_visitor" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_stat_counters_old`;
      PTOSC
    else
      remove_column :stat_counters, :last_visitor
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = StatCounter.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=stat_counters \\
          --alter "ADD COLUMN last_visitor varchar(255)" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_stat_counters_old`;
      PTOSC
    else
      add_column :stat_counters, :last_visitor, :string
    end
  end
end
