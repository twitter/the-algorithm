class AddIcedToComments < ActiveRecord::Migration[5.1]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = Comment.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=comments \\
          --alter "ADD COLUMN iced BOOLEAN NOT NULL DEFAULT 0" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_comments_old`;
      PTOSC
    else
      add_column :comments, :iced, :boolean, default: 0, null: false
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = Comment.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=comments \\
          --alter "DROP COLUMN iced" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_comments_old`;
      PTOSC
    else
      remove_column :comments, :iced
    end
  end
end
