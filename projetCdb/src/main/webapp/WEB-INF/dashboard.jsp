<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database JSP</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.2/css/all.css"
	integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr"
	crossorigin="anonymous">


</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/projetCdb/"> Application -
				Computer Database </a> <a class="navbar-brand"
				href="/projetCdb/testSpring"> Test Spring </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${nbComputers} computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="/projetCdb/" method="POST"
						class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="/projetCdb/addComputer">Add Computer</a> <a
						class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="/projetCdb/delete" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<!--  Table -->
		<section>
			<div class="container" style="margin-top: 10px;">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<!-- Variable declarations for passing labels as parameters -->
							<!-- Table header for Computer Name -->

							<th class="editMode" style="width: 60px; height: 22px;"><input
								type="checkbox" id="selectall" /> <span
								style="vertical-align: top;"> - <a href="#"
									id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
										class="fa fa-trash-o fa-lg"></i>
								</a>
							</span></th>
							<th>Computer name
								<div class="pull-right">
									<a href="/projetCdb/sortComputers?sortName=true&asc=true&selectedPage=${numPage}"> 
										<i class="fas fa-sort-up align-top "></i>
									</a>
									 <a href="/projetCdb/sortComputers?sortName=true&asc=false">
									 	<i class="fas fa-sort-down align-bottom "></i>
									 </a>
								</div>
							</th>
							<th>Introduced date</th>
							<!-- Table header for Discontinued Date -->
							<th>Discontinued date</th>
							<!-- Table header for Company -->
							<th>Company
								<div class="pull-right">
									<a> <i class="fas fa-sort-up align-top "></i></a> <a><i
										class="fas fa-sort-down align-bottom "></i></a>
								</div>
							</th>

						</tr>
					</thead>

					<!-- Browse attribute computers -->

					<tbody id="results">
						<c:forEach items="${computers}" var="computer">
							<tr>
								<td class="editMode"><input type="checkbox" name="cb"
									class="cb" value="${computer.id}"></td>
								<td><a
									href="/projetCdb/editComputer?idComputer=<c:out value="${computer.id}"/>">${computer.name}</a></td>
								<td>${computer.introduced}</td>
								<td>${computer.discontinued}</td>
								<td>${computer.company}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</section>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a href="#" aria-label="Previous"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<c:forEach var="numPage" begin="1" end="${nbPages}">
					<li><a href="/projetCdb/cdb?selectedPage=${numPage}">${numPage}</a></li>
				</c:forEach>
				<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<button type="button" class="btn btn-default">10</button>
				<button type="button" class="btn btn-default">50</button>
				<button type="button" class="btn btn-default">100</button>
			</div>
	</footer>
	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/dashboard.js"></script>

</body>
</html>