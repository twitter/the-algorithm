class AddUniqueIndicesToKudosAndChangeIdType < ActiveRecord::Migration[5.1]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = Kudo.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=kudos \\
          --alter "ADD UNIQUE INDEX index_kudos_on_commentable_and_user (commentable_id, commentable_type, user_id),
                   ADD UNIQUE INDEX index_kudos_on_commentable_and_ip_address (commentable_id, commentable_type, ip_address),
                   CHANGE COLUMN id id bigint NOT NULL AUTO_INCREMENT" \\
          --no-swap-tables --no-drop-new-table --no-drop-triggers --no-check-unique-key-change \\
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
      add_index :kudos, [:commentable_id, :commentable_type, :user_id],
                unique: true, name: "index_kudos_on_commentable_and_user"
      add_index :kudos, [:commentable_id, :commentable_type, :ip_address],
                unique: true, name: "index_kudos_on_commentable_and_ip_address"

      change_column :kudos, :id, "bigint AUTO_INCREMENT"
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = Kudo.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=kudos \\
          --alter "DROP INDEX index_kudos_on_commentable_and_user,
                   DROP INDEX index_kudos_on_commentable_and_ip_address,
                   CHANGE COLUMN id id int NOT NULL AUTO_INCREMENT" \\
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
      remove_index :kudos, name: "index_kudos_on_commentable_and_user"
      remove_index :kudos, name: "index_kudos_on_commentable_and_ip_address"

      change_column :kudos, :id, "int AUTO_INCREMENT"
    end
  end
end
