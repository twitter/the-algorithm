class IndexTagNominationsOnTagSetNomination < ActiveRecord::Migration[5.2]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = TagNomination.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=tag_nominations \\
          --alter "ADD INDEX index_tag_nominations_on_tag_set_nomination_id_and_type (tag_set_nomination_id, type)" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_tag_nominations_old`;
      PTOSC
    else
      add_index :tag_nominations, [:tag_set_nomination_id, :type]
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = TagNomination.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=tag_nominations \\
          --alter "DROP INDEX index_tag_nominations_on_tag_set_nomination_id_and_type" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_tag_nominations_old`;
      PTOSC
    else
      remove_index :tag_nominations, [:tag_set_nomination_id, :type]
    end
  end
end
