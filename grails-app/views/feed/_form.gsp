<%@ page import="refreshbpm.Feed" %>



<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="feed.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${feedInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'content', 'error')} ">
	<label for="content">
		<g:message code="feed.content.label" default="Content" />
		
	</label>
	<g:textField name="content" value="${feedInstance?.content}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'socialMedia', 'error')} ">
	<label for="socialMedia">
		<g:message code="feed.socialMedia.label" default="Social Media" />
		
	</label>
	<g:select id="socialMedia" name="socialMedia.id" from="${refreshbpm.SocialMedia.list()}" optionKey="id" value="${feedInstance?.socialMedia?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'imagePath', 'error')} ">
	<label for="imagePath">
		<g:message code="feed.imagePath.label" default="Image Path" />
		
	</label>
	<g:textField name="imagePath" value="${feedInstance?.imagePath}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'userId', 'error')} ">
	<label for="userId">
		<g:message code="feed.userId.label" default="User Id" />
		
	</label>
	<g:textField name="userId" value="${feedInstance?.userId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'job', 'error')} ">
	<label for="job">
		<g:message code="feed.job.label" default="Job" />
		
	</label>
	<g:select id="job" name="job.id" from="${refreshbpm.JobTitle.list()}" optionKey="id" value="${feedInstance?.job?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'country', 'error')} ">
	<label for="country">
		<g:message code="feed.country.label" default="Country" />
		
	</label>
	<g:textField name="country" value="${feedInstance?.country}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'city', 'error')} ">
	<label for="city">
		<g:message code="feed.city.label" default="City" />
		
	</label>
	<g:textField name="city" value="${feedInstance?.city}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'address', 'error')} ">
	<label for="address">
		<g:message code="feed.address.label" default="Address" />
		
	</label>
	<g:textField name="address" value="${feedInstance?.address}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'emotions', 'error')} ">
	<label for="emotions">
		<g:message code="feed.emotions.label" default="Emotions" />
		
	</label>
	<g:select name="emotions" from="${refreshbpm.Emotion.list()}" multiple="multiple" optionKey="id" size="5" value="${feedInstance?.emotions*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'lat', 'error')} required">
	<label for="lat">
		<g:message code="feed.lat.label" default="Lat" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="lat" step="any" required="" value="${feedInstance.lat}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'lng', 'error')} required">
	<label for="lng">
		<g:message code="feed.lng.label" default="Lng" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="lng" step="any" required="" value="${feedInstance.lng}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedInstance, field: 'publishTime', 'error')} required">
	<label for="publishTime">
		<g:message code="feed.publishTime.label" default="Publish Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="publishTime" required="" value="${feedInstance.publishTime}"/>
</div>

