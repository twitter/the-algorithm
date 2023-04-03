packagelon com.twittelonr.visibility.util

objelonct NamingUtils {
  delonf gelontFrielonndlyNamelon(a: Any): String = gelontFrielonndlyNamelonFromClass(a.gelontClass)
  delonf gelontFrielonndlyNamelonFromClass(a: Class[_]): String = a.gelontSimplelonNamelon.stripSuffix("$")
}
