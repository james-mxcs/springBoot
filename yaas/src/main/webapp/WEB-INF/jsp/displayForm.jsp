<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<body>
	
	<c:forEach var="i" items="${result }">
		<p>Address1: ${i.address1 }</p>
		<p>Address2: ${i.address2 }</p>
		<p>City: ${i.city }</p>
		<p>State: ${i.state }</p>
		<p>Zipcode5: ${i.zipcode5 }</p>
		<p>Zipcode4: ${i.zipcode4 }</p>
	</c:forEach>
</body>

</html>