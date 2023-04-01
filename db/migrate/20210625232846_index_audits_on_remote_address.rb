class IndexAuditsOnRemoteAddress < ActiveRecord::Migration[5.2]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = ActiveRecord::Base.connection.current_database

      puts <<~PTOSC
        Schema Change Command:
        pt-online-schema-change D=#{database},t=audits \\
          --alter "ADD INDEX index_audits_on_remote_address (remote_address)" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_audits_old`;
      PTOSC
    else
      add_index :audits, :remote_address, name: "index_audits_on_remote_address"
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = ActiveRecord::Base.connection.current_database

      puts <<~PTOSC
        Schema Change Command:
        pt-online-schema-change D=#{database},t=audits \\
          --alter "DROP INDEX index_audits_on_remote_address" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_audits_old`;
      PTOSC
    else
      remove_index :audits, name: "index_audits_on_remote_address"
    end
  end
end
