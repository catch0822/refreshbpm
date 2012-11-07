
<%@ page import="refreshbpm.Feed" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-original">
		<g:set var="entityName" value="${message(code: 'feed.label', default: 'Feed')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-feed" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-feed" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="title" title="${message(code: 'feed.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="content" title="${message(code: 'feed.content.label', default: 'Content')}" />
					
						<th><g:message code="feed.socialMedia.label" default="Social Media" /></th>
					
						<g:sortableColumn property="imagePath" title="${message(code: 'feed.imagePath.label', default: 'Image Path')}" />
					
						<g:sortableColumn property="userId" title="${message(code: 'feed.userId.label', default: 'User Id')}" />
					
						<th><g:message code="feed.job.label" default="Job" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${feedInstanceList}" status="i" var="feedInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${feedInstance.id}">${fieldValue(bean: feedInstance, field: "title")}</g:link></td>
					
						<td>${fieldValue(bean: feedInstance, field: "content")}</td>
					
						<td>${fieldValue(bean: feedInstance, field: "socialMedia")}</td>
					
						<td>${fieldValue(bean: feedInstance, field: "imagePath")}</td>
					
						<td>${fieldValue(bean: feedInstance, field: "userId")}</td>
					
						<td>${fieldValue(bean: feedInstance, field: "job")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${feedInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
