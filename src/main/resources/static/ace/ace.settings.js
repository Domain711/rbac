/**
 <b>Settings box</b>. It's good for demo only. You don't need this.
*/
(function($ , undefined) {
	ace.settings.navbar_fixed(null,false);   //导航条固定
	ace.settings.sidebar_fixed(null, false);  //侧栏
	ace.settings.breadcrumbs_fixed(null, false);  //面包屑

 $('#ace-settings-btn').on(ace.click_event, function(e){
	e.preventDefault();

	$(this).toggleClass('open');
	$('#ace-settings-box').toggleClass('open');
 })

//导航条
 $('#ace-settings-navbar').on('click', function(){
	ace.settings.navbar_fixed(null, this.checked);//@ ace-extra.js
	//$(window).triggerHandler('resize.navbar');
	//force redraw?
	//if(ace.vars['webkit']) ace.helper.redraw(document.body);
 }).each(function(){this.checked = ace.settings.is('navbar', 'fixed');})  /*each是页面初步载入时给复选框赋值*/

//侧栏
 $('#ace-settings-sidebar').on('click', function(){
	ace.settings.sidebar_fixed(null, this.checked);//@ ace-extra.js

	//if(ace.vars['webkit']) ace.helper.redraw(document.body);
 }).each(function(){this.checked = ace.settings.is('sidebar', 'fixed')})

//面包屑
 $('#ace-settings-breadcrumbs').on('click', function(){
	ace.settings.breadcrumbs_fixed(null, this.checked);//@ ace-extra.js

	//if(ace.vars['webkit']) ace.helper.redraw(document.body);
 }).each(function(){this.checked = ace.settings.is('breadcrumbs', 'fixed')})

//切换到窄屏
 $('#ace-settings-add-container').on('click', function(){
	ace.settings.main_container_fixed(null, this.checked);//@ ace-extra.js
	//if(ace.vars['webkit']) ace.helper.redraw(document.body);
 }).each(function(){this.checked = ace.settings.is('main-container', 'fixed')})

/*简洁菜单*/
 $('#ace-settings-compact').on('click', function(){
	if(this.checked) {
		$('#sidebar').addClass('compact');
		var hover = $('#ace-settings-hover');
		if( hover.length > 0 ) {
			hover.removeAttr('checked').trigger('click');
		}
	}
	else {
		$('#sidebar').removeClass('compact');
		$('#sidebar[data-sidebar-scroll=true]').ace_sidebar_scroll('reset')
	}
	
	if(ace.vars['old_ie']) ace.helper.redraw($('#sidebar')[0], true);
 }).removeAttr('checked').trigger('click');  //启动简洁菜单
// $('#ace-settings-compact').removeAttr('checked').trigger('click');  //简洁菜单,要放在 $('#ace-settings-compact').on('click', function()...事件绑定后面。

//交互菜单
 $('#ace-settings-highlight').on('click', function(){
	if(this.checked) $('#sidebar .nav-list > li').addClass('highlight');
	else $('#sidebar .nav-list > li').removeClass('highlight');
	
	if(ace.vars['old_ie']) ace.helper.redraw($('#sidebar')[0]);
 }).removeAttr('checked');  //启用交互菜单
//$('#ace-settings-highlight').removeAttr('checked').trigger('click');  //交互菜单

//二级菜单浮动显示：若启动简洁菜单，则二级菜单同时被设置为浮动显示。但仍应再触发一次，否则可能会有BUG，导致二级菜单不会浮动显示
 $('#ace-settings-hover').on('click', function(){
	if($('#sidebar').hasClass('h-sidebar')) return;
	if(this.checked) {
		$('#sidebar li').addClass('hover')
		.filter('.open').removeClass('open').find('> .submenu').css('display', 'none');
		//and remove .open items
	}
	else {
		$('#sidebar li.hover').removeClass('hover');

		var compact = $('#ace-settings-compact');
		if( compact.length > 0 && compact.get(0).checked ) {
			compact.trigger('click');
		}
	}
	
	$('.sidebar[data-sidebar-hover=true]').ace_sidebar_hover('reset')
	$('.sidebar[data-sidebar-scroll=true]').ace_sidebar_scroll('reset')
	
	if(ace.vars['old_ie']) ace.helper.redraw($('#sidebar')[0]);
 }).removeAttr('checked').trigger('click');

	//刚加载页面时，如果侧面菜单是收缩的，则要展开侧面菜单
	if($('#sidebar-collapse > i.ace-icon').hasClass('fa-angle-double-right')) {
		$('#sidebar-collapse').trigger('click');
	}
})(jQuery);