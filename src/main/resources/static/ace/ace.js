(function (B, C) {
    if (!("ace" in window)) {
        window["ace"] = {}
    }
    if (!("helper" in window["ace"])) {
        window["ace"].helper = {}
    }
    if (!("vars" in window["ace"])) {
        window["ace"].vars = {}
    }
    window["ace"].vars["icon"] = " ace-icon ";
    window["ace"].vars[".icon"] = ".ace-icon";
    ace.vars["touch"] = ("ontouchstart" in document.documentElement);
    ace["click_event"] = ace.vars["touch"] && B.fn.tap ? "tap" : "click";
    var A = navigator.userAgent;
    ace.vars["webkit"] = !!A.match(/AppleWebKit/i);
    ace.vars["safari"] = !!A.match(/Safari/i) && !A.match(/Chrome/i);
    ace.vars["android"] = ace.vars["safari"] && !!A.match(/Android/i);
    ace.vars["ios_safari"] = !!A.match(/OS ([4-9])(_\d)+ like Mac OS X/i) && !A.match(/CriOS/i);
    ace.vars["ie"] = window.navigator.msPointerEnabled || (document.all && document.querySelector);
    ace.vars["old_ie"] = document.all && !document.addEventListener;
    ace.vars["very_old_ie"] = document.all && !document.querySelector;
    ace.vars["firefox"] = "MozAppearance" in document.documentElement.style;
    ace.vars["non_auto_fixed"] = ace.vars["android"] || ace.vars["ios_safari"]
})(jQuery);
jQuery(function (C) {
    B();
    J();
    F();
    A();
    G();
    E();
    D();
    I();
    L();
    H();
    K();

    function B() {
        if (ace.vars["non_auto_fixed"]) {
            C("body").addClass("mob-safari")
        }
        ace.vars["transition"] = !!C.support.transition.end
    }

    function J() {
        var M = C(".sidebar");
        if (C.fn.ace_sidebar) {
            M.ace_sidebar()
        }
        if (C.fn.ace_sidebar_scroll) {
            M.ace_sidebar_scroll({
                "scroll_to_active": true,
                "include_shortcuts": true,
                "include_toggle": false || ace.vars["safari"] || ace.vars["ios_safari"],
                "smooth_scroll": 150,
                "outside": false
            })
        }
        if (C.fn.ace_sidebar_hover) {
            M.ace_sidebar_hover({
                "sub_hover_delay": 750,
                "sub_scroll_style": "no-track scroll-thin scroll-margin scroll-visible"
            })
        }
    }

    function F() {
        if (C.fn.ace_ajax) {
            C("[data-ajax-content=true]").ace_ajax({
                "close_active": true, "content_url": function (M) {
                    if (!M.match(/^page\//)) {
                        return false
                    }
                    var N = document.location.pathname;
                    if (N.match(/(\/ajax\/)(ajax\.html)?/)) {
                        return N.replace(/(\/ajax\/)(ajax\.html)?/, "/ajax/" + M.replace(/^page\//, "") + ".html")
                    }
                    return N + "?" + M.replace(/\//, "=")
                }, "default_url": "page/index"
            })
        }
    }

    function A() {
        var M = !!C.fn.ace_scroll;
        if (M) {
            C(".dropdown-content").ace_scroll({reset: false, mouseWheelLock: true})
        }
        if (M && !ace.vars["old_ie"]) {
            C(window).on("resize.reset_scroll", function () {
                C(".ace-scroll:not(.scroll-disabled)").not(":hidden").ace_scroll("reset")
            });
            if (M) {
                C(document).on("settings.ace.reset_scroll", function (O, N) {
                    if (N == "sidebar_collapsed") {
                        C(".ace-scroll:not(.scroll-disabled)").not(":hidden").ace_scroll("reset")
                    }
                })
            }
        }
    }

    function G() {
        C(document).on("click.dropdown.pos", '.dropdown-toggle[data-position="auto"]', function () {
            var N = C(this).offset();
            var M = C(this.parentNode);
            if (parseInt(N.top + C(this).height()) + 50 > (ace.helper.scrollTop() + ace.helper.winHeight() - M.find(".dropdown-menu").eq(0).height())) {
                M.addClass("dropup")
            } else {
                M.removeClass("dropup")
            }
        })
    }

    function E() {
        C('.ace-nav [class*="icon-animated-"]').closest("a").one("click", function () {
            var M = C(this).find('[class*="icon-animated-"]').eq(0);
            var N = M.attr("class").match(/icon\-animated\-([\d\w]+)/);
            M.removeClass(N[0])
        });
        C(document).on("click", ".dropdown-navbar .nav-tabs", function (O) {
            O.stopPropagation();
            var P, M;
            var N = O.target;
            if ((P = C(O.target).closest("[data-toggle=tab]")) && P.length > 0) {
                P.tab("show");
                O.preventDefault();
                C(window).triggerHandler("resize.navbar.dropdown")
            }
        })
    }

    function D() {
        C(".sidebar .nav-list .badge[title],.sidebar .nav-list .badge[title]").each(function () {
            var M = C(this).attr("class").match(/tooltip\-(?:\w+)/);
            M = M ? M[0] : "tooltip-error";
            C(this).tooltip({
                "placement": function (N, O) {
                    var P = C(O).offset();
                    if (parseInt(P.left) < parseInt(document.body.scrollWidth / 2)) {
                        return "right"
                    }
                    return "left"
                },
                container: "body",
                template: '<div class="tooltip ' + M + '"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
            })
        })
    }

    function I() {
        var N = C(".btn-scroll-up");
        if (N.length > 0) {
            var M = false;
            C(window).on("scroll.scroll_btn", function () {
                var Q = ace.helper.scrollTop();
                var P = ace.helper.winHeight();
                var O = document.body.scrollHeight;
                if (Q > parseInt(P / 4) || (Q > 0 && O >= P && P + Q >= O - 1)) {
                    if (!M) {
                        N.addClass("display");
                        M = true
                    }
                } else {
                    if (M) {
                        N.removeClass("display");
                        M = false
                    }
                }
            }).triggerHandler("scroll.scroll_btn");
            N.on(ace.click_event, function () {
                var O = Math.min(500, Math.max(100, parseInt(ace.helper.scrollTop() / 3)));
                C("html,body").animate({scrollTop: 0}, O);
                return false
            })
        }
    }

    function L() {
        if (ace.vars["webkit"]) {
            var M = C(".ace-nav").get(0);
            if (M) {
                C(window).on("resize.webkit_fix", function () {
                    ace.helper.redraw(M)
                })
            }
        }
        if (ace.vars["ios_safari"]) {
            C(document).on("ace.settings.ios_fix", function (O, P, N) {
                if (P != "navbar_fixed") {
                    return
                }
                C(document).off("focus.ios_fix blur.ios_fix", "input,textarea,.wysiwyg-editor");
                if (N == true) {
                    C(document).on("focus.ios_fix", "input,textarea,.wysiwyg-editor", function () {
                        C(window).on("scroll.ios_fix", function () {
                            var Q = C("#navbar").get(0);
                            if (Q) {
                                ace.helper.redraw(Q)
                            }
                        })
                    }).on("blur.ios_fix", "input,textarea,.wysiwyg-editor", function () {
                        C(window).off("scroll.ios_fix")
                    })
                }
            }).triggerHandler("ace.settings.ios_fix", ["navbar_fixed", C("#navbar").css("position") == "fixed"])
        }
    }

    function H() {
        C(document).on("hide.bs.collapse show.bs.collapse", function (N) {
            var M = N.target.getAttribute("id");
            var O = C('a[href*="#' + M + '"]');
            if (O.length == 0) {
                O = C('a[data-target*="#' + M + '"]')
            }
            if (O.length == 0) {
                return
            }
            O.find(ace.vars[".icon"]).each(function () {
                var S = C(this);
                var Q;
                var P = null;
                var R = null;
                if ((P = S.attr("data-icon-show"))) {
                    R = S.attr("data-icon-hide")
                } else {
                    if (Q = S.attr("class").match(/fa\-(.*)\-(up|down)/)) {
                        P = "fa-" + Q[1] + "-down";
                        R = "fa-" + Q[1] + "-up"
                    }
                }
                if (P) {
                    if (N.type == "show") {
                        S.removeClass(P).addClass(R)
                    } else {
                        S.removeClass(R).addClass(P)
                    }
                    return false
                }
            })
        })
    }

    function K() {
        if (ace.vars["old_ie"]) {
            return
        }
        C(".ace-nav > li").on("shown.bs.dropdown.navbar", function (O) {
            N.call(this)
        }).on("hidden.bs.dropdown.navbar", function (O) {
            C(window).off("resize.navbar.dropdown");
            M.call(this)
        });

        function N() {
            var V = C(this).find("> .dropdown-menu");
            if (V.css("position") == "fixed") {
                var S = parseInt(C(window).width());
                var R = S > 320 ? 60 : (S > 240 ? 40 : 30);
                var U = parseInt(S) - R;
                var W = parseInt(C(window).height()) - 30;
                var g = parseInt(Math.min(U, 320));
                V.css("width", g);
                var Y = false;
                var b = 0;
                var a = V.find(".tab-pane.active .dropdown-content.ace-scroll");
                if (a.length == 0) {
                    a = V.find(".dropdown-content.ace-scroll")
                } else {
                    Y = true
                }
                var Z = a.closest(".dropdown-menu");
                var e = V[0].scrollHeight;
                if (a.length == 1) {
                    var P = a.find(".scroll-content")[0];
                    if (P) {
                        e = P.scrollHeight
                    }
                    b += Z.find(".dropdown-header").outerHeight();
                    b += Z.find(".dropdown-footer").outerHeight();
                    var T = Z.closest(".tab-content");
                    if (T.length != 0) {
                        b += T.siblings(".nav-tabs").eq(0).height()
                    }
                }
                var Q = parseInt(Math.min(W, 480, e + b));
                var c = parseInt(Math.abs((U + R - g) / 2));
                var X = parseInt(Math.abs((W + 30 - Q) / 2));
                var f = parseInt(V.css("z-index")) || 0;
                V.css({"height": Q, "left": c, "right": "auto", "top": X - (!Y ? 1 : 3)});
                if (a.length == 1) {
                    if (!ace.vars["touch"]) {
                        a.ace_scroll("update", {size: Q - b}).ace_scroll("enable").ace_scroll("reset")
                    } else {
                        a.ace_scroll("disable").css("max-height", Q - b).addClass("overflow-scroll")
                    }
                }
                V.css("height", Q + (!Y ? 2 : 7));
                if (V.hasClass("user-menu")) {
                    V.css("height", "");
                    var O = C(this).find(".user-info");
                    if (O.length == 1 && O.css("position") == "fixed") {
                        O.css({
                            "left": c,
                            "right": "auto",
                            "top": X,
                            "width": g - 2,
                            "max-width": g - 2,
                            "z-index": f + 1
                        })
                    } else {
                        O.css({"left": "", "right": "", "top": "", "width": "", "max-width": "", "z-index": ""})
                    }
                }
                C(this).closest(".navbar.navbar-fixed-top").css("z-index", f)
            } else {
                if (V.length != 0) {
                    M.call(this, V)
                }
            }
            var d = this;
            C(window).off("resize.navbar.dropdown").one("resize.navbar.dropdown", function () {
                C(d).triggerHandler("shown.bs.dropdown.navbar")
            })
        }

        function M(O) {
            O = O || C(this).find("> .dropdown-menu");
            if (O.length > 0) {
                O.css({
                    "width": "",
                    "height": "",
                    "left": "",
                    "right": "",
                    "top": ""
                }).find(".dropdown-content").each(function () {
                    if (ace.vars["touch"]) {
                        C(this).css("max-height", "").removeClass("overflow-scroll")
                    }
                    var Q = parseInt(C(this).attr("data-size") || 0) || C.fn.ace_scroll.defaults.size;
                    C(this).ace_scroll("update", {size: Q}).ace_scroll("enable").ace_scroll("reset")
                });
                if (O.hasClass("user-menu")) {
                    var P = C(this).find(".user-info").css({
                        "left": "",
                        "right": "",
                        "top": "",
                        "width": "",
                        "max-width": "",
                        "z-index": ""
                    })
                }
            }
            C(this).closest(".navbar").css("z-index", "")
        }
    }
});
ace.helper.redraw = function (A, C) {
    var B = A.style["display"];
    A.style.display = "none";
    A.offsetHeight;
    if (C !== true) {
        A.style.display = B
    } else {
        setTimeout(function () {
            A.style.display = B
        }, 10)
    }
};
ace.helper.boolAttr = function (A, B) {
    return A.getAttribute(B) === "true"
};
ace.helper.intAttr = function (A, B) {
    return parseInt(A.getAttribute(B)) || 0
};
ace.helper.scrollTop = function () {
    return document.scrollTop || document.documentElement.scrollTop || document.body.scrollTop
};
ace.helper.winHeight = function () {
    return window.innerHeight || document.documentElement.clientHeight
};
ace.helper.camelCase = function (A) {
    return A.replace(/-([\da-z])/gi, function (C, B) {
        return B ? B.toUpperCase() : ""
    })
};
ace.helper.removeStyle = "removeProperty" in document.documentElement.style ? function (A, B) {
    A.style.removeProperty(B)
} : function (A, B) {
    A.style[ace.helper.camelCase(B)] = ""
};
ace.helper.hasClass = "classList" in document.documentElement ? function (A, B) {
    return A.classList.contains(B)
} : function (A, B) {
    return A.className.indexOf(B) > -1
};