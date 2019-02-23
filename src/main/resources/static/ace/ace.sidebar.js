/**
 <b>Sidebar functions</b>. Collapsing/expanding, toggling mobile view menu and other sidebar functions.
 */

$(".nav-mo .nav-lis").hover(
	function(){
		$(".nav-lis ul").css({display:"none"});  //清除其它打开的面板
		$(this).addClass("hover");
		$("ul",this).css({display:"block"});  //恢复本条的display才能显示。
	},function(){
		$(this).removeClass("hover");
		$(".nav-lis ul").css({display:"none"});  //清除所有的面板。让原有UI的点击document才会关闭变成鼠标移开就关闭。
	}
);

//设置iframe高度。
$parentHeight =$(window).height()-$('#navbar').height()-$('#breadcrumbs').height()-15;
$("iframe#main_iframe").height($parentHeight);


$('ul.nav-list li,ul.ace-nav li').click(function(){
	var href = $(this).find('a').attr('href');
	if(href!='#' && href.indexOf('javascript:')==-1) {
		$("iframe#main_iframe").attr('src',href);
	}
	if($(this).parent('ul').is('ul.dropdown-menu')) return false; //右上角的菜单不需要加active类。

	/** =========================================================================================
	 *  样式调整： 只对无下拉子菜单的菜单起作用。带有下拉菜单的，在ace.sidebar.js中控制。否则会出错。
	 ** ========================================================================================= */

	//1、若无子菜单，则加上active类。
	var hasSubMenu = $(this).children('ul.submenu');
	if(hasSubMenu.length==0) {
		$('ul.nav-list li').removeClass('active');
		$(this).addClass('active');
	}
	//2、若属于下拉菜单中的子菜单，需要给上级加active类和open类，重要的是open类。
	var parent = $(this).parents('ul.submenu');
	if(parent.length && hasSubMenu.length==0){
		$(this).parents('ul.submenu').parent('li').addClass('active open'); //open 否则选中的样式会跑到最顶层。
		//$sub.parents().removeClass('nav-hide').addClass('nav-show').css('display','block');
	}
	$('ul.nav-list').removeClass('nav-show');  //去掉顶层
	var openLi = $(this).siblings('li.open');
	var openUl = $(this).siblings('ul.submenu');
	//关闭同级的已展开的菜单。
	if(openLi.length>0){
		openLi.removeClass('open').find('> ul').removeClass('nav-show').addClass('nav-hide').css('display','none');
		var subOpenElements1 = openLi.find('.open');
		subOpenElements1.removeClass('open');
		var subOpenElements2 = openLi.find('.nav-show');
		subOpenElements2.removeClass('nav-show').addClass('nav-hide').css('display','none');
	}
	if(openUl.length>0){
		openUl.removeClass('nav-show').addClass('nav-hide').css('display','none');
	}

	//在简洁模式的菜单下，会有BUG，需要移除不是active的Li的open类
	$('ul.nav-list li.open').each(function(){
		if(!$(this).hasClass('active')) {$(this).removeClass('open'); }
	})

	/** =========================================================================================
	 *  阻止链接跳转 ：不要删除，否则链接不会在iframe中打开
	 ** ========================================================================================= */
	if(href!='#') return false;
});


(function($ , undefined) {
	var sidebar_count = 0;

	function Sidebar(sidebar, options) {
		var self = this;
		this.$sidebar = $(sidebar);
		this.$sidebar.attr('data-sidebar', 'true');
		if( !this.$sidebar.attr('id') ) this.$sidebar.attr( 'id' , 'id-sidebar-'+(++sidebar_count) )

		var duration = options.duration || ace.helper.intAttr(sidebar, 'data-submenu-duration') ||300;//transition duration


		//some vars
		this.minimized = false;//will be initiated later
		this.collapsible = false;//...
		this.horizontal = false;//...
		this.mobile_view = false;//


		this.vars = function() {
			return {'minimized': this.minimized, 'collapsible': this.collapsible, 'horizontal': this.horizontal, 'mobile_view': this.mobile_view}
		}
		this.get = function(name) {
			if(this.hasOwnProperty(name)) return this[name];
		}
		this.set = function(name, value) {
			if(this.hasOwnProperty(name)) this[name] = value;
		}


		this.ref = function() {
			//return a reference to self
			return this;
		}

		var toggleIcon = function(minimized) {
			var icon = $(this).find(ace.vars['.icon']), icon1, icon2;
			if(icon.length > 0) {
				icon1 = icon.attr('data-icon1');//the icon for expanded state
				icon2 = icon.attr('data-icon2');//the icon for collapsed state

				if(minimized !== undefined) {
					if(minimized) icon.removeClass(icon1).addClass(icon2);
					else icon.removeClass(icon2).addClass(icon1);
				}
				else {
					icon.toggleClass(icon1).toggleClass(icon2);
				}
			}
		}

		var findToggleBtn = function() {
			var toggle_btn = self.$sidebar.find('.sidebar-collapse');
			if(toggle_btn.length == 0) toggle_btn = $('.sidebar-collapse[data-target="#'+(self.$sidebar.attr('id')||'')+'"]');
			if(toggle_btn.length != 0) toggle_btn = toggle_btn[0];
			else toggle_btn = null;

			return toggle_btn;
		}

		//展开或收缩菜单会执行本函数
		this.toggleMenu = function(toggle_btn, save) {
			if(this.collapsible) return;

			//var minimized = this.$sidebar.hasClass('menu-min');
			this.minimized = !this.minimized;

			try {
				//toggle_btn can also be a param to indicate saving to cookie or not?! if toggle_btn === false, it won't be saved
				ace.settings.sidebar_collapsed(sidebar, this.minimized, !(toggle_btn === false || save === false));//@ ace-extra.js
			} catch(e) {  //这里不会执行
				if(this.minimized)
					this.$sidebar.addClass('menu-min');   //收缩
				else this.$sidebar.removeClass('menu-min');  //展开
			}

			if( !toggle_btn ) {
				toggle_btn = findToggleBtn();
			}
			if(toggle_btn) {
				toggleIcon.call(toggle_btn, this.minimized);
			}

			//force redraw for ie8
			if(ace.vars['old_ie']) ace.helper.redraw(sidebar);
		}
		this.collapse = function(toggle_btn, save) {
			if(this.collapsible) return;
			this.minimized = false;

			this.toggleMenu(toggle_btn, save);
		}
		this.expand = function(toggle_btn, save) {
			if(this.collapsible) return;
			this.minimized = true;

			this.toggleMenu(toggle_btn, save);
		}



		//collapse/expand in 2nd mobile style
		this.toggleResponsive = function(toggle_btn) {
			if(!this.mobile_view || this.mobile_style != 3) return;

			if( this.$sidebar.hasClass('menu-min') ) {
				//remove menu-min because it interferes with responsive-max
				this.$sidebar.removeClass('menu-min');
				var btn = findToggleBtn();
				if(btn) toggleIcon.call(btn);
			}


			this.minimized = !this.$sidebar.hasClass('responsive-min');
			this.$sidebar.toggleClass('responsive-min responsive-max');


			if( !toggle_btn ) {
				toggle_btn = this.$sidebar.find('.sidebar-expand');
				if(toggle_btn.length == 0) toggle_btn = $('.sidebar-expand[data-target="#'+(this.$sidebar.attr('id')||'')+'"]');
				if(toggle_btn.length != 0) toggle_btn = toggle_btn[0];
				else toggle_btn = null;
			}

			if(toggle_btn) {
				var icon = $(toggle_btn).find(ace.vars['.icon']), icon1, icon2;
				if(icon.length > 0) {
					icon1 = icon.attr('data-icon1');//the icon for expanded state
					icon2 = icon.attr('data-icon2');//the icon for collapsed state

					icon.toggleClass(icon1).toggleClass(icon2);
				}
			}

			$(document).triggerHandler('settings.ace', ['sidebar_collapsed' , this.minimized]);
		}

		//some helper functions
		this.is_collapsible = function() {
			var toggle
			return (this.$sidebar.hasClass('navbar-collapse'))
				&& ((toggle = $('.navbar-toggle[data-target="#'+(this.$sidebar.attr('id')||'')+'"]').get(0)) != null)
				&&  toggle.scrollHeight > 0
			//sidebar is collapsible and collapse button is visible?
		}
		this.is_mobile_view = function() {
			var toggle
			return ((toggle = $('.menu-toggler[data-target="#'+(this.$sidebar.attr('id')||'')+'"]').get(0)) != null)
				&&  toggle.scrollHeight > 0
		}


		//带有下拉菜单控制：对无下拉的无效。
		this.$sidebar.on(ace.click_event+'.ace.submenu', '.nav-list', function (ev) {
			var nav_list = this;

			//check to see if we have clicked on an element which is inside a .dropdown-toggle element?!
			//if so, it means we should toggle a submenu
			var link_element = $(ev.target).closest('a');
			if(!link_element || link_element.length == 0) return;//return if not clicked inside a link element

			var minimized  = self.minimized && !self.collapsible;
			//if .sidebar is .navbar-collapse and in small device mode, then let minimized be uneffective

			if( !link_element.hasClass('dropdown-toggle') ) {//it doesn't have a submenu return
				//just one thing before we return
				//if sidebar is collapsed(minimized) and we click on a first level menu item
				//and the click is on the icon, not on the menu text then let's cancel event and cancel navigation
				//Good for touch devices, that when the icon is tapped to see the menu text, navigation is cancelled
				//navigation is only done when menu text is tapped

				if( ace.click_event == 'tap'
					&&
					minimized
					&&
					link_element.get(0).parentNode.parentNode == nav_list )//only level-1 links
				{
					var text = link_element.find('.menu-text').get(0);
					if( text != null && ev.target != text && !$.contains(text , ev.target) ) {//not clicking on the text or its children
						ev.preventDefault();
						return false;
					}
				}


				//ios safari only has a bit of a problem not navigating to link address when scrolling down
				//specify data-link attribute to ignore this
				if(ace.vars['ios_safari'] && link_element.attr('data-link') !== 'false') {
					//only ios safari has a bit of a problem not navigating to link address when scrolling down
					//please see issues section in documentation
					document.location = link_element.attr('href');
					ev.preventDefault();
					return false;
				}

				return;
			}

			ev.preventDefault();




			var sub = link_element.siblings('.submenu').get(0);
			if(!sub) return false;
			var $sub = $(sub);

			var height_change = 0;//the amount of height change in .nav-list

			var parent_ul = sub.parentNode.parentNode;
			if
			(
				( minimized && parent_ul == nav_list )
				||
				( ( $sub.parent().hasClass('hover') && $sub.css('position') == 'absolute' ) && !self.collapsible )
			)
			{
				return false;
			}


			var sub_hidden = (sub.scrollHeight == 0)

			//if not open and visible, let's open it and make it visible
			if( sub_hidden ) {//being shown now
				$(parent_ul).find('> .open > .submenu').each(function() {
					//close all other open submenus except for the active one
					if(this != sub && !$(this.parentNode).hasClass('active')) {
						height_change -= this.scrollHeight;
						self.hide(this, duration, false);
					}
				})
			}

			if( sub_hidden ) {//being shown now
				self.show(sub, duration);
				//if a submenu is being shown and another one previously started to hide, then we may need to update/hide scrollbars
				//but if no previous submenu is being hidden, then no need to check if we need to hide the scrollbars in advance
				if(height_change != 0) height_change += sub.scrollHeight;//we need new updated 'scrollHeight' here
			} else {
				self.hide(sub, duration);
				height_change -= sub.scrollHeight;
				//== -1 means submenu is being hidden
			}

			//hide scrollbars if content is going to be small enough that scrollbars is not needed anymore
			//do this almost before submenu hiding begins
			//but when minimized submenu's toggle should have no effect
			if (height_change != 0) {
				if(self.$sidebar.attr('data-sidebar-scroll') == 'true' && !self.minimized)
					self.$sidebar.ace_sidebar_scroll('prehide', height_change)
			}

			return false;
		})

		var submenu_working = false;
		this.show = function(sub, $duration, wait) {
			if(wait !== false) {
				if(submenu_working) return false;
				submenu_working = true;
			}

			$duration = $duration || duration;

			var $sub = $(sub);
			var event;
			$sub.trigger(event = $.Event('show.ace.submenu'))
			if (event.isDefaultPrevented()) return false;

			//折叠菜单打开
			$('ul.nav-list li').removeClass('active').removeClass('open');  //清除所有active
			$sub.parent().addClass('active');
			$('ul.nav-show').removeClass('nav-show').addClass('nav-hide').css('display','none');  //关闭其它的，
			$sub.parents('ul').removeClass('nav-hide').addClass('nav-show').css('display','block'); //保持其上层菜单是打开的。

			$sub.css({
				height: 0,
				overflow: 'hidden',
				display: 'block'
			})
			.removeClass('nav-hide').addClass('nav-show')    //only for window < @grid-float-breakpoint and .navbar-collapse.menu-min
			.parent().addClass('open');

			sub.scrollTop = 0;//this is for submenu_hover when sidebar is minimized and a submenu is scrollTop'ed using scrollbars ...

			if( $duration > 0 ) {
				$sub.css({height: sub.scrollHeight,
					'transition-property': 'height',
					'transition-duration': ($duration/1000)+'s'})
			}

			var complete = function(ev, trigger) {
				ev && ev.stopPropagation();
				$sub
				.css({'transition-property': '', 'transition-duration': '', overflow:'', height: ''})
				//if(ace.vars['webkit']) ace.helper.redraw(sub);//little Chrome issue, force redraw ;)

				if(trigger !== false) $sub.trigger($.Event('shown.ace.submenu'))

				if(wait !== false) submenu_working = false;
			}

			if( $duration > 0 && !!$.support.transition.end ) {
				$sub.one($.support.transition.end, complete);
			}
			else complete();

			//there is sometimes a glitch, so maybe retry
			if(ace.vars['android']) {
				setTimeout(function() {
					complete(null, false);
					ace.helper.redraw(sub);
				}, $duration + 20);
			}

			return true;
		}


		this.hide = function(sub, $duration, wait) {
			if(wait !== false) {
				if(submenu_working) return false;
				submenu_working = true;
			}

			$duration = $duration || duration;

			var $sub = $(sub);
			var event;
			$sub.trigger(event = $.Event('hide.ace.submenu'))
			if (event.isDefaultPrevented()) return false;

			$sub.css({
				height: sub.scrollHeight,
				overflow: 'hidden',
				display: 'block'
			})
			.parent().removeClass('open').removeClass('active');  //修改1

			sub.offsetHeight;
			//forces the "sub" to re-consider the new 'height' before transition

			if( $duration > 0 ) {
				$sub.css({'height': 0,
					'transition-property': 'height',
					'transition-duration': ($duration/1000)+'s'});
			}


			var complete = function(ev, trigger) {
				ev && ev.stopPropagation();
				$sub
				.css({display: 'none', overflow:'', height: '', 'transition-property': '', 'transition-duration': ''})
				.removeClass('nav-show').addClass('nav-hide')//only for window < @grid-float-breakpoint and .navbar-collapse.menu-min

				if(trigger !== false) $sub.trigger($.Event('hidden.ace.submenu'))

				if(wait !== false) submenu_working = false;
			}

			if( $duration > 0 && !!$.support.transition.end ) {
				$sub.one($.support.transition.end, complete);
			}
			else complete();


			//there is sometimes a glitch, so maybe retry
			if(ace.vars['android']) {
				setTimeout(function() {
					complete(null, false);
					ace.helper.redraw(sub);
				}, $duration + 20);
			}

			return true;
		}

		this.toggle = function(sub, $duration) {
			$duration = $duration || duration;

			if( sub.scrollHeight == 0 ) {//if an element is hidden scrollHeight becomes 0
				if( this.show(sub, $duration) ) return 1;
			} else {
				if( this.hide(sub, $duration) ) return -1;
			}
			return 0;
		}


		//sidebar vars
		var minimized_menu_class  = 'menu-min';
		var responsive_min_class  = 'responsive-min';
		var horizontal_menu_class = 'h-sidebar';

		var sidebar_mobile_style = function() {
			//differnet mobile menu styles
			this.mobile_style = 1;//default responsive mode with toggle button inside navbar
			if(this.$sidebar.hasClass('responsive') && !$('.menu-toggler[data-target="#'+this.$sidebar.attr('id')+'"]').hasClass('navbar-toggle')) this.mobile_style = 2;//toggle button behind sidebar
			else if(this.$sidebar.hasClass(responsive_min_class)) this.mobile_style = 3;//minimized menu
			else if(this.$sidebar.hasClass('navbar-collapse')) this.mobile_style = 4;//collapsible (bootstrap style)
		}
		sidebar_mobile_style.call(self);

		function update_vars() {
			this.mobile_view = this.mobile_style < 4 && this.is_mobile_view();
			this.collapsible = !this.mobile_view && this.is_collapsible();

			this.minimized =
				(!this.collapsible && this.$sidebar.hasClass(minimized_menu_class))
				||
				(this.mobile_style == 3 && this.mobile_view && this.$sidebar.hasClass(responsive_min_class))

			this.horizontal = !(this.mobile_view || this.collapsible) && this.$sidebar.hasClass(horizontal_menu_class)
		}

		//update some basic variables
		$(window).on('resize.sidebar.vars' , function(){
			update_vars.call(self);
		}).triggerHandler('resize.sidebar.vars')

	}//end of Sidebar


	//sidebar events

	//menu-toggler
	$(document)
	.on(ace.click_event+'.ace.menu', '.menu-toggler', function(e){
		var btn = $(this);
		var sidebar = $(btn.attr('data-target'));
		if(sidebar.length == 0) return;

		e.preventDefault();

		sidebar.toggleClass('display');
		btn.toggleClass('display');

		var click_event = ace.click_event+'.ace.autohide';
		var auto_hide = sidebar.attr('data-auto-hide') === 'true';

		if( btn.hasClass('display') ) {
			//hide menu if clicked outside of it!
			if(auto_hide) {
				$(document).on(click_event, function(ev) {
					if( sidebar.get(0) == ev.target || $.contains(sidebar.get(0), ev.target) ) {
						ev.stopPropagation();
						return;
					}

					sidebar.removeClass('display');
					btn.removeClass('display');
					$(document).off(click_event);
				})
			}

			if(sidebar.attr('data-sidebar-scroll') == 'true') sidebar.ace_sidebar_scroll('reset');
		}
		else {
			if(auto_hide) $(document).off(click_event);
		}

		return false;
	})

	// //侧栏收缩、扩展按钮
	// .on(ace.click_event+'.ace.menu', '.sidebar-collapse', function(e){
	// 	var target = $(this).attr('data-target'), $sidebar = null;
	// 	if(target) $sidebar = $(target);
	// 	if($sidebar == null || $sidebar.length == 0) $sidebar = $(this).closest('.sidebar');
	// 	if($sidebar.length == 0) return;
	//
	// 	e.preventDefault();
	// 	$sidebar.ace_sidebar('toggleMenu', this);
	// 	//如果是收缩菜单，则添加交互菜单。如果是展开菜单，则去掉交互菜单
	// 	if($('#sidebar').hasClass('menu-min')){  //收缩状态，则添加上交互菜单
	// 		$('#ace-settings-highlight').removeAttr('checked').trigger('click');  //交互菜单
	// 		$('div.quick-nav span').html('');  //除去：快捷菜单
	// 	}
	// 	else{   //展开状态，则去掉交互菜单
	// 		$('#ace-settings-highlight').attr('checked',true).trigger('click');  //交互菜单，用click事件触发
	// 		$('div.quick-nav span').html('  快捷菜单');
	// 	}
	// })
	//this button is used in `mobile_style = 3` responsive menu style to expand minimized sidebar
	.on(ace.click_event+'.ace.menu', '.sidebar-expand', function(e){
		var target = $(this).attr('data-target'), $sidebar = null;
		if(target) $sidebar = $(target);
		if($sidebar == null || $sidebar.length == 0) $sidebar = $(this).closest('.sidebar');
		if($sidebar.length == 0) return;

		var btn = this;
		e.preventDefault();
		$sidebar.ace_sidebar('toggleResponsive', this);

		var click_event = ace.click_event+'.ace.autohide';
		if($sidebar.attr('data-auto-hide') === 'true') {
			if( $sidebar.hasClass('responsive-max') ) {
				$(document).on(click_event, function(ev) {
					if( $sidebar.get(0) == ev.target || $.contains($sidebar.get(0), ev.target) ) {
						ev.stopPropagation();
						return;
					}

					$sidebar.ace_sidebar('toggleResponsive', btn);
					$(document).off(click_event);
				})
			}
			else {
				$(document).off(click_event);
			}
		}
	})
	/**
	 .on('shown.bs.collapse.sidebar hidden.bs.collapse.sidebar', '.sidebar[data-auto-hide=true]', function(e){
		var click_event = ace.click_event+'.ace.autohide';

		var sidebar = this;
		if(e.type == 'shown') {
			$(document).on(click_event, function(ev) {
				if( sidebar == ev.target || $.contains(sidebar, ev.target) ) {
					ev.stopPropagation();
					return;
				}

				$(sidebar).collapse('hide');
				$(document).off(click_event);
			})
		}
		else $(document).off(click_event);
	});
	 */

		//hover时激活
	$.fn.ace_sidebar = function (option, value) {
		var method_call;

		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('ace_sidebar');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('ace_sidebar', (data = new Sidebar(this, options)));
			if (typeof option === 'string' && typeof data[option] === 'function') {
				if(value instanceof Array) method_call = data[option].apply(data, value);
				else method_call = data[option](value);
			}
		});

		return (method_call === undefined) ? $set : method_call;
	};


})(window.jQuery);
