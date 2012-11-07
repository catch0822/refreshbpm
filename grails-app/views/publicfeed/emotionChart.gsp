<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="refreshbpm.EmotionGroup"%> 
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:javascript src="emotionChart/highstock.js"/>
    <g:javascript src="emotionChart/jquery-1.7.2.min.js"/>
    <g:javascript src="emotionChart/jquery.smooth-scroll.min.js"/>
    <g:javascript src="emotionChart/lightbox.js"/>
    <g:javascript src="emotionChart/jquery.masonry.min.js"/>
    
    <script type="text/javascript" src="${resource(dir: 'path', file: 'control.js')}"/></script>  
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'lightbox.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'screen.css')}" type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.lightbox-0.5.css')}" type="text/css">

	<script type="text/javascript">
	  jQuery.noConflict();
		

jQuery(document).ready(function() {
	
	changeEmotion("${EmotionGroup.get(1).id}")
});

function getRandomFeed(date, emotionGroupNameId){	
	jQuery.ajax({
		  url: '${createLink(action:'getRandomFeed')}',
		  data:{
			  date: date,
			  emotionGroupNameId: emotionGroupNameId
		  }	,
		  success: function(ret) {

			var htmlString=""
			for(var i=0;i<ret[0].amount;i++){
			  if(i==0)
					 htmlString+="<div class=\"item\">"	
				 else if(i==ret[0].amount-1)
					 htmlString+="<div class=\"item\">"
			     else
			    	 htmlString+="<div class=\"item\">"

			    htmlString+="<a href="+ret[0].imagePathList[i]+" rel=\"lightbox[flickr]\" title=\'"+ret[0].titleList[i]+ret[0].contentList[i]+"\'><img src="+ret[0].imageThumbnailPathList[i]+" \"alt=\"flickr\"/></a></div>"	     
			 }
				jQuery('#flickrPic').html(htmlString); 
				
				jQuery('#masonry').masonry({
				    itemSelector : '.item',
				  });
				  
				 jQuery('a').smoothScroll({
			       	 speed: 2000,
			       	 easing: 'easeInOutCubic'
			     });
		  }
											
	
	});	
}

function changeEmotion(emotionGroupNameId){

	var color=""
		
	jQuery('#container').html("<img src=\"../images/emotionGroup/loading.gif\" style=\"display: block; margin-left: auto; margin-right: auto;\"/>")
	
	jQuery.ajax({
		  async:false,
		  url: '${createLink(action:'getEmotionGroupColor')}',
		  data:{
			  emotionGroupNameId: emotionGroupNameId
		  }	,
		  success: function(ret) {
			color=ret
		  }	
	});
		
	jQuery.ajax({
		  url: '${createLink(action:'changeEmptionGroup')}',
		  data:{
			  emotionGroupNameId: emotionGroupNameId
		  },
		  success: function(data) {
			
			var dataList=[];
			for(var i=0;i<data[0].date.length;i++){

				dataList[i]=[data[0].date[i],data[0].amount[i]]
			}
			
			  window.chart = new Highcharts.StockChart({
				    chart: {
			            renderTo: 'container',
			            events:{
						   load:function(){
							   getRandomFeed(null, emotionGroupNameId);
						   }
				        }
			        },
				    navigator: {
			            outlineColor: "#000000",
			            outlineWidth: 2
			        },

				    title: {
				        text: data[0].title
				    },
				    
				    series: [{
				        name: data[0].title,
				        data: dataList,
				        color: color,
				        type: 'spline',
				        tooltip: {
				        	valueDecimals: 2
				        }
				    }],
				    
				    tooltip: {
			            formatter: function() {
			                var s = '<b>'+ Highcharts.dateFormat('%b %e, %Y', this.x) +'</b>';

			                jQuery.each(this.points, function(i, point) {
			                    s += '<br/><span style="color:blue">'+this.series.name+'</span>: <b>'+point.y+'</b><br/>';
			                });
			            
			                return s;
			            },
			            borderColor: 'gray',
			            borderWidth: 3,
			            style: {
			            	fontSize: '12pt',
			            	padding: '5px'
			            }
			        },
					rangeSelector: {
						inputStyle: {
			                color: '#039',
			                fontWeight: 'bold',
			                width:'100px'
			            },
						buttonSpacing:3,
						buttons: [
						{
							type: 'day',
							count: 1,
							text: '1d'
						}, {
							type: 'week',
							count: 1,
							text: '1w'
						}, {
							type: 'month',
							count: 1,
							text: '1m'
						}, {
							type: 'month',
							count: 3,
							text: '3m'
						},{
							type: 'month',
							count: 6,
							text: '0.5y'
						}],
						selected: 3  // '1d' is default selected
					},
					credits: {
				    	text:"RefreshBpm",
				    	href:"${createLink(uri:'/')}"
					},

					plotOptions: {
						series: {
							allowPointSelect: true,
							showCheckbox: true,
							point: {
								events: {
									select: function() { 								
										 getRandomFeed(this.x, emotionGroupNameId);
									}
								}

							}							
						}
					}
					
				});
		   
		  }
		});	
}

</script>

<style>

.item {
  width: 100px;
  height: 100px;
  margin: 5px;
  float: left;
}

</style>
	
  </head>
  <body>
 
        <div id='outer_container' class="outer_container" >
                <a class="menu_button" href="#" title="Toggle"><span></span></a>
                <ul class="menu_option">
                  <li id="emotionballsItemOuter"><g:link controller="publicfeed" action="emotionBalls"><span id="emotionballsItem" title="show emotion balls">emotionballs</span></g:link></li>
                  <li id="emotionchartItemOuter" ><g:link controller="publicfeed" action="emotionChart" class="selected"><span id="emotionchartItem" title="show emotion chart">linechart</span></g:link></li>
                  <li id="emotionmapItemOuter"><g:link controller="publicfeed" action="emotionMap"><span id="emotionmapItem" title="show emotion map">map</span></g:link></li>
                  <g:if test="${session.facebookUser}">
    			  	  <g:if test="${session.mapMode=="personal"}"> 	
    			    		 <li><g:link controller="publicfeed" action="emotionBalls" params="['mode':'global']"><span id="emotionglobalItem" title="global">global</span></g:link></li>		
    			      </g:if>
    			      <g:else>
    			      		 <li><g:link controller="publicfeed" action="emotionBalls" params="['mode':'personal']"><span id="emotionpersonalItem" title="personal">personal</span></g:link></li>
    			      </g:else>
    			 
				  </g:if>
                </ul>
        </div>	
		
		<div id="maincontent">
			<div class="innertube">
				<br/>
        
            	<div id="page" class="container" style="width:1024px">		
                	<div style="width:95%;height:920px;">
	                	<div id="colorBar"> 
	                		<ul>
								<g:each var="i" in="${ (1..<13) }">
	   								<li><a onclick='changeEmotion("${EmotionGroup.get(i)?.id}")'><span>${EmotionGroup.get(i)?.name}</span><img src="${createLinkTo(dir:'images/emotionGroup',file:i+'.png')}"/></a></li>
	 							</g:each>
							</ul>
	                	</div>
                	      
					<div id="container" style="height: 500px"></div> 	      
							<div id="content">
						  		<div class="masonry" id="flickrPic" style="width:1024px; border:0px solid #003; margin:0 auto;">		  	    	
						    	</div>
							</div>						 
             		 </div>
      		   </div>      	
			</div>
		</div> 
    
  	<!--  	<div id="framecontentBottom">
			<div class="innertube">
		 		
			</div>
		</div>-->
  </body>
</html>
