<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	edu.sjsu.cmpe275.lab2.entities.Organization org = (edu.sjsu.cmpe275.lab2.entities.Organization) request
			.getAttribute("organization");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>


	<%
		if (org != null) {
	%>

	<h4>
		<%
			out.print("OrganizationID: " +org.getOrganizationId());
		%>
	</h4>
	<h4>
		<%
			out.print("Name: " + org.getName());
		%>
	</h4>

	<h4>
		<%
			out.print("Description: " + org.getDescription());
		%>
	</h4>


	<h4>
		<%
			if (org.getAddress() != null)
					out.print("Address:  "
							+ org.getAddress().getStreet() + " "
							+ org.getAddress().getCity() + " "
							+ org.getAddress().getState() + " "
							+ org.getAddress().getZip());
		%>
	</h4>
	<%
		}
	%>
</body>
</html>