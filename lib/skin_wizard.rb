module SkinWizard
  def header_styles(color)
    if color.present?
      "
        #header .primary,
        #footer,
        .autocomplete .dropdown ul li:hover,
        .autocomplete .dropdown li.selected,
        a.tag:hover,
        .listbox .heading a.tag:visited:hover,
        .splash .favorite li:nth-of-type(odd) a:hover,
        .splash .favorite li:nth-of-type(odd) a:focus,
        #tos_prompt .heading { 
          background-image: none;
          background-color: #{color};
        }

        #header .heading a,
        #header .user a:hover,
        #header .user a:focus,
        #dashboard a:hover,
        .actions a:hover,
        .actions input:hover,
        .actions a:focus,
        .actions input:focus,
        label.action:hover,
        .action:hover,
        .action:focus,
        a.cloud1,
        a.cloud2,
        a.cloud3,
        a.cloud4,
        a.cloud5,
        a.cloud6,
        a.cloud7,
        a.cloud8,
        a.work,
        .blurb h4 a:link,
        .splash .module h3,
        .splash .browse li a:before {
          color: #{color};
        }

        #dashboard,
        #dashboard.own {
          border-color: #{color};
        }

        .actions .reindex a {
          border-bottom-color: #{color};
        }
      "
    else
      ""
    end
  end

  def font_size_styles(percentage)
    if percentage.present?
      "
        body {
          font-size: #{percentage}%;
        }
      "
    else
      ""
    end
  end

  def font_styles(names)
    if names.present?
      "
        body,
        .toggled form,
        .dynamic form,
        .secondary,
        .dropdown,
        blockquote,
        pre,
        input,
        textarea,
        button,
        .heading .actions,
        .heading .action,
        .heading span.actions,
        span.unread,
        .replied,
        span.claimed,
        .actions span.defaulted {
          font-family: #{names};
        }
      "
    else
      ""
    end
  end

  def background_color_styles(color)
    if color.present?
      "
        body,
        .toggled form,
        .dynamic form,
        .secondary,
        .dropdown,
        th,
        tr:hover,
        col.name,
        div.dynamic,
        fieldset fieldset,
        fieldset dl dl,
        form blockquote.userstuff,
        form.verbose legend,
        .verbose form legend,
        #modal,
        .own,
        .draft,
        .draft .wrapper,
        .unread,
        .child,
        .unwrangled,
        .unreviewed,
        .thread .even,
        .listbox .index,
        .nomination dt,
        #tos_prompt {
          background: #{color};
        }

        @media only screen and (max-width: 42em) {
          #outer {
            background: #{color}
          }
        }

        a.tag:hover,
        .listbox .heading a.tag:visited:hover {
          color: #{color};
        }

        tbody tr,
        thead td,
        #footer,
        #modal {
          border-color: #{color};
        }

        .toggled form,
        .dynamic form,
        .secondary,
        .wrapper {
          box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.5)
        }

        .listbox,
        fieldset fieldset.listbox {
          box-shadow: 0 0 0 1px #{color};
        }
        
        .listbox .index {
          box-shadow: inset 1px 1px 3px rgba(0, 0, 0, 0.5);
        }
      "
    else
      ""
    end
  end

  def foreground_color_styles(color)
    if color.present?
      "
        body,
        .toggled form,
        .dynamic form,
        .secondary,
        .dropdown,
        #header .search,
        form dd.required,
        .post .required .warnings,
        dd.required,
        .required .autocomplete,
        span.series .divider,
        .filters .expander,
        .userstuff h2 {
          color: #{color};
        }
        
        /* these  colors should be separate, but for now... */
        a,
        a:link,
        a:visited,
        a:hover,
        #header a,
        #header a:visited,
        #header .primary .open a,
        #header .primary .dropdown:hover a,
        #header .primary .dropdown a:focus,
        #header .primary .menu a,
        #dashboard a,
        #dashboard span,
        a.tag,
        .listbox > .heading,
        .listbox .heading a:visited,
        .filters dt a:hover {
          color: #{color};
        }

        form dt,
        .filters .group dt.bookmarker,
        .faq .categories h3,
        .splash .module h3,
        .userstuff h3 {
          border-color: #{color};
        }

        /* some things with unchanging background colors (e.g. caution notices) need the default text color */
        .qtip-content,
        .notice:not(.required),
        .comment_notice,
        .kudos_notice,
        ul.notes,
        .caution,
        .notice a {
          color: #2a2a2a;
        }

        .current,
        a.current {
          color: #111;
        }
      "
    else
      ""
    end
  end

  def paragraph_margin_styles(ems)
    if ems.present?
      "
        .userstuff p {
          margin: #{ems}em auto;
        }
      "
    else
      ""
    end
  end

  def accent_color_styles(color)
    if color.present?
      "
        table,
        thead td,
        #header .actions a:hover,
        #header .actions a:focus,
        #header .dropdown:hover a,
        #header .open a,
        #header .menu,
        #small_login,
        fieldset,
        form dl,
        fieldset dl dl,
        fieldset fieldset fieldset,
        fieldset fieldset dl dl,
        .ui-sortable li,
        .ui-sortable li:hover,
        dd.hideme,
        form blockquote.userstuff,
        dl.index dd,
        .statistics .index li:nth-of-type(even),
        .listbox,
        fieldset fieldset.listbox,
        .item dl.visibility,
        .reading h4.viewed,
        .comment h4.byline,
        .splash .favorite li:nth-of-type(odd) a,
        .splash .module div.account,
        .search [role=\"tooltip\"] {
          background: #{color};
          border-color: #{color};
        }

        #dashboard a:hover,
        #dashboard .current,
        li.relationships a {
          background: #{color};
        }

        li.blurb,
        fieldset,
        form dl,
        thead,
        tfoot,
        tfoot td,
        th,
        tr:hover,
        col.name,
        #dashboard ul,
        .toggled form,
        .dynamic form,
        form.verbose legend,
        .verbose form legend,
        .secondary,
        dl.meta,
        .bookmark .user,
        div.comment,
        li.comment,
        .comment div.icon,
        .splash .news li,
        .userstuff blockquote {
          border-color: #{color};
        }

        fieldset,
        form dl,
        fieldset dl dl,
        fieldset fieldset fieldset,
        fieldset fieldset dl dl,
        form blockquote.userstuff {
          box-shadow: inset 1px 0 5px rgba(0, 0, 0, 0.5);
        }

        fieldset dl,
        fieldset.actions,
        fieldset dl fieldset dl {
          box-shadow: none;
        }

        form.verbose legend,
        .verbose form legend,
        .ui-sortable li:hover {
          box-shadow: 1px 2px 3px rgba(0, 0, 0, 0.5);
        }

        @media only screen and (max-width: 62em) {
          #dashboard .secondary {
            background: #{color};
            box-shadow: none;
          }
        }

        @media only screen and (max-width: 42em) {
          .javascript {
            background: #{color};
          }
        }
      "
    else
      ""
    end
  end

  def work_margin_styles(percentage)
    if percentage.present?
      "
        #workskin {
          margin: auto #{percentage}%;
          max-width: 100%;
        }
      "
    else
      ""
    end
  end
end
