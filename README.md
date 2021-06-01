# Database link
HTTP requests can be issued to [this](https://fake-database-fe-support.herokuapp.com/) link.

# List of available requests 

## Provider (prefix: <strong>/provider</strong>)

<ul>
<li><dl> <strong>GET</strong> requests
    <dt><em>/getAll</em></dt>
    <dd>Returns a list containing all available providers. Requires authentication.</dd>
    <dt><em>/getById?provider_id={provider_id}</em></dt>
    <dd>Returns a provider specified by id. Requires authentication.</dd>
    <dt><em>/getInterval?skip={skip}&count={count}</em></dt>
    <dd>Given a skip and count values, it returns a list of COUNT elements skipping SKIP rows from the database.  Requires authentication.</dd>
    <dt><em>/getCount</em></dt>
    <dd>Returns the number of providers. Requires authentication.</dd>
    <dt><em>/searchCount?query={query}</em></dt>
    <dd>Returns the number of providers that contain the QUERY string. Requires authentication.</dd>
    <dt><em>/search</em></dt>
    <dd>Given a skip and count values, it returns a list of COUNT providers that contain the QUERY string skipping SKIP rows from the database. Requires authentication.</dd>
</dl></li>
</ul>

## User (prefix: <strong>/user</strong>)

<ul>
<li><dl> <strong>GET</strong> requests
    <dt><em>/getAll</em></dt>
    <dd>Returns a list containing all registered users.</dd>
    <dt><em>/get?id={id}</em></dt>
    <dd>Returns the user with the specified ID (if they exist).</dd>
</dl></li>

<li><dl> <strong>POST</strong> requests
    <dt><em>/register</em></dt>
    <dd>Inserts a user specified in JSON format in the request body into the database.</dd>
    <dt><em>/login?username={username_id}&password={password}</em></dt>
    <dd>Logs a user in (by creating a session) and returns a JSON object containing the user id and the token necessary for authorization. Sessions do not currently have a lifetime (they are persistent until the user specifically logs out).</dd>
</dl></li>

<li><dl> <strong>PUT</strong> requests
    <dt><em>/update?id={id}&password={password}&avatar={avatar}&bio={bio}</em></dt>
    <dd>Updates an already existing user's password/avatar/bio. Requires authentication.</dd>
</dl></li>

<li><dl> <strong>DELETE</strong> requests
    <dt><em>/delete?id={id}</em></dt>
    <dd>Deletes the user specified by their ID from the database (if they exist).</dd>
    <dt><em>/logout</em></dt>
    <dd>Logs a user out of the application (deletes the session from the session table). Requires authentication.</dd>
</dl></li>
</ul>

## Posts (prefix: <strong>/post</strong>)

<ul>
<li><dl> <strong>GET</strong> requests
    <dt><em>/getInterval?skip={skip}&count={count}</em></dt>
    <dd>Skips the first <strong>SKIP</strong> posts and returns the next <strong>COUNT</strong> posts. Requires authentication.</dd>
    <dt><em>/getIntervalByProvider?provider_id={provider_id}&skip={skip}&count={count}</em></dt>
    <dd>Skips the first <strong>SKIP</strong> posts and returns the next <strong>COUNT</strong> posts from the provider with <strong>PROVIDER_ID</strong>. Requires authentication.</dd>
</dl></li>
</ul>

## User preferences (prefix: <strong>/preferences</strong>)

<ul>
<li><dl> <strong>GET</strong> requests
    <dt><em>/getAll</em></dt>
    <dd>Returns a list containing all the rows in the table.</dd>
    <dt><em>/isSubscribed?uid={uid}&prov_id={prov_id}</em></dt>
    <dd>Checks if the a user is subscribed to the provider specified by ID. Requires authentication.</dd>
    <dt><em>/getByUserId?uid={uid}&skip={skip}&count={count}</em></dt>
    <dd>Skips the first <strong>SKIP</strong> providers in the subscription list of the specified user and returns the next <strong>COUNT</strong> providers. If not specified, the <strong>SKIP</strong> parameter defaults to 0, and the <strong>COUNT</strong> parameter to 100. Requires authentication.</dd>
</dl></li>

<li><dl> <strong>POST</strong> requests
    <dt><em>/subscribeUserToProviders?uid={uid}</em></dt>
    <dd>Inserts an entry into the table for each provider ID in the request body. Sets the subscription status for all specified IDs to true. Requires authentication.</dd>
</dl></li>

<li><dl> <strong>PUT</strong> requests
    <dt><em>/updateSubscriptionStatus?uid={uid}&prov_id={prov_id}&status={status}</em></dt>
    <dd>Updates the subscription status for the specified user and provider. Requires authentication.</dd>
</dl></li>
</ul>

# Other mentions

## Requires authentication
Any method that requires authentication will require the headers `X-Auth-User` and `X-Auth-Token` in order to work. These are the values received upon user login, and have the following format:
```json
{
  "user_id": <your-id-here>,
  "token": <your-token-here>
}
```
Field `user_id` is of type integer, and field `token` is a string.

## User POST request body format

```json
{
  "username": "gigi",
  "passwordHash" : "plaintextPassword", // will change the confusing key name in the future
  "avatarUrl": "www.example.com",
  "bio": "I am gigi",
  "email": "gigi@yahoo.com"
}
```

## User preferences POST request body format

```json
[1, 2, 3, 4] // IDs of the providers
```
