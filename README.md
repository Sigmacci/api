<h1>API for ToDo application</h1>
<h3>Supports the following endpoints:</h3>
<h5>GET</h5>
<ul>
  <li>Get all users:</li>
  <code>/api/users</code>
  <li>Get user by id:</li>
  <code>/api/users/{userId}</code>
  <li>Get user's todos by user's id:</li>
  <code>/api/users/{userId}/todos</code>
  <li>Get user's todo by id:</li>
  <code>/api/users/{userId}/todos/{todoId}</code>
  <li>Get all todos:</li>
  <code>/api/todos</code>
</ul>
<h5>POST</h5>
<ul>
  <li>Create new todo for user:</li>
  <code>/api/users/{userId}/todos</code>
  <li>Register user:</li>
  <code>/api/auth/register</code>
  <li>Sign in:</li>
  <code>/api/auth/login</code>
  <li>Sign out:</li>
  <code>/api/auth/logout</code>
</ul>
<h5>PUT</h5>
<ul>
  <li>Edit todo:</li>
  <code>/api/users/{userId}/todos/{toDoId}</code>
</ul>
<h5>DELETE</h5>
<ul>
  <li>Delete todo:</li>
  <code>/api/todos/{id}</code>
</ul>
