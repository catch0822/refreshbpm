
<%@ page import="refreshbpm.Feed" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-original">
		<g:set var="entityName" value="${message(code: 'feed.label', default: 'Feed')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-feed" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-feed" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list feed">
			
				<g:if test="${feedInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="feed.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${feedInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${feedInstance?.content}">
				<li class="fieldcontain">
					<span id="content-label" class="property-label"><g:message code="feed.content.label" default="Content" /></span>
					
						<span class="property-value" aria-labelledby="content-label"><g:fieldValue bean="${feedInstance}" field="content"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${feedInstance?.socialMedia}">
				<li class="fieldcontain">
					<span id="socialMedia-label" class="property-label"><g:message code="feed.socialMedia.label" default="Social Media" /></span>
					
						<span class="property-value" aria-labelledby="socialMedia-label"><g:link controller="socialMedia" action="show" id="${feedInstance?.socialMedia?.id}">${feedInstance?.socialMedia?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${feedInstance?.imagePath}">
				<li class="fieldcontain">
					<span id="imagePath-label" class="property-label"><g:message code="feed.imagePath.label" default="Image Path" /></span>
					
						<span class="property-value" aria-labelledby="imagePath-label"><g:fieldValue bean="${feedInstance}" field="imagePath"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${feedInstance?.userId}">
				<li class="fieldcontain">
					<span id="userId-label" class="property-label"><g:message code="feed.userId.label" default="User Id" /></span>
					
						<span class="property-value" aria-labelledby="userId-label"><g:fieldValue bean="${feedInstance}" field="userId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${feedInstance?.job}">
				<li class="fieldcontain">
					<span id="job-label" class="property-label"><g:message code="feed.job.label" default="Job" /></span>
					
						<span class="property-value" aria-labelledby="job-label"><g:link controller="jobTitle" action="show" id="${feedInstance?.job?.id}">${feedInstance?.job?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${feedInstance?.country}">
				<li class="fieldcontain">
					<span id="country-label" class="property-label"><g:message code="feed.country.label" default="Country" /></span>
					
						<span class="property-value" aria-labelledby="country-label"><g:fieldValue bean="${feedInstance}" field="country"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${feedInstance?.city}">
				<li class="fieldcontain">
					<span id="city-label" class="property-label"><g:message code="feed.city.label" default="City" /></span>
					
						<span class="property-value" aria-labelledby="city-label"><g:fieldValue bean="${feedInstance}" field="city"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${feedInstance?.address}">
				<li class="fieldcontain">
					<span id="address-label" class="property-label"><g:message code="feed.address.label" default="Address" /></span>
					
						<span class="property-value" aria-labelledby="address-label"><g:fieldValue bean="${feedInstance}" field="address"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${feedInstance?.emotions}">
				<li class="fieldcontain">
					<span id="emotions-label" class="property-label"><g:message code="feed.emotions.label" default="Emotions" /></span>
					
						<g:each in="${feedInstance.emotions}" var="e">
						<span class="property-value" aria-labelledby="emotions-label"><g:link controller="emotion" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${feedInstance?.lat}">
				<li class="fieldcontain">
					<span id="lat-label" class="property-label"><g:message code="feed.lat.label" default="Lat" /></span>
					
						<span class="property-value" aria-labelledby="lat-label"><g:fieldValue bean="${feedInstance}" field="lat"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${feedInstance?.lng}">
				<li class="fieldcontain">
					<span id="lng-label" class="property-label"><g:message code="feed.lng.label" default="Lng" /></span>
					
						<span class="property-value" aria-labelledby="lng-label"><g:fieldValue bean="${feedInstance}" field="lng"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${feedInstance?.publishTime}">
				<li class="fieldcontain">
					<span id="publishTime-label" class="property-label"><g:message code="feed.publishTime.label" default="Publish Time" /></span>
					
						<span class="property-value" aria-labelledby="publishTime-label"><g:fieldValue bean="${feedInstance}" field="publishTime"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${feedInstance?.id}" />
					<g:link class="edit" action="edit" id="${feedInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
