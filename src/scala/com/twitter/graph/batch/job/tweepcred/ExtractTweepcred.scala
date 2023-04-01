package com.twitter.graph.batch.job.tweepcred

// This is a stupid fix, but I don't have time to do a cleaner implementation
import com.twitter.pluck.source.combined_user_source.MostRecentCombinedUserSnapshotSource
import com.twitter.scalding._

/**
 * Calculate tweepcred from the given pagerank file. If post_adjust is true,
 * reduce pagerank for users with low followers compared to number of
 * followings based on existing reputation code.
 * Options:
 * --input_pagerank: given pagerank
 * --user_mass: user mass tsv file, generated by twadoop user_mass job
 * --output_pagerank: where to put pagerank file
 * --output_tweepcred: where to put tweepcred file
 * optional arguments:
 * --post_adjust: whether to do post adjust, default true
 *
 */
class ExtractTweepcred(args: Args) extends Job(args) {
  val POST_ADJUST = args.getOrElse("post_adjust", "true").toBoolean

  val inputPagerank = getInputPagerank(args("input_pagerank"))
    .map(() -> ('num_followers, 'num_followings)) { (u: Unit) =>
      (0, 0)
    }

  val userInfo = TypedPipe
    .from(MostRecentCombinedUserSnapshotSource)
    .flatMap { combinedUser =>
      val user = Option(combinedUser.user)
      val userId = user.map(_.id).getOrElse(0L)
      val userExtended = Option(combinedUser.user_extended)
      val numFollowers = userExtended.flatMap(u => Option(u.followers)).map(_.toInt).getOrElse(0)
      val numFollowings = userExtended.flatMap(u => Option(u.followings)).map(_.toInt).getOrElse(0)

      if (userId == 0L || user.map(_.safety).exists(_.deactivated)) {
        None
      } else {
        Some((userId, 0.0, numFollowers, numFollowings))
      }
    }
    .toPipe[(Long, Double, Int, Int)]('src_id, 'mass_input, 'num_followers, 'num_followings)

  val pagerankWithSuspended = (inputPagerank ++ userInfo)
    .groupBy('src_id) {
      _.max('mass_input)
        .max('num_followers)
        .max('num_followings)
    }

  pagerankWithSuspended
    .discard('num_followers, 'num_followings)
    .write(Tsv(args("output_pagerank")))

  val adjustedPagerank =
    if (POST_ADJUST) {
      pagerankWithSuspended
        .map(('mass_input, 'num_followers, 'num_followings) -> 'mass_input) {
          input: (Double, Int, Int) =>
            Reputation.adjustReputationsPostCalculation(input._1, input._2, input._3)
        }
        .normalize('mass_input)
    } else {
      pagerankWithSuspended
        .discard('num_followers, 'num_followings)
    }

  val tweepcred = adjustedPagerank
    .map('mass_input -> 'mass_input) { input: Double =>
      Reputation.scaledReputation(input)
    }

  tweepcred.write(Tsv(args("output_tweepcred")))
  tweepcred.write(Tsv(args("current_tweepcred")))
  tweepcred.write(Tsv(args("today_tweepcred")))

  def getInputPagerank(fileName: String) = {
    Tsv(fileName).read
      .mapTo((0, 1) -> ('src_id, 'mass_input)) { input: (Long, Double) =>
        input
      }
  }
}
