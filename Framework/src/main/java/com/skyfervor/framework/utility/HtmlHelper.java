package com.skyfervor.framework.utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class HtmlHelper {
	/**
	 * 
	 * @param contextPath
	 * @param relativePath
	 * @return
	 */
	public static String getScriptUrl(String contextPath, String relativePath) {		
//		return ServiceConfigContext.GetProviderCrmStaticUrl() + "/crm-web/providercrm/scripts/" + relativePath + "?123";
		
		return contextPath + "/providercrm/scripts/" + relativePath;
	}
	/**
	 * 
	 * @param contextPath
	 * @param relativePath
	 * @return
	 */
	public static String getStyleUrl(String contextPath, String relativePath) {
//		return ServiceConfigContext.GetProviderCrmStaticUrl() + "/crm-web/providercrm/styles/" + relativePath + "?123";
		return contextPath + "/providercrm/styles/" + relativePath;
	}
	/**
	 * 
	 * @param contextPath
	 * @param relativePath
	 * @return
	 */
	public static String getImageUrl(String contextPath, String relativePath) {
//		return ServiceConfigContext.GetProviderCrmStaticUrl() + "/crm-web/providercrm/images/" + relativePath + "?123";
		return contextPath + "/providercrm/images/" + relativePath;
	}

	/**
	 * 非Ajax情况，Controller中需提示用户，并关闭界面时使用。
	 * 		例如：修改活动时，弹出活动详情页。当用户无权限，要提示用户并关闭弹出页面。
	 * @param response 当前请求的response对象。
	 * @param info     需要给用户提示的消息内容。
	 * @throws IOException
	 */
	public static void sendMsg(HttpServletResponse response, String info) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/javascript\">");
		builder.append("alert('");
		builder.append(info);
		builder.append("');");
		builder.append("window.close();");
		builder.append("</script>");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(builder.toString());
		printWriter.flush();
	}
}
