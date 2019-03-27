<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Please sign in</title>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous" />
</head>
<body>
	<div class="container">
		<form class="form-signin" method="post" action="/projetCdb/user/create">
			<h2 class="form-signin-heading">Créer un utilisateur</h2>
			<p>
				<label for="username" class="sr-only">utilisateur</label> <input type="text" id="username" name="username" class="form-control"
					placeholder="nom d'utilisateur" required autofocus>
			</p>
			<p>
				<label for="password" class="sr-only">mot de pass</label> <input type="password" id="password" name="password" class="form-control"
					placeholder="mot de pass" required>
			</p>
			<p>
				<label for="roleId">Role</label> <select
									class="form-control" id="roleId" name="idRole">
									<c:forEach items="${roles}" var="role">
										<option value="${role.getId()}">${role.role}</option>
									</c:forEach>
								</select>
			</p>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Créer</button>
		</form>
		
	</div>
</body>
</html>