class RemoveAnonCommentingDisabledFromWorks < ActiveRecord::Migration[5.1]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = Work.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=works \\
          --alter "DROP COLUMN anon_commenting_disabled" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_works_old`;
      PTOSC
    else
      remove_column :works, :anon_commenting_disabled
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = Work.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=works \\
          --alter "ADD COLUMN anon_commenting_disabled tinyint(1) NOT NULL DEFAULT 0" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_works_old`;
      PTOSC
    else
      add_column :works, :anon_commenting_disabled, :boolean, default: false, null: false
    end
  end
end
