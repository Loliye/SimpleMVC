package org.mikufans;

import org.mikufans.core.ConfigHelper;

public interface SimpleConstants
{
    String UTF_8 = "UTF-8";

    String CONFIG_PROPS = "simple.properties";
    String SQL_PROPS = "simple-sql.properties";


    String PK_NAME = "id";
    String PACKAGE_PATH = "simple.app_base_package";


    String WWW_PATH=ConfigHelper.getString("simple.www_path","/www/");
    String JSP_PATH=ConfigHelper.getString("simple.jsp_path","/WEB-INF/jsp/");
}
