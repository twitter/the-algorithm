class DropApprovedFromFeedbacks < ActiveRecord::Migration[5.1]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = Feedback.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=feedbacks \\
          --alter "DROP COLUMN approved" \\
          --no-swap-tables --no-drop-new-table --no-drop-triggers \\
          -uroot --ask-pass --chunk-size=10k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=25 --critical-load Threads_running=400 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Swap and Cleanup Commands:

        RENAME TABLE
          `#{database}`.`feedbacks` TO `#{database}`.`_feedbacks_old`,
          `#{database}`.`_feedbacks_new` TO `#{database}`.`feedbacks`;

        DROP TRIGGER IF EXISTS `#{database}`.`pt_osc_#{database}_feedbacks_del`;
        DROP TRIGGER IF EXISTS `#{database}`.`pt_osc_#{database}_feedbacks_ins`;
        DROP TRIGGER IF EXISTS `#{database}`.`pt_osc_#{database}_feedbacks_upd`;

        DROP TABLE IF EXISTS `#{database}`.`_feedbacks_old`;
      PTOSC
    else
      remove_column :feedbacks, :approved
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = Feedback.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=feedbacks \\
          --alter "ADD COLUMN approved boolean DEFAULT 0 NOT NULL" \\
          --no-swap-tables --no-drop-new-table --no-drop-triggers \\
          -uroot --ask-pass --chunk-size=10k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=25 --critical-load Threads_running=400 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Swap and Cleanup Commands:

        RENAME TABLE
          `#{database}`.`feedbacks` TO `#{database}`.`_feedbacks_old`,
          `#{database}`.`_feedbacks_new` TO `#{database}`.`feedbacks`;

        DROP TRIGGER IF EXISTS `#{database}`.`pt_osc_#{database}_feedbacks_del`;
        DROP TRIGGER IF EXISTS `#{database}`.`pt_osc_#{database}_feedbacks_ins`;
        DROP TRIGGER IF EXISTS `#{database}`.`pt_osc_#{database}_feedbacks_upd`;

        DROP TABLE IF EXISTS `#{database}`.`_feedbacks_old`;
      PTOSC
    else
      add_column :feedbacks, :approved, :boolean, null: false, default: false
    end
  end
end
