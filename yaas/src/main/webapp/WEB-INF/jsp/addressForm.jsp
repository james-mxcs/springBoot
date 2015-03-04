<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<body>
	<form action="/submitAddress" method="post">
		<p>USPS user id: <input type="text" name="uspsID" /></p>
		<p>Address 1: <input type="text" name="address1" /></p>
  		<p>Address 2: <input type="text" name="address2" /></p>
  		<p>City: <input type="text" name="city" /></p>
  		<p>State: <input type="text" name="state" /></p>
  		<p>Zipcode: <input type="text" name="zipcode5" /></p>
  		<input type="submit" value="Submit" />
	</form>
</body>

</html>