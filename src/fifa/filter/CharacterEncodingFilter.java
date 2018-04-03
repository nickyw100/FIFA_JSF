// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharacterEncodingFilter.java

package fifa.filter;

import java.io.IOException;
import javax.servlet.*;

public class CharacterEncodingFilter
    implements Filter
{

    public CharacterEncodingFilter()
    {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    public void destroy()
    {
    }

    public void init(FilterConfig filterconfig)
        throws ServletException
    {
    }
}
