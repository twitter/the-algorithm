class DropOldColumnsFromWorks < ActiveRecord::Migration[5.1]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = Work.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=works \\
          --alter "DROP COLUMN last_visitor_old,
                   DROP COLUMN hit_count_old" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_works_old`;
      PTOSC
    else
      remove_column :works, :last_visitor_old
      remove_column :works, :hit_count_old
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = Work.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=works \\
          --alter "ADD COLUMN last_visitor_old VARCHAR(255),
                   ADD COLUMN hit_count_old INT NOT NULL DEFAULT 0" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_works_old`;
      PTOSC
    else
      add_column :works, :last_visitor_old, :string
      add_column :works, :hit_count_old, :int, null: false, default: 0
    end
  end
end
