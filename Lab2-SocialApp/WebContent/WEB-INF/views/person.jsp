<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	edu.sjsu.cmpe275.lab2.entities.Person person = (edu.sjsu.cmpe275.lab2.entities.Person) request
			.getAttribute("person");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
	<h1>${message}</h1>
	<%
		if (person != null) {
	%>
	<h4>
		<%
			out.print("PersonID: " + person.getPersonId());
		%>
	</h4>
	<h4>
		<%
			out.print("FirstName: " + person.getFirstName());
		%>
	</h4>

	<h4>
		<%
			out.print("LastName: " + person.getLastName());
		%>
	</h4>

	<h4>
		<%
			out.print("Email: " + person.getEmail());
		%>
	</h4>

	<h4>
		<%
			out.print("Description: " + person.getDescription());
		%>
	</h4>
	<h4>
		<%
			if (person.getAddress() != null)
					out.print("Person Address:  "
							+ person.getAddress().getStreet() + " "
							+ person.getAddress().getCity() + " "
							+ person.getAddress().getState() + " "
							+ person.getAddress().getZip());
		%>
	</h4>
	<h4>
		<%
			if (person.getOrganization() != null) {
					out.print("OrganizationId: "
							+ person.getOrganization().getOrganizationId());
		%>
	</h4>

	<h4>
		<%
			out.print("OrganizationName: "
							+ person.getOrganization().getName());
		%>
	</h4>
	<h4>
		<%
			if (person.getOrganization().getAddress() != null)
						out.print("Organization Address: "
								+ person.getOrganization().getAddress()
										.getStreet()
								+ " "
								+ person.getOrganization().getAddress()
										.getCity()
								+ " "
								+ person.getOrganization().getAddress()
										.getState()
								+ " "
								+ person.getOrganization().getAddress()
										.getZip());
				}
		%>
	</h4>

<h4>Friends:</h4>
	<%
		if (person.getFriends().size() > 0) {
	%>
	
	<p>

		<table border="1">
		<tr>

			<th>FriendID
			</td>
			<th>FirstName
			</td>
			<th>LastName
			</td>
			<th>Email
			</td>

	
		</tr>
			<%
				for (int i = 0; i < person.getFriends().size(); i++) {
			%>
	
	<tr>
			<td>
				<%
					out.println(person.getFriends().get(i).getPersonId());
				%>
			</td>
			<td>
				<%
					out.println(person.getFriends().get(i).getFirstName());
				%>
			</td>
			<td>
				<%
					out.println(person.getFriends().get(i).getLastName());
				%>
			</td>
			<td>
				<%
					out.println(person.getFriends().get(i).getEmail());
				%>
			</td>

		</tr>
		<%
			}
		%>
		</p>
		<%
			}
		%>
	</table>
	<%
		}
	%>
</body>
</html>