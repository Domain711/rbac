(function (C, E) {
    var D = ace.vars["safari"] && navigator.userAgent.match(/version\/[1-5]/i);
    var A = "getComputedStyle" in window ? function (F, G) {
        F.offsetHeight;
        return window.getComputedStyle(F).position == G
    } : function (F, G) {
        F.offsetHeight;
        return C(F).css("position") == G
    };

    function B(M, a) {
        var h = this;
        var Y = C(window);
        var S = C(M), L = S.find(".nav-list"), O = S.find(".sidebar-toggle").eq(0),
            d = S.find(".sidebar-shortcuts").eq(0);
        var e = S.ace_sidebar("ref");
        S.attr("data-sidebar-scroll", "true");
        var g = L.get(0);
        if (!g) {
            return
        }
        var W = null, f = null, V = null, X = null, U = null, P = null;
        var H = a.scroll_to_active || ace.helper.boolAttr(M, "data-scroll-to-active") || false,
            N = a.include_shortcuts || ace.helper.boolAttr(M, "data-scroll-include-shortcuts") || false,
            R = a.include_toggle || ace.helper.boolAttr(M, "data-scroll-include-toggle") || false,
            K = a.smooth_scroll || ace.helper.intAttr(M, "data-scroll-smooth") || false,
            T = a.outside || ace.helper.boolAttr(M, "data-scroll-outside") || false,
            G = a.scroll_style || S.attr("data-scroll-style") || "", i = true;
        var J = a.mousewheel_lock || ace.helper.boolAttr(M, "data-mousewheel-lock") || false;
        this.is_scrolling = false;
        var F = false;
        this.sidebar_fixed = A(M, "fixed");
        var I, Q;
        var Z = function () {
            var j = L.parent().offset();
            if (h.sidebar_fixed) {
                j.top -= ace.helper.scrollTop()
            }
            return Y.innerHeight() - j.top - (R ? 0 : O.outerHeight())
        };
        var c = function () {
            return g.clientHeight
        };
        var b = function (o) {
            if (F) {
                return
            }
            if (!h.sidebar_fixed) {
                return
            }
            L.wrap('<div class="nav-wrap-up pos-rel" />');
            L.after("<div><div></div></div>");
            L.wrap('<div class="nav-wrap" />');
            if (!R) {
                O.css({"z-index": 1})
            }
            if (!N) {
                d.css({"z-index": 99})
            }
            W = L.parent().next().ace_scroll({
                size: Z(),
                mouseWheelLock: true,
                hoverReset: false,
                dragEvent: true,
                styleClass: G,
                touchDrag: false
            }).closest(".ace-scroll").addClass("nav-scroll");
            P = W.data("ace_scroll");
            f = W.find(".scroll-content").eq(0);
            V = f.find(" > div").eq(0);
            U = C(P.get_track());
            X = U.find(".scroll-bar").eq(0);
            if (N && d.length != 0) {
                L.parent().prepend(d).wrapInner("<div />");
                L = L.parent()
            }
            if (R && O.length != 0) {
                L.append(O);
                L.closest(".nav-wrap").addClass("nav-wrap-t")
            }
            L.css({position: "relative"});
            if (T === true) {
                W.addClass("scrollout")
            }
            g = L.get(0);
            g.style.top = 0;
            f.on("scroll.nav", function () {
                g.style.top = (-1 * this.scrollTop) + "px"
            });
            L.on(!!C.event.special.mousewheel ? "mousewheel.ace_scroll" : "mousewheel.ace_scroll DOMMouseScroll.ace_scroll", function (p) {
                if (!h.is_scrolling || !P.is_active()) {
                    return !J
                }
                return W.trigger(p)
            });
            L.on("mouseenter.ace_scroll", function () {
                U.addClass("scroll-hover")
            }).on("mouseleave.ace_scroll", function () {
                U.removeClass("scroll-hover")
            });
            var j = f.get(0);
            L.on("ace_drag.nav", function (p) {
                if (!h.is_scrolling || !P.is_active()) {
                    p.retval.cancel = true;
                    return
                }
                if (C(p.target).closest(".can-scroll").length != 0) {
                    p.retval.cancel = true;
                    return
                }
                if (p.direction == "up" || p.direction == "down") {
                    P.move_bar(true);
                    var q = p.dy;
                    q = parseInt(Math.min(I, q));
                    if (Math.abs(q) > 2) {
                        q = q * 2
                    }
                    if (q != 0) {
                        j.scrollTop = j.scrollTop + q;
                        g.style.top = (-1 * j.scrollTop) + "px"
                    }
                }
            });
            if (K) {
                L.on("touchstart.nav MSPointerDown.nav pointerdown.nav", function (p) {
                    L.css("transition-property", "none");
                    X.css("transition-property", "none")
                }).on("touchend.nav touchcancel.nav MSPointerUp.nav MSPointerCancel.nav pointerup.nav pointercancel.nav", function (p) {
                    L.css("transition-property", "top");
                    X.css("transition-property", "top")
                })
            }
            if (D && !R) {
                var l = O.get(0);
                if (l) {
                    f.on("scroll.safari", function () {
                        ace.helper.redraw(l)
                    })
                }
            }
            F = true;
            if (o == true) {
                h.reset();
                if (H) {
                    h.scroll_to_active()
                }
                H = false
            }
            if (typeof K === "number" && K > 0) {
                L.css({"transition-property": "top", "transition-duration": (K / 1000).toFixed(2) + "s"});
                X.css({"transition-property": "top", "transition-duration": (K / 1500).toFixed(2) + "s"});
                W.on("drag.start", function (p) {
                    p.stopPropagation();
                    L.css("transition-property", "none")
                }).on("drag.end", function (p) {
                    p.stopPropagation();
                    L.css("transition-property", "top")
                })
            }
            if (ace.vars["android"]) {
                var n = ace.helper.scrollTop();
                if (n < 2) {
                    window.scrollTo(n, 0);
                    setTimeout(function () {
                        h.reset()
                    }, 20)
                }
                var m = ace.helper.winHeight(), k;
                C(window).on("scroll.ace_scroll", function () {
                    if (h.is_scrolling && P.is_active()) {
                        k = ace.helper.winHeight();
                        if (k != m) {
                            m = k;
                            h.reset()
                        }
                    }
                })
            }
        };
        this.scroll_to_active = function () {
            if (!P || !P.is_active()) {
                return
            }
            try {
                var m;
                var n = e["vars"]();
                var o = S.find(".nav-list");
                if (n["minimized"] && !n["collapsible"]) {
                    m = o.find("> .active")
                } else {
                    m = L.find("> .active.hover");
                    if (m.length == 0) {
                        m = L.find(".active:not(.open)")
                    }
                }
                var l = m.outerHeight();
                o = o.get(0);
                var p = m.get(0);
                while (p != o) {
                    l += p.offsetTop;
                    p = p.parentNode
                }
                var j = l - W.height();
                if (j > 0) {
                    g.style.top = -j + "px";
                    f.scrollTop(j)
                }
            } catch (k) {
            }
        };
        this.reset = function (l) {
            if (l === true) {
                this.sidebar_fixed = A(M, "fixed")
            }
            if (!this.sidebar_fixed) {
                this.disable();
                return
            }
            if (!F) {
                b()
            }
            var k = e["vars"]();
            var j = !k["collapsible"] && !k["horizontal"] && (I = Z()) < (Q = g.clientHeight);
            this.is_scrolling = true;
            if (j) {
                V.css({height: Q, width: 8});
                W.prev().css({"max-height": I});
                P.update({size: I});
                P.enable();
                P.reset()
            }
            if (!j || !P.is_active()) {
                if (this.is_scrolling) {
                    this.disable()
                }
            } else {
                S.addClass("sidebar-scroll")
            }
        };
        this.disable = function () {
            this.is_scrolling = false;
            if (W) {
                W.css({"height": "", "max-height": ""});
                V.css({height: "", width: ""});
                W.prev().css({"max-height": ""});
                P.disable()
            }
            if (parseInt(g.style.top) < 0 && K && C.support.transition.end) {
                L.one(C.support.transition.end, function () {
                    S.removeClass("sidebar-scroll");
                    L.off(".trans")
                })
            } else {
                S.removeClass("sidebar-scroll")
            }
            g.style.top = 0
        };
        this.prehide = function (k) {
            if (!this.is_scrolling || e.get("minimized")) {
                return
            }
            if (c() + k < Z()) {
                this.disable()
            } else {
                if (k < 0) {
                    var j = f.scrollTop() + k;
                    if (j < 0) {
                        return
                    }
                    g.style.top = (-1 * j) + "px"
                }
            }
        };
        this._reset = function (j) {
            if (j === true) {
                this.sidebar_fixed = A(M, "fixed")
            }
            if (ace.vars["webkit"]) {
                setTimeout(function () {
                    h.reset()
                }, 0)
            } else {
                this.reset()
            }
        };
        this.set_hover = function () {
            if (U) {
                U.addClass("scroll-hover")
            }
        };
        this.get = function (j) {
            if (this.hasOwnProperty(j)) {
                return this[j]
            }
        };
        this.set = function (j, k) {
            if (this.hasOwnProperty(j)) {
                this[j] = k
            }
        };
        this.ref = function () {
            return this
        };
        this.updateStyle = function (j) {
            if (P == null) {
                return
            }
            P.update({styleClass: j})
        };
        S.on("hidden.ace.submenu.sidebar_scroll shown.ace.submenu.sidebar_scroll", ".submenu", function (j) {
            j.stopPropagation();
            if (!e.get("minimized")) {
                h._reset();
                if (j.type == "shown") {
                    h.set_hover()
                }
            }
        });
        b(true)
    }

    C(document).on("settings.ace.sidebar_scroll", function (F, H, G) {
        C(".sidebar[data-sidebar-scroll=true]").each(function () {
            var K = C(this);
            var I = K.ace_sidebar_scroll("ref");
            if (H == "sidebar_collapsed" && A(this, "fixed")) {
                if (K.attr("data-sidebar-hover") == "true") {
                    K.ace_sidebar_hover("reset")
                }
                I._reset()
            } else {
                if (H === "sidebar_fixed" || H === "navbar_fixed") {
                    var L = I.get("is_scrolling");
                    var J = A(this, "fixed");
                    I.set("sidebar_fixed", J);
                    if (J && !L) {
                        I._reset()
                    } else {
                        if (!J) {
                            I.disable()
                        }
                    }
                }
            }
        })
    });
    C(window).on("resize.ace.sidebar_scroll", function () {
        C(".sidebar[data-sidebar-scroll=true]").each(function () {
            var H = C(this);
            if (H.attr("data-sidebar-hover") == "true") {
                H.ace_sidebar_hover("reset")
            }
            var F = C(this).ace_sidebar_scroll("ref");
            var G = A(this, "fixed");
            F.set("sidebar_fixed", G);
            F._reset()
        })
    });
    if (!C.fn.ace_sidebar_scroll) {
        C.fn.ace_sidebar_scroll = function (H, F) {
            var I;
            var G = this.each(function () {
                var K = C(this);
                var L = K.data("ace_sidebar_scroll");
                var J = typeof H === "object" && H;
                if (!L) {
                    K.data("ace_sidebar_scroll", (L = new B(this, J)))
                }
                if (typeof H === "string" && typeof L[H] === "function") {
                    I = L[H](F)
                }
            });
            return (I === E) ? G : I
        }
    }
})(window.jQuery);