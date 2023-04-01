class AddUniqueIndexToFilterTaggings < ActiveRecord::Migration[5.1]
  def up
    if Rails.env.staging? || Rails.env.production?
      database = FilterTagging.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=filter_taggings \\
          --alter "ADD UNIQUE INDEX index_filter_taggings_on_filter_and_filterable (filter_id, filterable_type, filterable_id),
                   DROP INDEX index_filter_taggings_on_filter_id_and_filterable_type" \\
          --no-drop-old-table --no-check-unique-key-change \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_filter_taggings_old`;
      PTOSC
    else
      add_index :filter_taggings, [:filter_id, :filterable_type, :filterable_id],
                unique: true, name: "index_filter_taggings_on_filter_and_filterable"

      remove_index :filter_taggings, name: "index_filter_taggings_on_filter_id_and_filterable_type"
    end
  end

  def down
    if Rails.env.staging? || Rails.env.production?
      database = FilterTagging.connection.current_database

      puts <<~PTOSC
        Schema Change Command:

        pt-online-schema-change D=#{database},t=filter_taggings \\
          --alter "DROP INDEX index_filter_taggings_on_filter_and_filterable,
                   ADD INDEX index_filter_taggings_on_filter_id_and_filterable_type (filter_id, filterable_type)" \\
          --no-drop-old-table \\
          -uroot --ask-pass --chunk-size=5k --max-flow-ctl 0 --pause-file /tmp/pauseme \\
          --max-load Threads_running=15 --critical-load Threads_running=100 \\
          --set-vars innodb_lock_wait_timeout=2 --alter-foreign-keys-method=auto \\
          --execute

        Table Deletion Command:

        DROP TABLE IF EXISTS `#{database}`.`_filter_taggings_old`;
      PTOSC
    else
      remove_index :filter_taggings, name: "index_filter_taggings_on_filter_and_filterable"

      add_index :filter_taggings, [:filter_id, :filterable_type],
                name: "index_filter_taggings_on_filter_id_and_filterable_type"
    end
  end
end
