package com.yunos.rbac.util;

import com.github.pagehelper.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 煮剑焚酒.
 * QQ:1397257618
 * User: sdake
 * Date: 2019/2/1
 * Time: 19:40
 */
public class PageTool<T> implements Serializable {

    private HttpServletRequest httpServletRequest;
    //是否简单分页
    private boolean simple = false;

    StringBuffer stringBuffer = new StringBuffer();
    StringBuffer pageStr = new StringBuffer();
    StringBuffer html = new StringBuffer();
    StringBuffer html2 = new StringBuffer();
    StringBuffer replace = new StringBuffer(); //替换字符串

    private static final long serialVersionUID = 1L;
    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //当前页的数量
    private int size;
    //由于startRow和endRow不常用，这里说个具体的用法
    //可以在页面中"显示startRow到endRow 共size条数据"

    //当前页面第一个元素在数据库中的行号
    private int startRow;
    //当前页面最后一个元素在数据库中的行号
    private int endRow;
    //总记录数
    private long total;
    //总页数
    private int pages;
    //结果集
    private List<T> list;

    //前一页
    private int prePage;
    //下一页
    private int nextPage;

    //是否为第一页
    private boolean isFirstPage = false;
    //是否为最后一页
    private boolean isLastPage = false;
    //是否有前一页
    private boolean hasPreviousPage = false;
    //是否有下一页
    private boolean hasNextPage = false;
    //导航页码数
    private int navigatePages;
    //所有导航页号
    private int[] navigatepageNums;
    //导航条上的第一页
    private int navigateFirstPage;
    //导航条上的最后一页
    private int navigateLastPage;

    private PageTool() {
    }

    public static PageTool getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final PageTool INSTANCE = new PageTool();
    }

    /**
     * 包装Page对象
     *
     * @param list
     */
    public PageTool setData(List<T> list, HttpServletRequest httpServletRequest) {
//        this(list, httpServletRequest, 8, false);
        return setData(list, httpServletRequest, 8);
    }

    public PageTool setData(List<T> list, HttpServletRequest httpServletRequest, int navigatePages) {
//        this(list, httpServletRequest, navigatePages, false);
        return setData(list, httpServletRequest, navigatePages, false);
    }

    /**
     * 包装Page对象
     *
     * @param list          page结果
     * @param navigatePages 页码数量
     */
    public PageTool setData(List<T> list, HttpServletRequest httpServletRequest, int navigatePages, boolean simple) {
        stringBuffer.delete(0, (stringBuffer.length()));
        pageStr.delete(0, (stringBuffer.length()));
        html.delete(0, (stringBuffer.length()));
        if (list instanceof Page) {
            Page page = (Page) list;
            this.httpServletRequest = httpServletRequest;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.simple = simple;
            this.pages = page.getPages();
            this.list = page;
            this.size = page.size();
            this.total = page.getTotal();
            //由于结果是>startRow的，所以实际的需要+1
            if (this.size == 0) {
                this.startRow = 0;
                this.endRow = 0;
            } else {
                this.startRow = page.getStartRow() + 1;
                //计算实际的endRow（最后一页的时候特殊）
                this.endRow = this.startRow - 1 + this.size;
            }
        } else if (list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();

            this.pages = this.pageSize > 0 ? 1 : 0;
            this.list = list;
            this.size = list.size();
            this.total = list.size();
            this.startRow = 0;
            this.endRow = list.size() > 0 ? list.size() - 1 : 0;
        }
        if (list instanceof Collection) {
            this.navigatePages = navigatePages;
            //计算导航页
            calcNavigatepageNums();
            //计算前后页，第一页，最后一页
            calcPage();
            //判断页面边界
            judgePageBoudary();
        }
        return getInstance();
    }


    /**
     * 计算导航页
     */
    private void calcNavigatepageNums() {
        //当总页数小于或等于导航页码数时
        if (pages <= navigatePages) {
            navigatepageNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时
            navigatepageNums = new int[navigatePages];
            int startNum = pageNum - navigatePages / 2;
            int endNum = pageNum + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > pages) {
                endNum = pages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
    }

    /**
     * 计算前后页，第一页，最后一页
     */
    private void calcPage() {
        if (navigatepageNums != null && navigatepageNums.length > 0) {
            navigateFirstPage = navigatepageNums[0];
            navigateLastPage = navigatepageNums[navigatepageNums.length - 1];
            if (pageNum > 1) {
                prePage = pageNum - 1;
            }
            if (pageNum < pages) {
                nextPage = pageNum + 1;
            }
        }
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages || pages == 0;
        ;
        hasPreviousPage = pageNum > 1;
        hasNextPage = pageNum < pages;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Deprecated
    // firstPage就是1, 此函数获取的是导航条上的第一页, 容易产生歧义
    public int getFirstPage() {
        return navigateFirstPage;
    }

    @Deprecated
    public void setFirstPage(int firstPage) {
        this.navigateFirstPage = firstPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    @Deprecated
    // 请用getPages()来获取最后一页, 此函数获取的是导航条上的最后一页, 容易产生歧义.
    public int getLastPage() {
        return navigateLastPage;
    }

    @Deprecated
    public void setLastPage(int lastPage) {
        this.navigateLastPage = lastPage;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int[] getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageTool{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", size=").append(size);
        sb.append(", startRow=").append(startRow);
        sb.append(", endRow=").append(endRow);
        sb.append(", total=").append(total);
        sb.append(", pages=").append(pages);
        sb.append(", list=").append(list);
        sb.append(", prePage=").append(prePage);
        sb.append(", nextPage=").append(nextPage);
        sb.append(", isFirstPage=").append(isFirstPage);
        sb.append(", isLastPage=").append(isLastPage);
        sb.append(", hasPreviousPage=").append(hasPreviousPage);
        sb.append(", hasNextPage=").append(hasNextPage);
        sb.append(", navigatePages=").append(navigatePages);
        sb.append(", navigateFirstPage=").append(navigateFirstPage);
        sb.append(", navigateLastPage=").append(navigateLastPage);
        sb.append(", navigatepageNums=");
        if (navigatepageNums == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < navigatepageNums.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(navigatepageNums[i]);
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }

/****************************************************/
    /**
     * 输出页码条
     *
     * @return
     */
    public String render() {
        // 当前页等于1，并且没有下一页,不显示页码
        if (!(1 == pageNum && (nextPage == 0))) {
            if (pageStr.length() > 0) {
                pageStr.delete(0, pageStr.length());
            }
            pageStr.append("<ul class=\"pager pagination\">");
            if (simple) {
                //简单分页
                pageStr.append(getPreviousButton());
                pageStr.append(getNextButton());
                pageStr.append("</ul>");
                return pageStr.toString();
            } else {
                pageStr.append(getPreviousButton());
                pageStr.append(getLinks());
                pageStr.append(getNextButton());
                pageStr.append("</ul>");
                return pageStr.toString();
            }
        }
        return "";
    }

    /**
     * 上一页按钮
     *
     * @return
     */
    private String getPreviousButton() {
        if (pageNum <= 1) {
            return getDisabledTextWrapper("&laquo;");
        }
        return getPageLinkWrapper(httpServletRequest(pageNum - 1), "&laquo;");
    }

    /**
     * 下一页按钮
     *
     * @return
     */
    protected String getNextButton() {
        if (!hasNextPage) {
            return getDisabledTextWrapper("&raquo;");
        }
        return getPageLinkWrapper(httpServletRequest(pageNum + 1), "&raquo;");
    }


    /**
     * 页码按钮
     *
     * @return
     */
    protected String getLinks() {
        if (simple) {
            return "";
        }
        ArrayList<String> httpServletRequestRange1 = null;
        ArrayList<String> httpServletRequestRange2 = null;
        ArrayList<String> httpServletRequestRange3 = null;
        int side = 3;
        int window = side * 2;
        if (navigateLastPage < window + 6) {
            httpServletRequestRange1 = gethttpServletRequestRange(1, navigateLastPage);
        } else if (pageNum <= window) {
            httpServletRequestRange1 = gethttpServletRequestRange(1, navigateLastPage);
            httpServletRequestRange2 = gethttpServletRequestRange(navigateLastPage - 1, navigateLastPage);
        } else if (pageNum > (navigateLastPage - window)) {
            httpServletRequestRange1 = gethttpServletRequestRange(1, 2);
            httpServletRequestRange2 = gethttpServletRequestRange(navigateLastPage - (window + 2), navigateLastPage);
        } else {
            httpServletRequestRange1 = gethttpServletRequestRange(1, 2);
            httpServletRequestRange2 = gethttpServletRequestRange(navigateLastPage - 1, navigateLastPage);
            httpServletRequestRange3 = gethttpServletRequestRange(pageNum - side, pageNum + side);
        }
        if (html.length() > 0) {
            html.delete(0, html.length());
        }
        if (httpServletRequestRange1 != null) {
            html.append(gethttpServletRequestLinks(httpServletRequestRange1));
        }
        if (httpServletRequestRange2 != null) {
            html.append(getDots());
            html.append(gethttpServletRequestLinks(httpServletRequestRange2));
        }
        if (httpServletRequestRange3 != null) {
            html.append(getDots());
            html.append(gethttpServletRequestLinks(httpServletRequestRange3));
        }
        return html.toString();
    }

    /**
     * 生成省略号按钮
     *
     * @return string
     */
    protected String getDots() {
        return getDisabledTextWrapper("...");
    }

    /**
     * 批量生成页码按钮
     *
     * @param
     * @return
     */
    protected String gethttpServletRequestLinks(ArrayList<String> httpServletRequests) {
        if (html2.length() > 0) {
            html2.delete(0, html2.length());
        }
        int size = httpServletRequests.size();
        for (int i = 0; i < size; i++) {
            html2.append(getPageLinkWrapper(httpServletRequests.get(i), "" + (i + 1)));
        }
        return html2.toString();
    }


    /**
     * 创建一组分页链接
     *
     * @return
     */
    public ArrayList<String> gethttpServletRequestRange(int start, int end) {
        ArrayList<String> httpServletRequests = new ArrayList<>();
        for (int page = start; page <= end; page++) {
            httpServletRequests.add(httpServletRequest(page));
        }
        return httpServletRequests;
    }

    /**
     * 生成普通页码按钮
     *
     * @return
     */
    protected String getPageLinkWrapper(String httpServletRequest, String page) {
        int i;
        try {
            i = Integer.parseInt(page);
        } catch (Exception e) {
            return getAvailablePageWrapper(httpServletRequest, page);
        }

        if (i == pageNum) {
            return getActivePageWrapper(page);
        }
        return getAvailablePageWrapper(httpServletRequest, page);
    }

    /**
     * 生成一个激活的按钮
     *
     * @return
     */
    protected String getActivePageWrapper(String text) {
        return "<li class=\"active page-item\"><span class=\"page-link\">" + text + "</span></li>";
    }

    /**
     * 生成一个禁用的按钮
     *
     * @return
     */
    protected String getDisabledTextWrapper(String text) {
        return "<li class=\"disabled page-item\"><span class=\"page-link\">" + text + "</span></li>";
    }

    /**
     * 生成一个可点击的按钮
     *
     * @param httpServletRequest
     * @param page
     * @return
     */
    protected String getAvailablePageWrapper(String httpServletRequest, String page) {
        return "<li class=\"page-item\"><a class=\"page-link\" href=\"" + httpServletRequest + "\">" + page + "</a></li>";
    }

    /**
     * page=2&name=tom
     *
     * @param page
     * @return
     */
    private String httpServletRequest(int page) {
        if (page <= 0) {
            page = 1;
        }
        String queryString = httpServletRequest.getQueryString();
        if (queryString != null) {
            //查找字符串中子字符串第一次出现的位置
            int index = queryString.indexOf("page=");
            int index1 = queryString.indexOf("&");

            if (stringBuffer.length() > 0) {
                stringBuffer.delete(0, stringBuffer.length());
            }
            if (replace.length() > 0) {
                replace.delete(0, replace.length());
            }

            if (index != -1) {
                //说明有page值
                if (index1 != -1) {
                    //说明有其他参数也有page
                    if (index == 0) { //?page=  在最前面
                        return stringBuffer
                                .append(httpServletRequest.getRequestURI())
                                .append("?page=")
                                .append(page)
                                .append(queryString.substring(index1))
                                .toString();
                    } else {
                        StringBuffer rep;
                        replace.append(queryString);
                        int i = replace.indexOf("&", index);
                        if (i == -1) {
                            rep = replace.replace(index + 5, replace.length(), "" + page);
                        }else {
                            rep = replace.replace(index + 5, i, "" + page);
                        }
                        return stringBuffer
                                .append(httpServletRequest.getRequestURI())
                                .append("?")
                                .append(rep)
                                .toString();
                    }
                } else {
//                没有其他参数
                    return stringBuffer
                            .append(httpServletRequest.getRequestURI())
                            .append("?page=")
                            .append(page)
                            .toString();
                }
            }
        } else {
            return httpServletRequest.getRequestURI() + "?page=" + page;
        }
        return httpServletRequest.getRequestURI();
    }


}
