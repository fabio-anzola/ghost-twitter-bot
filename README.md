# ghost-twitter-bot
A Program which enables you to automagically post a notification of every new article from your Ghost (CMS) to Twitter.

## Cronjob
```sh
*/10 * * * * java -jar /some/path/ghost-twitter-poster.jar BASE_URL APIKEY DBHOST:PORT DBNAME? UNAME PASSWD > /some/path/twitter-bot.log
```