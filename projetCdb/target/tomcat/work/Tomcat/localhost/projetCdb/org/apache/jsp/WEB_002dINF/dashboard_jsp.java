/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2019-03-01 11:04:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class dashboard_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fend_005fbegin;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fend_005fbegin = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fend_005fbegin.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<title>Computer Database JSP</title>\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("<meta charset=\"utf-8\">\n");
      out.write("<!-- Bootstrap -->\n");
      out.write("<link href=\"static/css/bootstrap.min.css\" rel=\"stylesheet\"\n");
      out.write("\tmedia=\"screen\">\n");
      out.write("<link href=\"static/css/font-awesome.css\" rel=\"stylesheet\" media=\"screen\">\n");
      out.write("<link href=\"static/css/main.css\" rel=\"stylesheet\" media=\"screen\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\t<header class=\"navbar navbar-inverse navbar-fixed-top\">\n");
      out.write("\t\t<div class=\"container\">\n");
      out.write("\t\t\t<a class=\"navbar-brand\" href=\"/projetCdb/\"> Application -\n");
      out.write("\t\t\t\tComputer Database </a>\n");
      out.write("\t\t</div>\n");
      out.write("\t</header>\n");
      out.write("\n");
      out.write("\t<section id=\"main\">\n");
      out.write("\t\t<div class=\"container\">\n");
      out.write("\t\t\t<h1 id=\"homeTitle\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${nbComputers}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write(" computers found</h1>\n");
      out.write("\t\t\t<div id=\"actions\" class=\"form-horizontal\">\n");
      out.write("\t\t\t\t<div class=\"pull-left\">\n");
      out.write("\t\t\t\t\t<form id=\"searchForm\" action=\"/projetCdb/\" method=\"POST\" class=\"form-inline\">\n");
      out.write("\n");
      out.write("\t\t\t\t\t\t<input type=\"search\" id=\"searchbox\" name=\"search\"\n");
      out.write("\t\t\t\t\t\t\tclass=\"form-control\" placeholder=\"Search name\" /> <input\n");
      out.write("\t\t\t\t\t\t\ttype=\"submit\" id=\"searchsubmit\" value=\"Filter by name\"\n");
      out.write("\t\t\t\t\t\t\tclass=\"btn btn-primary\" />\n");
      out.write("\t\t\t\t\t</form>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t\t<div class=\"pull-right\">\n");
      out.write("\t\t\t\t\t<a class=\"btn btn-success\" id=\"addComputer\"\n");
      out.write("\t\t\t\t\t\thref=\"/projetCdb/addComputer\">Add Computer</a> <a\n");
      out.write("\t\t\t\t\t\tclass=\"btn btn-default\" id=\"editComputer\" href=\"#\"\n");
      out.write("\t\t\t\t\t\tonclick=\"$.fn.toggleEditMode();\">Edit</a>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\n");
      out.write("\t\t<form id=\"deleteForm\" action=\"#\" method=\"POST\">\n");
      out.write("\t\t\t<input type=\"hidden\" name=\"selection\" value=\"\">\n");
      out.write("\t\t</form>\n");
      out.write("\n");
      out.write("\t\t<!--  Table -->\n");
      out.write("\t\t<section>\n");
      out.write("\t\t\t<div class=\"container\" style=\"margin-top: 10px;\">\n");
      out.write("\t\t\t\t<table class=\"table table-striped table-bordered\">\n");
      out.write("\t\t\t\t\t<thead>\n");
      out.write("\t\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t\t<!-- Variable declarations for passing labels as parameters -->\n");
      out.write("\t\t\t\t\t\t\t<!-- Table header for Computer Name -->\n");
      out.write("\n");
      out.write("\t\t\t\t\t\t\t<th class=\"editMode\" style=\"width: 60px; height: 22px;\"><input\n");
      out.write("\t\t\t\t\t\t\t\ttype=\"checkbox\" id=\"selectall\" /> <span\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"vertical-align: top;\"> - <a href=\"#\"\n");
      out.write("\t\t\t\t\t\t\t\t\tid=\"deleteSelected\" onclick=\"$.fn.deleteSelected();\"> <i\n");
      out.write("\t\t\t\t\t\t\t\t\t\tclass=\"fa fa-trash-o fa-lg\"></i>\n");
      out.write("\t\t\t\t\t\t\t\t</a>\n");
      out.write("\t\t\t\t\t\t\t</span></th>\n");
      out.write("\t\t\t\t\t\t\t<th>Computer name</th>\n");
      out.write("\t\t\t\t\t\t\t<th>Introduced date</th>\n");
      out.write("\t\t\t\t\t\t\t<!-- Table header for Discontinued Date -->\n");
      out.write("\t\t\t\t\t\t\t<th>Discontinued date</th>\n");
      out.write("\t\t\t\t\t\t\t<!-- Table header for Company -->\n");
      out.write("\t\t\t\t\t\t\t<th>Company</th>\n");
      out.write("\n");
      out.write("\t\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t\t</thead>\n");
      out.write("\n");
      out.write("\t\t\t\t\t<!-- Browse attribute computers -->\n");
      out.write("\n");
      out.write("\t\t\t\t\t<tbody id=\"results\">\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t\t</tbody>\n");
      out.write("\t\t\t\t</table>\n");
      out.write("\t\t</section>\n");
      out.write("\t\t</div>\n");
      out.write("\t</section>\n");
      out.write("\n");
      out.write("\t<footer class=\"navbar-fixed-bottom\">\n");
      out.write("\t\t<div class=\"container text-center\">\n");
      out.write("\t\t\t<ul class=\"pagination\">\n");
      out.write("\t\t\t\t<li><a href=\"#\" aria-label=\"Previous\"> <span\n");
      out.write("\t\t\t\t\t\taria-hidden=\"true\">&laquo;</span>\n");
      out.write("\t\t\t\t</a></li>\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_c_005fforEach_005f1(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t<li><a href=\"#\" aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span>\n");
      out.write("\t\t\t\t</a></li>\n");
      out.write("\t\t\t</ul>\n");
      out.write("\n");
      out.write("\t\t\t<div class=\"btn-group btn-group-sm pull-right\" role=\"group\">\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"btn btn-default\">10</button>\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"btn btn-default\">50</button>\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"btn btn-default\">100</button>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t</footer>\n");
      out.write("\t<script src=\"static/js/jquery.min.js\"></script>\n");
      out.write("\t<script src=\"static/js/bootstrap.min.js\"></script>\n");
      out.write("\t<script src=\"static/js/dashboard.js\"></script>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fforEach_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f0.setParent(null);
    // /WEB-INF/dashboard.jsp(79,6) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setItems(new org.apache.jasper.el.JspValueExpression("/WEB-INF/dashboard.jsp(79,6) '${computers}'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${computers}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    // /WEB-INF/dashboard.jsp(79,6) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setVar("computer");
    int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
      if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("\t\t\t\t\t\t\t<tr>\n");
          out.write("\t\t\t\t\t\t\t\t<td class=\"editMode\"><input type=\"checkbox\" name=\"cb\"\n");
          out.write("\t\t\t\t\t\t\t\t\tclass=\"cb\" value=\"0\"></td>\n");
          out.write("\t\t\t\t\t\t\t\t<td><a\n");
          out.write("\t\t\t\t\t\t\t\t\thref=\"/projetCdb/editComputer?idComputer=");
          if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
            return true;
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${computer.name}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("</a></td>\n");
          out.write("\t\t\t\t\t\t\t\t<td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${computer.introduced}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("</td>\n");
          out.write("\t\t\t\t\t\t\t\t<td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${computer.discontinued}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("</td>\n");
          out.write("\t\t\t\t\t\t\t\t<td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${computer.company}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("</td>\n");
          out.write("\t\t\t\t\t\t\t</tr>\n");
          out.write("\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (java.lang.Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f0.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fout_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f0, javax.servlet.jsp.PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f0 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fout_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f0);
    // /WEB-INF/dashboard.jsp(84,50) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fout_005f0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${computer.id}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
    if (_jspx_th_c_005fout_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f1 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fend_005fbegin.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f1.setParent(null);
    // /WEB-INF/dashboard.jsp(102,4) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f1.setVar("numPage");
    // /WEB-INF/dashboard.jsp(102,4) name = begin type = int reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f1.setBegin(1);
    // /WEB-INF/dashboard.jsp(102,4) name = end type = int reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f1.setEnd(((java.lang.Integer) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${nbPages}", java.lang.Integer.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false)).intValue());
    int[] _jspx_push_body_count_c_005fforEach_005f1 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
      if (_jspx_eval_c_005fforEach_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("\t\t\t\t\t<li><a href=\"/projetCdb/cdb?selectedPage=");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${numPage}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${numPage}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("</a></li>\n");
          out.write("\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (java.lang.Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f1[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f1.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1);
    }
    return false;
  }
}
