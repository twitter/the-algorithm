class DropPseudIdFromKudos < ActiveRecord::Migration[5.1]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = Kudo.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=kudos \\
          --alter "DROP COLUMN pseud_id,
                   DROP INDEX index_kudos_on_commentable_id_and_commentable_type_and_pseud_id" \\
          --no-swap-tables --no-drop-new-table --no-drop-triggers \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Swap and Cleanup Commands:

        RENAME TABLE
          `#{database}`.`kudos` TO `#{database}`.`_kudos_old`,
          `#{database}`.`_kudos_new` TO `#{database}`.`kudos`;

        DROP TRIGGER IF EXISTS `#{database}`.`pt_osc_#{database}_kudos_del`;
        DROP TRIGGER IF EXISTS `#{database}`.`pt_osc_#{database}_kudos_ins`;
        DROP TRIGGER IF EXISTS `#{database}`.`pt_osc_#{database}_kudos_upd`;

        DROP TABLE IF EXISTS `#{database}`.`_kudos_old`;
      PTOSC
    else
      remove_column :kudos, :pseud_id
      # index_kudos_on_pseud_id is automatically dropped with the column
      remove_index :kudos, name: "index_kudos_on_commentable_id_and_commentable_type_and_pseud_id"
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = Kudo.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=kudos \\
          --alter "ADD COLUMN pseud_id INT,
                   ADD INDEX index_kudos_on_pseud_id (pseud_id),
                   ADD INDEX index_kudos_on_commentable_id_and_commentable_type_and_pseud_id (commentable_id, commentable_type, pseud_id)" \\
          --no-swap-tables --no-drop-new-table --no-drop-triggers \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Swap and Cleanup Commands:

        RENAME TABLE
          `#{database}`.`kudos` TO `#{database}`.`_kudos_old`,
          `#{database}`.`_kudos_new` TO `#{database}`.`kudos`;

        DROP TRIGGER IF EXISTS `#{database}`.`pt_osc_#{database}_kudos_del`;
        DROP TRIGGER IF EXISTS `#{database}`.`pt_osc_#{database}_kudos_ins`;
        DROP TRIGGER IF EXISTS `#{database}`.`pt_osc_#{database}_kudos_upd`;

        DROP TABLE IF EXISTS `#{database}`.`_kudos_old`;
      PTOSC
    else
      add_column :kudos, :pseud_id, :integer
      add_index :kudos, [:pseud_id], name: "index_kudos_on_pseud_id"
      add_index :kudos, [:commentable_id, :commentable_type, :pseud_id], name: "index_kudos_on_commentable_id_and_commentable_type_and_pseud_id"
    end
  end
end
